package com.stockmarket.stockdetails.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.stockmarket.stockdetails.models.Company;
import com.stockmarket.stockdetails.models.StockDateRequestDTO;
import com.stockmarket.stockdetails.models.StockDetail;
import com.stockmarket.stockdetails.models.StockDetailList;
import com.stockmarket.stockdetails.models.StockResponse;
import com.stockmarket.stockdetails.service.ServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ControllerTest {

	@InjectMocks
	private Controller controller;

	@Mock
	private ServiceImpl service;

	@Test
	void getAllLatest() {

		List<StockDetail> sdList = new ArrayList<>(Arrays.asList(
				new StockDetail(1, 18.2, "Company1", "Sundareswar Das", new BigInteger("120000000"),
						"https://www.56789.com", "BSE"),
				new StockDetail(1, 23.2, "Company2", "Sundareswar Das", new BigInteger("120000000"),
						"https://www.56789.com", "BSE"),
				new StockDetail(1, 45.2, "Company3", "Sundareswar Das", new BigInteger("120000000"),
						"https://www.56789.com", "BSE")));

		Mockito.lenient().when(service.getAllLatest()).thenReturn(sdList);
		assertEquals(3, service.getAllLatest().size());
		verify(service, times(1)).getAllLatest();

	}

	@Test
	void getLatestByCompanyCode() {

		StockDetail sd = new StockDetail(1, 18.2, "Company1", "Sundareswar Das", new BigInteger("120000000"),
				"https://www.56789.com", "BSE");

		Mockito.lenient().when(service.getLatestByCompanyCode(1)).thenReturn(sd);
		assertEquals("Company1", service.getLatestByCompanyCode(1).getName());
		verify(service, times(1)).getLatestByCompanyCode(1);

	}

	@Test
	void deleteCompanyDetails() {

		service.deleteCompanyDetails(1);
		verify(service, times(1)).deleteCompanyDetails(1);

	}

	@Test
	void getDetailList() {
		
		Company c = new Company(1,"Company1","Sundareswar Das",new BigInteger("120000000"),"https://www.56789.com","BSE");
		List<StockResponse> sr = new ArrayList<>();
		StockDateRequestDTO dateDTO = new StockDateRequestDTO(new Date(), null);
 		StockDetailList sd = new StockDetailList(c,sr,0.0,0.0,0.0);

 		Mockito.lenient().when(service.getDetailList(1, dateDTO)).thenReturn(sd);
 		
 		assertEquals("BSE", service.getDetailList(1, dateDTO).getCompany().getExchange());

	}

}
