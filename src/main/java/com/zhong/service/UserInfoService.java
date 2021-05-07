package com.zhong.service;

import com.zhong.po.UserInfo;

/**
 * Created by cc on 2021/5/3
 */
public interface UserInfoService {

    UserInfo findByUid(Long uid);

    void updateByUid(Long id, String filename);

    void saveOrUpdate(Long id, String nickname);

    UserInfo saveUserInfo(UserInfo userInfo);

    void saveOrUpdateWork(Long uid, String work);

    void saveOrUpdateCompany(Long uid, String company);

    void saveOrUpdateHobby(Long uid, String hobby);

    void saveOrUpdateIntro(Long uid, String intro);
}
