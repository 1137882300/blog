package com.zhong.po;

import lombok.Builder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by cc on 2021/3/14
 */
@Entity(name = "t_comment")
public class Comment {

    @Id//主键id
    @GeneratedValue//生成策略
    private Long id;
    private String nickname;
    private String email;
    private String content;
    private String avatar;
    @Temporal(TemporalType.TIMESTAMP) //关于time的时候，对应到数据库里面需要这个注解
    private Date createTime;

    @ManyToOne
    private Blog blog;

    /*自连接关系,一个父，多个子*/
    @OneToMany(mappedBy = "parentComment")
    private List<Comment> replyComments = new ArrayList<>();
    @ManyToOne
    private Comment parentComment;

    private Long parentCommentUid;//该作者的id

    private Long commentUid;//评论人的id

    public Comment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public List<Comment> getReplyComments() {
        return replyComments;
    }

    public void setReplyComments(List<Comment> replyComments) {
        this.replyComments = replyComments;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public Long getCommentUid() {
        return commentUid;
    }

    public void setCommentUid(Long commentUid) {
        this.commentUid = commentUid;
    }

    public Long getParentCommentUid() {
        return parentCommentUid;
    }

    public void setParentCommentUid(Long parentCommentUid) {
        this.parentCommentUid = parentCommentUid;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createTime=" + createTime +
                ", parentCommentUid=" + parentCommentUid +
                ", commentUid=" + commentUid +
                '}';
    }
}
