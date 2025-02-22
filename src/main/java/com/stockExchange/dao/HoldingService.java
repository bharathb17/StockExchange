package com.stockExchange.dao;

import com.stockExchange.data.dto.ResponseDTO;
import com.stockExchange.data.entity.HoldingEntity;

public interface HoldingService {
	public ResponseDTO addNewHolding(HoldingEntity holding);
	public ResponseDTO getAllHoldingsByUsername(String username);
	public ResponseDTO deleteHolding(String username, String companyName);
	public ResponseDTO getHoldingByUserameAndCompanyName(String username, String companyName);	
}