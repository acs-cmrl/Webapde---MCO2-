package webapde.project.servlets;

import java.io.IOException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Statement;

/**
 * Servlet implementation class signUp
 */
@WebServlet("/signUp")
public class signUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public signUp() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		String uname = request.getParameter("uname");
		String pword = request.getParameter("pword");
		String desc = request.getParameter("desc");
		
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	    final String DB_URL="jdbc:mysql://localhost:3306/webapdedb";
	    //  Database credentials
	    final String USER = "root";
	    final String PASS = "1234";
	    
	    boolean hasUser = false;
	    
	    try {
	    	// Register JDBC driver
	         Class.forName(JDBC_DRIVER);

	         // Open a connection
	         Connection db = DriverManager.getConnection(DB_URL, USER, PASS);
	         
	         //SQL STATEMENT
	         Statement getAllUsers = (Statement)db.createStatement();
	         //String sql = "SELECT user_name = '" + uname + "' FROM users";
	         String sql = "SELECT user_name FROM users";
	         ResultSet users = getAllUsers.executeQuery(sql);
	         while(users.next()){
	        	 String username = users.getString("user_name");
	        	 if(username.equals(uname)) {
	        		 hasUser = true;
	        	 }
	         }
	         if(!hasUser && !uname.isEmpty() && !pword.isEmpty()) {
	        	 sql = "INSERT INTO users (user_name,user_password,user_description) VALUES ("
	        			 + "'"+uname+"',"
	        	 		 + "'"+pword+"',"
	        	 		 + "'"+desc+"'"
	        	 		 + ")";
	        	 getAllUsers.executeUpdate(sql);
	        	 request.setAttribute("message", "Successfully Registered");
	        	 request.getRequestDispatcher("registerSuccess.jsp").forward(request, response);
	         }
	         else 
	        	request.setAttribute("invalidLogin", "Please enter a valid username and password.");
	        	request.getRequestDispatcher("Register.jsp").forward(request, response);
	         
	         getAllUsers.close();
	         users.close();
	         db.close();
	    }catch(Exception e) {System.out.print(e);}
	    finally{} 
	}

}
