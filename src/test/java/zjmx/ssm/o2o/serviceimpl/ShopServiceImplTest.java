package zjmx.ssm.o2o.serviceimpl;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import zjmx.ssm.o2o.BaseTest;
import zjmx.ssm.o2o.dao.ShopDao;
import zjmx.ssm.o2o.dto.ShopExecution;
import zjmx.ssm.o2o.entity.Shop;
import zjmx.ssm.o2o.service.ShopService;

public class ShopServiceImplTest extends BaseTest{
	@Autowired
	private ShopService shopService; 
	@Test
	public void TestQuery() {
		//模糊查询测试
		Shop ShopCondition1=new Shop();
		ShopCondition1.setShopName("古茗");
		ShopExecution shopList = shopService.getShopList(ShopCondition1, 0, 5);
		System.out.println("店铺数："+shopList.getShoplist());
	}
}
