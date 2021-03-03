package zjmx.ssm.o2o.service;

import java.util.List;

import zjmx.ssm.o2o.entity.Swipper;

/**
 * @author jj
 * 两个方法
 * 1.查询swiper轮播
 * 2.批量插入轮播
 */
public interface SwipperService {
	
	/**
	 * @param swipperCondition
	 * @return swiper查询结果
	 */
	List<Swipper> querySwippers(Swipper swipperCondition) throws Exception;
	/**
	 * @param swippers
	 * @return 插入几条数据
	 */
	int batchInsertSwippers(List<Swipper> swippers);
}
