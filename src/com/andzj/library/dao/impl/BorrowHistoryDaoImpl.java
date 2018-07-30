package com.andzj.library.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.andzj.library.bean.BorrowHistoryInformation;
import com.andzj.library.dao.BorrowHistoryDao;

public class BorrowHistoryDaoImpl extends HibernateDaoSupport implements BorrowHistoryDao{
	/******************************����������ʷ��¼*********************************/
	//����һ��������ʷ
	@Override
	public boolean addBorrowHistory(BorrowHistoryInformation borrowHistoryInformation) throws HibernateException
	{
		getHibernateTemplate().save("BorrowHistoryInformation",borrowHistoryInformation);
		return true;
	}
	
	//Ϊĳ��������ʷ�����ӱ�ע
	@Override
	public boolean addBorrowHistoryNotes(String historyId,String historyNotes) throws HibernateException
	{
		String hql = "from BorrowHistoryInformation where history_id = ?";
		List<BorrowHistoryInformation> list = getHibernateTemplate().find(hql,historyId);
		if (list != null && list.size() >0)
		{
			BorrowHistoryInformation information = list.get(0);
			information.setHistoryNotes(historyNotes);
			getHibernateTemplate().update("BorrowHistoryInformation",information);
			return true;
		}
		return false;
	}
	//�޸�ĳ��������ʷ�ı�ע
	@Override
	public boolean updateBorrowHistoryNotes(String historyId,String newHistoryNotes) throws HibernateException
	{
		String hql = "from BorrowHistoryInformation where history_id = ?";
		List<BorrowHistoryInformation> list = getHibernateTemplate().find(hql,historyId);
		if (list != null && list.size() >0)
		{
			BorrowHistoryInformation information = list.get(0);
			information.setHistoryNotes(newHistoryNotes);
			getHibernateTemplate().update("BorrowHistoryInformation",information);
			return true;
		}
		return false;
	}
	
	//��ѯ���н�����ʷ��¼
	@Override
	public List<BorrowHistoryInformation> findAllBorrowHistory() throws HibernateException
	{
		String hql = "from BorrowHistoryInformation ";
		List<BorrowHistoryInformation> list = getHibernateTemplate().find(hql);
		if (list != null && list.size() >0)
		{
			return list;
		}
		return null;
	}
		
	//��ѯĳ��Ľ�����ʷ��¼
	@Override
	public List<BorrowHistoryInformation> searchBorrowHistoryByBookIsbn(String bookIsbn) throws HibernateException
	{
		String hql = "from BorrowHistoryInformation where book_isbn = ?";
		List<BorrowHistoryInformation> list = getHibernateTemplate().find(hql,bookIsbn);
		if (list != null && list.size() >0)
		{
			return list; 
		}
		return null;
	}
		
	//��ѯĳ�˺��Ƿ������ĳ��
	@Override
	public boolean checkBorrowHistoryExisted(String accountName,String bookIsbn) throws HibernateException
	{
		String hql = "from BorrowHistoryInformation where account_name = ? AND book_isbn = ? ";
		List<BorrowHistoryInformation> list = getHibernateTemplate().find(hql,accountName,bookIsbn);
		if (list != null && list.size() >0)
		{
			return true; 
		}
		return true;
	}
		
	//��ѯĳ�˺ŵĽ�����ʷ��¼
	@Override
	public List<BorrowHistoryInformation> searchBorrowHistoryByAccountName(String accountName) throws HibernateException
	{
		String hql = "from BorrowHistoryInformation where account_name = ?";
		List<BorrowHistoryInformation> list = getHibernateTemplate().find(hql,accountName);
		if (list != null && list.size() >0)
		{
			return list; 
		}
		return null;
	}

	//ͨ�����ݿ�id��ѯ������ʷ��¼
	@Override
	public List<BorrowHistoryInformation> searchBorrowHistoryById(String historyId) throws HibernateException
	{
		String hql = "from BorrowHistoryInformation where history_id = ?";
		List<BorrowHistoryInformation> list = getHibernateTemplate().find(hql,historyId);
		if (list != null && list.size() >0)
		{
			return list; 
		}
		return null;
	}
		
	//��ѯĳ��Ľ�����ʷ��¼����
	@Override
	public int findBookBorrowHistoryNumber(String bookIsbn) throws HibernateException
	{
		String hql = "from BorrowHistoryInformation where book_isbn = ?";
		List<BorrowHistoryInformation> list = getHibernateTemplate().find(hql,bookIsbn);
		if (list != null && list.size() >0)
		{
			return list.size(); 
		}
		return 0;
	}
	
}
