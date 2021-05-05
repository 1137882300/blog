package com.zhong.service.impl;

import com.zhong.dao.UserRepository;
import com.zhong.po.Integral;
import com.zhong.po.User;
import com.zhong.service.BlogService;
import com.zhong.service.FollowService;
import com.zhong.service.IntegralService;
import com.zhong.service.UserService;
import com.zhong.util.MD5Utils;
import com.zhong.vo.UserInfoDataVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;

/**
 * Created by cc on 2021/3/16
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowService followService;

    @Autowired
    private IntegralService integralService;

    @Autowired
    private BlogService blogService;

    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, password);
        return user;
    }

    /**
     * 验证用户是否存在
     * @param username
     * @return
     */
    @Override
    public boolean checkUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null){
            return true; //有此用户
        }
        return false;//没有此用户
    }

    /**
     * 保存用户
     * @param user
     */
    @Override
    public User saveUser(User user) {
        User u = userRepository.save(user);//返回的就是这个对象实例
        log.info("user=>"+u);
        if (u == null){
            throw new RuntimeException("保存用户失败"+u);
        }
        return u;
    }

    /**
     * 根据 id 查 user
     * @param id
     * @return
     */
    @Override
    public User findUserById(Long id) {

        return userRepository.getOne(id);
    }

    /**
     * 根据 uid 更新 用户信息
     * @param id
     */
    @Transactional
    @Override
    public void updateByUid(Long id,String filename) {
        userRepository.updateByUid(id,filename);
    }

    /**
     * 保存或者更新通过 id
     * @param user
     * @return
     */
    @Override
    public User saveOrUpdateUser(User user) {
        User thisUser = userRepository.save(user);
        return thisUser;
    }

    /**
     * 获取profile的用户信息数据
     * @return
     */
    @Override
    public UserInfoDataVO getProfileData(Long uid) {
        //关注了
        int follow = followService.countFollowByUid(uid);
        //关注者
        int follower = followService.countFollowerByUid(uid);
        //获得的点赞
        //todo
        //文章被阅读
        int views = blogService.countViews(uid);
        //积分值
        Integral integral = integralService.findIntegral(uid);
        //组装数据
        UserInfoDataVO userInfoDataVO = UserInfoDataVO.builder()
                .follow(follow)
                .follower(follower)
                .views(views)
                .integral(integral.getIntegral())
                .build();
        log.warn("userInfoDataVO - > {}",userInfoDataVO);
        return userInfoDataVO;
    }


}
