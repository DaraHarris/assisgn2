
import java.util.HashMap;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.*;

import org.xml.sax.SAXException;

public class AdditionModel{
	
	private File selectedFile;
	private HashMap<String, Integer> hMap;
	private HashMap<String, Integer> hMapForCorrelatedNumber;
	
	private DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	private DocumentBuilder docBuilder = null;
	private NodeList nodeList;
	
	public void catergorizeKeywordsToHashtable(File selectedFile) {
		
		try {
			docBuilder = factory.newDocumentBuilder();			
		} catch (ParserConfigurationException e) {	
			e.printStackTrace();
		}
		try {
			Document doc = docBuilder.parse(selectedFile);
			doc.getDocumentElement().normalize();
			getNodeListFromXML(doc.getDocumentElement());
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	//reference from https://stackoverflow.com/questions/5386991/java-most-efficient-method-to-iterate-over-all-elements-in-a-org-w3c-dom-docume
	//getlist of value for each node and update hash table key value pair. 
	public void getNodeListFromXML(Node node) {
		
		nodeList = node.getChildNodes();
	    for (int i = 0; i < nodeList.getLength(); i++) {
	        Node currentNode = nodeList.item(i);
	        if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
	            //calls this method for all the children which is Element
	        	getNodeListFromXML(currentNode);
	        	
	        } else {
	        	String value = currentNode.getNodeValue();
	        	if (value != null) {
	        		updateValueOfHashMap(value);
	        	}		
	        }
	    }
	}

	//update key:value pair in HashMap
	public void updateValueOfHashMap(String key) {
		
		hMap = new HashMap<String, Integer>();
		for (int i = 0; i< hMap.size();i++) {
			if(hMap.containsKey(key)) {
				int value = hMap.get(key)+1;
				hMap.put(key,value);
				System.out.print(hMap.get(key));
			} else {
				hMap.put(key,1);
				System.out.print(hMap.get(key));
			}
		}	
		
	}
	
	//GET DATA IN A TREE FORMAT OR INDENTION
	public static String getItemDataFromXML(Document xmlDoc, String keyword) {

		xmlDoc.getDocumentElement().normalize();
		NodeList nList = xmlDoc.getElementsByTagName("title");
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getTextContent().contains(keyword)) {
				return transferNodeToSring(nNode.getParentNode());
			}
		}
		return "Not Found";
	}
	//get data as per node
	public static String transferNodeToSring(Node node) {
		try {
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			DOMSource source = new DOMSource();
			StringWriter writer = new StringWriter();
			source.setNode(node);
			transformer.transform(source, new StreamResult(writer));
			return writer.toString();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String transferNodeListToSring(NodeList nList) {
		String xmlSection = " ";
		TransformerFactory tf = TransformerFactory.newInstance();
	    Transformer transformer;
	    DOMSource source = new DOMSource();
	    StringWriter writer = new StringWriter();
	    try {
			transformer = tf.newTransformer();
			for (int i = 0; i < nList.getLength(); ++i) { 
	    		 source.setNode(nList.item(i));
	    	     transformer.transform(source, new StreamResult(writer));
	    	     xmlSection = writer.toString();
	    	}
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return xmlSection = writer.toString();
	}
	
	//reference from https://howtodoinjava.com/xml/xml-to-string-write-xml-file/
	public static String writeXmlDocumentToXmlFile(Document xmlDocument){
		String xmlString = "";
	    TransformerFactory tf = TransformerFactory.newInstance();
	    Transformer transformer;
	    try {
	        transformer = tf.newTransformer();
	         
	        // Uncomment if you do not require XML declaration
	        // transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	   
	        //A character stream that collects its output in a string buffer, which can then be used to construct a string.
	        StringWriter writer = new StringWriter();

	        //transform document to string
	        transformer.transform(new DOMSource(xmlDocument), new StreamResult(writer));
	 
	        xmlString = writer.getBuffer().toString();   
	    }
	    catch (TransformerException e)
	    {
	        e.printStackTrace();
	    }
	    catch (Exception e)
	    {
	        e.printStackTrace();
	    }
		return  xmlString;
	}
	
	//reference from https://howtodoinjava.com/xml/xml-to-string-write-xml-file/
	public static Document convertXMLFileToXMLDocument(File selectedFile)
	{
	    //Parser that produces DOM object trees from XML content
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	     
	    //API to obtain DOM Document instance
	    DocumentBuilder builder = null;
	    try
	    {
	        //Create DocumentBuilder with default configuration
	        builder = factory.newDocumentBuilder();
	         
	        //Parse the content to Document object
	        Document xmlDocument = builder.parse(selectedFile);
	         
	        return xmlDocument;
	    }
	    catch (Exception e)
	    {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	//return frequency according to key
	public int getDataFromHashtable(String keyword) {	
		for(int i = 0; i < hMap.size();i++) {
			if(hMap.containsKey(keyword)) {
				hMap.get(keyword);
				break;
			}
		}
		return hMap.get(keyword);
	}
	
//	public copyDataToHashMapPerTitle() {
//		
//		
//	}

//	public HashMap copyDataToHashtableDescendingOrder(int correlatedNumber, String title) {
//		
//		hMapForCorrelatedNumber = new Hashtable<String, Integer>();
//		try {
//			for (int i= 0 ; i < correlatedNumber; i++) {
//				
//				if(htable.containsKey(title)) {
//					
//					int frequency = htable.get(title);
//					htableForCorrelatedNumber.put(title, frequency);
//					
//				}
//			}
//			
//		
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return htableForCorrelatedNumber;
//		
//		
//	}
//	
	
}
