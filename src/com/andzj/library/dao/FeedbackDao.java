package com.andzj.library.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.andzj.library.bean.FeedbackInformation;

public interface FeedbackDao {
	/************************��������***********************/
	//���һ����������
	public boolean addFeedback(FeedbackInformation feedbackInformation) throws HibernateException;
	
	//��һ�������������Ϊ������״̬
	public boolean updateFeedbackHandling(String feedbackId,String handleAccountName)  throws HibernateException;
	
	//��һ�������������Ϊ�������״̬(֮ǰ��Ҫ����Ƿ���ͬһ������Ա������)
	public boolean updateFeedbackHandled(String feedbackId,String resultMessage,String handleCompleteTime) throws HibernateException;
	
	//��ѯ���з�������
	public List<FeedbackInformation> findAllFeedback()  throws HibernateException;
	
	//ͨ��id��ѯ
	public List<FeedbackInformation> searchFeedbackById(Integer feedbackId) throws HibernateException;
	
	//��ѯĳ�˺ŵķ�������
	public List<FeedbackInformation> searchFeedbackByFeedbackAccountName(String feedbackAccountName) throws HibernateException;
	
	//��ѯĳ����������µ����з�������
	public List<FeedbackInformation> searchFeedbackByCategory(Integer feedbackCategory)  throws HibernateException;
	
	//��ѯĳ������״̬�µ����з�������
	public List<FeedbackInformation> searchFeedbackByState(Integer feedbackState)  throws HibernateException;
	
	//��ѯĳ������Ա��������з�������
	public List<FeedbackInformation> searchFeedbackByHandleAccountName(String handleAccountName)  throws HibernateException;
	
	

}
