package com.douzone.mysite.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mvc.action.Action;
import com.douzone.mvc.util.WebUtils;
import com.douzone.mysite.repository.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.PageVo;

public class SearchAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String kwd = request.getParameter("kwd");
		String search = request.getParameter("search");
		int page = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
	
		BoardDao dao = new BoardDao();
		List<BoardVo> list = null;
		
		PageVo pagevo = new PageVo();
		pagevo.setPageNo(page);
		pagevo.setPageSize(10);
		
		//키워드 파라미타가 없거나 아무 값도 입력안하고 검색했을 경우
		if(kwd == null || kwd == "") {
			pagevo.setTotalCount(dao.getTotalCount());
			page = (page - 1) * 10;
			list = dao.getList(page, pagevo.getPageSize());
		} else {
			pagevo.setTotalCount(dao.getSearchTotalCount(kwd, search));
			page = (page - 1) * 10;
			list = dao.getSearch(kwd, search, page, pagevo.getPageSize());
		}
		
		request.setAttribute("pagevo", pagevo);
		request.setAttribute("list", list);
		WebUtils.forward(request, response, "/WEB-INF/views/board/list.jsp");
	}

}
