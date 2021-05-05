package com.zhong.dao;

import com.zhong.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by cc on 2021/3/16
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    /*JpaRepository<User,Long> 第一个参数是实体，第二个是主键的参数*/

    User findByUsernameAndPassword(String username,String password);


    //通过username查用户
    @Query(nativeQuery = true,value = "select * from t_user where username = :username ")
    User findByUsername(@Param("username") String username);


    //更新img 地址
    @Modifying
    @Query(nativeQuery = true,value = "update t_user set avatar = ?2 where id = ?1")
    void updateByUid(Long id,String filename);
//    void updateByUid(@Param("id") Long id,@Param("filename") String filename);
}
