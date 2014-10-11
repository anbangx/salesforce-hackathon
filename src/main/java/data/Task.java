package data;

import java.sql.Date;
import java.util.ArrayList;

public class Task {
	
	private int id;
	private String event;
	private String catogory;
	private ArrayList<Date> startTimes;
	private long duringTime;
	private Date finalStartTime;
	private boolean scheduled;
	
	public Task(){
		scheduled = false;
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

	public String getCatogory() {
		return catogory;
	}

	public void setCatogory(String catogory) {
		this.catogory = catogory;
	}

	public ArrayList<Date> getStartTimes() {
		return startTimes;
	}

	public void setStartTimes(ArrayList<Date> startTimes) {
		this.startTimes = startTimes;
	}

	public long getDuringTime() {
		return duringTime;
	}

	public void setDuringTime(long duringTime) {
		this.duringTime = duringTime;
	}

	public Date getFinalStartTime() {
		return finalStartTime;
	}

	public void setFinalStartTime(Date finalStartTime) {
		this.finalStartTime = finalStartTime;
	}

	public boolean isScheduled() {
		return scheduled;
	}

	public void setScheduled(boolean scheduled) {
		this.scheduled = scheduled;
	}
	
}
