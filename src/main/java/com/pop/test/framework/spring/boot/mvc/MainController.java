package com.pop.test.framework.spring.boot.mvc;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MainController {
    @RequestMapping("/")
    @ResponseBody
    public String hello() {
        return "Hello World!";
    }

    @RequestMapping("/upload")
    @ResponseBody
    public String upload(MultipartFile file) throws IOException {
        System.out.println(file.getName());
        System.out.println(new String(file.getBytes()));
        return "SUCCESS";
    }
}