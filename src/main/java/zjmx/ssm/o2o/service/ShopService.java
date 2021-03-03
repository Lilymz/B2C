package zjmx.ssm.o2o.service;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import zjmx.ssm.o2o.dto.ShopExecution;
import zjmx.ssm.o2o.entity.Shop;
import zjmx.ssm.o2o.utils.ImageHolder;

public interface ShopService {
	
	/**根据shopCondition返回想应得列表
	 * @param shopCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);
	/**
	 * 根据id获取店
	 * @param shopId
	 * @return
	 */
	public Shop getByShopId(long shopId);
	
	/**更新店铺信息，包括图片处理,
	 * @param shop
	 * @param shopImginInputStream
	 * @param filename
	 * @return
	 */
	public ShopExecution modifyShop(Shop shop,ImageHolder thumbnail)throws RuntimeException;
	/**添加店铺
	 * @param shop
	 * @param shopImg
	 * @return
	 */
	public ShopExecution addShop(Shop shop,CommonsMultipartFile shopImg); 
	/**
	 * 管理员操作店铺
	 * @param shop
	 * @return
	 */
	public int updateShopByshopIdService(Shop shop);
}
