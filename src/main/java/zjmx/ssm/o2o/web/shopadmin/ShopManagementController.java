package zjmx.ssm.o2o.web.shopadmin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

import zjmx.ssm.o2o.dto.ProductExecution;
import zjmx.ssm.o2o.dto.ProductTypeExecution;
import zjmx.ssm.o2o.dto.ShopExecution;
import zjmx.ssm.o2o.entity.Area;
import zjmx.ssm.o2o.entity.Comment;
import zjmx.ssm.o2o.entity.ProductType;
import zjmx.ssm.o2o.entity.Shop;
import zjmx.ssm.o2o.entity.ShopType;
import zjmx.ssm.o2o.entity.UserInfo;
import zjmx.ssm.o2o.enums.ProductEnum;
import zjmx.ssm.o2o.enums.ProductTypeEnum;
import zjmx.ssm.o2o.enums.ShopStateEnum;
import zjmx.ssm.o2o.service.AreaService;
import zjmx.ssm.o2o.service.CommentService;
import zjmx.ssm.o2o.service.ProductService;
import zjmx.ssm.o2o.service.ProductTypeService;
import zjmx.ssm.o2o.service.ShopService;
import zjmx.ssm.o2o.service.ShopTypeService;
import zjmx.ssm.o2o.utils.CodeCheckUtil;
import zjmx.ssm.o2o.utils.HttpServletRequestUtil;
import zjmx.ssm.o2o.utils.ImageHolder;
import zjmx.ssm.o2o.utils.ImageUtil;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {

	@Autowired
	private ShopService shopservice;
	
	@Autowired
	private ShopTypeService shoptypeservice;
	
	@Autowired
	private AreaService areaservice;
	@Autowired
	private ProductTypeService producttypeservice;
	@Autowired
	private ProductService productService;
	@Autowired
	private CommentService commentService;
	//????????????
	@RequestMapping("/logout")
	@ResponseBody
	private Map<String, Object> logout(HttpSession session){
		Map<String, Object> modelMap=new HashMap<String, Object>();
		session.removeAttribute("Shopadmin");
		modelMap.put("success", true);
		modelMap.put("url", "/o2o/shopadmin/shopAdminLogin");
		return modelMap;
	}
	@RequestMapping(value = "/insertComment",method = {RequestMethod.POST})
	@ResponseBody
	private Map<String, Object> insertComment(HttpSession session,@RequestBody String commentContent){
		Map<String, Object> modelMap=new HashMap<String, Object>();
		//??????????????????????????????
		if(commentContent.replaceAll("\"", "").trim().equals("")) {
			modelMap.put("success", false);
			modelMap.put("errMsg","????????????????????????!");
			return modelMap;
		}
		//??????????????????????????????
		 Shop shop = (Shop)session.getAttribute("currentShop");
		 if(shop==null) {
			modelMap.put("success", false);
			modelMap.put("errMsg","??????????????????????????????");
			return modelMap;
		 }
		 Comment comment=new Comment();
		 comment.setShop(shop);
		 comment.setLove(0);
		 comment.setCommentContent(commentContent.replaceAll("\"", "").trim());
		 comment.setCreateTime(new Date());
		 int effectRow = commentService.insertCommentService(comment);
		 if (effectRow>0) {
			 	modelMap.put("success", true);
				modelMap.put("url","/o2o/shop/shopindex?shopId="+shop.getShopId());
				return modelMap;
		 }
		modelMap.put("success", false);
		modelMap.put("errMsg","???????????????");
		return modelMap;
	}
	@RequestMapping(value = "/getCommentsByShopId")
	@ResponseBody
	private Map<String, Object> getCommentsByShopId(HttpServletRequest request){
		Map<String, Object> modelMap=new HashMap<String, Object>();
		//??????????????????
		Shop currentShop =(Shop) request.getSession().getAttribute("currentShop");
		if(currentShop==null) {
			modelMap.put("success", false);
			modelMap.put("errMsg","????????????????????????");
			return modelMap;
		}
		try {
			Comment commentCondition=new Comment();
			commentCondition.setShop(currentShop);
			//????????????.
			List<Comment> commentsService = commentService.getCommentsService(commentCondition);
			if (commentsService==null) {
				modelMap.put("success", true);
				modelMap.put("Msg","?????????????????????????????????");
				modelMap.put("comments",null);
				return modelMap;
			}
			modelMap.put("success", true);
			modelMap.put("Msg","");
			modelMap.put("comments",commentsService);
			return modelMap;
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg",e.getMessage());
			return modelMap;
		}
	}
	@RequestMapping(value = "/getShopsByShopType",method = {RequestMethod.POST})
	@ResponseBody
	private Map<String, Object> getShopsByShopType(HttpServletRequest request,@RequestBody long shopTypeId){
		Map<String, Object> modelMap=new HashMap<String, Object>();
		if(shopTypeId==0) {
			modelMap.put("success", false);
			modelMap.put("errMsg","???????????????????????????");
			return modelMap;
		}
		ShopType shopType=shoptypeservice.queryShopTypeByNameService(shopTypeId);
		if(shopType!=null) {
			try {
				
				Shop shopCondition=new Shop();
				shopCondition.setShopType(shopType);
				shopCondition.setEnableStatus(1);
				ShopExecution shopList = shopservice.getShopList(shopCondition, 0, 100);
				if(shopList.getCount()>0) {
					List<Shop> shoplist2 = shopList.getShoplist();
					modelMap.put("success", true);
					modelMap.put("shopList",shoplist2);
					return modelMap;
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg","????????????");
					return modelMap;
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg",e.getMessage());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg","????????????????????????");
			return modelMap;
		}
	}
	@RequestMapping(value = "/getshopByShopId",method = {RequestMethod.GET})
	@ResponseBody
	private Map<String, Object> getshopByShopId(HttpServletRequest request){
		Long shopId=HttpServletRequestUtil.getLong(request, "shopId");
		UserInfo userInfo=(UserInfo)request.getSession().getAttribute("userInfo");
		Map<String, Object> modelMap=new HashMap<String, Object>();
		if (userInfo!=null) {
			modelMap.put("userInfo",userInfo);
		}
		Shop byShopId = shopservice.getByShopId(shopId);
		if (byShopId!=null) {
			request.getSession().setAttribute("currentShop",byShopId);
			modelMap.put("success", true);
			modelMap.put("Shop", byShopId);
		}else {
			modelMap.put("success", false);
			modelMap.put("Msg", "?????????????????????????????????ID");
		}
		return modelMap;
	}
	@RequestMapping(value = "/getshoplistByShopLike",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	private Map<String, Object> getshoplistByShopLike(HttpServletRequest request){
		request.getSession().setAttribute("shopStr", request.getParameter("shopStr"));
		Map<String, Object> modelMap=new HashMap<String, Object>();
		UserInfo userInfo=(UserInfo)request.getSession().getAttribute("userInfo");
		String shopstr=HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper=new ObjectMapper();
		Shop ShopCondition=new Shop();
		try {
			
			ShopCondition=mapper.readValue(shopstr, Shop.class);
			ShopCondition.setEnableStatus(1);
			ShopExecution se=shopservice.getShopList(ShopCondition, 0, 5);
			modelMap.put("success", true);
			modelMap.put("user",userInfo);
			modelMap.put("shopList",se.getShoplist());
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("Msg", e.getMessage());
		}
		return modelMap;
	}
	@RequestMapping(value = "/getshoplistByArea",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	private Map<String, Object> getShopListByArea(HttpServletRequest request){
		request.getSession().setAttribute("shopStr", request.getParameter("shopStr"));
		Map<String, Object> modelMap=new HashMap<String, Object>();
		UserInfo userInfo=(UserInfo)request.getSession().getAttribute("userInfo");
		String shopstr=HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper=new ObjectMapper();
		Shop ShopCondition=new Shop();
		try {
			
			ShopCondition=mapper.readValue(shopstr, Shop.class);
			Area area=areaservice.queryAreaByName(ShopCondition.getArea().getAreaName());
			ShopCondition.setArea(area);
			ShopCondition.setUserInfo(userInfo);
			ShopCondition.setEnableStatus(1);
			ShopExecution se=shopservice.getShopList(ShopCondition, 0, 100);
			modelMap.put("success", true);
			modelMap.put("user",userInfo);
			modelMap.put("shopList",se.getShoplist());
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("Msg", e.getMessage());
		}
		return modelMap;
	}
	@RequestMapping(value = "/getproductByid",method = {RequestMethod.GET})
	@ResponseBody
	private Map<String, Object> getProductByid(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Shop CurrentShop=(Shop) request.getSession().getAttribute("currentShop");
		long productId=HttpServletRequestUtil.getLong(request, "productId");
		if (CurrentShop!=null) {
			try {
				ProductExecution pe=new ProductExecution();
				pe=productService.queryProductById(productId);
				List<ProductType> producttypeList=new ArrayList<ProductType>();
				producttypeList=(List<ProductType>) producttypeservice.getProductTypes(CurrentShop.getShopId()).getProductTypeslist();
				if (pe.getState()==ProductEnum.PRODUCTQUERY_SUCCESS.getState()) {
					modelMap.put("success",true);
					modelMap.put("Product",pe.getProduct());
					modelMap.put("productTypeList",producttypeList);
				}else {
					modelMap.put("success",false);
					modelMap.put("errMsg",pe.getStateInfo());
				}
			} catch (Exception e) {
				modelMap.put("success",false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		}else {
			modelMap.put("success",false);
			modelMap.put("errMsg", "???????????????");
			return modelMap;
		}
		return modelMap;
	}
	
	@RequestMapping(value = "/deleteproducttype",method = {RequestMethod.GET})
	@ResponseBody
	private Map<String, Object> DeleteProducttype(HttpServletRequest request){
		Map<String, Object> modelMap=new HashMap<String, Object>();
			try {
				ProductTypeExecution pe=producttypeservice.deleteProductType(HttpServletRequestUtil.getLong(request, "productTypeId"));
				if(pe.getState()==3333) {
					modelMap.put("success", true);
					modelMap.put("Msg",pe.getStateInfo());
				}else {
					modelMap.put("success", false);
					modelMap.put("error","????????????????????????");
					modelMap.put("Msg",pe.getStateInfo());
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("Msg","???????????????????????????????????????????????????????????????");
			}
			return modelMap;
	}
	//???????????????????????????
	@RequestMapping(value = "/batchproducttype",method = {RequestMethod.POST})
	@ResponseBody
	private Map<String, Object> batchInsertProducttype(@RequestBody List<ProductType> productTypeList,HttpServletRequest request){
		Map<String, Object> modelMap=new HashMap<String, Object>();
		Shop currentShop=(Shop)request.getSession().getAttribute("currentShop");
			for (ProductType productType : productTypeList) {
				productType.setShopId(currentShop.getShopId());
			}
			if(productTypeList!=null&&productTypeList.size()>0) {
				try {
					ProductTypeExecution pe=producttypeservice.batchInsertProductType(productTypeList);
					if (pe.getState()==ProductTypeEnum.INSERT_ProductType_SUCCESS.getState()) {
						modelMap.put("success",true);
						modelMap.put("Msg",pe.getStateInfo());
					}else {
						modelMap.put("errMsg",pe.getStateInfo());
						modelMap.put("success",false);
					}
				} catch (Exception e) {
					modelMap.put("errMsg",e.toString());
					modelMap.put("success",false);
					return modelMap;
				}
			}else {
				modelMap.put("errMsg","???????????????????????????");
				modelMap.put("success",false);
			}
			return modelMap;
	}
	
	
	
	@RequestMapping(value = "/getproducttype",method = {RequestMethod.GET})
	@ResponseBody
	private Map<String, Object> getProductTypes(HttpServletRequest request){
		Map<String, Object> modelMap=new HashMap<String, Object>();
		UserInfo Shopadmin=(UserInfo) request.getSession().getAttribute("Shopadmin");
		if(Shopadmin==null) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "??????????????????");
			modelMap.put("url", "/o2o/shopadmin/shopAdminLogin");
			return modelMap;
		}
		ProductTypeExecution pe=producttypeservice.getProductTypes(HttpServletRequestUtil.getLong(request, "ShopId"));
		if(pe!=null) {
			try {
				if (pe.getProductTypeslist()==null) {
					modelMap.put("success",false);
					modelMap.put("Msg","???ProductType?????????"+pe.getStateInfo());
				}else {
					modelMap.put("success",true);
					modelMap.put("ProductTypeList",pe.getProductTypeslist());
					modelMap.put("Msg","ProductType??????????????????"+pe.getStateInfo());
				}
			} catch (Exception e) {
				modelMap.put("success",false);
				modelMap.put("Msg",e.getMessage());
			}
		}
		return modelMap;
	}
	@RequestMapping(value = "/getshoplist",method = {RequestMethod.GET})
	@ResponseBody
	private Map<String, Object> getShopList(HttpServletRequest request){
		Map<String, Object> modelMap=new HashMap<String, Object>();
		UserInfo Shopadmin=(UserInfo) request.getSession().getAttribute("Shopadmin");
		if(Shopadmin==null) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "??????????????????");
			modelMap.put("url", "/o2o/shopadmin/shopAdminLogin");
			return modelMap;
		}
		try {
			Shop ShopCondition=new Shop();
			ShopCondition.setUserInfo(Shopadmin);
			ShopExecution se=shopservice.getShopList(ShopCondition, 0, 100);
			modelMap.put("success", true);
			modelMap.put("Shopadmin",Shopadmin);
			modelMap.put("shopList",se.getShoplist());
			return modelMap;
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
	}
	@RequestMapping(value = "/getshoplistUserInfo",method = {RequestMethod.GET})
	@ResponseBody
	private Map<String, Object> getshoplistUserInfo(HttpServletRequest request){
		Map<String, Object> modelMap=new HashMap<String, Object>();
		UserInfo userInfo=(UserInfo) request.getSession().getAttribute("userInfo");
		if(userInfo==null) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "??????????????????");
			modelMap.put("url", "/o2o/user/user_login");
			return modelMap;
		}
		try {
			Shop ShopCondition=new Shop();
			ShopCondition.setEnableStatus(1);
			ShopExecution se=shopservice.getShopList(ShopCondition, 0, 100);
			modelMap.put("success", true);
			modelMap.put("userInfo",userInfo);
			modelMap.put("shopList",se.getShoplist());
			return modelMap;
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
	}
	@RequestMapping(value = "/getshopById",method = {RequestMethod.GET})
	@ResponseBody
	private Map<String, Object> getShopById(HttpServletRequest request){
		Map<String, Object> modelMap=new HashMap<String, Object>();
		UserInfo Shopadmin=(UserInfo) request.getSession().getAttribute("Shopadmin");
		if(Shopadmin==null) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "??????????????????");
			modelMap.put("url", "/o2o/shopadmin/shopAdminLogin");
			return modelMap;
		}
		Long shopId=HttpServletRequestUtil.getLong(request, "ShopId");
		List<ShopType> shopTypelist=new ArrayList<ShopType>();
		if(shopId>-1) {
			//1.??????????????????
			Shop shop=shopservice.getByShopId(shopId);
			request.getSession().setAttribute("currentShop",shop);
			shopTypelist=shoptypeservice.getShoptypelist(new ShopType());
			
			//2.??????????????????
			List<Area> list=areaservice.getAreas();
			if (shop!=null&&list!=null) {
				modelMap.put("shopTypeList", shopTypelist);
				modelMap.put("shop", shop);
				modelMap.put("arealist", list);
				modelMap.put("success", true);
				return modelMap;
			}else {
				modelMap.put("success", false);
				modelMap.put("Msg", "shop????????????area???");
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("Msg", "???????????????Id???");
			return modelMap;
		}

	}
	@RequestMapping(value = "/getshopinitinfo",method = {RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> getShopInitInfo(){
		//??????modelmap????????????
		Map<String, Object> modelmap=new HashMap<String, Object>();
		List<ShopType> shopTypelist=new ArrayList<ShopType>();
		List<Area> arealist=new ArrayList<Area>();
		try {
			shopTypelist=shoptypeservice.getShoptypelist(new ShopType());
			arealist=areaservice.getAreas();
			modelmap.put("shopTypeList", shopTypelist);
			modelmap.put("areaList",arealist);
			modelmap.put("success", true);
		} catch (Exception e) {
			modelmap.put("success", false);
			modelmap.put("errMsg",e.getMessage());
		}
		return modelmap;
	}
	@RequestMapping(value = "/modifyshop",method = {RequestMethod.POST})
	@ResponseBody
	private Map<String, Object> modifyShop(HttpServletRequest request,@RequestParam("verifyCodeActual")String verifyCodeActual) throws RuntimeException, IOException{
		//??????request?????????????????????,?????????????????????????????????
		request.getSession().setAttribute("code", verifyCodeActual);
		request.getSession().setAttribute("shopStr", request.getParameter("shopStr"));
		Map<String, Object> modelMap=new HashMap<String, Object>();
		if(!CodeCheckUtil.checkCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "code is error");
			modelMap.put("url", "/o2o/shopadmin/goshop");
			return modelMap;
		}
		String shopstr=HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper=new ObjectMapper();
		Shop shop=new Shop();
		try {
			shop=mapper.readValue(shopstr, Shop.class);
			shop.setShopId(HttpServletRequestUtil.getLong(request, "ShopId"));
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg",e.getMessage());
			return modelMap;
		}
		//??????????????????????????????????????????????????????service????????????CommonsMultipartFile??????
		CommonsMultipartFile shopImg=null;
		CommonsMultipartResolver commonsMultipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
		if (commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest=(MultipartHttpServletRequest)request;
			shopImg=(CommonsMultipartFile)multipartHttpServletRequest.getFile("showImg");//??????????????????shopImg
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg","????????????????????????");
			return modelMap;
		}
		//????????????
		if (shop!=null&&shopImg!=null) {
			UserInfo info=new UserInfo();//???????????????????????????????????????????????????Session???????????????????????????
			info.setUserId(1);
			shop.setUserInfo(info);
			ImageHolder imageHolder=new ImageHolder(shopImg.getInputStream(), shopImg.getOriginalFilename());
			ShopExecution se=shopservice.modifyShop(shop,imageHolder);
			if (se.getState()==ShopStateEnum.SUCCESS.getState()) {
				modelMap.put("success", true);
				modelMap.put("url", "/o2o/shopadmin/goshop?ShopId="+se.getShop().getShopId());
			}else {
				modelMap.put("success", false);
				modelMap.put("url", "/o2o/shopadmin/goshop?ShopId="+se.getShop().getShopId());
				modelMap.put("errMsg",se.getStateInfo());
			}
			return modelMap;
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg","?????????????????????");
			return modelMap;
		}
	}
	
	@RequestMapping(value = "/registershop",method = {RequestMethod.POST})
	@ResponseBody
	private Map<String, Object> registerShop(HttpServletRequest request,@RequestParam("verifyCodeActual")String verifyCodeActual){
		//??????request?????????????????????,?????????????????????????????????\
		UserInfo Shopadmin=(UserInfo) request.getSession().getAttribute("Shopadmin");
		request.getSession().setAttribute("code", verifyCodeActual);
		request.getSession().setAttribute("shopStr", request.getParameter("shopStr"));
		Map<String, Object> modelMap=new HashMap<String, Object>();
		if(!CodeCheckUtil.checkCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "code is error");
			return modelMap;
		}
		String shopstr=HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper=new ObjectMapper();
		Shop shop=null;
		try {
			shop=mapper.readValue(shopstr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg",e.getMessage());
			return modelMap;
		}
		//??????????????????????????????????????????????????????service????????????CommonsMultipartFile??????
		CommonsMultipartFile shopImg=null;
		CommonsMultipartResolver commonsMultipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
		if (commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest=(MultipartHttpServletRequest)request;
			shopImg=(CommonsMultipartFile)multipartHttpServletRequest.getFile("showImg");//??????????????????shopImg
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg","????????????????????????");
			return modelMap;
		}
		//????????????
		if (shop!=null&&shopImg!=null) {
			shop.setUserInfo(Shopadmin);
			ShopExecution se=shopservice.addShop(shop, shopImg);
			if (se.getState()==ShopStateEnum.CHECK.getState()) {
				modelMap.put("success", true);
				modelMap.put("url", "/o2o/shopadmin/goshop?ShopId="+se.getShop().getShopId());
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg",se.getStateInfo());
			}
			return modelMap;
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg","?????????????????????");
			return modelMap;
		}
	}
}
