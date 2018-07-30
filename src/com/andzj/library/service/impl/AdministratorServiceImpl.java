package com.andzj.library.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.HibernateException;

import com.andzj.library.bean.AdministratorAccount;
import com.andzj.library.bean.BookInformation;
import com.andzj.library.bean.BorrowHistoryInformation;
import com.andzj.library.bean.BorrowInformation;
import com.andzj.library.bean.CommentInformation;
import com.andzj.library.bean.FeedbackInformation;
import com.andzj.library.bean.ScoreInformation;
import com.andzj.library.bean.UserAccount;
import com.andzj.library.dao.AdministratorDao;
import com.andzj.library.dao.BookDao;
import com.andzj.library.dao.BorrowDao;
import com.andzj.library.dao.BorrowHistoryDao;
import com.andzj.library.dao.CommentDao;
import com.andzj.library.dao.FeedbackDao;
import com.andzj.library.dao.ScoreDao;
import com.andzj.library.dao.UserDao;
import com.andzj.library.service.AdministratorService;
import com.andzj.library.util.JsonResponse;
import com.andzj.library.util.MyConfigure;
import com.andzj.library.util.MyTimeUtils;

/**
 * 管理员访问的业务层实现类
 * @author zj
 *
 */
public class AdministratorServiceImpl implements AdministratorService {
	private AdministratorDao administratorDao;
	public void setAdministratorDao(AdministratorDao administratorDao) {
		this.administratorDao = administratorDao;
	}
	
	private BookDao bookDao;
	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}	
	
	private BorrowDao borrowDao;
	public void setBorrowDao(BorrowDao borrowDao) {
		this.borrowDao = borrowDao;
	}	
	
	private BorrowHistoryDao borrowHistoryDao;
	public void setBorrowHistoryDao(BorrowHistoryDao borrowHistoryDao) {
		this.borrowHistoryDao = borrowHistoryDao;
	}

	private CommentDao commentDao;
	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}
	
	private ScoreDao scoreDao;
	public void setScoreDao(ScoreDao scoreDao) {
		this.scoreDao = scoreDao;
	}

	private UserDao userDao;
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}	
	
	private FeedbackDao feedbackDao;
	public void setFeedbackDao(FeedbackDao feedbackDao){
		this.feedbackDao = feedbackDao;
	}
	
	private List<AdministratorAccount> emptyAdministratorAccountList = new ArrayList<>();
	private List<BookInformation> emptyBookInformationList = new ArrayList<>();
	private List<BorrowInformation> emptyBorrowInformationList = new ArrayList<>();
	private List<BorrowHistoryInformation> emptyBorrowHistoryInformationList = new ArrayList<>();
	private List<CommentInformation> emptyCommentInformationList = new ArrayList<>();
	private List<ScoreInformation> emptyScoreInformationList = new ArrayList<>();
	private List<UserAccount> emptyUserAccountList = new ArrayList<>();
	private List<FeedbackInformation> emptyFeedbackInformationList = new ArrayList<>();
	
	/***************************文件存储*******************************/
	//存储文件
	public boolean saveFile(String fileRelativePathStr,String fileNameStr,File file) throws IOException
	{
		if (file == null)
		{
			return false;
		}
		//String fileAbsoultPathStr = ServletActionContext.getServletContext().getRealPath("/") + fileRelativePathStr;
		String fileAbsoultPathStr = MyConfigure.File_PhysicalAddress + fileRelativePathStr;
		File filePath = new File(fileAbsoultPathStr);
		if (!filePath.exists())
		{
			filePath.mkdirs();
		}
		File outputFile = new File(fileAbsoultPathStr + "/" +fileNameStr);
		if (outputFile.exists())
		{
			outputFile.delete();
		}
		outputFile.createNewFile();
		FileInputStream inputStream = new FileInputStream(file);
		FileOutputStream outputStream = new FileOutputStream(outputFile);
		byte[] buf = new byte[1024];
		int length = 0;
		while ((length = inputStream.read(buf)) != -1)
		{
			outputStream.write(buf, 0 , length);
		}
		inputStream.close();
		outputStream.flush();
		outputStream.close();
		return true;
	}
	
	//删除文件
	public boolean deleteFile(String fileRelativePathStr,String fileNameStr) throws IOException
	{
		String fileAbsoultPathStr = MyConfigure.File_PhysicalAddress + fileRelativePathStr;
		File outputFile = new File(fileAbsoultPathStr + "/" +fileNameStr);
		if (outputFile.exists())
		{
			if (outputFile.delete())
			{
				return true;
			}
			return false;
		}
		return true;
	}

	
	
	/***************************操作管理员**************************************/
	//添加管理员账户
	@Override
	public boolean addAdministratorAccount(AdministratorAccount account) throws HibernateException {
		if (administratorDao.checkAdminiAccountExisted(account.getAccountName()))
		{
			try 
			{
				JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
				jsonResponse.put("info", "AccountExisted");
				jsonResponse.commitAndClose();
				return true;
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			return false;
		}
		if ( administratorDao.addAdministratorAccount(account) )
		{
			try 
			{
				JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
				jsonResponse.put("info", "AddSuccess");
				jsonResponse.commitAndClose();
				return true;
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			return false;
		}
		try 
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			jsonResponse.put("info", "AddError");
			jsonResponse.commitAndClose();
			return true;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return false;
		
	}
	
	//管理员登录
	@Override
	public boolean administratorLogin(AdministratorAccount account) throws HibernateException {
		AdministratorAccount resultAccount =  administratorDao.administratorLogin(account);
		if (resultAccount != null)
		{
			if (resultAccount.getPasswordMD5().equals(account.getPasswordMD5()))
			{
				try
				{
					resultAccount.setPasswordMD5("");
		    		JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
		    		jsonResponse.put("data", resultAccount);
		    		jsonResponse.put("info", "LoginSuccess");
		    		jsonResponse.commitAndClose();
		    		return true;
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				return false;
			}
			try
			{
				JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
				jsonResponse.put("info", "PasswordWrong");
				jsonResponse.commitAndClose();
				return false;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return false;
		}
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			jsonResponse.put("info", "AccountNotExisted");
			jsonResponse.commitAndClose();
			return false;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//查询指定管理员帐号是否存在
	@Override
	public boolean checkAdminAccountExisted(String accountName) throws HibernateException
	{
		if (administratorDao.checkAdminiAccountExisted(accountName))
		{
			try 
			{
				JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
				jsonResponse.put("info", "AccountExisted");
				jsonResponse.commitAndClose();
				return true;
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			return false;
		}
		try 
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			jsonResponse.put("info", "AccountNotExisted");
			jsonResponse.commitAndClose();
			return true;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return false;
	}
		
	//删除管理员帐号
	@Override
	public boolean deleteAdministratorAccount(String operateAccountName,String accountName) throws HibernateException
	{
		try 
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			if ("admin".equals(accountName) || operateAccountName.equals(accountName))
			{
				jsonResponse.put("info", "ForbidDelete");
				jsonResponse.commitAndClose();
				return true;
			}
			AdministratorAccount account = administratorDao.deleteAdministratorAccount(accountName);
			if (account != null)
			{
				jsonResponse.put("info", "DeleteSuccess");
				jsonResponse.put("delete_data", account);
				jsonResponse.commitAndClose();
				return true;
			}	
			jsonResponse.put("info", "DeleteError");
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return false;
	}
			
	//更改管理员帐号的昵称和密码
	@Override
	public boolean updateAdministratorAccount(String accountName,String newNickname,String newPasswordMD5) throws HibernateException
	{
		if (administratorDao.updateAdministratorAccount(accountName, newNickname, newPasswordMD5))
		{
			try
			{
				JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
				jsonResponse.put("info", "UpdateSuccess");
				jsonResponse.commitAndClose();
				return true;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return false;
		}
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			jsonResponse.put("info", "UpdateError");
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}


	//查询所有管理员账户
	@Override
	public boolean findAllAdminAccount() throws HibernateException {
		
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			
			List<AdministratorAccount> accounts =  administratorDao.findAllAdminAccount();
			if (accounts != null)
			{
				jsonResponse.put("info", "Match");
				jsonResponse.put("size", accounts.size());
				jsonResponse.put("data", accounts);
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "NotMatch");
			jsonResponse.put("data", emptyAdministratorAccountList);
			jsonResponse.put("size", 0);
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//查询管理员帐号
	public boolean searchAdminAccount(String accountName) throws HibernateException
	{
		List<AdministratorAccount> accounts =  administratorDao.searchAdminAccount(accountName);
		try 
		{
			if (accounts != null)
			{
				JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
				jsonResponse.put("info", "Match");
				jsonResponse.put("data", accounts);
				jsonResponse.put("size", accounts.size());
				jsonResponse.commitAndClose();
				return true;
			}
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			jsonResponse.put("info", "NotMatch");
			jsonResponse.put("data",emptyAdministratorAccountList);
			jsonResponse.put("size", 0);
			jsonResponse.commitAndClose();
			return true;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return false;
	}

	


	/*******************************操作图书**************************/
	//添加图书信息
	@Override
	public boolean addBook(BookInformation book) throws HibernateException {
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			if (bookDao.checkBookIsbnExisted(book.getBookIsbn()))
			{
				jsonResponse.put("info", "BookIsbnExisted");
				jsonResponse.commitAndClose();
				return true;
			}
			if (bookDao.addBook(book))
			{
				jsonResponse.put("info", "AddSuccess");
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "AddError");
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean addBook(BookInformation book,String bookImageRelativePath,String bookImageName,File file) throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			if (bookDao.checkBookIsbnExisted(book.getBookIsbn()))
			{
				jsonResponse.put("info", "BookIsbnExisted");
				jsonResponse.commitAndClose();
				return true;
			}
			if (bookDao.addBook(book))
			{
				try
				{
					if (this.saveFile(bookImageRelativePath, bookImageName, file))
					{
						jsonResponse.put("image", "SaveSuccess");
					}
					else
					{
						jsonResponse.put("image","SaveError");
					}
				}
				catch (IOException e)
				{
					jsonResponse.put("image","SaveError");
					e.printStackTrace();
				}
				jsonResponse.put("info", "AddSuccess");
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "AddError");
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	//删除图书 
	@Override
	public boolean deleteBook(String bookIsbn) throws HibernateException
	{
		try 
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			if (!bookDao.checkBookIsbnExisted(bookIsbn))
			{
				jsonResponse.put("info", "BookNotExisted");
				jsonResponse.commitAndClose();
				return true;
			}
			BookInformation bookInformation =  bookDao.deleteBook(bookIsbn);	
			if (bookInformation != null)
			{
				jsonResponse.put("info", "DeleteSuccess");
				jsonResponse.put("delete_data", bookInformation);
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "DeleteError");
			jsonResponse.commitAndClose();
			return true;
		} 
		catch (Exception e) 
		{
			
			e.printStackTrace();
		}
		return false;
	}
	
	//更新图书信息
	@Override
	public boolean updateBook(BookInformation book) throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			if (!bookDao.checkBookIsbnExisted(book.getBookIsbn()))
			{
				jsonResponse.put("info", "BookNotExisted");
				jsonResponse.commitAndClose();
				return true;
			}
			if (bookDao.updateBook(book))
			{
				jsonResponse.put("info", "UpdateSuccess");
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "UpdateError");
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public boolean updateBook(BookInformation book,String bookImageRelativePath,String bookImageName,File file) throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			if (!bookDao.checkBookIsbnExisted(book.getBookIsbn()))
			{
				jsonResponse.put("info", "BookNotExisted");
				jsonResponse.commitAndClose();
				return true;
			}
			if (bookDao.updateBook(book))
			{
				try
				{
					if (this.saveFile(bookImageRelativePath, bookImageName, file))
					{
						jsonResponse.put("image_address", bookImageRelativePath + "/" + bookImageName);
						jsonResponse.put("image", "SaveSuccess");
					}
					else
					{
						jsonResponse.put("image","SaveError");
					}
				}
				catch (IOException e)
				{
					jsonResponse.put("image","SaveError");
					e.printStackTrace();
				}
				jsonResponse.put("info", "UpdateSuccess");
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "UpdateError");
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//查询所有图书
	@Override
	public boolean findAllBook() throws HibernateException
	{
		List<BookInformation> books = bookDao.findAllBook();
		if (books != null)
		{
			try
			{
				JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
				jsonResponse.put("info","Match");
				jsonResponse.put("size", books.size());
				jsonResponse.put("data", books);
				jsonResponse.commitAndClose();
				return true;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return false;
		}
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			jsonResponse.put("info", "NotMatch");
			jsonResponse.put("data", new ArrayList<BookInformation>());
			jsonResponse.put("size", 0);
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//查询isbn是否重复
	public boolean checkBookIsbnExisted(String bookIsbn) throws HibernateException
	{
		if (bookDao.checkBookIsbnExisted(bookIsbn))
		{
			try
			{
				JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
				jsonResponse.put("info", "BookIsbnExisted");
				jsonResponse.commitAndClose();
				return true;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return false;
		}
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			jsonResponse.put("info", "BookIsbnNotExisted");
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//以ISBN查询图书
	@Override
	public boolean searchBookByIsbn(String bookIsbn) throws HibernateException
	{
		List<BookInformation> books = bookDao.searchBookByIsbn(bookIsbn);
		if (books != null)
		{
			try
			{
				JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
				jsonResponse.put("info", "Match");
				jsonResponse.put("data",books);
				jsonResponse.put("size",books.size());
				jsonResponse.commitAndClose();
				return true;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return false;
		}
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			jsonResponse.put("info", "NotMatch");
			jsonResponse.put("data",emptyBookInformationList);
			jsonResponse.put("size", 0);
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//以书名查询图书
	@Override
	public boolean searchBookByName(String bookName) throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			List<BookInformation> list = bookDao.searchBookByName(bookName);
			if (list != null )
			{
				jsonResponse.put("info", "Match");
				jsonResponse.put("data",list);
				jsonResponse.put("size", list.size());
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "NotMatch");
			jsonResponse.put("data",emptyBookInformationList);
			jsonResponse.put("size", 0);
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//以作者查询图书
	@Override
	public boolean searchBookByAuthor(String bookAuthor) throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			List<BookInformation> list = bookDao.searchBookByAuthor(bookAuthor);
			if (list != null )
			{
				jsonResponse.put("info", "Match");
				jsonResponse.put("data",list);
				jsonResponse.put("size", list.size());
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "NotMatch");
			jsonResponse.put("data",emptyBookInformationList);
			jsonResponse.put("size", 0);
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//以出版社查询图书
	@Override
	public boolean searchBookByPublishCompany(String bookPublishCompany) throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			List<BookInformation> list = bookDao.searchBookByPublishCompany(bookPublishCompany);
			if (list != null )
			{
				jsonResponse.put("info", "Match");
				jsonResponse.put("data",list);
				jsonResponse.put("size", list.size());
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "NotMatch");
			jsonResponse.put("data",emptyBookInformationList);
			jsonResponse.put("size", 0);
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//以关键字查询图书
	@Override
	public boolean searchBookByKeyWords(String bookKeyWords) throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			List<BookInformation> list = bookDao.searchBookByKeyWords(bookKeyWords);
			if (list != null )
			{
				jsonResponse.put("info", "Match");
				jsonResponse.put("data",list);
				jsonResponse.put("size", list.size());
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "NotMatch");
			jsonResponse.put("data",emptyBookInformationList);
			jsonResponse.put("size", 0);
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//在以上类别中查询
	@Override
	public boolean searchBook(String searchWords) throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			List<BookInformation> list = bookDao.searchBook(searchWords);
			if (list != null )
			{
				jsonResponse.put("info", "Match");
				jsonResponse.put("data",list);
				jsonResponse.put("size", list.size());
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "NotMatch");
			jsonResponse.put("data",emptyBookInformationList);
			jsonResponse.put("size", 0);
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//查询评分前面的书籍
	public boolean searchHotBook() throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			List<BookInformation> list = bookDao.searchHotBook();
			if (list != null )
			{
				jsonResponse.put("info", "Match");
				jsonResponse.put("data",list);
				jsonResponse.put("size", list.size());
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "NotMatch");
			jsonResponse.put("data",emptyBookInformationList);
			jsonResponse.put("size", 0);
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	/********************************操作图书借阅********************************************/
	//增加一条借阅记录
	@Override
	public boolean addBorrow(BorrowInformation borrow) throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			if (!bookDao.checkBookIsbnExisted(borrow.getBookIsbn()))
			{
				jsonResponse.put("info", "BookIsbnNotExisted");//这本书不存在
				jsonResponse.commitAndClose();
				return true;
			}	
			if (!userDao.checkUserAccountExisted(borrow.getBorrowAccountName()))
			{
				jsonResponse.put("info", "AccountNotExisted");//账户不存在
				jsonResponse.commitAndClose();
				return true;
			}
			if (borrowDao.checkBorrowExisted(borrow.getBorrowAccountName(), borrow.getBookIsbn()))
			{
				jsonResponse.put("info", "BorrowExisted");//存在此条借阅记录,不可重复借阅
				jsonResponse.commitAndClose();
				return true;
			}
			int remainBorrowNumber = bookDao.findBookBorrowNumber(borrow.getBookIsbn());
			int borrowedNumber = borrowDao.findBookBorrowNumber(borrow.getBookIsbn());
			if ( (remainBorrowNumber - borrowedNumber) <= 0)
			{
				jsonResponse.put("info", "BorrowFull");//能借的都借了,不能再借了
				jsonResponse.commitAndClose();
				return true;
			}
			BorrowInformation borrowInformation = borrowDao.addBorrow(borrow);
			
			if (borrowInformation != null)
			{
				jsonResponse.put("info", "AddSuccess");
				jsonResponse.put("add_data", borrowInformation);
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "AddError");
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//删除一条借阅记录
	@Override
	public boolean deleteBorrow(String borrowId) throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			List<BorrowInformation> list = borrowDao.searchBorrowById(borrowId);
			if (list != null)
			{
				BorrowInformation borrow = list.get(0);
				String returnTime = MyTimeUtils.getMyTimeStr(new Date());
				BorrowHistoryInformation history = new BorrowHistoryInformation();
				history.setAccountName(borrow.getBorrowAccountName());
				history.setBookIsbn(borrow.getBookIsbn());
				history.setBorrowTime(borrow.getBorrowTime());
				history.setReturnTime(returnTime);
				history.setHistoryNotes("");
				if (borrowHistoryDao.addBorrowHistory(history))
				{
					BorrowInformation borrowInformation =  borrowDao.deleteBorrow(borrowId);
					if (borrowInformation != null)
					{
						jsonResponse.put("info", "DeleteSuccess");
						jsonResponse.put("delete_data", borrowInformation);
						jsonResponse.commitAndClose();
						return true;
					}
				}
				jsonResponse.put("info", "DeleteError");
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "BorrowNotExisted");
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//修改一条借阅记录 只能修改 归还时间和借阅状态
	@Override
	public boolean updateBorrow(BorrowInformation borrow) throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			if (borrowDao.updateBorrow(borrow))
			{
				jsonResponse.put("info", "UpdateSuccess");
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "UpdateError");
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//查询所有记录
	public boolean findAllBorrow() throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			List<BorrowInformation> list = borrowDao.findAllBorrow();
			if (list != null)
			{
				jsonResponse.put("info", "Match");
				jsonResponse.put("size", list.size());
				jsonResponse.put("data", list);
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "NotMatch");
			jsonResponse.put("data", emptyBorrowInformationList);
			jsonResponse.put("size", 0);
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//查询某书的借阅记录
	@Override
	public boolean searchBorrowByBookIsbn(String bookIsbn) throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			List<BorrowInformation> list = borrowDao.searchBorrowByBookIsbn(bookIsbn);			
			if (list != null)
			{
				jsonResponse.put("info", "Match");
				jsonResponse.put("size", list.size());
				jsonResponse.put("data", list);
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "NotMatch");
			jsonResponse.put("data", emptyBorrowInformationList);
			jsonResponse.put("size", 0);
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//查询某账号是否借阅了某书
	@Override
	public boolean checkBorrowExisted(String borrowAccountName,String bookIsbn) throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			if (borrowDao.checkBorrowExisted(borrowAccountName, bookIsbn))
			{
				jsonResponse.put("info", "BorrowExisted");
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "BorrowNotExisted");
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	
	//查询某账号的借阅记录
	@Override
	public boolean searchBorrowByAccountName(String accountName) throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			List<BorrowInformation> list = borrowDao.searchBorrowByAccountName(accountName);			
			if (list != null)
			{
				jsonResponse.put("info", "Match");
				jsonResponse.put("size", list.size());
				jsonResponse.put("data", list);
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "NotMatch");
			jsonResponse.put("size", 0);
			jsonResponse.put("data", emptyBorrowInformationList);
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//通过数据库id查询借阅记录
	@Override
	public boolean searchBorrowById(String borrowId) throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			List<BorrowInformation> list = borrowDao.searchBorrowById(borrowId);			
			if (list != null)
			{
				jsonResponse.put("info", "Match");
				jsonResponse.put("size", list.size());
				jsonResponse.put("data", list);
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "NotMatch");
			jsonResponse.put("size", 0);
			jsonResponse.put("data", emptyBorrowInformationList);
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	
	/******************************操作借阅历史记录*********************************/
	//增加一条借阅历史
	public boolean addBorrowHistory(BorrowHistoryInformation borrowHistoryInformation) throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			if (borrowHistoryDao.addBorrowHistory(borrowHistoryInformation))
			{
				jsonResponse.put("info", "AddSuccess");
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "AddError");
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//为某条借阅历史的增加备注
	public boolean addBorrowHistoryNotes(String historyId,String historyNotes) throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());			
			if (borrowHistoryDao.addBorrowHistoryNotes(historyId, historyNotes))
			{
				jsonResponse.put("info", "AddSuccess");
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "AddError");
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//修改某条借阅历史的备注
	public boolean updateBorrowHistoryNotes(String historyId,String newHistoryNotes) throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());		
			if (borrowHistoryDao.updateBorrowHistoryNotes(historyId, newHistoryNotes))
			{
				jsonResponse.put("info", "UpdateSuccess");
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "UpdateError");
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//查询所有借阅历史记录
	public boolean findAllBorrowHistory() throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			List<BorrowHistoryInformation> list = borrowHistoryDao.findAllBorrowHistory();	
			if (list != null)
			{
				jsonResponse.put("info", "Match");
				jsonResponse.put("data", list);
				jsonResponse.put("size", list.size());
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "NotMatch");
			jsonResponse.put("data", emptyBorrowHistoryInformationList);
			jsonResponse.put("size", 0);
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
		
	//查询某书的借阅历史记录
	public boolean searchBorrowHistoryByBookIsbn(String bookIsbn) throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			List<BorrowHistoryInformation> list = borrowHistoryDao.searchBorrowHistoryByBookIsbn(bookIsbn);	
			if (list != null)
			{
				jsonResponse.put("info", "Match");
				jsonResponse.put("data", list);
				jsonResponse.put("size", list.size());
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "NotMatch");
			jsonResponse.put("data", emptyBorrowHistoryInformationList);
			jsonResponse.put("size", 0);
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
		
	//查询某账号是否借阅了某书
	public boolean checkBorrowHistoryExisted(String accountName,String bookIsbn) throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			if (borrowHistoryDao.checkBorrowHistoryExisted(accountName, bookIsbn))
			{
				jsonResponse.put("info", "HistoryExisted");
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "HistoryNotExisted");
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
		
	//查询某账号的借阅历史记录
	public boolean searchBorrowHistoryByAccountName(String accountName) throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			List<BorrowHistoryInformation> list = borrowHistoryDao.searchBorrowHistoryByAccountName(accountName);
			if (list != null)
			{
				jsonResponse.put("info", "Match");
				jsonResponse.put("data", list);
				jsonResponse.put("size", list.size());
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "NotMatch");
			jsonResponse.put("data", emptyBorrowHistoryInformationList);
			jsonResponse.put("size", 0);
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	//通过数据库id查询借阅历史记录
	public boolean searchBorrowHistoryById(String historyId) throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			List<BorrowHistoryInformation> list = borrowHistoryDao.searchBorrowHistoryById(historyId);
			if (list != null)
			{
				jsonResponse.put("info", "Match");
				jsonResponse.put("data", list);
				jsonResponse.put("size", list.size());
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "NotMatch");
			jsonResponse.put("data", emptyBorrowHistoryInformationList);
			jsonResponse.put("size", 0);
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
		
	//查询某书的借阅历史记录条数
	public boolean findBookBorrowHistoryNumber(String bookIsbn) throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			int number = borrowHistoryDao.findBookBorrowHistoryNumber(bookIsbn);
			if (number >0)
			{
				jsonResponse.put("info", "Match");
				jsonResponse.put("number", number);
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "NotMatch");
			jsonResponse.put("number", 0);
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	
	/********************************操作图书评论*****************************************/
	//增加一条图书评论
	@Override
	public boolean addComment(CommentInformation comment) throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			if (!bookDao.checkBookIsbnExisted(comment.getBookIsbn()))
			{
				jsonResponse.put("info", "BookIsbnNotExisted");//这本书不存在
				jsonResponse.commitAndClose();
				return true;
			}	
			if (!userDao.checkUserAccountExisted(comment.getCommentAccountName()))
			{
				jsonResponse.put("info", "AccountNotExisted");//账户不存在
				jsonResponse.commitAndClose();
				return true;
			}
			
			if (commentDao.addComment(comment))
			{
				jsonResponse.put("info", "AddSuccess");
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "AddError");
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//删除一条图书评论
	@Override
	public boolean deleteComment(String commentId) throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			CommentInformation commentInformation = commentDao.deleteComment(commentId);
			if (commentInformation != null)
			{
				jsonResponse.put("info", "DeleteSuccess");
				jsonResponse.put("delete_data", commentInformation);
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "DeleteError");
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//更新一条评论
	public boolean updateComment(CommentInformation commentInformation) throws HibernateException
	{
		try
		{
			
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			if (commentDao.updateComment(commentInformation))// && bookDao.updateCommentScore(commentInformation.getBookIsbn(), oldCommentScore, newCommentScore))
			{
				jsonResponse.put("info", "UpdateSuccess");
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "UpdateError");
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	
	//查询所有评论
	public boolean findAllComment() throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			List<CommentInformation> list = commentDao.findAllComment();
			if (list != null)
			{
				jsonResponse.put("info", "Match");
				jsonResponse.put("size", list.size());
				jsonResponse.put("data", list);
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "NotMatch");
			jsonResponse.put("size", 0);
			jsonResponse.put("data", emptyCommentInformationList);
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	
	
	//查询某书的评论 
	@Override
	public boolean searchCommentByBookIsbn(String bookIsbn) throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			List<CommentInformation> list = commentDao.searchCommentByBookIsbn(bookIsbn);
			if (list != null)
			{
				jsonResponse.put("info", "Match");
				jsonResponse.put("size", list.size());
				jsonResponse.put("data", list);
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "NotMatch");
			jsonResponse.put("size", 0);
			jsonResponse.put("data", emptyCommentInformationList);
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//查询某帐号的评论
	@Override
	public boolean searchCommentByAccountName(String accountName) throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			List<CommentInformation> list = commentDao.searchCommentByAccountName(accountName);
			if (list != null)
			{
				jsonResponse.put("info", "Match");
				jsonResponse.put("size", list.size());
				jsonResponse.put("data", list);
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "NotMatch");
			jsonResponse.put("size", 0);
			jsonResponse.put("data", emptyCommentInformationList);
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//查询某人是否评论过某书
	public boolean checkCommentExisted(String commentAccountName,String bookIsbn) throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			if (commentDao.checkCommentExisted(commentAccountName, bookIsbn))
			{
				jsonResponse.put("info", "CommentExisted");
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "CommentNotExisted");
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	
	/******************************操作图书评分**************************************/
	//增加评分
	public boolean addScore(ScoreInformation scoreInformation) throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			List<ScoreInformation> list = scoreDao.searchScoreRecord(scoreInformation.getScoreAccountName(), scoreInformation.getBookIsbn());
			if (list != null)//同一个isbn,只允许评分一次,如果评过,则更新评分
			{
				Double oldScore = list.get(0).getCommentScore();
				if (scoreDao.updateScore(scoreInformation) && bookDao.updateCommentScore(scoreInformation.getBookIsbn(), oldScore, scoreInformation.getCommentScore()))
				{
					jsonResponse.put("info", "UpdateSuccess");
					jsonResponse.commitAndClose();
					return true;
				}
				jsonResponse.put("info", "UpdateError");
				jsonResponse.commitAndClose();
				return true;
			}
			if (!bookDao.checkBookIsbnExisted(scoreInformation.getBookIsbn()))
			{
				jsonResponse.put("info", "BookIsbnNotExisted");
				jsonResponse.commitAndClose();
				return true;
			}
			if (!userDao.checkUserAccountExisted(scoreInformation.getScoreAccountName()))
			{
				jsonResponse.put("info", "AccountNotExisted");
				jsonResponse.commitAndClose();
				return true;
			}
			if (scoreDao.addScore(scoreInformation) && bookDao.addCommentScore(scoreInformation.getBookIsbn(), scoreInformation.getCommentScore()))
			{
				jsonResponse.put("info", "AddSuccess");
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "AddError");
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
		
	//增加评论和评分
	public boolean addCommentWithScore(CommentInformation commentInformation,ScoreInformation scoreInformation) throws HibernateException
	{
		try 
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			if (!bookDao.checkBookIsbnExisted(scoreInformation.getBookIsbn()))
			{
				jsonResponse.put("info", "BookIsbnNotExisted");
				jsonResponse.commitAndClose();
				return true;
			}
			if (!userDao.checkUserAccountExisted(scoreInformation.getScoreAccountName()))
			{
				jsonResponse.put("info", "AccountNotExisted");
				jsonResponse.commitAndClose();
				return true;
			}
			
			if (commentDao.addComment(commentInformation))
			{
				jsonResponse.put("info1", "CommentAddSuccess");
				
				List<ScoreInformation> list = scoreDao.searchScoreRecord(scoreInformation.getScoreAccountName(), scoreInformation.getBookIsbn());
				if (list != null)//同一个isbn,只允许评分一次,如果评过,则更新评分
				{
					Double oldScore = list.get(0).getCommentScore();
					if (scoreDao.updateScore(scoreInformation) && bookDao.updateCommentScore(scoreInformation.getBookIsbn(), oldScore, scoreInformation.getCommentScore()))
					{
						
						jsonResponse.put("info2", "ScoreUpdateSuccess");
						jsonResponse.commitAndClose();
						return true;
					}
					jsonResponse.put("info2", "ScoreUpdateError");
					jsonResponse.commitAndClose();
					return true;
				}
				if (scoreDao.addScore(scoreInformation) && bookDao.addCommentScore(scoreInformation.getBookIsbn(), scoreInformation.getCommentScore()))
				{
					jsonResponse.put("info2", "ScoreAddSuccess");
					jsonResponse.commitAndClose();
					return true;
				}
				jsonResponse.put("info2", "ScoreAddError");
				jsonResponse.commitAndClose();
				return true;
			}
			else 
			{
				jsonResponse.put("info1", "CommentAddError");
				jsonResponse.commitAndClose();
				return true;
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//更改评分
	public boolean updateScore(ScoreInformation scoreInformation) throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			Double oldScore = scoreDao.searchScore(scoreInformation.getScoreAccountName(), scoreInformation.getBookIsbn());
			if (scoreDao.updateScore(scoreInformation) || bookDao.updateCommentScore(scoreInformation.getBookIsbn(), oldScore, scoreInformation.getCommentScore()))
			{
				jsonResponse.put("info", "UpdateSuccess");
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "UpdateError");
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
		
	//查询所有评分
	public boolean findAllScoreRecord() throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			List<ScoreInformation> list = scoreDao.findAllScoreRecord();
			if (list != null)
			{
				jsonResponse.put("info", "Match");
				jsonResponse.put("data", list);
				jsonResponse.put("size", list.size());
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "NotMatch");
			jsonResponse.put("data", emptyScoreInformationList);
			jsonResponse.put("size", 0);
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
		
	//查询某书的所有评分条数
	public boolean searchScoreRecordByBookIsbn(String bookIsbn) throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			List<ScoreInformation> list = scoreDao.searchScoreRecordByBookIsbn(bookIsbn);
			if (list != null)
			{
				jsonResponse.put("info", "Match");
				jsonResponse.put("data", list);
				jsonResponse.put("size", list.size());
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "NotMatch");
			jsonResponse.put("data", emptyScoreInformationList);
			jsonResponse.put("size", 0);
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
		
	//查询某账号的所有评分条数
	public boolean searchScoreRecordByAccountName(String scoreAccountName) throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			List<ScoreInformation> list = scoreDao.searchScoreRecordByAccountName(scoreAccountName);
			if (list != null)
			{
				jsonResponse.put("info", "Match");
				jsonResponse.put("data", list);
				jsonResponse.put("size", list.size());
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "NotMatch");
			jsonResponse.put("data", emptyScoreInformationList);
			jsonResponse.put("size", 0);
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
		
	//查询某账号对某书的评分详情
	public boolean searchScoreRecord(String scoreAccountName,String bookIsbn) throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			List<ScoreInformation> list = scoreDao.searchScoreRecord(scoreAccountName, bookIsbn);
			if (list != null)
			{
				jsonResponse.put("info", "Match");
				jsonResponse.put("data", list);
				jsonResponse.put("size", list.size());
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "NotMatch");
			jsonResponse.put("data", emptyScoreInformationList);
			jsonResponse.put("size", 0);
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
		
	//通过id查询评分详情
	public boolean searchScoreRecordById(String scoreId) throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			List<ScoreInformation> list = scoreDao.searchScoreRecordById(scoreId);
			if (list != null)
			{
				jsonResponse.put("info", "Match");
				jsonResponse.put("data", list);
				jsonResponse.put("size", list.size());
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "NotMatch");
			jsonResponse.put("data", emptyScoreInformationList);
			jsonResponse.put("size", 0);
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
		
	//查询某账号是否评分过某书
	public boolean checkScoreExisted(String scoreAccountName,String bookIsbn) throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			if (scoreDao.checkScoreExisted(scoreAccountName, bookIsbn))
			{
				jsonResponse.put("info", "ScoreExist");
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "ScoreNotExist");
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
		
	//查询某账号对某书的评分
	public boolean searchScore(String scoreAccountName,String bookIsbn) throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			Double score = scoreDao.searchScore(scoreAccountName, bookIsbn);
			if (score >= 0)
			{
				jsonResponse.put("info", "Match");
				jsonResponse.put("score",score);
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "NotMatch");
			jsonResponse.put("score", -1.0);
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	

	
	/*******************************操作普通用户*************************************/
	//普通用户注册
	@Override
	public boolean addUserAccount(UserAccount account) throws HibernateException
	{
		if (userDao.checkUserAccountExisted(account.getAccountName()))
		{
			try 
			{
				JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
				jsonResponse.put("info", "AccountExisted");
				jsonResponse.commitAndClose();
				return true;
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			return false;
		}
		if (userDao.addUserAccount(account))
		{
			try 
			{
				JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
				jsonResponse.put("info", "AddSuccess");
				jsonResponse.commitAndClose();
				return true;
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			return false;
		}
		try 
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			jsonResponse.put("info", "AddError");
			jsonResponse.commitAndClose();
			return true;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//普通用户登录
	@Override
	public boolean userLogin(UserAccount account) throws HibernateException
	{
		UserAccount resultAccount = userDao.userLogin(account.getAccountName());
		
		if (resultAccount != null )
		{
			if (resultAccount.getPasswordMD5().equals(account.getPasswordMD5()))
			{
				try 
				{
					resultAccount.setPasswordMD5("");
					JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
					jsonResponse.put("info", "LoginSuccess");
					jsonResponse.put("data", resultAccount);
					jsonResponse.commitAndClose();
					return true;
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
				return false;
			}
			else 
			{
				try 
				{
					JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
					jsonResponse.put("info", "PasswordWrong");
					jsonResponse.put("data", "");
					jsonResponse.commitAndClose();
					return true;
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
				return false;
			}
		}
		try 
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			jsonResponse.put("info", "AccountNotExisted");
			jsonResponse.put("data", "");
			jsonResponse.commitAndClose();
			return true;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//普通用户自动登录验证
	@Override
	@Deprecated
	public boolean userAutoLogin(String accountName,String passwordMD5) throws HibernateException
	{
		try 
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
		
			UserAccount userAccount = userDao.userLogin(accountName);
			if (userAccount != null)
			{
				if (passwordMD5.equals((userAccount.getPasswordMD5())))
				{
					jsonResponse.put("info", "LoginSuccess");
					jsonResponse.commitAndClose();
					return true;
				}
				else
				{
					jsonResponse.put("info", "PasswordWrong");
					jsonResponse.commitAndClose();
					return true;
				}
			}
			jsonResponse.put("info", "LoginError");
			jsonResponse.commitAndClose();
			return true;
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return false;
	}

	
	//删除普通用户
	@Override
	public boolean deleteUserAccount(String accountName) throws HibernateException
	{
		try 
		{
			UserAccount account = userDao.deleteUserAccount(accountName);
			if (account != null)
			{
				JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
				jsonResponse.put("info", "DeleteSuccess");
				jsonResponse.put("delete_data", account);
				jsonResponse.commitAndClose();
				return true;
			}
			else
			{
				JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
				jsonResponse.put("info", "DeleteError");
				jsonResponse.commitAndClose();
				return true;
			}
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//修改普通用户信息
	public boolean updateUserAccount(UserAccount account) throws HibernateException
	{
		try
		{
			if (userDao.updateUserAccount(account))
			{
				JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
				jsonResponse.put("info", "UpdateSuccess");
				jsonResponse.commitAndClose();
				return true;
			}
			else
			{
				JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
				jsonResponse.put("info", "UpdateError");
				jsonResponse.commitAndClose();
				return true;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	
	//普通用户修改普通信息
	@Override
	public boolean updateUserAccount(String accountName,String newBindStudentAccount,String newNickname,String newSex,String newDescribeWords,String updateTimeStr) throws HibernateException
	{
		try
		{
			UserAccount account = userDao.updateUserAccount(accountName, newBindStudentAccount,newNickname,newSex,newDescribeWords,updateTimeStr);
			if (account != null)
			{
				JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
				jsonResponse.put("info", "UpdateSuccess");
				jsonResponse.put("account", account);
				jsonResponse.commitAndClose();
				return true;
			}
			else
			{
				JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
				jsonResponse.put("info", "UpdateError");
				jsonResponse.commitAndClose();
				return true;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//更改图片地址
	public boolean updateUserImage(String accountName,String userImageRelativePath,String userImageName,File imageFile,String updateTime) throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			
			UserAccount useAccount = userDao.updateUserImage(accountName, userImageRelativePath + "/" + userImageName,updateTime);
			if (useAccount != null)
			{
				if (saveFile(userImageRelativePath, userImageName, imageFile))
				{
					//jsonResponse.put("image_address", userImageRelativePath + "/" + userImageName);
					jsonResponse.put("user_account", useAccount);
					jsonResponse.put("image", "SaveSuccess");
				}
				else 
				{
					jsonResponse.put("image", "SaveError");
				}
				jsonResponse.put("info", "UpdateSuccess");
				jsonResponse.commitAndClose();
				return true;
			}
			else
			{
				jsonResponse.put("info", "UpdateError");
				jsonResponse.commitAndClose();
				return true;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//普通用户修改密码
	@Override
	public boolean updateUserAccount(String accountName,String newPasswordMD5,String updateTime) throws HibernateException
	{
		try
		{
			if (userDao.updateUserAccount(accountName, newPasswordMD5,updateTime))
			{
				JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
				jsonResponse.put("info", "UpdateSuccess");
				jsonResponse.commitAndClose();
				return true;
			}
			else
			{
				JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
				jsonResponse.put("info", "UpdateError");
				jsonResponse.commitAndClose();
				return true;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	//查询所有普通用户
	@Override
	public boolean findAllUserAccount() throws HibernateException
	{
		try 
		{
			List<UserAccount> accounts = userDao.findAllUserAccount();
			if (accounts != null)
			{
				JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
				jsonResponse.put("info", "Match");
				jsonResponse.put("size", accounts.size());
				jsonResponse.put("data", accounts);
				jsonResponse.commitAndClose();
				return true;
			}
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			jsonResponse.put("info", "NotMatch");
			jsonResponse.put("size", 0);
			jsonResponse.put("data", emptyUserAccountList);
			jsonResponse.commitAndClose();
			return true;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	//查询指定普通用户的信息
	public boolean searchUserAccount(String accountName) throws HibernateException
	{
		
		try 
		{
			List<UserAccount> accounts = userDao.searchUserAccount(accountName);
			if (accounts != null)
			{
				JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
				jsonResponse.put("info", "Match");
				jsonResponse.put("data", accounts);
				jsonResponse.put("size", accounts.size());
				jsonResponse.commitAndClose();
				return true;
			}
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			jsonResponse.put("info", "NotMatch");
			jsonResponse.put("data", emptyUserAccountList);
			jsonResponse.put("size",0);
			jsonResponse.commitAndClose();
			return true;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	
	//查询指定普通用户帐号是否存在
	@Override
	public boolean checkUserAccountExisted(String accountName) throws HibernateException
	{
		try 
		{			
			if (userDao.checkUserAccountExisted(accountName))
			{
				JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
				jsonResponse.put("info", "AccountExisted");
				jsonResponse.commitAndClose();
				return true;
			}
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			jsonResponse.put("info", "AccountNotExisted");
			jsonResponse.commitAndClose();
			return true;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//检查某密码的正确性
	@Override
	public boolean checkUserPassword(String accountName,String passwordMD5) throws HibernateException
	{
		try 
		{			
			if (userDao.checkUserPassword(accountName, passwordMD5))
			{
				JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
				jsonResponse.put("info", "PasswordRight");
				jsonResponse.commitAndClose();
				return true;
			}
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			jsonResponse.put("info", "PasswordWrong");
			jsonResponse.commitAndClose();
			return true;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return false;
	}

	
	
	/************************反馈建议***********************/
	//添加一条反馈建议
	public boolean addFeedback(FeedbackInformation feedbackInformation) throws HibernateException
	{
		try 
		{			
			if (feedbackDao.addFeedback(feedbackInformation))
			{
				JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
				jsonResponse.put("info", "AddSuccess");
				jsonResponse.commitAndClose();
				return true;
			}
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			jsonResponse.put("info", "AddError");
			jsonResponse.commitAndClose();
			return true;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//将一条反馈建议更改为处理中状态
	public boolean updateFeedbackHandling(String feedbackId,String handleAccountName)  throws HibernateException
	{
		try 
		{			
			if (feedbackDao.updateFeedbackHandling(feedbackId, handleAccountName))
			{
				JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
				jsonResponse.put("info", "UpdateSuccess");
				jsonResponse.commitAndClose();
				return true;
			}
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			jsonResponse.put("info", "UpdateError");
			jsonResponse.commitAndClose();
			return true;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//将一条反馈建议更改为处理完成状态(之前就要检查是否是同一个管理员操作的)
	public boolean updateFeedbackHandled(String feedbackId,String resultMessage,String handleCompleteTime) throws HibernateException
	{
		try 
		{			
			if (feedbackDao.updateFeedbackHandled(feedbackId, resultMessage, handleCompleteTime))
			{
				JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
				jsonResponse.put("info", "UpdateSuccess");
				jsonResponse.commitAndClose();
				return true;
			}
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			jsonResponse.put("info", "UpdateError");
			jsonResponse.commitAndClose();
			return true;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//查询所有反馈建议
	public boolean findAllFeedback()  throws HibernateException
	{
		try 
		{			
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			List<FeedbackInformation> list = feedbackDao.findAllFeedback();
			if (list != null)
			{
				jsonResponse.put("info", "Match");
				jsonResponse.put("data", list);
				jsonResponse.put("size", list.size());
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "NotMatch");
			jsonResponse.put("data", emptyFeedbackInformationList);
			jsonResponse.put("size", 0);
			jsonResponse.commitAndClose();
			return true;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//通过id查询
	public boolean searchFeedbackById(Integer feedbackId) throws HibernateException
	{
		try 
		{			
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			List<FeedbackInformation> list = feedbackDao.searchFeedbackById(feedbackId);
			if (list != null)
			{
				jsonResponse.put("info", "Match");
				jsonResponse.put("data", list);
				jsonResponse.put("size", list.size());
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "NotMatch");
			jsonResponse.put("data", emptyFeedbackInformationList);
			jsonResponse.put("size", 0);
			jsonResponse.commitAndClose();
			return true;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//查询某账号的反馈建议
	public boolean searchFeedbackByFeedbackAccountName(String feedbackAccountName) throws HibernateException
	{
		try 
		{			
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			List<FeedbackInformation> list = feedbackDao.searchFeedbackByFeedbackAccountName(feedbackAccountName);
			if (list != null)
			{
				jsonResponse.put("info", "Match");
				jsonResponse.put("data", list);
				jsonResponse.put("size", list.size());
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "NotMatch");
			jsonResponse.put("data", emptyFeedbackInformationList);
			jsonResponse.put("size", 0);
			jsonResponse.commitAndClose();
			return true;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//查询某个反馈类别下的所有反馈建议
	public boolean searchFeedbackByCategory(Integer feedbackCategory)  throws HibernateException
	{
		try 
		{			
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			List<FeedbackInformation> list = feedbackDao.searchFeedbackByCategory(feedbackCategory);
			if (list != null)
			{
				jsonResponse.put("info", "Match");
				jsonResponse.put("data", list);
				jsonResponse.put("size", list.size());
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "NotMatch");
			jsonResponse.put("data", emptyFeedbackInformationList);
			jsonResponse.put("size", 0);
			jsonResponse.commitAndClose();
			return true;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	
	
	//查询某个反馈状态下的所有反馈建议
	public boolean searchFeedbackByState(Integer feedbackState)  throws HibernateException
	{
		try 
		{			
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			List<FeedbackInformation> list = feedbackDao.searchFeedbackByState(feedbackState);
			if (list != null)
			{
				jsonResponse.put("info", "Match");
				jsonResponse.put("data", list);
				jsonResponse.put("size", list.size());
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "NotMatch");
			jsonResponse.put("data", emptyFeedbackInformationList);
			jsonResponse.put("size", 0);
			jsonResponse.commitAndClose();
			return true;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//查询某个管理员受理的所有反馈建议
	public boolean searchFeedbackByHandleAccountName(String handleAccountName)  throws HibernateException
	{
		try 
		{			
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			List<FeedbackInformation> list = feedbackDao.searchFeedbackByHandleAccountName(handleAccountName);
			if (list != null)
			{
				jsonResponse.put("info", "Match");
				jsonResponse.put("data", list);
				jsonResponse.put("size", list.size());
				jsonResponse.commitAndClose();
				return true;
			}
			jsonResponse.put("info", "NotMatch");
			jsonResponse.put("data", emptyFeedbackInformationList);
			jsonResponse.put("size", 0);
			jsonResponse.commitAndClose();
			return true;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	
	
}
