package com.andzj.library.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hslf.blip.Bitmap;
import org.apache.struts2.ServletActionContext;

import com.andzj.library.service.AdministratorService;
import com.andzj.library.util.Encrypter;
import com.andzj.library.util.JsonResponse;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UploadBookImageAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	private AdministratorService administratorService;
	
	private File choose_file;
	public File getChoose_file() {
		return choose_file;
	}
	public void setChoose_file(File choose_file) {
		this.choose_file = choose_file;
	}
	
	private String choose_fileFileName;
	public String getChoose_fileFileName() {
		return choose_fileFileName;
	}
	public void setChoose_fileFileName(String choose_fileFileName) {
		this.choose_fileFileName = choose_fileFileName;
	}

	public void setAdministratorService(AdministratorService administratorService) {
		this.administratorService = administratorService;
	}
	
	public String execute() 
	{
		
		try
		{
			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest)context.get(ServletActionContext.HTTP_REQUEST);
			String bookIsbn = request.getParameter("book_isbn");
			String bookAuthor = request.getParameter("book_author");
			System.out.println("bookIsbn:" + bookIsbn  + "  bookAuthor:" + bookAuthor);
			
			String pathString = ServletActionContext.getServletContext().getRealPath("/");
			//System.out.println("pathString" + pathString);
			String imagePath = pathString + "book_images";
			File folder = new File(imagePath);
			if (!folder.exists())
			{
				folder.mkdir();
			}
			//System.out.println("imagePath:" + imagePath);

			String bookImageName = bookIsbn + choose_fileFileName.substring(choose_fileFileName.indexOf("."));
			//System.out.println(bookImageName);
			File imageFile = new File(imagePath + "\\" + bookImageName);
			if (imageFile.exists())
			{
				imageFile.delete();
			}
			imageFile.createNewFile();
			FileInputStream inputStream = new FileInputStream(this.choose_file);
			FileOutputStream outputStream = new FileOutputStream(imageFile);
			byte[] buf = new byte[1024];
			int length = 0;
			while ((length = inputStream.read(buf)) != -1)
			{
				outputStream.write(buf, 0 , length);
			}
			inputStream.close();
			outputStream.flush();
			outputStream.close();
			JsonResponse jsonResponse = new JsonResponse(ServletActionContext.getResponse());
			jsonResponse.put("info", "UploadSuccess");
			jsonResponse.commitAndClose();
			return null;
//			if (//administratorService.updateAdministratorAccount(accountName, nickname, passwordMD5))
//			{
//				return null;
//			}
//			else 
//			{
//				return ERROR;
//			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return ERROR;
	}
}
