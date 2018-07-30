package com.andzj.library.action.app;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.andzj.library.bean.UserAccount;
import com.andzj.library.service.AdministratorService;
import com.andzj.library.util.Encrypter;
import com.andzj.library.util.JsonResponse;
import com.andzj.library.util.MyRegExpUtil;
import com.andzj.library.util.MyTimeUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UserAccountLoginApp  extends ActionSupport{
	private static final long serialVersionUID = 1L;
	private AdministratorService administratorService;

	public void setAdministratorService(AdministratorService administratorService) {
		this.administratorService = administratorService;
	}
	
	public String execute() {
		try {
			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest)context.get(ServletActionContext.HTTP_REQUEST);
			String accountName= request.getParameter("account_name");
			String passwordMD5= request.getParameter("password_md5");
			//String passwordMD5_1 = Encrypter.GetMD5Code(passwordMD5);
			UserAccount account = new UserAccount();
			account.setAccountName(accountName);
			account.setPasswordMD5(passwordMD5);
			if( administratorService.userLogin(account))
			{
				return null;
			}
			return null;
		
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			try 
			{
				JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
				jsonResponse.put("info", "AddError");
				jsonResponse.commitAndClose();
				return null;
			} 
			catch (Exception e2)
			{
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return null;
	}
}
