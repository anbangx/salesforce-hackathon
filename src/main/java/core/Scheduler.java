package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeMap;

import datamodel.Interval;
import datamodel.Task;

public class Scheduler {
	
	public static ArrayList<Task> scheduledTasks = null;
	
	public static void init(ArrayList<Task> scheduledTasks){
		Scheduler.scheduledTasks = scheduledTasks;
		Collections.sort(Scheduler.scheduledTasks, Task.Comparators.ScheduledTaskStartTime);
	}
	
	public static HashMap<Integer, ArrayList<Task>> partitionTaskByPriority(ArrayList<Task> todoTasks){
		HashMap<Integer, ArrayList<Task>> paritions = new HashMap<Integer, ArrayList<Task>>();
		for(int i = 1; i <= 3; i++){
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
			if((startTime >= interval.start && startTime < interval.end)
					|| (endTime > interval.start && endTime <= interval.end))
				return true;
		}
		return false;
	}
	
	public static ArrayList<Task> schedule(ArrayList<Task> todoTasks){
		ArrayList<Task> scheduledTaskThisTime = new ArrayList<Task>();
		// 1. partition todoTask by priority
		HashMap<Integer, ArrayList<Task>> partitionsByPriority = partitionTaskByPriority(todoTasks);
		
		// 2. for tasks with the same priority, partition todoTask by startTime
		for(int i = 3; i >= 1; i--){
			ArrayList<Task> paritionByPriority = partitionsByPriority.get(i);
			TreeMap<Long, ArrayList<Task>> partitionsByStartTime = partitionTaskByStartTime(paritionByPriority);
			for(Long startTime : partitionsByStartTime.keySet()){
				ArrayList<Task> partitionByStartTime = partitionsByStartTime.get(startTime);
				// 3. for tacks with same priority and startTime, sort by durtingTime
				Collections.sort(partitionByStartTime, Task.Comparators.DuringTime);
				for(Task task : partitionByStartTime){
					if(task.isScheduled()){
						continue;
					} 
					// 4. check if this task can be scheduled in current calendar
					if(!checkIfConflict(startTime, startTime + task.getDuringTime(), Scheduler.scheduledTasks)){
						// 5.if not conflict, schedule todoTask to scheduledTask
						task.setScheduled(true);
						task.setScheduledInterval(new Interval(startTime, startTime + task.getDuringTime()));
						scheduledTaskThisTime.add(task);
						scheduledTasks.add(task);
						todoTasks.remove(task);
					}
				}
			}
		}
		return scheduledTaskThisTime;
	}
	
	public static void test1(){
		System.out.println("Test1 - simple test.....");
		// Test1 - simple test
		Task task1 = new Task();
		ArrayList<Interval> intervals1 = new ArrayList<Interval>();
		intervals1.add(new Interval(0, 2));
		task1.setTodoIntervals(intervals1);
		
		Task task2 = new Task();
		ArrayList<Interval> intervals2 = new ArrayList<Interval>();
		intervals2.add(new Interval(3, 5));
		task2.setTodoIntervals(intervals2);
		
		Task task3 = new Task();
		ArrayList<Interval> intervals3 = new ArrayList<Interval>();
		intervals3.add(new Interval(5, 9));
		task3.setTodoIntervals(intervals3);
		
		ArrayList<Task> todoTasks = new ArrayList<Task>();
		todoTasks.add(task1);
		todoTasks.add(task2);
		todoTasks.add(task3);
		
		Scheduler.init(new ArrayList<Task>());
		ArrayList<Task> returnScheduledTasks = Scheduler.schedule(todoTasks);
		System.out.println("returnScheduledTasks: " + returnScheduledTasks);
		System.out.println("todoTasks: " + todoTasks);
			
	}
	
	public static void test2(){
		System.out.println("Test2 - test priority.....");
		// Test2 - test priority
		Task task1 = new Task();
		ArrayList<Interval> intervals1 = new ArrayList<Interval>();
		intervals1.add(new Interval(0, 2));
		task1.setTodoIntervals(intervals1);
		task1.setPriority(1);
		
		Task task2 = new Task();
		ArrayList<Interval> intervals2 = new ArrayList<Interval>();
		intervals2.add(new Interval(2, 4));
		task2.setTodoIntervals(intervals2);
		task2.setPriority(3);
		
		Task task3 = new Task();
		ArrayList<Interval> intervals3 = new ArrayList<Interval>();
		intervals3.add(new Interval(1, 3));
		task3.setTodoIntervals(intervals3);
		task3.setPriority(2);
		
		ArrayList<Task> todoTasks = new ArrayList<Task>();
		todoTasks.add(task1);
		todoTasks.add(task2);
		todoTasks.add(task3);
		
		Scheduler.init(new ArrayList<Task>());
		ArrayList<Task> returnScheduledTasks = Scheduler.schedule(todoTasks);
		System.out.println("returnScheduledTasks: " + returnScheduledTasks);
		System.out.println("todoTasks: " + todoTasks);
	}
	
	public static void test3(){
		System.out.println("Test3 - test startTime.....");
		// Test3 - test startTime
		Task task1 = new Task();
		ArrayList<Interval> intervals1 = new ArrayList<Interval>();
		intervals1.add(new Interval(0, 2));
		task1.setTodoIntervals(intervals1);
		task1.setPriority(1);
		
		Task task2 = new Task();
		ArrayList<Interval> intervals2 = new ArrayList<Interval>();
		intervals2.add(new Interval(1, 3));
		task2.setTodoIntervals(intervals2);
		task2.setPriority(1);
		
		ArrayList<Task> todoTasks = new ArrayList<Task>();
		todoTasks.add(task1);
		todoTasks.add(task2);
		
		Scheduler.init(new ArrayList<Task>());
		ArrayList<Task> returnScheduledTasks = Scheduler.schedule(todoTasks);
		System.out.println("returnScheduledTasks: " + returnScheduledTasks);
		System.out.println("todoTasks: " + todoTasks);
	}
	
	public static void test4(){
		System.out.println("Test4 - test duringTime.....");
		// Test4 - test duringTime
		Task task1 = new Task();
		ArrayList<Interval> intervals1 = new ArrayList<Interval>();
		intervals1.add(new Interval(0, 2));
		task1.setTodoIntervals(intervals1);
		task1.setPriority(1);
		
		Task task2 = new Task();
		ArrayList<Interval> intervals2 = new ArrayList<Interval>();
		intervals2.add(new Interval(0, 3));
		task2.setTodoIntervals(intervals2);
		task2.setPriority(1);
		
		ArrayList<Task> todoTasks = new ArrayList<Task>();
		todoTasks.add(task1);
		todoTasks.add(task2);
		
		Scheduler.init(new ArrayList<Task>());
		ArrayList<Task> returnScheduledTasks = Scheduler.schedule(todoTasks);
		System.out.println("returnScheduledTasks: " + returnScheduledTasks);
		System.out.println("todoTasks: " + todoTasks);
	}
	
	public static void test5(){
		System.out.println("Test5 - test multiple time slots in one task.....");
		// Test5 - test priority
		Task task1 = new Task();
		ArrayList<Interval> intervals1 = new ArrayList<Interval>();
		intervals1.add(new Interval(0, 1));
		intervals1.add(new Interval(1, 2));
		intervals1.add(new Interval(2, 3));
		task1.setTodoIntervals(intervals1);
		task1.setPriority(1);
		
		Task task2 = new Task();
		ArrayList<Interval> intervals2 = new ArrayList<Interval>();
		intervals2.add(new Interval(0, 2));
		intervals2.add(new Interval(2, 4));
		intervals2.add(new Interval(4, 6));
		task2.setTodoIntervals(intervals2);
		task2.setPriority(2);
		
		ArrayList<Task> todoTasks = new ArrayList<Task>();
		todoTasks.add(task1);
		todoTasks.add(task2);
		
		Scheduler.init(new ArrayList<Task>());
		ArrayList<Task> returnScheduledTasks = Scheduler.schedule(todoTasks);
		System.out.println("returnScheduledTasks: " + returnScheduledTasks);
		System.out.println("todoTasks: " + todoTasks);
	}
	
	public static void main(String [ ] args){
		test1();
		test2();
		test3();
		test4();
		test5();
	}
}
