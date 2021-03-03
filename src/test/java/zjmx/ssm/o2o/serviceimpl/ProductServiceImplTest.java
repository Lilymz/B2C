package zjmx.ssm.o2o.serviceimpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import zjmx.ssm.o2o.BaseTest;
import zjmx.ssm.o2o.dao.ProductImgDao;
import zjmx.ssm.o2o.dto.ProductExecution;
import zjmx.ssm.o2o.entity.Product;
import zjmx.ssm.o2o.entity.ProductType;
import zjmx.ssm.o2o.entity.Shop;
import zjmx.ssm.o2o.entity.ShopType;
import zjmx.ssm.o2o.service.ProductService;
import zjmx.ssm.o2o.utils.ImageHolder;

public class ProductServiceImplTest extends BaseTest{

	@Autowired
	private ProductService service;
	@Test
	public void TestgetProducts() {
		Product shopCondition=new Product();
		Shop sh1=new Shop();
		sh1.setShopId(1L);
		shopCondition.setShop(sh1);
		ProductExecution products = service.getProducts(shopCondition, 0, 100);
		System.out.println(products.getProducts());
		
	}
	@Test
	@Ignore
	public void TestAGetProducts() throws FileNotFoundException {

		Shop shop=new Shop();
		shop.setShopId(1L);
		ShopType shopType=new ShopType();
		shopType.setShoptypeId(1L);
		Product product=new Product();
		product.setProductName("蓝莓");
		product.setProductDesc("鲜味解渴");
		product.setNormalPrice("8");
		product.setPromotionPrice("6");
		product.setShop(shop);
		product.setPriority(20);
		File file=new File("D:\\IDEA_鬼刀.jpg");
		InputStream is=new FileInputStream(file);
		ImageHolder ih=new ImageHolder(is, file.getName());
		File file1=new File("C:\\Users\\jj\\Pictures\\1920x1024.jpg");
		InputStream is1=new FileInputStream(file);
		File file2=new File("C:\\Users\\jj\\Pictures\\Emilia.jpg");
		InputStream is2=new FileInputStream(file);
		List<ImageHolder> list=new ArrayList<ImageHolder>();
		ImageHolder ih1=new ImageHolder(is1, file1.getName());
		ImageHolder ih2=new ImageHolder(is2, file2.getName());
		list.add(ih1);
		list.add(ih2);
		ProductExecution pe=service.insertProduct(product, ih, list);
		System.out.println(product.getProductId());
		System.out.println(pe.getStateInfo());
	}
	
	@Test
	@Ignore
	public void TestBModifyProduct() throws FileNotFoundException {
		Shop shop=new Shop();
		shop.setShopId(1L);
		ProductType productType=new ProductType();
		productType.setProductTypeId(2L);
		Product product=new Product();
		product.setProductId(9L);
		product.setProductName("摩卡可可&&碎片星冰乐");
		product.setProductDesc("摩卡酱和咖啡在冰块的碰撞下魅力四射，可可碎片带来不同的口感，顶部覆以稀奶油与摩卡酱，让浓郁变得更加有趣。(可可碎片为代可可脂巧克力)。2020.8.11");
		product.setNormalPrice("50");
		product.setPromotionPrice("32");
		product.setShop(shop);
		product.setProductType(productType);
		product.setPriority(8);
		File file=new File("D:\\IDEA_鬼刀.jpg");
		InputStream is=new FileInputStream(file);
		ImageHolder ih=new ImageHolder(is, file.getName());
		File file1=new File("C:\\Users\\jj\\Pictures\\1920x1024.jpg");
		InputStream is1=new FileInputStream(file);
		File file2=new File("C:\\Users\\jj\\Pictures\\Emilia.jpg");
		InputStream is2=new FileInputStream(file);
		List<ImageHolder> list=new ArrayList<ImageHolder>();
		ImageHolder ih1=new ImageHolder(is1, file1.getName());
		ImageHolder ih2=new ImageHolder(is2, file2.getName());
		list.add(ih1);
		list.add(ih2);
		ProductExecution pes=service.updateProduct(product, ih, list);
		System.out.println(pes.getStateInfo());
	}
}
