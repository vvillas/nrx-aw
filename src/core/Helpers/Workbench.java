package core.Helpers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Workbench {
	
	public String label;
	public Long start;
	public Long end;
	
	public Workbench(String lbl){
		label = lbl;
		start = Calendar.getInstance().getTimeInMillis();
	}
	
	public void reStart(){
		start = Calendar.getInstance().getTimeInMillis();
	}
	
	public void finish(){
		end = Calendar.getInstance().getTimeInMillis();
	}
	
	public Long elapsedTime(){
		return end - start;
	}
	

}
