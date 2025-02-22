package com.stockExchange.daoImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.stockExchange.dao.WatchListService;
import com.stockExchange.data.dto.ResponseDTO;
import com.stockExchange.data.dto.WatchListDTO;
import com.stockExchange.data.entity.WatchListEntity;
import com.stockExchange.repository.WatchListRepo;

@Service
public class WatchListServiceImpl implements WatchListService {

	@Autowired
	WatchListRepo watchlistrepo;

	@Override
	public ResponseDTO AddWatchList(WatchListDTO user) {

		Optional<WatchListEntity> data = watchlistrepo.findByUsernameAndCompanyName(user.getUsername(),	user.getCompanyName());
		
		if (data.isPresent()) {
			return new ResponseDTO(HttpStatus.BAD_REQUEST, "Watchlist for this company is already recorded");
		} else {
			WatchListEntity newUser = new WatchListEntity(user);
			watchlistrepo.save(newUser);
			return new ResponseDTO(HttpStatus.OK, "Watchlist Added", newUser);
		}
	}

	@Override
	public ResponseDTO DeleteWatchList(WatchListDTO user) {

		Optional<WatchListEntity> data = watchlistrepo.findByUsernameAndCompanyName(user.getUsername(),	user.getCompanyName());
		
		if (data.isEmpty()) {
			return new ResponseDTO(HttpStatus.NOT_FOUND, "Company not found");
		} else {
			watchlistrepo.delete(data.get());
			return new ResponseDTO(HttpStatus.OK, "Watchlist Deleted");
		}
	}

	@Override
	public ResponseDTO getAllUserWatchList(String username) {
		
		List<Optional<WatchListEntity>> userWatchlists = watchlistrepo.findByUsername(username);
		
		if (userWatchlists.isEmpty()) {
			return new ResponseDTO(HttpStatus.NOT_FOUND, "User not found");
		} else {
			return new ResponseDTO(HttpStatus.OK, "Watchlist found for the users", userWatchlists);
		}
	}
}
