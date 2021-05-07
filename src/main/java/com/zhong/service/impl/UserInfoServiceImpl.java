package com.zhong.service.impl;

import com.sun.el.lang.ELArithmetic;
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

    @Override
    public void updateByUid(Long uid, String filename) {
        UserInfo userInfo = this.findByUid(uid);
        if (userInfo != null){
            userInfoRepository.updateFilenameByUid(uid,filename);
        } else {
            //添加进去
            UserInfo info = UserInfo.builder()
                    .uid(uid)
                    .avatar(filename)
                    .build();
            userInfoRepository.save(info);
        }

    }

    /**
     * 修改 nickname
     * @param uid
     * @param nickname
     */
    @Override
    public void saveOrUpdate(Long uid, String nickname) {
        //先判断是否有这个数据
        UserInfo userInfo = userInfoRepository.findByUid(uid);
        if (userInfo != null){
            userInfo.setNickname(nickname);
            userInfoRepository.save(userInfo);
        } else {
            UserInfo info = new UserInfo();
            info.setUid(uid);
            info.setNickname(nickname);
            userInfoRepository.save(info);
        }
    }

    /**
     * 保存用户信息对象
     * @param userInfo
     * @return
     */
    @Override
    public UserInfo saveUserInfo(UserInfo userInfo) {
        return userInfoRepository.save(userInfo);
    }

    @Override
    public void saveOrUpdateWork(Long uid, String work) {
        //先判断是否有这个数据
        UserInfo userInfo = this.findByUid(uid);
        if (userInfo != null){
            userInfo.setWork(work);
            userInfoRepository.save(userInfo);
        } else {
            UserInfo info = new UserInfo();
            info.setUid(uid);
            info.setWork(work);
            userInfoRepository.save(info);
        }
    }

    @Override
    public void saveOrUpdateCompany(Long uid, String company) {
        //先判断是否有这个数据
        UserInfo userInfo = this.findByUid(uid);
        if (userInfo != null){
            userInfo.setCompany(company);
            userInfoRepository.save(userInfo);
        } else {
            UserInfo info = new UserInfo();
            info.setUid(uid);
            info.setCompany(company);
            userInfoRepository.save(info);
        }
    }

    @Override
    public void saveOrUpdateHobby(Long uid, String hobby) {
        //先判断是否有这个数据
        UserInfo userInfo = this.findByUid(uid);
        if (userInfo != null){
            userInfo.setHobby(hobby);
            userInfoRepository.save(userInfo);
        } else {
            UserInfo info = new UserInfo();
            info.setUid(uid);
            info.setHobby(hobby);
            userInfoRepository.save(info);
        }
    }

    @Override
    public void saveOrUpdateIntro(Long uid, String intro) {
        //先判断是否有这个数据
        UserInfo userInfo = this.findByUid(uid);
        if (userInfo != null){
            userInfo.setIntro(intro);
            userInfoRepository.save(userInfo);
        } else {
            UserInfo info = new UserInfo();
            info.setUid(uid);
            info.setIntro(intro);
            userInfoRepository.save(info);
        }
    }
}
