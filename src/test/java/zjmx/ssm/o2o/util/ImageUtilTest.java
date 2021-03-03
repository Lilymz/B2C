package zjmx.ssm.o2o.util;

import org.junit.Test;

import zjmx.ssm.o2o.utils.ImageUtil;

public class ImageUtilTest {
	@Test
	public void TestinputstreamToMultipartFile() throws Exception {
		ImageUtil.deleteThumbnail("D:/o2o/image/ProductImage/productId-0/test.png");
	}
}
