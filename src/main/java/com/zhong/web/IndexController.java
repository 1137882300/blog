package com.zhong.web;

import com.zhong.service.BlogService;
import com.zhong.service.TagService;
import com.zhong.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping(value = {"/","/index"})
    public String index(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC)
                        Pageable pageable, Model model){ //updateTime  与数据库的不一致可以，因为他是jpa的内置的
        //分页的数据放到 model
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags",tagService.listTagTop(10));
        model.addAttribute("recommend",blogService.listRecommendBlogTop(8));

        return "index";
    }

    @PostMapping("/search")// update_time 必须要和数据库一致，因为sql是自己写的
    public String search(@PageableDefault(size = 8,sort = {"update_time"},direction = Sort.Direction.DESC)
                                 Pageable pageable, @RequestParam String query, Model model){
        model.addAttribute("page",blogService.listBlog(pageable, "%"+query+"%"));
        model.addAttribute("query",query);
        return "search";
    }



    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model){
        model.addAttribute("blog",blogService.getBlogConvert(id));
        return "blog";
    }

    @GetMapping("/footer/newblog")
    public String newblogs(Model model){
        model.addAttribute("newblogs",blogService.listRecommendBlogTop(3));

        return "_fragments :: newblogList";
    }





}
