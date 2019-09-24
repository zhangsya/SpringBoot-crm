package com.tedu.common.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * 用户新增页面对角色信息的封装
 * @author Administrator
 *
 */
@Data
public class CheckRolesBox implements Serializable {

	private static final long serialVersionUID = -3545229079346446993L;
	
	private Integer id;
	private String name;

}
