package com.andzj.library.dao.impl;

import java.util.List;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.sql.Update;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.andzj.library.bean.BookInformation;
import com.andzj.library.dao.BookDao;
import com.opensymphony.xwork2.util.Key;


public class BookDaoImpl  extends HibernateDaoSupport implements BookDao{
	
	public String buildSearchWords(String oldWords)
	{
		if (oldWords == null) return null;
		StringBuilder s = new StringBuilder(oldWords);
        int i = s.indexOf(" ");
        while (i >= 0)
        {
            s.replace(i,i+1,"%");
            i = s.indexOf(" ");
        }
        s.insert(0,"%");
        s.append("%");
        return s.toString();
	}
	/*******************************����ͼ��**************************/
	//���ͼ����Ϣ
	@Override
	public boolean addBook(BookInformation book) throws HibernateException {
		getHibernateTemplate().save(book);
		return true;
	}
	
	//���һ������
	@Override
	public boolean addCommentScore(String bookIsbn,Double newCommentScore) throws HibernateException{
		String hql = "from BookInformation where book_isbn = ?";
		List<BookInformation> books = this.getHibernateTemplate().find(hql,bookIsbn);
		if (books != null && books.size() > 0)
		{
			BookInformation book = books.get(0);
			Double oldAverageScore = book.getBookAverageScore();
			Integer oldScoreNumber = book.getBookScoreNumber();
			
			double oldSumScore = oldAverageScore.doubleValue() * oldScoreNumber.intValue();
			
			Integer newScoreNumber = new Integer(oldScoreNumber.intValue() + 1);
			Double newAverageScore = new Double((oldSumScore + newCommentScore.doubleValue())/(newScoreNumber.intValue()));
			
			book.setBookAverageScore(newAverageScore);
			book.setBookScoreNumber(newScoreNumber);
			getHibernateTemplate().update("BookInformation",book);
			return true;
		}
		return false;
	}
	
	//ɾ��ͼ�� 
	@Override
	public BookInformation deleteBook(String bookIsbn) throws HibernateException
	{
		String hql = "from BookInformation where book_isbn = ?";
		List<BookInformation> books = this.getHibernateTemplate().find(hql,bookIsbn);
		if (books != null && books.size() > 0)
		{
			getHibernateTemplate().delete(books.get(0));
			return books.get(0);
		}
		return null;
	}
	
	//����ͼ����Ϣ
	@Override
	public boolean updateBook(BookInformation book) throws HibernateException
	{
		String hql = "from BookInformation where book_isbn = ?";
		List<BookInformation> books = this.getHibernateTemplate().find(hql,book.getBookIsbn());
		if (books != null && books.size() > 0)
		{
			BookInformation oldBook = books.get(0);
			book.setBookId(oldBook.getBookId());
			if (book.getBookImageAddress() == null)
			{
				book.setBookImageAddress(oldBook.getBookImageAddress());
			}
			book.setBookAverageScore(oldBook.getBookAverageScore());
			book.setBookScoreNumber(oldBook.getBookScoreNumber());
			getHibernateTemplate().update("BookInformation",book);
			return true;
		}
		return false;
	}
	
	//��������
	@Override
	public boolean updateCommentScore(String bookIsbn,Double oldCommentScore,Double newCommentScore) throws HibernateException
	{
		String hql = "from BookInformation where book_isbn = ?";
		List<BookInformation> books = this.getHibernateTemplate().find(hql,bookIsbn);
		if (books != null && books.size() > 0)
		{
			BookInformation book = books.get(0);
			Double oldAverageScore = book.getBookAverageScore();
			Integer ScoreNumber = book.getBookScoreNumber();
			
			double oldSumScore = oldAverageScore.doubleValue() * ScoreNumber.intValue();

			Double newAverageScore = new Double( (oldSumScore - oldCommentScore.doubleValue() + newCommentScore.doubleValue()) / (ScoreNumber.intValue()));
			
			book.setBookAverageScore(newAverageScore);
			getHibernateTemplate().update("BookInformation",book);
			//System.out.println("score:"+ newAverageScore);
			return true;
		}
		return false;
	}
	
	
	//��ѯ����ͼ��
	@Override
	public List<BookInformation> findAllBook() throws HibernateException
	{
		List<BookInformation> books = getHibernateTemplate().find("from BookInformation ");
		if (books != null && books.size() > 0)
		{
			return books;
		}
		return null;
	}
	
	//��ѯISBN�Ƿ����
	@Override
	public boolean checkBookIsbnExisted(String bookIsbn) throws HibernateException
	{
		String hql = "from BookInformation where book_isbn = ?";
		List<BookInformation> books = getHibernateTemplate().find(hql,bookIsbn);
		if (books != null && books.size() > 0)
		{
			return true;
		}
		return false;
	}

	
	//��ISBN��ѯͼ��
	@Override
	public List<BookInformation> searchBookByIsbn(String bookIsbn) throws HibernateException
	{
		String hql = "from BookInformation where book_isbn = ?";
		List<BookInformation> books = getHibernateTemplate().find(hql,bookIsbn);
		if (books != null && books.size() >0 )
		{
			return books;
		}
		return null;
	}
	
	//��������ѯͼ��
	@Override
	public List<BookInformation> searchBookByName(String bookName) throws HibernateException
	{
		String hql = "from BookInformation where book_name LIKE ?";
		String searchWords = buildSearchWords(bookName);
		List<BookInformation> list = getHibernateTemplate().find(hql,searchWords);
		if (list != null && list.size() >0)
		{
			return list;
		}
		return null;
	}
	
	//�����߲�ѯͼ��
	@Override
	public List<BookInformation> searchBookByAuthor(String bookAuthor) throws HibernateException
	{
		String hql = "from BookInformation where book_author LIKE ?";
		String searchWords = buildSearchWords(bookAuthor);
		List<BookInformation> list = getHibernateTemplate().find(hql,searchWords);
		if (list != null && list.size() >0)
		{
			return list;
		}
		return null;
	}
	
	//�Գ������ѯͼ��
	@Override
	public List<BookInformation> searchBookByPublishCompany(String bookPublishCompany) throws HibernateException
	{
		String hql = "from BookInformation where book_publish_company LIKE ?";
		String searchWords = buildSearchWords(bookPublishCompany);
		List<BookInformation> list = getHibernateTemplate().find(hql,searchWords);
		if (list != null && list.size() >0)
		{
			return list;
		}
		return null;
	}
	
	//�Թؼ��ֲ�ѯͼ��
	@Override
	public List<BookInformation> searchBookByKeyWords(String bookKeyWords) throws HibernateException
	{
		String hql = "from BookInformation where book_key_words LIKE ?";
		String searchWords = buildSearchWords(bookKeyWords);
		List<BookInformation> list = getHibernateTemplate().find(hql,searchWords);
		if (list != null && list.size() >0)
		{
			return list;
		}
		return null;
	}
	
	//����������в�ѯ
	@Override
	public List<BookInformation> searchBook(String searchWords) throws HibernateException
	{
		String hql = "from BookInformation where book_isbn = ? OR book_name LIKE ? OR book_author LIKE ? OR book_publish_company LIKE ? OR book_key_words LIKE ?";
		String newSearchWords = buildSearchWords(searchWords);
		List<BookInformation> list = getHibernateTemplate().find(hql,searchWords,newSearchWords,newSearchWords,newSearchWords,newSearchWords);
		if (list != null && list.size() >0)
		{
			return list;
		}
		return null;
	}
	
	
	//��ѯĳ���ܽ��������(����-���ɽ�����)
	public int findBookBorrowNumber(String bookIsbn) throws HibernateException
	{
		String hql = "from BookInformation where book_isbn = ?";
		List<BookInformation> books = getHibernateTemplate().find(hql,bookIsbn);
		if (books != null && books.size() >0 )
		{
			BookInformation book = books.get(0);
			int totalNumber = book.getBookTotalNumber();
			int remainNumber = book.getBookRemainNumber();
			return totalNumber - remainNumber;
		}
		return -1;
	}
	
	//��ѯ����ǰ����鼮
	public List<BookInformation> searchHotBook() throws HibernateException
	{
		String hql = "from BookInformation order by book_average_score desc";// limit 0,5";
		Session session = getSession();
		Query query = session.createQuery(hql);
		//List<BookInformation> bookInformations = getHibernateTemplate().find(hql);
		query.setFirstResult(0);
		query.setMaxResults(5);
		List<BookInformation> bookInformations = query.list();
		if (bookInformations != null && bookInformations.size() >0)
		{
			return bookInformations;
		}
		return null;
	}

}
