package com.zhong.dao;

import com.zhong.po.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.NamedNativeQuery;

/**
 * Created by cc on 2021/4/29
 */
public interface FollowRepository extends JpaRepository<Follow,Long> {


    @Query(nativeQuery = true, value = "select * from t_follow where uid = ?1 and fid = ?2")
    Follow queryByUidAndFid(Long uid, Long fid);

    /**
     * 根据 uid 查   我关注了谁  关注了  1 -> (1，69) : fid->uid
     * @param uid
     * @return
     */
    @Query(nativeQuery = true, value = "select count(1) from t_follow where fid = ?1 and state = 1")
    int countFollowByUid(Long uid);

    /**
     * 根据uid 查   我被谁关注   关注者 （1，71，69） -> 1 : fid->uid
     * @param uid
     * @return
     */
    @Query(nativeQuery = true, value = "select count(1) from t_follow where uid = ?1 and state = 1")
    int countFollowerByUid(Long uid);

}
