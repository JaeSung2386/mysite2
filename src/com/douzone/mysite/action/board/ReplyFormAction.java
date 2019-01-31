package com.douzone.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mvc.action.Action;
import com.douzone.mvc.util.WebUtils;
import com.douzone.mysite.repository.BoardDao;
import com.douzone.mysite.vo.BoardVo;

public class ReplyFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String no = request.getParameter("no");
		BoardVo vo = new BoardDao().get(Long.parseLong(no));
		request.setAttribute("vo", vo);
		System.out.println("씨바");
		WebUtils.forward(request, response, "/WEB-INF/views/board/reply.jsp");
	}
}
