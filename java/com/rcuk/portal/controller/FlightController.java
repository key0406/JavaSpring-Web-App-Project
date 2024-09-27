package com.rcuk.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FlightController {
    @GetMapping({"/flight"})
    public String flight(){return "flight";}
}
