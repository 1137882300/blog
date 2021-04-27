package com.zhong.service.impl;

import com.google.common.collect.Lists;
import com.zhong.dao.TagRepository;
import com.zhong.exception.NotFoundException;
import com.zhong.po.Tag;
import com.zhong.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cc on 2021/3/21
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag getTag(Long id) {
        return tagRepository.getOne(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTag(String ids) {//1,2,3
        return tagRepository.findAllById(convertToList(ids));
    }


    /*
        字符串转成数组，在添加到list里去，还要获取新添加的标签[1,2,openfeign,3,汉字,4,6]
     */
    private List<Long> convertToList(String ids){
        List<Long> list = Lists.newArrayList();
        if (!"".equals(ids) && ids != null){
            String[] idArray = ids.split(",");
            for (int i = 0; i < idArray.length; i++) {
                //如果是自己新添加的标签
                if (!isNumeric(idArray[i])){
                    Tag tag = new Tag();
                    List<Tag> all = tagRepository.findAll();
                    Long id = all.get(all.size() - 1).getId() + 1;
                    tag.setId(id);
                    tag.setName(idArray[i]);
                    tagRepository.save(tag);
                    list.add(id);
                } else {
                    list.add(new Long(idArray[i]));
                }
            }
        }
        return list;
    }

    /*
        判断是否为数字
     */
    public boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }


    @Override
    public List<Tag> listTagTop(Integer size) {
        //TODO: 排序方式要改
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(0, size, sort);
        return tagRepository.findTagTop(pageable);
    }


    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t = tagRepository.getOne(id);
        if (t == null){
            throw new NotFoundException("不存在该标签");
        }
        BeanUtils.copyProperties(tag,t);
        return tagRepository.save(t);
    }

    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
