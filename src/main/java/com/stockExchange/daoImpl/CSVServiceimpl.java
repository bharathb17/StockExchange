package com.stockExchange.daoImpl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.stockExchange.dao.CSVService;
import com.stockExchange.data.entity.StockEntity;
import com.stockExchange.repository.StockRepo;
import com.stockExchange.repository.TransactionRepo;
import com.stockExchange.utils.CSVHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CSVServiceimpl implements CSVService {
  @Autowired
  StockRepo stockRepo;
  
  @Autowired
  TransactionRepo transactionRepo;

  public void save(MultipartFile file) {
    try {
      List<StockEntity> stockEntity = CSVHelper.csvToStocks(file.getInputStream());
      for(int i = 0; i < stockEntity.size(); i++) {
    	  StockEntity newStock = stockEntity.get(i);
//    	  Optional<StockEntity> oldStock =  stockRepo.findByCompanyNameAndDate(newStock.getCompanyName(), LocalDate.now());
    	  Optional<StockEntity> oldStock =  stockRepo.findByCompanyNameAndDate(newStock.getCompanyName(), LocalDate.now().minusDays(1));
    	  if(oldStock.isEmpty()) {
    		  newStock.setDate(LocalDate.now());
    		  stockRepo.save(newStock);
    	  } else {
    		  StockEntity stock = oldStock.get();
    		  stock.setDescription(newStock.getDescription());
     		  Date date = new Date();
     		  stock.setDate(LocalDate.now());
    		  LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    		  LocalDate newLocalDate = localDate.minusDays(1);
    		  Date prevDate = Date.from(newLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    		  
    		  Calendar calendar = Calendar.getInstance();
    		  calendar.setTime(prevDate);

    		  int year = calendar.get(Calendar.YEAR);
    		  int month = calendar.get(Calendar.MONTH) + 1;
    		  int day = calendar.get(Calendar.DAY_OF_MONTH);

    		  String dateStr = year + "-" + month + "-" + day;
    		  
    		  Long sum = transactionRepo.sumQuantityByCompanyNameAndDateAndStatus(stock.getCompanyName(), dateStr , "Success");
    		  stock.setVolumeOfTrade(sum != null ? sum : 0L);

    		  // Calculate ChangePercentage
    		  Double changePercent = ((newStock.getSharePrice()  - stock.getSharePrice())/stock.getSharePrice())*100;
              stock.setChangePercentage(changePercent);
    		  
//    		  System.out.println(newStock.getSharePrice()+ " "+ stock.getSharePrice());
    		  stock.setSharePrice(newStock.getSharePrice());
    		  stockRepo.save(stock);
    	  } 
      }
    } catch (IOException e) {
      throw new RuntimeException("fail to store csv data: " + e.getMessage());
    }
  }


}
