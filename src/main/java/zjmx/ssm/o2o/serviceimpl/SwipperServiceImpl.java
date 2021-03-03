package zjmx.ssm.o2o.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zjmx.ssm.o2o.dao.SwipperDao;
import zjmx.ssm.o2o.entity.Swipper;
import zjmx.ssm.o2o.service.SwipperService;

@Service
public class SwipperServiceImpl implements SwipperService{
	
	@Autowired
	private SwipperDao swipperdao;
	@Override
	public List<Swipper> querySwippers(Swipper swipperCondition) throws Exception {
		List<Swipper> res=new ArrayList<Swipper>();
			if (swipperCondition!=null&&swipperCondition.getEnableStatus()!=0) {
				//访问数据库查询所需数据
				res=swipperdao.querySwippers(swipperCondition);
				if(res==null) {
					throw new Exception("无swipper数据");
				}
			}else {
				throw new Exception("swipper条件为空或者状态值为0");
			}
		return res;
	}

	@Override
	public int batchInsertSwippers(List<Swipper> swippers) {
		// TODO Auto-generated method stub
		return 0;
	}

}
