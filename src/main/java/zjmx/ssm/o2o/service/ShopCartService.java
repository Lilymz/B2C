package zjmx.ssm.o2o.service;

import java.util.List;

import zjmx.ssm.o2o.entity.ShopCart;

public interface ShopCartService {
	List<ShopCart> queryShopCartsService(ShopCart shopCart);
	int insertShopCartService(ShopCart shopCart);
	int deleteShopCart(ShopCart shopCart);
}
