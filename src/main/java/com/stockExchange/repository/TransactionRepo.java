package com.stockExchange.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.stockExchange.data.entity.TransactionEntity;

public interface TransactionRepo extends JpaRepository<TransactionEntity, Integer> {
	public List<TransactionEntity> findByUsernameOrderByTimeStampDesc(String userName);
	public List<TransactionEntity> findByDateOrderByTimeStamp(String date);
	public List<TransactionEntity> findByDateAndStatusOrderByTimeStamp(String date, String status);
	public List<TransactionEntity> findByCompanyName(String companyName);

	@Query("SELECT SUM(t.quantity) FROM TransactionEntity t WHERE t.companyName = :companyName AND t.date = :date AND t.status = :status")
    public Long sumQuantityByCompanyNameAndDateAndStatus(@Param("companyName") String companyName,
                                                   @Param("date") String date,
                                                   @Param("status") String status);

}
