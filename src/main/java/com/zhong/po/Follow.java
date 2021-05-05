package com.zhong.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 关注表
 * Created by cc on 2021/4/14
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "t_follow")
public class Follow {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Long uid;//用户表

    private Long fid;//关注用户的id

    private int state;//点赞状态, 1:已点赞，2:未点赞

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;//关注时间

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;//关注时间
}
