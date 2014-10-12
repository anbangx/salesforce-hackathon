package datamodel;

public class Interval {
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.start + ":" + this.end;
	}



	public long start;
	public long end;
	
	
	
	public Interval(long start, long end){
		this.start = start;
		this.end = end;
	}
}
