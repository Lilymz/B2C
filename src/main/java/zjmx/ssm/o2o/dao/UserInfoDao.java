package zjmx.ssm.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import zjmx.ssm.o2o.entity.UserInfo;

public interface UserInfoDao {
	//更新money
	int updateUserInfoBymoney(@Param("userInfoConditon")UserInfo userInfoConditon);
	//用户注册
	int insertUserInfo(@Param("userInfoConditon")UserInfo userInfoConditon);
	//登录条件查询
	UserInfo queryUserInfo(@Param("userInfoConditon")UserInfo userInfoConditon);
	//查询店铺管理员
	List<UserInfo> queryUserInfos(@Param("userInfoConditon")UserInfo userInfoConditon);
	//登录验证
	UserInfo verifyUserInfo(@Param("userInfoConditon")UserInfo userInfoConditon);
	//删除用户
	int deleteUserInfoByUserId(Integer userId);
}
