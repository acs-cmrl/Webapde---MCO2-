package webapde.project.servlets;
 
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.List;
 
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.mysql.jdbc.Statement;
 
/**
 * A Java servlet that handles file upload from client.
 *
 * @author www.codejava.net
 */
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
     
    // location to store file uploaded
    private static final String UPLOAD_DIRECTORY = "images";
 
    // upload settings
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 40;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
 
    /**
     * Upon receiving file upload submission, parses the request to read
     * upload data and saves the file on disk.
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // checks if the request actually contains upload file
    	String newFileName = "";
        if (!ServletFileUpload.isMultipartContent(request)) {
            // if not, we stop here
            PrintWriter writer = response.getWriter();
            writer.println("Error: Form must has enctype=multipart/form-data.");
            writer.flush();
            return;
        }
 
        // configures upload settings
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // sets memory threshold - beyond which files are stored in disk
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // sets temporary location to store files
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
 
        ServletFileUpload upload = new ServletFileUpload(factory);
         
        // sets maximum size of upload file
        upload.setFileSizeMax(MAX_FILE_SIZE);
         
        // sets maximum size of request (include file + form data)
        upload.setSizeMax(MAX_REQUEST_SIZE);
 
        // constructs the directory path to store upload file
        // this path is relative to application's directory
        String uploadPath = getServletContext().getRealPath("")//"C:\\Users\\Pons\\eclipse-workspace\\WEBAPDEMCO2v7\\WebContent"
                + File.separator + UPLOAD_DIRECTORY;
         
        String privacy = "public";
        String title = "";
        String description = "";
        // creates the directory if it does not exist
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
 
        try {
            // parses the request's content to extract file data
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);
 
            if (formItems != null && formItems.size() > 0) {
                // iterates over form's fields
                for (FileItem item : formItems) {
                    // processes only fields that are not form fields
                	System.out.print(item.getFieldName());
                	if(item.getFieldName().equals("privacy")) {
                		System.out.println(item.getString());
                		if(item.getString()!=null)
                			privacy = "private";
                		else privacy = "public";
                	}
                    if (!item.isFormField()) {
                        String fileName = new File(item.getName()).getName();
                        String filePath = uploadPath + File.separator + fileName;
                        File storeFile = new File(filePath);
                        // saves the file on disk
                        item.write(storeFile);
                        request.setAttribute("message",
                            "Upload has been done successfully!");
                        System.out.println(filePath);
                        newFileName = fileName;
                    }
                    else {
                    	if(item.getFieldName().equals("title"))
                    		title = item.getString();
                    	else if(item.getFieldName().equals("description"))
                    		description = item.getString();
                    	//else if(item.getName())
                    }
                }
            }
        } catch (Exception ex) {
            request.setAttribute("message",
                    "There was an error: " + ex.getMessage());
        }
        
        // TODO Auto-generated method stub
     	//doGet(request, response);
     		
     	final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
     	final String DB_URL="jdbc:mysql://localhost:3306/webapdedb";
     	//  Database credentials
     	final String USER = "root";
     	final String PASS = "1234";
     	
     	String fileNameArray[] = newFileName.split("\\.");
     	System.out.println(newFileName);
     	System.out.println(fileNameArray[0]);
     	System.out.println(fileNameArray[1]);
     	try {
	    	// Register JDBC driver
	         Class.forName(JDBC_DRIVER);

	         // Open a connection
	         Connection db = DriverManager.getConnection(DB_URL, USER, PASS);
	         
	         Statement insertPhoto = (Statement)db.createStatement();
	         
	         //String privacy = "";
	         //String title = request.getParameter("title");
	         //String description = request.getParameter("description");
	         //if(request.getParameter("privacy")==null)
	        	 //privacy = "private";
	         //else privacy = "public";
	         
	         String sqlTag = "INSERT INTO photos (owner_id, photo_path, photo_format, photo_title, photo_description, photo_privacy)\r\n" + 
	         		"VALUES ( '" + request.getSession().getAttribute("userId") + "', '" + 
	        		 "images/" + fileNameArray[0] + "', '" +
	         		fileNameArray[1] + "', '" +
	        		 title + "', '" + 
	         		description + "', '" +
	        		 privacy + "')";
	         
	         //if(!tags.next()) 
	         insertPhoto.executeUpdate(sqlTag);
	         
	         insertPhoto.close();
	         db.close();
	    }catch(Exception e) {System.out.print(e);}
	    finally{} 
     	
        // redirects client to message page
        getServletContext().getRequestDispatcher("/message.jsp").forward(
                request, response);
    }
}