package zjmx.ssm.o2o.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zjmx.ssm.o2o.dao.AdminDao;
import zjmx.ssm.o2o.entity.Admin;
import zjmx.ssm.o2o.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	private AdminDao adminDao;
	@Override
	public Admin queryAdminService(Admin admin) {
		return adminDao.queryAdmin(admin);
	}
	
}
