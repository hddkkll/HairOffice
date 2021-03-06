package kr.or.bit.service;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.QnADao;
import kr.or.bit.dto.QnADto;

public class QnAupdateservice implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = null;
		
		
		String uploadpath = request.getSession().getServletContext().getRealPath("upload");
		System.out.println("uploadpath  " + uploadpath);
		
		
		int size = 1024*1024*50;	//10M 네이버 계산기
		int result;
		
		MultipartRequest multi;
		try {
			QnADao dao = new QnADao();
			multi = new MultipartRequest(request,uploadpath, size, "UTF-8", new DefaultFileRenamePolicy());
			Enumeration filenames = multi.getFileNames();
			
			
			
			int boardID = Integer.parseInt(multi.getParameter("boardid"));
			System.out.println("boardID 확인 : " + boardID);
			String userid = multi.getParameter("userid");
			String boardSubject = multi.getParameter("boardsubject"); 
			String boardContent = multi.getParameter("boardcontent");
			String fileName = multi.getParameter("filename");
			
			
			System.out.println("업데이트service 받아온 값을 봅시다.");
			//System.out.println("boardid : " + boardID);
			System.out.println("subject : " + boardSubject);
			System.out.println("content : " + boardContent);
			System.out.println("filename : " + fileName);
			
			
			QnADto qna = new QnADto();
			
			qna.setBoardID(boardID);
			qna.setUserID(userid);
			qna.setBoardSubject(boardSubject);
			qna.setBoardContent(boardContent);
			qna.setFileName(fileName);
			
			System.out.println(qna.toString());
			
			String file = (String)filenames.nextElement();
			String filename = multi.getFilesystemName(file);
			qna.setFileName(filename);
						
			result = dao.updateQnA(qna);

			if (result > 0) {
				System.out.println("등록성공");
			} else { // -1 (제약, 컬럼길이 문제)
				System.out.println("등록실패");
			}
			

			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("/QnA.do"); //리스트
			
			} catch (Exception e) {
			e.printStackTrace();
			}

//		
//		System.out.println("업데이트 서비스 시작");
//		System.out.println("boardid확인 : " + multi.getParameter("boardid"));
//	//	int boardID = Integer.parseInt(request.getParameter("boardid"));
//		String boardName = request.getParameter("boardname");
//		String boardSubject = request.getParameter("boardsubject"); 
//		String boardContent = request.getParameter("boardcontent");
//		String fileName = request.getParameter("filename");
//		
//		int result = 0;
//		System.out.println("업데이트service 받아온 값을 봅시다.");
//		//System.out.println("boardid : " + boardID);
//		System.out.println("name : " + boardName);
//		System.out.println("subject : " + boardSubject);
//		System.out.println("content : " + boardContent);
//		System.out.println("filename : " + fileName);
//		
//		
//		try {
//			request.setCharacterEncoding("UTF-8");
//			QnADto qna = new QnADto();
//			
//			//qna.setBoardID(boardID);
//			qna.setBoardName(boardName);
//			qna.setBoardSubject(boardSubject);
//			qna.setBoardContent(boardContent);
//			qna.setFileName(fileName);
//			
//			System.out.println(qna.toString());
//			
//			QnADao dao = new QnADao();
//			result = dao.updateQnA(qna);
//
//			if (result > 0) {
//				System.out.println("등록성공");
//			} else { // -1 (제약, 컬럼길이 문제)
//				System.out.println("등록실패");
//			}
//			
//
//			forward = new ActionForward();
//			forward.setRedirect(false);
//			forward.setPath("/QnA.do"); //리스트
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		return forward;
	}

}
