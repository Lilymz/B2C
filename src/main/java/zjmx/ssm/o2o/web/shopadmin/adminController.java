package zjmx.ssm.o2o.web.shopadmin;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import zjmx.ssm.o2o.entity.Comment;
import zjmx.ssm.o2o.entity.UserInfo;
import zjmx.ssm.o2o.service.UserInfoService;

@Controller
@RequestMapping("shopadmin")
public class adminController {
	
	@Autowired
	private UserInfoService userInfoService;
	@RequestMapping(value = "/shopAdminLogin")
	private String WellcomeIndexPage() {
		return "shop/shopAdminLogin";
	}
	@RequestMapping(value = "/shopAdminRegister")
	private String registerIndexPage() {
		return "shop/ShopAdminRegister";
	}
	//店家注册
	@RequestMapping(value = "/shopAdminRegister",method = {RequestMethod.POST})
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
			userInfo.setUserType(2);
			userInfo.setCreateTime(new Date());
			userInfo.setLastEditTime(new Date());
			userInfo.setMoney(0);
			int effectRow=userInfoService.insertUserInfoService(userInfo);
			if(effectRow>0) {
				modelMap.put("success",true);
				modelMap.put("Msg","注册成功，等待管理员审核");
				modelMap.put("url","/o2o/shopadmin/shopAdminLogin");
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
	//管理员用户登录
	@RequestMapping(value = "/verifyShopAdmin",method = {RequestMethod.POST})
	@ResponseBody
	private Map<String, Object> verifyUser(HttpServletRequest request){
		Map<String, Object> modelMap=new HashMap<String, Object>();
		//获取前端传来的用户名及密码
		String adminName = request.getParameter("adminName");
		String password = request.getParameter("password");
		if(adminName==null||password==null) {
			modelMap.put("success", false);
			modelMap.put("errMsg","店家用户名或密码错误");
			return modelMap;
		}
		UserInfo Shopadmin=new UserInfo();
		//userType=2确保是店家
		Shopadmin.setUserType(2);
		//进行登录验证
		Shopadmin.setUserName(adminName);
		Shopadmin.setUserPassword(password);
		//存入把店主信息存入session
		Shopadmin=userInfoService.verifyUserInfo(Shopadmin);
		if(Shopadmin!=null&&Shopadmin.getEnableStatus()==1) {
			request.getSession().setAttribute("Shopadmin",Shopadmin);
		}else if (Shopadmin.getEnableStatus()==0) {
			modelMap.put("success", false);
			modelMap.put("errMsg","账号审核中...");
			return modelMap;
		}else if(Shopadmin.getEnableStatus()==2){
			modelMap.put("success", false);
			modelMap.put("errMsg","账号被平台禁用...");
			return modelMap;
		}
		Shopadmin=(UserInfo)request.getSession().getAttribute("Shopadmin");
		if(Shopadmin!=null) {
			try {
				if(Shopadmin.getUserId()!=null) {
				modelMap.put("success", true);
				modelMap.put("url", "/o2o/shopadmin/shoplist");
				modelMap.put("isLogin", true);
				return modelMap;
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", "店主登录失败，无店主id！");
					return modelMap;
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg",e.getMessage());
				return modelMap;
			}
				
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg","店主请先登录!");
				return modelMap;
			}
	}
	//用户校验（testing）
	@RequestMapping(value = "/isLogin",method = {RequestMethod.GET})
	@ResponseBody
	private Map<String, Object> isLogin(HttpServletRequest request){
		Map<String, Object> modelMap=new HashMap<String, Object>();
		Comment adminInfo=(Comment)request.getSession().getAttribute("adminInfo");
		if(adminInfo!=null) {
			modelMap.put("success", true);
			modelMap.put("userInfo",adminInfo);
			modelMap.put("errMsg","余额不足100!请尽快充值");
			return modelMap;	
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg","用户未登录请先登录!");
			return modelMap;
		}
	}
}
