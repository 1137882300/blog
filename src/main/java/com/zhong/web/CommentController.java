package com.zhong.web;

import com.zhong.po.Blog;
import com.zhong.po.Comment;
import com.zhong.request.CommentRequest;
import com.zhong.service.BlogService;
import com.zhong.service.CommentService;
import com.zhong.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by cc on 2021/4/3
 */
@Slf4j
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;


    @Value("${comment.avatar}")
    private String avatar;

    /**
     * 返回评论信息到页面上
     * @param blogId
     * @param model
     * @return
     */
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        model.addAttribute("comments",commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";//局部刷新
    }

    /**
     * 页面提交评论
     * @param comment
     * @return
     */
    @Deprecated
    @PostMapping("/comments")
    public String post(Comment comment){
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        comment.setAvatar(avatar);
        System.out.println(comment);
        commentService.saveComment(comment);
        log.info("comment => {}",comment);
        return "redirect:/comments/" + blogId;
    }

    /**
     * 评论的提交
     * @param request
     * @return
     */
    @PostMapping("comment/publish")
    public String commentPublish(CommentRequest request){
        log.info("CommentRequest: {} ", request);

        Long blogId = request.getBlogId();//博客
        Long commentUid = request.getCommentUid();//评论人
        Long parentCommentUid = request.getParentCommentUid();//作者
        String content = request.getContent();//评语
        Long flag = request.getFlag();//flag标识顶级/子级

        Comment comment = new Comment();
        comment.setBlog(blogService.getBlog(blogId));
        comment.setCommentUid(commentUid);//评论人
        comment.setContent(content);
        comment.setParentCommentUid(parentCommentUid);//作者

        commentService.addComment(comment, flag);

        return "redirect:/comments/" + blogId;
    }

}
