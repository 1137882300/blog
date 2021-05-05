package com.zhong.service;

import com.zhong.po.User;
import com.zhong.vo.UserInfoDataVO;

import javax.jws.soap.SOAPBinding;

/**
 * Created by cc on 2021/3/16
 */
public interface UserService {

    //验证账号密码
    User checkUser(String username,String password);

    //验证用户是否存在
    boolean checkUser(String username);

    //保存用户
    User saveUser(User user);

    User findUserById(Long id);

    void updateByUid(Long id,String filename);

    User saveOrUpdateUser(User user);

    UserInfoDataVO getProfileData(Long uid);
}
