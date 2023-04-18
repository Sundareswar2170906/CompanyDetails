package com.stockmarket.stockdetails.service;

import java.util.List;

import com.stockmarket.stockdetails.models.StockDateRequestDTO;
import com.stockmarket.stockdetails.models.StockDetail;
import com.stockmarket.stockdetails.models.StockDetailList;

public interface IService {

	List<StockDetail> getAllLatest();

	StockDetail getLatestByCompanyCode(int companycode);

	void deleteCompanyDetails(int companycode);

	StockDetailList getDetailList(int companycode, StockDateRequestDTO stockDate);

}
