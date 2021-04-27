package com.zhong.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Created by cc on 2021/4/26
 */
@Data
@Builder(toBuilder = true)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class SignInDataVO {


    private Long time;//时间间隔

    private Long integral;//积分数量

    private Long grade;//等级


}
