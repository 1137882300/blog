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
     * 设置等级的图标
     */
    @Override
    public void setGradeIcon(Integral integral){
        if (integral == null){
            return;
        }
//        Long uid = integral.getUid();
        Long grade = integral.getGrade();
        int value = grade.intValue();
        if (value < 100 && value >= 0){
            //Lv1
            integral.setImg("data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyMyIgaGVpZ2h0PSIxNCIgdmlld0JveD0iMCAwIDIzIDE0Ij4KICAgIDxnIGZpbGw9Im5vbmUiIGZpbGwtcnVsZT0iZXZlbm9kZCI+CiAgICAgICAgPHBhdGggZmlsbD0iIzhDREJGNCIgZD0iTTMgMWgxN2EyIDIgMCAwIDEgMiAydjhhMiAyIDAgMCAxLTIgMkgzYTIgMiAwIDAgMS0yLTJWM2EyIDIgMCAwIDEgMi0yeiIvPgogICAgICAgIDxwYXRoIGZpbGw9IiNGRkYiIGQ9Ik0zIDRoMnY3SDN6TTggNmgybDIgNWgtMnoiLz4KICAgICAgICA8cGF0aCBmaWxsPSIjRkZGIiBkPSJNMTQgNmgtMmwtMiA1aDJ6TTMgOWg1djJIM3pNMTYgNGwxLTF2MmgtMXpNMTcgM2gydjJoLTJ6TTE3IDVoMnY2aC0yeiIvPgogICAgPC9nPgo8L3N2Zz4K");
        } else if (value > 101 && value <= 500){
             //"Lv2";
            integral.setImg("data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyMyIgaGVpZ2h0PSIxNCIgdmlld0JveD0iMCAwIDIzIDE0Ij4KICAgIDxnIGZpbGw9Im5vbmUiIGZpbGwtcnVsZT0iZXZlbm9kZCI+CiAgICAgICAgPHBhdGggZmlsbD0iIzZFQ0VGRiIgZD0iTTMgMWgxN2EyIDIgMCAwIDEgMiAydjhhMiAyIDAgMCAxLTIgMkgzYTIgMiAwIDAgMS0yLTJWM2EyIDIgMCAwIDEgMi0yeiIvPgogICAgICAgIDxwYXRoIGZpbGw9IiNGRkYiIGQ9Ik0zIDRoMnY3SDN6TTggNmgybDIgNWgtMnoiLz4KICAgICAgICA8cGF0aCBmaWxsPSIjRkZGIiBkPSJNMTQgNmgtMmwtMiA1aDJ6TTMgOWg1djJIM3pNMTUgM2g1djJoLTV6TTE4IDVoMnYyaC0yek0xNSA5VjdoMnYyeiIvPgogICAgICAgIDxwYXRoIGZpbGw9IiNGRkYiIGQ9Ik0xNSA4VjZoNXYyek0xNSA5aDV2MmgtNXoiLz4KICAgIDwvZz4KPC9zdmc+Cg==");
        } else if (value > 501 && value <= 1500){
           //  "Lv3";
            integral.setImg("data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyMyIgaGVpZ2h0PSIxNCIgdmlld0JveD0iMCAwIDIzIDE0Ij4KICAgIDxnIGZpbGw9Im5vbmUiIGZpbGwtcnVsZT0iZXZlbm9kZCI+CiAgICAgICAgPHBhdGggZmlsbD0iIzU5OURGRiIgZD0iTTMgMWgxN2EyIDIgMCAwIDEgMiAydjhhMiAyIDAgMCAxLTIgMkgzYTIgMiAwIDAgMS0yLTJWM2EyIDIgMCAwIDEgMi0yeiIvPgogICAgICAgIDxwYXRoIGZpbGw9IiNGRkYiIGQ9Ik0zIDRoMnY3SDN6TTggNmgybDIgNWgtMnoiLz4KICAgICAgICA8cGF0aCBmaWxsPSIjRkZGIiBkPSJNMTQgNmgtMmwtMiA1aDJ6TTMgOWg1djJIM3pNMTUgM2g1djJoLTV6TTE4IDVoMnYxaC0yek0xOCA4aDJ2MWgtMnpNMTYgNmg0djJoLTR6TTE1IDloNXYyaC01eiIvPgogICAgPC9nPgo8L3N2Zz4K");
        } else if (value > 1501 && value <= 3000){
          //   "Lv4";
            integral.setImg("data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyMyIgaGVpZ2h0PSIxNCIgdmlld0JveD0iMCAwIDIzIDE0Ij4KICAgIDxnIGZpbGw9Im5vbmUiIGZpbGwtcnVsZT0iZXZlbm9kZCI+CiAgICAgICAgPHBhdGggZmlsbD0iIzM0RDE5QiIgZD0iTTMgMWgxN2EyIDIgMCAwIDEgMiAydjhhMiAyIDAgMCAxLTIgMkgzYTIgMiAwIDAgMS0yLTJWM2EyIDIgMCAwIDEgMi0yeiIvPgogICAgICAgIDxwYXRoIGZpbGw9IiNGRkYiIGQ9Ik0zIDRoMnY3SDN6TTggNmgybDIgNWgtMnoiLz4KICAgICAgICA8cGF0aCBmaWxsPSIjRkZGIiBkPSJNMTQgNmgtMmwtMiA1aDJ6TTMgOWg1djJIM3pNMTYuMzMzIDRMMTcgM3YzaC0yek0xNSA2aDJ2NGgtMnpNMTcgOGgxdjJoLTF6TTE3IDNoMXYyaC0xek0xOCAzaDJ2OGgtMnoiLz4KICAgIDwvZz4KPC9zdmc+Cg==");
        } else if (value > 3001 && value <= 5000){
           //  "Lv5";
            integral.setImg("data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyMyIgaGVpZ2h0PSIxNCIgdmlld0JveD0iMCAwIDIzIDE0Ij4KICAgIDxnIGZpbGw9Im5vbmUiIGZpbGwtcnVsZT0iZXZlbm9kZCI+CiAgICAgICAgPHBhdGggZmlsbD0iI0ZGQTAwMCIgZD0iTTMgMWgxN2EyIDIgMCAwIDEgMiAydjhhMiAyIDAgMCAxLTIgMkgzYTIgMiAwIDAgMS0yLTJWM2EyIDIgMCAwIDEgMi0yeiIvPgogICAgICAgIDxwYXRoIGZpbGw9IiNGRkYiIGQ9Ik0zIDRoMnY3SDN6TTggNmgybDIgNWgtMnoiLz4KICAgICAgICA8cGF0aCBmaWxsPSIjRkZGIiBkPSJNMTQgNmgtMmwtMiA1aDJ6TTMgOWg1djJIM3pNMTggOGgydjNoLTJ6TTE1IDNoNXYyaC01ek0xNSA5aDN2MmgtM3pNMTYgNmgzdjJoLTN6Ii8+CiAgICAgICAgPHBhdGggZmlsbD0iI0ZGRiIgZD0iTTE4IDZoMnYyaC0yek0xNSA1aDJ2M2gtMnoiLz4KICAgIDwvZz4KPC9zdmc+Cg==");
        } else if (value > 5001 && value <= 10000){
          //   "Lv6";
            integral.setImg("data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyMyIgaGVpZ2h0PSIxNCIgdmlld0JveD0iMCAwIDIzIDE0Ij4KICAgIDxnIGZpbGw9Im5vbmUiIGZpbGwtcnVsZT0iZXZlbm9kZCI+CiAgICAgICAgPHBhdGggZmlsbD0iI0YzNjI2MiIgZD0iTTMgMWgxN2EyIDIgMCAwIDEgMiAydjhhMiAyIDAgMCAxLTIgMkgzYTIgMiAwIDAgMS0yLTJWM2EyIDIgMCAwIDEgMi0yeiIvPgogICAgICAgIDxwYXRoIGZpbGw9IiNGRkYiIGQ9Ik0zIDRoMnY3SDN6TTggNmgybDIgNWgtMnoiLz4KICAgICAgICA8cGF0aCBmaWxsPSIjRkZGIiBkPSJNMTQgNmgtMmwtMiA1aDJ6TTMgOWg1djJIM3pNMTggN2gydjRoLTJ6TTE3IDNoMnYyaC0yek0xNSA1aDJ2NmgtMnoiLz4KICAgICAgICA8cGF0aCBmaWxsPSIjRkZGIiBkPSJNMTcgNmgzdjJoLTN6TTE2LjMzMyAzLjY2N0wxNyAzdjJoLTJ6TTE3IDloMnYyaC0yeiIvPgogICAgPC9nPgo8L3N2Zz4K");
        } else {
          //   "Lv7";
            integral.setImg("data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyMyIgaGVpZ2h0PSIxNCIgdmlld0JveD0iMCAwIDIzIDE0Ij4KICAgIDxnIGZpbGw9Im5vbmUiIGZpbGwtcnVsZT0iZXZlbm9kZCI+CiAgICAgICAgPHBhdGggZmlsbD0iI0VGNzdDRSIgZD0iTTMgMWgxN2EyIDIgMCAwIDEgMiAydjhhMiAyIDAgMCAxLTIgMkgzYTIgMiAwIDAgMS0yLTJWM2EyIDIgMCAwIDEgMi0yeiIvPgogICAgICAgIDxwYXRoIGZpbGw9IiNGRkYiIGQ9Ik0zIDRoMnY3SDN6TTggNmgybDIgNWgtMnoiLz4KICAgICAgICA8cGF0aCBmaWxsPSIjRkZGIiBkPSJNMTQgNmgtMmwtMiA1aDJ6TTMgOWg1djJIM3pNMTggNWgybC0yIDZoLTJ6TTE1IDNoNXYyaC01eiIvPgogICAgPC9nPgo8L3N2Zz4K");
        }
        integralRepository.save(integral);
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
            log.info("");
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
