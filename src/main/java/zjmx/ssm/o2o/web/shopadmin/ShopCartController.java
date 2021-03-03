package zjmx.ssm.o2o.web.shopadmin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import zjmx.ssm.o2o.entity.ShopCart;
import zjmx.ssm.o2o.entity.UserInfo;
import zjmx.ssm.o2o.service.ShopCartService;
import zjmx.ssm.o2o.service.UserInfoService;
import zjmx.ssm.o2o.utils.HttpServletRequestUtil;

@Controller
@RequestMapping("shopcart")
public class ShopCartController {
	@Autowired
	private ShopCartService shopCartService;
	@Autowired
	private UserInfoService userInfservice;
	@RequestMapping(value = "/goShopCart")
	private String goShopCart(HttpServletRequest request){
		return "frontPage/shopcart";
	}
	@RequestMapping(value = "/insertShopCart",method = {RequestMethod.POST})
	@ResponseBody
	private Map<String, Object> insertShopCart(HttpServletRequest request){
		Map<String, Object> modelMap=new HashMap<String, Object>();
		request.getSession().setAttribute("productImg", request.getParameter("productImg"));
		request.getSession().setAttribute("productDesc", request.getParameter("productDesc"));
		request.getSession().setAttribute("promotionPrice", request.getParameter("promotionPrice"));
		request.getSession().setAttribute("userId", request.getParameter("userId"));
		String productName=request.getParameter("productName");
		String shopId=request.getParameter("shopId");
		String productImg=HttpServletRequestUtil.getString(request, "productImg");
		String productDesc=HttpServletRequestUtil.getString(request, "productDesc");
		String promotionPrice=HttpServletRequestUtil.getString(request, "promotionPrice");
		int userId=HttpServletRequestUtil.getInt(request, "userId");
		if(productImg!=null&&productDesc!=null&&promotionPrice!=null&&userId!=0&&productName!=null) {
			ShopCart shopCart=new ShopCart();
			shopCart.setShopcartProductImg(productImg);
			shopCart.setShopcartProductDesc(productDesc);
			shopCart.setShopcartProductPromotion(Integer.valueOf(promotionPrice));
			shopCart.setShopcartProductName(productName);
			shopCart.setShopcartShopId(Long.valueOf(shopId));
			UserInfo userInfo=new UserInfo();
			userInfo.setUserId(Integer.valueOf(userId));
			shopCart.setUserfo(userInfo);
			try {
				if(shopCartService.insertShopCartService(shopCart)>0) {
					modelMap.put("success", true);
					return modelMap;
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", "购物车信息插入错误！");
					return modelMap;
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg",e.getMessage());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "购物车信息不完整！");
			return modelMap;
		}
		
	}
	@RequestMapping(value = "/queryShopcartByuserId",method = {RequestMethod.GET})
	@ResponseBody
	private Map<String, Object> queryShopcartByuserId(HttpServletRequest request){
		Map<String, Object> modelMap=new HashMap<String, Object>();
		try {
			UserInfo userInfo=(UserInfo) request.getSession().getAttribute("userInfo");
			if(userInfo!=null) {
				ShopCart shopCart = new ShopCart();
				shopCart.setUserfo(userInfo);
				List<ShopCart> queryShopCartsService = shopCartService.queryShopCartsService(shopCart);
				modelMap.put("success", true);
				modelMap.put("queryShopCartsService",queryShopCartsService);
				return modelMap;
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "用户未登录！无法购买");
				return modelMap;
			}
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		
	}
	@RequestMapping(value = "/deleteShopcartByImg",method = {RequestMethod.POST})
	@ResponseBody
	private Map<String, Object> deleteShopcartByImg(HttpServletRequest request,@RequestBody List<String> ShopCartImgAddr){
		Map<String, Object> modelMap=new HashMap<String, Object>();
		ShopCart shopCart=new ShopCart();
		System.out.println(ShopCartImgAddr);
		if(ShopCartImgAddr!=null) {
			try {
				for (int i = 0; i < ShopCartImgAddr.size()-1; i++) {
					shopCart.setShopcartProductImg(ShopCartImgAddr.get(i));
					shopCartService.deleteShopCart(shopCart);
				}
				UserInfo userInfoConditon=(UserInfo)request.getSession().getAttribute("userInfo");
				userInfoConditon.setMoney(userInfoConditon.getMoney()-Integer.valueOf(ShopCartImgAddr.get(ShopCartImgAddr.size()-1)));
				userInfservice.updateUserInfoBymoneyService(userInfoConditon);
				
				modelMap.put("success", true);
				modelMap.put("url","/o2o/shopcart/goShopCart");
				return modelMap;
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
			
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg","未获取到前台发送过来的购物车商品信息");
			return modelMap;
		}
	}
}
