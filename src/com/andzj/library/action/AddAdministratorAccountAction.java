package com.andzj.library.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.andzj.library.bean.AdministratorAccount;
import com.andzj.library.service.AdministratorService;
import com.andzj.library.util.Encrypter;
import com.andzj.library.util.Info;
import com.andzj.library.util.MyTimeUtils;
import com.andzj.library.util.ResultUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class AddAdministratorAccountAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	private AdministratorService administratorService;

	public void setAdministratorService(AdministratorService administratorService) {
		this.administratorService = administratorService;
	}
	
	public String execute() {
		try {
			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest)context.get(ServletActionContext.HTTP_REQUEST);
			String accountName= request.getParameter("account_name");//后台用户名
			String password= request.getParameter("password");//后台密码
			String nickname = request.getParameter("nickname");
			String registerTimeStr = MyTimeUtils.getMyTimeStr(new Date());
			String passwordMD5 = Encrypter.GetMD5Code(password);
			
			AdministratorAccount account = new AdministratorAccount();
			account.setAccountName(accountName);
			account.setPasswordMD5(passwordMD5);
			account.setAdministratorNickname(nickname);
			account.setRegisterTime(registerTimeStr);
			
			if (administratorService.addAdministratorAccount(account))
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
