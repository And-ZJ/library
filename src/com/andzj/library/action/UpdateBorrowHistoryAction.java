package com.andzj.library.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.andzj.library.service.AdministratorService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UpdateBorrowHistoryAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	private AdministratorService administratorService;

	public void setAdministratorService(AdministratorService administratorService) {
		this.administratorService = administratorService;
	}
	
	public String execute() {
		try 
		{
			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest)context.get(ServletActionContext.HTTP_REQUEST);
			String historyId = request.getParameter("history_id");
			String historyNotes = request.getParameter("history_notes");
			if (administratorService.updateBorrowHistoryNotes(historyId, historyNotes))
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
