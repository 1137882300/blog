package com.zhong.dao;

import com.zhong.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by cc on 2021/3/16
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    /*JpaRepository<User,Long> 第一个参数是实体，第二个是主键的参数*/

    User findByUsernameAndPassword(String username,String password);


}
