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
	/********************************����ͼ�����********************************************/
	//����һ�����ļ�¼
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
	
	//ɾ��һ�����ļ�¼
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
	
	//�޸�һ�����ļ�¼ ���޸Ĺ黹ʱ��ͽ���״̬
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
	
	//��ѯ���м�¼
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
	
	//��ѯĳ��Ľ��ļ�¼
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
	
	//��ѯĳ�˺��Ƿ������ĳ��
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

	
	//��ѯĳ�˺ŵĽ��ļ�¼
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
	
	//��ѯĳ��Ľ��ļ�¼����
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
	
	//ͨ�����ݿ�id��ѯ���ļ�¼
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
