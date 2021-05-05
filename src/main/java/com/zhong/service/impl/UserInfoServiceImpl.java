package com.zhong.service.impl;

import com.zhong.dao.UserInfoRepository;
import com.zhong.po.UserInfo;
import com.zhong.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by cc on 2021/5/3
 */
@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserInfo findByUid(Long uid) {
        UserInfo userInfo = userInfoRepository.findByUid(uid);
        return userInfo;
    }
}
