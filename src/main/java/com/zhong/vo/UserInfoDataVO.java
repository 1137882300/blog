package com.zhong.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by cc on 2021/5/4
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDataVO {

    private int follow;//关注了

    private int follower; //关注者

    private int thumbs;//点赞数

    private int views;//文章被阅读

    private Long integral;//积分值

    private String img;//等级图标



}
