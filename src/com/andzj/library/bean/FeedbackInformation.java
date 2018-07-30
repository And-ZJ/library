package com.andzj.library.bean;

public class FeedbackInformation {
	private Integer feedbackId;
	private String feedbackAccountName;
	private String feedbackContent;
	private String feedbackTime;
	private Integer feedbackCategory;
	private Integer feedbackState;
	private String handleAccountName;
	private String handleCompleteTime;
	private String resultMessage;
	public Integer getFeedbackId() {
		return feedbackId;
	}
	public void setFeedbackId(Integer feedbackId) {
		this.feedbackId = feedbackId;
	}
	public String getFeedbackAccountName() {
		return feedbackAccountName;
	}
	public void setFeedbackAccountName(String feedbackAccountName) {
		this.feedbackAccountName = feedbackAccountName;
	}
	public String getFeedbackContent() {
		return feedbackContent;
	}
	public void setFeedbackContent(String feedbackContent) {
		this.feedbackContent = feedbackContent;
	}
	public String getFeedbackTime() {
		return feedbackTime;
	}
	public void setFeedbackTime(String feedbackTime) {
		this.feedbackTime = feedbackTime;
	}
	public Integer getFeedbackCategory() {
		return feedbackCategory;
	}
	public void setFeedbackCategory(Integer feedbackCategory) {
		this.feedbackCategory = feedbackCategory;
	}
	public Integer getFeedbackState() {
		return feedbackState;
	}
	public void setFeedbackState(Integer feedbackState) {
		this.feedbackState = feedbackState;
	}
	public String getHandleAccountName() {
		return handleAccountName;
	}
	public void setHandleAccountName(String handleAccountName) {
		this.handleAccountName = handleAccountName;
	}
	public String getHandleCompleteTime() {
		return handleCompleteTime;
	}
	public void setHandleCompleteTime(String handleCompleteTime) {
		this.handleCompleteTime = handleCompleteTime;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	
}
