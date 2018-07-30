package com.andzj.library.action;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.andzj.library.bean.BorrowInformation;
import com.andzj.library.service.AdministratorService;
import com.andzj.library.util.Encrypter;
import com.andzj.library.util.MyTimeUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UpdateBorrowAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	private AdministratorService administratorService;

	public void setAdministratorService(AdministratorService administratorService) {
		this.administratorService = administratorService;
	}
	
	public String execute() 
	{
		try
		{
			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest)context.get(ServletActionContext.HTTP_REQUEST);
			String bookIsbn= request.getParameter("book_isbn");
			String borrowAccountName= request.getParameter("borrow_account_name");
			String borrowTime = request.getParameter("borrow_time");
			String returnDays = request.getParameter("return_days");
			String borrowState = request.getParameter("borrow_state");
			
	
			GregorianCalendar calendar = MyTimeUtils.getCalendar(borrowTime);
			calendar.add(Calendar.DAY_OF_YEAR,Integer.valueOf(returnDays));
			String returnTimeStr = MyTimeUtils.getMyTimeStr(calendar);
			
			//System.out.println("borrowTime:" + borrowTime + ",returnTime:" + returnTimeStr);
			
			BorrowInformation borrowInformation = new BorrowInformation();
			borrowInformation.setBookIsbn(bookIsbn);
			borrowInformation.setBorrowAccountName(borrowAccountName);
			borrowInformation.setBorrowTime(borrowTime);
			borrowInformation.setReturnTime(returnTimeStr);
			borrowInformation.setBorrowState(borrowState);
			
			
			if (administratorService.updateBorrow(borrowInformation))
			{
				return null;
			}
			else 
			{
				return ERROR;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return ERROR;
	}
}
