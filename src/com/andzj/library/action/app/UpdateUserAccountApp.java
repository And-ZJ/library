package com.andzj.library.action.app;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.andzj.library.bean.UserAccount;
import com.andzj.library.service.AdministratorService;
import com.andzj.library.util.Encrypter;
import com.andzj.library.util.MyTimeUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UpdateUserAccountApp extends ActionSupport{
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
			String bindStudentAccount = request.getParameter("bind_student_account");
			String nickname = request.getParameter("nickname");
			String sex = request.getParameter("sex");
			String describeWords = request.getParameter("describe_words");
			String updateTimeStr = MyTimeUtils.getMyTimeStr(new Date());
//			System.out.println(accountName);
//			System.out.println(bindStudentAccount);
//			System.out.println(nickname);
//			System.out.println(sex);
//			System.out.println(describeWords);
			if (administratorService.updateUserAccount(accountName, bindStudentAccount, nickname, sex, describeWords,updateTimeStr))
			{
				return null;
			}
			else 
			{
				return null;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}

