package com.andzj.library.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.hql.ast.HqlASTFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.andzj.library.bean.AdministratorAccount;
import com.andzj.library.bean.UserAccount;
import com.andzj.library.dao.UserDao;

public class UserDaoImpl extends HibernateDaoSupport implements UserDao{
	/*******************************操作普通用户*************************************/
	//普通用户注册
	@Override
	public boolean addUserAccount(UserAccount account) throws HibernateException
	{
		getHibernateTemplate().save(account);
		return true;
	}
	
	//普通用户登录
	@Override
	public UserAccount userLogin(String accountName) throws HibernateException
	{
		String hql = "from UserAccount where account_name = ?";
		List<UserAccount> list = this.getHibernateTemplate().find(hql,accountName);
		
		if (list != null && list.size() >0)
		{
			return list.get(0);
		}
		return null;
	}
	
	//普通用户自动登录验证
	@Override
	@Deprecated
	public UserAccount userAutoLogin(String accountName,String passwordMD5) throws HibernateException
	{
		String hql = "from UserAccount where account_name = ?";
		List<UserAccount> list = getHibernateTemplate().find(hql,accountName);
		if (list != null && list.size() >0)
		{
			UserAccount account = list.get(0);
			if (passwordMD5.equals(account.getPasswordMD5()))
			{
				return account;
			}
		}
		return null;
	}
	
	
	//删除普通用户
	@Override
	public UserAccount deleteUserAccount(String accountName) throws HibernateException
	{
		List<UserAccount> accounts=new ArrayList<UserAccount>();
		//Object[] params = new Object[] { accountName};
		accounts = getHibernateTemplate().find("from UserAccount where account_name = ? ",accountName);	
		if (accounts != null && accounts.size() > 0)
		{
			getHibernateTemplate().delete(accounts.get(0));
			return accounts.get(0);	
		}
		return null;
	}
	
	//修改普通用户信息
	public boolean updateUserAccount(UserAccount account) throws HibernateException
	{
		String hql = "from UserAccount where account_name = ?";
		List<UserAccount> list = this.getHibernateTemplate().find(hql,account.getAccountName());
		
		if (list != null && list.size() >0)
		{
			account.setAccountId(list.get(0).getAccountId());
			account.setRegisterTime(list.get(0).getRegisterTime());
			getHibernateTemplate().update("UserAccount",account);
			return true;
		}
		return false;
	}

	
	//普通用户修改普通信息
	@Override
	public UserAccount updateUserAccount(String accountName,String newBindStudentAccount,String newNickname,String newSex,String newDescribeWords,String updateTimeStr) throws HibernateException
	{
		String hql = "from UserAccount where account_name = ?";
		List<UserAccount> list = this.getHibernateTemplate().find(hql,accountName);
		
		if (list != null && list.size() >0)
		{
			UserAccount account = list.get(0);
			account.setBindStudentAccount(newBindStudentAccount);
			account.setUserNickname(newNickname);
			account.setUserSex(newSex);
			account.setUserDescribeWords(newDescribeWords);
			account.setUserUpdateTime(updateTimeStr);
			getHibernateTemplate().update("UserAccount",account);
			account.setPasswordMD5("");
			return account;
		}
		return null;
	}
	
	//普通用户修改密码
	@Override
	public boolean updateUserAccount(String accountName,String newPasswordMD5,String updateTime) throws HibernateException
	{
		String hql = "from UserAccount where account_name = ?";
		List<UserAccount> list = this.getHibernateTemplate().find(hql,accountName);
		
		if (list != null && list.size() >0)
		{
			UserAccount account = list.get(0);
			account.setPasswordMD5(newPasswordMD5);
			account.setUserUpdateTime(updateTime);
			getHibernateTemplate().update("UserAccount",account);
			return true;
		}
		return false;
	}

	//更改图片地址
	public UserAccount updateUserImage(String accountName,String imageAddress,String updateTime) throws HibernateException
	{
		String hql = "from UserAccount where account_name = ?";
		List<UserAccount> list = this.getHibernateTemplate().find(hql,accountName);
		
		if (list != null && list.size() >0)
		{
			UserAccount account = list.get(0);
			account.setUserImageStr(imageAddress);
			account.setUserUpdateTime(updateTime);
			getHibernateTemplate().update("UserAccount",account);
			return account;
		}
		return null;
	}
	
	
	//查询所有普通用户
	@Override
	public List<UserAccount> findAllUserAccount() throws HibernateException
	{
		String hql = "from UserAccount ";
		List<UserAccount> list = this.getHibernateTemplate().find(hql);
		
		if (list != null && list.size() > 0)
		{
			return list;
		}
		return null;
	}
	
	//查询指定普通用户的信息
	public List<UserAccount> searchUserAccount(String accountName) throws HibernateException
	{
		String hql = "from UserAccount where account_name = ?";
		List<UserAccount> list = this.getHibernateTemplate().find(hql,accountName);
		
		if (list != null && list.size() > 0)
		{
			return list;
		}
		return null;
	}

	
	//查询指定普通用户帐号是否存在
	@Override
	public boolean checkUserAccountExisted(String accountName) throws HibernateException
	{
		String hql = "from UserAccount where account_name = ?";
		List<UserAccount> list = this.getHibernateTemplate().find(hql,accountName);
		
		if (list != null && list.size() > 0)
		{
			return true;
		}
		return false;
	}
	
	//检查某密码的正确性
	public boolean checkUserPassword(String accountName,String passwordMD5) throws HibernateException
	{
		String hql = "from UserAccount where account_name = ? AND password_md5 = ?";
		List<UserAccount> list = this.getHibernateTemplate().find(hql,accountName,passwordMD5);
		
		if (list != null && list.size() > 0)
		{
			return true;
		}
		return false;
	}
}
