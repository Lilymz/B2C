package zjmx.ssm.o2o.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import zjmx.ssm.o2o.entity.ShippingAddr;

/**
 * @author jj
 *
 */
public interface ShippingAddrDao {
	/**
	 *    多条件查询，根据某一或某些条件查询收货地址，如userID
	 * @param shippingAddr
	 * @return
	 */
	ShippingAddr queryShippingAddrByIf(@Param("shippingAddr")ShippingAddr shippingAddr);
	/**
	 * 多条件更改，根据某一或某些条件更改收货地址，如userID
	 * @param shippingAddr
	 * @return
	 */
	int updateShippingAddrByIf(@Param("shippingAddr")ShippingAddr shippingAddr);
	/**
	 * 根据goodsId来删除收货地址
	 * @param goodsId
	 * @return
	 */
	int deleteShippingAddrBygoodsId(Integer goodsId);
	/**
	 * 插入一条收货地址
	 * @param shippingAddr
	 * @return
	 */
	int insertShippingAddr(@Param("shippingAddr")ShippingAddr shippingAddr);
}
