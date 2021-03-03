package zjmx.ssm.o2o.dao;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import zjmx.ssm.o2o.BaseTest;
import zjmx.ssm.o2o.entity.Comment;
import zjmx.ssm.o2o.entity.Shop;

public class CommentDaoTest extends BaseTest{

	@Autowired
	private CommentDao dao;
	@Test
	public void queryTest() {
		Comment shopAdmin=new Comment();
		
		Shop shop=new Shop();
		shop.setShopId(1L);
		shopAdmin.setShop(shop);
		System.out.println(dao.getComments(shopAdmin));
	}
	@Test
	public void updateTest() {
		Comment shopAdmin=new Comment();
		shopAdmin.setCommentId(1);
		dao.updateComment(shopAdmin);
	}
	@Test
	public void insertTest() {
		Comment commentCondition=new Comment();
		Shop shop=new Shop();
		shop.setShopId(1L);
		commentCondition.setShop(shop);
		commentCondition.setCommentContent("saaaaaaaaaaaaaaaaaaaaaaadaaaaaaaaaaaaaaaaaaaaaaaaa");
		commentCondition.setLove(0);
		commentCondition.setCreateTime(new Date());
		dao.insertComment(commentCondition);
	}
}
