package com.douzone.mysite.action.comment;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mvc.action.Action;
import com.douzone.mvc.util.WebUtils;
import com.douzone.mysite.repository.CommentDao;
import com.douzone.mysite.vo.CommentVo;

public class ModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String no = request.getParameter("no");
		String contents = request.getParameter("contents");
		String user_no = request.getParameter("user_no");
		String board_no = request.getParameter("board_no");
		
		CommentVo vo = new CommentVo();
		vo.setNo(Long.parseLong(no));
		vo.setContents(contents);
		vo.setUser_no(Long.parseLong(user_no));
		vo.setBoard_no(Long.parseLong(board_no));
		
		new CommentDao().Update(vo);
		WebUtils.redirect(request, response, request.getContextPath() + "/board?a=view&no=" + board_no);
	}

}
