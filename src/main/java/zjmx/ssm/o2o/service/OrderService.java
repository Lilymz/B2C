package zjmx.ssm.o2o.service;

import java.util.List;

import zjmx.ssm.o2o.entity.Order;

public interface OrderService {
	List<Order> queryOrderByShopId(Order order);
	int updateOrderByIf(Order order);
	int deleteOrderByOrderId(Integer orderId);
	int insertOrder(Order order);
	int batchInsertOrder(List<Order> batchOrders);
}
