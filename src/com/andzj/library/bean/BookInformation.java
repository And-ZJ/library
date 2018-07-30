package com.andzj.library.bean;


public class BookInformation 
{
	private Integer bookId;
	private String bookIsbn;
	private String bookImageAddress;
	private String bookName;
	private String bookAuthor;
	private Double bookPrice;
	private String bookPublishCompany;
	private String bookPublishTime;
	private String bookSummary;
	
	private Integer bookTotalNumber;
	private Integer bookRemainNumber;
	private Double bookAverageScore;
	private Integer bookScoreNumber;
	private String bookPosition;
	private String bookKeyWords;
	private String bookNotes;
	private String operateAccountName;
	private String operateTime;
	
	public Integer getBookId() {
		return bookId;
	}
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
	public String getBookIsbn() {
		return bookIsbn;
	}
	public void setBookIsbn(String bookIsbn) {
		this.bookIsbn = bookIsbn;
	}
	public String getBookImageAddress() {
		return bookImageAddress;
	}
	public void setBookImageAddress(String bookImageAddress) {
		this.bookImageAddress = bookImageAddress;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getBookAuthor() {
		return bookAuthor;
	}
	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}
	public Double getBookPrice() {
		return bookPrice;
	}
	public void setBookPrice(Double bookPrice) {
		this.bookPrice = bookPrice;
	}
	public String getBookPublishCompany() {
		return bookPublishCompany;
	}
	public void setBookPublishCompany(String bookPublishCompany) {
		this.bookPublishCompany = bookPublishCompany;
	}
	public String getBookPublishTime() {
		return bookPublishTime;
	}
	public void setBookPublishTime(String bookPublishTime) {
		this.bookPublishTime = bookPublishTime;
	}
	public String getBookSummary() {
		return bookSummary;
	}
	public void setBookSummary(String bookSummary) {
		this.bookSummary = bookSummary;
	}
	
	
	public Integer getBookTotalNumber() {
		return bookTotalNumber;
	}
	public void setBookTotalNumber(Integer bookTotalNumber) {
		this.bookTotalNumber = bookTotalNumber;
	}
	public Integer getBookRemainNumber() {
		return bookRemainNumber;
	}
	public void setBookRemainNumber(Integer bookRemainNumber) {
		this.bookRemainNumber = bookRemainNumber;
	}
	public Double getBookAverageScore() {
		return bookAverageScore;
	}
	public void setBookAverageScore(Double bookAverageScore) {
		this.bookAverageScore = bookAverageScore;
	}
	public Integer getBookScoreNumber() {
		return bookScoreNumber;
	}
	public void setBookScoreNumber(Integer bookScoreNumber) {
		this.bookScoreNumber = bookScoreNumber;
	}
	public String getBookPosition() {
		return bookPosition;
	}
	public void setBookPosition(String bookPosition) {
		this.bookPosition = bookPosition;
	}
	public String getBookKeyWords() {
		return bookKeyWords;
	}
	public void setBookKeyWords(String bookKeyWords) {
		this.bookKeyWords = bookKeyWords;
	}
	public String getBookNotes() {
		return bookNotes;
	}
	public void setBookNotes(String bookNotes) {
		this.bookNotes = bookNotes;
	}
	public String getOperateAccountName() {
		return operateAccountName;
	}
	public void setOperateAccountName(String operateAccountName) {
		this.operateAccountName = operateAccountName;
	}
	public String getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
	
	@Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("id:").append(bookId).append("  ");
        builder.append("书名:<").append(bookName).append(">  ");
        builder.append("ISBN:").append(bookIsbn).append("  ");
        builder.append("作者:").append(bookAuthor).append("  ");
        builder.append("价格:").append(bookPrice).append("  ");
        builder.append("出版社:").append(bookPublishCompany).append("  ");
        builder.append("出版时间:").append(bookPublishTime).append("  ");
        builder.append("简介:").append(bookSummary).append("  ");
        builder.append("总数量:").append(bookTotalNumber).append("  ");
        builder.append("不可借数量:").append(bookRemainNumber).append("  ");
        builder.append("评分:").append(bookAverageScore).append("  ");
        builder.append("评分人数:").append(bookScoreNumber).append("  ");
        builder.append("存放位置:").append(bookPosition).append("  ");
        builder.append("关键字:").append(bookKeyWords).append("  ");
        builder.append("备注:").append(bookNotes).append("  ");
        builder.append("管理员:").append(operateAccountName).append("  ");
        builder.append("录入时间:").append(operateTime).append("  ");
		builder.append("照片地址:").append(bookImageAddress).append("\n");

        return builder.toString();
    }
	
}
