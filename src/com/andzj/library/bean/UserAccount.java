package com.andzj.library.bean;

public class UserAccount 
{
	private Integer accountId;
	private String accountName;
	private String passwordMD5;
	private String registerTime;
	private String bindStudentAccount;

	private String userImageStr;
	private String userNickname;
	private String userSex;
	private String userDescribeWords;
	private String userUpdateTime;
	public Integer getAccountId() {
        return accountId;
    }
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getPasswordMD5() {
		return passwordMD5;
	}
	public void setPasswordMD5(String passwordMD5) {
		this.passwordMD5 = passwordMD5;
	}
	public String getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}
	public String getBindStudentAccount() {
		return bindStudentAccount;
	}
	public void setBindStudentAccount(String bindStudentAccount) {
		this.bindStudentAccount = bindStudentAccount;
	}
	public String getUserImageStr() {
		return userImageStr;
	}
	public void setUserImageStr(String userImageStr) {
		this.userImageStr = userImageStr;
	}
	public String getUserNickname() {
		return userNickname;
	}
	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}
	public String getUserSex() {
		return userSex;
	}
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
	public String getUserDescribeWords() {
		return userDescribeWords;
	}
	public void setUserDescribeWords(String userDescribeWords) {
		this.userDescribeWords = userDescribeWords;
	}
	public String getUserUpdateTime() {
		return userUpdateTime;
	}
	public void setUserUpdateTime(String userUpdateTime) {
		this.userUpdateTime = userUpdateTime;
	}

	
}
