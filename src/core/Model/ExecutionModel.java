package core.Model;

public class ExecutionModel {

	private String methodName; // metodo a ser chamado
	private String massFile; // arquivo de massa de dados
	private String reportFile; // arquivo de relatorio
	private String locatorsFile; // arquivo de Locators
	private String webDriver; // webDriver a ser invocado (FireFox)
	private String targetURL; // URL inicial
	private String userLogin;
	private String userPassword;
	private Boolean loginOnEatch;
	
	private Boolean boValid = false;

	public ExecutionModel(String[] params) {

		try {
			
			setMethodName(params[0]);
			setMassFile(params[1]);
			setReportFile(params[2]);
			setLocatorsFile(params[3]);
			setWebDriver(params[4]);
			setTargetURL(params[5]);
			setUserLogin(params[6]);
			setUserPassword(params[7]);
			setLoginOnEatch(params[8]);
			
			boValid = true;
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String reflectMethod) {
		this.methodName = reflectMethod;
	}

	public String getMassFile() {
		return massFile;
	}

	public void setMassFile(String massFile) {
		this.massFile = massFile;
	}

	public String getReportFile() {
		return reportFile;
	}

	public void setReportFile(String reportFile) {
		this.reportFile = reportFile;
	}

	public String getLocatorsFile() {
		return locatorsFile;
	}

	public void setLocatorsFile(String locatorsFile) {
		this.locatorsFile = locatorsFile;
	}

	public String getWebDriver() {
		return webDriver;
	}

	public void setWebDriver(String webDriver) {
		this.webDriver = webDriver;
	}

	public String getTargetURL() {
		return targetURL;
	}

	public void setTargetURL(String targetURL) {
		this.targetURL = targetURL;
	}
	
	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Boolean getLoginOnEatch() {
		return loginOnEatch;
	}

	public void setLoginOnEatch(String loginOnEatch) {
		System.out.println(loginOnEatch);
		if(loginOnEatch.equals("true")){
			this.loginOnEatch = true;
		}
		else{
			this.loginOnEatch = false;
		}
	}

	public Boolean isValid(){
		return boValid;
	}

}
