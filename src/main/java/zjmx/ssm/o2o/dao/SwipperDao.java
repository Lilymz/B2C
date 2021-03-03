package zjmx.ssm.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import zjmx.ssm.o2o.entity.Swipper;

public interface SwipperDao {
	public List<Swipper> querySwippers(@Param("swipperCondition")Swipper swipperCondition);
	public int batchInsertSwippers(List<Swipper> swippers);
}
