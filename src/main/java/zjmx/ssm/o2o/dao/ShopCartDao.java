package zjmx.ssm.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import zjmx.ssm.o2o.entity.ShopCart;

public interface ShopCartDao {
	//通过user来查询数据库中购物车的总商品
	List<ShopCart> queryShopCarts(@Param("shopCart")ShopCart shopCart);
	//插入一条购物车数据
	int insertShopCart(@Param("shopCart")ShopCart shopCart);
	//通过shopCart进行批量删除
	int deleteShopCart(@Param("shopCart")ShopCart shopCart);
}
