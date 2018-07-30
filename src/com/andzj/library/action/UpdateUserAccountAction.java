package com.andzj.library.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.andzj.library.bean.UserAccount;
import com.andzj.library.service.AdministratorService;
import com.andzj.library.util.Encrypter;
import com.andzj.library.util.MyTimeUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UpdateUserAccountAction extends ActionSupport{
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
			String updateMode = request.getParameter("mode");
			String updateTimeStr = MyTimeUtils.getMyTimeStr(new Date());
			if ("password".equals(updateMode))
			{
				String password = request.getParameter("password");
				String passwordMD5 = Encrypter.GetMD5Code(password);
				if (administratorService.updateUserAccount(accountName, passwordMD5,updateTimeStr))
				{
					return null;
				}
				else 
				{
					return ERROR;
				}
			}
			else if ("other".equals(updateMode))
			{
				String bindStudentAccount = request.getParameter("bind_student_account");
				String nickname = request.getParameter("nickname");
				
				String sex = request.getParameter("sex");
				String describeWords = request.getParameter("describe_words");
				
				if (administratorService.updateUserAccount(accountName, bindStudentAccount, nickname, sex, describeWords,updateTimeStr))
				{
					return null;
				}
				else 
				{
					return ERROR;
				}
			}
			else if ("both".equals(updateMode))
			{
				String password = request.getParameter("password");
				String passwordMD5 = Encrypter.GetMD5Code(password);
				String bindStudentAccount = request.getParameter("bind_student_account");
				String nickname = request.getParameter("nickname");
				String sex = request.getParameter("sex");
				String describeWords = request.getParameter("describe_words");
				UserAccount account = new UserAccount();
				account.setAccountName(accountName);
				account.setBindStudentAccount(bindStudentAccount);
				account.setPasswordMD5(passwordMD5);
				account.setUserDescribeWords(describeWords);
				account.setUserNickname(nickname);
				account.setUserSex(sex);
				account.setUserUpdateTime(updateTimeStr);

				if (administratorService.updateUserAccount(account))
				{
					return null;
				}
				else 
				{
					return ERROR;
				}
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

