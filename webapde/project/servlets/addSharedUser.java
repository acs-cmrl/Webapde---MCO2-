package webapde.project.servlets;

import java.io.IOException;
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
 * Servlet implementation class addSharedUser
 */
@WebServlet("/addSharedUser")
public class addSharedUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addSharedUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	    final String DB_URL="jdbc:mysql://localhost:3306/webapdedb";
	    //  Database credentials
	    final String USER = "root";
	    final String PASS = "1234";
	    
	    int photoId = Integer.parseInt(request.getParameter("photoId"));
	    String newSharedUser = request.getParameter("newSharedUser");
	    boolean notPresent = true;
	    
	    try {
	    	// Register JDBC driver
	         Class.forName(JDBC_DRIVER);

	         // Open a connection
	         Connection db = DriverManager.getConnection(DB_URL, USER, PASS);
	         
	         //SQL STATEMENT	        		 
	         String sql = "SELECT a.*\r\n" + 
        		 		"FROM  shared a, users b\r\n" + 
        		 		"WHERE a.photo_id = " + photoId + " and a.user_id = b.user_id and b.user_name = '" + newSharedUser + "'";

	         Statement getAllSharedUsers = (Statement)db.createStatement();
	         Statement insertSharedUser = (Statement)db.createStatement();
	         ResultSet sharedUsers = getAllSharedUsers.executeQuery(sql);
	         

	         String sql2 = "Select user_id\r\n" + 
	         		"From users \r\n" + 
	         		"where user_name = '" + newSharedUser + "'";
	         
	         if(sharedUsers.next()) 
	        	 notPresent = false;
	        	 
	         getAllSharedUsers.close();
	         getAllSharedUsers = (Statement)db.createStatement();
	         
	         System.out.println("1");
	         ResultSet userId = getAllSharedUsers.executeQuery(sql2);
	         System.out.println("2");
	         
	         System.out.println("3");
	         
	         String sqlUser = null;
	         
	         if(userId.next())
		         sqlUser = "INSERT INTO shared (photo_id, user_id)\r\n" + 
		 	         		"VALUES ( " + photoId + ", " + userId.getInt("user_id") + " )";
	         System.out.println("4");
	         
	         if(notPresent && sqlUser != null) {
	        	 System.out.println(5);
	        	 insertSharedUser.executeUpdate(sqlUser);
	         }
	         
	         System.out.println("6");
	         
	         //redirect to some place
	         request.setAttribute("photoId", Integer.toString(photoId));
	         request.setAttribute("newTag", "shareduser");
	         request.getRequestDispatcher("viewImageFromAddTag").forward(request, response);
	         
	         sharedUsers.close();
	         userId.close();
	         getAllSharedUsers.close();
	         insertSharedUser.close();
	         db.close();
	    }catch(Exception e) {System.out.print(e);}
	    finally{}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
