package com.zhong.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 点赞表
 * Created by cc on 2021/4/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "t_thumbs_up")
public class ThumbsUp {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Long uid; //用户id

    private Long totalNumber;//点赞数

    private Integer status;//点赞状态，1—有效赞，0-取消赞

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;



}
