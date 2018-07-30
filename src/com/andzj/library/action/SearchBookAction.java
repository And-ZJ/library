package com.andzj.library.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.andzj.library.service.AdministratorService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SearchBookAction  extends ActionSupport{
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
			//all,name,isbn,author,company,key,most,nothing
			if ("all".equals(searchMode))
			{
				if (administratorService.findAllBook())
				{
					return null;
				}
			}
			else if ("isbn".equals(searchMode))
			{
				String bookIsbn = request.getParameter("search_words");
				if (administratorService.searchBookByIsbn(bookIsbn))
				{
					return null;
				}
			}
			else if ("name".equals(searchMode))
			{
				String bookName = request.getParameter("search_words");
				if (administratorService.searchBookByName(bookName))
				{
					return null;
				}
			}
			else if ("author".equals(searchMode))
			{
				String bookAuthor = request.getParameter("search_words");
				if (administratorService.searchBookByAuthor(bookAuthor))
				{
					return null;
				}
			}
			else if ("company".equals(searchMode))
			{
				String bookPublishCompany = request.getParameter("search_words");
				if (administratorService.searchBookByPublishCompany(bookPublishCompany))
				{
					return null;
				}
			}
			else if ("key".equals(searchMode))
			{
				String bookKeyWords = request.getParameter("search_words");
				if (administratorService.searchBookByKeyWords(bookKeyWords))
				{
					return null;
				}
			}
			else if ("most".equals(searchMode))
			{
				String searchWords = request.getParameter("search_words");
				if (administratorService.searchBook(searchWords))
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
			return ERROR;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return ERROR;
	}
}
