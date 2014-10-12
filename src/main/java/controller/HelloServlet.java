package controller;

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

import tablemanager.TaskTableManager;
import datamodel.Interval;
import datamodel.Task;
import datamodel.Task.CATEGORY;

public class HelloServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().println("I am here");
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
		TaskTableManager taskManager = TaskTableManager.getInstance();
		taskManager.createTable();
		
		ArrayList<Interval> ds = new ArrayList<Interval>();
		ds.add(new Interval(5, 9));
		Task t1 = new Task(101, "SMS", CATEGORY.FAMILY, ds, new Interval(1,3), true, 1);
		Task t2 = new Task(102, "TDS", CATEGORY.OTHER, ds, new Interval(7,8), false, 2);
		taskManager.writeTaskToDB(t1);
		taskManager.writeTaskToDB(t2);
		
		ArrayList<Task> tasks = taskManager.readTaskTable();
		for(Task task : tasks) {
			//System.out.println(task.toString());
			resp.getWriter().println(task.toString());
		}
		
		resp.getWriter().println();
		resp.getWriter().println();
		
		t2.setCategory(CATEGORY.COMPANY);
		t2.setEvent("BYE");
		ArrayList<Task> ls = new ArrayList<Task>();
		ls.add(t2);
		int rn = -10;
		rn = taskManager.updateTasks(ls);
		resp.getWriter().println("!!!! row num updated = " + rn);
		tasks = taskManager.readTaskTable();
		for(Task task : tasks) {
			resp.getWriter().println(task.toString());
		}		
		
		
		
	//	try {
			//connection = getConnection();

			//stmt = connection.createStatement();
			// stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
			// stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
			// ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");
			//
			// String out = "Hello!\n";
			// while (rs.next()) {
			// out += "Read from DB: " + rs.getTimestamp("tick") + "\n";
			// }
			// create
//			String sql = null;
//			
//			sql = "DROP TABLE IF EXISTS COMPANY;";
//			stmt.execute(sql);
//			
//			sql = "DROP TABLE IF EXISTS " + TASK_TABLE_NAME + ";";
//			stmt.execute(sql);
//			
//			sql = "CREATE TABLE COMPANY "
//					+ "(ID INT PRIMARY KEY     NOT NULL,"
//					+ " NAME           TEXT    NOT NULL, "
//					+ " AGE            INT     NOT NULL, "
//					+ " ADDRESS        CHAR(50), " + " SALARY         REAL)";
//			stmt.executeUpdate(sql);

			// insert
//			sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
//					+ "VALUES (1, 'Paul', 32, 'California', 20000.00 );";
//			stmt.executeUpdate(sql);
//
//			sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
//					+ "VALUES (2, 'Allen', 25, 'Texas', 15000.00 );";
//			stmt.executeUpdate(sql);
//
//			sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
//					+ "VALUES (3, 'Teddy', 23, 'Norway', 20000.00 );";
//			stmt.executeUpdate(sql);
//
//			sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
//					+ "VALUES (4, 'Mark', 25, 'Rich-Mond ', 65000.00 );";
//			stmt.executeUpdate(sql);

			// select
//			ResultSet rs = stmt.executeQuery("SELECT * FROM COMPANY;");
//			String out = "Hello!\n";
//			while (rs.next()) {
//				int id = rs.getInt("id");
//				String name = rs.getString("name");
//				int age = rs.getInt("age");
//				String address = rs.getString("address");
//				float salary = rs.getFloat("salary");
//				out += "ID = " + id + "\n";
//				out += "NAME = " + name + "\n";
//				out += "AGE = " + age + "\n";
//				out += "ADDRESS = " + address + "\n";
//				out += "SALARY = " + salary + "\n";
//				out += "\n";
//			}
//			
//			
//			createTasksTable();
//			StringBuffer sb = new StringBuffer();
//			Task t = new Task(100, "HIT", CATEGORY.COMPANY, new ArrayList<Interval>(), new Interval(4,8), true, 2);
//			int num  = flushTaskToDB(t);
//			//int num = 0;
//			sb.append("\n" + " !!!insert " + num + " rows here" + "\n");
//			rs = stmt.executeQuery("SELECT * FROM TASK;");
//			while (rs.next()) { //(ID,EVENT,Catogory,ToDoIntervals,ScheduledInterval,DuringTime,Scheduled,Priority)
//				int id = rs.getInt("ID");
//				String name = rs.getString("EVENT");
//				String category = rs.getString("Catogory");
//				String scheduled = rs.getString("Scheduled");
//				sb.append("ID = " + id + "\n");
//				sb.append("NAME = " + name + "\n");
//				sb.append("category = " + category + "\n");
//				sb.append("Scheduled = " + scheduled + "\n");
//	
//				sb.append("\n");
//			}
//			
//			
//			resp.getWriter().print(out + "\n $$$ \n" + sb.toString());
//		} catch (Exception e) {
//			resp.getWriter().print("There was an error: " + e.getMessage());
//		} finally {
//			try {
//				stmt.close();
//				connection.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
	}

}
