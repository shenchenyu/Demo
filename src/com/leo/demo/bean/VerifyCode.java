package com.leo.demo.bean;

import java.io.Serializable;

public class VerifyCode implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean verifyResult;
	public boolean isVerifyResult() {
		return verifyResult;
	}
	public void setVerifyResult(boolean verifyResult) {
		this.verifyResult = verifyResult;
	}
}
