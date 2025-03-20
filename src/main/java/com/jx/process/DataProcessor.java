package com.jx.process;

import com.jx.model.Area;
import com.jx.model.City;
import com.jx.model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Processor for administrative region data.
 * 
 * @author jx
 */
public class DataProcessor {

    /**
     * Processes raw data into a structured province list.
     * 
     * @param stringName List of region names
     * @param stringCode List of region codes
     * @return List of provinces with their cities and areas
     */
    public static List<Province> processData(List<String> stringName, List<String> stringCode) {
        List<Province> provinceList = new ArrayList<Province>();
        for (int i = 0; i < stringCode.size(); i++) {
            String provinceName = stringName.get(i);
            String provinceCode = stringCode.get(i);
            if (provinceCode.endsWith("0000")) {
                Province province = new Province();
                provinceList.add(province);
                province.setCode(provinceCode);
                province.setName(provinceName);
                List<City> cities = new ArrayList<City>();
                province.setCityList(cities);
                //香港，澳门，台湾，没有市级行政单位划分，城市 地区 和省份保持一致
                if (provinceName.contains("香港") || provinceName.contains("澳门") || provinceName.contains("台湾")) {
                    City city = new City();
                    List<Area> areas = new ArrayList<Area>();
                    city.setName(provinceName);
                    city.setCode(provinceCode);
                    city.setAreaList(areas);
                    cities.add(city);
                    Area area = new Area();
                    area.setName(provinceName);
                    area.setCode(provinceCode);
                    areas.add(area);
                }
                //直辖市 城市和省份名称一样
                if (provinceName.contains("北京") || provinceName.contains("上海") || provinceName.contains("天津") || provinceName.contains("重庆")) {
                    City city = new City();
                    List<Area> areas = new ArrayList<Area>();
                    city.setName(provinceName);
                    city.setCode(provinceCode);
                    city.setAreaList(areas);
                    cities.add(city);
                    //县区
                    for (int k = 0; k < stringCode.size(); k++) {
                        String areaName = stringName.get(k);
                        String areaCode = stringCode.get(k);
                        if (!provinceCode.equals(areaCode) && areaCode.startsWith(provinceCode.substring(0, 2))) {
                            Area area = new Area();
                            area.setName(areaName);
                            area.setCode(areaCode);
                            areas.add(area);
                        }
                    }
                }
                for (int j = 0; j < stringCode.size(); j++) {
                    String cityName = stringName.get(j);
                    String cityCode = stringCode.get(j);
                    //遍历获取地级市
                    if (!cityCode.equals(provinceCode) && cityCode.startsWith(provinceCode.substring(0, 2)) && cityCode.endsWith("00")) {
                        City city = new City();
                        List<Area> areas = new ArrayList<Area>();
                        city.setName(cityName);
                        city.setCode(cityCode);
                        city.setAreaList(areas);
                        cities.add(city);
                        //遍历获取县区
                        for (int k = 0; k < stringCode.size(); k++) {
                            String areaName = stringName.get(k);
                            String areaCode = stringCode.get(k);
                            if (!areaCode.equals(cityCode) && areaCode.startsWith(cityCode.substring(0, 4))) {
                                Area area = new Area();
                                area.setName(areaName);
                                area.setCode(areaCode);
                                areas.add(area);
                            }
                        }
                    }
                }
            }
        }
        return provinceList;
    }
}