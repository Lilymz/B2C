package zjmx.ssm.o2o.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zjmx.ssm.o2o.dao.ShopTypeDao;
import zjmx.ssm.o2o.entity.ShopType;
import zjmx.ssm.o2o.service.ShopTypeService;

@Service
public class ShopTypeServiceImpl implements ShopTypeService{
	
	@Autowired
	private ShopTypeDao shoptypedao;
	@Override
	public List<ShopType> getShoptypelist(ShopType shopType) {
		
		return shoptypedao.queryShopType(shopType);
	}
	@Override
	public int deleteShoptype(long shoptypeId) {
		return shoptypedao.deleteShopTyped(shoptypeId);
	}
	@Override
	public ShopType queryShopTypeByNameService(long shopTypeId) {
		return shoptypedao.queryShopTypeByShopTypeName(shopTypeId);
	}

}
