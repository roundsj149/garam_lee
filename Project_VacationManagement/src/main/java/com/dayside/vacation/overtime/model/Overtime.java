package com.dayside.vacation.overtime.model;

import java.util.Date;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Overtime {
	
	private String overtimeApplyNo;
	private String employeeNo;
	private Date applyDt;
	private String startDt;
	private String endDt;
	private String overtimeType;
	private String overtimeContent;
	
}
