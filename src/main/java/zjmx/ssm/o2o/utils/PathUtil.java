package zjmx.ssm.o2o.utils;

public class PathUtil {
	//针对不同系统的文件分隔符
	private static String separator=System.getProperty("file.separator");
	
	/**获取不同系统的基路径
	 * @return
	 */
	public static String getImgBasePath() {
		String os=System.getProperty("os.name");
		String basePath="";
		if(os.toLowerCase().startsWith("win")) {
			basePath="D/projectdev/image/";
		}else {
			basePath="/home/zhangjie/image/";
		}
		basePath=basePath.replace("/", separator);
		return basePath;
	}
	
	/**根据不同的shop店家获取店家图片的路径
	 * @param shopId
	 * @param Typename 比如是店铺的图片路径则为：D:/o2o/image/Shop,商品的图片路径则为:D:/o2o/image/Product
	 * @return
	 */
	public static String getShopImgPath(long shopId,String Typename) {
		String imagePath="D:/o2o/image/"+Typename+"/";
		return imagePath.replace("/", separator);
	}
}
