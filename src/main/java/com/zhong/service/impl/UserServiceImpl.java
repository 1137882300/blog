package com.zhong.service.impl;

import com.zhong.dao.UserRepository;
import com.zhong.po.User;
import com.zhong.service.UserService;
import com.zhong.util.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by cc on 2021/3/16
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

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


}
