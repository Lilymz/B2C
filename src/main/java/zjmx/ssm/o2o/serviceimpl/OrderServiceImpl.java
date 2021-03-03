package zjmx.ssm.o2o.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zjmx.ssm.o2o.dao.OrderDao;
import zjmx.ssm.o2o.entity.Order;
import zjmx.ssm.o2o.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderDao dao;
	@Override
	public List<Order> queryOrderByShopId(Order order) {
		return dao.queryOrderByShopId(order);
	}

	@Override
	public int updateOrderByIf(Order order) {
		return dao.updateOrderByIf(order);
	}

	@Override
	public int deleteOrderByOrderId(Integer orderId) {
		return dao.deleteOrderByOrderId(orderId);
	}

	@Override
	public int insertOrder(Order order) {
		return dao.insertOrder(order);
	}
	
	@Override
	public int batchInsertOrder(List<Order> batchOrders) {
		return dao.batchInsertOrder(batchOrders);
	}

}
