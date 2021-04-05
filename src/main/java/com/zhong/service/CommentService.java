package com.zhong.service;

import com.zhong.po.Comment;

import java.util.List;

/**
 * Created by cc on 2021/4/3
 */
public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);


    Comment saveComment(Comment comment);

}
