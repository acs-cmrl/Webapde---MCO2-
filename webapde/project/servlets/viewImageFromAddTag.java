package webapde.project.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import webapde.project.beans.Photo;

/**
 * Servlet implementation class viewImageFromPublicAndShare
 */
@WebServlet("/viewImageFromAddTag")
public class viewImageFromAddTag extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public viewImageFromAddTag() {
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
				// doGet(request, response);
				//Photo photo = (Photo)request.getParameter("photos");
				//int photoNum = Integer.parseInt((String)request.getAttribute("photoId"));
				String photoType = "";
				Photo photos = new Photo();
				ArrayList<String> newTags = new ArrayList();
				photos = (Photo)request.getSession().getAttribute("currentPhoto");
				//System.out.println(photoNum);
				//System.out.println(photos.get(photoNum).getTitle());
				if(!request.getAttribute("newTag").equals("shareduser"))
					photos.getTags().add((String)request.getAttribute("newTag"));
				//newTags = photos.getTags();
				//newTags.add((String)request.getAttribute("newTag"));
				//photos.setTags(newTags);
				request.setAttribute("viewThisPhoto", photos);
				request.getRequestDispatcher("image.jsp").forward(request, response);
	}

}
