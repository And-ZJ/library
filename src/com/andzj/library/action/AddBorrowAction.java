package com.andzj.library.action;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.andzj.library.bean.AdministratorAccount;
import com.andzj.library.bean.BookInformation;
import com.andzj.library.bean.BorrowInformation;
import com.andzj.library.service.AdministratorService;
import com.andzj.library.util.Encrypter;
import com.andzj.library.util.MyTimeUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AddBorrowAction extends ActionSupport{
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
			String returnDays = request.getParameter("return_days");
			GregorianCalendar calendar = new GregorianCalendar();
			String borrowTimeStr = MyTimeUtils.getMyTimeStr(calendar);
			
			if (returnDays != null && !"".equals(returnDays))
			{
				//如果设置了归还天数
				calendar.add(Calendar.DAY_OF_YEAR,Integer.valueOf(returnDays));
			}
			else 
			{
				calendar.add(Calendar.DAY_OF_YEAR,30);
			}
			
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
			return ERROR;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return ERROR;
	}
}
