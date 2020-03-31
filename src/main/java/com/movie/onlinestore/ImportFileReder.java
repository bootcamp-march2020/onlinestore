package com.movie.onlinestore;

import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class ImportFileReder {

    @Value( "${importdata.prefix}" )
    private String urlPrefix;

    public List<String[]> obtainRecords(String fileName) throws IOException {
        List<String[]> recordList = new ArrayList<>();
        URL url = new URL(urlPrefix + fileName);
        CSVReader csvReader = new CSVReader(new InputStreamReader(url.openStream()));
        String[] record;
        if (csvReader.readNext() != null) {
            while ((record = csvReader.readNext()) != null) {
                recordList.add(record);
            }
        }
        return recordList;
    }

}
