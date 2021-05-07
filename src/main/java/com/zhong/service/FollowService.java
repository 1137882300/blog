package com.zhong.service;

import com.zhong.po.Follow;

import java.security.acl.LastOwnerException;

/**
 * Created by cc on 2021/4/29
 */
public interface FollowService {

    Follow saveFollow(Long uid, Long fid, int state);

    Follow updateFollow(Follow follow, int state);

    Follow queryByUidAndFid(Long uid, Long fid);

    Follow saveOrUpdateFollow(Long uid, Long fid, int state);

    int countFollowByUid(Long uid);

    int countFollowerByUid(Long uid);

    Follow saveFollow(Long uid, Long fid);

}
