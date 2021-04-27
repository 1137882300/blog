package com.zhong.web;

import com.zhong.po.Blog;
import com.zhong.po.User;
import com.zhong.service.BlogService;
import com.zhong.service.TagService;
import com.zhong.service.TypeService;
import com.zhong.vo.BlogQuery;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * Created by cc on 2021/4/15
 */
@Controller
@Slf4j
public class BlogShowController {


    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping("/user/blog/toBlogInput")
    public String toBlogInput(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("blog",new Blog());
        return "blog-input";
    }


    @PostMapping("/user/blog/blogInput")
    public String blogInput(Blog blog, Model model, HttpSession session){
        blog.setUser((User) session.getAttribute("user"));
        if(blog.getFlag() == null || blog.getFlag().equals("")){
            blog.setFlag("原创");
        }
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b;
        //新建博客
        if (blog.getId() == null){
            b = blogService.saveBlog(blog);
        } else {
            b = blogService.updateBlog(blog.getId(), blog);
        }
        if (b == null){
            model.addAttribute("message","操作失败");
        } else {
            model.addAttribute("message","操作成功");
            model.addAttribute("title",b.getTitle());
            model.addAttribute("img",b.getFirstPicture());
        }
        //跳转 发布成功的页面(更新，发布都是这个页面)
        return "published";
    }










}
