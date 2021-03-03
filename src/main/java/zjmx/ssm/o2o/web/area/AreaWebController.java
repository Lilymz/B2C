package zjmx.ssm.o2o.web.area;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import zjmx.ssm.o2o.entity.Area;
import zjmx.ssm.o2o.service.AreaService;

@Controller
@RequestMapping("/Area")
public class AreaWebController {
	@Autowired
	private AreaService service;
	@RequestMapping("/list")
	@ResponseBody
	public Map<String, Object> areaTest(HttpServletRequest request){
		System.out.println(request.getContextPath());
		Map<String, Object> map=new HashMap<String, Object>();
		List<Area> list=service.getAreas();
		map.put("row", list.size());
		map.put("total", list);
		return map;
	}
}
