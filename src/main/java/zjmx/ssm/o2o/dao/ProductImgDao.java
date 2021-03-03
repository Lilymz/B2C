package zjmx.ssm.o2o.dao;

import java.util.List;

import zjmx.ssm.o2o.entity.ProductImage;

public interface ProductImgDao {
	
	/**根据商品id查询产品展示图片
	 * @param productId
	 * @return
	 */
	public List<ProductImage> queryProductImageList(long productId);
	
	/**批量添加产品展示图片
	 * @param productImageList
	 * @return
	 */
	public int batchInsertProductImg(List<ProductImage> productImageList);
	
	/**根据商品Id删除展示图
	 * @param productId
	 * @return
	 */
	public int deleteProductImgByProductId(long productId);
}
