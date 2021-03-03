package zjmx.ssm.o2o.dao;

import org.apache.ibatis.annotations.Param;

import zjmx.ssm.o2o.entity.Admin;

public interface AdminDao {
	//根据账号密码登录管理员
	Admin queryAdmin(@Param("admin")Admin admin);
}
