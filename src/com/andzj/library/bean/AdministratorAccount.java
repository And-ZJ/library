package com.andzj.library.bean;

import org.apache.commons.lang3.builder.CompareToBuilder;

public class AdministratorAccount 
{
	private Integer accountId;
	private String accountName;
	private String passwordMD5;
	private String registerTime;
	private String administratorNickname;
	
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
	public String getAdministratorNickname() {
		return administratorNickname;
	}
	public void setAdministratorNickname(String administratorNickname) {
		this.administratorNickname = administratorNickname;
	}
	
}
