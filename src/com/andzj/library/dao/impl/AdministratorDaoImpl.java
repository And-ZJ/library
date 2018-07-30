package com.andzj.library.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.andzj.library.bean.AdministratorAccount;
import com.andzj.library.bean.BookInformation;
import com.andzj.library.bean.BorrowInformation;
import com.andzj.library.bean.CommentInformation;
import com.andzj.library.bean.UserAccount;
import com.andzj.library.dao.AdministratorDao;

/**
 * 管理员访问的DAO实现类
 * @author zj
 *
 */
public class AdministratorDaoImpl extends HibernateDaoSupport implements AdministratorDao {

	/***************************操作管理员******************************/
	//添加管理员账户
	@Override
	public boolean addAdministratorAccount(AdministratorAccount account)  throws HibernateException{
		getHibernateTemplate().save(account);
		return true;
	}
	
	//管理员帐号登录
	@Override
	public AdministratorAccount administratorLogin(AdministratorAccount account)  throws HibernateException{
		String hql = "from AdministratorAccount where account_name = ?";
		List<AdministratorAccount> list = this.getHibernateTemplate().find(hql,account.getAccountName());
		
		if (list != null && list.size() >0)
		{
			return list.get(0);
		}
		return null;
	}
	
	//删除管理员帐号
	@Override
	public AdministratorAccount deleteAdministratorAccount(String accountName) throws HibernateException
	{
		List<AdministratorAccount> accounts=new ArrayList<AdministratorAccount>();
		//Object[] params = new Object[] { accountName};
		accounts = getHibernateTemplate().find("from AdministratorAccount where account_name = ? ",accountName);	
		if (accounts != null && accounts.size() > 0)
		{
			getHibernateTemplate().delete(accounts.get(0));
			return accounts.get(0);	
		}
		return null;
	}
	
	//更改管理员帐号的昵称和密码
	@Override
	public boolean updateAdministratorAccount(String accountName,String newNickname,String newPasswordMD5) throws HibernateException
	{
		String hql = "from AdministratorAccount where account_name = ?";
		List<AdministratorAccount> list = this.getHibernateTemplate().find(hql,accountName);
		
		if (list != null && list.size() >0)
		{
			AdministratorAccount account = list.get(0);
			account.setAdministratorNickname(newNickname);
			account.setPasswordMD5(newPasswordMD5);
			getHibernateTemplate().update("AdministratorAccount",account);
			return true;
		}
		return false;
	}
	
	//查询所有管理员帐号
	@Override
	public List<AdministratorAccount> findAllAdminAccount()  throws HibernateException{
		List<AdministratorAccount> accounts = getHibernateTemplate().find("from AdministratorAccount ");
		if (accounts !=null && accounts.size() >0)
		{
			return accounts;
		}
		return null;
	}
	
	//查询指定管理员帐号信息
	public List<AdministratorAccount> searchAdminAccount(String accountName) throws HibernateException
	{
		String hql = "from AdministratorAccount where account_name = ?";
		List<AdministratorAccount> list = this.getHibernateTemplate().find(hql,accountName);
		
		if (list != null && list.size() >0)
		{
			return list;
		}
		return null;
	}
	
		
	//查询指定管理员帐号是否存在
	@Override
	public boolean checkAdminiAccountExisted(String accountName) throws HibernateException
	{
		String hql = "from AdministratorAccount where account_name = ?";
		List<AdministratorAccount> list = this.getHibernateTemplate().find(hql,accountName);
		
		if (list != null && list.size() > 0)
		{
			return true;
		}
		return false;
	}

}
