package zjmx.ssm.o2o.entity;

public class ShopCart {
	private Integer shopcartId;
	private String shopcartProductImg;
	private String shopcartProductDesc;
	private Integer shopcartProductPromotionPrice;
	private String shopcartProductName;
	private UserInfo userfo;
	private long shopcartShopId;
	public Integer getShopcartId() {
		return shopcartId;
	}
	public void setShopcartId(Integer shopcartId) {
		this.shopcartId = shopcartId;
	}
	public String getShopcartProductImg() {
		return shopcartProductImg;
	}
	public void setShopcartProductImg(String shopcartProductImg) {
		this.shopcartProductImg = shopcartProductImg;
	}
	public String getShopcartProductDesc() {
		return shopcartProductDesc;
	}
	public void setShopcartProductDesc(String shopcartProductDesc) {
		this.shopcartProductDesc = shopcartProductDesc;
	}
	public Integer getShopcartProductPromotion() {
		return shopcartProductPromotionPrice;
	}
	public void setShopcartProductPromotion(Integer shopcartProductPromotionPrice) {
		this.shopcartProductPromotionPrice = shopcartProductPromotionPrice;
	}
	public UserInfo getUserfo() {
		return userfo;
	}
	public void setUserfo(UserInfo userfo) {
		this.userfo = userfo;
	}
	public String getShopcartProductName() {
		return shopcartProductName;
	}
	public void setShopcartProductName(String shopcartProductName) {
		this.shopcartProductName = shopcartProductName;
	}
	public long getShopcartShopId() {
		return shopcartShopId;
	}
	public void setShopcartShopId(long shopcartShopId) {
		this.shopcartShopId = shopcartShopId;
	}
	@Override
	public String toString() {
		return "ShopCart [shopcartId=" + shopcartId + ", shopcartProductImg=" + shopcartProductImg
				+ ", shopcartProductDesc=" + shopcartProductDesc + ", shopcartProductPromotionPrice="
				+ shopcartProductPromotionPrice + ", shopcartProductName=" + shopcartProductName + ", userfo=" + userfo
				+ ", shopcartShopId=" + shopcartShopId + "]";
	}
}
