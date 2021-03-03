package zjmx.ssm.o2o.service;

import java.util.List;

import zjmx.ssm.o2o.dto.ProductTypeExecution;
import zjmx.ssm.o2o.entity.ProductType;

public interface ProductTypeService {
	
	/**根据当前传入的店铺Id查询该店铺下的商品类型
	 * @param shopId
	 * @return
	 */
	public ProductTypeExecution getProductTypes(long shopId);
	
	/**根据当前传入的商品类型Id删除该店铺下的商品类型
	 * @param productTypeId
	 * @return
	 */
	public ProductTypeExecution deleteProductType(long productTypeId);
	
	/**批量增加商品类别
	 * @param productTypes
	 * @return
	 */
	public ProductTypeExecution batchInsertProductType(List<ProductType> productTypes);
}
