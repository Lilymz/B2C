package zjmx.ssm.o2o.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zjmx.ssm.o2o.dao.ProductTypeDao;
import zjmx.ssm.o2o.dto.ProductTypeExecution;
import zjmx.ssm.o2o.entity.ProductType;
import zjmx.ssm.o2o.enums.ProductTypeEnum;
import zjmx.ssm.o2o.service.ProductTypeService;

@Service
public class ProductTypeServiceimpl implements ProductTypeService{
	
	@Autowired
	private ProductTypeDao productTypeDao;

	@Override
	public ProductTypeExecution getProductTypes(long shopId) {
		List<ProductType> listType=productTypeDao.getProductTypes(shopId);
		ProductTypeExecution pe=null;
		if (!productTypeDao.getProductTypes(shopId).isEmpty()) {
			pe=new ProductTypeExecution(ProductTypeEnum.ProductType_SUCCESS, listType);
		}
		if(pe==null) {
			pe=new ProductTypeExecution(ProductTypeEnum.ProductType_FAIL);
		}
		return pe;
	}

	@Override
	public ProductTypeExecution deleteProductType(long productTypeId) {
		boolean res=productTypeDao.deleteProductType(productTypeId);
		ProductTypeExecution pe=null;
		if (res) {
			pe=new ProductTypeExecution(ProductTypeEnum.DELETE_ProductType_SUCCESS);
		}else {
			pe=new ProductTypeExecution(ProductTypeEnum.DELETE_ProductType_FAIL);
		}
		return pe;
	}

	@Override
	@Transactional
	public ProductTypeExecution batchInsertProductType(List<ProductType> productTypes) {
		ProductTypeExecution pe=null;
		if(productTypes!=null&&productTypes.size()>0) {
			try {
				int res=productTypeDao.batchInsertProductType(productTypes);
				if (res>0) {
					pe=new ProductTypeExecution(ProductTypeEnum.INSERT_ProductType_SUCCESS);
				}else {
					pe=new ProductTypeExecution(ProductTypeEnum.INSERT_ProductType_FAIL);
				}
			} catch (Exception e) {
				throw new RuntimeException();
			}
		}
		return pe;
	}
}
