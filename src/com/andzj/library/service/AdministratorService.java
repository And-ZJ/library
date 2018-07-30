package com.andzj.library.service;

import java.io.File;
import java.io.IOException;

import org.hibernate.HibernateException;

import com.andzj.library.bean.AdministratorAccount;
import com.andzj.library.bean.BookInformation;
import com.andzj.library.bean.BorrowHistoryInformation;
import com.andzj.library.bean.BorrowInformation;
import com.andzj.library.bean.CommentInformation;
import com.andzj.library.bean.FeedbackInformation;
import com.andzj.library.bean.ScoreInformation;
import com.andzj.library.bean.UserAccount;


/**
 * 管理员访问的业务层接口
 * @author zj
 *
 */
public interface AdministratorService {
	/***************************文件存储*******************************/
	//存储文件
	public boolean saveFile(String fileRelativePathStr,String fileNameStr,File file)  throws IOException;
	//删除文件
	public boolean deleteFile(String fileRelativePathStr,String fileNameStr) throws IOException;
	
	/***************************操作管理员******************************/
	//添加管理员账户
	public boolean addAdministratorAccount(AdministratorAccount account)  throws HibernateException;
	
	//管理员帐号登录
	public boolean administratorLogin(AdministratorAccount account)  throws HibernateException;
	
	//查询所有管理员帐号
	public boolean findAllAdminAccount()  throws HibernateException;
	
	//查询管理员帐号
	public boolean searchAdminAccount(String accountName) throws HibernateException;
	
	//查询指定管理员帐号是否存在
	public boolean checkAdminAccountExisted(String accountName) throws HibernateException;
	
	//删除管理员帐号
	public boolean deleteAdministratorAccount(String operateAccountName,String accountName) throws HibernateException;
	
	//更改管理员帐号的昵称和密码
	public boolean updateAdministratorAccount(String accountName,String newNickname,String newPasswordMD5) throws HibernateException;
	
	

	/*******************************操作图书**************************/
	
	//添加图书信息
	public boolean addBook(BookInformation book)  throws HibernateException;
	public boolean addBook(BookInformation book,String bookImageRelativePath,String bookImageName,File file) throws HibernateException;
	
	//删除图书 
	public boolean deleteBook(String bookIsbn) throws HibernateException;
	
	//更新图书信息
	public boolean updateBook(BookInformation book) throws HibernateException;
	public boolean updateBook(BookInformation book,String bookImageRelativePath,String bookImageName,File file) throws HibernateException;
	
	//查询所有图书
	public boolean findAllBook() throws HibernateException;
	
	//查询isbn是否重复
	public boolean checkBookIsbnExisted(String bookIsbn) throws HibernateException;
	
	//以ISBN查询图书
	public boolean searchBookByIsbn(String bookIsbn) throws HibernateException;
	
	//以书名查询图书
	public boolean searchBookByName(String bookName) throws HibernateException;
	
	//以作者查询图书
	public boolean searchBookByAuthor(String bookAuthor) throws HibernateException;
	
	//以出版社查询图书
	public boolean searchBookByPublishCompany(String bookPublishCompany) throws HibernateException;
	
	//以关键字查询图书
	public boolean searchBookByKeyWords(String bookKeyWords) throws HibernateException;
	
	//在以上类别中查询
	public boolean searchBook(String searchWords) throws HibernateException;
	
	//查询评分前面的书籍
	public boolean searchHotBook() throws HibernateException;
	
	/********************************操作图书借阅********************************************/
	//增加一条借阅记录
	public boolean addBorrow(BorrowInformation borrow) throws HibernateException;
	
	//删除一条借阅记录
	public boolean deleteBorrow(String borrowId) throws HibernateException;
	
	//修改一条借阅记录
	public boolean updateBorrow(BorrowInformation borrow) throws HibernateException;
	
	//查询所有记录
	public boolean findAllBorrow() throws HibernateException;
	
	//查询某书的借阅记录
	public boolean searchBorrowByBookIsbn(String bookIsbn) throws HibernateException;
	
	//查询某账号是否借阅了某书
	public boolean checkBorrowExisted(String borrowAccountName,String bookIsbn) throws HibernateException;

	//查询某账号的借阅记录
	public boolean searchBorrowByAccountName(String accountName) throws HibernateException;
	
	//通过数据库id查询借阅记录
	public boolean searchBorrowById(String borrowId) throws HibernateException;
	
	
	/******************************操作借阅历史记录*********************************/
	//增加一条借阅历史
	public boolean addBorrowHistory(BorrowHistoryInformation borrowHistoryInformation) throws HibernateException;
	
	//为某条借阅历史的增加备注
	public boolean addBorrowHistoryNotes(String historyId,String historyNotes) throws HibernateException;
	
	//修改某条借阅历史的备注
	public boolean updateBorrowHistoryNotes(String historyId,String newHistoryNotes) throws HibernateException;
	
	//查询所有借阅历史记录
	public boolean findAllBorrowHistory() throws HibernateException;
		
	//查询某书的借阅历史记录
	public boolean searchBorrowHistoryByBookIsbn(String bookIsbn) throws HibernateException;
		
	//查询某账号是否借阅了某书
	public boolean checkBorrowHistoryExisted(String accountName,String bookIsbn) throws HibernateException;
		
	//查询某账号的借阅历史记录
	public boolean searchBorrowHistoryByAccountName(String accountName) throws HibernateException;
 
	//通过数据库id查询借阅历史记录
	public boolean searchBorrowHistoryById(String historyId) throws HibernateException;
		
	//查询某书的借阅历史记录条数
	public boolean findBookBorrowHistoryNumber(String bookIsbn) throws HibernateException;
	
	
	/********************************操作图书评论*****************************************/
	//增加一条图书评论
	public boolean addComment(CommentInformation comment) throws HibernateException;
	
	//删除一条图书评论
	public boolean deleteComment(String commentId) throws HibernateException;
	
	//更新一条评论
	public boolean updateComment(CommentInformation commentInformation) throws HibernateException;
	
	//查询所有评论
	public boolean findAllComment() throws HibernateException;

	//查询某书的评论 
	public boolean searchCommentByBookIsbn(String bookIsbn) throws HibernateException;
	
	//查询某帐号的评论
	public boolean searchCommentByAccountName(String accountName) throws HibernateException;
	
	//查询某人是否评论过某书
	public boolean checkCommentExisted(String commentAccountName,String bookIsbn) throws HibernateException;

	
	/******************************操作图书评分**************************************/
	//增加评分
	public boolean addScore(ScoreInformation scoreInformation) throws HibernateException;
		
	//增加评论和评分
	public boolean addCommentWithScore(CommentInformation commentInformation,ScoreInformation scoreInformation) throws HibernateException;
	
	//更改评分
	public boolean updateScore(ScoreInformation scoreInformation) throws HibernateException;
		
	//查询所有评分
	public boolean findAllScoreRecord() throws HibernateException;
		
	//查询某书的所有评分条数
	public boolean searchScoreRecordByBookIsbn(String bookIsbn) throws HibernateException;
		
	//查询某账号的所有评分条数
	public boolean searchScoreRecordByAccountName(String scoreAccountName) throws HibernateException;
		
	//查询某账号对某书的评分详情
	public boolean searchScoreRecord(String scoreAccountName,String bookIsbn) throws HibernateException;
		
	//通过id查询评分详情
	public boolean searchScoreRecordById(String scoreId) throws HibernateException;
		
	//查询某账号是否评分过某书
	public boolean checkScoreExisted(String scoreAccountName,String bookIsbn) throws HibernateException;
		
	//查询某账号对某书的评分
	public boolean searchScore(String scoreAccountName,String bookIsbn) throws HibernateException;
	
	
	
	/*******************************操作普通用户*************************************/
	//普通用户注册
	public boolean addUserAccount(UserAccount account) throws HibernateException;
	
	//普通用户登录
	public boolean userLogin(UserAccount account) throws HibernateException;
	
	//普通用户自动登录验证
	public boolean userAutoLogin(String accountName,String passwordMD5) throws HibernateException;

	
	//删除普通用户
	public boolean deleteUserAccount(String accountName) throws HibernateException;
	
	//修改普通用户信息
	public boolean updateUserAccount(UserAccount account) throws HibernateException;

	//普通用户修改普通信息
	public boolean updateUserAccount(String accountName,String newBindStudentAccount,String newNickname,String newSex,String newDescribeWords,String updateTime) throws HibernateException;
	
	//普通用户修改密码
	public boolean updateUserAccount(String accountName,String newPasswordMD5,String updateTime) throws HibernateException;

	//更改图片地址
	public boolean updateUserImage(String accountName,String userImageRelativePath,String userImageName,File imageFile,String updateTime) throws HibernateException;
	
	//查询所有普通用户
	public boolean findAllUserAccount() throws HibernateException;
	
	//查询指定普通用户的信息
	public boolean searchUserAccount(String accountName) throws HibernateException;
	
	
	//查询指定普通用户帐号是否存在
	public boolean checkUserAccountExisted(String accountName) throws HibernateException;

	//检查某密码的正确性
	public boolean checkUserPassword(String accountName,String passwordMD5) throws HibernateException;

	
	
	/************************反馈建议***********************/
	//添加一条反馈建议
	public boolean addFeedback(FeedbackInformation feedbackInformation) throws HibernateException;
	
	//将一条反馈建议更改为处理中状态
	public boolean updateFeedbackHandling(String feedbackId,String handleAccountName)  throws HibernateException;
	
	//将一条反馈建议更改为处理完成状态(之前就要检查是否是同一个管理员操作的)
	public boolean updateFeedbackHandled(String feedbackId,String resultMessage,String handleCompleteTime) throws HibernateException;
	
	//查询所有反馈建议
	public boolean findAllFeedback()  throws HibernateException;
	
	//通过id查询
	public boolean searchFeedbackById(Integer feedbackId) throws HibernateException;
	
	//查询某账号的反馈建议
	public boolean searchFeedbackByFeedbackAccountName(String feedbackAccountName) throws HibernateException;
	
	//查询某个反馈类别下的所有反馈建议
	public boolean searchFeedbackByCategory(Integer feedbackCategory)  throws HibernateException;
	
	//查询某个反馈状态下的所有反馈建议
	public boolean searchFeedbackByState(Integer feedbackState)  throws HibernateException;
	
	//查询某个管理员受理的所有反馈建议
	public boolean searchFeedbackByHandleAccountName(String handleAccountName)  throws HibernateException;
	
	
}
