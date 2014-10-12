package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeMap;

import datamodel.Interval;
import datamodel.Task;

public class Scheduler {
	
	public static ArrayList<Task> scheduledTasks;
	
	public Scheduler(ArrayList<Task> scheduledTasks){
		Scheduler.scheduledTasks = scheduledTasks;
		Collections.sort(Scheduler.scheduledTasks, Task.Comparators.ScheduledTaskStartTime);
	}
	
	public static HashMap<Integer, ArrayList<Task>> partitionTaskByPriority(ArrayList<Task> todoTasks){
		HashMap<Integer, ArrayList<Task>> paritions = new HashMap<Integer, ArrayList<Task>>();
		for(int i = 0; i < 3; i++){
			ArrayList<Task> partition = new ArrayList<Task>();
			paritions.put(i, partition);
		}
		for(Task task : todoTasks)
			paritions.get(task.getPriority()).add(task);
		return paritions;
	}
	
	public static TreeMap<Long, ArrayList<Task>> partitionTaskByStartTime(ArrayList<Task> todoTasks){
		TreeMap<Long, ArrayList<Task>> partitions = new TreeMap<Long, ArrayList<Task>>();
		for(Task task : todoTasks){
			for(Interval interval : task.getTodoIntervals()){
				long startTime = interval.start;
				ArrayList<Task> partition;
				if(!partitions.containsKey(startTime)){
					partition = new ArrayList<Task>();
					partitions.put(startTime, partition);
				} else
					partition = partitions.get(startTime);
				partition.add(task);
			}
		}
		return partitions;
	}
	
	public static boolean checkIfConflict(long startTime, long endTime, ArrayList<Task> scheduledTasks){
		for(Task scheduledTask : scheduledTasks){
			Interval interval = scheduledTask.getScheduledInterval();
			if((startTime >= interval.start && startTime <= interval.end)
					|| (endTime >= interval.start && endTime <= interval.end))
				return true;
		}
		return false;
	}
	
	public static ArrayList<Task> schedule(ArrayList<Task> todoTasks){
		ArrayList<Task> scheduledTaskThisTime = new ArrayList<Task>();
		// 1. partition todoTask by priority
		HashMap<Integer, ArrayList<Task>> partitionsByPriority = partitionTaskByPriority(todoTasks);
		
		// 2. for tasks with the same priority, partition todoTask by startTime
		for(int i = 2; i >= 0; i--){
			ArrayList<Task> paritionByPriority = partitionsByPriority.get(i);
			TreeMap<Long, ArrayList<Task>> partitionsByStartTime = partitionTaskByStartTime(paritionByPriority);
			for(Long startTime : partitionsByStartTime.keySet()){
				ArrayList<Task> partitionByStartTime = partitionsByStartTime.get(startTime);
				// 3. for tacks with same priority and startTime, sort by durtingTime
				Collections.sort(partitionByStartTime, Task.Comparators.DuringTime);
				for(Task task : partitionByStartTime){
					if(task.isScheduled()){
						partitionByStartTime.remove(task);
						continue;
					} 
					// 4. check if this task can by scheduled in current calendar
					if(!checkIfConflict(startTime, startTime + task.getDuringTime(), Scheduler.scheduledTasks)){
						// 5.if not conflict, schedule todoTask to scheduledTask
						task.setScheduled(true);
						task.setScheduledInterval(new Interval(startTime, startTime + task.getDuringTime()));
						scheduledTaskThisTime.add(task);
						todoTasks.remove(task);
					}
				}
			}
		}
		return scheduledTaskThisTime;
	}
}
