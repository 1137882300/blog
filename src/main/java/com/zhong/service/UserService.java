package com.zhong.service;

import com.zhong.po.User;

/**
 * Created by cc on 2021/3/16
 */
public interface UserService {

    User checkUser(String username,String password);

}
