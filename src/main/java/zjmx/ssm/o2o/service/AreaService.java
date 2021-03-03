package zjmx.ssm.o2o.service;

import java.util.List;

import zjmx.ssm.o2o.entity.Area;
public interface AreaService {
	public List<Area> getAreas();
	Area queryAreaByName(String areaName);
}
