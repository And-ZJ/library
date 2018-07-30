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
        builder.append("����:<").append(bookName).append(">  ");
        builder.append("ISBN:").append(bookIsbn).append("  ");
        builder.append("����:").append(bookAuthor).append("  ");
        builder.append("�۸�:").append(bookPrice).append("  ");
        builder.append("������:").append(bookPublishCompany).append("  ");
        builder.append("����ʱ��:").append(bookPublishTime).append("  ");
        builder.append("���:").append(bookSummary).append("  ");
        builder.append("������:").append(bookTotalNumber).append("  ");
        builder.append("���ɽ�����:").append(bookRemainNumber).append("  ");
        builder.append("����:").append(bookAverageScore).append("  ");
        builder.append("��������:").append(bookScoreNumber).append("  ");
        builder.append("���λ��:").append(bookPosition).append("  ");
        builder.append("�ؼ���:").append(bookKeyWords).append("  ");
        builder.append("��ע:").append(bookNotes).append("  ");
        builder.append("����Ա:").append(operateAccountName).append("  ");
        builder.append("¼��ʱ��:").append(operateTime).append("  ");
		builder.append("��Ƭ��ַ:").append(bookImageAddress).append("\n");

        return builder.toString();
    }
	
}
