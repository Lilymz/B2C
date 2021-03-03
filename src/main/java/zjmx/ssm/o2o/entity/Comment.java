package zjmx.ssm.o2o.entity;

import java.util.Date;

/**管理员账号
 * @author jj
 *
 */
public class Comment {
	private Integer commentId;
	private String commentContent;
	private Integer love;
	private Date createTime;
	private Shop shop;
	public Integer getCommentId() {
		return commentId;
	}
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public Integer getLove() {
		return love;
	}
	public void setLove(Integer love) {
		this.love = love;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	@Override
	public String toString() {
		return "ShopAdmin [commentId=" + commentId + ", commentContent=" + commentContent + ", love=" + love
				+ ", createTime=" + createTime + ", shop=" + shop + "]";
	}
}
