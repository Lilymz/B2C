package zjmx.ssm.o2o.dao;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import zjmx.ssm.o2o.BaseTest;
import zjmx.ssm.o2o.entity.ShopCart;
import zjmx.ssm.o2o.entity.UserInfo;

public class ShopcartDaoTest extends BaseTest{
	@Autowired
	private ShopCartDao dao;
	@Test
	public void shopCartTestQuery() {
		ShopCart shopCartConditon=new ShopCart();
		UserInfo userInfo=new UserInfo();
		userInfo.setUserId(1);
		shopCartConditon.setUserfo(userInfo);
		System.out.println(dao.queryShopCarts(shopCartConditon));
	}
	@Test
	@Ignore
	public void shopCartTestInsert() {
		ShopCart shopCartConditon=new ShopCart();
		shopCartConditon.setShopcartProductImg("\\image\\ProductImage\\productId-21\\main.png");
		shopCartConditon.setShopcartProductDesc("加芋圆，五分甜,不加料，五分甜。奶盖易沉淀，建议分装哦~");
		shopCartConditon.setShopcartProductPromotion(15);
		shopCartConditon.setShopcartShopId(1L);
		UserInfo userInfo=new UserInfo();
		userInfo.setUserId(1);
		shopCartConditon.setUserfo(userInfo);
		dao.insertShopCart(shopCartConditon);
	}
	@Test
	@Ignore
	public void shopCartTestDelete() {
		ShopCart shopCart=new ShopCart();
		shopCart.setShopcartProductImg("\\image\\ProductImage\\productId-21\\main.png");
		dao.deleteShopCart(shopCart);
	}
}
