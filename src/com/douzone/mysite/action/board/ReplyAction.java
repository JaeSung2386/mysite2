package com.douzone.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mvc.action.Action;
import com.douzone.mvc.util.WebUtils;
import com.douzone.mysite.repository.BoardDao;
import com.douzone.mysite.vo.BoardVo;

public class ReplyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		String user_no = request.getParameter("userno");
		String no = request.getParameter("no");
		String g_no = request.getParameter("g_no");
		String o_no = request.getParameter("o_no");
		String depth = request.getParameter("depth");
		
		BoardVo vo = new BoardVo();
		vo.setUser_no(Long.parseLong(user_no));
		vo.setNo(Long.parseLong(no));
		vo.setTitle(title);
		vo.setContents(contents);
		vo.setG_no(Integer.parseInt(g_no));
		vo.setO_no(Integer.parseInt(o_no));
		vo.setDepth(Integer.parseInt(depth));
		
		new BoardDao().replyinsert(vo);
		new BoardDao().replyUpdate(vo);
		WebUtils.redirect(request, response, request.getContextPath() + "/board");
	}

}
