package com.example.demo.controller;

import com.example.demo.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by 92992 on 2018/11/29.
 */
@Controller
@RequestMapping("/index")
public class ViewController {

    /**
     * 获取 form 表单页面
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView createForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("title", "创建用户");
        return new ModelAndView("index", "userModel", model);
    }

    @RequestMapping("/main")
    public ModelAndView main(){
        return new ModelAndView("main");
    }

    @RequestMapping("/index_main")
    public ModelAndView indexMain(){
        return new ModelAndView("index_main");
    }
}
