package com.zhong.web.admin;

import com.zhong.po.Blog;
import com.zhong.po.Type;
import com.zhong.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QSort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by cc on 2021/3/19
 */
@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String types(@PageableDefault(size = 6,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model){
        model.addAttribute("page",typeService.listType(pageable));

        return "admin/types";
    }


    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());

        return "admin/types-input";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));

        return "admin/types-input";
    }


    @PostMapping("/types")//BindingResult 接收验证的结果,@Valid Type type, BindingResult result 顺序一定要是这样的才有效果
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        Type flagType = typeService.getTypeByName(type.getName());
        if (flagType != null){
            result.rejectValue("name","nameError","该分类已存在！");//加一条验证的信息进去
        }
        if (result.hasErrors()){
            return "admin/types-input";
        }
        Type t = typeService.saveType(type);
        if (t == null){
            attributes.addFlashAttribute("message","新增失败");
        } else {
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/types";
    }

    @PostMapping("/types/{id}")//BindingResult 接收验证的结果
    public String editPost(@Valid Type type, BindingResult result,@PathVariable Long id, RedirectAttributes attributes){
        Type flagType = typeService.getTypeByName(type.getName());
        if (flagType != null){
            result.rejectValue("name","nameError","该分类已存在！");//加一条验证的信息进去
        }
        if (result.hasErrors()){
            return "admin/types-input";
        }
        Type t = typeService.updateType(id,type);
        if (t == null){
            attributes.addFlashAttribute("message","更新失败");
        } else {
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/types";
    }


    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        Type type = typeService.getType(id);
        List<Blog> boo = type.getBlogs();
        if (boo.size() > 0){
            attributes.addFlashAttribute("message","有关联的博客无法删除！");
            return "redirect:/admin/types";
        }
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }



}
