package datamodel;

import java.util.ArrayList;
import java.util.Comparator;

public class Task {

	public enum CATEGORY {
		COMPANY,
		FAMILY,
		OTHER
	}
	
//	public enum PRIORITY {
//		HIGH,
//		MEDIUM,
//		LOW
//	}
	
	private int id;
	private String event;
	private CATEGORY category;
	private ArrayList<Interval> todoIntervals;
	private Interval scheduledInterval;
	private boolean scheduled;
	private int priority;
	
//	@Override
//	public String toString() {
//		StringBuffer sb = new StringBuffer();
//		sb.append("ID = " + id + "\n");
//		sb.append("EVENT = " + event + "\n");
//		sb.append("CATEGORY = " + category + "\n");
//		sb.append("todoIntervals = " + todoIntervals.toString() + "\n");
//		sb.append("scheduledInterval = " + scheduledInterval.toString() + "\n");
//		sb.append("scheduled = " + scheduled + "\n");
//		sb.append("priority = " + priority + "\n");
//		
//		return sb.toString();
//	}
	
	public Task(){
		scheduled = false;
		priority = 1;
	}
	
	public Task(int i, String e, CATEGORY c, ArrayList<Interval> ds, Interval in, boolean s, int p) {
		this.id = i;
		this.event = e;
		this.category = c;
		this.todoIntervals = ds;
		this.scheduledInterval = in;
		this.scheduled = s;
		this.priority = p;
	}
	
	public String toString(){
		return scheduled + "; interval: " + scheduledInterval;
	}
	
	public static class Comparators {
		public static Comparator<Task> ScheduledTaskStartTime = new Comparator<Task>() {
			
			public int compare(Task task1, Task task2) {
				long startTime1 = task1.getScheduledInterval().start;
				long startTime2 = task2.getScheduledInterval().start;
				if(startTime1 > startTime2)
					return 1;
				else if(startTime1 < startTime2)
					return -1;
				else
					return 0;
			}
			
		};
		
		public static Comparator<Task> Priority = new Comparator<Task>() {
			
			public int compare(Task task1, Task task2) {
				//ascending order
				return task1.getPriority() - task2.getPriority();
			}
			
		};
		
		public static Comparator<Task> DuringTime = new Comparator<Task>() {
			
			public int compare(Task task1, Task task2) {
				long duringTime1 = task1.getDuringTime();
				long duringTime2 = task2.getDuringTime();
				if(duringTime1 > duringTime2)
					return 1;
				else if(duringTime1 < duringTime2)
					return -1;
				else
					return 0;
			}
			
		};
	}
	
	/**
	 * here assume the during time for the same task is the same
	 * @return
	 */
	public long getDuringTime(){
		return todoIntervals.get(0).end - todoIntervals.get(0).start;
	}
	
	public static Comparator<Task> startTimeComparator = new Comparator<Task>() {
		
		public int compare(Task task1, Task task2) {
			return 0;
		}
		
	};

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public CATEGORY getCatogory() {
		return category;
	}

	public void setCatogory(CATEGORY catogory) {
		this.category = catogory;
	}

	public ArrayList<Interval> getTodoIntervals() {
		return todoIntervals;
	}

	public void setTodoIntervals(ArrayList<Interval> todoIntervals) {
		this.todoIntervals = todoIntervals;
	}

	public Interval getScheduledInterval() {
		return scheduledInterval;
	}

	public void setScheduledInterval(Interval scheduledInterval) {
		this.scheduledInterval = scheduledInterval;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public boolean isScheduled() {
		return scheduled;
	}

	public void setScheduled(boolean scheduled) {
		this.scheduled = scheduled;
	}
	
	
	public static void main(String[] args) {
		ArrayList<Interval> inters = new ArrayList<Interval>();
		inters.add(new Interval(0, 1));
		inters.add(new Interval(2, 3));
		inters.add(new Interval(5, 9));
		
		System.out.println(inters);
	}
}
