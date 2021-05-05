package com.zhong.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by cc on 2021/5/3
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDataVO {

    private String nickname;

    private String work;//职位

    private String company;//公司

    private String hobby;// 个人介绍

    private String intro;//个人主页
}
