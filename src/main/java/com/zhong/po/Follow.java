package com.zhong.po;

import javax.persistence.*;
import java.util.Date;

/**
 * 关注表
 * Created by cc on 2021/4/14
 */
@Entity(name = "t_follow")
public class Follow {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Long uid;//用户表

    private Long fid;//关注用户的id

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;//关注时间

}
