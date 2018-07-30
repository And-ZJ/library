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
 * ����Ա���ʵ�DAOʵ����
 * @author zj
 *
 */
public class AdministratorDaoImpl extends HibernateDaoSupport implements AdministratorDao {

	/***************************��������Ա******************************/
	//��ӹ���Ա�˻�
	@Override
	public boolean addAdministratorAccount(AdministratorAccount account)  throws HibernateException{
		getHibernateTemplate().save(account);
		return true;
	}
	
	//����Ա�ʺŵ�¼
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
	
	//ɾ������Ա�ʺ�
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
	
	//���Ĺ���Ա�ʺŵ��ǳƺ�����
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
	
	//��ѯ���й���Ա�ʺ�
	@Override
	public List<AdministratorAccount> findAllAdminAccount()  throws HibernateException{
		List<AdministratorAccount> accounts = getHibernateTemplate().find("from AdministratorAccount ");
		if (accounts !=null && accounts.size() >0)
		{
			return accounts;
		}
		return null;
	}
	
	//��ѯָ������Ա�ʺ���Ϣ
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
	
		
	//��ѯָ������Ա�ʺ��Ƿ����
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
