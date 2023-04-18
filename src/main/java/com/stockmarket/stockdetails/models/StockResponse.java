package com.stockmarket.stockdetails.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public class StockResponse {
		
		private int code;
		private double price;
		private String date;
		private String time;
		
	}

