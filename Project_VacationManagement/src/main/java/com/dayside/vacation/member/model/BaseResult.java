package com.dayside.vacation.member.model;

import com.dayside.vacation.common.result.ResultCodeDesc;

/**
 * 결과 코드/설명
 * @author April
 *
 */
public class BaseResult {

	private String returnCode;
	private String returnDesc;

	public BaseResult() {

	}

	public BaseResult(ResultCodeDesc resultCodeDesc) {
		this.returnCode = resultCodeDesc.getResultCodeStr();
		this.returnDesc = resultCodeDesc.getResultDescStr();
	}
	
	public BaseResult(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnDesc() {
		return returnDesc;
	}

	public void setReturnDesc(String returnDesc) {
		this.returnDesc = returnDesc;
	}
}
