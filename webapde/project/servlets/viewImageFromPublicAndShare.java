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
@WebServlet("/viewImageFromPublicAndShare")
public class viewImageFromPublicAndShare extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public viewImageFromPublicAndShare() {
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
				String photoNum = (String)request.getParameter("photoId");
				String photoType = request.getParameter("photoType");
				ArrayList<Photo> photos = new ArrayList();
				if(photoType.equals("public"))
					photos = (ArrayList<Photo>)request.getSession().getAttribute("publicPhotos");
				else
					photos = (ArrayList<Photo>)request.getSession().getAttribute("sharedPhotos");
				System.out.println(Integer.parseInt(photoNum));
				System.out.println(photos.get(Integer.parseInt(photoNum)).getTitle());
				request.getSession().setAttribute("currentPhoto", photos.get(Integer.parseInt(photoNum)));
				request.setAttribute("viewThisPhoto", photos.get(Integer.parseInt(photoNum)));
				request.getRequestDispatcher("image.jsp").forward(request, response);
	}

}
