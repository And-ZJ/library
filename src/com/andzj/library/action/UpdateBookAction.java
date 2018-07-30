package com.andzj.library.action;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.andzj.library.bean.BookInformation;
import com.andzj.library.service.AdministratorService;
import com.andzj.library.util.Encrypter;
import com.andzj.library.util.MyTimeUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UpdateBookAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	private AdministratorService administratorService;

	public void setAdministratorService(AdministratorService administratorService) {
		this.administratorService = administratorService;
	}
	
	private File choose_file;
	public File getChoose_file() {
		return choose_file;
	}
	public void setChoose_file(File choose_file) {
		this.choose_file = choose_file;
	}
	
	private String choose_fileFileName;
	public String getChoose_fileFileName() {
		return choose_fileFileName;
	}
	public void setChoose_fileFileName(String choose_fileFileName) {
		this.choose_fileFileName = choose_fileFileName;
	}
	
	public String execute() 
	{
		try
		{
			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest)context.get(ServletActionContext.HTTP_REQUEST);
			String bookIsbn= request.getParameter("book_isbn");
			String bookName= request.getParameter("book_name");
			String bookAuthor = request.getParameter("book_author");
			String bookPrice = request.getParameter("book_price");
			String bookPublishCompany = request.getParameter("book_publish_company");
			String bookPublishTime = request.getParameter("book_publish_time");
			String bookSummary = request.getParameter("book_summary");
			
			String bookTotalNumber = request.getParameter("book_total_number");
			String bookRemainNumber = request.getParameter("book_remain_number");
			String bookPosition = request.getParameter("book_position");
			String bookKeyWords = request.getParameter("book_key_words");
			String bookNotes = request.getParameter("book_notes");
			String accountName = request.getParameter("administrator_account_name");
			
			BookInformation book = new BookInformation();
			book.setBookName(bookName);
			book.setBookIsbn(bookIsbn);
			book.setBookAuthor(bookAuthor);
			book.setBookPrice(Double.valueOf(bookPrice));
			book.setBookPublishCompany(bookPublishCompany);
			book.setBookPublishTime(bookPublishTime);
			book.setBookSummary(bookSummary);
			
			book.setBookTotalNumber(Integer.valueOf(bookTotalNumber));
			book.setBookRemainNumber(Integer.valueOf(bookRemainNumber));
			book.setBookAverageScore(Double.valueOf(0.0));
			book.setBookScoreNumber(Integer.valueOf(0));
			book.setBookPosition(bookPosition);
			book.setBookKeyWords(bookKeyWords);
			book.setBookNotes(bookNotes);
			book.setOperateAccountName(accountName);
			String operateTime = MyTimeUtils.getMyTimeStr(new Date());
			book.setOperateTime(operateTime);
			
			if (getChoose_file() != null)
			{
				//String pathStr = ServletActionContext.getServletContext().getRealPath("/") + "book_images";
				//System.out.println("pathString" + pathStr);
				String bookImageName = bookIsbn + choose_fileFileName.substring(choose_fileFileName.indexOf("."));
				//System.out.println(bookImageName);
				book.setBookImageAddress("book_images/" + bookImageName); 
				//System.out.println("Save Book Image:" + book.toString());
				if (administratorService.updateBook(book,"book_images",bookImageName,this.choose_file))
				{
					return null;
				}
				return ERROR;
			}
			//System.out.println("Save Book:" + book.toString());
			if ( administratorService.updateBook(book))
			{
				return null;
			}
			return ERROR;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return ERROR;
	}
}
