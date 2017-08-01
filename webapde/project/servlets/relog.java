package webapde.project.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RelogServlet
 */
@WebServlet("/relog")
public class relog extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public relog() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		// get cookies
		Cookie[] cookies = request.getCookies();
		String username = null;
		// check is username cookie exists
		if(cookies!=null){
			for(int i = 0; i < cookies.length; i++){
				Cookie currentCookie = cookies[i];
				if(currentCookie.getName().equals("username")){
					username = currentCookie.getValue();
					currentCookie.setMaxAge(60*60*24*7*3);
					currentCookie.setHttpOnly(true);
					response.addCookie(currentCookie);
				}
			}
		}
	
		// if exists
		if(username!=null){
			request.getSession().setAttribute("user", username);
			request.getSession().setAttribute("loggedin", "true");
			request.getRequestDispatcher("viewPublicAndSharedPhotos").forward(request, response);
		}else{
		// else
			// user had not visited website, or logged out
			// go to index.html
			request.getSession().setAttribute("user", "visitor");
			request.getSession().setAttribute("loggedin", "false");
			response.sendRedirect("viewPublicPhotos");
		}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
