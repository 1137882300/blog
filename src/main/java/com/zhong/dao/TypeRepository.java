package com.zhong.dao;

import com.zhong.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by cc on 2021/3/19
 */

public interface TypeRepository extends JpaRepository<Type,Long> {

    Type findByName(String name);


    @Query(nativeQuery = true,value = "select * from t_type")
    List<Type> findTypeTop(Pageable pageable);


}
