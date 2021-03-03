package zjmx.ssm.o2o.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import zjmx.ssm.o2o.dto.ProductExecution;
import zjmx.ssm.o2o.entity.Product;
import zjmx.ssm.o2o.utils.ImageHolder;

public interface ProductService {
	
	public ProductExecution getProducts(Product productConditon,int beginIndex, int pageSize);
	/**
	 * @param product
	 * @param thumbnail
	 * @param imagesList
	 * @return
	 * @throws RuntimeException
	 */
	public ProductExecution insertProduct(Product product,ImageHolder thumbnail,List<ImageHolder> imagesList)throws RuntimeException;
	public ProductExecution updateProduct(Product product,ImageHolder thumbnail,List<ImageHolder> imagesList)throws RuntimeException;
	public ProductExecution deleteProduct(long productId);
	public ProductExecution queryProductById(Long productId);
	int updateProductEnableStatusByProductId(Product product);
}
