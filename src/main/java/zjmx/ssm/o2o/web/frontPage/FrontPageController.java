package zjmx.ssm.o2o.web.frontPage;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Page")
public class FrontPageController {
	
	@RequestMapping(value = "/index")
	private String WellcomeIndexPage() {
		return "frontPage/pageindex";
	}
	@RequestMapping(value = "/shoptypeShop")
	private String shoptypeShop() {
		return "frontPage/shoptypeShop";
	}
	@RequestMapping(value = "/shop")
	private String showShop() {
		return "shop/shop";
	}
	@RequestMapping(value = "/o2ofaq")
	private String o2ofaq() {
		return "user/faq";
	}
	@RequestMapping(value = "/o2otestimonial")
	private String o2otestimonial() {
		return "user/testimonial";
	}
	@RequestMapping(value = "/register")
	private String o2oregister() {
		return "user/register";
	}
	@RequestMapping(value = "/contact")
	private String o2ocontact() {
		return "user/contact";
	}
	@RequestMapping(value = "/shopcart")
	private String showShopCart() {
		return "frontPage/shopcart";
	}
}
