package zjmx.ssm.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import zjmx.ssm.o2o.entity.Order;

public interface OrderDao {
	/**
	 * 根据ShopID查询属于该店铺下的所有订单信息
	 * @param order
	 * @return
	 */
	List<Order> queryOrderByShopId(@Param("order")Order order);
	/**
	 * 根据所有传入的参数更新订单信息
	 * @param order
	 * @return
	 */
	int updateOrderByIf(@Param("order")Order order);
	/**
	 * 根据订单id删除相对应的订单信息
	 * @param orderId
	 * @return
	 */
	int deleteOrderByOrderId(Integer orderId);
	/**
	 * 生成一份新订单信息(购买)
	 * @param order
	 * @return
	 */
	int insertOrder(@Param("order")Order order);
	/**
	 * 批量生成新订单信息(购物车)
	 * @param batchOrders
	 * @return
	 */
	int batchInsertOrder(List<Order> batchOrders);
	
}
