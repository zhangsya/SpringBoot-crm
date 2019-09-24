package com.tedu.common.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class JsonResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8514020192025088535L;
	
	/**
	 * 状态码
	 * 1表示SUCCESS
	 * 0表示ERROR
	 */
	private int state = 1;
	/**
	 * 状态信息
	 */
	private String message = "ok";
	/**
	 * 正确数据
	 */
	private Object data;
	/**
	 * 
	 */
	public JsonResult() {
		super();
	}
	
	/**
	 * @param message
	 */
	public JsonResult(String message) {
		super();
		this.message = message;
	}
	/**
	 * @param data
	 */
	public JsonResult(Object data) {
		super();
		this.data = data;
	}
	
	/**出现异常时时调用*/
	public JsonResult(Throwable t){
		this.state=0;
		this.message=t.getMessage();
	}

}
