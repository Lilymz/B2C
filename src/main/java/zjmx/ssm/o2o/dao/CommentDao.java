package zjmx.ssm.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import zjmx.ssm.o2o.entity.Comment;

public interface CommentDao {
	
	//查询评论
	List<Comment> getComments(@Param("commentCondition")Comment commentCondition);
	//增加评论
	int insertComment(Comment commentCondition);
	//更新点赞
	int updateComment(@Param("commentCondition")Comment commentCondition);
	//保留，不用
	int deleteComment(@Param("commentCondition")Comment commentCondition);
	
}
