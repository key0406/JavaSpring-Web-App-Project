package com.rcuk.portal.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Flight2Controller {
    @GetMapping({"/flight2"})
    public String flight2(){return "flight2";}
}
