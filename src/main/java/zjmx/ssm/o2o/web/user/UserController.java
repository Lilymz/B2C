package zjmx.ssm.o2o.web.user;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import zjmx.ssm.o2o.entity.Order;
import zjmx.ssm.o2o.entity.ShippingAddr;
import zjmx.ssm.o2o.entity.Shop;
import zjmx.ssm.o2o.entity.UserInfo;
import zjmx.ssm.o2o.service.OrderService;
import zjmx.ssm.o2o.service.ShippingAddrService;
import zjmx.ssm.o2o.service.ShopService;
import zjmx.ssm.o2o.service.UserInfoService;
import zjmx.ssm.o2o.utils.HttpServletRequestUtil;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserInfoService userinfoService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private ShippingAddrService shippingAddrService;
	@Autowired
	private ShopService shopService;
	@RequestMapping("/user_login")
	private String login() {
		return "user/userLogin";
	}
	@RequestMapping("/goUserOrder")
	private String goUserOrder() {return "user/userOrder";}
	//用户注册
	@RequestMapping(value = "/userRegister",method = {RequestMethod.POST})
	@ResponseBody
	private Map<String, Object> userRegister(HttpServletRequest request,@RequestBody String user){
		Map<String , Object> modelMap=new HashMap<String, Object>();
		ObjectMapper mapper=new ObjectMapper();
		if(user.replaceAll("\"", "").equals("")) {
			modelMap.put("success",false);
			modelMap.put("errMsg","未成功传入数据，请核对输入信息!");
			return modelMap;
		}
		try {
			UserInfo userInfo=mapper.readValue(user, UserInfo.class);
			userInfo.setProfileImg("\\image\\ProductImageDetailed\\designImg.jpg");
			userInfo.setEnableStatus(0);
			userInfo.setUserType(1);
			userInfo.setCreateTime(new Date());
			userInfo.setLastEditTime(new Date());
			userInfo.setMoney(10);
			int effectRow=userinfoService.insertUserInfoService(userInfo);
			if(effectRow>0) {
				modelMap.put("success",true);
				modelMap.put("Msg","注册成功，等待管理员审核");
				modelMap.put("url","/o2o/");
				return modelMap;
			}else {
				modelMap.put("success",false);
				modelMap.put("errMsg","未注册成功");
				return modelMap;
			}
		} catch (JsonProcessingException e) {
			modelMap.put("success",false);
			modelMap.put("errMsg",e.getMessage());
			return modelMap;
		}
	}
	//确认状态
	@RequestMapping("/submitOrder")
	@ResponseBody
	private Map<String, Object> updateOrder(HttpServletRequest request,@RequestBody String orderId){
		Map<String, Object> modelMap=new HashMap<>();
		//非空校验
		if(orderId==null) {
			modelMap.put("success",false);
			modelMap.put("errMsg","未能更新订单,无法获取更新参数");
			return modelMap;
		}
			Order order=new Order();
			order.setOrderId(Integer.valueOf(orderId.replaceAll("\"", "").trim()));
			order.setOrderEnableStatus(1);
			int updateOrderByIf = orderService.updateOrderByIf(order);
			if(updateOrderByIf>0) {
			modelMap.put("success",true);
			modelMap.put("url","/o2o/user/goUserOrder?userId="+((UserInfo)request.getSession().getAttribute("userInfo")).getUserId());
			return modelMap;
			}
			modelMap.put("success",false);
			modelMap.put("errMsg","未成功更新数据");
			return modelMap;
	}
	//用户订单页面
	@RequestMapping(value = "/getUserOrder",method = {RequestMethod.GET})
	@ResponseBody
	private Map<String ,Object> getUserOrder(HttpServletRequest request,@RequestParam("userId")Integer userId){
		Map<String, Object> modelMap=new HashMap<>();
		//非空校验
		if(userId==null) {
			modelMap.put("success",false);
			modelMap.put("errMsg","未成功获取用户Id,无法查询订单信息");
			return modelMap;
		}
		try {
			Order orderCondition=new Order();
			UserInfo userInfo=new UserInfo();
			userInfo.setUserId(userId);
			orderCondition.setUserInfo(userInfo);
			//根据id查询属于该店铺下的订单
			List<Order> orders = orderService.queryOrderByShopId(orderCondition);
			if (orders.size()==0) {
				modelMap.put("success",false);
				modelMap.put("Msg","无订单信息");
				return modelMap;
			}
			modelMap.put("success",true);
			modelMap.put("orders",orders);
			return modelMap;
		} catch (Exception e) {
			modelMap.put("success",false);
			modelMap.put("errMsg",e.getMessage());
			return modelMap;
		}
	}
	//批量添加订单
	@RequestMapping(value = "/batchaddOrderBydirect",method = {RequestMethod.POST})
	@ResponseBody
	private Map<String, Object> batchaddOrderBydirect(HttpServletRequest request){
	Map<String, Object> modelMap=new HashMap<String, Object>();
	String ShopCartProductName=request.getParameter("ShopCartProductName");
	String ShopCartProductPrice=request.getParameter("ShopCartProductPrice");
	String ShopCartShopId=request.getParameter("ShopCartShopId");
		ObjectMapper mapper=new ObjectMapper();
		List<String> listShopCartProductName=null;
		List<String> listShopCartProductPrice=null;
		List<String> listShopCartShopId=null;
		try {
			listShopCartProductName=mapper.readValue(ShopCartProductName, List.class);
			listShopCartProductPrice=mapper.readValue(ShopCartProductPrice, List.class);
			listShopCartShopId=mapper.readValue(ShopCartShopId, List.class);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(listShopCartProductPrice.size()==0||listShopCartProductName.size()==0) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "传入商品数据为空!");
			return modelMap;
		}
		List<Order> list=new ArrayList<Order>();
		for (int i = 0; i < listShopCartProductName.size(); i++) {
			Date date=new Date();
			Order order=new Order();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
			order.setOrderNumber(sdf.format(date)+((int)9999*Math.random()*100));
			order.setOrderProductName(listShopCartProductName.get(i));
			order.setOrderMethod(1);
			order.setCreateTime(new Date());
			order.setDealTime(new Date(0L));
			order.setOrderEnableStatus(0);
			order.setOrderPrice(listShopCartProductPrice.get(i));
			UserInfo userInfo= (UserInfo)request.getSession().getAttribute("userInfo");
			ShippingAddr shippingAddr=new ShippingAddr();
			shippingAddr.setUserInfo(userInfo);
			shippingAddr = shippingAddrService.queryShippingAddrByIf(shippingAddr);
			order.setUserInfo(userInfo);
			order.setShippingAddr(shippingAddr);
			Shop shop = shopService.getByShopId(Long.valueOf(listShopCartShopId.get(i)));
			order.setShop(shop);
			list.add(order);
		}
		
		int effectRow=orderService.batchInsertOrder(list);
		if(effectRow>0) {
			modelMap.put("success", true);
			modelMap.put("url","/o2o/Page/shop");
			return modelMap;
		}
		modelMap.put("success", false);
		modelMap.put("errMsg", "未插入成功！");
		return modelMap;
	}
	//加入订单
	@RequestMapping(value = "/addOrderBydirect",method = {RequestMethod.POST})
	@ResponseBody
	private Map<String, Object> addOrderBydirect(HttpServletRequest request){
		Map<String, Object> modelMap=new HashMap<String, Object>();
		String productName=request.getParameter("productName");
		String promotionPrice=request.getParameter("promotionPrice");
		String shopId=request.getParameter("shopId");
		//非空判断
		if(productName==null||promotionPrice==null||shopId==null) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "传入购买数据为空!");
			return modelMap;
		}
		Date date=new Date();
		Order order=new Order();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		order.setOrderNumber(sdf.format(date)+((int)9999*Math.random()*100));
		order.setOrderProductName(productName);
		order.setOrderMethod(1);
		order.setCreateTime(new Date());
		order.setDealTime(new Date(0L));
		order.setOrderEnableStatus(0);
		order.setOrderPrice(promotionPrice);
		UserInfo userInfo= (UserInfo)request.getSession().getAttribute("userInfo");
		ShippingAddr shippingAddr=new ShippingAddr();
		shippingAddr.setUserInfo(userInfo);
		shippingAddr = shippingAddrService.queryShippingAddrByIf(shippingAddr);
		order.setUserInfo(userInfo);
		order.setShippingAddr(shippingAddr);
		Shop shop = shopService.getByShopId(Long.valueOf(shopId));
		order.setShop(shop);
		int effectRow=orderService.insertOrder(order);
		if(effectRow>0) {
			modelMap.put("success", true);
			modelMap.put("url","/o2o/shop/shopindex?shopId="+shopId);
			return modelMap;
		}
		modelMap.put("success", false);
		modelMap.put("errMsg", "未插入成功！");
		return modelMap;
	}
	//用户登录
	@RequestMapping(value = "/verifyUser",method = {RequestMethod.POST})
	@ResponseBody
	private Map<String, Object> verifyUser(HttpServletRequest request){
		Map<String, Object> modelMap=new HashMap<String, Object>();
		request.getSession().setAttribute("userName", request.getParameter("userName"));
		request.getSession().setAttribute("userPassword", request.getParameter("userPassword"));
		UserInfo verifyInfo=new UserInfo();
		verifyInfo.setUserName(HttpServletRequestUtil.getString(request, "userName"));
		verifyInfo.setUserPassword(HttpServletRequestUtil.getString(request, "userPassword"));
		verifyInfo=userinfoService.verifyUserInfo(verifyInfo);
		UserInfo userInfo=null;
		if(verifyInfo!=null&&verifyInfo.getEnableStatus()==1) {
			request.getSession().setAttribute("userInfo",verifyInfo);
		}else if (verifyInfo.getEnableStatus()==0) {
			modelMap.put("success", false);
			modelMap.put("errMsg","账号审核中...");
			return modelMap;
		}else if(verifyInfo.getEnableStatus()==2){
			modelMap.put("success", false);
			modelMap.put("errMsg","账号被平台禁用...");
			return modelMap;
		}
		userInfo=(UserInfo)request.getSession().getAttribute("userInfo");
		if(userInfo!=null) {
			try {
				if(userInfo.getUserId()!=null) {
				modelMap.put("success", true);
				modelMap.put("url", "/o2o/Page/index");
				return modelMap;
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", "用户登录失败，查找不到用户id！");
					return modelMap;
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg",e.getMessage());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "未成功获取用户，用户不存在！");
			return modelMap;
		}
	}
	//用户校验（testing）
	@RequestMapping(value = "/isLogin",method = {RequestMethod.GET})
	@ResponseBody
	private Map<String, Object> isLogin(HttpServletRequest request){
		Map<String, Object> modelMap=new HashMap<String, Object>();
		UserInfo userInfo=(UserInfo)request.getSession().getAttribute("userInfo");
		if(userInfo!=null) {
			try {
				if(userInfo.getMoney()>100) {
					modelMap.put("success", true);
					modelMap.put("userInfo",userInfo);
					return modelMap;
				}else {
					modelMap.put("success", true);
					modelMap.put("userInfo",userInfo);
					modelMap.put("errMsg","余额不足100!请尽快充值");
					return modelMap;
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg",e.getMessage());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg","用户未登录请先登录!");
			return modelMap;
		}
	}
	//用户校验（testing）
	@RequestMapping(value = "/UpdateUser",method = {RequestMethod.POST})
	@ResponseBody
	private Map<String, Object> updateUser(HttpServletRequest request){
		Map<String, Object> modelMap=new HashMap<String, Object>();
		request.getSession().setAttribute("id", request.getParameter("id"));
		request.getSession().setAttribute("money", request.getParameter("money"));
		Integer id=HttpServletRequestUtil.getInt(request, "id");
		Integer money=HttpServletRequestUtil.getInt(request, "money");
		if (money!=null&&id!=null) {
			UserInfo userInfoConditon=new UserInfo();
			userInfoConditon.setUserId(id);
			userInfoConditon.setMoney(money);
			userinfoService.updateUserInfoBymoneyService(userInfoConditon);
			userInfoConditon.setMoney(null);
			userInfoConditon=userinfoService.queryUserInfoService(userInfoConditon);
			request.getSession().setAttribute("userInfo",userInfoConditon);
			UserInfo userInfo=(UserInfo)request.getSession().getAttribute("userInfo");
			modelMap.put("success", true);
			modelMap.put("userInfo",userInfo);
			return modelMap;
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg","未购买成功！");
			return modelMap;
		}
		
	}
}
