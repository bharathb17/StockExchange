package com.stockExchange.dao;

import org.springframework.web.multipart.MultipartFile;

public interface CSVService {
	 public void save(MultipartFile file);
}
