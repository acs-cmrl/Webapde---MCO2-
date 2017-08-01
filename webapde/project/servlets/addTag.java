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

import webapde.project.beans.Photo;

/**
 * Servlet implementation class addTag
 */
@WebServlet("/addTag")
public class addTag extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addTag() {
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
	    String newTag = request.getParameter("newTag");
	    
	    try {
	    	// Register JDBC driver
	         Class.forName(JDBC_DRIVER);

	         // Open a connection
	         Connection db = DriverManager.getConnection(DB_URL, USER, PASS);
	         
	         //SQL STATEMENT	        		 
	         String sql = "SELECT a.* \r\n" + 
	         		"FROM  tags a, photos b \r\n" + 
	         		"WHERE b.photo_id = " + photoId + " and a.photo_id = b.photo_id and a.photo_tag = '" + newTag + "'";

	         Statement getAllTags = (Statement)db.createStatement();
	         Statement insertTag = (Statement)db.createStatement();
	         ResultSet tags = getAllTags.executeQuery(sql);
	         
	         String sqlTag = "INSERT INTO tags (photo_id, photo_tag)\r\n" + 
	         		"VALUES ( '" + photoId + "', '" + newTag + "' )";
	         
	         if(!tags.next()) 
	        	 insertTag.executeUpdate(sqlTag);
	         
	         //redirect to some place
	         request.setAttribute("photoId", Integer.toString(photoId));
	         request.setAttribute("newTag", (String)newTag);
	         request.getRequestDispatcher("viewImageFromAddTag").forward(request, response);
	         
	         tags.close();
	         getAllTags.close();
	         insertTag.close();
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
