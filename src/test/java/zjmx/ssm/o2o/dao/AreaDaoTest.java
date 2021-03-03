package zjmx.ssm.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import zjmx.ssm.o2o.BaseTest;
import zjmx.ssm.o2o.entity.Area;

/**测试结束，结果满意
 * @author jj
 *
 */
public class AreaDaoTest extends BaseTest{
	@Autowired
	private AreaDao dao;
	@Test
	public void testQueryList() {
		List<Area> list=dao.queryArea();
		assertEquals(2,list.size());
	}
}
