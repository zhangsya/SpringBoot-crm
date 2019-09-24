package com.tedu.sys.service.realm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tedu.sys.dao.SysMenuDao;
import com.tedu.sys.dao.SysRoleMenuDao;
import com.tedu.sys.dao.SysUserDao;
import com.tedu.sys.dao.SysUserRoleDao;
import com.tedu.sys.entity.SysUser;

@Service
public class ShiroUserRealm extends AuthorizingRealm {

	@Autowired
	SysUserDao sysUserDao;
	
	@Autowired
	SysUserRoleDao sysUserRoleDao;
	
	@Autowired
	SysRoleMenuDao sysRoleMenuDao;
	
	@Autowired
	SysMenuDao sysMenuDao;

	/**
	 * 设置凭证匹配器(与用户添加操作使用相同的加密算法)
	 */
	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		// 构建凭证匹配对象
		HashedCredentialsMatcher cMatcher = new HashedCredentialsMatcher();
		// 设置加密算法
		cMatcher.setHashAlgorithmName("MD5");
		// 设置加密次数
		cMatcher.setHashIterations(1);
		super.setCredentialsMatcher(cMatcher);
	}

	/**
	 * 通过此方法完成认证数据的获取及封装,系统 底层会将认证数据传递认证管理器，由认证 管理器完成认证操作。
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 1.获取用户名(用户页面输入)
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();
		// 2.基于用户名查询用户信息
		SysUser user = sysUserDao.findUserByUserName(username);
		// 3.判定用户是否存在
		if (user == null)
			throw new UnknownAccountException();
		// 4.判定用户是否已被禁用。
		if (user.getValid() == 0)
			throw new LockedAccountException();

		// 5.封装用户信息
		ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());
		// 记住：构建什么对象要看方法的返回值
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, // principal (身份)
				user.getPassword(), // hashedCredentials
				credentialsSalt, // credentialsSalt
				getName());// realName
		// 6.返回封装结果
		return info;// 返回值会传递给认证管理器(后续
		// 认证管理器会通过此信息完成认证操作)
	}

	/**
	 * 完成授权信息的获取以及封装
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		System.out.println("获取授权信息:GetAuthorizationInfo");
		// 1.获取登陆用户信息
		SysUser user = (SysUser) arg0.getPrimaryPrincipal();
		// 2.基于用户id获取用户对应的角色
		if (user == null)
			throw new AuthorizationException();
		List<Integer> roleIds = sysUserRoleDao.findRoleIdsByUserId(user.getId());
		// 3.基于角色id获取对应的菜单信息
		if (roleIds == null || roleIds.size() == 0)
			throw new AuthorizationException();
		Integer[] array = {};
		List<Integer> menuIds = sysRoleMenuDao.findMenuIdsByRoleIds(roleIds.toArray(array));
		if (menuIds == null || menuIds.size() == 0)
			throw new AuthorizationException();
		// 4.基于菜单id获取对应的权限标识(permisssion)
		List<String> permissions = sysMenuDao.findPermissions(menuIds.toArray(array));
		if (permissions == null || permissions.size() == 0)
			throw new AuthorizationException();
		// 5.对权限标识permission进行封装并返回
		Set<String> setPermissions = new HashSet<>();
		for (String p : permissions) {
			if (!StringUtils.isEmpty(p)) {
				setPermissions.add(p);
			}
		}
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(setPermissions);
		return info;// 返回给SecurityManager
	}

}
