package core;

import java.sql.Date;
import java.util.ArrayList;

import javax.measure.quantity.Duration;

import datamodel.Interval;
import datamodel.Task;

public class Scheduler {
	
	public ArrayList<Interval> scheduledIntervals;
	
//	public Scheduler(ArrayList<Task> scheduledTasks){
//		this.scheduledIntervals = tasksToInterval(scheduledTasks, true);
//	}
//	
//	public static ArrayList<Interval> tasksToInterval(ArrayList<Task> tasks, boolean scheduled){
//		ArrayList<Interval> intervals = new ArrayList<Interval>();
//		for(Task task : tasks){
//			if(task.isScheduled() == scheduled){
//				long finalStartTime = task.getFinalStartTime();
//				Interval interval = new Interval(finalStartTime, finalStartTime + task.getDuringTime());
//				intervals.add(interval);
//			} else{
//				for(Long startTime : task.getStartTimes()){
//					Interval interval = new Interval(startTime, startTime + task.getDuringTime());
//					intervals.add(interval);
//				}
//			}
//		}
//		return intervals;
//	}
//	
//	public static void schedule(ArrayList<Task> todoTasks, Date startTime, Date endTime){
//		// get todoIntervals
//		ArrayList<Interval> todoIntervals = tasksToInterval(todoTasks, false); 
//				
//		// sort by priority
//		
//		// sort by during time
//		
//		// sort by start time
//	}
}
