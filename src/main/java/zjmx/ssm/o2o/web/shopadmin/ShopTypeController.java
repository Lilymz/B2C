package zjmx.ssm.o2o.web.shopadmin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import zjmx.ssm.o2o.entity.ShopType;
import zjmx.ssm.o2o.service.ShopTypeService;

@Controller
@RequestMapping(value = "shopType")
public class ShopTypeController {
	@Autowired
	private ShopTypeService shopTypeService;
	@RequestMapping(value = "/getShopTypeParent",method = {RequestMethod.GET})
	@ResponseBody
	private Map<String, Object> getShopTypeParent(){
		Map<String, Object> modelMap=new HashMap<String, Object>();
		//设置默认的父级元素为0
		ShopType shopTypeCondition=new ShopType();
		shopTypeCondition.setParentId(0);
		//非空判断
		if(shopTypeCondition!=null&&shopTypeCondition.getParentId()!=null) {
			List<ShopType> shopTypes=shopTypeService.getShoptypelist(shopTypeCondition);
			if(shopTypes!=null) {
				modelMap.put("success", true);
				modelMap.put("shopTypes", shopTypes);
				return modelMap;
			}
			modelMap.put("success", false);
			modelMap.put("Msg", "无父级商品类别");
			return modelMap;
		}
		modelMap.put("success", false);
		modelMap.put("Msg", "附近查询条件为空或者父级ID不为0");
		return modelMap;
	}
}
