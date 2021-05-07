package com.zhong.service;

import com.zhong.po.ThumbsUp;
import com.zhong.vo.ThumbsUpDataVO;

/**
 * Created by cc on 2021/5/5
 */
public interface ThumbsUpService {


    ThumbsUpDataVO getThumbsUpData(Long zid, Long bid);

    ThumbsUp saveThumbsUp(Long uid, Long zid, Long bid);

    ThumbsUp findByIds(Long uid, Long zid, Long bid);

    int countThumbsByUid(Long uid);
}
