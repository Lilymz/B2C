package zjmx.ssm.o2o.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zjmx.ssm.o2o.dao.UserInfoDao;
import zjmx.ssm.o2o.entity.UserInfo;
import zjmx.ssm.o2o.service.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService{
	
	@Autowired
	private UserInfoDao dao;
	@Override
	public int updateUserInfoBymoneyService(UserInfo userInfoConditon) {
		return dao.updateUserInfoBymoney(userInfoConditon);
	}

	@Override
	public int insertUserInfoService(UserInfo userInfoConditon) {
		return dao.insertUserInfo(userInfoConditon);
	}

	@Override
	public UserInfo queryUserInfoService(UserInfo userInfoConditon) {
		return dao.queryUserInfo(userInfoConditon);
	}

	@Override
	public UserInfo verifyUserInfo(UserInfo userInfoConditon) {
		return dao.verifyUserInfo(userInfoConditon);
	}

	@Override
	public List<UserInfo> queryUserInfosService(UserInfo userInfoConditon) {
		return dao.queryUserInfos(userInfoConditon);
	}

	@Override
	public int deleteUserInfoByUserId(Integer userId) {
		return dao.deleteUserInfoByUserId(userId);
	}

}
