package com.andzj.library.dao.impl;

import java.awt.TexturePaint;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.hql.ast.HqlASTFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.andzj.library.bean.BorrowInformation;
import com.andzj.library.bean.CommentInformation;
import com.andzj.library.dao.BorrowDao;

public class BorrowDaoImpl extends HibernateDaoSupport implements BorrowDao{
	/********************************操作图书借阅********************************************/
	//增加一条借阅记录
	@Override
	public BorrowInformation addBorrow(BorrowInformation borrow) throws HibernateException
	{
		this.getHibernateTemplate().save(borrow);
		String hql = "from BorrowInformation where book_isbn = ? AND borrow_account_name = ? ";
		List<BorrowInformation> list = getHibernateTemplate().find(hql,borrow.getBookIsbn(),borrow.getBorrowAccountName());
		if (list != null && list.size() >0)
		{
			return list.get(0);
		}
		return null;
	}
	
	//删除一条借阅记录
	@Override
	public BorrowInformation deleteBorrow(String borrowId) throws HibernateException
	{
		String hql = "from BorrowInformation where borrow_id = ?";
		List<BorrowInformation> list = getHibernateTemplate().find(hql,borrowId);
		if (list != null && list.size() >0)
		{
			getHibernateTemplate().delete("BorrowInformation",list.get(0));
			return list.get(0);
		}
		return null;
	}
	
	//修改一条借阅记录 可修改归还时间和借阅状态
	@Override
	public boolean updateBorrow(BorrowInformation borrow) throws HibernateException
	{
		String hql = "from BorrowInformation where book_isbn = ? AND borrow_account_name = ? ";
		List<BorrowInformation> list = getHibernateTemplate().find(hql,borrow.getBookIsbn(),borrow.getBorrowAccountName());
		if (list != null && list.size() >0)
		{
			BorrowInformation borrowInformation = list.get(0);
			borrowInformation.setReturnTime(borrow.getReturnTime());
			borrowInformation.setBorrowState(borrow.getBorrowState());
			getHibernateTemplate().update("BorrowInformation",borrowInformation);
			return true;
		}
		return false;
	}
	
	//查询所有记录
	public List<BorrowInformation> findAllBorrow() throws HibernateException
	{
		String hql = "from BorrowInformation ";
		List<BorrowInformation> list = getHibernateTemplate().find(hql);
		if (list != null && list.size() >0)
		{
			return list;
		}
		return null;
	}
	
	//查询某书的借阅记录
	@Override
	public List<BorrowInformation> searchBorrowByBookIsbn(String bookIsbn) throws HibernateException
	{
		String hql = "from BorrowInformation where book_isbn = ? ";
		List<BorrowInformation> list = getHibernateTemplate().find(hql,bookIsbn);
		if (list != null && list.size() >0)
		{
			return list;
		}
		return null;
	}
	
	//查询某账号是否借阅了某书
	@Override
	public boolean checkBorrowExisted(String borrowAccountName,String bookIsbn) throws HibernateException
	{
		String hql = "from BorrowInformation where book_isbn = ? AND borrow_account_name = ? ";
		List<BorrowInformation> list = getHibernateTemplate().find(hql,bookIsbn,borrowAccountName);
		if (list != null && list.size() >0)
		{
			return true;
		}
		return false;
	}

	
	//查询某账号的借阅记录
	@Override
	public List<BorrowInformation> searchBorrowByAccountName(String borrowAccountName) throws HibernateException
	{
		String hql = "from BorrowInformation where borrow_account_name = ? ";
		List<BorrowInformation> list = getHibernateTemplate().find(hql,borrowAccountName);
		if (list != null && list.size() >0)
		{
			return list;
		}
		return null;
	}
	
	//查询某书的借阅记录条数
	@Override
	public int findBookBorrowNumber(String bookIsbn) throws HibernateException
	{
		String hql = "from BorrowInformation where book_isbn = ? ";
		List<BorrowInformation> list = getHibernateTemplate().find(hql,bookIsbn);
		if (list != null && list.size() >0)
		{
			return list.size();
		}
		return 0;
	}
	
	//通过数据库id查询借阅记录
	@Override
	public List<BorrowInformation> searchBorrowById(String borrowId) throws HibernateException
	{
		String hql = "from BorrowInformation where borrow_id = ?";
		List<BorrowInformation> list = getHibernateTemplate().find(hql,borrowId);
		if (list != null && list.size() >0)
		{
			return list;
		}
		return null;
	}
}
