package com.stockExchange.dao;

import com.stockExchange.data.dto.ResponseDTO;
import com.stockExchange.data.dto.WatchListDTO;

public interface WatchListService {

	public ResponseDTO AddWatchList(WatchListDTO watchList);
	public ResponseDTO DeleteWatchList(WatchListDTO watchList);
	public ResponseDTO getAllUserWatchList(String username);
	
}
