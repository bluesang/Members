package spms.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.dao.MemberDao;

// 오류 처리 JSP 적용  
@WebServlet("/member/delete")
public class MemberDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int memberCount = 0;		
		try {
			ServletContext sc = this.getServletContext();
			Connection servletConn = (Connection) sc.getAttribute("conn");   
			
			MemberDao memberDao = new MemberDao();
			memberDao.setConn(servletConn);
			
			int no = Integer.parseInt(request.getParameter("no"));
			memberCount = memberDao.delete(no);
			
			response.sendRedirect("list");
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
			
		} finally {
			
			//try {if (conn != null) conn.close();} catch(Exception e) {}
		}

	}
}