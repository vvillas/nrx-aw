package core.Model;

import java.util.Calendar;

import org.apache.xpath.operations.Bool;

import core.Helpers.Workbench;

public class ExecutionResult {

	private int ordm;
	private String label;
	private String user;
	private Boolean result;
	private String description;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Workbench workbench;
	
	public ExecutionResult(String lbl) {
		workbench = new Workbench(lbl);
		this.setLabel(lbl);
		setResult(false);
	}

	public int getOrdm() {
		return ordm;
	}

	public void setOrdm(int ordm) {
		this.ordm = ordm;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Boolean getResult() {
		return result;
	}

	private void setResult(Boolean result) {
		this.result = result;
	}
	
	public void finishWithSuccess(){
		this.workbench.finish();
		this.setResult(true);
		this.setDescription("");
	}
	
	public void finishWithFail(String description){
		this.workbench.finish();
		this.setResult(false);
		this.setDescription(description);
	}
	
}
