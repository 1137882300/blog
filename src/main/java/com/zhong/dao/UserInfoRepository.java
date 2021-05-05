package com.zhong.dao;

import com.zhong.po.User;
import com.zhong.po.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by cc on 2021/5/3
 */
public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {


    @Query(nativeQuery = true,value = "select * from t_user_info where uid = ?1")
    UserInfo findByUid(Long uid);
}
