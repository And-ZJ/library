package com.andzj.library.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.andzj.library.bean.CommentInformation;
import com.andzj.library.bean.ScoreInformation;
import com.andzj.library.service.AdministratorService;
import com.andzj.library.util.MyTimeUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AddScoreAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	private AdministratorService administratorService;

	public void setAdministratorService(AdministratorService administratorService) {
		this.administratorService = administratorService;
	}
	
	public String execute() {
		try {
			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest)context.get(ServletActionContext.HTTP_REQUEST);
			String bookIsbn= request.getParameter("book_isbn");
			String scoreAccountName= request.getParameter("score_account_name");
			String commentScore = request.getParameter("comment_score");
			
			String scoreTime = MyTimeUtils.getMyTimeStr(new Date());
			
			ScoreInformation scoreInformation = new ScoreInformation();
			scoreInformation.setBookIsbn(bookIsbn);
			scoreInformation.setScoreAccountName(scoreAccountName);
			scoreInformation.setCommentScore(Double.valueOf(commentScore));
			scoreInformation.setScoreTime(scoreTime);
			
			if (administratorService.addScore(scoreInformation))
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
