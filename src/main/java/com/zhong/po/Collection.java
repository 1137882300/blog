package com.zhong.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * 收藏表
 * Created by cc on 2021/4/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "t_collection")
public class Collection {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Long uid;//用户id

    private Long blogId;//博客id

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;//收藏时间



}
