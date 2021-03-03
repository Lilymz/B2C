package zjmx.ssm.o2o.web.superadmin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import zjmx.ssm.o2o.dto.ShopExecution;
import zjmx.ssm.o2o.entity.Admin;
import zjmx.ssm.o2o.entity.Shop;
import zjmx.ssm.o2o.entity.UserInfo;
import zjmx.ssm.o2o.service.AdminService;
import zjmx.ssm.o2o.service.ShopService;
import zjmx.ssm.o2o.service.UserInfoService;
import zjmx.ssm.o2o.utils.MD5EncodeUtil;

@Controller
@RequestMapping("superadmin")
public class SuperAdminController {
	//查询userType为2的userInfo
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private ShopService shopservice;
	//店铺管理更改
	@RequestMapping(value = "/updateShopByShopId",method = {RequestMethod.POST})
	@ResponseBody
	private Map<String, Object> updateShopByShopId(HttpServletRequest request){
		Map<String, Object> modelMap=new HashMap<String, Object>();
		String shopId=request.getParameter("currentId");
		String flag=request.getParameter("flag");
		String updateData=request.getParameter("updateData");
		if(shopId==null||updateData==null) {
			modelMap.put("success",false);
			modelMap.put("errMsg","参数未接受成功");
			return modelMap;
		}
		Shop shopCondition=new Shop();
		shopCondition.setShopId(Long.valueOf(shopId));
		if (Integer.valueOf(flag)==1) {
			shopCondition.setPriority(Integer.valueOf(updateData));
		}else if (Integer.valueOf(flag)==2) {
			shopCondition.setEnableStatus(Integer.valueOf(updateData));
		}else if (Integer.valueOf(flag)==3) {
			shopCondition.setAdvice(updateData);
		}
		try {
			int effectedRow = shopservice.updateShopByshopIdService(shopCondition);
			if(effectedRow>0) {
				modelMap.put("success",true);
				//更新当前页面
				modelMap.put("url","/o2o/superadmin/shopRegisterManagement");
				return modelMap;
			}
			modelMap.put("success",false);
			modelMap.put("errMsg","未能成功匹配店铺");
			return modelMap;
		} catch (Exception e) {
			modelMap.put("success",false);
			modelMap.put("errMsg",e.getMessage());
			return modelMap;
		}
	}
	//店铺审核管理
	@RequestMapping(value = "/getshoplistEnable",method = {RequestMethod.GET})
	@ResponseBody
	private Map<String, Object> getShopList(HttpServletRequest request){
		Map<String, Object> modelMap=new HashMap<String, Object>();
		try {
			Shop ShopCondition=new Shop();
			ShopCondition.setEnableStatus(0);
			ShopExecution se=shopservice.getShopList(ShopCondition, 0, 100);
			modelMap.put("success", true);
			modelMap.put("shopList",se.getShoplist());
			return modelMap;
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
	}
	//管理员登录
	@RequestMapping(value = "/Login",method = {RequestMethod.POST})
	@ResponseBody
	private Map<String, Object> VerifyLogin(HttpServletRequest request){
		Map<String, Object> modelMap=new HashMap<String, Object>();
		//传入用户名和密码
		String adminName = request.getParameter("adminName");
		String adminPassword = request.getParameter("adminPassword");
		Admin admin=new Admin();
		//非空校验
		if (adminName==null&&adminPassword==null) {
			modelMap.put("success" , false);
			modelMap.put("errMsg" , "未成功接收用户名或者密码");
			return modelMap;
		}
		try {
			admin.setAdminName(adminName);
			admin.setAdminPassword(MD5EncodeUtil.GetMD5(adminPassword));
			//核对用户名密码
			Admin adminInfo = adminService.queryAdminService(admin);
			if(adminInfo!=null) {
				modelMap.put("success",true);
				modelMap.put("url", "/o2o/superadmin/userManagement");
				//把管理员放入session
				request.getSession().setAttribute("adminInfo", adminInfo);
				return modelMap;
			}else {
				modelMap.put("success",false);
				modelMap.put("errMsg","请认真核对账号或者密码");
				return modelMap;
			}
		} catch (Exception e) {
			modelMap.put("success",false);
			modelMap.put("errMsg",e.getMessage());
			return modelMap;
		}
	}
	@RequestMapping(value = "/isLogin",method = {RequestMethod.GET})
	@ResponseBody
	private Map<String, Object> isLogin(HttpSession session){
		Map<String, Object> modelMap=new HashMap<String, Object>();
		//获取存在session中的Superadmin数据
		Admin adminInfo=(Admin) session.getAttribute("adminInfo");
		if (adminInfo==null) {
			modelMap.put("success",true);
			modelMap.put("url","/o2o/superadmin/index");
			
			modelMap.put("errMsg","管理员未登录!");
			return modelMap;
		}
		modelMap.put("success",false);
		return modelMap;
	}
	//所有用户信息
	@RequestMapping(value = "/getAllUser",method = {RequestMethod.GET})
	@ResponseBody
	private Map<String, Object> getAllUser(HttpServletRequest request){
		Map<String, Object> modelMap=new HashMap<String, Object>();
		//无条件查询所有店主即userType=2
		UserInfo userInfoConditon=new UserInfo();
		userInfoConditon.setUserType(1);
		List<UserInfo> userInfos = userInfoService.queryUserInfosService(userInfoConditon);
		if(userInfos.size()==0) {
			modelMap.put("success",false);
			modelMap.put("errMsg","本平台无用户");
			return modelMap;
		}
		modelMap.put("success",true);
		modelMap.put("adminInfo",request.getSession().getAttribute("adminInfo"));
		modelMap.put("userInfos",userInfos);
		return modelMap;
	}
	//管理员退出
	@RequestMapping("/logout")
	@ResponseBody
	private Map<String, Object> logout(HttpSession session){
		Map<String, Object> modelMap=new HashMap<String, Object>();
		session.removeAttribute("adminInfo");
		modelMap.put("success", true);
		modelMap.put("url", "/o2o/superadmin/index");
		return modelMap;
	}
	//搜索用户信息
	@RequestMapping(value = "/userSearchByName",method = {RequestMethod.POST})
	@ResponseBody
	private Map<String, Object> userSearchByName(HttpServletRequest request,@RequestBody String userName){
		Map<String, Object> modelMap=new HashMap<String, Object>();
		UserInfo userInfoConditon=new UserInfo();
		userInfoConditon.setUserType(1);
		userInfoConditon.setUserName(userName.replaceAll("\"", ""));
		List<UserInfo> userInfos = userInfoService.queryUserInfosService(userInfoConditon);
		if(userInfos.size()==0) {
			modelMap.put("success",false);
			modelMap.put("errMsg","本平台无此用户");
			return modelMap;
		}
		modelMap.put("success",true);
		modelMap.put("userInfos",userInfos);
		return modelMap;
	}
	//更改用户状态值
	@RequestMapping(value = "/updateUserByEnableStatus",method = {RequestMethod.POST})
	@ResponseBody
	private Map<String, Object> updateUserByEnableStatus(HttpServletRequest request){
		Map<String, Object> modelMap=new HashMap<String, Object>();
		String userId=request.getParameter("currentId");
		String userEnableStatus=request.getParameter("userEnableStatus");
		if(userId==null&&userEnableStatus==null) {
			modelMap.put("success",false);
			modelMap.put("errMsg","参数未接受成功");
			return modelMap;
		}
		UserInfo userInfoConditon=new UserInfo();
		userInfoConditon.setUserId(Integer.valueOf(userId.trim()));
		userInfoConditon.setEnableStatus(Integer.valueOf(userEnableStatus.trim()));
		try {
			int effectedRow = userInfoService.updateUserInfoBymoneyService(userInfoConditon);
			if(effectedRow>0) {
				modelMap.put("success",true);
				//更新当前页面
				modelMap.put("url","/o2o/superadmin/userManagement");
				return modelMap;
			}
			modelMap.put("success",false);
			modelMap.put("errMsg","未能成功匹配用户");
			return modelMap;
		} catch (Exception e) {
			modelMap.put("success",false);
			modelMap.put("errMsg",e.getMessage());
			return modelMap;
		}
	}
	//删除用户
	@RequestMapping(value = "/deleteUserByuserId",method = {RequestMethod.POST})
	@ResponseBody
	private Map<String, Object> deleteUserByuserId(HttpServletRequest request,@RequestBody String currentId){
		Map<String, Object> modelMap=new HashMap<String, Object>();
		if(currentId==null) {
			modelMap.put("success",false);
			modelMap.put("errMsg","未成功获取参数Id");
			return modelMap;
		}
		try {
			int effectedRow = userInfoService.deleteUserInfoByUserId(Integer.valueOf(currentId.replaceAll("\"", "").trim()));
			if(effectedRow>0) {
				modelMap.put("success",true);
				//更新当前页面
				modelMap.put("url","/o2o/superadmin/userManagement");
				return modelMap;
			}
			modelMap.put("success",false);
			modelMap.put("errMsg","未能成功匹配用户");
			return modelMap;
		} catch (Exception e) {
			modelMap.put("success",false);
			modelMap.put("errMsg",e.getMessage());
			return modelMap;
		}
	}
	//所有商店管理者信息
	@RequestMapping(value = "/getAllShopAdmin",method = {RequestMethod.GET})
	@ResponseBody
	private Map<String, Object> getAllShopAdmin(HttpServletRequest request){
		Map<String, Object> modelMap=new HashMap<String, Object>();
		//无条件查询所有店主即userType=2
		UserInfo userInfoConditon=new UserInfo();
		userInfoConditon.setUserType(2);
		List<UserInfo> ShopAdmins = userInfoService.queryUserInfosService(userInfoConditon);
		if(ShopAdmins.size()==0) {
			modelMap.put("success",false);
			modelMap.put("errMsg","无店铺管理员");
			return modelMap;
		}
		modelMap.put("success",true);
		modelMap.put("adminInfo",request.getSession().getAttribute("adminInfo"));
		modelMap.put("ShopAdmins",ShopAdmins);
		return modelMap;
	}
	//搜索店主信息
	@RequestMapping(value = "/shopSearchByName",method = {RequestMethod.POST})
	@ResponseBody
	private Map<String, Object> shopSearchByName(HttpServletRequest request,@RequestBody String shopName){
		Map<String, Object> modelMap=new HashMap<String, Object>();
		UserInfo userInfoConditon=new UserInfo();
		userInfoConditon.setUserType(2);
		userInfoConditon.setUserName(shopName.replaceAll("\"", ""));
		List<UserInfo> ShopAdmins = userInfoService.queryUserInfosService(userInfoConditon);
		if(ShopAdmins.size()==0) {
			modelMap.put("success",false);
			modelMap.put("errMsg","本平台无此店主");
			return modelMap;
		}
		modelMap.put("success",true);
		modelMap.put("ShopAdmins",ShopAdmins);
			return modelMap;
	}
	//更改店主状态值
	@RequestMapping(value = "/updateShopByEnableStatus",method = {RequestMethod.POST})
	@ResponseBody
	private Map<String, Object> updateShopByEnableStatus(HttpServletRequest request){
		Map<String, Object> modelMap=new HashMap<String, Object>();
		String userId=request.getParameter("currentId");
		String shopEnableStatus=request.getParameter("shopEnableStatus");
		if(userId==null&&shopEnableStatus==null) {
			modelMap.put("success",false);
			modelMap.put("errMsg","参数未接受成功");
			return modelMap;
		}
		UserInfo userInfoConditon=new UserInfo();
		userInfoConditon.setUserId(Integer.valueOf(userId.trim()));
		userInfoConditon.setEnableStatus(Integer.valueOf(shopEnableStatus.trim()));
		try {
			int effectedRow = userInfoService.updateUserInfoBymoneyService(userInfoConditon);
			if(effectedRow>0) {
				modelMap.put("success",true);
				//更新当前页面
				modelMap.put("url","/o2o/superadmin/shopManagement");
				return modelMap;
			}
			modelMap.put("success",false);
			modelMap.put("errMsg","未能成功匹配店主");
			return modelMap;
		} catch (Exception e) {
			modelMap.put("success",false);
			modelMap.put("errMsg",e.getMessage());
			return modelMap;
		}
	}
	//删除店家
	@RequestMapping(value = "/deleteShopAdminByuserId",method = {RequestMethod.POST})
	@ResponseBody
	private Map<String, Object> deleteShopAdminByuserId(HttpServletRequest request,@RequestBody String currentId){
		Map<String, Object> modelMap=new HashMap<String, Object>();
		if(currentId==null) {
			modelMap.put("success",false);
			modelMap.put("errMsg","未成功获取参数Id");
			return modelMap;
		}
		try {
			int effectedRow = userInfoService.deleteUserInfoByUserId(Integer.valueOf(currentId.replaceAll("\"", "").trim()));
			if(effectedRow>0) {
				modelMap.put("success",true);
				//更新当前页面
				modelMap.put("url","/o2o/superadmin/shopManagement");
				return modelMap;
			}
			modelMap.put("success",false);
			modelMap.put("errMsg","未能成功匹配店主");
			return modelMap;
		} catch (Exception e) {
			modelMap.put("success",false);
			modelMap.put("errMsg",e.getMessage());
			return modelMap;
		}
	}
}
