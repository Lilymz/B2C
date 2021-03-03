package zjmx.ssm.o2o.serviceimpl;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import zjmx.ssm.o2o.BaseTest;
import zjmx.ssm.o2o.dao.ShopDao;
import zjmx.ssm.o2o.dto.ShopExecution;
import zjmx.ssm.o2o.entity.Area;
import zjmx.ssm.o2o.entity.Shop;
import zjmx.ssm.o2o.entity.ShopType;
import zjmx.ssm.o2o.entity.UserInfo;
import zjmx.ssm.o2o.enums.ShopStateEnum;
import zjmx.ssm.o2o.service.ShopService;
import zjmx.ssm.o2o.utils.ImageHolder;


/**测试结束，结果满意
 * @author jj
 *
 */
public class AddShopServiceImplTest extends BaseTest{
	
	@Autowired
	private ShopService serviceImpl;
	@Test
	public void testShopList() {
		Shop ShopCondition=new Shop();
		UserInfo userInfo=new UserInfo();
		userInfo.setUserId(1);
		ShopCondition.setUserInfo(userInfo);
		ShopExecution se=serviceImpl.getShopList(ShopCondition, 3, 2);
//		int count=serviceImpl.queryShopCount(ShopCondition);
		System.out.println("店铺数："+se.getShoplist().size());
		System.out.println("店铺个数："+se.getCount());
	}
	@Test
	@Ignore
	public void testupdateShop() throws FileNotFoundException {
		Shop shop=serviceImpl.getByShopId(1);
		shop.setShopName("修改后的名称1");
		File file=new File("C:/Users/jj/Pictures/Emilia.jpg");
		InputStream is=new FileInputStream(file);
		ImageHolder imageHolder=new ImageHolder(is, "Emilia.jpg");
		ShopExecution execution=serviceImpl.modifyShop(shop, imageHolder);
	}
	@Test
	@Ignore
	public void addTest() {
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
		shop.setPriority(2);
		shop.setCreateTime(new Date());
		shop.setEnableStatus(ShopStateEnum.CHECK.getState());
		shop.setAdvice("无建议!");
		CommonsMultipartFile file = null;//这里因为不能从前端传数据过来测试，已经更改过参数测试一下方法已成功
		ShopExecution se=serviceImpl.addShop(shop, file);
		assertEquals(ShopStateEnum.CHECK.getState(), se.getState());
	}
}
