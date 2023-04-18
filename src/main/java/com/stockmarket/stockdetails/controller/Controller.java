package com.stockmarket.stockdetails.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.stockmarket.stockdetails.models.StockDateRequestDTO;
import com.stockmarket.stockdetails.models.StockDetail;
import com.stockmarket.stockdetails.models.StockDetailList;
import com.stockmarket.stockdetails.service.IService;

@RestController
public class Controller {

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	private IService service;

	@GetMapping("/api/v/1.0/market/stock/getall/latest")
	public ResponseEntity<List<StockDetail>> getAllCompanyDetailsByCompanyCode() {		
		List<StockDetail> all= service.getAllLatest();		
		return new ResponseEntity<List<StockDetail>>(all, HttpStatus.OK);
	}

	@GetMapping("/api/v/1.0/market/stock/get/latest/{companycode}")
	public ResponseEntity<StockDetail> getCompanyDetailsByCompanyCode(@PathVariable int companycode) {		
		StockDetail stockDetail = service.getLatestByCompanyCode(companycode);
		return new ResponseEntity<StockDetail>(stockDetail, HttpStatus.OK);
	}

	@GetMapping("/api/v/1.0/market/company/delete/{companycode}")
	public ResponseEntity deleteCompanyDetailsByCompanyCode(@PathVariable int companycode) {
		service.deleteCompanyDetails(companycode);
		return new ResponseEntity(HttpStatus.OK);
	}

	@PostMapping("/api/v/1.0/market/detail/get/latest/{companycode}")
	public ResponseEntity<StockDetailList> getDetailsByCodeAndDate(@PathVariable int companycode, @RequestBody StockDateRequestDTO stockDate ) {
		StockDetailList detailList = service.getDetailList(companycode, stockDate);
		return new ResponseEntity<StockDetailList>(detailList, HttpStatus.OK)  ;
	}

}
