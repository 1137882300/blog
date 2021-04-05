package com.zhong.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by cc on 2021/4/5
 */
@Controller
public class AboutShowController {



    @GetMapping("/about")
    public String about(){
        return "about";
    }

}
