package zjmx.ssm.o2o.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import zjmx.ssm.o2o.BaseTest;
import zjmx.ssm.o2o.entity.Order;
import zjmx.ssm.o2o.entity.ShippingAddr;
import zjmx.ssm.o2o.entity.Shop;
import zjmx.ssm.o2o.entity.UserInfo;

public class OrderDaoTest extends BaseTest{

	@Autowired
	private OrderDao dao;
	@Test
	public void TestQuery() {
		Order order=new Order();
		order.setOrderNumber("0201208474");
		System.out.println("结果为："+dao.queryOrderByShopId(order));
	}
	@Test
	public void TestUpdate() {
		Order order=new Order();
//		order.setOrderId(1);
		order.setOrderNumber("2020120844482942");
		order.setOrderProductName("芝士鲜橙");
		order.setOrderMethod(1);
		order.setDealTime(new Date());
		order.setOrderEnableStatus(1);
		order.setOrderPrice("9");
		System.out.println("结果为："+dao.updateOrderByIf(order));
	}
	@Test
	public void TestInsert() {
		Order order=new Order();
		UserInfo userInfo=new UserInfo();
		userInfo.setUserId(1);
		Shop shop=new Shop();
		shop.setShopId(1L);
		ShippingAddr shippingAddr=new ShippingAddr();
		shippingAddr.setGoodsId(2);
		order.setOrderNumber("2020120847482829");
		order.setOrderProductName("珍芋奶绿");
		order.setOrderMethod(0);
		order.setCreateTime(new Date());
		order.setDealTime(null);
		order.setOrderEnableStatus(0);
		order.setOrderPrice("10");
		order.setUserInfo(userInfo);
		order.setShop(shop);
		order.setShippingAddr(shippingAddr);
		System.out.println(dao.insertOrder(order));
	}
	@Test
	public void TestBatchInsert() {
		Order order=new Order();
		Order order1=new Order();
		UserInfo userInfo=new UserInfo();
		userInfo.setUserId(1);
		Shop shop=new Shop();
		shop.setShopId(1L);
		ShippingAddr shippingAddr=new ShippingAddr();
		shippingAddr.setGoodsId(2);
		order.setOrderNumber("2020120847482829xx");
		order.setOrderProductName("珍芋奶绿xxx");
		order.setOrderMethod(0);
		order.setCreateTime(new Date());
		order.setDealTime(null);
		order.setOrderEnableStatus(0);
		order.setOrderPrice("10");
		order.setUserInfo(userInfo);
		order.setShop(shop);
		order.setShippingAddr(shippingAddr);
		
		order1.setOrderNumber("112020120847423829");
		order1.setOrderProductName("珍芋奶绿大杯");
		order1.setOrderMethod(1);
		order1.setCreateTime(new Date());
		order1.setDealTime(null);
		order1.setOrderEnableStatus(0);
		order1.setOrderPrice("26");
		order1.setUserInfo(userInfo);
		order1.setShop(shop);
		order1.setShippingAddr(shippingAddr);
		List<Order> list=new ArrayList<Order>();
		list.add(order);
		list.add(order1);
		System.out.println(dao.batchInsertOrder(list));
	}
}
