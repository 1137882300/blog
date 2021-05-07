package com.zhong.service.impl;

import com.zhong.dao.FollowRepository;
import com.zhong.exception.BizException;
import com.zhong.po.Follow;
import com.zhong.service.FollowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by cc on 2021/4/29
 */
@Slf4j
@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private FollowRepository followRepository;

    /**
     * 保存关注
     * @param uid
     * @param fid
     * @return
     */
    @Override
    public Follow saveFollow(Long uid, Long fid, int state) {
        if (uid == null || fid == null){
            //throw new BizException();
            return null;
        }
        Follow follow = Follow.builder()
                .uid(uid)
                .fid(fid)
                .createTime(new Date())
                .state(state)
                .build();
        Follow fo = followRepository.save(follow);
        log.info("follow->{}", fo);
        return fo;
    }

    /**
     * 更新
     * @return
     */
    @Override
    public Follow updateFollow(Follow follow,int state) {
        follow.setUpdateTime(new Date());
        follow.setState(state);
        Follow save = followRepository.save(follow);
        return save;
    }

    /**
     * 根据uid 和 fid 查询
     * @param uid
     * @param fid
     * @return
     */
    @Override
    public Follow queryByUidAndFid(Long uid, Long fid) {
        Follow follow = followRepository.queryByUidAndFid(uid, fid);
        log.info("queryByUidAndFid follow ->{} ",follow);
        if (follow != null){
            return follow;
        }
        return null;
    }

    /**
     * 更新或保存
     * @return
     */
    @Override
    public Follow saveOrUpdateFollow(Long uid, Long fid,int state) {
        Follow follow = this.queryByUidAndFid(uid, fid);
        if (follow != null){
            //保存过
            this.updateFollow(follow,state);
        } else {
            //没有保存过
            this.saveFollow(uid,fid,state);
        }
        return null;
    }

    /**
     * 根据 uid 查询 自己关注了多少
     * 1我被谁关注：fid -> uid 的统计 ： （1，71，69） -> 1
     * 1我关注谁： fid -> uid 的统计： 1 -> (1，69)
     * @param uid
     * @return
     */

    @Override
    public int countFollowByUid(Long uid) {
        return followRepository.countFollowByUid(uid);
    }

    @Override
    public int countFollowerByUid(Long uid) {
        return followRepository.countFollowerByUid(uid);
    }

    /**
     * 保存 关注信息
     * @param uid
     * @param fid
     * @return
     */
    @Override
    public Follow saveFollow(Long uid, Long fid) {
        Follow follow = Follow.builder()
                .fid(fid)
                .uid(uid)
                .state(1)
                .createTime(new Date())
                .build();
        return followRepository.save(follow);
    }


}
