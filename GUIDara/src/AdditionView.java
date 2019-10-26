import java.beans.EventHandler;
import java.io.File;

import org.w3c.dom.Document;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class AdditionView  {	
	
	private AdditionModel model;
	
	
	//tab content for XML tab
	private VBox vboxForXMLLoadTab = new VBox();
	private GridPane gridForXML = new GridPane();
	private Button btnToChooseXML = new Button("browse");
	private Button btnToLoadXML = new Button("load");
	private Label labelForTextAreaXML = new Label("Content for Chosen XML: ");
	private TextArea txtAreaForXML = new TextArea();
	
	//tab content for keyword search;
	private VBox vboxForKeywordSearch = new VBox();
	private GridPane gridForKeywordSearch = new GridPane();
	private TextField inputForKeyword = new TextField();
	private Button keywordSearchBtn = new Button("enter");
	private TextArea outputForKeywordSearch = new TextArea();
    
    //tab content for statistic chart;
	private VBox vboxForStatisticChart = new VBox();
	private VBox vboxForToggleGroup = new VBox();
	private ToggleGroup radioGroup = new ToggleGroup();
	private RadioButton radioButton1 = new RadioButton("Top 3-correlated keywords ");
	private RadioButton radioButton2 = new RadioButton("Top 5-correlated keywords");
	private RadioButton radioButton3 = new RadioButton("Top 8-correlated keywords");
	private RadioButton radioButton4 = new RadioButton("Top 10-correlated keywords");
	private Button barChartButton = new Button("Bar Chart");
	private Button pieChartButton = new Button("Pie Chart");
	private VBox vboxForChartButtons = new VBox();
	private PieChart pieChart = new PieChart();
	private TextField textfield = new TextField();
    
    //main tabpane;
	private TabPane tabPane = new TabPane();
	private VBox vbox= new VBox(tabPane);
	private Tab tabForXMLload = new Tab("XML File", vboxForXMLLoadTab);
	private Tab tabForDataSearch = new Tab("Keyword Search"  , vboxForKeywordSearch);
	private Tab tabForChart = new Tab("Statistics Chart" , vboxForStatisticChart);
	

	public AdditionView(){
		createAndConfigurePane();
		createAndLayoutControls();
		
		btnToChooseXML.setOnAction (e -> {
			AdditionController.browseFile(e);
			
		});
		
		btnToLoadXML.setOnAction( e -> {
			String output = AdditionController.parseXML(e);
			txtAreaForXML.setText(output);	
		});
		
		keywordSearchBtn.setOnAction( e -> {
			String keyword = inputForKeyword.getText();
			String sectionXML = AdditionController.getSectionXML(e, keyword);
			outputForKeywordSearch.setText(sectionXML);
		});
		
		barChartButton.setOnAction( e -> {
			//check if togglegroup is set; if not pop alertbox;
			//if yes/ get the value from radio button, hashtable then pass value to draw pie chart.
		});
		
		pieChartButton.setOnAction( e -> {
			
			
		});
		
		
		
	}
	

	

	public Parent asParent() {	
		return vbox;
	}

	private void createAndLayoutControls() {
		
		
		//scrollPanelForXML.setContent();	
		gridForXML.addRow(0, new Label("Choose XML file: "), btnToChooseXML, btnToLoadXML);
		vboxForXMLLoadTab.getChildren().add(0, gridForXML);
		vboxForXMLLoadTab.getChildren().add(1, labelForTextAreaXML);
		vboxForXMLLoadTab.getChildren().add(2, txtAreaForXML);
		
        //tab content for keyword search;
        gridForKeywordSearch.addRow(0, new Label("keyword: "),inputForKeyword,keywordSearchBtn);
        vboxForKeywordSearch.getChildren().add(0,gridForKeywordSearch);
        vboxForKeywordSearch.getChildren().add(1,outputForKeywordSearch);
        
        //tab content for statistic chart;
        radioButton1.setToggleGroup(radioGroup);
        radioButton2.setToggleGroup(radioGroup);
        radioButton3.setToggleGroup(radioGroup);
        radioButton4.setToggleGroup(radioGroup);
        vboxForToggleGroup.getChildren().addAll(radioButton1,radioButton2,radioButton3,radioButton4);
        vboxForChartButtons.getChildren().addAll(pieChartButton, barChartButton);
        vboxForStatisticChart.getChildren().add(0, vboxForToggleGroup);
        vboxForStatisticChart.getChildren().add(1, vboxForChartButtons);
        vboxForStatisticChart.getChildren().add(2, textfield);
        
        //add tabs to main tab pane
		tabPane.getTabs().add(tabForXMLload);
        tabPane.getTabs().add(tabForDataSearch);
        tabPane.getTabs().add(tabForChart);
	}

	private void createAndConfigurePane() {
		
	}

	
	
//	private String getCorreleatedKeywordSelection() {
//		RadioButton selectedRadioButton = (RadioButton)radioGroup.getSelectedToggle();
//		String toggleGroupValue = selectedRadioButton.getText();
//		return toggleGroupValue;
//	}
	

	
}
