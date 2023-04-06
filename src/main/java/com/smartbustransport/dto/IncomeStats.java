package com.smartbustransport.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IncomeStats {
	
	
    private Metrics day;
    private Metrics weekly;
    private Metrics monthly;
    private Metrics yearly;

}
