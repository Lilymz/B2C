package zjmx.ssm.o2o.entity;

import java.util.Date;

/**商品图片（一个商品可能有多个图片，多对一的关系）
 * @author jj
 *
 */
public class ProductImage {
	
	//商品图片id
	private long productImageId;
	//商品图片地址
	private String imgAddr;
	//商品图片描述
	private String imgDesc;
	//权重
	private Integer priority;
	//创建时间
	private Date createTime;
	//商品id
	private Long productId;
	public long getProductImageId() {
		return productImageId;
	}
	public void setProductImageId(long productImageId) {
		this.productImageId = productImageId;
	}
	public String getImgAddr() {
		return imgAddr;
	}
	public void setImgAddr(String imgAddr) {
		this.imgAddr = imgAddr;
	}
	public String getImgDesc() {
		return imgDesc;
	}
	public void setImgDesc(String imgDesc) {
		this.imgDesc = imgDesc;
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
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	@Override
	public String toString() {
		return "ProductImage [productImageId=" + productImageId + ", imgAddr=" + imgAddr + ", imgDesc=" + imgDesc
				+ ", priority=" + priority + ", createTime=" + createTime + ", productId=" + productId + "]";
	}
}
