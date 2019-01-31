package com.douzone.mysite.action.comment;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mvc.action.Action;
import com.douzone.mvc.util.WebUtils;
import com.douzone.mysite.repository.CommentDao;
import com.douzone.mysite.vo.CommentVo;

public class ModifyFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String no = request.getParameter("no");
		CommentVo vo = new CommentDao().get(Long.parseLong(no));
		request.setAttribute("vo", vo);
		
		WebUtils.forward(request, response, "/WEB-INF/views/comment/modify.jsp");
	}

}
