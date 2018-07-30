package com.andzj.library.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.andzj.library.bean.ScoreInformation;
import com.andzj.library.dao.ScoreDao;

public class ScoreDaoImpl extends HibernateDaoSupport implements ScoreDao{
	/****************************�����鼮����*********************************/
	//��������
	@Override
	public boolean addScore(ScoreInformation scoreInformation) throws HibernateException
	{
		getHibernateTemplate().save("ScoreInformation",scoreInformation);
		return true;
	}
	
	//��������
	@Override
	public boolean updateScore(ScoreInformation scoreInformation) throws HibernateException
	{
		String hql = "from ScoreInformation where score_account_name = ? AND book_isbn = ?";
		List<ScoreInformation> list = getHibernateTemplate().find(hql,scoreInformation.getScoreAccountName(),scoreInformation.getBookIsbn());
		if (list != null && list.size() >0)
		{
			ScoreInformation information = list.get(0);
			information.setCommentScore(scoreInformation.getCommentScore());
			information.setScoreTime(scoreInformation.getScoreTime());
			getHibernateTemplate().update("ScoreInformation",information);
			return true;
		}
		return false;
	}
	
	//��ѯ��������
	@Override
	public List<ScoreInformation> findAllScoreRecord() throws HibernateException
	{
		String hql = " from ScoreInformation ";
		List<ScoreInformation> list = getHibernateTemplate().find(hql);
		if (list != null && list.size() >0)
		{
			return list;
		}
		return null;
	}
	
	//��ѯĳ���������������
	@Override
	public List<ScoreInformation> searchScoreRecordByBookIsbn(String bookIsbn) throws HibernateException
	{
		String hql = " from ScoreInformation where book_isbn = ?";
		List<ScoreInformation> list = getHibernateTemplate().find(hql,bookIsbn);
		if (list != null && list.size() >0)
		{
			return list;
		}
		return null;
	}
	
	//��ѯĳ�˺ŵ�������������
	@Override
	public List<ScoreInformation> searchScoreRecordByAccountName(String scoreAccountName) throws HibernateException
	{
		String hql = " from ScoreInformation where score_account_name = ?";
		List<ScoreInformation> list = getHibernateTemplate().find(hql,scoreAccountName);
		if (list != null && list.size() >0)
		{
			return list;
		}
		return null;
	}
	
	//��ѯĳ�˺Ŷ�ĳ�����������
	@Override
	public List<ScoreInformation> searchScoreRecord(String scoreAccountName,String bookIsbn) throws HibernateException
	{
		String hql = " from ScoreInformation where score_account_name = ? AND book_isbn = ?";
		List<ScoreInformation> list = getHibernateTemplate().find(hql,scoreAccountName,bookIsbn);
		if (list != null && list.size() >0)
		{
			return list;
		}
		return null;
	}
	
	//ͨ��id��ѯ��������
	@Override
	public List<ScoreInformation> searchScoreRecordById(String scoreId) throws HibernateException
	{
		String hql = " from ScoreInformation where score_id = ?";
		List<ScoreInformation> list = getHibernateTemplate().find(hql,scoreId);
		if (list != null && list.size() >0)
		{
			return list;
		}
		return list;
	}
	
	
	//��ѯĳ�˺��Ƿ����ֹ�ĳ��
	@Override
	public boolean checkScoreExisted(String scoreAccountName,String bookIsbn) throws HibernateException
	{
		String hql = " from ScoreInformation where score_account_name = ? AND book_isbn = ?";
		List<ScoreInformation> list = getHibernateTemplate().find(hql,scoreAccountName,bookIsbn);
		if (list != null && list.size() >0)
		{
			return true;
		}
		return false;
	}
	
	//��ѯĳ�˺Ŷ�ĳ�������
	@Override
	public Double searchScore(String scoreAccountName,String bookIsbn) throws HibernateException
	{
		String hql = " from ScoreInformation where score_account_name = ? AND book_isbn = ?";
		List<ScoreInformation> list = getHibernateTemplate().find(hql,scoreAccountName,bookIsbn);
		if (list != null && list.size() >0)
		{
			return list.get(0).getCommentScore();
		}
		return -1.0;
	}
}
