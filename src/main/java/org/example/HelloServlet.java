package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.Task;
import data.Task.CATEGORY;
import data.Task.PRIORITY;

public class HelloServlet extends HttpServlet {

	
	private static final String TASK_TABLE_NAME = "TASK";
	private static final String STAT_TABLE_NAME = "STAT";
	
	protected void createTasksTable() throws URISyntaxException, SQLException {
		Connection connection = getConnection();
		String sql = "CREATE TABLE " + TASK_TABLE_NAME 
				+ " (ID INT PRIMARY KEY     NOT NULL,"
				+ " Event           TEXT    NOT NULL, "
				+ " Catogory            TEXT     NOT NULL, "
				+ " StartTimes        TEXT, " 
				+ " DuringTime         numeric,"
				+ " FinalStartTime			numeric," 
				+ " Scheduled				TEXT,"
				+ " Priority		TEXT"
				+ ");";
		
		Statement stmt = connection.createStatement();
		stmt.execute(sql);

		stmt.close();
		connection.close();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		showDatabase(request, response);
		// response.setContentType("text/html");
		// response.setStatus(HttpServletResponse.SC_OK);
		// response.getWriter().println("<h1>Hello Servlet</h1>");
		// response.getWriter().println("session=" +
		// request.getSession(true).getId());
		// if (request.getRequestURI().endsWith("?db")) {
		// showDatabase(request,response);
		// } else {
		// // showHome(request,response);
		// }
	}

	private void showDatabase(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Connection connection = null;
		Statement stmt = null;
		try {
			connection = getConnection();

			stmt = connection.createStatement();
			// stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
			// stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
			// ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");
			//
			// String out = "Hello!\n";
			// while (rs.next()) {
			// out += "Read from DB: " + rs.getTimestamp("tick") + "\n";
			// }
			// create
			String sql = null;
			
			sql = "DROP TABLE IF EXISTS COMPANY;";
			stmt.execute(sql);
			
			sql = "DROP TABLE IF EXISTS " + TASK_TABLE_NAME + ";";
			stmt.execute(sql);
			
			sql = "CREATE TABLE COMPANY "
					+ "(ID INT PRIMARY KEY     NOT NULL,"
					+ " NAME           TEXT    NOT NULL, "
					+ " AGE            INT     NOT NULL, "
					+ " ADDRESS        CHAR(50), " + " SALARY         REAL)";
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
			ResultSet rs = stmt.executeQuery("SELECT * FROM COMPANY;");
			String out = "Hello!\n";
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				String address = rs.getString("address");
				float salary = rs.getFloat("salary");
				out += "ID = " + id + "\n";
				out += "NAME = " + name + "\n";
				out += "AGE = " + age + "\n";
				out += "ADDRESS = " + address + "\n";
				out += "SALARY = " + salary + "\n";
				out += "\n";
			}
			
			
			createTasksTable();
			StringBuffer sb = new StringBuffer();
			Task t = new Task(100, "HIT", CATEGORY.COMPANY, new ArrayList<Long>(), 1000L, 2313424L, true, PRIORITY.MEDIUM);
			int num  = flushTaskToDB(t);
			
			sb.append("\n" + " !!!insert " + num + " rows here" + "\n");
			rs = stmt.executeQuery("SELECT * FROM TASK;");
			while (rs.next()) { //ID,EVENT,Catogory,StartTimes,DuringTime,FinalStartTime,Scheduled
				int id = rs.getInt("ID");
				String name = rs.getString("EVENT");
				String category = rs.getString("Catogory");
				String startTimes = rs.getString("StartTimes");
				long duringTime = rs.getLong("DuringTime");
				sb.append("ID = " + id + "\n");
				sb.append("NAME = " + name + "\n");
				sb.append("category = " + category + "\n");
				sb.append("startTimes = " + startTimes + "\n");
				sb.append("duringTime = " + duringTime + "\n");
				sb.append("\n");
			}
			
			
			resp.getWriter().print(out + "\n $$$ \n" + sb.toString());
		} catch (Exception e) {
			resp.getWriter().print("There was an error: " + e.getMessage());
		} finally {
			try {
				stmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private Connection getConnection() throws URISyntaxException, SQLException {
		URI dbUri = new URI(System.getenv("DATABASE_URL"));

		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();

		return DriverManager.getConnection(dbUrl, username, password);
	}

	public int flushTaskToDB(Task task) {
		Connection connection = null;
		Statement pStmt = null;
		int r = -2;
		try {
			connection = getConnection();
			pStmt = connection.createStatement();
			String sql = "INSERT INTO " + TASK_TABLE_NAME + 
							" (ID,EVENT,Catogory,StartTimes,DuringTime,FinalStartTime,Scheduled,Priority) "
							+ "VALUES ("
							+ task.getId()
							+ ", '"
							+ task.getEvent()
							+ "', '"
							+ task.getCatogory().name()
							+ "', '"
							+ task.getStartTimes().toString()
							+ "', "
							+ task.getDuringTime()
							+ ", "
							+ task.getFinalStartTime()
							+ ", '"
							+ task.isScheduled()
							+ "' ,'"
							+ task.getPriority().name()
							+ "');";
				r = pStmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} finally {
			try {
				pStmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}
		return r;

	}
}
