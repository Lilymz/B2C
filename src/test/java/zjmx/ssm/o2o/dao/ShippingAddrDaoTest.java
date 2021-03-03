package zjmx.ssm.o2o.dao;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import zjmx.ssm.o2o.BaseTest;
import zjmx.ssm.o2o.entity.ShippingAddr;
import zjmx.ssm.o2o.entity.UserInfo;

public class ShippingAddrDaoTest extends BaseTest{
	@Autowired
	private ShippingAddrDao dao;
	@Test
	public void TestShippingAddrQuery() {
		UserInfo userInfo=new UserInfo();
		userInfo.setUserId(1);
		ShippingAddr shippingAddr=new ShippingAddr();
		shippingAddr.setUserInfo(userInfo);
		System.out.println("查询结果为："+dao.queryShippingAddrByIf(shippingAddr));
	}
	@Test
	public void TestShippingAddrUpdate() {
		UserInfo userInfo=new UserInfo();
		userInfo.setUserId(1);
		ShippingAddr shippingAddr=new ShippingAddr();
		shippingAddr.setUserInfo(userInfo);
		shippingAddr.setGoodsName("梅欣");
		shippingAddr.setGoodsPhone("181178930201");
		System.out.println("查询结果为："+dao.updateShippingAddrByIf(shippingAddr));
	}
	@Test
	public void TestShippingAddrDelete() {
		System.out.println("查询结果为："+dao.deleteShippingAddrBygoodsId(1));
	}
	@Test
	public void TestShippingAddrInsert() {
		UserInfo userInfo=new UserInfo();
		userInfo.setUserId(1);
		ShippingAddr shippingAddr=new ShippingAddr();
		shippingAddr.setUserInfo(userInfo);
		shippingAddr.setGoodsName("张杰");
		shippingAddr.setGoodsPhone("19429592419");
		shippingAddr.setGoodsAddr("福建省厦门市集美区集岑路1号");
		System.out.println("查询结果为："+dao.insertShippingAddr(shippingAddr));
	}
}
