package com.stockExchange.data.entity;

import com.stockExchange.data.dto.TransactionDTO;
import jakarta.persistence.*;

import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "transaction_details")
public class TransactionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int transactionId;

	@Column
	private String username;

	@Column
	private String companyName;

	@Column
	private Integer quantity;

	@Column
	private String type;

	@Column
	private String status;

	@Column
	private String date;

	@Column
	private Date timeStamp;

	public TransactionEntity() {
	}

	public TransactionEntity(TransactionDTO transaction) {
		this.username = transaction.getUsername();
		this.companyName = transaction.getCompanyName();
		this.quantity = transaction.getQuantity();
		this.type = transaction.getType();
		this.status = "pending";
		this.timeStamp = new Date();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(this.timeStamp);

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		this.date = year + "-" + month + "-" + day;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
}
