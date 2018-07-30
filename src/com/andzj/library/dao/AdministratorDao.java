package com.andzj.library.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.andzj.library.bean.AdministratorAccount;

/**
 * 管理员访问的DAO接口
 * @author zj
 *
 */
public interface AdministratorDao {
	/***************************操作管理员******************************/
	//添加管理员账户
	public boolean addAdministratorAccount(AdministratorAccount account)  throws HibernateException;
	
	//管理员帐号登录
	public AdministratorAccount administratorLogin(AdministratorAccount account)  throws HibernateException;
	
	//查询所有管理员帐号
	public List<AdministratorAccount> findAllAdminAccount()  throws HibernateException;
	
	//查询指定管理员帐号信息
	public List<AdministratorAccount> searchAdminAccount(String accountName) throws HibernateException;
	
	//查询指定管理员帐号是否存在
	public boolean checkAdminiAccountExisted(String accountName) throws HibernateException;
	
	//删除管理员帐号
	public AdministratorAccount deleteAdministratorAccount(String accountName) throws HibernateException;
	
	//更改管理员帐号的昵称和密码
	public boolean updateAdministratorAccount(String accountName,String newNickname,String newPasswordMD5) throws HibernateException;
	
	

		
	
	
	
	
	
}
