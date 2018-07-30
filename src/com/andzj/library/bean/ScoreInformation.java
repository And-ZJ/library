package com.andzj.library.bean;

public class ScoreInformation {
	private Integer scoreId;
	private String bookIsbn;
	private String scoreAccountName;
	private Double commentScore;
	private String scoreTime;
	public Integer getScoreId() {
		return scoreId;
	}
	public void setScoreId(Integer scoreId) {
		this.scoreId = scoreId;
	}
	public String getBookIsbn() {
		return bookIsbn;
	}
	public void setBookIsbn(String bookIsbn) {
		this.bookIsbn = bookIsbn;
	}
	public String getScoreAccountName() {
		return scoreAccountName;
	}
	public void setScoreAccountName(String scoreAccountName) {
		this.scoreAccountName = scoreAccountName;
	}
	public Double getCommentScore() {
		return commentScore;
	}
	public void setCommentScore(Double commentScore) {
		this.commentScore = commentScore;
	}
	public String getScoreTime() {
		return scoreTime;
	}
	public void setScoreTime(String scoreTime) {
		this.scoreTime = scoreTime;
	}

}
