package zjmx.ssm.o2o.web.frontPage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import zjmx.ssm.o2o.entity.Swipper;
import zjmx.ssm.o2o.service.SwipperService;

@Controller
@RequestMapping(value = "swipper")
public class SwipperController {
	@Autowired
	private SwipperService swipperService;
	@RequestMapping(value = "/getSwippers",method = {RequestMethod.GET})
	@ResponseBody
	private Map<String, Object> getSwippers(){
		Map<String, Object> modelMap=new HashMap<String, Object>();
		//取出状态值为1且最新的Swippers,有且只能取三个
		Swipper swipper=new Swipper();
		swipper.setEnableStatus(1);
		try {
			List<Swipper> swippers=swipperService.querySwippers(swipper);
			if (swippers!=null) {
				modelMap.put("success", true);
				modelMap.put("swippers", swippers);
			}
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("Msg", "原因："+e.getMessage());
		}
		return modelMap;
	}
}
