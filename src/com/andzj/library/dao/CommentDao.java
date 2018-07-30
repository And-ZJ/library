package com.andzj.library.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.andzj.library.bean.CommentInformation;

public interface CommentDao {
	
	/********************************����ͼ������*****************************************/
	//����һ��ͼ������
	public boolean addComment(CommentInformation comment) throws HibernateException;
	
	//ɾ��һ��ͼ������
	public CommentInformation deleteComment(String commentId) throws HibernateException;
	
	//�޸�һ������
	public boolean updateComment(CommentInformation comment) throws HibernateException;
	
	//��ѯ��������
	public List<CommentInformation> findAllComment() throws HibernateException;
	
	//��ѯĳ������� 
	public List<CommentInformation> searchCommentByBookIsbn(String bookIsbn) throws HibernateException;
	
	//��ѯĳ�ʺŵ�����
	public List<CommentInformation> searchCommentByAccountName(String accountName) throws HibernateException;
	
	//��ѯĳ���Ƿ����۹�ĳ��
	public boolean checkCommentExisted(String commentAccountName,String bookIsbn) throws HibernateException;
}
