package zjmx.ssm.o2o.enums;

public enum ProductEnum {
	PRODUCTEMPTY(200,"商品为空"),PRODUCTIMGLIST(201,"创建商品详情图失败"),
	PRODUCTQUERY_ERROR(400,"商品查询错误"),PRODUCTQUERY_SUCCESS(401,"商品查询成功"),
	PRODUCTINSERT_ERROR(500,"商品添加错误"),PRODUCTINSERT_SUCCESS(501,"商品添加成功"),
	PRODUCTUPDATE_ERROR(600,"商品更改错误"),PRODUCTUPDATE_SUCCESS(601,"商品类型删除成功"),
	PRODUCTDELETE_ERROR(300,"商品删除错误"),PRODUCTDELETE_SUCCESS(301,"商品删除成功");
	private int state;
	private String stateInfo;
	private ProductEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}
	public static ProductEnum stateOf(int state) {
		for (ProductEnum prductEnum : values()) {
			if(prductEnum.getState()==state) {
				return prductEnum;
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
