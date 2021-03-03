package zjmx.ssm.o2o.service;

import zjmx.ssm.o2o.entity.ShippingAddr;

public interface ShippingAddrService {
	ShippingAddr queryShippingAddrByIf(ShippingAddr shippingAddr);
	int updateShippingAddrByIf(ShippingAddr shippingAddr);
	int deleteShippingAddrBygoodsId(Integer goodsId);
	int insertShippingAddr(ShippingAddr shippingAddr);
}
