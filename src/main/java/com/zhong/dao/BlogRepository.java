package com.zhong.dao;

import com.zhong.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.Name;
import java.util.List;

/**
 * Created by cc on 2021/3/21
 */
public interface BlogRepository extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {

    @Query(nativeQuery = true,value = "select * from t_blog where recommend = true")
    List<Blog> findBlogTop(Pageable pageable);


    @Query(nativeQuery = true, value = "select * from t_blog b where b.title like ?1 or b.content like ?1")//第二个参数
    Page<Blog> findByQuery(String query, Pageable pageable);

    /*
    @Query("select u from User u where u.firstname = :firstname or u.lastname = :lastname")
    User findByLastnameOrFirstname(@Param("lastname") String lastname,
                                 @Param("firstname") String firstname);
     */

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "update t_blog set views = views+1 where id = ?1")
    int updateViews(Long id);


//    @Query(nativeQuery = true, value = "select date_format(b.update_time, '%Y') as year from t_blog b group by year order by year desc")
    @Query(nativeQuery = false, value = "select function('date_format',b.updateTime,'%Y') as year from t_blog b group by function('date_format',b.updateTime,'%Y') order by function('date_format',b.updateTime,'%Y') desc ")
    List<String> findGroupYears();


    @Query(nativeQuery = true,value = "select * from t_blog b where date_format(b.update_time, '%Y') = :year")
    List<Blog> findByYear(@Param("year") String year);

    @Query(nativeQuery = true, value = "select * from t_blog where user_id = ?1")
    List<Blog> getBlogByUid(Long uid);

    @Query(nativeQuery = true, value = "select ifnull((select sum(views) as views from t_blog where user_id = ?1),0)")
    Integer countViews(Long uid);
}
