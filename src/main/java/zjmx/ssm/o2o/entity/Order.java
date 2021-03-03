package zjmx.ssm.o2o.entity;

import java.util.Date;

public class Order {
	//订单Id
	private Integer orderId;
	//订单编号(当前年月日+7位随机数)
	private String orderNumber;
	//商品名
	private String orderProductName;
	//支付方式
	private Integer orderMethod;
	//下单时间
	private Date createTime;
	//完成时间
	private Date dealTime;
	//订单状态
	private Integer orderEnableStatus;
	//商品价格
	private String orderPrice;
	//订单当前用户
	private UserInfo userInfo;
	//订单当前店铺
	private Shop shop;
	//订单收货地址（用户手机号，用户地址）
	private ShippingAddr shippingAddr;
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getOrderProductName() {
		return orderProductName;
	}
	public void setOrderProductName(String orderProductName) {
		this.orderProductName = orderProductName;
	}
	public Integer getOrderMethod() {
		return orderMethod;
	}
	public void setOrderMethod(Integer orderMethod) {
		this.orderMethod = orderMethod;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getDealTime() {
		return dealTime;
	}
	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}
	public Integer getOrderEnableStatus() {
		return orderEnableStatus;
	}
	public void setOrderEnableStatus(Integer orderEnableStatus) {
		this.orderEnableStatus = orderEnableStatus;
	}
	public String getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	public ShippingAddr getShippingAddr() {
		return shippingAddr;
	}
	public void setShippingAddr(ShippingAddr shippingAddr) {
		this.shippingAddr = shippingAddr;
	}
	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", orderNumber=" + orderNumber + ", orderProductName=" + orderProductName
				+ ", orderMethod=" + orderMethod + ", createTime=" + createTime + ", dealTime=" + dealTime
				+ ", orderEnableStatus=" + orderEnableStatus + ", orderPrice=" + orderPrice + ", userInfo=" + userInfo
				+ ", shop=" + shop + ", shippingAddr=" + shippingAddr + "]";
	}
}
