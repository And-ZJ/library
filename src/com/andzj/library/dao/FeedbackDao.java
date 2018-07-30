package com.andzj.library.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.andzj.library.bean.FeedbackInformation;

public interface FeedbackDao {
	/************************反馈建议***********************/
	//添加一条反馈建议
	public boolean addFeedback(FeedbackInformation feedbackInformation) throws HibernateException;
	
	//将一条反馈建议更改为处理中状态
	public boolean updateFeedbackHandling(String feedbackId,String handleAccountName)  throws HibernateException;
	
	//将一条反馈建议更改为处理完成状态(之前就要检查是否是同一个管理员操作的)
	public boolean updateFeedbackHandled(String feedbackId,String resultMessage,String handleCompleteTime) throws HibernateException;
	
	//查询所有反馈建议
	public List<FeedbackInformation> findAllFeedback()  throws HibernateException;
	
	//通过id查询
	public List<FeedbackInformation> searchFeedbackById(Integer feedbackId) throws HibernateException;
	
	//查询某账号的反馈建议
	public List<FeedbackInformation> searchFeedbackByFeedbackAccountName(String feedbackAccountName) throws HibernateException;
	
	//查询某个反馈类别下的所有反馈建议
	public List<FeedbackInformation> searchFeedbackByCategory(Integer feedbackCategory)  throws HibernateException;
	
	//查询某个反馈状态下的所有反馈建议
	public List<FeedbackInformation> searchFeedbackByState(Integer feedbackState)  throws HibernateException;
	
	//查询某个管理员受理的所有反馈建议
	public List<FeedbackInformation> searchFeedbackByHandleAccountName(String handleAccountName)  throws HibernateException;
	
	

}
