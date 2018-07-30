package com.andzj.library.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.andzj.library.bean.BookInformation;

public interface BookDao {
	/*******************************操作图书**************************/
	//添加图书信息
	public boolean addBook(BookInformation book)  throws HibernateException;
	
	//添加一条评分
	public boolean addCommentScore(String bookIsbn,Double newCommentScore) throws HibernateException;
	
	//删除图书 
	public BookInformation deleteBook(String bookIsbn) throws HibernateException;
	
	//更新图书信息
	public boolean updateBook(BookInformation book) throws HibernateException;
	
	//更新评分
	public boolean updateCommentScore(String bookIsbn,Double oldCommentScore,Double newCommentScore) throws HibernateException;
	
	//查询所有图书
	public List<BookInformation> findAllBook() throws HibernateException;
	
	//查询ISBN是否存在
	public boolean checkBookIsbnExisted(String bookIsbn) throws HibernateException;
	
	//以ISBN查询图书
	public List<BookInformation> searchBookByIsbn(String bookIsbn) throws HibernateException;
	
	//以书名查询图书
	public List<BookInformation> searchBookByName(String bookName) throws HibernateException;
	
	//以作者查询图书
	public List<BookInformation> searchBookByAuthor(String bookAuthor) throws HibernateException;
	
	//以出版社查询图书
	public List<BookInformation> searchBookByPublishCompany(String bookPublishCompany) throws HibernateException;
	
	//以关键字查询图书
	public List<BookInformation> searchBookByKeyWords(String bookKeyWords) throws HibernateException;
	
	//在以上类别中查询
	public List<BookInformation> searchBook(String searchWords) throws HibernateException;

	//查询某书能借出的数量(总数-不可借数量)
	public int findBookBorrowNumber(String bookIsbn) throws HibernateException;
	
	//查询评分前面的书籍
	public List<BookInformation> searchHotBook() throws HibernateException;
}
