 
[bug]
   1. blog详细页面 滑动侦听 
   2.  /*当页面加载完成*/
        $(function () {
          $('#comment-container').load(/*[[@{/comments/{id}}]]*/"comments/6");
        })
   3. 分类下拉框 默认值那个

[提示]
    1. https://picsum.photos/   图片的接口可以用 ，可用于上传图片 
    
[功能]
    1. 点赞
    2. 关注
    3. 积分
    4. 制作二维码分享的h5
    5. 上传图片，用哪个接口
    6. 评论 排序问题
    7. 标签、类型 后端都要加上时间的列
    8. 签到-》等级和积分都加
    9. 等级和用户类型是挂钩的
[注意]
    1. 删的是状态

 parent_comment_id  就是 一个标识  flag ，为null 表示父级，有值表示子级
 blogID 和 parent_comment_Uid 对应的

只能说：在这片文章里，我评论了你，他回复了我


Jpa 写原生sql
最后一个deleteByAppintmentIds方法是根据传入的一组appointmentid数据删除数据库所有匹配的结果。
@Transactional注解：开启事务，当sql语句执行的是更新、删除时一定要加
@Modifying注解：标识此方法执行的是更新、删除、插入操作；（JPA默认使用executeQuery()执行自定义的sql语句，当自定义的时update、insert、delete语句时，需要使用execute()执行，否则会报错）
@Query注解：用来写sql，nativeQuery = true说明写的sql是本地sql，不需要经过JPA二次翻译，value中的值为sql语句体
