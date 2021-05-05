package com.zhong.service.impl;

import com.zhong.dao.IntegralRepository;
import com.zhong.exception.BizException;
import com.zhong.po.Integral;
import com.zhong.service.IntegralService;
import com.zhong.vo.SignInDataVO;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.zhong.timer.DayInterval.getCurrentTime2EarlyMorning;

/**
 * Created by cc on 2021/4/24
 */

@Slf4j
@Service
public class IntegralServiceImpl implements IntegralService {

    @Autowired
    private IntegralRepository integralRepository;

    /**
     * 根据 user的 id 查 等级
     * @param id
     * @return
     */
    @Override
    public Integral findIntegral(Long id) {
        Integral integral = integralRepository.findIntegralById(id);
        return integral;
    }

    /**
     * 新增 积分类型
     * @param integral
     * @return
     */
    @Override
    public Long saveIntegral(Integral integral) {
        Integral integral1 = integralRepository.save(integral);
        return integral1.getId();
    }

    /**
     * 等级转换 Lvxxxxx
     * 等级表
     * ------------------------------
     * Lv1  /   0    ~ 100
     * Lv2  /   101  ~ 500
     * Lv3  /   501  ~ 1500
     * Lv4  /   1501 ~ 3000
     * Lv5  /   3001 ~ 5000
     * Lv6  /   5001 ~ 10000
     * Lv7  /   10000~ 无穷大
     * ------------------------------
     * @param grade
     */
    @Override
    public String convertGrade(Long grade) {
        int value = grade.intValue();
        String thisGrade = new String();
        if (value < 100 && value >= 0){
            thisGrade = "Lv1";
        } else if (value > 101 && value <= 500){
            thisGrade = "Lv2";
        } else if (value > 501 && value <= 1500){
            thisGrade = "Lv3";
        } else if (value > 1501 && value <= 3000){
            thisGrade = "Lv4";
        } else if (value > 3001 && value <= 5000){
            thisGrade = "Lv5";
        } else if (value > 5001 && value <= 10000){
            thisGrade = "Lv6";
        } else {
            thisGrade = "Lv7";
        }
        return thisGrade;
    }

    /**
     * 根据uid， 等级和积分 +20
     * @param uid
     * @return
     */
    @Override
    public SignInDataVO updateGradeAndIntegralByUid(Long uid) {
        int i = integralRepository.updateByUid(20,50,uid);
        if (i < 0){
            new BizException();
        }
        //签到的此刻时间-到第二天的时间=时间间隔,间隔time才能再次签到
        Long time = getCurrentTime2EarlyMorning(System.currentTimeMillis());
        Integral integral = integralRepository.findIntegralById(uid);
        log.info("integral->{}",integral);
        SignInDataVO signInDataVO = new SignInDataVO();
        signInDataVO.setTime(time);
        signInDataVO.setGrade(integral.getGrade());
        signInDataVO.setIntegral(integral.getIntegral());
        log.info("signInDataVO->{}",signInDataVO);
        return signInDataVO;
    }



}
