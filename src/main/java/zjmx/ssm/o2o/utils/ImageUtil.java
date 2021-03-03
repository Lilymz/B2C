package zjmx.ssm.o2o.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

/**这个类是实现Shop上传的图片做成缩略图的工具类
 * 三个成员变量：realPath为当前执行的类路径(为了找到logo.png)
 * sf为生成sf定义的时间戳格式
 * r为生成的随机函数
 * @author jj
 *
 */
/**
 * @author jj
 *
 */
public class ImageUtil {
	private static final String realPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	private static final SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Random r = new Random();

	/**
	 * @param thumbnail:文件上传的内容
	 * @param targetAddr：接受后存储在服务器的路径（可能不存在）
	 * @return
	 */
	public static String genernateThumbnail(ImageHolder thumbnail, String targetAddr) {
		// 文件名
		String realFileName = getRandomFileName();
		// 拓展名
		String extension = thumbnail.getImageName();
		// 可能目标文件路径不存在，所以必须创建
		makeDirPath(targetAddr);
		// 根据targetAddr+文件名+扩展名得到的相对路径
		String relativePath = targetAddr + realFileName + extension;
		File destFile = new File(relativePath);
		try {
			Thumbnails.of(thumbnail.getImage()).size(740, 247)
					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(
							new File("D:/o2o/image/copyRight" + "/logo.jpg")),
							0.6f)
					.outputQuality(0.8f).toFile(destFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return relativePath;
	}

	/**
	 * 解决可能目标地址不存在的目录逐级递归创建
	 * 
	 * @param targetAddr
	 */
	private static void makeDirPath(String targetAddr) {
		String completePath = targetAddr;
		File file = new File(completePath);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	/**
	 * 处理上传缩略图存储在服务器上的文件名 每个文件名不一样可以设置年月日小时分钟秒的时间戳+5为随机数
	 * 
	 * @return
	 */
	private static String getRandomFileName() {
		// 获取随机五位数;
		int anyNum = r.nextInt() * 89999 + 10000;
		// 当前时间格式
		String nowTimestr = sf.format(new Date());
		return nowTimestr + anyNum;
	}

	/**
	 * 判断storePath是什么路径，如果是文件路径父则删除该文件 如果是目录路径则删除该路径下的所有文件
	 * 
	 * @param storePath可以是文件也可以是目录路径
	 */
	public static void deleteFileOrPath(String storePath) {
		File fileorPath = new File(storePath);
		if (fileorPath.exists()) {
			if (fileorPath.isDirectory()) {
				File files[] = fileorPath.listFiles();
				for (int i = 0; i < files.length; i++) {
					files[i].delete();
				}
				fileorPath.delete();
				System.out.println("根据路径删除成功！");
			}else {
				if (fileorPath.getParentFile().isDirectory()) {
					File files[] = fileorPath.getParentFile().listFiles();
					for (int i = 0; i < files.length; i++) {
						files[i].delete();
					}
				}
				fileorPath.getParentFile().delete();
				System.out.println("根据文件寻找父级图片删除成功！");
			}
		}else{
			System.out.println("未有该路径存在");
		}
	}
	/**
	 * @param storePath
	 */
	public static void deleteThumbnail(String storePath) {
		File fileorPath = new File(storePath);
		if (fileorPath.exists()) {
			if (fileorPath.isFile()) {
				fileorPath.delete();
				System.out.println("缩略图删除成功！");
			}
		}else{
			System.out.println("未有该路径存在");
		}
	}
}
