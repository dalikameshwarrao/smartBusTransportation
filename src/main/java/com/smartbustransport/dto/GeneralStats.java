package com.smartbustransport.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GeneralStats {
	private Stats day;
    private Stats weekly;
    private Stats monthly;
    private Stats yearly;
}
