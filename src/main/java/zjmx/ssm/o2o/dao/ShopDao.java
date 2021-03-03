package zjmx.ssm.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import zjmx.ssm.o2o.entity.Shop;

public interface ShopDao {

	/**店铺列表，可输入条件为店铺名，店铺状态，店铺类别，区域Id,owner;
	 * @param ShopCondition查询条件
	 * @param rowIndex从第几行开始取
	 * @param pageSize返回的条数
	 * @return
	 */
	public List<Shop> queryShopList(@Param("ShopCondition") Shop ShopCondition, @Param("rowIndex") int rowIndex,
			@Param("pageSize") int pageSize);

	/**
	 * 查询shop的总数
	 * @param ShopCondition
	 * @return
	 */
	public int queryShopCount(@Param("ShopCondition") Shop ShopCondition);
	/**
	 * 根据ShopId 查询店铺信息
	 * 
	 * @param shopId
	 * @return
	 */
	public Shop queryByShopId(long shopId);

	/**
	 * 插入店铺信息
	 * 
	 * @param shop
	 * @return
	 */
	public int insertShop(Shop shop);

	/**
	 * 更新店铺信息
	 * 
	 * @param shop
	 * @return
	 */
	public int updateShop(Shop shop);

}
