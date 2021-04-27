package com.zhong.web;

import com.zhong.po.Integral;
import com.zhong.po.User;
import com.zhong.service.IntegralService;
import com.zhong.service.UserService;
import com.zhong.request.RegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Random;

/**
 * Created by cc on 2021/4/5
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private IntegralService integralService;

    /**
     * 跳转用户登录
     * @return
     */
    @GetMapping("/user/toLogin")
    public String toLogin(){
        return "login-user";
    }

    /**
     * 登录
     * @param username
     * @param password
     * @param session
     * @param attributes
     * @return
     */
    @PostMapping("/user/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){
        User user = userService.checkUser(username,password);
        if (user != null){
            user.setPassword(null);
            session.setAttribute("user",user);
            return "redirect:/";//跳转到首页
        }else {
            attributes.addFlashAttribute("message","用户名或密码错误");//这是是重定向，所以不能用model
            return "redirect:/user/toLogin";
        }
    }

    /**
     * 跳转注册
     * @return
     */
    @GetMapping("/user/register")
    public String toRegister(){
        return "register";
    }


    @PostMapping("/user/register")
    public String register(RegisterForm registerForm, Model model){
        // 表单密码重复判断
        if (!registerForm.getPassword().equals(registerForm.getRepassword())){
            model.addAttribute("message","两次密码不一致");
            return "register";
        }
        //判断用户是否已存在
        boolean boo = userService.checkUser(registerForm.getUsername());
        if (boo){
            model.addAttribute("message","用户已存在");
            return "register";
        }

        //构建用户对象
        User user = new User();
        user.setUsername(registerForm.getUsername());
        user.setPassword(registerForm.getPassword());
        user.setAvatar("https://picsum.photos/id/" + new Random().nextInt(1000) + "/800/400");
        user.setCreateTime(new Date());
        User saveUser = userService.saveUser(user);
        //构建积分对象
        Integral integral = new Integral()
                .toBuilder()
                .uid(saveUser.getId())
                .grade(20L)
                .integral(20L)
                .createTime(new Date())
                .build();
        integralService.saveIntegral(integral);
        // 注册成功，重定向到登录页面
        return "redirect:/user/toLogin";
    }

    /**
     * 注销
     * @param session
     * @return
     */
    @GetMapping("/user/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/index";
    }



}
