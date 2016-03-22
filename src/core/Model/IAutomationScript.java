package core.Model;

import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Element;

import core.Helpers.XMLFile;

public abstract class IAutomationScript {
	
	protected WebDriver driver;
	protected XMLFile locators;
	
	protected abstract ExecutionResult run(String[] params);
	
	public WebElement getElement(String id){
		
		Element el = locators.getElement(id);
		WebElement wel = null;
		
		switch (el.getAttribute("by")) {
			case "name" : 
				wel = driver.findElement(By.name(el.getAttribute("value")));
			break;
			case "id" : 
				wel = driver.findElement(By.id(el.getAttribute("value")));
			break;
			case "url":
				wel = driver.findElement(By.linkText(el.getAttribute("value")));
			break;
			case "xpath":
				wel = driver.findElement(By.xpath(el.getAttribute("value")));
			break;

			default:
				System.err.println(
						"Não foi possível buscar o elemento: " + id + "\n"
						+ "Referência de locators deve possuir o parametro \"by\" "
						+ "com um dos seguintes valores [name] [id] [xpath] [url]");
			break;
		}
		
		return wel;
		
	}
	
}
