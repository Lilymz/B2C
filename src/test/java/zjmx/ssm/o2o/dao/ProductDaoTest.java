package zjmx.ssm.o2o.dao;

import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import zjmx.ssm.o2o.BaseTest;
import zjmx.ssm.o2o.entity.Product;
import zjmx.ssm.o2o.entity.ProductType;
import zjmx.ssm.o2o.entity.Shop;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)//UT按着方法名顺序执行
public class ProductDaoTest extends BaseTest{
	
	@Autowired
	private ProductDao dao;
	
	@Test
	@Ignore
	public void TestAinsertProduct() {
		Product productConditon=new Product();
		productConditon.setProductName("蓝莓1");
		productConditon.setProductDesc("新鲜橙汁！从山里农民亲自采摘，未有农药，果汁浓郁甜，700mL");
		productConditon.setImgAddr("xxxxxx");
		productConditon.setNormalPrice("8");
		productConditon.setPromotionPrice("5");
		productConditon.setPriority(1);
		productConditon.setCreateTime(new Date());
		productConditon.setLastEditTime(new Date());
		productConditon.setEnableStatus(1);
		Shop shop=new Shop();
		shop.setShopId(2L);
		productConditon.setShop(shop);
		ProductType productType=new ProductType();
		productType.setProductTypeId(11L);
		productConditon.setProductType(productType);
		int res=dao.insertProduct(productConditon);
		System.out.println(productConditon.getProductId());
	}
	
	@Test
	@Ignore
	public void TestBQueryProductList() {
		Product productConditon=new Product();
		Shop shop=new Shop();
		shop.setShopId(1L);
		productConditon.setShop(shop);
		ProductType productType=new ProductType();
		productType.setProductTypeId(1L);
		productConditon.setProductType(productType);
		List<Product> list=dao.queryProductList(productConditon, 0, 5);
		System.out.println(list);
	}
	
	@Test
	@Ignore
	public void TestCdeleteProduct() {
		System.out.println(dao.deleteProduct(3L));
	}
	
	@Test
	@Ignore
	public void TestDqueryProductById() {
		System.out.println(dao.queryProductById(1L));
	}
	
	@Test
	private void TestEUpdateProduct() {
		Product product=new Product();
		product.setProductId(10L);
		ProductType productType=new ProductType();
		productType.setProductTypeId(2L);
		product.setProductName("摩卡*星冰乐");
		product.setProductType(productType);
		product.setPriority(15);
		product.setNormalPrice("50");
		product.setPromotionPrice("34");
		product.setImgAddr("D:\\o2o\\image\\ProductImage\\productId-0\\20200811154635920291071摩卡星冰乐main.png");
		product.setProductDesc("摩卡酱与星冰乐烘焙咖啡、在牛奶加冰块中绽放快乐，伴着雪白的稀奶油，让你每个瞬间都充满活力.-2020.8.11");
		System.out.println(dao.updateProduct(product));
	}
}
