package zjmx.ssm.o2o.dto;

import java.util.List;

import zjmx.ssm.o2o.entity.Shop;
import zjmx.ssm.o2o.enums.ShopStateEnum;

/**用于处理Shop处理后的结果状态是什么
 * 两个参数用于存储shop增删改查的信息，
 * 只有结果状态的话单凭状态码不能知道是什么错误，所以需要一个状态标识
 * @author jj
 *
 */
public class ShopExecution {
	//结果状态
	private int state;
	//状态标识
	private String stateInfo;
	
	//返回店铺数量
	private int count;
	
	//操作的shop（增删改的时候用到）
	private Shop shop;
	
	//shop列表（用于查询店铺的信息）
	private List<Shop> shoplist;
	
	public ShopExecution() {
	}
	//店铺操作失败使用的构造器
	public ShopExecution(ShopStateEnum stateEnum) {
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
	}
	//店铺操作成功使用的构造器
	public ShopExecution(ShopStateEnum stateEnum,Shop shop) {
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
		this.shop=shop;
	}
	//店铺操作成功使用的构造器
		public ShopExecution(ShopStateEnum stateEnum,List<Shop> shoplist) {
			this.state=stateEnum.getState();
			this.stateInfo=stateEnum.getStateInfo();
			this.shoplist=shoplist;
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
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	public List<Shop> getShoplist() {
		return shoplist;
	}
	public void setShoplist(List<Shop> shoplist) {
		this.shoplist = shoplist;
	}
}
