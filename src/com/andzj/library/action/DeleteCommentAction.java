package com.andzj.library.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.andzj.library.service.AdministratorService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DeleteCommentAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	private AdministratorService administratorService;

	public void setAdministratorService(AdministratorService administratorService) {
		this.administratorService = administratorService;
	}
	
	public String execute() 
	{
		try {
			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest)context.get(ServletActionContext.HTTP_REQUEST);
			String commentId = request.getParameter("comment_id");
			//String bookIsbn = request.getParameter("book_isbn");
			//String commentAccountName = request.getParameter("comment_account_name");
			if ( administratorService.deleteComment(commentId))
			{
				return null;
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

