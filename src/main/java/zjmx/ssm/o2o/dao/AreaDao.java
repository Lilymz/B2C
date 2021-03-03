package zjmx.ssm.o2o.dao;

import java.util.List;

import zjmx.ssm.o2o.entity.Area;

public interface AreaDao {
	public List<Area> queryArea();
	public Area queryAreaByName(String areaName);
}
