package zjmx.ssm.o2o.dao;

import java.util.List;

import zjmx.ssm.o2o.entity.ProductType;

public interface ProductTypeDao {
	public List<ProductType> getProductTypes(long shopId);
	public boolean deleteProductType(long productTypeId);
	public int batchInsertProductType(List<ProductType> productTypes);
}
