package controller;

import java.io.IOException;  
import java.io.PrintWriter;  
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
  
import org.json.JSONObject;  
  
public class JsonServlet extends HttpServlet {  
    private static final long serialVersionUID = 1L;  
  
    public JsonServlet() {  
        super();  
        // TODO Auto-generated constructor stub  
    }  
  
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
  
    }  
  
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
      
        PrintWriter out= response.getWriter();  
        JSONObject json = new JSONObject();  
        json.put("name", "Sameera Jayasekara");  
        json.put("email", "codesstore@blogspot.com");  
        out.print(json);  
      
    }  
      
  
}  
