package zjmx.ssm.o2o.dao;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import zjmx.ssm.o2o.BaseTest;
import zjmx.ssm.o2o.entity.UserInfo;

public class UserInfoDaoTest extends BaseTest{
	@Autowired
	private UserInfoDao userInfoDao;
	@Test
	public void TestTheWhole() {
		UserInfo userInfoConditon=new UserInfo();
		//查询所有用户
		userInfoConditon.setUserType(1);
		System.out.println("查询所有用户："+userInfoDao.queryUserInfos(userInfoConditon));
		//查询单个用户
		userInfoConditon.setUserId(1);
		System.out.println("查询单个用户："+userInfoDao.queryUserInfo(userInfoConditon));
		//验证用户
		userInfoConditon.setUserName("admin");
		userInfoConditon.setUserPassword("123456");
		System.out.println("验证单个用户："+userInfoDao.verifyUserInfo(userInfoConditon));
		//更新用户
		userInfoConditon.setMoney(6000);
		System.out.println("更新单个用户："+userInfoDao.updateUserInfoBymoney(userInfoConditon));
		//插入用户
		userInfoConditon.setUserName("test");
		userInfoConditon.setUserPassword("123456");
		userInfoConditon.setProfileImg("\\xx\\xxx\\xx.jpg");
		userInfoConditon.setEnableStatus(1);
		userInfoConditon.setUserType(1);
		userInfoConditon.setGender("男");
		userInfoConditon.setEmail("3128269437@qq.com");
		userInfoConditon.setCreateTime(new Date());
		userInfoConditon.setLastEditTime(new Date());
		userInfoConditon.setMoney(200);
		System.out.println("插入单个用户："+userInfoDao.insertUserInfo(userInfoConditon));
	}
}
