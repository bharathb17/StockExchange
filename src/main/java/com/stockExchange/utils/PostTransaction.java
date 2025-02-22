package com.stockExchange.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.stockExchange.dao.TransactionService;
import com.stockExchange.data.entity.HoldingEntity;
import com.stockExchange.data.entity.StockEntity;
import com.stockExchange.data.entity.TransactionEntity;
import com.stockExchange.repository.HoldingRepo;
import com.stockExchange.repository.StockRepo;
import com.stockExchange.repository.TransactionRepo;

@Component
public class PostTransaction {

	@Autowired
	TransactionRepo transactionRepo;

	@Autowired
	TransactionService transactionService;

	@Autowired
	StockRepo stockRepo;

	@Autowired
	HoldingRepo holdingRepo;

	@Scheduled(cron = "0 42 14 * * ?")
	
	private void process() {

		System.out.println("....Post Process Started....");
		Calendar calendar = Calendar.getInstance();
		String date = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-"
				+ calendar.get(Calendar.DAY_OF_MONTH);

		List<TransactionEntity> transactions = transactionRepo.findByDateAndStatusOrderByTimeStamp(date, "pending");

		List<String> companyList = stockRepo.findCompanyNameByDate(LocalDate.now());

		for (String companyName : companyList) {

			List<TransactionEntity> buyList = new ArrayList<>();
			List<TransactionEntity> sellList = new ArrayList<>();

			for (TransactionEntity transaction : transactions) {

				if (transaction.getCompanyName().equals(companyName)) {
					if ("Buy".equals(transaction.getType())) {
						buyList.add(transaction);
					}

					if ("Sell".equals(transaction.getType())) {
						sellList.add(transaction);
					}
				}
			}

			holdingProcess(buyList, sellList, companyName, date);
		}
		System.out.println("....Over....");
	}

	public void holdingProcess(List<TransactionEntity> buyList, List<TransactionEntity> sellList, String companyName,
			String date) {

		int totalBuyingQuantity = 0;
		int totalSellingQuantity = 0;

		for (TransactionEntity buyOrder : buyList) {
			totalBuyingQuantity += buyOrder.getQuantity();
		}

		for (TransactionEntity sellOrder : sellList) {
			totalSellingQuantity += sellOrder.getQuantity();
		}

		Optional<StockEntity> companyInfo = stockRepo.findByCompanyNameAndDate(companyName, LocalDate.now());

		if (totalBuyingQuantity < totalSellingQuantity) {

			for (TransactionEntity buyOrder : buyList) {

				Optional<HoldingEntity> holding = holdingRepo.findByUsernameAndCompanyName(buyOrder.getUsername(),
						companyName);

				if (holding.isPresent()) {
					holding.get().setInvestedValue(holding.get().getInvestedValue()
							+ (buyOrder.getQuantity() * companyInfo.get().getSharePrice()));
					holding.get().setQuantity(holding.get().getQuantity() + buyOrder.getQuantity());
					holding.get().setCurrentValue(holding.get().getQuantity() * companyInfo.get().getSharePrice());

					holdingRepo.save(holding.get());

					buyOrder.setStatus("Success");
					transactionRepo.save(buyOrder);
				} else {
					HoldingEntity newHolding = new HoldingEntity();

					newHolding.setCompanyName(buyOrder.getCompanyName());
					newHolding.setUsername(buyOrder.getUsername());
					newHolding.setQuantity(buyOrder.getQuantity());
					newHolding.setInvestedValue(companyInfo.get().getSharePrice() * buyOrder.getQuantity());
					newHolding.setCurrentValue(companyInfo.get().getSharePrice() * buyOrder.getQuantity());

					holdingRepo.save(newHolding);

					buyOrder.setStatus("Success");
					transactionRepo.save(buyOrder);
				}
			}

			int totalSuccessSell = 0;
			for (TransactionEntity sellOrder : sellList) {
				totalSuccessSell += sellOrder.getQuantity();

				Optional<HoldingEntity> holding = holdingRepo.findByUsernameAndCompanyName(sellOrder.getUsername(),
						companyName);

				if (totalSuccessSell < totalBuyingQuantity) {
					holding.get()
							.setInvestedValue(holding.get().getInvestedValue()
									- ((holding.get().getInvestedValue() / holding.get().getQuantity())
											* sellOrder.getQuantity()));
					holding.get().setQuantity(holding.get().getQuantity() - sellOrder.getQuantity());
					holding.get().setCurrentValue(holding.get().getQuantity() * companyInfo.get().getSharePrice());

					holdingRepo.save(holding.get());

					sellOrder.setStatus("Success");
					transactionRepo.save(sellOrder);
				} else {

					int difference = totalSuccessSell - totalBuyingQuantity;

					if (totalBuyingQuantity == (totalSuccessSell - difference)) {
						sellOrder.setQuantity(sellOrder.getQuantity() - difference);
						sellOrder.setStatus("Success");

						holding.get()
								.setInvestedValue(holding.get().getInvestedValue()
										- ((holding.get().getInvestedValue() / holding.get().getQuantity())
												* sellOrder.getQuantity()));
						holding.get().setQuantity(holding.get().getQuantity() - sellOrder.getQuantity());
						holding.get().setCurrentValue(holding.get().getQuantity() * companyInfo.get().getSharePrice());

						holdingRepo.save(holding.get());
						transactionRepo.save(sellOrder);

						TransactionEntity cancelRecord = new TransactionEntity();

						cancelRecord.setCompanyName(sellOrder.getCompanyName());
						cancelRecord.setUsername(sellOrder.getUsername());
						cancelRecord.setDate(date);
						cancelRecord.setQuantity(difference);
						cancelRecord.setType(sellOrder.getType());
						cancelRecord.setStatus("Cancelled");
						cancelRecord.setTimeStamp(new Date());

						transactionRepo.save(cancelRecord);
					} else {
						sellOrder.setStatus("Cancelled");
						transactionRepo.save(sellOrder);
					}
				}
			}
		} else if (totalBuyingQuantity > totalSellingQuantity) {

			for (TransactionEntity sellOrder : sellList) {

				Optional<HoldingEntity> holding = holdingRepo.findByUsernameAndCompanyName(sellOrder.getUsername(),
						companyName);

				holding.get().setInvestedValue(holding.get().getInvestedValue()
						- ((holding.get().getInvestedValue() / holding.get().getQuantity()) * sellOrder.getQuantity()));
				holding.get().setQuantity(holding.get().getQuantity() - sellOrder.getQuantity());
				holding.get().setCurrentValue(holding.get().getQuantity() * companyInfo.get().getSharePrice());

				holdingRepo.save(holding.get());

				sellOrder.setStatus("Success");
				transactionRepo.save(sellOrder);
			}

			int totalSuccessBuy = 0;
			for (TransactionEntity buyOrder : buyList) {
				totalSuccessBuy += buyOrder.getQuantity();

				Optional<HoldingEntity> holding = holdingRepo.findByUsernameAndCompanyName(buyOrder.getUsername(),
						companyName);

				if (totalSuccessBuy < totalSellingQuantity) {
					if (holding.isPresent()) {
						holding.get().setInvestedValue(holding.get().getInvestedValue()
								+ (buyOrder.getQuantity() * companyInfo.get().getSharePrice()));
						holding.get().setQuantity(holding.get().getQuantity() + buyOrder.getQuantity());
						holding.get().setCurrentValue(holding.get().getQuantity() * companyInfo.get().getSharePrice());

						holdingRepo.save(holding.get());

						buyOrder.setStatus("Success");
						transactionRepo.save(buyOrder);
					} else {
						HoldingEntity newHolding = new HoldingEntity();

						newHolding.setCompanyName(buyOrder.getCompanyName());
						newHolding.setUsername(buyOrder.getUsername());
						newHolding.setQuantity(buyOrder.getQuantity());
						newHolding.setInvestedValue(companyInfo.get().getSharePrice() * buyOrder.getQuantity());
						newHolding.setCurrentValue(companyInfo.get().getSharePrice() * buyOrder.getQuantity());

						holdingRepo.save(newHolding);

						buyOrder.setStatus("Success");
						transactionRepo.save(buyOrder);
					}
				} else {

					int difference = totalSuccessBuy - totalSellingQuantity;

					if (totalSellingQuantity == (totalSuccessBuy - difference)) {
						buyOrder.setQuantity(buyOrder.getQuantity() - difference);
						buyOrder.setStatus("Success");

						if (holding.isPresent()) {
							holding.get().setInvestedValue(holding.get().getInvestedValue()
									+ (buyOrder.getQuantity() * companyInfo.get().getSharePrice()));
							holding.get().setQuantity(holding.get().getQuantity() + buyOrder.getQuantity());
							holding.get()
									.setCurrentValue(holding.get().getQuantity() * companyInfo.get().getSharePrice());

							holdingRepo.save(holding.get());

							buyOrder.setStatus("Success");
							transactionRepo.save(buyOrder);

							TransactionEntity cancelRecord = new TransactionEntity();

							cancelRecord.setCompanyName(buyOrder.getCompanyName());
							cancelRecord.setUsername(buyOrder.getUsername());
							cancelRecord.setDate(date);
							cancelRecord.setQuantity(difference);
							cancelRecord.setType("Buy");
							cancelRecord.setStatus("Cancelled");
							cancelRecord.setTimeStamp(new Date());

							transactionRepo.save(cancelRecord);
						} else {
							HoldingEntity newHolding = new HoldingEntity();

							newHolding.setCompanyName(buyOrder.getCompanyName());
							newHolding.setUsername(buyOrder.getUsername());
							newHolding.setQuantity(buyOrder.getQuantity());
							newHolding.setInvestedValue(companyInfo.get().getSharePrice() * buyOrder.getQuantity());
							newHolding.setCurrentValue(companyInfo.get().getSharePrice() * buyOrder.getQuantity());

							holdingRepo.save(newHolding);

							buyOrder.setStatus("Success");
							transactionRepo.save(buyOrder);

							TransactionEntity cancelRecord = new TransactionEntity();

							cancelRecord.setCompanyName(buyOrder.getCompanyName());
							cancelRecord.setUsername(buyOrder.getUsername());
							cancelRecord.setDate(date);
							cancelRecord.setQuantity(difference);
							cancelRecord.setStatus("Cancelled");
							cancelRecord.setTimeStamp(new Date());

							transactionRepo.save(cancelRecord);
						}
					} else {
						buyOrder.setStatus("Cancelled");
						transactionRepo.save(buyOrder);
					}
				}
			}
		} else {
			for (TransactionEntity buyOrder : buyList) {
				Optional<HoldingEntity> holding = holdingRepo.findByUsernameAndCompanyName(buyOrder.getUsername(),
						companyName);

				if (holding.isPresent()) {
					holding.get().setInvestedValue(holding.get().getInvestedValue()
							+ (buyOrder.getQuantity() * companyInfo.get().getSharePrice()));
					holding.get().setQuantity(holding.get().getQuantity() + buyOrder.getQuantity());
					holding.get().setCurrentValue(holding.get().getQuantity() * companyInfo.get().getSharePrice());

					holdingRepo.save(holding.get());

					buyOrder.setStatus("Success");
					transactionRepo.save(buyOrder);
				} else {
					HoldingEntity newHolding = new HoldingEntity();

					newHolding.setCompanyName(buyOrder.getCompanyName());
					newHolding.setUsername(buyOrder.getUsername());
					newHolding.setQuantity(buyOrder.getQuantity());
					newHolding.setInvestedValue(companyInfo.get().getSharePrice() * buyOrder.getQuantity());
					newHolding.setCurrentValue(companyInfo.get().getSharePrice() * buyOrder.getQuantity());

					holdingRepo.save(newHolding);

					buyOrder.setStatus("Success");
					transactionRepo.save(buyOrder);
				}
			}

			for (TransactionEntity sellOrder : sellList) {
				Optional<HoldingEntity> holding = holdingRepo.findByUsernameAndCompanyName(sellOrder.getUsername(),
						companyName);

				holding.get().setInvestedValue(holding.get().getInvestedValue()
						- ((holding.get().getInvestedValue() / holding.get().getQuantity()) * sellOrder.getQuantity()));
				holding.get().setQuantity(holding.get().getQuantity() - sellOrder.getQuantity());
				holding.get().setCurrentValue(holding.get().getQuantity() * companyInfo.get().getSharePrice());

				holdingRepo.save(holding.get());

				sellOrder.setStatus("Success");
				transactionRepo.save(sellOrder);
			}
		}
	}
}
