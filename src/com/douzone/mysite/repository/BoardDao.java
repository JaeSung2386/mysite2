package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.douzone.mysite.vo.BoardVo;

public class BoardDao {
	
	public int delete( BoardVo vo ) {
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = 
				" delete board " + 
				"  from board" + 
				"  join user " +
				"  on board.user_no = ?" +
				"  where board.no = ?" +
				"  and user.password = ?";
			
			pstmt = conn.prepareStatement( sql );

			pstmt.setLong( 1, vo.getUser_no() );
			pstmt.setLong(2, vo.getNo());
			pstmt.setString(3, vo.getPassword());
			
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;		
	}
	
	public List<BoardVo> getSearch(String krd, String search, int startRow, int endRow) {
		List<BoardVo> list = new ArrayList<BoardVo>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			stmt = conn.createStatement();
			String sql = null;
			
			if ("title".equals(search)) {
				sql = 
					"select board.no, " +
					"		title, " +
					"		contents, " +
					" 		write_date, " +
					"		hit, " +
					"		g_no, " +
					" 		o_no, " +
					" 		depth, " +
					" 		name, " +
					"		user_no " +
					"from board, user " +
					"where board.user_no = user.no " +
					"and title LIKE '%" + krd + "%' " +
					"order by g_no desc, " +
					"o_no asc ";
				
			} else if("contents".equals(search)) {
				sql = 
						"select board.no, " +
						"		title, " +
						"		contents, " +
						" 		write_date, " +
						"		hit, " +
						"		g_no, " +
						" 		o_no, " +
						" 		depth, " +
						" 		name, " +
						"		user_no " +
						"from board, user " +
						"where board.user_no = user.no " +
						"and contents LIKE '%" + krd + "%' " +
						"order by g_no desc, " +
						"o_no asc ";
				
			} else if("all".equals(search)) {
				sql = 
						"select board.no, " +
						"		title, " +
						"		contents, " +
						" 		write_date, " +
						"		hit, " +
						"		g_no, " +
						" 		o_no, " +
						" 		depth, " +
						" 		name, " +
						"		user_no " +
						"from board, user " +
						"where board.user_no = user.no " +
						"and title LIKE '%" + krd + "%' " +
						"and contents LIKE '%" + krd + "%' " +
						"order by g_no desc, " +
						"o_no asc ";
			}
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				String write_date = rs.getString(4);
				int hit = rs.getInt(5);
				int g_no = rs.getInt(6);
				int o_no = rs.getInt(7);
				int depth = rs.getInt(8);
				String name = rs.getString(9);
				long user_no = rs.getLong(10);
				
				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setWrite_date(write_date);
				vo.setHit(hit);
				vo.setG_no(g_no);
				vo.setO_no(o_no);
				vo.setDepth(depth);
				vo.setName(name);
				vo.setUser_no(user_no);
				
				list.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	public int getSearchTotalCount(String kwd, String search) {
		int count = 0;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			stmt = conn.createStatement();
			String sql = null;
			
			if ("title".equals(search)) {
				sql = 
					"select count(*) " +
					"from board, user " +
					"where board.user_no = user.no " +
					"and title LIKE '%" + kwd + "%'";
				
			} else if("contents".equals(search)) {
				sql = 
						"select count(*) " +
								"from board, user " +
								"where board.user_no = user.no " +
								"and contents LIKE '%" + kwd + "%'";
				
			} else if("all".equals(search)) {
				sql = 
						"select count(*) " +
								"from board, user " +
								"where board.user_no = user.no " +
								"and title LIKE '%" + kwd + "%'"  +
								"and contents LIKE '%" + kwd + "%'";
			}
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return count;
	}
	
	public int insert(BoardVo vo) {
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = 
				" insert" + 
				"   into board" + 
				" values ( null, ?, ?, now(), 0, (select if(count(*)=0, 1, MAX(g_no)+1) FROM board b), 1, 0, ? )";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getUser_no());
			
			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return count;
	}
	
	public int insertComment(BoardVo vo) {
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = 
				" insert" + 
				"   into board" + 
				" values ( " + 
				"		null, " +
				"		?, ?, now(), 0, (select if(count(*)=0, 1, MAX(g_no)+1) FROM board b), 1, 0, ? )";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getUser_no());
			
			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return count;
	}	
	public int replyinsert(BoardVo vo) {
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = 
				" insert" + 
				"   into board" + 
				" values ( "+
				//no
				" 		null, " +
				//title
				"		?, " + 
				//contents
				"		?, " + 
				//글작성시간
				"		now(), " +
				//조회수
				"		0, " +
				//g_no(부모의 g_no)
				"		(select b.g_no FROM board b where b.no = ?), " +
				//o_no(부모의 o_no + 1)
				"		(select b.o_no+1 FROM board b where b.no = ?), " +
				//depth(부모의 depth + 1)
				"		(select b.depth+1 FROM board b where b.no = ?), " +
				//외래키(유저번호)
				"		? )";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getNo());
			pstmt.setLong(4, vo.getNo());
			pstmt.setLong(5, vo.getNo());
			pstmt.setLong(6, vo.getUser_no());

			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return count;
	}
	
	public void replyUpdate(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			
			String sql =
					"update board  " + 
					"set o_no = o_no + 1 " + 
					"where g_no = ? " +
					"and o_no > ? " +
					"and write_date < (select * from (select max(b.write_date) from board b) tmp)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, vo.getG_no());
			pstmt.setLong(2, vo.getO_no());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void HitUpdate(long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql =
					"update board " + 
					"set hit = hit + 1 " + 
					"where no = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int Update(BoardVo vo) {
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();

			String sql =
					"update board " + 
					"set title = ?, contents = ?, write_date = now() " + 
					"where user_no = ? " +
					"and no = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getUser_no());
			pstmt.setLong(4, vo.getNo());
			
			count = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}
	
	public int getTotalCount() {
		int count = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();

			String sql = 
				" select count(*) from board";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}
	
	public BoardVo get(long no) {
		BoardVo vo = new BoardVo();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();

			String sql = 
				" select title, contents, user_no, g_no, o_no, depth" + 
				"   from board" + 
				"  where no=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, no);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String title = rs.getString(1);
				String contents = rs.getString(2);
				long user_no = rs.getLong(3);
				int g_no = rs.getInt(4);
				int o_no = rs.getInt(5);
				int depth = rs.getInt(6);
				
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setUser_no(user_no);
				vo.setG_no(g_no);
				vo.setO_no(o_no);
				vo.setDepth(depth);
				
			}
		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return vo;
	}
	
	public List<BoardVo> getList(int startRow, int endRow) {
		List<BoardVo> list = new ArrayList<BoardVo>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			stmt = conn.createStatement();
			
			String sql = 
					"select b.no, " +
					"		b.title, " +
					"		b.contents, " +
					" 		b.write_date,  " +
					"		b.hit, " +
					"		b.g_no, " +
					" 		b.o_no, " +
					" 		b.depth, " +
					" 		u.name, " +
					"		b.user_no " +
					"from board b, user u " +
					"where b.user_no = u.no " +
					"order by b.g_no desc, " +
					"b.o_no asc " +
					"limit " + startRow + ", " + endRow;
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				String write_date = rs.getString(4);
				int hit = rs.getInt(5);
				int g_no = rs.getInt(6);
				int o_no = rs.getInt(7);
				int depth = rs.getInt(8);
				String name = rs.getString(9);
				long user_no = rs.getLong(10);
				
				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setWrite_date(write_date);
				vo.setHit(hit);
				vo.setG_no(g_no);
				vo.setO_no(o_no);
				vo.setDepth(depth);
				vo.setName(name);
				vo.setUser_no(user_no);
				
				list.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			//1. 드라이버 로딩
			Class.forName( "com.mysql.jdbc.Driver" );
			
			//2. 연결하기
			String url="jdbc:mysql://localhost/webdb?characterEncoding=utf8&serverTimezone=UTC";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch( ClassNotFoundException e ) {
			System.out.println( "드러이버 로딩 실패:" + e );
		} 
		
		return conn;
	}	
}
