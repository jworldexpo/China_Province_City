package com.jx;

import com.jx.cache.SimpleCache;
import com.jx.common.JSONFormatUtils;
import com.jx.file.FileUtils;
import com.jx.message.DataFetcher;
import com.jx.model.Province;
import com.jx.process.DataProcessor;

import java.io.IOException;
import java.util.List;

/**
 * Main application class.
 * 
 * @author jx
 */
public class Application {
    private static final String DATA_URL = "http://www.mca.gov.cn/article/sj/xzqh/2019/201901-06/201902061009.html";
    private static final String OUTPUT_FILE_NAME = "2019年1月中华人民共和国县以上行政区划代码.json";
    
    // Cache for storing processed data
    private static final SimpleCache<String, List<Province>> dataCache = new SimpleCache<>();
    
    public static void main(String[] args) {
        try {
            // Fetch data
            System.out.println("Fetching administrative region data...");
            DataFetcher.DataResult result = DataFetcher.fetchAdministrativeRegionData(DATA_URL);
            
            // Process data
            System.out.println("Processing data...");
            List<Province> provinceList = DataProcessor.processData(result.getNames(), result.getCodes());
            
            // Cache the result
            dataCache.put("provinceList", provinceList);
            
            // Save to file
            System.out.println("Saving data to file...");
            String outputPath = FileUtils.getProjectDir() + "/" + OUTPUT_FILE_NAME;
            JSONFormatUtils.jsonWriter(provinceList, outputPath);
            
            System.out.println("Done! Data saved to: " + outputPath);
        } catch (IOException e) {
            System.err.println("Error processing data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}