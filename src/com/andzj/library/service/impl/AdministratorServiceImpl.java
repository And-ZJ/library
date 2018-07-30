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
 * ����Ա���ʵ�ҵ���ʵ����
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
	
	/***************************�ļ��洢*******************************/
	//�洢�ļ�
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
	
	//ɾ���ļ�
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

	
	
	/***************************��������Ա**************************************/
	//��ӹ���Ա�˻�
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
	
	//����Ա��¼
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
	
	//��ѯָ������Ա�ʺ��Ƿ����
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
		
	//ɾ������Ա�ʺ�
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
			
	//���Ĺ���Ա�ʺŵ��ǳƺ�����
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


	//��ѯ���й���Ա�˻�
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
	
	//��ѯ����Ա�ʺ�
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

	


	/*******************************����ͼ��**************************/
	//���ͼ����Ϣ
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
	//ɾ��ͼ�� 
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
	
	//����ͼ����Ϣ
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
	
	//��ѯ����ͼ��
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
	
	//��ѯisbn�Ƿ��ظ�
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
	
	//��ISBN��ѯͼ��
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
	
	//��������ѯͼ��
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
	
	//�����߲�ѯͼ��
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
	
	//�Գ������ѯͼ��
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
	
	//�Թؼ��ֲ�ѯͼ��
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
	
	//����������в�ѯ
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
	
	//��ѯ����ǰ����鼮
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
	
	/********************************����ͼ�����********************************************/
	//����һ�����ļ�¼
	@Override
	public boolean addBorrow(BorrowInformation borrow) throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			if (!bookDao.checkBookIsbnExisted(borrow.getBookIsbn()))
			{
				jsonResponse.put("info", "BookIsbnNotExisted");//�Ȿ�鲻����
				jsonResponse.commitAndClose();
				return true;
			}	
			if (!userDao.checkUserAccountExisted(borrow.getBorrowAccountName()))
			{
				jsonResponse.put("info", "AccountNotExisted");//�˻�������
				jsonResponse.commitAndClose();
				return true;
			}
			if (borrowDao.checkBorrowExisted(borrow.getBorrowAccountName(), borrow.getBookIsbn()))
			{
				jsonResponse.put("info", "BorrowExisted");//���ڴ������ļ�¼,�����ظ�����
				jsonResponse.commitAndClose();
				return true;
			}
			int remainBorrowNumber = bookDao.findBookBorrowNumber(borrow.getBookIsbn());
			int borrowedNumber = borrowDao.findBookBorrowNumber(borrow.getBookIsbn());
			if ( (remainBorrowNumber - borrowedNumber) <= 0)
			{
				jsonResponse.put("info", "BorrowFull");//�ܽ�Ķ�����,�����ٽ���
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
	
	//ɾ��һ�����ļ�¼
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
	
	//�޸�һ�����ļ�¼ ֻ���޸� �黹ʱ��ͽ���״̬
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
	
	//��ѯ���м�¼
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
	
	//��ѯĳ��Ľ��ļ�¼
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
	
	//��ѯĳ�˺��Ƿ������ĳ��
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

	
	//��ѯĳ�˺ŵĽ��ļ�¼
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
	
	//ͨ�����ݿ�id��ѯ���ļ�¼
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
	
	
	/******************************����������ʷ��¼*********************************/
	//����һ��������ʷ
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
	
	//Ϊĳ��������ʷ�����ӱ�ע
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
	
	//�޸�ĳ��������ʷ�ı�ע
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
	
	//��ѯ���н�����ʷ��¼
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
		
	//��ѯĳ��Ľ�����ʷ��¼
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
		
	//��ѯĳ�˺��Ƿ������ĳ��
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
		
	//��ѯĳ�˺ŵĽ�����ʷ��¼
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

	//ͨ�����ݿ�id��ѯ������ʷ��¼
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
		
	//��ѯĳ��Ľ�����ʷ��¼����
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
	
	
	/********************************����ͼ������*****************************************/
	//����һ��ͼ������
	@Override
	public boolean addComment(CommentInformation comment) throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			if (!bookDao.checkBookIsbnExisted(comment.getBookIsbn()))
			{
				jsonResponse.put("info", "BookIsbnNotExisted");//�Ȿ�鲻����
				jsonResponse.commitAndClose();
				return true;
			}	
			if (!userDao.checkUserAccountExisted(comment.getCommentAccountName()))
			{
				jsonResponse.put("info", "AccountNotExisted");//�˻�������
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
	
	//ɾ��һ��ͼ������
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
	
	//����һ������
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

	
	//��ѯ��������
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

	
	
	//��ѯĳ������� 
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
	
	//��ѯĳ�ʺŵ�����
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
	
	//��ѯĳ���Ƿ����۹�ĳ��
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
	
	
	/******************************����ͼ������**************************************/
	//��������
	public boolean addScore(ScoreInformation scoreInformation) throws HibernateException
	{
		try
		{
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			List<ScoreInformation> list = scoreDao.searchScoreRecord(scoreInformation.getScoreAccountName(), scoreInformation.getBookIsbn());
			if (list != null)//ͬһ��isbn,ֻ��������һ��,�������,���������
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
		
	//�������ۺ�����
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
				if (list != null)//ͬһ��isbn,ֻ��������һ��,�������,���������
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
	
	//��������
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
		
	//��ѯ��������
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
		
	//��ѯĳ���������������
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
		
	//��ѯĳ�˺ŵ�������������
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
		
	//��ѯĳ�˺Ŷ�ĳ�����������
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
	
		
	//ͨ��id��ѯ��������
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
		
	//��ѯĳ�˺��Ƿ����ֹ�ĳ��
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
		
	//��ѯĳ�˺Ŷ�ĳ�������
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
	
	

	
	/*******************************������ͨ�û�*************************************/
	//��ͨ�û�ע��
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
	
	//��ͨ�û���¼
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
	
	//��ͨ�û��Զ���¼��֤
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

	
	//ɾ����ͨ�û�
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
	
	//�޸���ͨ�û���Ϣ
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

	
	//��ͨ�û��޸���ͨ��Ϣ
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
	
	//����ͼƬ��ַ
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
	
	//��ͨ�û��޸�����
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

	//��ѯ������ͨ�û�
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
	
	//��ѯָ����ͨ�û�����Ϣ
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
	
	
	
	
	//��ѯָ����ͨ�û��ʺ��Ƿ����
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
	
	//���ĳ�������ȷ��
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

	
	
	/************************��������***********************/
	//���һ����������
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
	
	//��һ�������������Ϊ������״̬
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
	
	//��һ�������������Ϊ�������״̬(֮ǰ��Ҫ����Ƿ���ͬһ������Ա������)
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
	
	//��ѯ���з�������
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
	
	//ͨ��id��ѯ
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
	
	//��ѯĳ�˺ŵķ�������
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
	
	//��ѯĳ����������µ����з�������
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
	
	
	//��ѯĳ������״̬�µ����з�������
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
	
	//��ѯĳ������Ա��������з�������
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
