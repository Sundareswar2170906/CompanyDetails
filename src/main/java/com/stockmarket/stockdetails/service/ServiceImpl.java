package com.stockmarket.stockdetails.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.stockmarket.stockdetails.models.Company;
import com.stockmarket.stockdetails.models.Stock;
import com.stockmarket.stockdetails.models.StockDTO;
import com.stockmarket.stockdetails.models.StockDateRequestDTO;
import com.stockmarket.stockdetails.models.StockDetail;
import com.stockmarket.stockdetails.models.StockDetailList;
import com.stockmarket.stockdetails.models.StockResponse;

@Service
public class ServiceImpl implements IService {
	
	//private String kafkaTopic= "detail-logs";

	@Autowired
	private KafkaTemplate<String, String> template;
	
	@Autowired
	RestTemplate restTemplate;

	@Override
	public List<StockDetail> getAllLatest() {
		
		var latestStockPricePerCompany = restTemplate.getForEntity("http://STOCK/api/v1.0/market/stock/latest/price",
				StockDTO[].class);
		StockDTO[] stock = latestStockPricePerCompany.getBody();
		HashMap<Integer, Double> stockMap = new HashMap<Integer, Double>();
		for (StockDTO sd : stock) {
			stockMap.put(sd.getCode(), sd.getPrice());
		}
		//template.send(kafkaTopic, "Get All Latest Price");
				var allCompanyDetails = restTemplate.getForEntity("http://company/api/v1.0/market/company/getall",
						Company[].class);
				Company[] company = allCompanyDetails.getBody();
				System.out.println(company);
		List<StockDetail> stockDetail = new ArrayList<>();
		for (Company c : company) {
			StockDetail sd = new StockDetail();
			sd.setCode(c.getCode());
			sd.setName(c.getName());
			sd.setCeo(c.getCeo());
			sd.setExchange(c.getExchange());
			sd.setTurnover(c.getTurnover());
			sd.setWebsite(c.getWebsite());
			if(stockMap.get(c.getCode())!=null) {
			sd.setPrice((double) stockMap.get(c.getCode()));
			}
			stockDetail.add(sd);
		}		
		return stockDetail;
	}

	@Override
	public StockDetail getLatestByCompanyCode(int companycode) {
		//template.send(kafkaTopic, "Get Latest Stock Price - Company Code : " + companycode);
		var companyDetails = restTemplate.getForEntity("http://COMPANY/api/v1.0/market/company/info/" + companycode,
				Company.class);
		Company company = companyDetails.getBody();

		var latestStockPricePerCompany = restTemplate
				.getForEntity("http://STOCK/api/v1.0/market/stock/latest/price/" + companycode, StockDTO.class);
		StockDTO stock = latestStockPricePerCompany.getBody();

		StockDetail stockDetail = new StockDetail();
		stockDetail.setName(company.getName());
		stockDetail.setCeo(company.getCeo());
		stockDetail.setCode(company.getCode());
		stockDetail.setTurnover(company.getTurnover());
		stockDetail.setExchange(company.getExchange());
		stockDetail.setWebsite(company.getWebsite());
		stockDetail.setPrice(stock.getPrice());
		return stockDetail;
	}

	@Override
	public void deleteCompanyDetails(int companycode) {
		//template.send(kafkaTopic, "Delete All Details - Company Code : " + companycode);
		restTemplate.delete("http://COMPANY/api/v1.0/market/company/delete/" + companycode);
		restTemplate.delete("http://STOCK/api/v1.0/market/stock/delete/" + companycode);		
	}

	@Override
	public StockDetailList getDetailList(int companycode, StockDateRequestDTO stockDate) {
		//template.send(kafkaTopic, "Get All By Date - Company Code : " + companycode);
		var companyDetails = restTemplate.getForEntity("http://COMPANY/api/v1.0/market/company/info/"+companycode, 
				Company.class);
		Company company = companyDetails.getBody();
		
		String startDate = null;
		String endDate = null;
		if(stockDate.getStartDate()!=null) {			
			startDate = (stockDate.getStartDate().getYear()+1900)+"-"+(stockDate.getStartDate().getMonth()+1)+"-"+stockDate.getStartDate().getDate();
			System.out.println(startDate);
		}
		if(stockDate.getEndDate()!=null) {			
			endDate = (stockDate.getEndDate().getYear()+1900)+"-"+(stockDate.getEndDate().getMonth()+1)+"-"+stockDate.getEndDate().getDate();
			System.out.println(endDate);
		}
		
		Stock[] stock = null;
		if(stockDate.getStartDate()!=null && stockDate.getEndDate()!=null) {
		var latestStockPricePerCompany = restTemplate.getForEntity("http://STOCK/api/v/1.0/market/stock/get/"+companycode+"/"+startDate+"/"+endDate, 
				Stock[].class);
		stock = latestStockPricePerCompany.getBody();	
		}
		else if(stockDate.getStartDate()!=null) {
			var latestStockPricePerCompany = restTemplate.getForEntity("http://STOCK/api/v/1.0/market/stock/get/"+companycode+"/"+startDate, 
					Stock[].class);
			stock = latestStockPricePerCompany.getBody();	
			}
		else if(stockDate.getEndDate()!=null) {
			var latestStockPricePerCompany = restTemplate.getForEntity("http://STOCK/api/v/1.0/market/stock/get/"+companycode+"/"+"/"+endDate, 
					Stock[].class);
			stock = latestStockPricePerCompany.getBody();	
			}
		else if(stockDate.getStartDate()==null && stockDate.getEndDate()==null) {
			System.out.println("Both null");
			var latestStockPricePerCompany = restTemplate.getForEntity("http://STOCK/api/v/1.0/market/stock/get/"+companycode, 
					Stock[].class);
			stock = latestStockPricePerCompany.getBody();	
			
			}
		

		List<StockResponse> srlist = new ArrayList<>();
		String dateString=null;
		String timeString=null;
		double max = 0;
		double min = 0;
		double avg=0;
		int flag = 0;
		for(Stock s : stock) {
			flag++;
			StockResponse sr = new StockResponse();
			System.out.println(s);
			sr.setCode(s.getId());
			sr.setPrice(s.getPrice());
			Date de = s.getDate();
			int month=de.getMonth()+1;
			int year = de.getYear()+1900;
			dateString = de.getDate()+"/"+month+"/"+year;
			sr.setDate(dateString);
			timeString = de.getHours()+":"+de.getMinutes()+":"+de.getSeconds();
			sr.setTime(timeString);
			
			if(flag==1) {
				max=s.getPrice();
				min=s.getPrice();
			}
			max=s.getPrice()>max?s.getPrice():max;
			min=s.getPrice()<min?s.getPrice():min;
			
			avg = avg+s.getPrice();

			
			srlist.add(sr);
		}
		
		StockDetailList sd = new StockDetailList();
		sd.setCompany(company);
		sd.setStock(srlist);
		sd.setMax(max);
		sd.setMin(min);
		sd.setAvg(avg/stock.length);
		System.out.println(sd);
		return sd;
	}

	
}
