package zjmx.ssm.o2o.entity;

import java.util.Date;

/**店铺类别
 * @author jj
 *
 */
public class ShopType {
	
	//店铺类别Id
	private long shoptypeId;
	//店铺类别名
	private String shoptypeName;
	//店铺类别权重
	private Integer priority;
	//店铺类别图片
	private String shoptypeImg;
	//店铺类别描述
	private String shoptypeDesc;
	//创建时间
	private Date createTime;
	//修改时间
	private Date lastEditTime;
	//父级店铺类型
	private Integer parentId;
	
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public long getShoptypeId() {
		return shoptypeId;
	}
	public void setShoptypeId(long shoptypeId) {
		this.shoptypeId = shoptypeId;
	}
	public String getShoptypeName() {
		return shoptypeName;
	}
	public void setShoptypeName(String shoptypeName) {
		this.shoptypeName = shoptypeName;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public String getShoptypeImg() {
		return shoptypeImg;
	}
	public void setShoptypeImg(String shoptypeImg) {
		this.shoptypeImg = shoptypeImg;
	}
	public String getShoptypeDesc() {
		return shoptypeDesc;
	}
	public void setShoptypeDesc(String shoptypeDesc) {
		this.shoptypeDesc = shoptypeDesc;
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
	@Override
	public String toString() {
		return "ShopType [shoptypeId=" + shoptypeId + ", shoptypeName=" + shoptypeName + ", priority=" + priority
				+ ", shoptypeImg=" + shoptypeImg + ", shoptypeDesc=" + shoptypeDesc + ", createTime=" + createTime
				+ ", lastEditTime=" + lastEditTime + ", parentId=" + parentId + "]";
	}
}
