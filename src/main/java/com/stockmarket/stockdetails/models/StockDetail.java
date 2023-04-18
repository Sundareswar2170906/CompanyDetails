package com.stockmarket.stockdetails.models;

import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDetail {

	private int code;
	private double price;	
	private String name;	
	private String ceo;	
	private BigInteger turnover;
	private String website;
	private String exchange;
	
}
