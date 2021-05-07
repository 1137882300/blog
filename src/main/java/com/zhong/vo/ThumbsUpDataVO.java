package com.zhong.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by cc on 2021/5/5
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ThumbsUpDataVO {

    private int num;//赞的数量



    private int total;//赞的总数


}
