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

public class AddCommentScoreAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	private AdministratorService administratorService;

	public void setAdministratorService(AdministratorService administratorService) {
		this.administratorService = administratorService;
	}
	
	public String execute() {
		try {
			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest)context.get(ServletActionContext.HTTP_REQUEST);
			//request.setCharacterEncoding("ISO-8859-1");
			String bookIsbn= request.getParameter("book_isbn");
			String commentAccountName= request.getParameter("comment_account_name");
			String scoreStr = request.getParameter("score");
			String commentContent = request.getParameter("comment_content");
			
			String time = MyTimeUtils.getMyTimeStr(new Date());
			
			ScoreInformation scoreInformation = new ScoreInformation();
			scoreInformation.setBookIsbn(bookIsbn);
			scoreInformation.setScoreAccountName(commentAccountName);
			scoreInformation.setCommentScore(Double.valueOf(scoreStr));
			scoreInformation.setScoreTime(time);
			
			CommentInformation commentInformation = new CommentInformation();
			commentInformation.setBookIsbn(bookIsbn);
			commentInformation.setCommentAccountName(commentAccountName);
			commentInformation.setCommentContent(commentContent);
			commentInformation.setCommentTime(time);
			
			if (administratorService.addCommentWithScore(commentInformation, scoreInformation))
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
