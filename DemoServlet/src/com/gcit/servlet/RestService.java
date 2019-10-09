package com.gcit.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcit.dto.Login;
import com.gcit.dto.User;
import com.google.gson.Gson;

/**
 * Servlet implementation class RestService
 */
@WebServlet(urlPatterns = { "/user", "/user/", "/user/id/*", "/user/id/*/login", "/user/id/*/login/", "/login", "/login/" })
public class RestService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RestService() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init() {
    	System.out.println("**************************************");
    	System.out.println("**************************************");
    	System.out.println("**************************************");
        System.out.println("***The Servlet is being initialized***");
    	System.out.println("**************************************");
    	System.out.println("**************************************");
    	System.out.println("**************************************");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getRequestURI().substring(request.getContextPath().length());
		
		Gson gson = new Gson();
		

		List<User> users = new ArrayList<User>();
		
		users.add(new User(1, "Brat", "Pitt", new Login(1, "user", "pass")));
		users.add(new User(2, "Al", "Pacino", new Login()));
		users.add(new User(3, "Al", "Pacino", new Login()));
		users.add(new User(4, "Natalie", "Portman", new Login()));
		
		
		if("/user".equals(path) || "/user/".equals(path)) {
			response.setContentType("application/json");
			     
			PrintWriter out = response.getWriter();
			  
			out.print(gson.toJson(users));
			out.flush();
		}
		
		if(path.contains("/user/id")) {
			String pathInfo = request.getPathInfo();
			if(pathInfo == null) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			}
			else {
				try {
					Integer id = Integer.parseInt(pathInfo.replaceAll("/", ""));
					
					response.setContentType("application/json");
				     
					PrintWriter out = response.getWriter();
					
					//out.print(pathInfo);
					  
					out.print(gson.toJson( users.stream()
												.filter( user -> user.getId() == id )
												.collect(Collectors.toList()) )
							);
					
					out.flush();
				}
				catch(Exception e) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				}
				
			}
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		BufferedReader reader = request.getReader();
		

		List<User> users = new ArrayList<User>();
		users.add(new User(1, "Brat", "Pitt", new Login(1, "user", "pass")));
		users.add(new User(2, "Al", "Pacino", new Login(2, "userN", "passW")));
		users.add(new User(3, "Al", "Pacino", new Login(3, "uName", "pWord")));
		users.add(new User(4, "Natalie", "Portman", new Login(4, "username", "password")));
		
		String path = request.getRequestURI().substring(request.getContextPath().length());

		Gson gson = new Gson();
		List<Login> login = new ArrayList<Login>();
		
		if("/login/".equals(path) || "/login".equals(path)) {
			response.setContentType("text/html");
			     
			PrintWriter out = response.getWriter();
//			
//			out.print(users.get(2).getLogin().getUsername());
//			out.print(users.get(2).getLogin().getPassword());
//			  
			login.add(gson.fromJson(request.getReader(), Login.class));
			
			List<User> user = users.stream().filter( user2 -> login.get(0).getUserId().equals(user2.getId()) 
															&& login.get(0).getUsername().equals(user2.getLogin().getUsername())
															&& login.get(0).getPassword().equals(user2.getLogin().getPassword()))
											.collect(Collectors.toList());
			
			if(user.size() > 0) {
				out.print(user.get(0).getFirstName() + " " + user.get(0).getLastName() + ": Successful Login");
			}
			else {
				out.print("Invalid Login");
			}
			
			//out.print(gson.toJson(login));
			out.flush();
		}

	}

}
