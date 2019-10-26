import java.beans.EventHandler;
import java.io.File;

import org.w3c.dom.Document;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class AdditionView  {	
	
	
	private AdditionModel model;
	
	//tab content for XML tab
	private VBox vboxForXMLLoadTab = new VBox();
	private HBox hboxForXML = new HBox();
	private TextField pathToXML = new TextField();
	private Button btnToChooseXML = new Button("Browse");
	private Button btnToLoadXML = new Button("Load");
	private Label labelForTextAreaXML = new Label("XML file content");
	private TextArea txtAreaForXML = new TextArea();
	
	//tab content for keyword search;
	private VBox vboxForKeywordSearch = new VBox();
	private HBox hboxForKeywordSearch = new HBox();
	private TextField inputForKeyword = new TextField();
	private Button keywordSearchBtn = new Button("Search");
	private Label labelForKeywordSearch = new Label("Result");
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
	private HBox hboxForChartButtons = new HBox();
	private PieChart pieChart = new PieChart();
	private TextField chartField = new TextField();
	
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
		
		//reference from :https://www.geeksforgeeks.org/javafx-radiobutton-with-examples/
		radioGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			
        	public void changed(ObservableValue<? extends Toggle> ov,Toggle old_toggle, Toggle new_toggle) {
        		
    	        if (radioGroup.getSelectedToggle() != null) {
    	        	RadioButton radioBtnSelected = (RadioButton)radioGroup.getSelectedToggle();
    	        } 
        	}
        });
		
		barChartButton.setOnAction( e -> {
			if (radioGroup.getSelectedToggle() == null){
				Alert alert = new Alert(AlertType.NONE); 
				alert.setAlertType(AlertType.ERROR); 
				alert.setContentText("please choose a correlated range.");
				alert.show();	
			} else {
		
//				String selectedRange = getCorreleatedKeywordSelection();
//				Alert alert = new Alert(AlertType.NONE); 
//				alert.setAlertType(AlertType.CONFIRMATION); 
//				String confirmText = getCorreleatedKeywordSelection();
//				alert.setContentText("you chose: " + confirmText);
//				alert.show();
//				int rangeValue;
//				switch (selectedRange){
//				case "Top 3-correlated keywords":
//					rangeValue = 3;
//					BarChartView barChart = new BarChartView();
//					
//					
//					break;
//				case "Top 5-correlated keywords":
//					rangeValue = 5;
//					break;
//				case "Top 8-correlated keywords":
//					rangeValue = 8;
//					break;	
//				case "Top 10-correlated keywords":
//					rangeValue = 10;
//					break;
//				}
			}	
		});
		
		pieChartButton.setOnAction( e -> {
			if (radioGroup.getSelectedToggle() == null){
				Alert alert = new Alert(AlertType.NONE); 
				alert.setAlertType(AlertType.ERROR); 
				alert.setContentText("please choose a correlated range.");
				alert.show();
				
			} else {
				String selectedRange = getCorreleatedKeywordSelection();
				Alert alert = new Alert(AlertType.NONE); 
				alert.setAlertType(AlertType.CONFIRMATION); 
				String confirmText = getCorreleatedKeywordSelection();
				alert.setContentText("you chose: " + confirmText);
				alert.show();
				int rangeValue;
				switch (selectedRange){
				case "Top 3-correlated keywords":
					rangeValue = 3;
					
					break;
				case "Top 5-correlated keywords":
					rangeValue = 5;
					break;
				case "Top 8-correlated keywords":
					rangeValue = 8;
					break;	
				case "Top 10-correlated keywords":
					rangeValue = 10;
					break;
				}	
			}		
		});		
	}
	
	public PieChart addPieChart() {
		ObservableList<PieChart.Data> pieChartData =
        FXCollections.observableArrayList(
        new PieChart.Data("Grapefruit", 13),
        new PieChart.Data("Oranges", 25),
        new PieChart.Data("Plums", 10),
        new PieChart.Data("Pears", 22),
        new PieChart.Data("Apples", 30));
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Imported Fruits");
        return chart;
		
		
	}
	public void addBarChart() {
		
	}

	public Parent asParent() {	
		return vbox;
	}

	private String getCorreleatedKeywordSelection() {
		RadioButton selectedRadioButton = (RadioButton)radioGroup.getSelectedToggle();
		String toggleGroupValue = selectedRadioButton.getText();
		return toggleGroupValue;
	}
	
	private void createAndLayoutControls() {
		//tab content for XML tab;	
		hboxForXML.getChildren().addAll(new Label("Choose XML file: "), pathToXML, btnToChooseXML, btnToLoadXML);
		vboxForXMLLoadTab.getChildren().addAll(hboxForXML, labelForTextAreaXML, txtAreaForXML);
		
        //tab content for keyword search;
        hboxForKeywordSearch.getChildren().addAll(new Label("Keyword: "), inputForKeyword, keywordSearchBtn);
        vboxForKeywordSearch.getChildren().addAll(hboxForKeywordSearch, labelForKeywordSearch, outputForKeywordSearch);
        
        //tab content for statistic chart;
        radioButton1.setToggleGroup(radioGroup);
        radioButton2.setToggleGroup(radioGroup);
        radioButton3.setToggleGroup(radioGroup);
        radioButton4.setToggleGroup(radioGroup);
        vboxForToggleGroup.getChildren().addAll(radioButton1,radioButton2,radioButton3,radioButton4);
        hboxForChartButtons.getChildren().addAll(pieChartButton, barChartButton);
        vboxForStatisticChart.getChildren().addAll(vboxForToggleGroup, hboxForChartButtons, chartField);
        
        //add tabs to main tab pane
        tabPane.getTabs().addAll(tabForXMLload, tabForDataSearch, tabForChart);
        
	}

	private void createAndConfigurePane() {

		//configuration for XML tab;
		hboxForXML.setMinHeight(40);
		hboxForXML.setSpacing(10);
		hboxForXML.setAlignment(Pos.CENTER_LEFT);
		
		labelForTextAreaXML.setFont(Font.font(30));
		labelForTextAreaXML.setAlignment(Pos.CENTER);
		labelForTextAreaXML.setMaxSize(1080, 50);

		txtAreaForXML.setMinSize(1060, 635);
		txtAreaForXML.setWrapText(true);
		txtAreaForXML.setEditable(false);
		
		vboxForXMLLoadTab.setSpacing(10);
		vboxForXMLLoadTab.setPadding(new Insets(10,10,10,10));
		
		//configuration for keyword search tab;
		hboxForKeywordSearch.setMinHeight(40);
		hboxForKeywordSearch.setSpacing(10);
		hboxForKeywordSearch.setAlignment(Pos.CENTER_LEFT);
		
		labelForKeywordSearch.setFont(Font.font(30));
		labelForKeywordSearch.setAlignment(Pos.CENTER);
		labelForKeywordSearch.setMaxSize(1080, 50);
		
		outputForKeywordSearch.setMinSize(1060, 635);
		outputForKeywordSearch.setWrapText(true);
		outputForKeywordSearch.setEditable(false);
		
		vboxForKeywordSearch.setSpacing(10);
		vboxForKeywordSearch.setPadding(new Insets(10,10,10,10));
		
		//configuration for statistic chart tab;
		vboxForToggleGroup.setSpacing(10);
		
		hboxForChartButtons.setSpacing(10);
		
		vboxForStatisticChart.setPadding(new Insets(10,10,10,10));
		vboxForStatisticChart.setSpacing(10);
		
		chartField.setMinSize(1060, 575);
		
	}
	
}
