package com.andzj.library.action.app;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.tools.ant.util.regexp.Regexp;

import com.andzj.library.bean.UserAccount;
import com.andzj.library.service.AdministratorService;
import com.andzj.library.util.Encrypter;
import com.andzj.library.util.JsonResponse;
import com.andzj.library.util.MyRegExpUtil;
import com.andzj.library.util.MyTimeUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class RegisterUserAccountApp extends ActionSupport{
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
			//String sex= request.getParameter("sex");
			//String describeWords= request.getParameter("describe_words");
			String registerTimeStr = MyTimeUtils.getMyTimeStr(new Date());
			String passwordMD5 = Encrypter.GetMD5Code(password);
			
			if (!MyRegExpUtil.matchPhoneNumber(accountName))
			{
				JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
				jsonResponse.put("info", "AccountFormatError");
				jsonResponse.commitAndClose();
				return null;
			}
			
			if (!MyRegExpUtil.matchOnlyDigit(bindStudentAccount))
			{
				JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
				jsonResponse.put("info", "StudentFormatError");
				jsonResponse.commitAndClose();
				return null;
			}
			
			String userUpdateTimeStr = registerTimeStr;
			UserAccount account = new UserAccount();
			account.setAccountName(accountName);
			account.setPasswordMD5(passwordMD5);
			account.setRegisterTime(registerTimeStr);
			account.setBindStudentAccount(bindStudentAccount);
			account.setUserNickname(nickname);
			account.setUserSex("±£√‹");
			account.setUserDescribeWords("");
			account.setUserUpdateTime(userUpdateTimeStr);
			if( administratorService.addUserAccount(account))
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
