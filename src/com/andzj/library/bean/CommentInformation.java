package com.andzj.library.bean;

public class CommentInformation {
	private Integer commentId;
	private String bookIsbn;
	private String commentAccountName;
	private String commentContent;
	private String commentTime;
	public Integer getCommentId() {
		return commentId;
	}
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
	public String getBookIsbn() {
		return bookIsbn;
	}
	public void setBookIsbn(String bookIsbn) {
		this.bookIsbn = bookIsbn;
	}
	public String getCommentAccountName() {
		return commentAccountName;
	}
	public void setCommentAccountName(String commentAccountName) {
		this.commentAccountName = commentAccountName;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public String getCommentTime() {
		return commentTime;
	}
	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
	}
	
}
