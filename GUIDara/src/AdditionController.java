import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;


public class AdditionController implements EventHandler<Event>{
	private static AdditionView view ;
	private static AdditionModel model ;
	private static Stage primaryStage;
	private KeyWordSearchApp app;
	private static File selectedFile;
	
	public AdditionController(AdditionView view, AdditionModel model, Stage primaryStage) {
		this.view = view ;
		this.model = model;
		this.primaryStage = primaryStage;
	}
	
	//get section of XML file according to the input keyword;
	public static String getSectionXML(ActionEvent xmlParse, String keyword) {
		Document xmlDocument = AdditionModel.convertXMLFileToXMLDocument(selectedFile);
		String result = AdditionModel.getItemDataFromXML(xmlDocument, keyword);
		
		return result;
	}
	
	//parse XML to text;tested
	public static String parseXML(ActionEvent xmlParse) {
		//show file browsing window;
		Document xmlDocument = model.convertXMLFileToXMLDocument(selectedFile);
		AdditionModel.writeXmlDocumentToXmlFile(xmlDocument);
		String result= model.writeXmlDocumentToXmlFile(xmlDocument);
		
		return result;
	}
	
	//browse file btn pressed; tested
	public static String browseFile(ActionEvent browseEvent) {
		
		FileChooser file = new FileChooser();      
        file.setTitle("Open XML File");
        selectedFile = file.showOpenDialog(primaryStage); 
        String filePath = selectedFile.getPath();
		return filePath;
	}

//	public static HashMap<String, int> getChartData(int rangeValue){
//		
//	}
	
	@Override
	public void handle(Event event) {

	}
	public static File getFilePath() {
		
		return selectedFile;
	}	
}
