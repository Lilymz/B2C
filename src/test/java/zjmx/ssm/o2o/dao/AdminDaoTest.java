package zjmx.ssm.o2o.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import zjmx.ssm.o2o.BaseTest;
import zjmx.ssm.o2o.entity.Admin;
import zjmx.ssm.o2o.utils.MD5EncodeUtil;

public class AdminDaoTest extends BaseTest{
	@Autowired
	private AdminDao adminDao;
	@Test
	public void queryAdmin() {
		Admin admin=new Admin();
		admin.setAdminName("superadmin");
		admin.setAdminPassword(MD5EncodeUtil.GetMD5("12345"));
		System.out.println(adminDao.queryAdmin(admin));
	}
}
