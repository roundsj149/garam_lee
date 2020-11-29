package com.dayside.vacation.common.exception;

import com.dayside.vacation.common.result.ResultCodeDesc;

public class VacationException extends Exception {

	private static final long serialVersionUID = -2032881245491918453L;
	private ResultCodeDesc ResultCodeDesc;
	
	public VacationException(ResultCodeDesc ResultCodeDesc) {
		this.setResultCodeDesc(ResultCodeDesc);
	}

	public ResultCodeDesc getResultCodeDesc() {
		return ResultCodeDesc;
	}

	public void setResultCodeDesc(ResultCodeDesc resultCodeDesc) {
		ResultCodeDesc = resultCodeDesc;
	}
}
