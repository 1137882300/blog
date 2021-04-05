40 的18分

删除都是有bug，牵扯主外键
前端图片的大小

有问题的
    /*当页面加载完成*/
    $(function () {
      $('#comment-container').load(/*[[@{/comments/{id}}]]*/"comments/6");

    })



Jpa 写原生sql
最后一个deleteByAppintmentIds方法是根据传入的一组appointmentid数据删除数据库所有匹配的结果。
@Transactional注解：开启事务，当sql语句执行的是更新、删除时一定要加
@Modifying注解：标识此方法执行的是更新、删除、插入操作；（JPA默认使用executeQuery()执行自定义的sql语句，当自定义的时update、insert、delete语句时，需要使用execute()执行，否则会报错）
@Query注解：用来写sql，nativeQuery = true说明写的sql是本地sql，不需要经过JPA二次翻译，value中的值为sql语句体
