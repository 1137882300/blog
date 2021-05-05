package com.zhong.web;

import com.fasterxml.jackson.databind.deser.BuilderBasedDeserializer;
import com.zhong.exception.BizException;
import com.zhong.po.Blog;
import com.zhong.po.Integral;
import com.zhong.po.User;
import com.zhong.po.UserInfo;
import com.zhong.service.*;
import com.zhong.request.RegisterForm;
import com.zhong.vo.UserDataVO;
import com.zhong.vo.UserInfoDataVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by cc on 2021/4/5
 */
@Controller
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private IntegralService integralService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private FollowService followService;


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


    @GetMapping("/user/profile")
    public String profile(@PageableDefault(size = 50,sort = {"updateTime"},direction = Sort.Direction.DESC)
                                      Pageable pageable, Model model){
        model.addAttribute("page",blogService.listBlog(pageable));
        return "profile";
    }

    /**
     * 根据 uid
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/user/profile/{id}")
    public String profile(@PathVariable("id") Long id, Model model){
        User user = userService.findUserById(id);
        model.addAttribute("user",user); //用户信息

        UserInfoDataVO userInfoDataVO =  userService.getProfileData(id);
        model.addAttribute("userInfoDataVO", userInfoDataVO);

        //博客信息
        List<Blog> blogList = blogService.getBlogByUid(id);
        model.addAttribute("blogList",blogList);

        return "profile";
    }



    @GetMapping("/user/settings")
    public String settings(HttpSession session,Model model){
        User user = (User) session.getAttribute("user");
        UserInfo userInfo = userInfoService.findByUid(user.getId());
        if (userInfo != null){
            UserDataVO userDataVO = UserDataVO.builder()
                    .nickname(user.getNickname())
                    .work(userInfo.getWork() != null ? userInfo.getWork() : "")
                    .company(userInfo.getCompany() != null ? userInfo.getCompany() : "")
                    .hobby(userInfo.getHobby() != null ? userInfo.getHobby() : "")
                    .intro(userInfo.getIntro() != null ? userInfo.getIntro() : "")
                    .build();
            model.addAttribute("userDataVO", userDataVO);
        } else {
            UserDataVO userDataVO = UserDataVO.builder()
                    .nickname(user.getNickname())
                    .work("")
                    .company("")
                    .hobby("")
                    .intro("")
                    .build();
            model.addAttribute("userDataVO", userDataVO);
        }
        return "settings";
    }


    @PostMapping("/user/avatar/upload")
    public String upload(@RequestParam(value = "file") MultipartFile file, HttpSession session){
        if (file.isEmpty()) {
            System.out.println("文件为空 ");
        }
        String originalFilename = file.getOriginalFilename();
        String suffixFilename = originalFilename.substring(originalFilename.lastIndexOf("."));
        String prefixFilename = originalFilename.substring(0,originalFilename.indexOf("."));
        //上传后的路径
        String filePath = "D:\\ideaProject\\blog\\src\\main\\resources\\static\\uploads\\";
        String newFilename = prefixFilename + "_" + UUID.randomUUID() + suffixFilename;
        File dest = new File(filePath + newFilename);
        if (!dest.getParentFile().exists()){
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String filename = "/static/uploads/" + newFilename;
        System.out.println(" -> "+filename);

        User user = (User) session.getAttribute("user");
        userService.updateByUid(user.getId(),filename);
        return "settings :: avatar";
    }


    @GetMapping("/user/update/nickname")
    public String nickname(HttpSession session,@Param("nickname") String nickname){
        User user = (User) session.getAttribute("user");
        user.setNickname(nickname);
        User u = userService.saveOrUpdateUser(user);
        log.info("user -> {}",u);
        return "settings :: nickname";
    }


}
