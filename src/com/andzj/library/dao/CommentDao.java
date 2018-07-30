package com.andzj.library.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.andzj.library.bean.CommentInformation;

public interface CommentDao {
	
	/********************************操作图书评论*****************************************/
	//增加一条图书评论
	public boolean addComment(CommentInformation comment) throws HibernateException;
	
	//删除一条图书评论
	public CommentInformation deleteComment(String commentId) throws HibernateException;
	
	//修改一条评论
	public boolean updateComment(CommentInformation comment) throws HibernateException;
	
	//查询所有评论
	public List<CommentInformation> findAllComment() throws HibernateException;
	
	//查询某书的评论 
	public List<CommentInformation> searchCommentByBookIsbn(String bookIsbn) throws HibernateException;
	
	//查询某帐号的评论
	public List<CommentInformation> searchCommentByAccountName(String accountName) throws HibernateException;
	
	//查询某人是否评论过某书
	public boolean checkCommentExisted(String commentAccountName,String bookIsbn) throws HibernateException;
}
