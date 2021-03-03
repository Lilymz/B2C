package zjmx.ssm.o2o.entity;

import java.util.Date;

/**主页轮播（头条）
 * @author jj
 *
 */
public class Swipper {
	//轮播图id
	private Integer swipperId;
	//轮播名称
	private String swipperName;
	//轮播显示跳转链接
	private String swipperLink;
	//轮播图
	private String swipperImg;
	//权重
	private Integer priority;
	//轮播状态
	private Integer enableStatus;
	//创建时间
	private Date createTime;
	//修改时间
	private Date lastEditTime;
	
	public Integer getSwipperId() {
		return swipperId;
	}
	public void setSwipperId(Integer swipperId) {
		this.swipperId = swipperId;
	}
	public String getSwipperName() {
		return swipperName;
	}
	public void setSwipperName(String swipperName) {
		this.swipperName = swipperName;
	}
	public String getSwipperLink() {
		return swipperLink;
	}
	public void setSwipperLink(String swipperLink) {
		this.swipperLink = swipperLink;
	}
	public String getSwipperImg() {
		return swipperImg;
	}
	public void setSwipperImg(String swipperImg) {
		this.swipperImg = swipperImg;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Integer getEnableStatus() {
		return enableStatus;
	}
	public void setEnableStatus(Integer enableStatus) {
		this.enableStatus = enableStatus;
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
		return "Swipper [swipperId=" + swipperId + ", swipperName=" + swipperName + ", swipperLink=" + swipperLink
				+ ", swipperImg=" + swipperImg + ", priority=" + priority + ", enableStatus=" + enableStatus
				+ ", createTime=" + createTime + ", lastEditTime=" + lastEditTime + "]";
	}
	
}
