package zjmx.ssm.o2o.serviceimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zjmx.ssm.o2o.dao.ProductDao;
import zjmx.ssm.o2o.dao.ProductImgDao;
import zjmx.ssm.o2o.dto.ProductExecution;
import zjmx.ssm.o2o.entity.Product;
import zjmx.ssm.o2o.entity.ProductImage;
import zjmx.ssm.o2o.enums.ProductEnum;
import zjmx.ssm.o2o.service.ProductService;
import zjmx.ssm.o2o.utils.ImageHolder;
import zjmx.ssm.o2o.utils.ImageUtil;
import zjmx.ssm.o2o.utils.PageCalculator;
import zjmx.ssm.o2o.utils.PathUtil;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductDao dao;
	@Autowired
	private ProductImgDao imgdao;
	/**
	 *查询商店列表并分页，可输入的条件有商品名（模糊），商品状态，店铺Id，商品类别
	 *return ProductExecution  -> dto
	 */
	@Override
	public ProductExecution getProducts(Product productConditon, int beginIndex, int pageSize) {
		int rowIndex=PageCalculator.calculateRowIndex(beginIndex, pageSize);
		List<Product> products=dao.queryProductList(productConditon, rowIndex, pageSize);
		ProductExecution pe=null;
		if(products!=null) {
			pe=new ProductExecution(ProductEnum.PRODUCTQUERY_SUCCESS, products);
		}else {
			pe=new ProductExecution(ProductEnum.PRODUCTQUERY_ERROR);
		}
		return pe;
	}

	/**
	 *添加商品，默认商品类型归属于productTypeId类型
	 * @param product
	 * @return
	 */
	@Override
	@Transactional
	//1.缩略图的处理：获取缩略图相对路径并赋值给Product
	//2.往tb_product中插入信息，获取productId
	//3.通过productId处理商品详情图
	//4.把详情图添加到tb_productimage
	public ProductExecution insertProduct(Product product,ImageHolder thumbnail,List<ImageHolder> imagesList) {
		//空值判断
		ProductExecution pe=null;
		if(product!=null&&product.getShop()!=null&&product.getShop().getShopId()!=null) {
			//给商品实体类赋上默认属性
			product.setCreateTime(new Date());
			product.setLastEditTime(new Date());
			//默认上架时间
			product.setEnableStatus(1);
			//缩略图存在则添加
			if (thumbnail!=null) {
				addThumbnail(product,thumbnail,"ProductImage"+"/productId-"+product.getProductId());//1
			}
			try {
				//创建商品信息
				int effectedNum=dao.insertProduct(product);//2
				System.out.println(product.getProductId());
				if (effectedNum<=0) {
					pe=new ProductExecution(ProductEnum.PRODUCTINSERT_ERROR);
					throw new RuntimeException();
				}
			} catch (Exception e) {
				pe=new ProductExecution(ProductEnum.PRODUCTINSERT_ERROR);
				System.out.println(e.getMessage());
				throw new RuntimeException();
			}
			//若商品详情图不为空则添加 
			if(imagesList!=null&&imagesList.size()>0) {
				addProductImgList(product,imagesList,"ProductImageDetailed"+"/productId-"+product.getProductId());
			}
			pe=new ProductExecution(ProductEnum.PRODUCTINSERT_SUCCESS, product);
		}else {
			pe=new ProductExecution(ProductEnum.PRODUCTEMPTY);
			throw new RuntimeException();
		}
		return pe;
	}
	//处理商品详情图添加到tb_productimage
	private void addProductImgList(Product product, List<ImageHolder> imagesList,String Typename) {
		//获取商品的相对路径
		String dest=PathUtil.getShopImgPath(product.getShop().getShopId(),Typename);
		//把商品详情图添加到tb_productimage中
		List<ProductImage> listimages=new ArrayList<ProductImage>();
		for (ImageHolder productImageHolder : imagesList) {
			try {
				String imgAddr=ImageUtil.genernateThumbnail(productImageHolder, dest);
				ProductImage productImage=new ProductImage();
				productImage.setImgAddr(imgAddr);
				productImage.setProductId(product.getProductId());
				productImage.setCreateTime(new Date());
				listimages.add(productImage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		//如果确实有图片添加则执行批量添加操作
		if(listimages.size()>0) {
			try {
				int effectedNum=imgdao.batchInsertProductImg(listimages);
				if (effectedNum<=0) {
					System.err.println("创建图片详情图失败");
					throw new RuntimeException();
				}
			} catch (Exception e) {
				throw new RuntimeException("创建商品详情图失败:"+e.getMessage());
			}
		}
		
	}
	//处理上传图片缩略图
	private void addThumbnail(Product product, ImageHolder thumbnail,String Typename){
		//获取商品的相对路径
		String dest=PathUtil.getShopImgPath(product.getShop().getShopId(),Typename);
		//获取商品图片的本地地址
		try {
			String productImgAddr=ImageUtil.genernateThumbnail(thumbnail, dest);
			
			product.setImgAddr(productImgAddr.substring(6));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional
	public ProductExecution updateProduct(Product product, ImageHolder thumbnail, List<ImageHolder> imagesList) {
		ProductExecution pe=null;
		//非空判断
		if (product!=null&&product.getProductType()!=null&&product.getShop().getShopId()!=null) {
			//给商品实体类赋上默认属性
			product.setCreateTime(new Date());
			product.setLastEditTime(new Date());
			//缩略图存在则添加
			if (thumbnail!=null) {
				addThumbnail(product,thumbnail,"ProductImage"+"/productId-"+product.getProductId());//1
			}
			try {
				Product currentProduct=dao.queryProductById(product.getProductId());
				//1.更新商品之前删除已存在缩略图地址及图片
				if(currentProduct.getImgAddr()!=null) {
					ImageUtil.deleteThumbnail(currentProduct.getImgAddr());
					//2删除以该productId的productImage数据及图片
					List<ProductImage> list=new ArrayList<ProductImage>();
					list=imgdao.queryProductImageList(product.getProductId());
					ImageUtil.deleteFileOrPath(list.get(0).getImgAddr());
					System.out.println(imgdao.deleteProductImgByProductId(product.getProductId()));
				}
				int flag=dao.updateProduct(product);
				if (flag>0) {
					pe=new ProductExecution(ProductEnum.PRODUCTUPDATE_SUCCESS, 2);
					
				}else {
					pe=new ProductExecution(ProductEnum.PRODUCTUPDATE_ERROR);
					throw new RuntimeException();
				}
				if(imagesList!=null&&imagesList.size()>0) {
					addProductImgList(product,imagesList,"ProductImageDetailed"+"/productId-"+product.getProductId());
				}
			} catch (Exception e) {
				pe=new ProductExecution(ProductEnum.PRODUCTUPDATE_ERROR);
				System.err.println(e.getMessage());
				throw new RuntimeException();
			}
		}else {
			System.err.println("Product或者Shop或者ProductType为空！");
			throw new RuntimeException();
		}
		return pe;
	}

	@Override
	public ProductExecution deleteProduct(long productId) {
		int flag=dao.deleteProduct(productId);
		ProductExecution pe=null;
		if (flag>0) {
			pe=new ProductExecution(ProductEnum.PRODUCTDELETE_SUCCESS, 3);
		}else {
			pe=new ProductExecution(ProductEnum.PRODUCTDELETE_ERROR);
		}
		return pe;
	}

	@Override
	public ProductExecution queryProductById(Long ProductId) {
		Product product=dao.queryProductById(ProductId);
		ProductExecution pe=null;
		if(product!=null) {
			pe=new ProductExecution(ProductEnum.PRODUCTQUERY_SUCCESS, product);
		}else {
			pe =new ProductExecution(ProductEnum.PRODUCTQUERY_ERROR);
		}
		return pe;
	}

	@Override
	public int updateProductEnableStatusByProductId(Product product) {
		return dao.updateProductEnableStatusByProductId(product);
	}

}
