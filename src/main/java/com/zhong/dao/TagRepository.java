package com.zhong.dao;

import com.zhong.po.Blog;
import com.zhong.po.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by cc on 2021/3/21
 */
public interface TagRepository extends JpaRepository<Tag,Long>, JpaSpecificationExecutor<Tag> {

    Tag findByName(String name);


    @Query(nativeQuery = true,value = "select * from t_tag")
    List<Tag> findTagTop(Pageable pageable);


}
