package com.rcuk.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookingController {
    @GetMapping({"/bookings"})
    public String booking(Model model, @RequestParam("username") String name){


        model.addAttribute("user",name);

        if(name.equals("koki")){
            model.addAttribute("price",250);
        }else{
            model.addAttribute("price",20);
        }



        {return "booking";}
    }
}

