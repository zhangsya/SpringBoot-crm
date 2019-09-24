package com.tedu.common.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class Node implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 4995315492086935481L;
	
	private Integer id;
	private String name;
	private String parentId;

}
