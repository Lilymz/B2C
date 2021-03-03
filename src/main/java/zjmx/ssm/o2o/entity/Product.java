package zjmx.ssm.o2o.entity;

import java.util.Date;
import java.util.List;

/**
 * 商品描述
 * @author jj
 *
 */
public class Product {
	//商品id
	private long productId;
	//商品名
	private String productName;
	//商品描述
	private String productDesc;
	//商品简略图
	private String imgAddr;
	//商品常规价
	private String normalPrice;
	//商品折扣价
	private String promotionPrice;
	//权重
	private Integer priority;
	//创建时间
	private Date createTime;
	//修改时间
	private Date lastEditTime;
	//商店状态
	private Integer enableStatus;
	//商品图片展示所有图片
	private List<ProductImage> productImglist;
	//归属店铺类型
	private ProductType productType;
	//归属商店
	private Shop shop;
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public String getImgAddr() {
		return imgAddr;
	}
	public void setImgAddr(String imgAddr) {
		this.imgAddr = imgAddr;
	}
	public String getNormalPrice() {
		return normalPrice;
	}
	public void setNormalPrice(String normalPrice) {
		this.normalPrice = normalPrice;
	}
	public String getPromotionPrice() {
		return promotionPrice;
	}
	public void setPromotionPrice(String promotionPrice) {
		this.promotionPrice = promotionPrice;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastEditTime() {
		return lastEditTime;
	}
	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}
	public Integer getEnableStatus() {
		return enableStatus;
	}
	public void setEnableStatus(Integer enableStatus) {
		this.enableStatus = enableStatus;
	}
	public List<ProductImage> getProductImglist() {
		return productImglist;
	}
	public void setProductImglist(List<ProductImage> productImglist) {
		this.productImglist = productImglist;
	}
	public ProductType getProductType() {
		return productType;
	}
	public void setProductType(ProductType productType) {
		this.productType = productType;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + ", productDesc=" + productDesc
				+ ", imgAddr=" + imgAddr + ", normalPrice=" + normalPrice + ", promotionPrice=" + promotionPrice
				+ ", priority=" + priority + ", createTime=" + createTime + ", lastEditTime=" + lastEditTime
				+ ", enableStatus=" + enableStatus + ", productImglist=" + productImglist + ", productType="
				+ productType + ", shop=" + shop + ", getProductId()=" + getProductId() + ", getProductName()="
				+ getProductName() + ", getProductDesc()=" + getProductDesc() + ", getImgAddr()=" + getImgAddr()
				+ ", getNormalPrice()=" + getNormalPrice() + ", getPromotionPrice()=" + getPromotionPrice()
				+ ", getPriority()=" + getPriority() + ", getCreateTime()=" + getCreateTime() + ", getLastEditTime()="
				+ getLastEditTime() + ", getEnableStatus()=" + getEnableStatus() + ", getProductImglist()="
				+ getProductImglist() + ", getProductType()=" + getProductType() + ", getShop()=" + getShop()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

}
