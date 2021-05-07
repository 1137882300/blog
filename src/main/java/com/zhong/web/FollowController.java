package com.zhong.web;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.zhong.po.Follow;
import com.zhong.po.User;
import com.zhong.result.JsonResult;
import com.zhong.service.FollowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;


/**
 * Created by cc on 2021/4/29
 */
@Slf4j
@Controller
public class FollowController {

    @Autowired
    private FollowService followService;

    /**
     * 关注(先存起来，个人主页的数据再去找)
     * @param session
     * @param fid
     * @return
     */
    @GetMapping("user/follow/{fid}/do")
    @ResponseBody
    public JsonResult followDo(HttpSession session, @PathVariable("fid") Long fid){
        User user = (User) session.getAttribute("user");
        log.info("fid->{}\tuser->{}",fid,user);
        followService.saveOrUpdateFollow(user.getId(), fid,1);
        return JsonResult.success(1);
    }

    /**
     * 取消关注
     * @param session
     * @param fid
     * @return
     */
    @GetMapping("user/follow/{fid}/undo")
    @ResponseBody
    public JsonResult followUndo(HttpSession session, @PathVariable("fid") Long fid){
        User user = (User) session.getAttribute("user");
        log.info("fid->{}\tuser->{}",fid,user);
        followService.saveOrUpdateFollow(user.getId(), fid,0);
        return JsonResult.success(0);
    }


    @GetMapping("/user/follow/{fid}/followJudge")
    public String followJudge(@PathVariable("fid") Long fid , HttpSession session,Model model){
        User user = (User) session.getAttribute("user");
        log.info("fid->{},uid->{}",fid,user.getId());
        Follow follow = followService.queryByUidAndFid(user.getId(), fid);
        if (follow != null){
            int state = follow.getState();
            log.info("state -> {}",state);
            model.addAttribute("state",state);
        } else {
            model.addAttribute("state",0);
        }
        return "blog :: follow";//局部刷新
    }


    /**
     * 关注/取消关注
     * @param fid
     * @param session
     * @param model
     * @return
     */
    @GetMapping("/user/follow/{fid}")
    public String follow(@PathVariable("fid") Long fid, HttpSession session, Model model){
        User user = (User) session.getAttribute("user");
        Long uid = user.getId();
        //判断有没有数据
        Follow follow = followService.queryByUidAndFid(user.getId(), fid);
        if (follow == null){
            //没有关注过, 这里只可能是 未关注 的状态 ，走这个就是 去 关注
            Follow f = followService.saveFollow(uid, fid);
            model.addAttribute("state", f.getState());
        } else {
            //有关注过的， 去取消关注
            if (follow.getState() == 1){
                //如果已关注，就取消关注
                Follow f = followService.updateFollow(follow, 0);
                model.addAttribute("state", f.getState());
            } else {
                //如果未关注 就去关注
                Follow f = followService.updateFollow(follow, 1);
                model.addAttribute("state", f.getState());
            }

        }
        return "blog :: follow";
    }


    /**
     * 判断是否关注
     * @param fid
     * @param session
     * @param model
     * @return
     */
    @GetMapping("/user/follow/{fid}/followWhether")
    public String followWhether(@PathVariable("fid") Long fid, HttpSession session, Model model){
        User user = (User) session.getAttribute("user");
        log.info("fid->{},uid->{}",fid,user.getId());
        Follow follow = followService.queryByUidAndFid(user.getId(), fid);
        if (follow != null){
            int state = follow.getState();
            log.info("state -> {}",state);
            model.addAttribute("state", state);
        } else {
            model.addAttribute("state",0);
        }
        return "blog :: follow";//局部刷新
    }





}
