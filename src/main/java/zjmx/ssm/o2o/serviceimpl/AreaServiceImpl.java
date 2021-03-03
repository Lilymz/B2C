package zjmx.ssm.o2o.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zjmx.ssm.o2o.dao.AreaDao;
import zjmx.ssm.o2o.entity.Area;
import zjmx.ssm.o2o.service.AreaService;
@Service
public class AreaServiceImpl implements AreaService{
	
	@Autowired
	private AreaDao dao;
	@Override
	public List<Area> getAreas() {
		List<Area> list=dao.queryArea();
		return list;
	}
	@Override
	public Area queryAreaByName(String areaName) {
		return dao.queryAreaByName(areaName);
	}

}
