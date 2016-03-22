package logon;

import org.openqa.selenium.WebDriver;

import core.ReflectionController;

public class Logon {
	
	private Boolean isLogged;
	
	public WebDriver driver;
	
	public Logon(){
		driver = ReflectionController.webDriver;
	}

	public Boolean login(){
		return true;
	}
	
	public Boolean logout(){
		return true;
	}
	
	public Boolean isLogged(){
		return isLogged;
	}
	
}
