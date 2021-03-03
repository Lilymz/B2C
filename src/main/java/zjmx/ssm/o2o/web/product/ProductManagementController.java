package zjmx.ssm.o2o.web.product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.Get;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import zjmx.ssm.o2o.dto.ProductExecution;
import zjmx.ssm.o2o.dto.ProductTypeExecution;
import zjmx.ssm.o2o.entity.Product;
import zjmx.ssm.o2o.entity.ProductType;
import zjmx.ssm.o2o.entity.Shop;
import zjmx.ssm.o2o.entity.UserInfo;
import zjmx.ssm.o2o.enums.ProductEnum;
import zjmx.ssm.o2o.enums.ProductTypeEnum;
import zjmx.ssm.o2o.service.ProductService;
import zjmx.ssm.o2o.service.ProductTypeService;
import zjmx.ssm.o2o.utils.CodeCheckUtil;
import zjmx.ssm.o2o.utils.HttpServletRequestUtil;
import zjmx.ssm.o2o.utils.ImageHolder;

@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductTypeService productTypeService;
	// 支持上传的商品详情图的最大数量
	private static final int MAX_COUNT_IMAGE = 6;
	//商品删除
	@RequestMapping(value = "/deletProductByProductId",method = {RequestMethod.POST})
	@ResponseBody
	private Map<String, Object> deletProductByProductId(HttpServletRequest request,@RequestBody String productId){
		Map<String, Object> modelMap=new HashMap<String, Object>();
		//非空判断
		if(productId==null) {
			modelMap.put("success",false);
			modelMap.put("errMsg","传入的商品Id未成功获取");
			return modelMap;
		}
		try {
			//执行删除
			ProductExecution deleteProduct = productService.deleteProduct(Long.valueOf(productId.replaceAll("\"", "")));
			//判断是否删除
			if(deleteProduct.getState()==ProductEnum.PRODUCTDELETE_SUCCESS.getState()) {
				modelMap.put("success",true);
				modelMap.put("Msg","成功删除");
				modelMap.put("url","/o2o/shopadmin/productManage?ShopId="+((Shop)request.getSession().getAttribute("currentShop")).getShopId());
				return modelMap;
			}
			modelMap.put("success",false);
			modelMap.put("errMsg",deleteProduct.getStateInfo());
			return modelMap;
		} catch (Exception e) {
			modelMap.put("success",false);
			modelMap.put("errMsg",e.getMessage());
			return modelMap;
		}
		
	}
	//增减优先级
	@RequestMapping(value = "/updateProductPriorityByProductId",method = {RequestMethod.POST})
	@ResponseBody
	private Map<String, Object> updateProductPriorityByProductId(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String,Object>();
		//获取当前商品的id
		String productId=request.getParameter("productId");
		//获取当前商品前端优先级;
		String priority = request.getParameter("priority");
		if(productId==null||priority==null) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "传入的数据为空，请检查！");
			return modelMap;
		}
		ProductExecution queryProductById = productService.queryProductById(Long.valueOf(productId.trim()));
		Product productCondition=new Product();
		productCondition.setProductId(Integer.valueOf(productId.trim()));
		productCondition.setPriority(Integer.valueOf(priority.trim()));
		productCondition.setLastEditTime(new Date());
		//进行数据更新把是否成功的状态返回给前端
		try {
			int updateProduct = productService.updateProductEnableStatusByProductId(productCondition);
			if (updateProduct>0) {
				modelMap.put("success", true);
				modelMap.put("Msg","成功更新");
				return modelMap;
			}else {
				modelMap.put("success", true);
				modelMap.put("Msg","无数据更新失败");
				return modelMap;
			}
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
	}
	//商品状态值
	@RequestMapping(value = "/updateProductEnableStatusByProductId",method = {RequestMethod.POST})
	@ResponseBody
	private Map<String, Object> updateProductEnableStatusByProductId(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String,Object>();
		//获取当前商品的id
		String productId=request.getParameter("productId");
		//获取当前商品前端优先级;
		String enableStatus = request.getParameter("enableStatus");
		if(productId==null||enableStatus==null) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "传入的数据为空，请检查！");
			return modelMap;
		}
		ProductExecution queryProductById = productService.queryProductById(Long.valueOf(productId.trim()));
		Product productCondition=new Product();
		productCondition.setProductId(Integer.valueOf(productId.trim()));
		productCondition.setEnableStatus(Integer.valueOf(enableStatus.trim()));
		productCondition.setLastEditTime(new Date());
		//进行数据更新把是否成功的状态返回给前端
		try {
			int updateProduct = productService.updateProductEnableStatusByProductId(productCondition);
			if (updateProduct>0) {
				modelMap.put("success", true);
				modelMap.put("Msg","成功更新");
				return modelMap;
			}else {
				modelMap.put("success", true);
				modelMap.put("Msg","无数据更新失败");
				return modelMap;
			}
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
	}
	//店铺主页通过商品类别获取属于该主页的商品
	@RequestMapping(value = "/getProductByProductTypeId", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	private Map<String, Object> getProductByProductTypeId(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String,Object>();
		//从主页获取店铺商品类别锁定商品
		long productTypeId=HttpServletRequestUtil.getLong(request, "productTypeId");
		if(productTypeId!=0) {
			try {
				Product productConditon=new Product();
				ProductType productType=new ProductType();
				productType.setProductTypeId(productTypeId);
				productConditon.setProductType(productType);
				ProductExecution products = productService.getProducts(productConditon, 0, 100);
				if(products.getState()==ProductEnum.PRODUCTQUERY_SUCCESS.getState()) {
					modelMap.put("success", true);
					modelMap.put("products", products.getProducts());
					return modelMap;
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", products.getStateInfo());      
					return modelMap;
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg","未成功获取店铺，请核对！");
			return modelMap;
		}
	}
	@RequestMapping(value = "/getproductsByShopIdIndex", method = { RequestMethod.GET })
	@ResponseBody
	private Map<String, Object> getproductsByShopIdIndex(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String,Object>();
		//登录验证
		UserInfo userInfo=(UserInfo) request.getSession().getAttribute("userInfo");
		if(userInfo==null) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请用户先登录");
			modelMap.put("url", "/o2o/user/user_login");
			return modelMap;
		}
		//从主页获取店铺Id锁定商品
		long shopId=HttpServletRequestUtil.getLong(request, "shopId");
		if(shopId!=0) {
			try {
				Product productConditon=new Product();
				Shop shop=new Shop();
				shop.setShopId(shopId);
				productConditon.setShop(shop);
				productConditon.setEnableStatus(1);
				ProductExecution products = productService.getProducts(productConditon, 0, 100);
				if(products.getState()==ProductEnum.PRODUCTQUERY_SUCCESS.getState()) {
					modelMap.put("success", true);
					modelMap.put("products", products.getProducts());
					return modelMap;
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", products.getStateInfo());
					return modelMap;
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg","未成功获取店铺，请核对！");
			return modelMap;
		}
	}
	//店铺主页获取属于该主页的商品
	@RequestMapping(value = "/getproductsByShopId", method = { RequestMethod.GET })
	@ResponseBody
	private Map<String, Object> getProductsByShopId(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String,Object>();
		//登录验证
		UserInfo userInfo=(UserInfo) request.getSession().getAttribute("Shopadmin");
		if(userInfo==null) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请店家先登录");
			modelMap.put("url", "/o2o/shopadmin/shopAdminLogin");
			return modelMap;
		}
		//从主页获取店铺Id锁定商品
		long shopId=HttpServletRequestUtil.getLong(request, "shopId");
		if(shopId!=0) {
			try {
				Product productConditon=new Product();
				Shop shop=new Shop();
				shop.setShopId(shopId);
				productConditon.setShop(shop);
				ProductExecution products = productService.getProducts(productConditon, 0, 100);
				if(products.getState()==ProductEnum.PRODUCTQUERY_SUCCESS.getState()) {
					modelMap.put("success", true);
					modelMap.put("products", products.getProducts());
					return modelMap;
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", products.getStateInfo());
					return modelMap;
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg","未成功获取店铺，请核对！");
			return modelMap;
		}
	}
	//店铺主页获取的页面
		@RequestMapping(value = "/getproductTypesByshopId", method = { RequestMethod.GET })
		@ResponseBody
		private Map<String, Object> getproductTypesByshopId(HttpServletRequest request) {
			Map<String, Object> modelMap = new HashMap<String, Object>();
			long shopId=HttpServletRequestUtil.getLong(request, "shopId");
			if (shopId!=0) {
				try {
					ProductTypeExecution pe = new ProductTypeExecution();
					pe = productTypeService.getProductTypes(shopId);
					if (pe.getState() == ProductTypeEnum.ProductType_SUCCESS.getState()) {
						modelMap.put("success", true);
						modelMap.put("ProductTypeList", pe.getProductTypeslist());
					} else {
						modelMap.put("success", false);
						modelMap.put("errMsg", pe.getStateInfo());
					}
				} catch (Exception e) {
					modelMap.put("success", false);
					modelMap.put("errMsg", e.toString());
					return modelMap;
				}
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "请在一个店铺页面！无店铺信息");
				return modelMap;
			}
			return modelMap;
		}
	//店铺管理员编辑获取的页面
	@RequestMapping(value = "/getproductTypelist", method = { RequestMethod.GET })
	@ResponseBody
	private Map<String, Object> getproductTypelist(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Shop CurrentShop = (Shop) request.getSession().getAttribute("currentShop");
		if (CurrentShop != null) {
			try {
				ProductTypeExecution pe = new ProductTypeExecution();
				pe = productTypeService.getProductTypes(CurrentShop.getShopId());
				if (pe.getState() == ProductTypeEnum.ProductType_SUCCESS.getState()) {
					modelMap.put("success", true);
					modelMap.put("ProductTypeList", pe.getProductTypeslist());
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请在一个店铺页面！无店铺信息");
			return modelMap;
		}
		return modelMap;
	}
	// 未实现
	@RequestMapping(value = "/modifyproduct", method = { RequestMethod.POST })
	@ResponseBody
	private Map<String, Object> ModifyProduct(HttpServletRequest request,
			@RequestParam("verifyCodeActual") String verifyCodeActual) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 验证码校验
		String productStr1 = request.getParameter("productStr");
		request.getSession().setAttribute("productStr", productStr1);
		request.getSession().setAttribute("code", verifyCodeActual);
		if (!CodeCheckUtil.checkCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码错误");
			return modelMap;
		}
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		String productStr = HttpServletRequestUtil.getString(request, "productStr");
		MultipartHttpServletRequest multipartRequest = null;
		ImageHolder thumbnail = null;
		List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		try {
			// 若请求文件中有文件流,则取出相关的文件(包括缩略图和详情图)
			if (multipartResolver.isMultipart(request)) {
				multipartRequest = (MultipartHttpServletRequest) request;
				// 取出缩略图并构造ImageHolder对象
				CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
				thumbnail = new ImageHolder(thumbnailFile.getInputStream(), thumbnailFile.getOriginalFilename());
				// 取出详情图列表并List<ImageHolder>对象 ,最大支持6张图片;
				for (int i = 0; i < MAX_COUNT_IMAGE; i++) {
					CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartRequest
							.getFile("productImg" + i);
					if (productImgFile != null) {
						ImageHolder productImg = new ImageHolder(productImgFile.getInputStream(),
								productImgFile.getOriginalFilename());
						productImgList.add(productImg);
					} else {
						// 若取出第i个详情图片，流为空则终止循环;
						break;
					}
				}
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "上传图片不能为空");
				return modelMap;
			}
		} catch (RuntimeException | IOException e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		try {
			// 尝试获取前端传来的表单String流并将其转换成Product实体类
			product = mapper.readValue(productStr, Product.class);
		} catch (RuntimeException | JsonProcessingException e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			e.printStackTrace();
			return modelMap;
		}
		if (product != null && thumbnail != null && productImgList != null) {
			try {
				// 从session中获取当前店铺赋值给product，减少对前端数据的依赖
				Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
				Shop shop = new Shop();
				shop.setShopId(currentShop.getShopId());
				product.setShop(shop);
				// 执行添加操作
				ProductExecution pe = productService.updateProduct(product, thumbnail, productImgList);
				if (pe.getState() == ProductEnum.PRODUCTUPDATE_SUCCESS.getState()) {
					modelMap.put("success", true);
					modelMap.put("url","/o2o/shopadmin/productManage?ShopId="+((Shop)request.getSession().getAttribute("currentShop")).getShopId());
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		}
		return modelMap;
	}

	@RequestMapping(value = "/addproduct", method = { RequestMethod.POST })
	@ResponseBody
	private Map<String, Object> addProduct(HttpServletRequest request,
			@RequestParam("verifyCodeActual") String verifyCodeActual) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 验证码校验
		String productStr1 = request.getParameter("productStr");
		request.getSession().setAttribute("productStr", productStr1);
		request.getSession().setAttribute("code", verifyCodeActual);

		// 1.校验验证码
		if (!CodeCheckUtil.checkCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码错误");
			return modelMap;
		}

		// 2.接受前端参数的变量初始化，包括商品，缩略图，详情图列表实体类
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		String productStr = HttpServletRequestUtil.getString(request, "productStr");
		MultipartHttpServletRequest multipartRequest = null;
		ImageHolder thumbnail = null;
		List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		try {
			// 若请求文件中有文件流,则取出相关的文件(包括缩略图和详情图)
			if (multipartResolver.isMultipart(request)) {
				multipartRequest = (MultipartHttpServletRequest) request;
				// 取出缩略图并构造ImageHolder对象
				CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
				thumbnail = new ImageHolder(thumbnailFile.getInputStream(), thumbnailFile.getOriginalFilename());
				// 取出详情图列表并List<ImageHolder>对象 ,最大支持6张图片;
				for (int i = 0; i < MAX_COUNT_IMAGE; i++) {
					CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartRequest
							.getFile("productImg" + i);
					if (productImgFile != null) {
						ImageHolder productImg = new ImageHolder(productImgFile.getInputStream(),
								productImgFile.getOriginalFilename());
						productImgList.add(productImg);
					} else {
						// 若取出第i个详情图片，流为空则终止循环;
						break;
					}
				}
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "上传图片不能为空");
				return modelMap;
			}
		} catch (RuntimeException | IOException e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		try {
			// 尝试获取前端传来的表单String流并将其转换成Product实体类
			product = mapper.readValue(productStr, Product.class);
		} catch (RuntimeException | JsonProcessingException e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			e.printStackTrace();
			return modelMap;
		}
		// 若Product信息，缩略图，详情图非空，则开始进行商品添加操作
		if (product != null && thumbnail != null && productImgList != null) {
			try {
				// 从session中获取当前店铺赋值给product，减少对前端数据的依赖
				Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
				Shop shop = new Shop();
				shop.setShopId(currentShop.getShopId());
				product.setShop(shop);
				//商品添加默认商品状态都为0,未上架
				product.setEnableStatus(0);
				// 执行添加操作
				ProductExecution pe = productService.insertProduct(product, thumbnail, productImgList);
				if (pe.getState() == ProductEnum.PRODUCTINSERT_SUCCESS.getState()) {
					modelMap.put("success", true);
					modelMap.put("url","/o2o/shopadmin/productManage?ShopId="+((Shop)request.getSession().getAttribute("currentShop")).getShopId());
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		}
		return modelMap;
	}
}
