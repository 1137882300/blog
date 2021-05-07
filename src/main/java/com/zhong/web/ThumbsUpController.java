package com.zhong.web;

import com.zhong.po.Blog;
import com.zhong.po.User;
import com.zhong.service.BlogService;
import com.zhong.service.ThumbsUpService;
import com.zhong.vo.ThumbsUpDataVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * Created by cc on 2021/5/5
 */
@Slf4j
@Controller
public class ThumbsUpController {


    @Autowired
    private ThumbsUpService thumbsUpService;


    @GetMapping("/user/thumbs/{bid}")
    public String thumbsUp(HttpSession session, @PathVariable("bid") Long bid, Model model){
        User user = (User) session.getAttribute("user");
        Long zid = user.getId();//点赞的人
        ThumbsUpDataVO thumbsUpData = thumbsUpService.getThumbsUpData(zid, bid);
        log.warn("thumbsUpData -> {}", thumbsUpData);
        model.addAttribute("num",thumbsUpData.getNum());
        return "blog :: thumbs";
    }


}
