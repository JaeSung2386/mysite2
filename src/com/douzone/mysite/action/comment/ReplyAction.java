package com.douzone.mysite.action.comment;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mvc.action.Action;
import com.douzone.mvc.util.WebUtils;
import com.douzone.mysite.repository.CommentDao;
import com.douzone.mysite.vo.CommentVo;

public class ReplyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String contents = request.getParameter("contents");
		String no = request.getParameter("no");
		String g_no = request.getParameter("g_no");
		String o_no = request.getParameter("o_no");
		String depth = request.getParameter("depth");
		String board_no = request.getParameter("board_no");
		String user_no = request.getParameter("user_no");
		
		CommentVo vo = new CommentVo();
		vo.setNo(Long.parseLong(no));
		vo.setContents(contents);
		vo.setG_no(Integer.parseInt(g_no));
		vo.setO_no(Integer.parseInt(o_no));
		vo.setDepth(Integer.parseInt(depth));
		vo.setBoard_no(Long.parseLong(board_no));
		vo.setUser_no(Long.parseLong(user_no));
		
		new CommentDao().replyinsert(vo);
		new CommentDao().replyUpdate(vo);
		WebUtils.redirect(request, response, request.getContextPath() + "/board?a=view&no=" + board_no);
	}

}
