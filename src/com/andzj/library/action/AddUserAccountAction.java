package com.andzj.library.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.andzj.library.bean.AdministratorAccount;
import com.andzj.library.bean.UserAccount;
import com.andzj.library.service.AdministratorService;
import com.andzj.library.util.Encrypter;
import com.andzj.library.util.MyTimeUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AddUserAccountAction  extends ActionSupport{
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
			String password= request.getParameter("password");
			String bindStudentAccount= request.getParameter("bind_student_account");
			String nickname = request.getParameter("nickname");
			String sex= request.getParameter("sex");
			String describeWords= request.getParameter("describe_words");
			String registerTimeStr = MyTimeUtils.getMyTimeStr(new Date());
			String passwordMD5 = Encrypter.GetMD5Code(password);
			String userUpdateTimeStr = registerTimeStr;
			UserAccount account = new UserAccount();
			account.setAccountName(accountName);
			account.setPasswordMD5(passwordMD5);
			account.setRegisterTime(registerTimeStr);
			account.setBindStudentAccount(bindStudentAccount);
			account.setUserNickname(nickname);
			account.setUserSex(sex);
			account.setUserDescribeWords(describeWords);
			account.setUserUpdateTime(userUpdateTimeStr);
			if( administratorService.addUserAccount(account))
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
