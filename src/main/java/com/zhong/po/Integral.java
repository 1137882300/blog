package com.zhong.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 积分等级
 * Created by cc on 2021/4/14
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "t_integral")
public class Integral {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Long uid;//用户id

    private Long integral;//积分数量，积分可以干什么？

    private Long grade;//等级，到达100 就升级

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;//积分时间

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;//更新时间

}
