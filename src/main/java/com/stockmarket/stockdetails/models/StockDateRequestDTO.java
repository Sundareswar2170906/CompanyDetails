package com.stockmarket.stockdetails.models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDateRequestDTO {
	private Date startDate;
	private Date endDate;
}
