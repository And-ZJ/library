package com.andzj.library.action.app;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.andzj.library.bean.BorrowInformation;
import com.andzj.library.service.AdministratorService;
import com.andzj.library.util.MyTimeUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AddBorrowApp extends ActionSupport{
	private static final long serialVersionUID = 1L;
	private AdministratorService administratorService;

	public void setAdministratorService(AdministratorService administratorService) {
		this.administratorService = administratorService;
	}
	
	public String execute() {
		try {
			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest)context.get(ServletActionContext.HTTP_REQUEST);
			String bookIsbn= request.getParameter("book_isbn");
			String borrowAccountName= request.getParameter("borrow_account_name");
			GregorianCalendar calendar = new GregorianCalendar();
			String borrowTimeStr = MyTimeUtils.getMyTimeStr(calendar);

			calendar.add(Calendar.DAY_OF_YEAR,30);
			
			String returnTimeStr = MyTimeUtils.getMyTimeStr(calendar);
			BorrowInformation borrowInformation = new BorrowInformation();
			borrowInformation.setBookIsbn(bookIsbn);
			borrowInformation.setBorrowAccountName(borrowAccountName);
			borrowInformation.setBorrowTime(borrowTimeStr);
			borrowInformation.setReturnTime(returnTimeStr);
			borrowInformation.setBorrowState("正常借出");
			
			//首先查询书的可借数量(由service查询)
			
			if (administratorService.addBorrow(borrowInformation))
			{
				return null;
			}
			return null;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}
}
