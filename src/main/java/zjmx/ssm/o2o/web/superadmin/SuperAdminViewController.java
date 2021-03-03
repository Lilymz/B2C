package zjmx.ssm.o2o.web.superadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("superadmin")
public class SuperAdminViewController {
	@RequestMapping("/index")
	private String index() {return "OrderManagement/index";}
	@RequestMapping("/userManagement")
	private String userManagement() {return "OrderManagement/userManagement";}
	@RequestMapping("/shopManagement")
	private String shopManagement() {return "OrderManagement/shopManagement";}
	@RequestMapping("/shopRegisterManagement")
	private String shopRegisterManagement() {return "OrderManagement/shopRegisterManagement";}
}
