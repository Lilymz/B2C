package zjmx.ssm.o2o.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zjmx.ssm.o2o.dao.CommentDao;
import zjmx.ssm.o2o.entity.Comment;
import zjmx.ssm.o2o.service.CommentService;

@Service
public class ShopAdminServiceImpl implements CommentService{
	@Autowired
	private CommentDao commentDao;
	@Override
	public List<Comment> getCommentsService(Comment commentCondition) {
		return commentDao.getComments(commentCondition);
	}

	@Override
	public int insertCommentService(Comment commentCondition) {
		return commentDao.insertComment(commentCondition);
	}

	@Override
	public int updateCommentService(Comment commentCondition) {
		// TODO Auto-generated method stub
		return commentDao.updateComment(commentCondition);
	}

	@Override
	public int deleteCommentService(Comment commentCondition) {
		return commentDao.deleteComment(commentCondition);
	}
}
