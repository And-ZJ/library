package com.andzj.library.bean;

public class BorrowHistoryInformation {
	private Integer historyId;
	private String bookIsbn;
	private String accountName;
	private String borrowTime;
	private String returnTime;
	private String historyNotes;
	

	public Integer getHistoryId() {
		return historyId;
	}
	public void setHistoryId(Integer historyId) {
		this.historyId = historyId;
	}
	public String getBookIsbn() {
		return bookIsbn;
	}
	public void setBookIsbn(String bookIsbn) {
		this.bookIsbn = bookIsbn;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getBorrowTime() {
		return borrowTime;
	}
	public void setBorrowTime(String borrowTime) {
		this.borrowTime = borrowTime;
	}
	public String getReturnTime() {
		return returnTime;
	}
	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}
	public String getHistoryNotes() {
		return historyNotes;
	}
	public void setHistoryNotes(String historyNotes) {
		this.historyNotes = historyNotes;
	}
	
}
