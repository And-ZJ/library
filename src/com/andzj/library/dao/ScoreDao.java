package com.andzj.library.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.andzj.library.bean.ScoreInformation;

public interface ScoreDao {
	/****************************操作书籍评分*********************************/
	//增加评分
	public boolean addScore(ScoreInformation scoreInformation) throws HibernateException;
	
	//更改评分
	public boolean updateScore(ScoreInformation scoreInformation) throws HibernateException;
	
	//查询所有评分
	public List<ScoreInformation> findAllScoreRecord() throws HibernateException;
	
	//查询某书的所有评分条数
	public List<ScoreInformation> searchScoreRecordByBookIsbn(String bookIsbn) throws HibernateException;
	
	//查询某账号的所有评分条数
	public List<ScoreInformation> searchScoreRecordByAccountName(String scoreAccountName) throws HibernateException;
	
	//查询某账号对某书的评分详情
	public List<ScoreInformation> searchScoreRecord(String scoreAccountName,String bookIsbn) throws HibernateException;
	
	//通过id查询评分详情
	public List<ScoreInformation> searchScoreRecordById(String scoreId) throws HibernateException;
	
	//查询某账号是否评分过某书
	public boolean checkScoreExisted(String scoreAccountName,String bookIsbn) throws HibernateException;
	
	//查询某账号对某书的评分
	public Double searchScore(String scoreAccountName,String bookIsbn) throws HibernateException;
}
 