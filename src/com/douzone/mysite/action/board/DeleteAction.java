package com.douzone.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mvc.action.Action;
import com.douzone.mvc.util.WebUtils;
import com.douzone.mysite.repository.BoardDao;
import com.douzone.mysite.repository.CommentDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.CommentVo;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String no = request.getParameter("no");
		String user_no = request.getParameter("user_no");
		String password = request.getParameter("password");
		
		//게시글에 있는 댓글을 전부 삭제하고 게시글 삭제
		//참조제약때문에 댓글을 선삭제해야함
		CommentVo vo1 = new CommentVo();
		vo1.setBoard_no(Long.parseLong(no));
		new CommentDao().deleteAll(vo1);
		
		BoardVo vo2 = new BoardVo();
		vo2.setNo(Long.parseLong(no));
		vo2.setUser_no(Long.parseLong(user_no));
		vo2.setPassword(password);
		new BoardDao().delete(vo2);
		
		
		WebUtils.redirect(request, response, request.getContextPath() + "/board");	
	}

}
