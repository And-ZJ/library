package com.andzj.library.bean;

public class BorrowInformation {
	private Integer borrowId;
	private String bookIsbn;
	private String borrowAccountName;
	private String borrowTime;
	private String returnTime;
	private String borrowState;
	public Integer getBorrowId() {
		return borrowId;
	}
	public void setBorrowId(Integer borrowId) {
		this.borrowId = borrowId;
	}
	public String getBookIsbn() {
		return bookIsbn;
	}
	public void setBookIsbn(String bookIsbn) {
		this.bookIsbn = bookIsbn;
	}
	public String getBorrowAccountName() {
		return borrowAccountName;
	}
	public void setBorrowAccountName(String borrowAccountName) {
		this.borrowAccountName = borrowAccountName;
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
	public String getBorrowState() {
		return borrowState;
	}
	public void setBorrowState(String borrowState) {
		this.borrowState = borrowState;
	}
	
	
	
}
