package com.andzj.library.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.andzj.library.bean.AdministratorAccount;

/**
 * ����Ա���ʵ�DAO�ӿ�
 * @author zj
 *
 */
public interface AdministratorDao {
	/***************************��������Ա******************************/
	//��ӹ���Ա�˻�
	public boolean addAdministratorAccount(AdministratorAccount account)  throws HibernateException;
	
	//����Ա�ʺŵ�¼
	public AdministratorAccount administratorLogin(AdministratorAccount account)  throws HibernateException;
	
	//��ѯ���й���Ա�ʺ�
	public List<AdministratorAccount> findAllAdminAccount()  throws HibernateException;
	
	//��ѯָ������Ա�ʺ���Ϣ
	public List<AdministratorAccount> searchAdminAccount(String accountName) throws HibernateException;
	
	//��ѯָ������Ա�ʺ��Ƿ����
	public boolean checkAdminiAccountExisted(String accountName) throws HibernateException;
	
	//ɾ������Ա�ʺ�
	public AdministratorAccount deleteAdministratorAccount(String accountName) throws HibernateException;
	
	//���Ĺ���Ա�ʺŵ��ǳƺ�����
	public boolean updateAdministratorAccount(String accountName,String newNickname,String newPasswordMD5) throws HibernateException;
	
	

		
	
	
	
	
	
}
