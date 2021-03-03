package zjmx.ssm.o2o.serviceimpl;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zjmx.ssm.o2o.BaseTest;
import zjmx.ssm.o2o.entity.Area;
import zjmx.ssm.o2o.service.AreaService;

/**测试结束，结果满意
 * @author jj
 *
 */
public class AreaServiceImplTest extends BaseTest{
	@Autowired
	private AreaService service;
	@Test
	@Ignore
	public void ServiceTest() {
		List<Area> list=service.getAreas();
		assertEquals((Integer)2,list.get(0).getPriority());
	}
}
