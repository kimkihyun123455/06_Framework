package edu.kh.project.calendar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Calendar {

	private int CalNo;
	private String startDate;
	private String endDate;
	private String CalTitle;

}
