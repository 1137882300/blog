package com.zhong.service.impl;

import com.google.common.collect.Lists;
import com.zhong.dao.CommentRepository;
import com.zhong.dao.UserRepository;
import com.zhong.po.Comment;
import com.zhong.po.User;
import com.zhong.service.CommentService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplyList = Lists.newArrayList();

    /**
     * 通过 blogId 查询所有的评论
     * @param blogId
     * @return
     */
    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
//        Sort sort = Sort.by(Sort.Direction.DESC, "create_time");
        List<Comment> comments = commentRepository.findByBlog_IdAndParentCommentNull(blogId);//降序

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
    @Deprecated
    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId != -1){
            //拿到父级的对象，放入父级的comment里
            comment.setParentComment(commentRepository.getOne(parentCommentId));
        } else {
            comment.setParentComment(null);//防止报错
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

    /**
     * 保存评论
     * @param comment
     * @return
     */
    @Transactional
    @Override
    public Comment addComment(Comment comment, Long flag) {
        //进来的一个评论实体
        log.info("comment ---> {}",comment);
        Long commentUid = comment.getCommentUid();//评论人的id
        Long parentCommentUid = comment.getParentCommentUid();//作者的id
        //从是否有父级开始 组装数据
        if (flag != -1){
            //说明是回复的，有父级
            User user = userRepository.getOne(commentUid);//获取评论人的基本信息
            comment.setAvatar(user.getAvatar());
            comment.setNickname(user.getNickname());
            comment.setParentCommentUid(parentCommentUid);
            comment.setCreateTime(new Date());
            System.out.println("-------->"+flag);
            comment.setParentComment(commentRepository.getOne(flag));
            comment.getParentComment().setId(flag);
        } else {
            //说明是评论的，没有父级
            User user = userRepository.getOne(commentUid);//获取评论人的基本信息
            comment.setAvatar(user.getAvatar());
            comment.setNickname(user.getNickname());
            comment.setParentCommentUid(parentCommentUid);
            comment.setCreateTime(new Date());
            comment.setParentComment(null);
        }
        return commentRepository.save(comment);
    }


}
