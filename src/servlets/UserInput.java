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
import objects.Result;

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
    	
    	//disable caching
    	response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
    	
    	if (request.getParameter("option").contains("getUrls")) {
    		
    		System.out.println("Entered GET request");
    		
    		//HashSet<String> urlsTraversed = new HashSet<String>();
    		//urlsTraversed = PageCrawler.getUrls();
    		
    		HashSet<Result> results = new HashSet<Result>();
    		results = Controller.getResults();
    		
    		//JSONObject jsonUrls = new JSONObject();
    		JSONObject jsonResults = new JSONObject();
    		
            try {
            	//jsonUrls.put("urls", urlsTraversed);
            	jsonResults.put("resultObjects", results);
            	System.out.println("jsonobject:" + jsonResults);
			} catch (JSONException e) {
				System.out.println("[UserInput] JSONException: " + e);
				response.setStatus(500);
			}
    		
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            //out.print(jsonUrls);
            //System.out.println("json Results:  "+ jsonResults);
            out.print(jsonResults);
            out.flush();
    		response.setStatus(200);
    	} else if (request.getParameter("option").contains("clearUrls")) {
    		Controller.clearResults();
    		response.setStatus(200);
    	} else {
    	
    		response.setStatus(400);
    	}   	
    	
    }
    
    
 
}