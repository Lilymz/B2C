package zjmx.ssm.o2o.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zjmx.ssm.o2o.dao.ShippingAddrDao;
import zjmx.ssm.o2o.entity.ShippingAddr;
import zjmx.ssm.o2o.service.ShippingAddrService;

@Service
public class ShippingAddrServiceImpl implements ShippingAddrService{

	@Autowired
	private ShippingAddrDao dao;
	@Override
	public ShippingAddr queryShippingAddrByIf(ShippingAddr shippingAddr) {
		return dao.queryShippingAddrByIf(shippingAddr);
	}
	@Override
	public int updateShippingAddrByIf(ShippingAddr shippingAddr) {
		return dao.updateShippingAddrByIf(shippingAddr);
	}
	@Override
	public int deleteShippingAddrBygoodsId(Integer goodsId) {
		return dao.deleteShippingAddrBygoodsId(goodsId);
	}

	@Override
	public int insertShippingAddr(ShippingAddr shippingAddr) {
		return dao.insertShippingAddr(shippingAddr);
	}

}
