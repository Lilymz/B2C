package zjmx.ssm.o2o.entity;

import java.util.Date;

public class ProductType {
	//商品类别Id
	private Long productTypeId;
	//店铺Id
	private Long shopId;
	//商品类别名称
	private String productTypeName;
	//权重
	private Integer priority;
	//创建时间
	private Date createTime;
	public Long getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(Long productTypeId) {
		this.productTypeId = productTypeId;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public String getProductTypeName() {
		return productTypeName;
	}
	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
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
	@Override
	public String toString() {
		return "ProductType [productTypeId=" + productTypeId + ", shopId=" + shopId + ", productTypeName="
				+ productTypeName + ", priority=" + priority + ", createTime=" + createTime + "]";
	}
	
}
