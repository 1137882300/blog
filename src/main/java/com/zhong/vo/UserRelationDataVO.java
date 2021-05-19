package com.zhong.vo;

import com.zhong.po.Integral;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by cc on 2021/5/8
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRelationDataVO {

    private Integer blogTotal;

    private Integer follow;

    private Integer follower;

}
