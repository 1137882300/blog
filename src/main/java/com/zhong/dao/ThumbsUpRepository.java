package com.zhong.dao;

import com.zhong.po.ThumbsUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by cc on 2021/5/5
 */
public interface ThumbsUpRepository extends JpaRepository<ThumbsUp,Long> {


    @Modifying
    @Query(nativeQuery = true, value = "insert into t_thumbs_up values ('total = total + 1','')")
    ThumbsUp saveThumbsUp(Long uid, Long zid, Long bid);

    @Query(nativeQuery = true, value = "select * from t_thumbs_up where uid = ?1 and zid = ?2 and bid = ?3")
    ThumbsUp findByIds(Long uid, Long zid, Long bid);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "update t_thumbs_up set total = total + 1 where uid = ?1 and zid = ?2 and bid = ?3")
    void updateByIds(Long uid, Long zid, Long bid);

    @Query(nativeQuery = true, value = "select sum(total) from t_thumbs_up where uid = ?1")
    Integer countThumbsByUid(Long uid);
}
