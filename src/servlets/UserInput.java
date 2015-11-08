package servlets;

/*
 * Used to handle the user's input
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import main.*;

@WebServlet("/UserInput")
public class UserInput extends HttpServlet {
 
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	
    	String domainName = request.getParameter("domainName");
    	int numberPages = Integer.parseInt(request.getParameter("numberPages"));
    	
    	Initialize.StartCrawler(domainName, numberPages);
    	
    	response.setStatus(200);
    }
    
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	
    	if (request.getParameter("get").indexOf("getUrls") > -1) {
    		HashSet<String> urlsTraversed = new HashSet<String>();
    		urlsTraversed = PageCrawler.getUrls();
    		
    		JSONObject jsonUrls = new JSONObject();
            try {
            	jsonUrls.put("urls", urlsTraversed);
			} catch (JSONException e) {
				System.out.println("[UserInput] JSONException: " + e);
				response.setStatus(500);
			}
    		
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(jsonUrls);
            out.flush();
    		response.setStatus(200);
    	} else {
    		response.setStatus(400);
    	}   	
    	
    }
    
    
 
}