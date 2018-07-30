package com.andzj.library.service;

import java.io.File;
import java.io.IOException;

import org.hibernate.HibernateException;

import com.andzj.library.bean.AdministratorAccount;
import com.andzj.library.bean.BookInformation;
import com.andzj.library.bean.BorrowHistoryInformation;
import com.andzj.library.bean.BorrowInformation;
import com.andzj.library.bean.CommentInformation;
import com.andzj.library.bean.FeedbackInformation;
import com.andzj.library.bean.ScoreInformation;
import com.andzj.library.bean.UserAccount;


/**
 * ����Ա���ʵ�ҵ���ӿ�
 * @author zj
 *
 */
public interface AdministratorService {
	/***************************�ļ��洢*******************************/
	//�洢�ļ�
	public boolean saveFile(String fileRelativePathStr,String fileNameStr,File file)  throws IOException;
	//ɾ���ļ�
	public boolean deleteFile(String fileRelativePathStr,String fileNameStr) throws IOException;
	
	/***************************��������Ա******************************/
	//��ӹ���Ա�˻�
	public boolean addAdministratorAccount(AdministratorAccount account)  throws HibernateException;
	
	//����Ա�ʺŵ�¼
	public boolean administratorLogin(AdministratorAccount account)  throws HibernateException;
	
	//��ѯ���й���Ա�ʺ�
	public boolean findAllAdminAccount()  throws HibernateException;
	
	//��ѯ����Ա�ʺ�
	public boolean searchAdminAccount(String accountName) throws HibernateException;
	
	//��ѯָ������Ա�ʺ��Ƿ����
	public boolean checkAdminAccountExisted(String accountName) throws HibernateException;
	
	//ɾ������Ա�ʺ�
	public boolean deleteAdministratorAccount(String operateAccountName,String accountName) throws HibernateException;
	
	//���Ĺ���Ա�ʺŵ��ǳƺ�����
	public boolean updateAdministratorAccount(String accountName,String newNickname,String newPasswordMD5) throws HibernateException;
	
	

	/*******************************����ͼ��**************************/
	
	//���ͼ����Ϣ
	public boolean addBook(BookInformation book)  throws HibernateException;
	public boolean addBook(BookInformation book,String bookImageRelativePath,String bookImageName,File file) throws HibernateException;
	
	//ɾ��ͼ�� 
	public boolean deleteBook(String bookIsbn) throws HibernateException;
	
	//����ͼ����Ϣ
	public boolean updateBook(BookInformation book) throws HibernateException;
	public boolean updateBook(BookInformation book,String bookImageRelativePath,String bookImageName,File file) throws HibernateException;
	
	//��ѯ����ͼ��
	public boolean findAllBook() throws HibernateException;
	
	//��ѯisbn�Ƿ��ظ�
	public boolean checkBookIsbnExisted(String bookIsbn) throws HibernateException;
	
	//��ISBN��ѯͼ��
	public boolean searchBookByIsbn(String bookIsbn) throws HibernateException;
	
	//��������ѯͼ��
	public boolean searchBookByName(String bookName) throws HibernateException;
	
	//�����߲�ѯͼ��
	public boolean searchBookByAuthor(String bookAuthor) throws HibernateException;
	
	//�Գ������ѯͼ��
	public boolean searchBookByPublishCompany(String bookPublishCompany) throws HibernateException;
	
	//�Թؼ��ֲ�ѯͼ��
	public boolean searchBookByKeyWords(String bookKeyWords) throws HibernateException;
	
	//����������в�ѯ
	public boolean searchBook(String searchWords) throws HibernateException;
	
	//��ѯ����ǰ����鼮
	public boolean searchHotBook() throws HibernateException;
	
	/********************************����ͼ�����********************************************/
	//����һ�����ļ�¼
	public boolean addBorrow(BorrowInformation borrow) throws HibernateException;
	
	//ɾ��һ�����ļ�¼
	public boolean deleteBorrow(String borrowId) throws HibernateException;
	
	//�޸�һ�����ļ�¼
	public boolean updateBorrow(BorrowInformation borrow) throws HibernateException;
	
	//��ѯ���м�¼
	public boolean findAllBorrow() throws HibernateException;
	
	//��ѯĳ��Ľ��ļ�¼
	public boolean searchBorrowByBookIsbn(String bookIsbn) throws HibernateException;
	
	//��ѯĳ�˺��Ƿ������ĳ��
	public boolean checkBorrowExisted(String borrowAccountName,String bookIsbn) throws HibernateException;

	//��ѯĳ�˺ŵĽ��ļ�¼
	public boolean searchBorrowByAccountName(String accountName) throws HibernateException;
	
	//ͨ�����ݿ�id��ѯ���ļ�¼
	public boolean searchBorrowById(String borrowId) throws HibernateException;
	
	
	/******************************����������ʷ��¼*********************************/
	//����һ��������ʷ
	public boolean addBorrowHistory(BorrowHistoryInformation borrowHistoryInformation) throws HibernateException;
	
	//Ϊĳ��������ʷ�����ӱ�ע
	public boolean addBorrowHistoryNotes(String historyId,String historyNotes) throws HibernateException;
	
	//�޸�ĳ��������ʷ�ı�ע
	public boolean updateBorrowHistoryNotes(String historyId,String newHistoryNotes) throws HibernateException;
	
	//��ѯ���н�����ʷ��¼
	public boolean findAllBorrowHistory() throws HibernateException;
		
	//��ѯĳ��Ľ�����ʷ��¼
	public boolean searchBorrowHistoryByBookIsbn(String bookIsbn) throws HibernateException;
		
	//��ѯĳ�˺��Ƿ������ĳ��
	public boolean checkBorrowHistoryExisted(String accountName,String bookIsbn) throws HibernateException;
		
	//��ѯĳ�˺ŵĽ�����ʷ��¼
	public boolean searchBorrowHistoryByAccountName(String accountName) throws HibernateException;
 
	//ͨ�����ݿ�id��ѯ������ʷ��¼
	public boolean searchBorrowHistoryById(String historyId) throws HibernateException;
		
	//��ѯĳ��Ľ�����ʷ��¼����
	public boolean findBookBorrowHistoryNumber(String bookIsbn) throws HibernateException;
	
	
	/********************************����ͼ������*****************************************/
	//����һ��ͼ������
	public boolean addComment(CommentInformation comment) throws HibernateException;
	
	//ɾ��һ��ͼ������
	public boolean deleteComment(String commentId) throws HibernateException;
	
	//����һ������
	public boolean updateComment(CommentInformation commentInformation) throws HibernateException;
	
	//��ѯ��������
	public boolean findAllComment() throws HibernateException;

	//��ѯĳ������� 
	public boolean searchCommentByBookIsbn(String bookIsbn) throws HibernateException;
	
	//��ѯĳ�ʺŵ�����
	public boolean searchCommentByAccountName(String accountName) throws HibernateException;
	
	//��ѯĳ���Ƿ����۹�ĳ��
	public boolean checkCommentExisted(String commentAccountName,String bookIsbn) throws HibernateException;

	
	/******************************����ͼ������**************************************/
	//��������
	public boolean addScore(ScoreInformation scoreInformation) throws HibernateException;
		
	//�������ۺ�����
	public boolean addCommentWithScore(CommentInformation commentInformation,ScoreInformation scoreInformation) throws HibernateException;
	
	//��������
	public boolean updateScore(ScoreInformation scoreInformation) throws HibernateException;
		
	//��ѯ��������
	public boolean findAllScoreRecord() throws HibernateException;
		
	//��ѯĳ���������������
	public boolean searchScoreRecordByBookIsbn(String bookIsbn) throws HibernateException;
		
	//��ѯĳ�˺ŵ�������������
	public boolean searchScoreRecordByAccountName(String scoreAccountName) throws HibernateException;
		
	//��ѯĳ�˺Ŷ�ĳ�����������
	public boolean searchScoreRecord(String scoreAccountName,String bookIsbn) throws HibernateException;
		
	//ͨ��id��ѯ��������
	public boolean searchScoreRecordById(String scoreId) throws HibernateException;
		
	//��ѯĳ�˺��Ƿ����ֹ�ĳ��
	public boolean checkScoreExisted(String scoreAccountName,String bookIsbn) throws HibernateException;
		
	//��ѯĳ�˺Ŷ�ĳ�������
	public boolean searchScore(String scoreAccountName,String bookIsbn) throws HibernateException;
	
	
	
	/*******************************������ͨ�û�*************************************/
	//��ͨ�û�ע��
	public boolean addUserAccount(UserAccount account) throws HibernateException;
	
	//��ͨ�û���¼
	public boolean userLogin(UserAccount account) throws HibernateException;
	
	//��ͨ�û��Զ���¼��֤
	public boolean userAutoLogin(String accountName,String passwordMD5) throws HibernateException;

	
	//ɾ����ͨ�û�
	public boolean deleteUserAccount(String accountName) throws HibernateException;
	
	//�޸���ͨ�û���Ϣ
	public boolean updateUserAccount(UserAccount account) throws HibernateException;

	//��ͨ�û��޸���ͨ��Ϣ
	public boolean updateUserAccount(String accountName,String newBindStudentAccount,String newNickname,String newSex,String newDescribeWords,String updateTime) throws HibernateException;
	
	//��ͨ�û��޸�����
	public boolean updateUserAccount(String accountName,String newPasswordMD5,String updateTime) throws HibernateException;

	//����ͼƬ��ַ
	public boolean updateUserImage(String accountName,String userImageRelativePath,String userImageName,File imageFile,String updateTime) throws HibernateException;
	
	//��ѯ������ͨ�û�
	public boolean findAllUserAccount() throws HibernateException;
	
	//��ѯָ����ͨ�û�����Ϣ
	public boolean searchUserAccount(String accountName) throws HibernateException;
	
	
	//��ѯָ����ͨ�û��ʺ��Ƿ����
	public boolean checkUserAccountExisted(String accountName) throws HibernateException;

	//���ĳ�������ȷ��
	public boolean checkUserPassword(String accountName,String passwordMD5) throws HibernateException;

	
	
	/************************��������***********************/
	//���һ����������
	public boolean addFeedback(FeedbackInformation feedbackInformation) throws HibernateException;
	
	//��һ�������������Ϊ������״̬
	public boolean updateFeedbackHandling(String feedbackId,String handleAccountName)  throws HibernateException;
	
	//��һ�������������Ϊ�������״̬(֮ǰ��Ҫ����Ƿ���ͬһ������Ա������)
	public boolean updateFeedbackHandled(String feedbackId,String resultMessage,String handleCompleteTime) throws HibernateException;
	
	//��ѯ���з�������
	public boolean findAllFeedback()  throws HibernateException;
	
	//ͨ��id��ѯ
	public boolean searchFeedbackById(Integer feedbackId) throws HibernateException;
	
	//��ѯĳ�˺ŵķ�������
	public boolean searchFeedbackByFeedbackAccountName(String feedbackAccountName) throws HibernateException;
	
	//��ѯĳ����������µ����з�������
	public boolean searchFeedbackByCategory(Integer feedbackCategory)  throws HibernateException;
	
	//��ѯĳ������״̬�µ����з�������
	public boolean searchFeedbackByState(Integer feedbackState)  throws HibernateException;
	
	//��ѯĳ������Ա��������з�������
	public boolean searchFeedbackByHandleAccountName(String handleAccountName)  throws HibernateException;
	
	
}
