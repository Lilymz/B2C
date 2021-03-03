package zjmx.ssm.o2o.utils;

import java.io.InputStream;

/**该类本是为了解决缩略图和详情图的处理
 * @author jj
 *
 */
public class ImageHolder {
	
	private InputStream image;
	private String imageName;
	public ImageHolder(InputStream image, String imageName) {
		this.image = image;
		this.imageName = imageName;
	}
	public InputStream getImage() {
		return image;
	}
	public void setImage(InputStream image) {
		this.image = image;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
}
