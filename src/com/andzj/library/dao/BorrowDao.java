package com.andzj.library.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.andzj.library.bean.BorrowInformation;

public interface BorrowDao {
	/********************************����ͼ�����********************************************/
	//����һ�����ļ�¼
	public BorrowInformation addBorrow(BorrowInformation borrow) throws HibernateException;
	
	//ɾ��һ�����ļ�¼
	public BorrowInformation deleteBorrow(String borrowId) throws HibernateException;
	
	//�޸�һ�����ļ�¼
	public boolean updateBorrow(BorrowInformation borrow) throws HibernateException;
	
	//��ѯ���м�¼
	public List<BorrowInformation> findAllBorrow() throws HibernateException;
	
	//��ѯĳ��Ľ��ļ�¼
	public List<BorrowInformation> searchBorrowByBookIsbn(String bookIsbn) throws HibernateException;
	
	//��ѯĳ�˺��Ƿ������ĳ��
	public boolean checkBorrowExisted(String borrowAccountName,String bookIsbn) throws HibernateException;
	
	//��ѯĳ�˺ŵĽ��ļ�¼
	public List<BorrowInformation> searchBorrowByAccountName(String borrowAccountName) throws HibernateException;

	//ͨ�����ݿ�id��ѯ���ļ�¼
	public List<BorrowInformation> searchBorrowById(String borrowId) throws HibernateException;
	
	//��ѯĳ��Ľ��ļ�¼����
	public int findBookBorrowNumber(String bookIsbn) throws HibernateException;
}
