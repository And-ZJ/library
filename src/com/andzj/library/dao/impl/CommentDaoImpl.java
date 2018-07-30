package com.andzj.library.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.andzj.library.bean.CommentInformation;
import com.andzj.library.dao.CommentDao;

public class CommentDaoImpl extends HibernateDaoSupport implements CommentDao{
	/********************************����ͼ������*****************************************/
	//����һ��ͼ������
	@Override
	public boolean addComment(CommentInformation comment) throws HibernateException
	{
		getHibernateTemplate().save("CommentInformation",comment);
		return true;
	}
	
	//ɾ��һ��ͼ������
	@Override
	public CommentInformation deleteComment(String commentId) throws HibernateException
	{
		String hql = " from CommentInformation where comment_id = ?";
		List<CommentInformation> list = getHibernateTemplate().find(hql,commentId);
		if (list != null && list.size() >0)
		{
			getHibernateTemplate().delete("CommentInformation",list.get(0));
			return list.get(0);
		}
		return null;
	}
	
	//�޸�һ������
	@Override
	public boolean updateComment(CommentInformation comment) throws HibernateException
	{
		String hql = " from CommentInformation where comment_account_name = ? AND book_isbn = ?";
		List<CommentInformation> list = getHibernateTemplate().find(hql,comment.getCommentAccountName(),comment.getBookIsbn());
		if (list != null && list.size() >0)
		{
			CommentInformation newComment = list.get(0);
			newComment.setCommentContent(comment.getCommentContent());
			newComment.setCommentTime(comment.getCommentTime());
			getHibernateTemplate().update("CommentInformation",newComment);
			return true;
		}
		return false;
	}
	
	//��ѯ��������
	@Override
	public List<CommentInformation> findAllComment() throws HibernateException
	{
		String hql = " from CommentInformation ";
		List<CommentInformation> list = getHibernateTemplate().find(hql);
		if (list !=null && list.size() >0)
		{
			return list;
		}
		return null;
	}

	
	//��ѯĳ������� 
	@Override
	public List<CommentInformation> searchCommentByBookIsbn(String bookIsbn) throws HibernateException
	{
		String hql = " from CommentInformation where book_isbn = ?";
		List<CommentInformation> list = getHibernateTemplate().find(hql,bookIsbn);
		if (list != null && list.size() >0)
		{
			return list;
		}
		return null;
	}
	
	//��ѯĳ�ʺŵ�����
	@Override
	public List<CommentInformation> searchCommentByAccountName(String accountName) throws HibernateException
	{
		String hql = " from CommentInformation where comment_account_name = ?";
		List<CommentInformation> list = getHibernateTemplate().find(hql,accountName);
		if (list != null && list.size() >0)
		{
			return list;
		}
		return null;
	}
	
	//��ѯĳ���Ƿ����۹�ĳ��
	public boolean checkCommentExisted(String commentAccountName,String bookIsbn) throws HibernateException
	{
		String hql = " from CommentInformation where comment_account_name = ? AND book_isbn = ?";
		List<CommentInformation> list = getHibernateTemplate().find(hql,commentAccountName,bookIsbn);
		if (list != null && list.size() >0)
		{
			return true;
		}
		return false;
	}
	
}
