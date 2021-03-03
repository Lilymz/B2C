package zjmx.ssm.o2o.service;

import java.util.List;

import zjmx.ssm.o2o.entity.Comment;

public interface CommentService {
	//查询评论
	List<Comment> getCommentsService(Comment commentCondition);
	//增加评论
	int insertCommentService(Comment commentCondition);
	//更新点赞
	int updateCommentService(Comment commentCondition);
	//保留，不用
	int deleteCommentService(Comment commentCondition);
}
