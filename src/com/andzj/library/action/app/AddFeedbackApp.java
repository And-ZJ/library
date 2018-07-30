package com.andzj.library.action.app;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.andzj.library.bean.BorrowInformation;
import com.andzj.library.bean.FeedbackInformation;
import com.andzj.library.service.AdministratorService;
import com.andzj.library.util.MyTimeUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AddFeedbackApp extends ActionSupport{
	private static final long serialVersionUID = 1L;
	private AdministratorService administratorService;

	public void setAdministratorService(AdministratorService administratorService) {
		this.administratorService = administratorService;
	}
	
	public String execute() {
		try {
			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest)context.get(ServletActionContext.HTTP_REQUEST);
			String feedbackAccountName= request.getParameter("feedback_account_name");
			String feedbackContent= request.getParameter("feedback_content");
			String feedbackCategoryStr = request.getParameter("feedback_category");
			Integer feedbackCategory = Integer.valueOf(feedbackCategoryStr);
			String feedbackTimeStr = MyTimeUtils.getMyTimeStr(new Date());

			FeedbackInformation feedbackInformation = new FeedbackInformation();
			feedbackInformation.setFeedbackAccountName(feedbackAccountName);
			feedbackInformation.setFeedbackContent(feedbackContent);
			feedbackInformation.setFeedbackTime(feedbackTimeStr);
			feedbackInformation.setFeedbackCategory(feedbackCategory);
			feedbackInformation.setFeedbackState(1);
			
			
			if (administratorService.addFeedback(feedbackInformation))
			{
				return null;
			}
			return null;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}
}
