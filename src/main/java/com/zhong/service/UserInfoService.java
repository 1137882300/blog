package com.zhong.service;

import com.zhong.po.UserInfo;

/**
 * Created by cc on 2021/5/3
 */
public interface UserInfoService {

    UserInfo findByUid(Long uid);

}
