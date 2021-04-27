package com.zhong.dao;

import com.zhong.po.Integral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by cc on 2021/4/25
 */
public interface IntegralRepository extends JpaRepository<Integral,Long> {

    @Query(nativeQuery = true, value = "select * from t_integral where uid = :id")
    Integral findIntegralById(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "update t_integral set grade=grade+ :grade ,integral=integral+ :integral where uid = :uid")
    int updateByUid(@Param("grade") int grade ,@Param("integral") int integral , @Param("uid") Long uid);
}
