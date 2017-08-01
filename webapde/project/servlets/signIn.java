package webapde.project.servlets;

import java.awt.Checkbox;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Statement;

/**
 * Servlet implementation class signIn
 */
@WebServlet("/signIn")
public class signIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public signIn() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		String uname = request.getParameter("uname");
		String pword = request.getParameter("pword");
		String rememberMe = request.getParameter("rememberMe");
		boolean found = false;
		
		// JDBC driver name and database URL
	      final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	      final String DB_URL="jdbc:mysql://localhost:3306/webapdedb";
	      //  Database credentials
	      final String USER = "root";
	      final String PASS = "1234";

	      try {
	         // Register JDBC driver
	         Class.forName(JDBC_DRIVER);

	         // Open a connection
	         Connection db = DriverManager.getConnection(DB_URL, USER, PASS);
	         
	         //SQL STATEMENT
	         Statement getAllUsers = (Statement)db.createStatement();
	         String sql = "SELECT user_name, user_password, user_description, user_id FROM users";
	         ResultSet users = getAllUsers.executeQuery(sql);
	         
	         //LOOPING THROUGH THE RESULT
	         while(users.next()) {
	        	 
	        	 //CHECKING IF USER IS IN DATABASE
	        	 String username = users.getString("user_name");
	        	 String password = users.getString("user_password");
	        	 String description = users.getString("user_description");
	        	 String userId = users.getString("user_id");
	        	 if(username.equals(uname) && password.equals(pword)){
	        		found = true;
	     			request.getSession().setAttribute("user", username);
	     			request.getSession().setAttribute("desc", description);
	     			request.getSession().setAttribute("userId",userId);
	     			//IF REMEMBER ME IS CHECKED ADD COOKIE
	     			if(rememberMe != null) {
		     			Cookie cookie = new Cookie("username", uname);
		     			cookie.setMaxAge(60*60*24*7*3);
		     			response.addCookie(cookie);
	     			}
	     			request.getSession().setAttribute("loggedin", "true");
	     			//request.getRequestDispatcher("viewMixedPhotos").forward(request, response);
	     			response.sendRedirect("viewPublicAndSharedPhotos");
	     			break;
	        	 }
	         }
	         
	         if(!found) {
	        	request.setAttribute("invalidLogin", "Please enter a valid username and password.");
	        	request.getRequestDispatcher("Login.jsp").forward(request, response);
   				//response.sendRedirect("Login.jsp");
	         }
   				
	         getAllUsers.close();
	         users.close();
	         db.close();
	      }catch(Exception e) {}
	      finally{} 
	}

}
