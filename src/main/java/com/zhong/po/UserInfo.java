package com.zhong.po;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "t_user_info")
public class UserInfo implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//mysql自动生成
    private Long id;

    private String uid;//用户id

    private String nickname;

    private String realname;//真实姓名

    private String qq;

    private String wechat;

    private String email;

    private String phone;

    private String work;//职位

    private String address;

    private String hobby;// 个人介绍

    private String intro;//个人主页

    private String company;//公司

}
