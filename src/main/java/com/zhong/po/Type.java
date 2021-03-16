package com.zhong.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by cc on 2021/3/14
 */
@Entity(name = "t_type")
public class Type {

    @Id//主键
    @GeneratedValue//mysql自动生成
    private Long id;
    private String name;

    @OneToMany(mappedBy = "type") //1 对多 （1 Type：Blog 多） one 是被维护的一端，需要加上 mappedBy = "type"
    private List<Blog> blogs = new ArrayList<>();

    public Type() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
