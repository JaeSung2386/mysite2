package com.douzone.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mvc.action.Action;
import com.douzone.mvc.util.WebUtils;
import com.douzone.mysite.repository.BoardDao;
import com.douzone.mysite.vo.BoardVo;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String user_no = request.getParameter("userno");
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		
		if(title == "") {
			title = "[제목없음]";
		}
		if(contents == "") {
			contents = "[내용없음]";
		}
		
		BoardVo vo = new BoardVo();
		vo.setUser_no(Long.parseLong(user_no));
		vo.setTitle(title);
		vo.setContents(contents);
		
		new BoardDao().insert(vo);
		
		WebUtils.redirect(request, response, request.getContextPath() + "/board");
	}

}
