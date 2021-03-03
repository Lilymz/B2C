package zjmx.ssm.o2o.dto;

import java.util.List;

import zjmx.ssm.o2o.entity.Product;
import zjmx.ssm.o2o.enums.ProductEnum;

public class ProductExecution {
	private int state;
	private String stateInfo;
	private List<Product> products;
	private Product product;
	int UpdateStatus=-1;//1为添加,2为更改，3为删除，-1为操作
	//无参构造器
	public ProductExecution() {
	}	
	//错误的构造器
	public ProductExecution(ProductEnum Enum) {
		this.state=Enum.getState();
		this.stateInfo=Enum.getStateInfo();
	}	
	//查询成功的构造器
	public ProductExecution(ProductEnum Enum,List<Product> products) {
		this.state=Enum.getState();
		this.stateInfo=Enum.getStateInfo();
		this.products=products;
	}	
	public ProductExecution(ProductEnum Enum,Product product) {
		this.state=Enum.getState();
		this.stateInfo=Enum.getStateInfo();
		this.product=product;
	}	
	//添加，更改，删除成功的构造器
	public ProductExecution(ProductEnum Enum,int Status) {
		this.state=Enum.getState();
		this.stateInfo=Enum.getStateInfo();
		this.UpdateStatus=Status;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
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
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	public int getUpdateStatus() {
		return UpdateStatus;
	}
	public void setUpdateStatus(int updateStatus) {
		UpdateStatus = updateStatus;
	}	
	
}
