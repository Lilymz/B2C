package zjmx.ssm.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import zjmx.ssm.o2o.BaseTest;
import zjmx.ssm.o2o.entity.ProductImage;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductImageDaoTest extends BaseTest{

	@Autowired
	private ProductImgDao dao;
	
	@Test
	@Ignore
	public void TestAProductImageBatchInsert() {
		ProductImage img1=new ProductImage();
		img1.setImgAddr("批处理之批量添加1图片地址");
		img1.setImgDesc("橙汁的展示图片，美味1");
		img1.setPriority(5);
		img1.setCreateTime(new Date());
		img1.setProductId(1L);
		ProductImage img2=new ProductImage();
		img2.setImgAddr("批处理之批量添加2图片地址");
		img2.setImgDesc("橙汁的展示图片，美味2");
		img2.setPriority(5);
		img2.setCreateTime(new Date());
		img2.setProductId(2L);
		List<ProductImage> productImageList=new ArrayList<ProductImage>();
		productImageList.add(img1);
		productImageList.add(img2);
		int res=dao.batchInsertProductImg(productImageList);
		assertEquals(2, res);
	}
	@Test
	public void TestBProductImageQueryByProductId() {
		List<ProductImage> productImageList=dao.queryProductImageList(2L);
		System.out.println(productImageList);
		
	}
	@Test
	@Ignore
	public void TestCDeleteProductImageByProductId() {
		int res=dao.deleteProductImgByProductId(5L);
		System.out.println(res);
	}
}
