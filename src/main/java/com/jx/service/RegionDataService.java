package com.jx.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jx.FileUtils;
import com.jx.Province;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Service to load and provide region data
 */
@Service
public class RegionDataService {
    
    private List<Province> provinces = new ArrayList<>();
    
    @PostConstruct
    public void init() {
        loadData();
    }
    
    /**
     * Load region data from JSON file
     */
    private void loadData() {
        try {
            String filePath = FileUtils.getProjectDir() + "/2019年1月中华人民共和国县以上行政区划代码.json";
            File file = new File(filePath);
            
            if (file.exists()) {
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<Province>>(){}.getType();
                provinces = gson.fromJson(new FileReader(file), listType);
            } else {
                System.err.println("Region data file not found: " + filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Get all provinces
     */
    public List<Province> getAllProvinces() {
        return provinces;
    }
    
    /**
     * Get province by code
     */
    public Province getProvinceByCode(String code) {
        return provinces.stream()
                .filter(p -> p.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }
}