package webapde.project.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Statement;

import webapde.project.beans.Photo;

/**
 * Servlet implementation class navbar
 */
@WebServlet("/navbar")
public class navbar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public navbar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		// JDBC driver name and database URL
	      final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	      final String DB_URL="jdbc:mysql://localhost:3306/webapdedb";
	      //  Database credentials
	      final String USER = "root";
	      final String PASS = "1234";
	      
	      String username = request.getParameter("user");
	      
	      try {
	         // Register JDBC driver
	         Class.forName(JDBC_DRIVER);

	         // Open a connection
	         Connection db = DriverManager.getConnection(DB_URL, USER, PASS);
	         
	         //SQL STATEMENT
	         Statement getPhotos = (Statement)db.createStatement();
	         String sql = "Select a.*\r\n" + 
	         		"From photos a, tags b\r\n" + 
	         		"Where a.photo_id = b.photo_id and b.photo_tag like \"%" + request.getParameter("search") + "%\" and a.photo_privacy = \"public\"";
	         ResultSet resultPhotos = getPhotos.executeQuery(sql);
	         ArrayList<Photo> publicPhotos = new ArrayList();
	         ArrayList<Photo> sharedPhotos = new ArrayList();
	         
	         //LOOPING THROUGH THE RESULT
	        
	         while(resultPhotos.next()) {
        		 Photo newPhoto = new Photo();
        		 newPhoto.setId(resultPhotos.getInt("photo_id"));
        		 newPhoto.setDescription(resultPhotos.getString("photo_description"));
        		 newPhoto.setFormat(resultPhotos.getString("photo_format"));
        		 newPhoto.setPrivacy(resultPhotos.getString("photo_privacy").equals("private"));
        		 newPhoto.setTitle(resultPhotos.getString("photo_title"));
        		 newPhoto.setURL(resultPhotos.getString("photo_path"));
        		 newPhoto.setOwner(resultPhotos.getString("user_name"));
        		 newPhoto.setOwnerId(resultPhotos.getInt("owner_id"));
        		 
        		 String tagSql = "SELECT a.*\r\n" + 
         		 		"FROM  tags a, photos b\r\n" + 
         		 		"WHERE b.photo_id = " + resultPhotos.getInt("photo_id") + " and a.photo_id = b.photo_id";
        		 Statement getTags = (Statement)db.createStatement();
        		 ResultSet resultTags = getTags.executeQuery(tagSql);
    	         ArrayList<String> tags = new ArrayList();
        		 while(resultTags.next())
        			 tags.add(resultTags.getString("photo_tag"));
        		 
        		 newPhoto.setTags(tags);
        		 
        		 publicPhotos.add(newPhoto);
        		 
        		 getTags.close();
        		 resultTags.close();
	         }

	         resultPhotos.close();
	         
	         sql = "SELECT a.*, b.user_name\r\n" + 
	         		"FROM photos a, users b, shared c, tags d\r\n" + 
	         		"WHERE a.photo_privacy = 'private' and d.photo_tag like \"%" + request.getParameter("search") + "%\" and a.owner_id = b.user_id and b.user_id = c.user_id and a.owner_id = c.user_id and a.owner_id = "
	         		+request.getSession().getAttribute("userId");
		     resultPhotos = getPhotos.executeQuery(sql);
	         while(resultPhotos.next()) {
        		 Photo newPhoto = new Photo();
        		 newPhoto.setId(resultPhotos.getInt("photo_id"));
        		 newPhoto.setDescription(resultPhotos.getString("photo_description"));
        		 newPhoto.setFormat(resultPhotos.getString("photo_format"));
        		 newPhoto.setPrivacy(resultPhotos.getString("photo_privacy").equals("private"));
        		 newPhoto.setTitle(resultPhotos.getString("photo_title"));
        		 newPhoto.setURL(resultPhotos.getString("photo_path"));
        		 newPhoto.setOwner(resultPhotos.getString("user_name"));
        		 newPhoto.setOwnerId(resultPhotos.getInt("owner_id"));
        		 
        		 String tagSql = "SELECT a.*\r\n" + 
        		 		"FROM tags a left join photos b\r\n" + 
        		 		"ON a.photo_id = b.photo_id and b.photo_privacy = \"private\"";
        		 Statement getTags = (Statement)db.createStatement();
        		 ResultSet resultTags = getTags.executeQuery(tagSql);
    	         ArrayList<String> tags = new ArrayList();
        		 while(resultTags.next())
        			 tags.add(resultTags.getString("photo_tag"));
        		 
        		 newPhoto.setTags(tags);
        		 
        		 sharedPhotos.add(newPhoto);
        		 
        		 getTags.close();
        		 resultTags.close();
	         }
	         
	         publicPhotos.sort(new Comparator<Photo>() {
			@Override
			public int compare(Photo arg0, Photo arg1) {
				// TODO Auto-generated method stub
				return arg0.getId() - arg1.getId();
			}
	         });
	         
	         sharedPhotos.sort(new Comparator<Photo>() {
					@Override
					public int compare(Photo arg0, Photo arg1) {
						// TODO Auto-generated method stub
						return arg0.getId() - arg1.getId();
					}
			         });
	         
	         Collections.reverse((List)publicPhotos);
	         Collections.reverse((List)sharedPhotos);
	         
	         //request.setAttribute("publicPhotosAndShared", photos);
	         request.setAttribute("foundPublicPhotos", publicPhotos);
	         request.setAttribute("foundSharedPhotos", sharedPhotos);
	         request.getRequestDispatcher("publicPhotosLoggedIn.jsp").forward(request, response);
	         
	         getPhotos.close();
	         resultPhotos.close();
	         db.close();
	      }catch(Exception e) {System.out.println(e);}
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
