package zjmx.ssm.o2o.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import zjmx.ssm.o2o.BaseTest;
import zjmx.ssm.o2o.entity.Swipper;

public class SwipperDaoTest extends BaseTest{
	@Autowired
	private SwipperDao dao;
	
	@Test
	public void TestAQuerySwipper() {
		List<Swipper> list=dao.querySwippers(new Swipper());
		System.out.println(list);
	}
	@Test
	@Ignore
	public void TestBbatchInsertSwippers() {
		Swipper swipper1=new Swipper();
		swipper1.setSwipperName("星冰乐");
		swipper1.setSwipperLink("/o2o/shop/showproduct?productId=xx");
		swipper1.setSwipperImg("20200811185727775355560Emilia.jpg");
		swipper1.setPriority(1);
		swipper1.setEnableStatus(1);
		swipper1.setCreateTime(new Date());
		swipper1.setLastEditTime(new Date());
		Swipper swipper2=new Swipper();
		swipper2.setSwipperName("摩卡可星冰乐");
		swipper2.setSwipperLink("/o2o/shop/showproduct?productId=xx");
		swipper2.setSwipperImg("\\image\\ProductImage\\productId-12\\20200811185727775355560Emilia.jpg");
		swipper2.setPriority(7);
		swipper2.setEnableStatus(1);
		swipper2.setCreateTime(new Date());
		swipper2.setLastEditTime(new Date());
		Swipper swipper3=new Swipper();
		swipper3.setSwipperName("摩卡可可碎片星冰乐");
		swipper3.setSwipperLink("/o2o/shop/showproduct?productId=xx");
		swipper3.setSwipperImg("D:\\o2o\\image\\ProductImage");
		swipper3.setPriority(9);
		swipper3.setEnableStatus(1);
		swipper3.setCreateTime(new Date());
		swipper3.setLastEditTime(new Date());
		List<Swipper> swippers=new ArrayList<Swipper>();
		swippers.add(swipper1);
		swippers.add(swipper2);
		swippers.add(swipper3);
		System.out.println(dao.batchInsertSwippers(swippers));
	}
}
