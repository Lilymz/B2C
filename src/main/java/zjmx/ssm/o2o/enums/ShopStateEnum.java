package zjmx.ssm.o2o.enums;

/**该部分还不太懂，得去看在看
 * @author jj
 *
 */
public enum ShopStateEnum {
	CHECK(0,"审核中"),OFFLINE(-1,"非法店铺"),SUCCESS(1,"操作成功"),PASS(2,"成功"),INNER_ERROR(-1001,"内部系统错误")
	,NULL_SHOPID(-1002,"shopId为空"),NULL_SHOP(-1003,"shop实体为空"),NULL_SHOP_AREA(-1004,"Shop中area为空")
	,NULL_USERINFO(-1005,"shopUSERINFO为空"),NULL_SHOPTPYE(-1006,"店铺类型为空");
	private int state;
	private String stateInfo;
	private ShopStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}
	/**根据传入的值返回相应的enum
	 * @param state
	 * @return
	 */
	public static ShopStateEnum stateOf(int state) {
		for(ShopStateEnum stateEnum:values()){
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
