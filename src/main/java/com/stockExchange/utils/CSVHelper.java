package com.stockExchange.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import com.stockExchange.data.entity.StockEntity;

public class CSVHelper {
	
  public static String TYPE = "text/csv";
  static String[] HEADERs = { "Id", "Title", "Description", "Published" };

  public static boolean hasCSVFormat(MultipartFile file) {

    if (!TYPE.equals(file.getContentType())) {
      return false;
    }

    return true;
  }

  public static List<StockEntity> csvToStocks(InputStream is) {
    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        CSVParser csvParser = new CSVParser(fileReader,
            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

      List<StockEntity> Stocks = new ArrayList<StockEntity>();

      Iterable<CSVRecord> csvRecords = csvParser.getRecords();

      for (CSVRecord csvRecord : csvRecords) {  	  
    	  String companyName = csvRecord.get("CompanyName");
          String symbol = csvRecord.get("Symbol");
          Double sharePrice = Double.parseDouble(csvRecord.get("SharePrice"));
          Long numberOfShares = Long.parseLong(csvRecord.get("NumberOfShares"));
          String description = csvRecord.get("Description");
          StockEntity stockEntity = new StockEntity(companyName, symbol, sharePrice, numberOfShares, description);
          Stocks.add(stockEntity);
      }

      return Stocks;
    } catch (IOException e) {
      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
    }
  }

}
