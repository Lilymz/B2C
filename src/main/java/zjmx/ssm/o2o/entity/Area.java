package zjmx.ssm.o2o.entity;

import java.util.Date;

/**地域
 * @author jj
 *注意：mysql写表的时候字段名不需要''用来括起来
 */
public class Area {
	//区域ID
	private Integer areaId;
	//区域名
	private String areaName;
	//权重（用于排位,权重越大越靠前）
	private Integer priority;
	//区域创建时间
	private Date createTime;
	//区域修改时间
	private Date lastEditTime;
	public Area() {}
	public Area(Integer areaId, String areaName, Integer priority, Date createTime, Date lastEditTime) {
		super();
		this.areaId = areaId;
		this.areaName = areaName;
		this.priority = priority;
		this.createTime = createTime;
		this.lastEditTime = lastEditTime;
	}
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
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
	@Override
	public String toString() {
		return "Area [areaId=" + areaId + ", areaName=" + areaName + ", priority=" + priority + ", createTime="
				+ createTime + ", lastEditTime=" + lastEditTime + "]";
	}
}
