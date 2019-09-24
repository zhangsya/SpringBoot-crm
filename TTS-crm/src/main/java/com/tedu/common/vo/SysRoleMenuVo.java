package com.tedu.common.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class SysRoleMenuVo implements Serializable{

	private static final long serialVersionUID = -7912980148138451268L;

	/**
	 * 角色名称
	 */
	private String name;
	/**
	 * 角色描述
	 */
	private String note;
	/**
	 * 角色对应的菜单ID
	 */
	private List<Integer> menuIds;
}
