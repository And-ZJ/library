package com.andzj.library.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.andzj.library.bean.ScoreInformation;

public interface ScoreDao {
	/****************************�����鼮����*********************************/
	//��������
	public boolean addScore(ScoreInformation scoreInformation) throws HibernateException;
	
	//��������
	public boolean updateScore(ScoreInformation scoreInformation) throws HibernateException;
	
	//��ѯ��������
	public List<ScoreInformation> findAllScoreRecord() throws HibernateException;
	
	//��ѯĳ���������������
	public List<ScoreInformation> searchScoreRecordByBookIsbn(String bookIsbn) throws HibernateException;
	
	//��ѯĳ�˺ŵ�������������
	public List<ScoreInformation> searchScoreRecordByAccountName(String scoreAccountName) throws HibernateException;
	
	//��ѯĳ�˺Ŷ�ĳ�����������
	public List<ScoreInformation> searchScoreRecord(String scoreAccountName,String bookIsbn) throws HibernateException;
	
	//ͨ��id��ѯ��������
	public List<ScoreInformation> searchScoreRecordById(String scoreId) throws HibernateException;
	
	//��ѯĳ�˺��Ƿ����ֹ�ĳ��
	public boolean checkScoreExisted(String scoreAccountName,String bookIsbn) throws HibernateException;
	
	//��ѯĳ�˺Ŷ�ĳ�������
	public Double searchScore(String scoreAccountName,String bookIsbn) throws HibernateException;
}
 