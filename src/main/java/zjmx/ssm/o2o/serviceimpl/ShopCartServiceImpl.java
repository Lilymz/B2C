package zjmx.ssm.o2o.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zjmx.ssm.o2o.dao.ShopCartDao;
import zjmx.ssm.o2o.entity.ShopCart;
import zjmx.ssm.o2o.service.ShopCartService;

@Service
public class ShopCartServiceImpl implements ShopCartService{
	
	@Autowired
	private ShopCartDao dao;
	@Override
	public List<ShopCart> queryShopCartsService(ShopCart shopCart) {
		return dao.queryShopCarts(shopCart);
	}

	@Override
	public int insertShopCartService(ShopCart shopCart) {
		return dao.insertShopCart(shopCart);
	}

	@Override
	public int deleteShopCart(ShopCart shopCart) {
		return dao.deleteShopCart(shopCart);
	}

}
