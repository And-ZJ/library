package com.andzj.library.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.andzj.library.bean.ScoreInformation;
import com.andzj.library.dao.ScoreDao;

public class ScoreDaoImpl extends HibernateDaoSupport implements ScoreDao{
	/****************************操作书籍评分*********************************/
	//增加评分
	@Override
	public boolean addScore(ScoreInformation scoreInformation) throws HibernateException
	{
		getHibernateTemplate().save("ScoreInformation",scoreInformation);
		return true;
	}
	
	//更改评分
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
	
	//查询所有评分
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
	
	//查询某书的所有评分条数
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
	
	//查询某账号的所有评分条数
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
	
	//查询某账号对某书的评分详情
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
	
	//通过id查询评分详情
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
	
	
	//查询某账号是否评分过某书
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
	
	//查询某账号对某书的评分
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
