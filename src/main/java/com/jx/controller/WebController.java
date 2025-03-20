package com.jx.controller;

import com.jx.service.RegionDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Web Controller for HTML views
 */
@Controller
public class WebController {
    
    @Autowired
    private RegionDataService regionDataService;
    
    /**
     * Home page - shows all provinces
     */
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("provinces", regionDataService.getAllProvinces());
        return "index";
    }
    
    /**
     * Province detail page
     */
    @GetMapping("/province/{code}")
    public String provinceDetail(@PathVariable String code, Model model) {
        model.addAttribute("province", regionDataService.getProvinceByCode(code));
        return "province";
    }
}