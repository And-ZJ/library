package com.andzj.library.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.andzj.library.bean.FeedbackInformation;
import com.andzj.library.dao.FeedbackDao;

public class FeedbackDaoImpl extends HibernateDaoSupport  implements FeedbackDao{
	//���һ����������
	public boolean addFeedback(FeedbackInformation feedbackInformation) throws HibernateException
	{
		getHibernateTemplate().save("FeedbackInformation",feedbackInformation);
		return true;
	}
	
	//��һ�������������Ϊ������״̬
	public boolean updateFeedbackHandling(String feedbackId,String handleAccountName)  throws HibernateException
	{
		String hql = "from FeedbackInformation where feedback_id = ?";
		List<FeedbackInformation> list = getHibernateTemplate().find(hql,feedbackId);
		if (list != null && list.size() >0)
		{
			FeedbackInformation feedbackInformation = list.get(0);
			feedbackInformation.setFeedbackState(1);
			feedbackInformation.setHandleAccountName(handleAccountName);
			getHibernateTemplate().update("FeedbackInformation",feedbackInformation);
			return true;
		}
		return false;
	}
	
	//��һ�������������Ϊ�������״̬(֮ǰ��Ҫ����Ƿ���ͬһ������Ա������)
	public boolean updateFeedbackHandled(String feedbackId,String resultMessage,String handleCompleteTime) throws HibernateException
	{
		String hql = "from FeedbackInformation where feedback_id = ?";
		List<FeedbackInformation> list = getHibernateTemplate().find(hql,feedbackId);
		if (list != null && list.size() >0)
		{
			FeedbackInformation feedbackInformation = list.get(0);
			feedbackInformation.setFeedbackState(2);
			feedbackInformation.setHandleCompleteTime(handleCompleteTime);
			getHibernateTemplate().update("FeedbackInformation",feedbackInformation);
			return true;
		}
		return false;
	}
	
	
	//��ѯ���з�������
	public List<FeedbackInformation> findAllFeedback()  throws HibernateException
	{
		String hql = "from FeedbackInformation ";
		List<FeedbackInformation> list = getHibernateTemplate().find(hql);
		if (list != null && list.size() >0)
		{
			return list;
		}
		return null;
	}
	
	//ͨ��id��ѯ
	public List<FeedbackInformation> searchFeedbackById(Integer feedbackId) throws HibernateException
	{
		String hql = "from FeedbackInformation where feedback_id = ?";
		List<FeedbackInformation> list = getHibernateTemplate().find(hql,feedbackId);
		if (list != null && list.size() >0)
		{
			return list;
		}
		return null;
	}
	
	//��ѯĳ�˺ŵķ�������
	public List<FeedbackInformation> searchFeedbackByFeedbackAccountName(String feedbackAccountName) throws HibernateException
	{
		String hql = "from FeedbackInformation where feedback_account_name = ?";
		List<FeedbackInformation> list = getHibernateTemplate().find(hql,feedbackAccountName);
		if (list != null && list.size() >0)
		{
			return list;
		}
		return null;
	}
	
	//��ѯĳ����������µ����з�������
	public List<FeedbackInformation> searchFeedbackByCategory(Integer feedbackCategory)  throws HibernateException
	{
		String hql = "from FeedbackInformation where feedback_category = ?";
		List<FeedbackInformation> list = getHibernateTemplate().find(hql,feedbackCategory);
		if (list != null && list.size() >0)
		{
			return list;
		}
		return null;
	}
	
	
	
	//��ѯĳ������״̬�µ����з�������
	public List<FeedbackInformation> searchFeedbackByState(Integer feedbackState)  throws HibernateException
	{
		String hql = "from FeedbackInformation where feedback_state = ?";
		List<FeedbackInformation> list = getHibernateTemplate().find(hql,feedbackState);
		if (list != null && list.size() >0)
		{
			return list;
		}
		return null;
	}
	
	//��ѯĳ������Ա��������з�������
	public List<FeedbackInformation> searchFeedbackByHandleAccountName(String handleAccountName)  throws HibernateException
	{
		String hql = "from FeedbackInformation where handle_account_name = ?";
		List<FeedbackInformation> list = getHibernateTemplate().find(hql,handleAccountName);
		if (list != null && list.size() >0)
		{
			return list;
		}
		return null;
	}
	
	
}
