package data;

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
	private CATEGORY catogory;
	private ArrayList<Long> startTimes;
	private long duringTime;
	private long finalStartTime;
	private boolean scheduled;
	private int priority;
	
	public Task(){
		scheduled = false;
	}
	
	public Task(int i, String e, CATEGORY c, ArrayList<Long> ds, long d, Long date, boolean s, int p) {
		this.id = i;
		this.event = e;
		this.catogory = c;
		this.startTimes = ds;
		this.duringTime = d;
		this.finalStartTime = date;
		this.scheduled = s;
	}
	
	public static Comparator<Task> PriorityComparator = new Comparator<Task>() {
		
		public int compare(Task task1, Task task2) {
			//ascending order
			return task1.getPriority() - task2.getPriority();
		}
		
	};
	
	public static Comparator<Task> DuringTimeComparator = new Comparator<Task>() {
		
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
		return catogory;
	}

	public void setCatogory(CATEGORY catogory) {
		this.catogory = catogory;
	}

	public ArrayList<Long> getStartTimes() {
		return startTimes;
	}

	public void setStartTimes(ArrayList<Long> startTimes) {
		this.startTimes = startTimes;
	}

	public long getDuringTime() {
		return duringTime;
	}

	public void setDuringTime(long duringTime) {
		this.duringTime = duringTime;
	}

	public Long getFinalStartTime() {
		return finalStartTime;
	}

	public void setFinalStartTime(Long finalStartTime) {
		this.finalStartTime = finalStartTime;
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
	
}
