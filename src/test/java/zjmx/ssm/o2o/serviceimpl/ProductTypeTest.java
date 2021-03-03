package zjmx.ssm.o2o.serviceimpl;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import zjmx.ssm.o2o.BaseTest;
import zjmx.ssm.o2o.dto.ProductTypeExecution;
import zjmx.ssm.o2o.entity.ProductType;
import zjmx.ssm.o2o.service.ProductTypeService;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductTypeTest extends BaseTest{
	
	@Autowired
	ProductTypeService service;
	@Test
	public void getProductTypesTest() {
		ProductTypeExecution pe=service.getProductTypes(1L);
		System.out.println(pe.getStateInfo());
		List<ProductType> list=pe.getProductTypeslist();
		System.out.println("第一个商品类型："+list.get(0).getProductTypeName()+"                第二个商品类型："+list.get(1).getProductTypeName());
	}
	@Test
	public void deleteProductTypesTest() {
		ProductTypeExecution pe=service.deleteProductType(4L);
		System.out.println(pe.getStateInfo());
		
	}
}
