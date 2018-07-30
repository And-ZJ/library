package com.andzj.library.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.andzj.library.bean.AdministratorAccount;
import com.andzj.library.service.AdministratorService;
import com.andzj.library.util.Encrypter;
import com.andzj.library.util.JsonResponse;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class AdministratorLoginAction extends ActionSupport{

	/**
	 * 注入业务层的类
	 */
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
			String accountName= request.getParameter("account_name");//后台用户名
			String password= request.getParameter("password");//后台密码
			String passwordMD5 = Encrypter.GetMD5Code(password);
			AdministratorAccount loginAccount = new AdministratorAccount();
			loginAccount.setAccountName(accountName);
			loginAccount.setPasswordMD5(passwordMD5);
			if (administratorService.administratorLogin(loginAccount))
			{
				return null;
			}
			return ERROR;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
		
	}
	
	
	
}
