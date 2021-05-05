package com.zhong.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by cc on 2021/5/2
 */
@Controller
public class FileController {


    @GetMapping(value = "/file")
    public String file() {
        return "file";
    }

    @PostMapping("/fileUpload")
    public String fileUpload(@RequestParam(value = "file") MultipartFile file, Model model){
        System.out.println(file);
        if (file.isEmpty()) {
            System.out.println("文件为空 ");
        }
        String originalFilename = file.getOriginalFilename();
        System.out.println(" -> "+originalFilename);
        String suffixFilename = originalFilename.substring(originalFilename.lastIndexOf("."));
        String prefixFilename = originalFilename.substring(0,originalFilename.indexOf("."));
        //上传后的路径
        String filePath = "D:\\ideaProject\\blog\\src\\main\\resources\\static\\uploads\\";
        String newFilename = prefixFilename + "_" + UUID.randomUUID() + suffixFilename;
        System.out.println(" -> "+newFilename);
        File dest = new File(filePath + newFilename);
        if (!dest.getParentFile().exists()){
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String filename = "/static/uploads/" + newFilename;
        System.out.println(" -> "+filename);
        model.addAttribute("filename", filename);
        return "file";
    }

}
