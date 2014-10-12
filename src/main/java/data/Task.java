package data;

import java.sql.Date;
import java.util.ArrayList;

public class Task {
	public enum CATEGORY {
		COMPANY,
		FAMILY,
		OTHER
	}
	
	public enum PRIORITY {
		HIGH,
		MEDIUM,
		LOW
	}
	
	private int id;
	private String event;
	private CATEGORY catogory;
	private ArrayList<Long> startTimes;
	private long duringTime;
	private long finalStartTime;
	private boolean scheduled;
	private PRIORITY priority;
	
	public Task(){
		scheduled = false;
	}
	
	public Task(int i, String e, CATEGORY c, ArrayList<Long> ds, long d, Long date, boolean s, PRIORITY p) {
		this.id = i;
		this.event = e;
		this.catogory = c;
		this.startTimes = ds;
		this.duringTime = d;
		this.finalStartTime = date;
		this.scheduled = s;
		this.priority = p;
	}

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

	public PRIORITY getPriority() {
		return priority;
	}

	public void setPriority(PRIORITY priority) {
		this.priority = priority;
	}

	public boolean isScheduled() {
		return scheduled;
	}

	public void setScheduled(boolean scheduled) {
		this.scheduled = scheduled;
	}
	
}
