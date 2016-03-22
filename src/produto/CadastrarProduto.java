package produto;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import core.ReflectionController;
import core.Model.ExecutionResult;
import core.Model.IAutomationScript;

public class CadastrarProduto extends IAutomationScript {
	
	public CadastrarProduto(){
		driver = ReflectionController.webDriver;
		locators = ReflectionController.xmlLocators;
	}

	public ExecutionResult run(String[] params) {
		
		ExecutionResult exec = new ExecutionResult(this.getClass().getName());
		
		// Go to the Google Suggest home page
		driver.get("http://www.uol.com.br");
		
		WebElement el;
		
		try{
		
			el = getElement("usuario");
			el.clear();
			el.sendKeys("victorvillasboas");
			
			
			
			el = getElement("password");
			el.clear();
			el.sendKeys("z010203");
			
			
			
			el = getElement("submit");
			el.click();
			
			
			
			if(!driver.getCurrentUrl().equals("http://mail.uol.com.br/main")){
				exec.finishWithFail("URL INCORRECT: "+ driver.getCurrentUrl());
				return exec;
			}
			
			
	
			/*
			 * // Enter the query string "Cheese" 
			 * WebElement query = webDriver.findElement(By.name("q"));
			 * 
			 * query.sendKeys("Cheese"); query.sendKeys(Keys.RETURN);
			 * 
			 * // Sleep until the div we want is visible or 5 seconds is over long
			 * end = System.currentTimeMillis() + 5000; while
			 * (System.currentTimeMillis() < end) { WebElement resultsDiv =
			 * webDriver.findElement(By.className("gssb_e"));
			 * 
			 * // If results have been returned, the results are displayed in a drop
			 * down. if (resultsDiv.isDisplayed()) { break; } }
			 */
			// And now list the suggestions
			// List<WebElement> allSuggestions =
			// webDriver.findElements(By.xpath("//i[@class='sprite-titulos']"));
	
			// Close the webDriver
			//webDriver.close();
	
			/*
			 * for (WebElement suggestion : allSuggestions) {
			 * System.out.println(suggestion.getText()); }
			 */
	
			// String [][] res =
			// {{"1","Executado com sucesso!"},{"2","Segunda saida de dados!"},
			// {"3","Page title is: " + webDriver.getTitle()}};
		
			
			
		}
		catch(Exception e){
			exec.finishWithFail(e.getLocalizedMessage());
			return exec;
		}
		
		exec.finishWithSuccess();
		return exec;

	}

}
