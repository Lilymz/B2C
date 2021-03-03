package zjmx.ssm.o2o.serviceimpl;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import zjmx.ssm.o2o.BaseTest;
import zjmx.ssm.o2o.entity.Swipper;
import zjmx.ssm.o2o.service.SwipperService;

public class SwipperServiceImplTest extends BaseTest{
	@Autowired
	private SwipperService swipperservice;
	@Test
	public void swipperQueryTest() throws Exception {
		Swipper swipper=new Swipper();
		swipper.setEnableStatus(1);
		List<Swipper> swippers=swipperservice.querySwippers(swipper);
		for (Swipper swipper2 : swippers) {
			System.out.println(swipper2);
		}
	}
}
