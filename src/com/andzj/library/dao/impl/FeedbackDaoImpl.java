package com.andzj.library.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.andzj.library.bean.FeedbackInformation;
import com.andzj.library.dao.FeedbackDao;

public class FeedbackDaoImpl extends HibernateDaoSupport  implements FeedbackDao{
	//添加一条反馈建议
	public boolean addFeedback(FeedbackInformation feedbackInformation) throws HibernateException
	{
		getHibernateTemplate().save("FeedbackInformation",feedbackInformation);
		return true;
	}
	
	//将一条反馈建议更改为处理中状态
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
	
	//将一条反馈建议更改为处理完成状态(之前就要检查是否是同一个管理员操作的)
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
	
	
	//查询所有反馈建议
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
	
	//通过id查询
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
	
	//查询某账号的反馈建议
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
	
	//查询某个反馈类别下的所有反馈建议
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
	
	
	
	//查询某个反馈状态下的所有反馈建议
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
	
	//查询某个管理员受理的所有反馈建议
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
