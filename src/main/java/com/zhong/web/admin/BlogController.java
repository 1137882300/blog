package com.zhong.web.admin;

import com.zhong.po.Blog;
import com.zhong.po.User;
import com.zhong.service.BlogService;
import com.zhong.service.TagService;
import com.zhong.service.TypeService;
import com.zhong.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * Created by cc on 2021/3/16
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 6,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return LIST;
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 6,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "admin/blogs :: blogList";//局部渲染，返回的是blogs页面里的blogList这个一块区域
    }


    @GetMapping("/blogs/input")
    public String input(Model model){
        setTypeAndTag(model);
        model.addAttribute("blog",new Blog());
        return INPUT;
    }

    private void setTypeAndTag(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
    }

    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        setTypeAndTag(model);
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
        return INPUT;
    }


    /**
     * 获取 写博客 的内容信息
     * @param blog
     * @param attributes
     * @param session
     * @return
     */
    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session){
        blog.setUser((User) session.getAttribute("user"));
        //判断flag 是否为空
        if(blog.getFlag() == null || blog.getFlag().equals("")){
            blog.setFlag("原创");
        }
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b;
        if (blog.getId() == null){
            b = blogService.saveBlog(blog);
        } else {
            b = blogService.updateBlog(blog.getId(),blog);
        }

        if (b == null){
            attributes.addFlashAttribute("message","操作失败");
        } else {
            attributes.addFlashAttribute("message","操作成功");
        }
        return REDIRECT_LIST;
    }


    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        Blog blog = blogService.getBlog(id);
        if (blog.isCommentabled()){
            attributes.addFlashAttribute("message","评论存在，无法删除");
        } else {
            blogService.deleteBlog(id);
            attributes.addFlashAttribute("message","删除成功");
        }
        return REDIRECT_LIST;
    }



}
