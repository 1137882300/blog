package com.zhong.service.impl;

import com.zhong.dao.ThumbsUpRepository;
import com.zhong.po.Blog;
import com.zhong.po.ThumbsUp;
import com.zhong.service.BlogService;
import com.zhong.service.ThumbsUpService;
import com.zhong.vo.ThumbsUpDataVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by cc on 2021/5/5
 */
@Slf4j
@Service
public class ThumbsUpServiceImpl implements ThumbsUpService {

    @Autowired
    private BlogService blogService;

    @Autowired
    private ThumbsUpRepository thumbsUpRepository;

    @Override
    public ThumbsUpDataVO getThumbsUpData(Long zid, Long bid){
        Blog blog = blogService.getBlog(bid);
        Long uid = blog.getUser().getId();
        ThumbsUp thumbsUp = thumbsUpRepository.findByIds(uid, zid, bid);
        if (thumbsUp == null){
            //未保存过
            this.saveThumbsUp(uid,zid,bid);
        } else {
            //保存过了,就更新
            thumbsUpRepository.updateByIds(uid,zid,bid);
        }

        ThumbsUp thumbs = this.findByIds(uid, zid, bid);
        ThumbsUpDataVO thumbsUpDataVO = ThumbsUpDataVO.builder()
                .num(thumbs.getTotal())
                .build();
        return thumbsUpDataVO;
    }

    /**
     * 保存 点赞
     * @param uid
     * @param zid
     * @param bid
     * @return
     */
    @Override
    public ThumbsUp saveThumbsUp(Long uid, Long zid, Long bid) {
        ThumbsUp thumbsUp = new ThumbsUp();
        thumbsUp.setBid(bid);
        thumbsUp.setZid(zid);
        thumbsUp.setUid(uid);
        thumbsUp.setCreateTime(new Date());
        thumbsUp.setStatus(1);
        thumbsUp.setTotal(1);
        return thumbsUpRepository.save(thumbsUp);
    }

    /**
     * 根据 ids 查询
     * @param uid
     * @param zid
     * @param bid
     * @return
     */
    @Override
    public ThumbsUp findByIds(Long uid, Long zid, Long bid) {
        ThumbsUp thumbsUp = thumbsUpRepository.findByIds(uid, zid, bid);
        return thumbsUp;
    }

    @Override
    public int countThumbsByUid(Long uid) {
        Integer thumbs = thumbsUpRepository.countThumbsByUid(uid);
        if (thumbs != null){
            return thumbs;
        }
        return 0;
    }
}
