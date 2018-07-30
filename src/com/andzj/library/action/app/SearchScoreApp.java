package com.andzj.library.action.app;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.andzj.library.service.AdministratorService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SearchScoreApp extends ActionSupport{
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
			String searchMode = request.getParameter("mode");
			//all,account,isbn,nothing
			if ("all".equals(searchMode))
			{
				if (administratorService.findAllComment())
				{
					return null;
				}
			}
			else if ("account".equals(searchMode))
			{
				String scoreAccountName= request.getParameter("search_words");
				if (administratorService.searchScoreRecordByAccountName(scoreAccountName))
				{
					return null;
				}
			}
			else if ("isbn".equals(searchMode))
			{
				String bookIsbn = request.getParameter("search_words");
				if (administratorService.searchScoreRecordByBookIsbn(bookIsbn))
				{
					return null;
				}
			}
			else if ("nothing".equals(searchMode)) 
			{
				return null;
			}
			else 
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

