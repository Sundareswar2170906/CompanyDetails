package com.stockmarket.stockdetails.models;

import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {

	private int code;	
	private String name;	
	private String ceo;	
	public BigInteger turnover;
	private String website;	
	private String exchange;
	
}
