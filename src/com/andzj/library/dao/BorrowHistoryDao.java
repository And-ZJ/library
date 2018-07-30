package com.andzj.library.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.andzj.library.bean.BorrowHistoryInformation;

public interface BorrowHistoryDao {
	/******************************����������ʷ��¼*********************************/
	//����һ��������ʷ
	public boolean addBorrowHistory(BorrowHistoryInformation borrowHistoryInformation) throws HibernateException;
	
	//Ϊĳ��������ʷ�����ӱ�ע
	public boolean addBorrowHistoryNotes(String historyId,String historyNotes) throws HibernateException;
	
	//�޸�ĳ��������ʷ�ı�ע
	public boolean updateBorrowHistoryNotes(String historyId,String newHistoryNotes) throws HibernateException;
	
	//��ѯ���н�����ʷ��¼
	public List<BorrowHistoryInformation> findAllBorrowHistory() throws HibernateException;
		
	//��ѯĳ��Ľ�����ʷ��¼
	public List<BorrowHistoryInformation> searchBorrowHistoryByBookIsbn(String bookIsbn) throws HibernateException;
		
	//��ѯĳ�˺��Ƿ������ĳ��
	public boolean checkBorrowHistoryExisted(String accountName,String bookIsbn) throws HibernateException;
		
	//��ѯĳ�˺ŵĽ�����ʷ��¼
	public List<BorrowHistoryInformation> searchBorrowHistoryByAccountName(String accountName) throws HibernateException;

	//ͨ�����ݿ�id��ѯ������ʷ��¼
	public List<BorrowHistoryInformation> searchBorrowHistoryById(String historyId) throws HibernateException;
		
	//��ѯĳ��Ľ�����ʷ��¼����
	public int findBookBorrowHistoryNumber(String bookIsbn) throws HibernateException;
	
}
