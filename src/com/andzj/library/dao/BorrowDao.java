package com.andzj.library.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.andzj.library.bean.BorrowInformation;

public interface BorrowDao {
	/********************************操作图书借阅********************************************/
	//增加一条借阅记录
	public BorrowInformation addBorrow(BorrowInformation borrow) throws HibernateException;
	
	//删除一条借阅记录
	public BorrowInformation deleteBorrow(String borrowId) throws HibernateException;
	
	//修改一条借阅记录
	public boolean updateBorrow(BorrowInformation borrow) throws HibernateException;
	
	//查询所有记录
	public List<BorrowInformation> findAllBorrow() throws HibernateException;
	
	//查询某书的借阅记录
	public List<BorrowInformation> searchBorrowByBookIsbn(String bookIsbn) throws HibernateException;
	
	//查询某账号是否借阅了某书
	public boolean checkBorrowExisted(String borrowAccountName,String bookIsbn) throws HibernateException;
	
	//查询某账号的借阅记录
	public List<BorrowInformation> searchBorrowByAccountName(String borrowAccountName) throws HibernateException;

	//通过数据库id查询借阅记录
	public List<BorrowInformation> searchBorrowById(String borrowId) throws HibernateException;
	
	//查询某书的借阅记录条数
	public int findBookBorrowNumber(String bookIsbn) throws HibernateException;
}
