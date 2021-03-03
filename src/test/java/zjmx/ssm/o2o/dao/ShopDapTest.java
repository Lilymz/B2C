package zjmx.ssm.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import zjmx.ssm.o2o.BaseTest;
import zjmx.ssm.o2o.entity.Area;
import zjmx.ssm.o2o.entity.Shop;
import zjmx.ssm.o2o.entity.ShopType;
import zjmx.ssm.o2o.entity.UserInfo;

/**测试结束，结果满意
 * @author jj
 *
 */
public class ShopDapTest extends BaseTest{
	
	@Autowired
	private ShopDao dao;
	@Test
	public void queryShopList() {
		
//		Shop ShopCondition=new Shop();
//		UserInfo userInfo=new UserInfo();
//		userInfo.setUserId(1);
//		ShopCondition.setUserInfo(userInfo);
//		List<Shop> list=dao.queryShopList(ShopCondition, 0, 5);
//		int count=dao.queryShopCount(ShopCondition);
//		System.out.println("店铺数："+list);
//		System.out.println("店铺个数："+count);
		
		//模糊查询测试
		Shop ShopCondition1=new Shop();
		ShopCondition1.setShopName("古茗");
		List<Shop> list1=dao.queryShopList(ShopCondition1, 0, 5);
		System.out.println("店铺数："+list1);
	}
	@Test
	@Ignore
	public void queryByShopId() {
		System.out.println(dao.queryByShopId(1L).getShopName());
	}
	@Test
	@Ignore
	public void insertTest() {
		Shop shop=new Shop();
		Area area=new Area();
		UserInfo userInfo=new UserInfo();
		ShopType shopType=new ShopType();
		area.setAreaId(1);
		userInfo.setUserId(1);
		shopType.setShoptypeId(1L);
		shop.setArea(area);
		shop.setUserInfo(userInfo);
		shop.setShopType(shopType);
		shop.setShopName("测试店铺添加的店铺名");
		shop.setShopDesc("测试店铺添加的店铺描述");
		shop.setShopAddr("测试店铺添加的店铺地址");
		shop.setPhone("123456");
		shop.setShopImg("test");
		shop.setPriority(2);
		shop.setCreateTime(new Date());
		shop.setEnableStatus(2);
		shop.setAdvice("无建议!");
		int EffectRow=dao.insertShop(shop);
		assertEquals(1, EffectRow);
	}
	@Test
	@Ignore
	public void updateTest() {
		Shop shop=new Shop();
		shop.setShopId(1L);
		shop.setShopName("测试店铺更改的店铺名");
		shop.setShopDesc("测试店铺更改的店铺描述");
		shop.setShopAddr("测试店铺添加的店铺地址");
		shop.setPhone("1234567");
		shop.setShopImg("test1");
		shop.setPriority(1);
		shop.setLastEditTime(new Date());
		shop.setEnableStatus(1);
		shop.setAdvice("建议!");
		int EffectRow=dao.updateShop(shop);
		assertEquals(1, EffectRow);
	}
}
