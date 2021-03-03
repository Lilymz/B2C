package zjmx.ssm.o2o.serviceimpl;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import zjmx.ssm.o2o.BaseTest;
import zjmx.ssm.o2o.dto.ShopExecution;
import zjmx.ssm.o2o.entity.Shop;
import zjmx.ssm.o2o.entity.ShopType;
import zjmx.ssm.o2o.service.ShopService;
import zjmx.ssm.o2o.service.ShopTypeService;

public class ShopTypeServiceTest extends BaseTest{
	
	@Autowired
	private ShopTypeService shopTypeService;
	@Autowired
	private ShopService shopService;
	@Test
	@Ignore
	public void TestShoptypeService() {
		ShopType shopType=new ShopType();
		System.out.println(shopTypeService.getShoptypelist(shopType));
	}
	@Test
//	@Ignore
	public void TestShopByShopTypeName() {
		long shopTypeId=1L;
		ShopType queryShopTypeByNameService = shopTypeService.queryShopTypeByNameService(shopTypeId);
		Shop shopCondition=new Shop();
		shopCondition.setShopType(queryShopTypeByNameService);
		ShopExecution shopList = shopService.getShopList(shopCondition, 0, 100);
		System.out.println(shopList.getCount()+"---------- "+shopList.getShoplist());
	}
}
