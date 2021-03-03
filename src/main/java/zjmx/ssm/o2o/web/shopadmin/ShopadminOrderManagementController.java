package zjmx.ssm.o2o.web.shopadmin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import zjmx.ssm.o2o.entity.Order;
import zjmx.ssm.o2o.entity.ShippingAddr;
import zjmx.ssm.o2o.entity.Shop;
import zjmx.ssm.o2o.service.OrderService;
import zjmx.ssm.o2o.service.ShippingAddrService;

@Controller
@RequestMapping("shopadmin")
public class ShopadminOrderManagementController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private ShippingAddrService shippngAddrService;
	@RequestMapping("/orderManagement")
	private String orderManagement() {return "OrderManagement/OrderManagement";}
	
	//获取所有订单
	@RequestMapping("/WholeOrder")
	@ResponseBody
	private Map<String, Object> WholeOrder(HttpServletRequest request,@RequestParam("ShopId")Long ShopId){
		Map<String, Object> modelMap=new HashMap<>();
		//非空校验
		if(ShopId==null) {
			modelMap.put("success",false);
			modelMap.put("errMsg","未成功获取店铺Id,无法查询订单信息");
			return modelMap;
		}
		try {
			Order orderCondition=new Order();
			Shop shop=new Shop();
			shop.setShopId(ShopId);
			orderCondition.setShop(shop);
			//根据id查询属于该店铺下的订单
			List<Order> orders = orderService.queryOrderByShopId(orderCondition);
			if (orders.size()==0) {
				modelMap.put("success",false);
				modelMap.put("Msg","无订单信息");
				return modelMap;
			}
			modelMap.put("success",true);
			modelMap.put("orders",orders);
			return modelMap;
		} catch (Exception e) {
			modelMap.put("success",false);
			modelMap.put("errMsg",e.getMessage());
			return modelMap;
		}
	}
	@RequestMapping("/updateOrder")
	@ResponseBody
	private Map<String, Object> updateOrder(HttpServletRequest request,@RequestBody String orderString){
		Map<String, Object> modelMap=new HashMap<>();
		//非空校验
		if(orderString=="{}") {
			modelMap.put("success",false);
			modelMap.put("errMsg","未能更新订单,无法获取更新参数");
			return modelMap;
		}
		ObjectMapper orderMapper=new ObjectMapper();
		try {
			@SuppressWarnings("unchecked")
			Map<String, String> orderMap=orderMapper.readValue(orderString, Map.class);
			ShippingAddr shippingAddr=new ShippingAddr();
			shippingAddr.setGoodsId(Integer.valueOf(orderMap.get("goodsId")));
			shippingAddr.setGoodsName(orderMap.get("goodsName"));
			shippingAddr.setGoodsPhone(orderMap.get("goodsPhone"));
			shippingAddr.setGoodsAddr(orderMap.get("goodsAddr"));
			//更新收货地址
			int effectRow = shippngAddrService.updateShippingAddrByIf(shippingAddr);
			//更新订单
			Order order=new Order();
			order.setOrderId(Integer.valueOf(orderMap.get("orderId")));
			order.setOrderEnableStatus(Integer.valueOf(orderMap.get("orderEnableStatus")));
			if(order.getOrderEnableStatus()==1) {
				order.setDealTime(new Date());
			}else {
				order.setDealTime(new Date(0L));
			}
			orderService.updateOrderByIf(order);
			modelMap.put("success",true);
			modelMap.put("url","/o2o/shopadmin/orderManagement?ShopId="+((Shop)request.getSession().getAttribute("currentShop")).getShopId());
			return modelMap;
		} catch (JsonProcessingException e) {
			modelMap.put("success",false);
			modelMap.put("errMsg",e.getMessage());
			return modelMap;
		}
	}
	@RequestMapping("/queryOrder")
	@ResponseBody
	private Map<String, Object> queryOrder(HttpServletRequest request,@RequestBody String OrderNumber){
		Map<String, Object> modelMap=new HashMap<>();
		//非空校验
		if(OrderNumber==null) {
			modelMap.put("success",false);
			modelMap.put("errMsg","未能查询更新订单,无法获取查询参数");
			return modelMap;
		}
		try {
			Order order=new Order();
			order.setOrderNumber(OrderNumber.replaceAll("\"", "").trim());
			List<Order> orders = orderService.queryOrderByShopId(order);
			if(orders.size()==0) {
				modelMap.put("success",false);
				modelMap.put("Msg","未查询到任何订单!");
				return modelMap;
			}
			modelMap.put("success",true);
			modelMap.put("orders",orders);
			return modelMap;
		} catch (Exception e) {
			modelMap.put("success",false);
			modelMap.put("errMsg",e.getMessage());
			return modelMap;
		}
	}
}
