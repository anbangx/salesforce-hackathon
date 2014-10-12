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
		
		/*
		 * The following code test insert, update operations on TASK table
		 * 
		ArrayList<Interval> ds = new ArrayList<Interval>();
		ds.add(new Interval(5, 9));
		Task t1 = new Task(101, "SMS", CATEGORY.FAMILY, ds, new Interval(1,3), true, 1);
		Task t2 = new Task(102, "TDS", CATEGORY.OTHER, ds, new Interval(7,8), false, 2);
		Task t3 = new Task(109, "PBS", CATEGORY.OTHER, ds, new Interval(17,32), true, 4);
		taskManager.writeTaskToDB(t1);
		taskManager.writeTaskToDB(t2);
		taskManager.writeTaskToDB(t3);
		
		ArrayList<Task> tasks = taskManager.readAllTasks();
		for(Task task : tasks) {
			resp.getWriter().println(task.toString());
		}
		
		resp.getWriter().println();
		resp.getWriter().println();
		
		tasks = taskManager.readUnscheduledTasks();
		tasks.get(0).setCategory(CATEGORY.COMPANY);
		tasks.get(0).setEvent("BYE");
		
		ArrayList<Task> ls = new ArrayList<Task>();
		ls.add(tasks.get(0));
		int rn = -10;
		rn = taskManager.updateTasks(ls);
		resp.getWriter().println("!!!! row num updated = " + rn);
		tasks = taskManager.readAllTasks();
		for(Task task : tasks) {
			resp.getWriter().println(task.toString());
		}		
		
		*/
		

	}

}
