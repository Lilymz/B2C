package zjmx.ssm.o2o.service;

import java.util.List;

import zjmx.ssm.o2o.entity.Shop;
import zjmx.ssm.o2o.entity.ShopType;

public interface ShopTypeService {
	/**获取多个店铺类别
	 * @param shopType
	 * @return
	 */
	public List<ShopType> getShoptypelist(ShopType shopType);
	public int deleteShoptype(long shoptypeId);
	public ShopType queryShopTypeByNameService(long shopTypeId);
}
