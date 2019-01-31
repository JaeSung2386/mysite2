package com.douzone.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mvc.action.Action;
import com.douzone.mvc.util.WebUtils;
import com.douzone.mysite.repository.BoardDao;
import com.douzone.mysite.vo.BoardVo;

public class ModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String no = request.getParameter("no");
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		String user_no = request.getParameter("userno");
		
		BoardVo vo = new BoardVo();
		vo.setNo(Long.parseLong(no));
		vo.setTitle(title);
		vo.setContents(contents);
		vo.setUser_no(Long.parseLong(user_no));
		
		
		new BoardDao().Update(vo);
		WebUtils.redirect(request, response, request.getContextPath() + "/board");
	}

}
