package com.andzj.library.dao;

import java.util.List;

import org.hibernate.HibernateException;
import com.andzj.library.bean.UserAccount;

public interface UserDao {
	/*******************************������ͨ�û�*************************************/
	//��ͨ�û�ע��
	public boolean addUserAccount(UserAccount account) throws HibernateException;
	
	//��ͨ�û���¼
	public UserAccount userLogin(String accountName) throws HibernateException;
	
	//��ͨ�û��Զ���¼��֤
	@Deprecated
	public UserAccount userAutoLogin(String accountName,String passwordMD5) throws HibernateException;
	
	//ɾ����ͨ�û�
	public UserAccount deleteUserAccount(String accountName) throws HibernateException;
	
	//�޸���ͨ�û���Ϣ
	public boolean updateUserAccount(UserAccount account) throws HibernateException;

	//��ͨ�û��޸���ͨ��Ϣ
	public UserAccount updateUserAccount(String accountName,String newBindStudentAccount,String newNickname,String newSex,String newDescribeWords,String updateTimeStr) throws HibernateException;
	
	//��ͨ�û��޸�����
	public boolean updateUserAccount(String accountName,String newPasswordMD5,String updateTime) throws HibernateException;

	//����ͼƬ��ַ
	public UserAccount updateUserImage(String accountName,String imageAddress,String updateTime) throws HibernateException;
	
	//��ѯ������ͨ�û�
	public List<UserAccount> findAllUserAccount() throws HibernateException;
	
	//��ѯָ����ͨ�û�����Ϣ
	public List<UserAccount> searchUserAccount(String accountName) throws HibernateException;
	
	//��ѯָ����ͨ�û��ʺ��Ƿ����
	public boolean checkUserAccountExisted(String accountName) throws HibernateException;

	//���ĳ�������ȷ��
	public boolean checkUserPassword(String accountName,String passwordMD5) throws HibernateException;
}
