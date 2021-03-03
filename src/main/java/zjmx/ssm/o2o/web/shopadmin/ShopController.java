package zjmx.ssm.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "shop",method = {RequestMethod.GET})
public class ShopController {
	
	@RequestMapping("product")
	public String goProduct() {
		return "product/product";
	}
	@RequestMapping("shopindex")
	public String goShopIndex() {
		return "shop/shopindex";
	}
}
