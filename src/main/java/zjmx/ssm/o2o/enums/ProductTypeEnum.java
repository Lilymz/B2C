package zjmx.ssm.o2o.enums;

public enum ProductTypeEnum {
	ProductType_FAIL(0000,"商品类别获取失败"),ProductType_SUCCESS(1111,"商品类别获取成功"),
	DELETE_ProductType_FAIL(2222,"商品类别删除失败"),DELETE_ProductType_SUCCESS(3333,"商品类别删除成功"),
	INSERT_ProductType_FAIL(4444,"商品类别添加失败"),INSERT_ProductType_SUCCESS(5555,"商品类别添加成功");
	private int state;
	private String stateInfo;
	private ProductTypeEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}
	public static ProductTypeEnum stateOf(int state) {
		for(ProductTypeEnum stateEnum:values()){
			if (stateEnum.getState()==state) {
				return stateEnum;
			}
		}
		return null;
	}
	public int getState() {
		return state;
	}
	public String getStateInfo() {
		return stateInfo;
	}
	
}
