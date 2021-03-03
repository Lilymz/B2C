package zjmx.ssm.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import zjmx.ssm.o2o.entity.ShopType;

public interface ShopTypeDao {
	public List<ShopType> queryShopType(@Param("ShopTypeCondition")ShopType ShopTypeCondition);
	public int deleteShopTyped(long shoptypeId);
	ShopType queryShopTypeByShopTypeName(long shopTypeId);
}
