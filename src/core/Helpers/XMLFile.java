package core.Helpers;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class XMLFile {

	NodeList nList;

	public Boolean parse(String filePath) {

		try {

			File xmlFile = new File(filePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			nList = doc.getElementsByTagName("elem");

			/*
			 * for (int temp = 0; temp < nList.getLength(); temp++) {
			 * 
			 * Node nNode = nList.item(temp);
			 * 
			 * System.out.println("\nCurrent Element :" + nNode.getNodeName());
			 * 
			 * if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			 * 
			 * Element eElement = (Element) nNode;
			 * 
			 * System.out.println("Staff id : " + eElement.getAttribute("id"));
			 * } }
			 */

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	public Element getElement(String id) {

		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;

				if (eElement.getAttribute("id").equals(id)) {
					return eElement;
				}
			}
		}
		
		System.out.println("Elemento não encontrado: "+ id);

		return null;

	}

}
