package com.zhong.dao;

import com.zhong.po.User;
import com.zhong.po.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by cc on 2021/5/3
 */
public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {


    @Query(nativeQuery = true,value = "select * from t_user_info where uid = ?1")
    UserInfo findByUid(Long uid);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "update t_user_info set avatar = ?2 where uid = ?1")
    void updateFilenameByUid(Long uid, String filename);


    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "update t_user_info set nickname = ?2 where uid = ?1")
    void updateNicknameByUid(Long uid, String nickname);
}
