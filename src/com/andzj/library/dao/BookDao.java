package com.andzj.library.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.andzj.library.bean.BookInformation;

public interface BookDao {
	/*******************************����ͼ��**************************/
	//���ͼ����Ϣ
	public boolean addBook(BookInformation book)  throws HibernateException;
	
	//���һ������
	public boolean addCommentScore(String bookIsbn,Double newCommentScore) throws HibernateException;
	
	//ɾ��ͼ�� 
	public BookInformation deleteBook(String bookIsbn) throws HibernateException;
	
	//����ͼ����Ϣ
	public boolean updateBook(BookInformation book) throws HibernateException;
	
	//��������
	public boolean updateCommentScore(String bookIsbn,Double oldCommentScore,Double newCommentScore) throws HibernateException;
	
	//��ѯ����ͼ��
	public List<BookInformation> findAllBook() throws HibernateException;
	
	//��ѯISBN�Ƿ����
	public boolean checkBookIsbnExisted(String bookIsbn) throws HibernateException;
	
	//��ISBN��ѯͼ��
	public List<BookInformation> searchBookByIsbn(String bookIsbn) throws HibernateException;
	
	//��������ѯͼ��
	public List<BookInformation> searchBookByName(String bookName) throws HibernateException;
	
	//�����߲�ѯͼ��
	public List<BookInformation> searchBookByAuthor(String bookAuthor) throws HibernateException;
	
	//�Գ������ѯͼ��
	public List<BookInformation> searchBookByPublishCompany(String bookPublishCompany) throws HibernateException;
	
	//�Թؼ��ֲ�ѯͼ��
	public List<BookInformation> searchBookByKeyWords(String bookKeyWords) throws HibernateException;
	
	//����������в�ѯ
	public List<BookInformation> searchBook(String searchWords) throws HibernateException;

	//��ѯĳ���ܽ��������(����-���ɽ�����)
	public int findBookBorrowNumber(String bookIsbn) throws HibernateException;
	
	//��ѯ����ǰ����鼮
	public List<BookInformation> searchHotBook() throws HibernateException;
}
