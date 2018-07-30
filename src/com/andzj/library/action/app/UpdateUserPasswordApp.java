package com.andzj.library.action.app;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.andzj.library.service.AdministratorService;
import com.andzj.library.util.MyTimeUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UpdateUserPasswordApp extends ActionSupport{
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
			String accountName = request.getParameter("account_name");
			String passwordMD5 = request.getParameter("password_md5");
			String updateTimeStr = MyTimeUtils.getMyTimeStr(new Date());
			if (administratorService.updateUserAccount(accountName, passwordMD5,updateTimeStr))
			{
				return null;
			}
			return null;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
