package zjmx.ssm.o2o.service;

import java.util.List;

import zjmx.ssm.o2o.entity.UserInfo;

public interface UserInfoService {
	int updateUserInfoBymoneyService(UserInfo userInfoConditon);
	int insertUserInfoService(UserInfo userInfoConditon);
	UserInfo queryUserInfoService(UserInfo userInfoConditon);
	UserInfo verifyUserInfo(UserInfo userInfoConditon);
	List<UserInfo> queryUserInfosService(UserInfo userInfoConditon);
	int deleteUserInfoByUserId(Integer userId);
}
