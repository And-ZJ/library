package com.andzj.library.dao;

import java.util.List;

import org.hibernate.HibernateException;
import com.andzj.library.bean.UserAccount;

public interface UserDao {
	/*******************************操作普通用户*************************************/
	//普通用户注册
	public boolean addUserAccount(UserAccount account) throws HibernateException;
	
	//普通用户登录
	public UserAccount userLogin(String accountName) throws HibernateException;
	
	//普通用户自动登录验证
	@Deprecated
	public UserAccount userAutoLogin(String accountName,String passwordMD5) throws HibernateException;
	
	//删除普通用户
	public UserAccount deleteUserAccount(String accountName) throws HibernateException;
	
	//修改普通用户信息
	public boolean updateUserAccount(UserAccount account) throws HibernateException;

	//普通用户修改普通信息
	public UserAccount updateUserAccount(String accountName,String newBindStudentAccount,String newNickname,String newSex,String newDescribeWords,String updateTimeStr) throws HibernateException;
	
	//普通用户修改密码
	public boolean updateUserAccount(String accountName,String newPasswordMD5,String updateTime) throws HibernateException;

	//更改图片地址
	public UserAccount updateUserImage(String accountName,String imageAddress,String updateTime) throws HibernateException;
	
	//查询所有普通用户
	public List<UserAccount> findAllUserAccount() throws HibernateException;
	
	//查询指定普通用户的信息
	public List<UserAccount> searchUserAccount(String accountName) throws HibernateException;
	
	//查询指定普通用户帐号是否存在
	public boolean checkUserAccountExisted(String accountName) throws HibernateException;

	//检查某密码的正确性
	public boolean checkUserPassword(String accountName,String passwordMD5) throws HibernateException;
}
