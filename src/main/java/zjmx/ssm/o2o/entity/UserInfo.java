package zjmx.ssm.o2o.entity;

import java.util.Date;

/**用户信息
 * @author jj
 *
 */
public class UserInfo {
	//用户ID
	private Integer userId;
	//用户名
	private String userName;
	//密码
	private String userPassword;
	//用户头像地址
	private String profileImg;
	//用户状态
	private Integer enableStatus;
	//用户标识
	private Integer userType;
	//用户性别
	private String gender;
	//用户邮箱
	private String email;
	//创建时间
	private Date createTime;
	//最近修改时间
	private Date lastEditTime;
	//余额
	private Integer money;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getProfileImg() {
		return profileImg;
	}
	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}
	public Integer getEnableStatus() {
		return enableStatus;
	}
	public void setEnableStatus(Integer enableStatus) {
		this.enableStatus = enableStatus;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public Integer getMoney() {
		return money;
	}
	public void setMoney(Integer money) {
		this.money = money;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	@Override
	public String toString() {
		return "UserInfo [userId=" + userId + ", userName=" + userName + ", userPassword=" + userPassword
				+ ", profileImg=" + profileImg + ", enableStatus=" + enableStatus + ", userType=" + userType
				+ ", gender=" + gender + ", email=" + email + ", createTime=" + createTime + ", lastEditTime="
				+ lastEditTime + ", money=" + money + "]";
	}
}
