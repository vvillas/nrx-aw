package core.Helpers;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import core.Model.ExecutionResult;

public class Report {

	public static void setReport(List<ExecutionResult> v_result, String filePath)
			throws IOException {

		System.out.println("Report Start - " + Timing.nowTime());

		/*
		 * BufferedReader br = new BufferedReader(new FileReader(
		 * "ShowGeneratedHtml.java"));
		 */

		File f = new File(filePath);
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		bw.write("<html>");
		bw.write("<body style=\"font: 13px/1.236 Verdana, Arial, Lucida Grande, sans-serif;width: 778px;\">");
		bw.write("<h1>Relat&oacute;rio de Execu&ccedil;&atilde;o de Rotina Autom&aacute;tica</h1>");
		bw.write("<p><strong>Nome do Script: </strong>"+v_result.get(0).getLabel()+"</p>");
		bw.write("<p><strong>Usu&aacute;rio: </strong>"+v_result.get(0).getUser()+"</p>");
		bw.write("<table style=\"border: 1px solid #CCC; background-color:#FFF; width:100%; font-size:11px; border-spacing: 1px;\"><thead>"
				+ "<tr style=\"border-color: #FFF; background-color: #CCC;height: 21px;color: white;\">"
				+ "<th>n&ordm;</th><th>In&iacute;cio</th><th>T&eacute;rmino</th><th>Tempo Total</th><th>Resultado</th><th>Descri&ccedil;&atilde;o</th>"
				+ "</tr>" + "</thead><tbody>");

		if (v_result.size() > 0) {
			String styleColor = "";
			String stResult = "";
			ExecutionResult res;
			for (Integer n = 0; n < v_result.size(); n++) {
				res = v_result.get(n);
				if(res.getResult()){
					styleColor = "background-color: #DED;";
					stResult = "SUCESSO";
				}
				else{
					styleColor = "background-color: #EDD;";
					stResult = "FALHOU";
				}
				
				// bw.write(res.label);
				bw.write("<tr style=\"border-color: #FFF; "+styleColor+" height: 21px;\">"
						+ "<td>" + n.toString() + "</td>"
						+ "<td>" + Timing.nowTime(res.workbench.start) + "</td>"
						+ "<td>" + Timing.nowTime(res.workbench.end) + "</td>"
						+ "<td>" + Timing.nowTime("mm:ss.SSS", res.workbench.elapsedTime()) + "</td>"
						+ "<td>" + stResult + "</td>"
						+ "<td>" + res.getDescription() + "</td>"
						+ "</tr>");
			}
		}

		bw.write("</tbody></table>");
		bw.write("</body>");
		bw.write("</html>");

		// br.close();
		bw.close();

		// Desktop.getDesktop().browse(f.toURI());
		Desktop.getDesktop().open(new File(filePath));

	}

}
