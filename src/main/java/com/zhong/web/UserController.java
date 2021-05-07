package com.zhong.web;

import com.fasterxml.jackson.databind.deser.BuilderBasedDeserializer;
import com.zhong.exception.BizException;
import com.zhong.po.*;
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

    private final String REDIRECT = "redirect:/user/settings";

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
            user.setPassword(password);
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
                .img("data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyMyIgaGVpZ2h0PSIxNCIgdmlld0JveD0iMCAwIDIzIDE0Ij4KICAgIDxnIGZpbGw9Im5vbmUiIGZpbGwtcnVsZT0iZXZlbm9kZCI+CiAgICAgICAgPHBhdGggZmlsbD0iIzhDREJGNCIgZD0iTTMgMWgxN2EyIDIgMCAwIDEgMiAydjhhMiAyIDAgMCAxLTIgMkgzYTIgMiAwIDAgMS0yLTJWM2EyIDIgMCAwIDEgMi0yeiIvPgogICAgICAgIDxwYXRoIGZpbGw9IiNGRkYiIGQ9Ik0zIDRoMnY3SDN6TTggNmgybDIgNWgtMnoiLz4KICAgICAgICA8cGF0aCBmaWxsPSIjRkZGIiBkPSJNMTQgNmgtMmwtMiA1aDJ6TTMgOWg1djJIM3pNMTYgNGwxLTF2MmgtMXpNMTcgM2gydjJoLTJ6TTE3IDVoMnY2aC0yeiIvPgogICAgPC9nPgo8L3N2Zz4K")
                .createTime(new Date())
                .build();
        integralService.saveIntegral(integral);
        //构建用户信息对象
        UserInfo userInfo = UserInfo.builder()
                .uid(saveUser.getId())
                .build();
        userInfoService.saveUserInfo(userInfo);
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
    public String profile(@PathVariable("id") Long id,HttpSession session , Model model){
        User user = userService.findUserById(id);
        model.addAttribute("user",user);
        //用户信息
        UserInfoDataVO userInfoDataVO =  userService.getProfileData(id);
        model.addAttribute("userInfoDataVO", userInfoDataVO);

        //博客信息
        List<Blog> blogList = blogService.getBlogByUid(id);
        model.addAttribute("blogList",blogList);

        //关注
        User u = (User) session.getAttribute("user");
        Follow follow = followService.queryByUidAndFid(u.getId(), id);
        if (follow == null){
            model.addAttribute("state",0);
        } else {
            model.addAttribute("state", follow.getState());
        }
        return "profile";
    }



    @GetMapping("/user/settings")
    public String settings(HttpSession session,Model model){
        User user = (User) session.getAttribute("user");
        UserInfo userInfo = userInfoService.findByUid(user.getId());
        if (userInfo != null){
            UserDataVO userDataVO = UserDataVO.builder()
                    .avatar(userInfo.getAvatar())
                    .nickname(user.getNickname())
                    .work(userInfo.getWork() != null ? userInfo.getWork() : "")
                    .company(userInfo.getCompany() != null ? userInfo.getCompany() : "")
                    .hobby(userInfo.getHobby() != null ? userInfo.getHobby() : "")
                    .intro(userInfo.getIntro() != null ? userInfo.getIntro() : "")
                    .build();
            log.warn("userDataVO -> {}",userDataVO);
            model.addAttribute("userDataVO", userDataVO);
        } else {
            UserDataVO userDataVO = UserDataVO.builder()
                    .avatar(user.getAvatar())
                    .nickname(user.getNickname())
                    .work("")
                    .company("")
                    .hobby("")
                    .intro("")
                    .build();
            log.warn("userDataVO -> {}",userDataVO);
            model.addAttribute("userDataVO", userDataVO);
        }
        return "settings";
    }

    /**
     * 上传图片
     * @param file
     * @param session
     * @return
     */
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
        userInfoService.updateByUid(user.getId(),filename);
        return "redirect:/user/settings";
    }

    /**
     * 修改 nickname
     * @param session
     * @param nickname
     * @return
     */
    @PostMapping("/user/update/nickname")
    public String nickname(HttpSession session,@Param("nickname") String nickname){
        log.warn("nickname ->{}",nickname);
        User user = (User) session.getAttribute("user");
        user.setNickname(nickname);
        User u = userService.saveOrUpdateUser(user);
        userInfoService.saveOrUpdate(user.getId(),nickname);
        log.info("user -> {}",u);
        return REDIRECT;
    }



    /**
     * 修改 work
     * @param session
     * @param work
     * @return
     */
    @PostMapping("/user/update/work")
    public String work(HttpSession session,@Param("work") String work){
        log.info("work -> {}",work);
        User user = (User) session.getAttribute("user");
        userInfoService.saveOrUpdateWork(user.getId(),work);
        return REDIRECT;
    }

    @PostMapping("/user/update/company")
    public String company(HttpSession session,@Param("company") String company){
        log.info("company -> {}",company);
        User user = (User) session.getAttribute("user");
        userInfoService.saveOrUpdateCompany(user.getId(),company);
        return REDIRECT;
    }

    @PostMapping("/user/update/intro")
    public String intro(HttpSession session,@Param("intro") String intro){
        log.info("intro -> {}",intro);
        User user = (User) session.getAttribute("user");
        userInfoService.saveOrUpdateIntro(user.getId(),intro);
        return REDIRECT;
    }

    @PostMapping("/user/update/hobby")
    public String hobby(HttpSession session,@Param("hobby") String hobby){
        log.info("hobby -> {}",hobby);
        User user = (User) session.getAttribute("user");
        userInfoService.saveOrUpdateHobby(user.getId(),hobby);
        return REDIRECT;
    }


}
