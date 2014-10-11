package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("<h1>Hello Servlet</h1>");
        response.getWriter().println("session=" + request.getSession(true).getId());
        if (request.getRequestURI().endsWith("/db")) {
	        showDatabase(request,response);
	      } else {
//	        showHome(request,response);
	      }
    }
    
    private void showDatabase(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
      try {
        Connection connection = getConnection();

        Statement stmt = connection.createStatement();
//        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
//        stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
//        ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");
//
//        String out = "Hello!\n";
//        while (rs.next()) {
//            out += "Read from DB: " + rs.getTimestamp("tick") + "\n";
//        }
        // create
        String sql = "CREATE TABLE COMPANY " +
            "(ID INT PRIMARY KEY     NOT NULL," +
            " NAME           TEXT    NOT NULL, " +
            " AGE            INT     NOT NULL, " +
            " ADDRESS        CHAR(50), " +
            " SALARY         REAL)";
        stmt.executeUpdate(sql);
        
        // insert
        sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
            + "VALUES (1, 'Paul', 32, 'California', 20000.00 );";
	      stmt.executeUpdate(sql);
	
	      sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
	            + "VALUES (2, 'Allen', 25, 'Texas', 15000.00 );";
	      stmt.executeUpdate(sql);
	
	      sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
	            + "VALUES (3, 'Teddy', 23, 'Norway', 20000.00 );";
	      stmt.executeUpdate(sql);
	
	      sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
	            + "VALUES (4, 'Mark', 25, 'Rich-Mond ', 65000.00 );";
	      stmt.executeUpdate(sql);
	      
	      // select
	      ResultSet rs = stmt.executeQuery( "SELECT * FROM COMPANY;" );
	      String out = "Hello!\n";
        while ( rs.next() ) {
           int id = rs.getInt("id");
           String  name = rs.getString("name");
           int age  = rs.getInt("age");
           String  address = rs.getString("address");
           float salary = rs.getFloat("salary");
           out += "ID = " + id + "\n";
           out += "NAME = " + name + "\n";
           out += "AGE = " + age + "\n";
           out += "ADDRESS = " + address + "\n";
           out += "SALARY = " + salary + "\n";
           out += "\n";
        }
        resp.getWriter().print(out);
      } catch (Exception e) {
        resp.getWriter().print("There was an error: " + e.getMessage());
      }
    }

    private Connection getConnection() throws URISyntaxException, SQLException {
      URI dbUri = new URI(System.getenv("DATABASE_URL"));

      String username = dbUri.getUserInfo().split(":")[0];
      String password = dbUri.getUserInfo().split(":")[1];
      String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();

      return DriverManager.getConnection(dbUrl, username, password);
    }
}
