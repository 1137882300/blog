package com.zhong.dao;

import com.zhong.po.Type;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by cc on 2021/3/19
 */
public interface TypeRepository extends JpaRepository<Type,Long> {

    Type findByName(String name);

}
