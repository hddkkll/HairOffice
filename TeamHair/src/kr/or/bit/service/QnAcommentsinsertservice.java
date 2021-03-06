package kr.or.bit.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.QnADao;
import kr.or.bit.dto.QnACommentsDto;

public class QnAcommentsinsertservice implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = null;
		
		System.out.println("넘어온 boardid 확인하기 : " + request.getParameter("boardid"));
		System.out.println("댓글값 : " + request.getParameter("comments"));
		System.out.println("userid 확인 : ::::::::::::::::::::::::::::"+request.getParameter("userid"));

		int boardid = Integer.parseInt(request.getParameter("boardid"));
		String comments = request.getParameter("comments");
		String userid = request.getParameter("userid");
		
		int result = 0;
		
		try {
			request.setCharacterEncoding("UTF-8");
			QnACommentsDto com = new QnACommentsDto();
			
			com.setBoardID(boardid);
			com.setComments(comments);
			com.setUserID(userid);
			
			QnADao dao = new QnADao();
			result = dao.insertQnAComments(com);

			if (result > 0) {
				System.out.println("등록성공");
			} else { // -1 (제약, 컬럼길이 문제)
				System.out.println("등록실패");
			}
			

			forward = new ActionForward(); forward.setRedirect(false);
			forward.setPath("/WEB-INF/QnA/reply.jsp"); //리스트
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return forward;
	}

}
