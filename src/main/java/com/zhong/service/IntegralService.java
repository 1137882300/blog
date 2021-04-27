package com.zhong.service;

import com.zhong.po.Integral;
import com.zhong.vo.SignInDataVO;
import javafx.util.Pair;

/**
 * Created by cc on 2021/4/24
 */
public interface IntegralService {

    Integral findIntegral(Long id);

    Long saveIntegral(Integral integral);

    String convertGrade(Long grade);

    SignInDataVO updateGradeAndIntegralByUid(Long uid);
}
