package com.jx.message;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Fetches data from external sources.
 * 
 * @author jx
 */
public class DataFetcher {

    /**
     * Fetches administrative region data from the official website.
     * 
     * @param url The URL to fetch data from
     * @return A pair of lists containing region names and codes
     * @throws IOException If there's an error fetching the data
     */
    public static DataResult fetchAdministrativeRegionData(String url) throws IOException {
        Document doc = Jsoup.connect(url).maxBodySize(0).get();
        Elements elements = doc.getElementsByClass("xl7016597");
        List<String> stringList = elements.eachText();
        List<String> stringName = new ArrayList<String>();
        List<String> stringCode = new ArrayList<String>();
        
        for (int i = 0; i < stringList.size(); i++) {
            if (i % 2 == 0) {
                // Region code
                stringCode.add(stringList.get(i));
            } else {
                // Region name
                stringName.add(stringList.get(i));
            }
        }
        
        // Validate data
        if (stringName.size() != stringCode.size()) {
            throw new RuntimeException("Data error: name and code lists have different sizes");
        }
        
        return new DataResult(stringName, stringCode);
    }
    
    /**
     * Class to hold the result of data fetching.
     */
    public static class DataResult {
        private final List<String> names;
        private final List<String> codes;
        
        public DataResult(List<String> names, List<String> codes) {
            this.names = names;
            this.codes = codes;
        }
        
        public List<String> getNames() {
            return names;
        }
        
        public List<String> getCodes() {
            return codes;
        }
    }
}