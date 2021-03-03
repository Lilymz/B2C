package zjmx.ssm.o2o.dto;

import java.util.List;

import zjmx.ssm.o2o.entity.ProductType;
import zjmx.ssm.o2o.enums.ProductTypeEnum;

public class ProductTypeExecution {
	private int state;
	private String stateInfo;
	private ProductType productType;
	private List<ProductType> productTypeslist;

	public ProductTypeExecution() {

	}

	// 删除商品类型的构造器
	public ProductTypeExecution(ProductTypeEnum ProductTypeEnum) {
		this.state = ProductTypeEnum.getState();
		this.stateInfo = ProductTypeEnum.getStateInfo();
	}

	// 商品类型操作成功
	public ProductTypeExecution(ProductTypeEnum ProductTypeEnum, ProductType ProductType) {
		this.state = ProductTypeEnum.getState();
		this.stateInfo = ProductTypeEnum.getStateInfo();
		this.productType = ProductType;
	}
	// 商品类型操作成功
	public ProductTypeExecution(ProductTypeEnum ProductTypeEnum, List<ProductType> listType) {
		this.state = ProductTypeEnum.getState();
		this.stateInfo = ProductTypeEnum.getStateInfo();
		this.productTypeslist = listType;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	public List<ProductType> getProductTypeslist() {
		return productTypeslist;
	}

	public void setProductTypeslist(List<ProductType> productTypeslist) {
		this.productTypeslist = productTypeslist;
	}
}
