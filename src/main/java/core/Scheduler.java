package core;

import java.sql.Date;
import java.util.ArrayList;

import data.Interval;
import data.Task;

public class Scheduler {
	
	public ArrayList<Interval> scheduledTasks;
	
	public Scheduler(ArrayList<Interval> scheduledTasks){
		this.scheduledTasks = scheduledTasks;
	}
	
	public static void schedule(ArrayList<Task> todoTasks, Date startTime, Date endTime){
		// sort by priority
		
		// sort by during time
		
		// sort by start time
	}
}
