package com.zhong.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created by cc on 2021/4/19
 */
@Data
@Accessors(chain = true)
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest implements Serializable {

    private Long blogId;//文章的id
    private Long parentCommentUid;//文章作者的id, 这里是blog.user的id
    private Long commentUid;//评论人id, 这个是session的user的id
    private String content;//评论的内容
    private Long flag;//标记父级还是子级

}
