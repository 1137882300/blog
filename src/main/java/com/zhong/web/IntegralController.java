package com.zhong.web;

import com.alibaba.fastjson.JSONObject;
import com.zhong.po.Integral;
import com.zhong.po.User;
import com.zhong.result.JsonResult;
import com.zhong.service.IntegralService;
import com.zhong.vo.SignInDataVO;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by cc on 2021/4/24
 */
@Slf4j
@Controller
public class IntegralController {

    @Autowired
    private IntegralService integralService;

    /**
     * 局部刷新 integral_grade
     * 显示 积分
     * @param
     * @return
     */
    @GetMapping("/integral/integral_grade")
    public String integralGrade(HttpSession session, Model model){
        Integral integral = getIntegral(session);
        if (integral != null){
            model.addAttribute("integralGrade",integral.getIntegral());
        }
        return "_fragments :: integral_grade";
    }

    /**
     * 局部刷新 grade_integral
     * 显示 等级
     * @param session
     * @param model
     * @return
     */
    @GetMapping("/integral/grade_integral")
    public String gradeIntegral(HttpSession session, Model model){
        Integral integral = getIntegral(session);
        if (integral != null){
            Long grade = integral.getGrade();
            //转换等级 为 Lv xxxxx
            String convertGrade = integralService.convertGrade(grade);
            integralService.setGradeIcon(integral);
            model.addAttribute("gradeIntegral",convertGrade);
        }
        return "_fragments :: grade_integral";
    }

    /**
     * 公共的方法
     * @param session
     * @return
     */
    private Integral getIntegral(HttpSession session){
        User user = (User) session.getAttribute("user");
        Integral integral = integralService.findIntegral(user.getId());
        log.info("integral -> {}", integral);
        if (integral != null){
            return integral;
        }
        return null;
    }


    @ResponseBody
    @GetMapping("/integral/signIn")
    public JsonResult<SignInDataVO> signIn(HttpSession session){
        Integral integral = getIntegral(session);
        if (integral != null){
            Long uid = integral.getUid();
            SignInDataVO signInDataVO = integralService.updateGradeAndIntegralByUid(uid);
            log.info("signInDataVO->{}",signInDataVO);
            return JsonResult.success(signInDataVO);
        }
        return JsonResult.success();
    }




}
