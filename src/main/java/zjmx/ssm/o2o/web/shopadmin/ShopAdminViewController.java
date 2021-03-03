package zjmx.ssm.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "shopadmin",method = {RequestMethod.GET})
public class ShopAdminViewController {
	@RequestMapping(value = "/shopoperation")
	public String shopOperation() {
		return "shop/shopoperation";
	}
	@RequestMapping(value = "/shoplist")
	public String shopList() {
		return "shop/shoplist";
	}
	@RequestMapping(value = "/goshop")
	public String goShop() {
		return "shop/goshop";
	}
	@RequestMapping(value = "/productManage")
	public String productManage() {
		return "product/productManage";
	}
	@RequestMapping(value = "/producttype")
	public String goProducttype() {
		return "product/producttype";
	}
	@RequestMapping(value = "/productoperation")
	public String productOperation() {
		return "shop/productoperation";
	}
}
