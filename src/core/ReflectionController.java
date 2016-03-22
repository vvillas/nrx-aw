/**
 * 
 */
package core;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.*;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.swing.JOptionPane;

import org.apache.http.util.Args;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.ie.*;
import org.w3c.dom.Element;

import core.Helpers.Report;
import core.Helpers.Timing;
import core.Helpers.Workbench;
import core.Helpers.XMLFile;
import core.Model.ExecutionModel;
import core.Model.ExecutionResult;

/**
 * @author Victor Miranda
 * 
 */
public class ReflectionController {

	public static List<List<String>> mass;
	public static WebDriver webDriver;
	public static List<ExecutionResult> result;
	public static ExecutionModel execModel;
	public static XMLFile xmlLocators;

	public static void main(String args[]) throws Exception {
		ReflectionController rc = new ReflectionController();
		rc.exec(args);
	}

	public Boolean exec(String[] args) throws IOException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException, InvocationTargetException {

		/*
		 * reflectMethod, // [0] metodo a ser chamado massFile, // [1] arquivo
		 * de massa de dados reportFile, // [2] arquivo de relatorio
		 * locatorsFile, // [3] arquivo de Locators webDriver, // [4] webDriver
		 * a ser invocado (FireFox) targetURL // [5] URL inicial username // [6]
		 * password // [7]
		 */

		execModel = new ExecutionModel(args);
		if(execModel.isValid()){

			// Prepara os parametros para execução via Reflection
			

			MainAutomation.sendMessage(new String[] {
					Timing.nowTime("HH:mm:ss"), "Automation Start" });

			MainAutomation.sendMessage(new String[] { "Script",
					execModel.getMethodName() });
			MainAutomation.sendMessage(new String[] { "WebDriver",
					execModel.getWebDriver() });

			// Carregar arquivo de Massa de Dados
			collectCSV(execModel.getMassFile());

			// Carregar o arquivo XML de Locators
			xmlLocators = new XMLFile();
			if (!xmlLocators.parse(execModel.getLocatorsFile())) {
				System.err.println("[Fatal Error] XML File: "
						+ execModel.getLocatorsFile() + " parsing error");
				return false;
			}

			// Carrega o Driver
			switch (execModel.getWebDriver()) {
			case "FireFox":
				webDriver = new FirefoxDriver();
				break;
			case "IE":
				webDriver = new InternetExplorerDriver();
				break;
			case "Chrome":
				webDriver = new ChromeDriver();
				break;
			default:
				webDriver = new FirefoxDriver();
				break;
			}

			// Execução da Rotina Automatica
			try {
				execReflection();
			} catch (NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}

			// Geração do Relatorio
			String filePath = execModel.getReportFile();

			//

			webDriver.close();

			//
			Report.setReport(result, filePath);

		}

		// Processo de finalização
		endAutomation();
		return true;

	}

	@SuppressWarnings("rawtypes")
	public static void execReflection() throws NoSuchMethodException,
			SecurityException, InstantiationException {

		List<ExecutionResult> vRes = new ArrayList<ExecutionResult>();

		if (!execModel.isValid()) {
			System.out
					.println(String
							.format("[Critical Error] execReflection method is missing params%n"));
		}
		else{

			try {
	
				Class<?> c = Class.forName(execModel.getMethodName());
				Object t = c.newInstance();
				Class[] argTypes = new Class[] { String[].class };
				Method method = c.getDeclaredMethod("run", argTypes);
				ExecutionResult res;
				int i = 1;
				// Realiza um chamada para cada registro da massa de dados
				for (List<String> line : mass) {
	
					System.out.println("[" + i + "] Executando Rotina Automática");
	
					String[] mainArgs = Arrays.copyOfRange(
							(String[]) line.toArray(), 0, line.size());
	
					// System.out.format("invoking %s.main()%n", c.getName());
					res = (ExecutionResult) method.invoke(t, (Object) mainArgs);
	
					vRes.add(res);
					
					i++;
				}
	
				result = vRes;
	
				// production code should handle these exceptions more gracefully
			} catch (ClassNotFoundException x) {
				x.printStackTrace();
			} catch (NoSuchMethodException x) {
				x.printStackTrace();
			} catch (IllegalAccessException x) {
				x.printStackTrace();
			} catch (InvocationTargetException x) {
				x.printStackTrace();
			}

		}
	}

	// Metodo antigo para execução por Reflection
	/*
	 * private static List<ExecutionResult> testReflectionAndCall(String
	 * className) throws ClassNotFoundException, InstantiationException,
	 * IllegalAccessException, InvocationTargetException {
	 * 
	 * if (!classExists(className)) {
	 * 
	 * System.err.println(className);
	 * System.err.println("ERROR: Reflection Execution - Class \"" + className +
	 * "\" NOT Found - " + Workbench.nowTime()); return null; }
	 * 
	 * Class<?> cls = Class.forName(className); Object icls = cls.newInstance();
	 * System.out.println("Reflection Execution - Started Class \"" +
	 * cls.getName() + "\" - " + Workbench.nowTime());
	 * 
	 * Method method = null;
	 * 
	 * try { // method = cls.getMethod("run", (Class<?>[]) null); method =
	 * cls.getDeclaredMethod("run", new Class[] {});
	 * 
	 * } catch (Exception e) { // handle exception
	 * JOptionPane.showMessageDialog(null,
	 * "ERROR: Automation Execution Method [run] not found for class: " +
	 * cls.getName()); }
	 * 
	 * if (method == null) { return null; } else {
	 * 
	 * System.out.println("Reflection Execution - Started Script \"" +
	 * method.getName() + "\" - " + Workbench.nowTime());
	 * 
	 * List<ExecutionResult> v_result = (List<ExecutionResult>) method
	 * .invoke(icls, execModel); // invokeMethod(icls, method);
	 * 
	 * if (v_result != null) { for (ExecutionResult res : v_result) {
	 * System.out.println(res.ordm + ": " + res.label); }
	 * 
	 * return v_result; } }
	 * 
	 * return null; }
	 */

	private static Boolean collectCSV(String filePath) throws IOException {

		try {

			FileReader fileReader = new FileReader(filePath);
			BufferedReader br = new BufferedReader(fileReader);
			List<List<String>> dataArr = new ArrayList<List<String>>();

			try {

				StringBuilder sb = new StringBuilder();
				String line = br.readLine();

				while (line != null) {
					sb.append(line);
					sb.append('\n');
					dataArr.add(Arrays.asList(line.split(";")));
					line = br.readLine();
				}

			} finally {
				br.close();
			}

			mass = dataArr;

		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			return false;
		}

		return true;

	}

	private static void endAutomation() {
		// Auto-generated method stub
		System.out.println("End Automation - " + Timing.nowTime());
	}

	public static boolean classExists(String className) {
		try {
			Class.forName(className);
			return true;
		} catch (ClassNotFoundException ex) {
			return false;
		}
	}

}
