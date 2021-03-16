package com.zhong.po;

import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cc on 2021/3/14
 */
@Entity(name = "t_tag")
public class Tag {
    @Id//主键id
    @GeneratedValue//mysql生成策略
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "tags") //一样需要指定那一端是维护，那一端是被维护(Tag指定为被维护端)
    private List<Blog> blogs = new ArrayList<>();

    public Tag() {
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
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
