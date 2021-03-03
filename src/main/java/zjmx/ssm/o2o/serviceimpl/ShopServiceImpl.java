package zjmx.ssm.o2o.serviceimpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import zjmx.ssm.o2o.dao.ShopDao;
import zjmx.ssm.o2o.dto.ShopExecution;
import zjmx.ssm.o2o.entity.Shop;
import zjmx.ssm.o2o.enums.ShopStateEnum;
import zjmx.ssm.o2o.service.ShopService;
import zjmx.ssm.o2o.utils.ImageHolder;
import zjmx.ssm.o2o.utils.ImageUtil;
import zjmx.ssm.o2o.utils.PathUtil;

@Service
public class ShopServiceImpl implements ShopService{
	
	@Autowired
	private ShopDao shopdao;
	@Override
	@Transactional
	public ShopExecution addShop(Shop shop, CommonsMultipartFile shopImg) {
		//空值判断
		if(shop==null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		if(shop.getArea()==null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP_AREA);
		}
		if(shop.getUserInfo()==null) {
			return new ShopExecution(ShopStateEnum.NULL_USERINFO);
		}
		if(shop.getShopType()==null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOPTPYE);
		}
		try {
			//赋初值
			shop.setEnableStatus(0);
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());
			int EffectRow=shopdao.insertShop(shop);
			//店铺创建失败判断
			if(EffectRow<=0) {
				throw new RuntimeException("店铺创建失败");
			}else {
				if (shopImg!=null) {
					try {
						//存储图片
						ImageHolder imageHolder=new ImageHolder(shopImg.getInputStream(), shopImg.getOriginalFilename());
						addShopImg(shop,imageHolder);
					} catch (Exception e) {
						throw new RuntimeException("店铺图片上传失败："+e.getMessage());
					}
					EffectRow=shopdao.updateShop(shop);
					if (EffectRow<=0) {
						throw new RuntimeException("更新店铺图片地址失败：");
					}
				}
			}
			
		} catch (Exception e) {
			throw new RuntimeException("addShop error:" +e.getMessage());
		}
		return new ShopExecution(ShopStateEnum.CHECK,shop);
	}
	//得到文件名字以及输入流，则不需要CommonsMultipartFile了
	private void addShopImg(Shop shop,ImageHolder thumbnail) {
//		//获取shop图片的相对路径
//		String dest=PathUtil.getShopImgPath(shop.getShopId());
//		//获取店铺图片本地地址
//		String shopImgAddr=ImageUtil.genernateThumbnail(shopImg, dest);//这个参数才是需要传入下面方法的，单元测试完改回来
//		shop.setShopImg(shopImgAddr);
		//20201112
		String dest=PathUtil.getShopImgPath(shop.getShopId(),"ShopImage"+"/ShopId-"+shop.getShopId());
		String shopImgAddr=ImageUtil.genernateThumbnail(thumbnail, dest);
		shop.setShopImg(shopImgAddr.substring(6));
		
	}
	@Override
	public Shop getByShopId(long shopId) {
		return shopdao.queryByShopId(shopId);
	}
	@Override
	public ShopExecution modifyShop(Shop shop, ImageHolder thumbnail)
			throws RuntimeException {
		if (shop==null||shop.getShopId()==null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}else {
			try {
			//是否需要处理图片
			if (thumbnail.getImage()!=null&&thumbnail.getImageName()!=null&&!"".equals(thumbnail.getImageName())) {
				Shop tempShop=shopdao.queryByShopId(shop.getShopId());
				if (tempShop.getShopImg()!=null) {
					ImageUtil.deleteFileOrPath(tempShop.getShopImg());
				}
			addShopImg(shop,thumbnail);
			}
			//更新店铺信息
			shop.setLastEditTime(new Date());
			int effectNum=shopdao.updateShop(shop);
			System.out.println(effectNum);
			if (effectNum<=0) {
				return new ShopExecution(ShopStateEnum.INNER_ERROR);
			}else {
				shop=shopdao.queryByShopId(shop.getShopId());
				return new ShopExecution(ShopStateEnum.SUCCESS,shop);
			}
			} catch (Exception e) {
				throw new RuntimeException("error modify: "+e.getMessage());
			}
		}
	}
	@Override
	public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
		List<Shop> shoplist=shopdao.queryShopList(shopCondition, pageIndex, pageSize);
		int count=shopdao.queryShopCount(shopCondition);
		ShopExecution se=new ShopExecution();
		if(shoplist!=null) {
			se.setShoplist(shoplist);
			se.setCount(count);
		}else {
			se.setState(ShopStateEnum.INNER_ERROR.getState());
		}
		return se;
	}
	@Override
	public int updateShopByshopIdService(Shop shop) {
		return shopdao.updateShop(shop);
	}
}
