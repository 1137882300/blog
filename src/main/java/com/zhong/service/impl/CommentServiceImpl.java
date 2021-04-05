package com.zhong.service.impl;

import com.google.common.collect.Lists;
import com.zhong.dao.CommentRepository;
import com.zhong.po.Comment;
import com.zhong.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by cc on 2021/4/3
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplyList = Lists.newArrayList();

    /**
     * 通过 blogId 查询所有的评论
     * @param blogId
     * @return
     */
    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "create_time");
        List<Comment> comments = commentRepository.findByBlog_IdAndParentCommentNull(blogId);

        return eachComment(comments);
    }

    /**
     * 循环每个顶级的评论节点
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments){
        List<Comment> commentsView = Lists.newArrayList();
        for (Comment comment : comments){
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);//避免数据在数据库里产生变化
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     * 拿到顶级节点的集合
     * @param comments
     */
    private void combineChildren(List<Comment> comments){
        for (Comment comment : comments){
            List<Comment> replyList = comment.getReplyComments();
            for (Comment re : replyList){
                //循环迭代，找出子代，存档在tempReplyList中
                recursively(re);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplyList);
            //清理临时存放区
            tempReplyList = Lists.newArrayList();
        }
    }

    /**
     * 递归迭代，就像剥洋葱
     * @param comment 被迭代的对象
     */
    private void recursively(Comment comment){
        //顶级节点添加到临时存放集合
        tempReplyList.add(comment);
        if (comment.getReplyComments().size() > 0){
            List<Comment> replyList = comment.getReplyComments();
            for (Comment re : replyList){
                tempReplyList.add(re);
                if (re.getReplyComments().size() > 0){
                    recursively(re);
                }
            }
        }
    }


    /**
     * 保存评论信息
     * @param comment
     * @return
     */
    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId != -1){
            comment.setParentComment(commentRepository.getOne(parentCommentId));
        } else {
            comment.setParentComment(null);//防止报错
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }




}
