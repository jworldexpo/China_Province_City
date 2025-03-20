package com.jx.controller;

import com.jx.Province;
import com.jx.service.RegionDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST Controller for region data
 */
@RestController
@RequestMapping("/api/regions")
public class RegionController {
    
    @Autowired
    private RegionDataService regionDataService;
    
    /**
     * Get all provinces
     */
    @GetMapping("/provinces")
    public List<Province> getAllProvinces() {
        return regionDataService.getAllProvinces();
    }
    
    /**
     * Get province by code
     */
    @GetMapping("/provinces/{code}")
    public Province getProvinceByCode(@PathVariable String code) {
        return regionDataService.getProvinceByCode(code);
    }
}