package tablemanager;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import datamodel.Interval;
import datamodel.Task;
import datamodel.Task.CATEGORY;

public class TaskTableManager {
	private static final String TASK_TABLE_NAME = "TASK";
	private static TaskTableManager _instance = null;
	
	public static TaskTableManager getInstance() {
		if(_instance == null) {
			_instance = new TaskTableManager() ;
		} 
		
		return _instance;
	}
	
	private TaskTableManager() {
		
	}
	
	private Connection getConnection() throws SQLException, URISyntaxException {
		URI dbUri = new URI(System.getenv("DATABASE_URL"));

		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();

		return DriverManager.getConnection(dbUrl, username, password);
	}
	
	public void createTable()  {
		Connection connection = null;
		Statement stmt = null;
		 
		
		try {
			connection = getConnection();
			stmt = connection.createStatement();
			
			String sql = "DROP TABLE IF EXISTS " + TASK_TABLE_NAME + ";";
			stmt.execute(sql);
			
			sql = "CREATE TABLE " + TASK_TABLE_NAME 
					+ " (ID INT PRIMARY KEY     NOT NULL,"
					+ " Event           TEXT    NOT NULL, "
					+ " Category            INT     NOT NULL, "
					+ " ToDoIntervals        TEXT, " 
					+ " ScheduledInterval         TEXT,"
					+ " Scheduled				boolean,"
					+ " Priority		INT"
					+ ");";
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public int writeTaskToDB(Task task) {
		Connection connection = null;
		Statement pStmt = null;
		int r = -2;
		try {
			connection = getConnection();
			pStmt = connection.createStatement();
			String sql = "INSERT INTO " + TASK_TABLE_NAME + 
							" (ID,EVENT,Category,ToDoIntervals,ScheduledInterval,Scheduled,Priority) "
							+ "VALUES ("
							+ task.getId()
							+ ", '"
							+ task.getEvent()
							+ "', "
							+ task.getCategory().ordinal()
							+ ", '"
							+ task.getTodoIntervals().toString()
							+ "', '"
							+ task.getScheduledInterval().toString()
							+ "', '"
							+ task.isScheduled()
							+ "' ,"
							+ task.getPriority()
							+ ");";
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
	
	public ArrayList<Task> readTaskTable() {
		Connection conn = null;
		Statement stmt = null;
		ArrayList<Task> tasks = new ArrayList<Task>();
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			
			String sql = "SELECT * FROM TASK;";
			
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) { //(ID,EVENT,Catogory,ToDoIntervals,ScheduledInterval,Scheduled,Priority)
				int id = rs.getInt("ID");
				String event = rs.getString("EVENT");
				int category = rs.getInt("Category");
				CATEGORY cate = CATEGORY.COMPANY;
				switch(category) {
				case 1:
					break;
				case 2:
					cate = CATEGORY.FAMILY;
					break;
				case 3:
					cate = CATEGORY.OTHER;
					break;
				default:
					break;
				}
				cate = CATEGORY.values()[category];
				
				String toDoIntervals = rs.getString("ToDoIntervals");
				ArrayList<Interval> inters = new ArrayList<Interval>();
				String[] ins = toDoIntervals.substring(1, toDoIntervals.length() - 1).split(",");
				for(String in : ins) {
					String[] parts = in.trim().split(":");
					Interval scheInterval = new Interval(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
					inters.add(scheInterval);
				}
				
				String scheduledInterval = rs.getString("ScheduledInterval");
				String[] parts = scheduledInterval.split(":");
				Interval scheInterval = new Interval(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
				boolean scheduled = rs.getBoolean("Scheduled");
				int priority = rs.getInt("Priority");
				
				Task t = new Task();
				t.setId(id);
				t.setEvent(event);
				t.setCategory(cate);
				t.setTodoIntervals(inters);
				t.setScheduledInterval(scheInterval);
				t.setScheduled(scheduled);
				t.setPriority(priority);
				
				tasks.add(t);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		return tasks;
	}
	
	public int updateTasks(ArrayList<Task> tasks) {
		Connection conn = null;
		Statement stmt = null;
		int rowNum = 0;
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			
			String sql = "";	
			
			for(Task t : tasks) {//ID,EVENT,Catogory,ToDoIntervals,ScheduledInterval,Scheduled,Priority
				sql = "update TASK "
					+ " set EVENT = '" + t.getEvent()
					+ "', CATEGORY = " + t.getCategory().ordinal()
					+ ", ToDoIntervals = '" + t.getTodoIntervals().toString()
					+ "', ScheduledInterval = '" + t.getScheduledInterval().toString()
					+ "', Scheduled = '" + t.isScheduled()
					+ "', Priority = " + t.getPriority()
					+ " where ID = " + t.getId() + " ;";		
			
				rowNum += stmt.executeUpdate(sql);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return rowNum;
	}
	
	
	public static void main(String[] args) {
		// 

	}

}
