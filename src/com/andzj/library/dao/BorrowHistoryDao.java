package com.andzj.library.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.andzj.library.bean.BorrowHistoryInformation;

public interface BorrowHistoryDao {
	/******************************操作借阅历史记录*********************************/
	//增加一条借阅历史
	public boolean addBorrowHistory(BorrowHistoryInformation borrowHistoryInformation) throws HibernateException;
	
	//为某条借阅历史的增加备注
	public boolean addBorrowHistoryNotes(String historyId,String historyNotes) throws HibernateException;
	
	//修改某条借阅历史的备注
	public boolean updateBorrowHistoryNotes(String historyId,String newHistoryNotes) throws HibernateException;
	
	//查询所有借阅历史记录
	public List<BorrowHistoryInformation> findAllBorrowHistory() throws HibernateException;
		
	//查询某书的借阅历史记录
	public List<BorrowHistoryInformation> searchBorrowHistoryByBookIsbn(String bookIsbn) throws HibernateException;
		
	//查询某账号是否借阅了某书
	public boolean checkBorrowHistoryExisted(String accountName,String bookIsbn) throws HibernateException;
		
	//查询某账号的借阅历史记录
	public List<BorrowHistoryInformation> searchBorrowHistoryByAccountName(String accountName) throws HibernateException;

	//通过数据库id查询借阅历史记录
	public List<BorrowHistoryInformation> searchBorrowHistoryById(String historyId) throws HibernateException;
		
	//查询某书的借阅历史记录条数
	public int findBookBorrowHistoryNumber(String bookIsbn) throws HibernateException;
	
}
