package zjmx.ssm.o2o.dao;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import zjmx.ssm.o2o.BaseTest;
import zjmx.ssm.o2o.entity.ShopType;

public class ShopTypeDaoTest extends BaseTest{
	@Autowired
	private ShopTypeDao dao;
	@Test
	@Ignore
	public void testQueryShoptype() {
		ShopType ShopTypeCondition=new ShopType();
		ShopTypeCondition.setParentId(1);
		System.out.println(ShopTypeCondition);
		List<ShopType> list=dao.queryShopType(ShopTypeCondition);
		ShopTypeCondition.setParentId(0);
		List<ShopType> list1=dao.queryShopType(ShopTypeCondition);
		System.out.println(list);
		System.out.println(list1);
	}
	@Test
	public void TestShopTypeByShopTypeName() {
		long shopTypeId=1L;
		System.out.println(dao.queryShopTypeByShopTypeName(shopTypeId));
	}
}
