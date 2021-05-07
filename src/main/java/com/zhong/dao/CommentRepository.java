package com.zhong.dao;

import com.zhong.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

/**
 * Created by cc on 2021/4/3
 */

public interface CommentRepository extends JpaRepository<Comment,Long>, JpaSpecificationExecutor<Comment> {

    //涉及到删除和修改在需要加上 @Modifying
    // 多表查询的时候 ，要加上 _ 下划线

//    List<Comment> getByBlog_IdAndParentCommentNull(Long blogId, Sort sort);

//    List<Comment> findByBlog_IdStartsWithAndParentCommentNull(Long blogId, Sort sort);

//    List<Comment> findByParentCommentNullAndBlogStartingWithAndBlogId(Long blogId, Sort sort);

// No property create found for type Comment!
//    List<Comment> findByBlog_IdStartingWithAndParentCommentNull(Long blogId, Sort sort);

    @Query(nativeQuery = true, value = "select * from t_comment where blog_id = :blogId and isnull(parent_comment_id) order by create_time asc")
    List<Comment> findByBlog_IdAndParentCommentNull(@Param("blogId") Long blogId);
}
    /*
    @Query("select u from User u where u.firstname = :firstname or u.lastname = :lastname")
    User findByLastnameOrFirstname(@Param("lastname") String lastname,
                                 @Param("firstname") String firstname);
     */
