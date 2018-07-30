package com.andzj.library.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.andzj.library.bean.BorrowHistoryInformation;
import com.andzj.library.dao.BorrowHistoryDao;

public class BorrowHistoryDaoImpl extends HibernateDaoSupport implements BorrowHistoryDao{
	/******************************操作借阅历史记录*********************************/
	//增加一条借阅历史
	@Override
	public boolean addBorrowHistory(BorrowHistoryInformation borrowHistoryInformation) throws HibernateException
	{
		getHibernateTemplate().save("BorrowHistoryInformation",borrowHistoryInformation);
		return true;
	}
	
	//为某条借阅历史的增加备注
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
	//修改某条借阅历史的备注
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
	
	//查询所有借阅历史记录
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
		
	//查询某书的借阅历史记录
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
		
	//查询某账号是否借阅了某书
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
		
	//查询某账号的借阅历史记录
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

	//通过数据库id查询借阅历史记录
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
		
	//查询某书的借阅历史记录条数
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
