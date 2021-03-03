package zjmx.ssm.o2o.entity;

public class ShippingAddr {
	//收货ID
	private Integer goodsId;
	//收货人
	private String goodsName;
	//电话
	private String goodsPhone;
	//地址
	private String goodsAddr;
	//当前用户的收货地址
	private UserInfo userInfo;
	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsPhone() {
		return goodsPhone;
	}
	public void setGoodsPhone(String goodsPhone) {
		this.goodsPhone = goodsPhone;
	}
	public String getGoodsAddr() {
		return goodsAddr;
	}
	public void setGoodsAddr(String goodsAddr) {
		this.goodsAddr = goodsAddr;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	@Override
	public String toString() {
		return "ShippingAddr [goodsId=" + goodsId + ", goodsName=" + goodsName + ", goodsPhone=" + goodsPhone
				+ ", goodsAddr=" + goodsAddr + ", userInfo=" + userInfo + "]";
	}
}
