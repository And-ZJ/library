package com.andzj.library.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.andzj.library.service.AdministratorService;
import com.andzj.library.util.Encrypter;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UpdateAdminAccountAction extends ActionSupport{
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
			String accountName= request.getParameter("account_name");
			String password= request.getParameter("password");
			String nickname = request.getParameter("nickname");
			String passwordMD5 = Encrypter.GetMD5Code(password);
			
			if (administratorService.updateAdministratorAccount(accountName, nickname, passwordMD5))
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
