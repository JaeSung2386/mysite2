package com.douzone.mysite.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mvc.action.Action;
import com.douzone.mvc.util.WebUtils;
import com.douzone.mysite.repository.BoardDao;
import com.douzone.mysite.repository.CommentDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.CommentVo;

public class ViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String no = request.getParameter("no");
		BoardVo vo = new BoardDao().get(Long.parseLong(no));
		new BoardDao().HitUpdate(Long.parseLong(no));
		request.setAttribute("vo", vo);
		
		CommentDao dao = new CommentDao();
		List<CommentVo> list = dao.getList(Long.parseLong(no));
		request.setAttribute("list", list);
		
		WebUtils.forward(request, response, "/WEB-INF/views/board/view.jsp");

	}

}
