package zjmx.ssm.o2o.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import zjmx.ssm.o2o.BaseTest;
import zjmx.ssm.o2o.entity.ProductType;

public class productTypeDaoTest extends BaseTest{
	
	@Autowired
	private ProductTypeDao dao;
	@Test
	public void TestProductType() {
		List<ProductType> list=dao.getProductTypes(1L);
		System.out.println(list);
	}
	@Test
	@Ignore
	public void TestDeleteProductType() {
		boolean res=dao.deleteProductType(3L);
		System.out.println(res);
	}
	@Test
	@Ignore
	public void TestBatchProductType() {
		//1
		ProductType productType1=new ProductType();
		productType1.setShopId(1L);;
		productType1.setProductTypeName("星巴克");
		productType1.setPriority(6);
		productType1.setCreateTime(new Date());
		
		//2
		ProductType productType2=new ProductType();
		productType2.setShopId(1L);;
		productType2.setProductTypeName("德克士");
		productType2.setPriority(2);
		productType2.setCreateTime(new Date());
		List<ProductType> productTypes=new ArrayList<ProductType>();
		productTypes.add(productType1);
		productTypes.add(productType2);
		dao.batchInsertProductType(productTypes);
	}
}
