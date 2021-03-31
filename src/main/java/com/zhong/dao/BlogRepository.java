package com.zhong.dao;

import com.zhong.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by cc on 2021/3/21
 */
public interface BlogRepository extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {

    @Query(nativeQuery = true,value = "select * from t_blog where recommend = true")
    List<Blog> findBlogTop(Pageable pageable);


    @Query(nativeQuery = true, value = "select * from t_blog b where b.title like ?1 or b.content like ?1")//第二个参数
    Page<Blog> findByQuery(String query, Pageable pageable);


}
