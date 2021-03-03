package zjmx.ssm.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import zjmx.ssm.o2o.entity.Product;

public interface ProductDao {
	/**查询商店列表并分页，可输入的条件有商品名（模糊），商品状态，店铺Id，商品类别
	 * @param productConditon
	 * @param beginIndex
	 * @param pageSize
	 * @return
	 */
	public List<Product> queryProductList(@Param("productConditon") Product productConditon,
			@Param("beginIndex") int beginIndex, @Param("pageSize") int pageSize);
	
	/**添加商品，默认商品类型归属于productTypeId为1的类型
	 * @param product
	 * @return
	 */
	public int insertProduct(@Param("product")Product product);
	
	/**更新商品
	 * @param product
	 * @return
	 */
	public int updateProduct(@Param("product")Product product);
	
	/**删除商品
	 * @param productId
	 * @return
	 */
	public int deleteProduct(long productId);
	
	/**根据商品Id编辑商品信息
	 * @param ProductId
	 * @return
	 */
	public Product queryProductById(Long ProductId);
	/**
	 * 根据商品ID更改状态值
	 * @param product
	 * @return
	 */
	int updateProductEnableStatusByProductId(@Param("product")Product product);
}
