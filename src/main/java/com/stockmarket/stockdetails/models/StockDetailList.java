package com.stockmarket.stockdetails.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDetailList {

	private Company company;
	private List<StockResponse> stock;
	private double max;
	private double min;
	private double avg;

}
