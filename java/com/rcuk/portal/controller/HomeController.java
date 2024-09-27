package com.rcuk.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping({"/"})
    public String index(Model model, @RequestParam("username") String name) {

        model.addAttribute("user", name);
        if(name.equals("key")){
            model.addAttribute("vip","VIP");
        }
        else if(name.equalsIgnoreCase("neko")){
            model.addAttribute("neko","Nyaaaaaaaaaa");
        }
        return "index";
    }
}
