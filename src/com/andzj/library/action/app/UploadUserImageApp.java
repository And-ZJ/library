package com.andzj.library.action.app;

import java.io.File;
import java.util.Date;

import javax.rmi.CORBA.Tie;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.andzj.library.bean.UserAccount;
import com.andzj.library.service.AdministratorService;
import com.andzj.library.util.Encrypter;
import com.andzj.library.util.JsonResponse;
import com.andzj.library.util.MyTimeUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UploadUserImageApp extends ActionSupport{
	private static final long serialVersionUID = 1L;
	private AdministratorService administratorService;

	public void setAdministratorService(AdministratorService administratorService) {
		this.administratorService = administratorService;
	}
	
	public File image;
	public String imageFileName;
	public File getImage() {
		return image;
	}
	public void setImage(File image) {
		this.image = image;
	}
	public String getImageFileName() {
		return imageFileName;
	}
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
	
	public String execute() 
	{
		try
		{
			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest)context.get(ServletActionContext.HTTP_REQUEST);
			String userAccountName = request.getParameter("account_name");
			//String theFileName= request.getParameter("theFileName");
			String updateTimeStr = MyTimeUtils.getMyTimeStr(new Date());
			
			if (image != null)
			{
				//System.out.println("image not null");
				//System.out.println("imageFileName:" + imageFileName);
				String userImageStr = userAccountName + imageFileName.substring(imageFileName.indexOf("."));
				//System.out.println(bookImageName);
				
				//System.out.println("Save Book Image:" + book.toString());
				if (administratorService.updateUserImage(userAccountName,"user_images", userImageStr,image, updateTimeStr))
				{
					return null;
				}
			}
			
			return null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return ERROR;
	}

}

