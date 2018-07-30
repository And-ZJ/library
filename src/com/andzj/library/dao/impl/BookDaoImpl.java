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
	/*******************************操作图书**************************/
	//添加图书信息
	@Override
	public boolean addBook(BookInformation book) throws HibernateException {
		getHibernateTemplate().save(book);
		return true;
	}
	
	//添加一条评分
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
	
	//删除图书 
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
	
	//更新图书信息
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
	
	//更新评分
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
	
	
	//查询所有图书
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
	
	//查询ISBN是否存在
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

	
	//以ISBN查询图书
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
	
	//以书名查询图书
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
	
	//以作者查询图书
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
	
	//以出版社查询图书
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
	
	//以关键字查询图书
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
	
	//在以上类别中查询
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
	
	
	//查询某书能借出的数量(总数-不可借数量)
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
	
	//查询评分前面的书籍
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
