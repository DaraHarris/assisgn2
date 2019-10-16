import java.io.File;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.*;

/**
 * FindKeywordGUI class is to create GUI for search keyword application.
 *
 * @author Sharmik Hirpara
 * @since 31/08/2019
 */

public class FindKeywordGUI extends Application {
	
	private static String methodName = "";                                             // For saving method name
	private static int buffSize = 0;                                                   // For saving buffer size
	private static String inputTXTFile = "";                                           // For saving input text file name
	private static String outputTXTFile = "";                                          // For saving output text file name
	private static String keyword = "";                                                // For saving keyword
	private static String[] keywords = null;										   // For saving multiple keyword 
    File selectedInputFile = new File("");											   // For saving details of input text file
    File selectedOutputFile = new File("");											   // For saving details of output text file
    
    DirectBuffer db = new DirectBuffer();											   // Object initialisation
	IndirectBuffer id = new IndirectBuffer();										   // Object initialisation
	BufferedIOStream bis = new BufferedIOStream();									   // Object initialisation
	ProgrammerManaged pmba = new ProgrammerManaged();								   // Object initialisation

	@Override  
    public void start(Stage primaryStage) throws Exception {

		Label inputLabel = new Label("Input file:");                                   // Input text file section - Lable Name
		inputLabel.setMinHeight(30);                                                   // Input text file section - Hight of lable
        TextField inputTextField= new TextField();                                     // Input text file section - Text field for file path
        Button btnInputFile = new Button("Browse");                                    // Input text file section - Button to select file from system
        
        Label outputLabel = new Label("Output file:");                                 // Output text file section - Lable Name
        outputLabel.setMinHeight(30);                                                  // Output text file section - Hight of lable
        TextField outputTextField= new TextField();                                    // Output text file section - Text field for file path
        Button btnOutputFile = new Button("Browse");                                   // Output text file section - Button to select file from system       

        Label keywordLabel = new Label("Keyword:");                                    // Keyword section - Lable Name
        keywordLabel.setMinHeight(30);                                                 // Keyword section - Hight of lable
        TextField keywordTextField= new TextField();                                   // Keyword section - Text field for file path
        
        Label bufferSizeLabel = new Label("Buffer Size:");                             // Buffer section - Lable Name
        bufferSizeLabel.setMinHeight(30);                                              // Buffer section - Hight of lable
        TextField bufferSizeTextField= new TextField();                                // Buffer section - Text field for file path

        VBox labels = new VBox();                                                      // Lable section - Create vertical box to add all lables
        labels.setSpacing(10);                                                         // Lable section - Set spacing between lables
        labels.setMinSize(80, 30);                                                     // Lable section - Set label minimum width and height 
        labels.getChildren().addAll(inputLabel, outputLabel, 
                                    keywordLabel, bufferSizeLabel);                    // Lable section - Add all lables in to lable section

        VBox textFields = new VBox();                                                  // Text field - Create vertical box to add all lables
        textFields.setSpacing(10);                                                     // Text field - Set spacing between lables
        textFields.setMinSize(120, 30);                                                // Text field - Set label minimum width and height   
        textFields.getChildren().addAll(inputTextField, outputTextField, 
                                        keywordTextField, bufferSizeTextField);        // Text field - Add all lables in to lable section

        VBox buttons = new VBox();                                                     // Buttons section- Create vertical box to add all lables                                        
        buttons.setSpacing(10);                                                        // Buttons section- Set spacing between lables                                      
        buttons.setMinSize(100, 30);                                                   // Buttons section- Set label minimum width and height                                                 
        buttons.getChildren().addAll(btnInputFile, btnOutputFile);                     // Buttons section- Add all lables in to lable section                                        
        
        HBox inputFields = new HBox();                                                 // Input field section - Create horizontal box to add all input sections                                                                              
        inputFields.setSpacing(20);                                                    // Input field section - Set spacing between adjecent fields                                                                                     
        inputFields.getChildren().addAll(labels, textFields, buttons);                 // Input field section - Add all fields in to input field section                                                                                
        
        ToggleGroup methods = new ToggleGroup();                                       // Method section - method group
        RadioButton directBufferBtn = new RadioButton("Direct Buffer");                // Method section - method option
		RadioButton indirectBufferBtn = new RadioButton("Indirect Buffer");            // Method section - method option
		RadioButton bufferIOBtn = new RadioButton("Buffer IO Stream");                 // Method section - method option
		RadioButton programmerManagedBtn = new RadioButton("Programmer Managed");      // Method section - method option
		directBufferBtn.setToggleGroup(methods);                                       // Method section - set toggle to select one option only from method group
		indirectBufferBtn.setToggleGroup(methods);                                     // Method section - set toggle to select one option only from method group
		bufferIOBtn.setToggleGroup(methods);                                           // Method section - set toggle to select one option only from method group
		programmerManagedBtn.setToggleGroup(methods);                                  // Method section - set toggle to select one option only from method group

		VBox radioButtons = new VBox();												   // Method section - Create vertical box to add all radio button section
        radioButtons.setSpacing(20);												   // Method section - Set spacing between fields
        radioButtons.getChildren().addAll(directBufferBtn, indirectBufferBtn, 
        								  bufferIOBtn, programmerManagedBtn);		   // Method section - Add all fields into method section

        Button searchBtn = new Button("Search");                                       // Search button
        Button saveOutput = new Button("Save Output");                                 // Save output in textfile button

        HBox buttonPanel = new HBox();												   // Buttons section - Create horizontal box to add all buttons
        buttonPanel.setSpacing(10);													   // Buttons section - Set spacing between buttons
        buttonPanel.getChildren().addAll(searchBtn, saveOutput);					   // Buttons section - Add all button into button section

        Label timeElapsedLabel = new Label("Elaspse time:");						   // Elapsed time section - Label name
        timeElapsedLabel.setMinHeight(30);											   // Elapsed time section - Set minimum height of lable
        TextField timeElapsedField= new TextField();								   // Elapsed time section - Textfield for displaying time elapsed
        timeElapsedField.setMaxWidth(150);											   // Elapsed time section - Set maximum width of textfield
        timeElapsedField.setAlignment(Pos.CENTER_RIGHT);							   // Elapsed time section - Set text alignment to right side 
        timeElapsedField.setEditable(false);										   // Elapsed time section - Make text field not editable
        Label timeUnit = new Label();												   // Elapsed time section - Lable name
        timeUnit.setMinHeight(30);													   // Elapsed time section - Set minimum height of lable
        timeUnit.setText("msec");													   // Elapsed time section - Set text into text field

        HBox timeMgmtFields = new HBox();											   // Elapsed time section - Create horizontal box to add time section
        timeMgmtFields.setSpacing(10);												   // Elapsed time section - Set spacing between fields
        timeMgmtFields.getChildren().addAll(timeElapsedLabel, 
        									timeElapsedField, timeUnit);			   // Elapsed time section - Add all fields to elapsed time section
        
        VBox inputArea = new VBox();												   // Input area - Create vertical box to add all input fields
        inputArea.setSpacing(30);													   // Input area - Set spacing between fields
        inputArea.setMinSize(300, 880);												   // Input area - Set minimum size of input area
        inputArea.getChildren().addAll(inputFields, radioButtons, 
        							   buttonPanel, timeMgmtFields);				   // Input area - Add all fields into input area
        
        Text inputTxtFileArea = new Text();											   // Input text file area - Craete text field for heading
        inputTxtFileArea.setText("Content of selected text file");					   // Input text file area - Set text 
        inputTxtFileArea.setFont(Font.font(30));									   // Input text file area - Set font size
        
        TextArea inputTextContent= new TextArea();									   // Input text file area - Create text area 
        inputTextContent.setMinSize(660, 810);										   // Input text file area - Set minimum size for text area
        inputTextContent.setBorder(new Border(new BorderStroke(Color.BLACK, 
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));   // Input text file area - Draw boarder to text area
        inputTextContent.setWrapText(true);											   // Input text file area - Set text wrapping mode enable
        inputTextContent.setEditable(false);										   // Input text file area - Diable text area to avoid editing
        
        VBox inputTXTFileHandling = new VBox(); 									   // Input text file area - Create vertical box for input text file area
        inputTXTFileHandling.setSpacing(10); 										   // Input text file area - Set spacing for fields
        inputTXTFileHandling.setMinSize(660, 890); 									   // Input text file area - Ser minimum size for input text area
        inputTXTFileHandling.setAlignment(Pos.TOP_CENTER); 							   // Input text file area - Set alignment
        inputTXTFileHandling.getChildren().addAll(inputTxtFileArea, inputTextContent); // Input text file area - Add all fields into input text file area
        
        Text outputTxtFileArea = new Text(); 										   // Output text file area - Craete text field for heading
        outputTxtFileArea.setText("Snippets of data which contains keyword"); 		   // Output text file area - Set text 
        outputTxtFileArea.setFont(Font.font(30)); 									   // Output text file area - Set font size
        
        TextArea outputTextContent= new TextArea(); 								   // Output text file area - Create text area 
        outputTextContent.setMinSize(660, 810); 									   // Output text file area - Set minimum size for text area
        outputTextContent.setBorder(new Border(new BorderStroke(Color.BLACK, 
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));   // Output text file area - Draw boarder to text area
        outputTextContent.setWrapText(true); 										   // Output text file area - Set text wrapping mode enable
        outputTextContent.setEditable(false); 										   // Output text file area - Diable text area to avoid editing

        VBox outputTXTFileHandling = new VBox(); 									   // Output text file area - Create vertical box for output text file area
        outputTXTFileHandling.setSpacing(10); 										   // Output text file area - Set spacing for fields
        outputTXTFileHandling.setMinSize(660, 890); 								   // Output text file area - Ser minimum size for output text area
        outputTXTFileHandling.setAlignment(Pos.TOP_CENTER); 						   // Output text file area - Set alignment
        outputTXTFileHandling.getChildren().addAll(outputTxtFileArea, 
        										   outputTextContent); 				   // Output text file area - Add all fields into output text file area

        Alert opeartionAlert = new Alert(AlertType.INFORMATION);					   // Alert section - Create alert with information type 
        opeartionAlert.setTitle("Save File");										   // Alert section - Set title of alert
        opeartionAlert.setHeaderText("Save operation Successful!");					   // Alert section - Set message 

        Alert errorAlert = new Alert(AlertType.ERROR);								   // Alert section - Create alert with error type
        errorAlert.setTitle("Error!");												   // Alert section - Set title of alert

        GridPane grdPane = new GridPane();											   // Main pane - Create grid pane for all areas
        grdPane.setHgap(10);														   // Main pane - Set horizontal gap between areas
        grdPane.setPadding(new Insets(10,10,10,10));								   // Main pane - Set padding

        grdPane.addColumn(0, inputArea);											   // Main pane - Add 1st area
        grdPane.addColumn(1, inputTXTFileHandling);									   // Main pane - Add 2nd area
        grdPane.addColumn(2, outputTXTFileHandling);								   // Main pane - Add 3rd area

        Scene scene = new Scene(grdPane, 1680, 900);								   // Scene - Create new window with size
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Find keyword from txt file");
        primaryStage.show();  

        btnInputFile.setOnAction(e->{												   // Event handler for input button to select text file
            FileChooser file = new FileChooser();      
            file.setTitle("Open File");
            selectedInputFile = file.showOpenDialog(primaryStage); 
            inputTXTFile = selectedInputFile.getPath();
            inputTextField.setText(inputTXTFile);
            BufferedIOStream bio = new BufferedIOStream();
            inputTextContent.setText(bio.ReadTxtFileString(inputTXTFile));
            
        });

        btnOutputFile.setOnAction(e->{												   // Event handler for output button to select text file
            FileChooser file = new FileChooser();      
            file.setTitle("Open File");
            selectedOutputFile = file.showOpenDialog(primaryStage);
            outputTXTFile = selectedOutputFile.getPath();
            outputTextField.setText(outputTXTFile);
        });

        searchBtn.setOnAction(e->{													   // Event handler for        
        	
        	if(inputTXTFile == "") {												   // Check if input text file is selected or not
        		errorAlert.setHeaderText("Please select input text file");
                errorAlert.show();
        	}
            
            if(keywordTextField.getText().length() != 0) {							   // Check if user has entered keywords ot not
                keyword = keywordTextField.getText();
                keywords = keyword.split(",");
            }
            else {
                errorAlert.setHeaderText("Please enter keywords");
                errorAlert.show();
            }
            
            if(bufferSizeTextField.getText().length() != 0)							   // Check if user has entered valid buffer size
            	buffSize = Integer.parseInt(bufferSizeTextField.getText());
            else if(buffSize <= 0){
            	errorAlert.setHeaderText("Please enter positive value for buffer size");
                errorAlert.show();
            }
            
            if(directBufferBtn.isSelected()) {										   // Check if user has selected one method
                methodName = "DirectBuffer";
            }
            else if(indirectBufferBtn.isSelected()) {
                methodName = "IndirectBuffer";
            }
            else if(bufferIOBtn.isSelected()) {
                methodName = "BufferedIOStream";
            }
            else if(programmerManagedBtn.isSelected()) {
                methodName = "ProgrammerManaged";
            }
            else {
            	errorAlert.setHeaderText("Please select method");
            	errorAlert.show();
            }
            
            if(inputTXTFile != "" && keyword != null && methodName != "" && buffSize > 0) {
                
            	System.out.println("Input text file path: " + inputTXTFile);
                System.out.println("Keyword: " + keyword);
                System.out.println("Method Name: " + methodName);
                System.out.println("Buffer Size: " + buffSize);
                
	            switch(methodName) {
					case "DirectBuffer":
						DirectBuffer.ReadDirectBuffer(buffSize, inputTXTFile, keywords);
						outputTextContent.setText(DirectBuffer.getOutput());
						timeElapsedField.setText(DirectBuffer.getElapsedTime());
						break;
					case "IndirectBuffer":
						IndirectBuffer.ReadIndirectBuffer(buffSize, inputTXTFile, keywords);
	                    outputTextContent.setText(IndirectBuffer.getOutput());
	                    timeElapsedField.setText(IndirectBuffer.getElapsedTime());
						break;
					case "BufferedIOStream":
						BufferedIOStream.ReadBufferedIOStream(buffSize, inputTXTFile, keywords);
						outputTextContent.setText(BufferedIOStream.getOutput());
						timeElapsedField.setText(BufferedIOStream.getElapsedTime());
						break;
					case "ProgrammerManaged":
						ProgrammerManaged.ReadProgrammerManaged(buffSize, inputTXTFile, outputTXTFile, keywords);
	                    outputTextContent.setText(ProgrammerManaged.getOutput());
	                    timeElapsedField.setText(ProgrammerManaged.getElapsedTime());
						break;
				}
            }
            
        });

        saveOutput.setOnAction(e->{

            if(outputTXTFile == "") {
            	errorAlert.setHeaderText("Please select output file");
            	errorAlert.show();
            }

            if(directBufferBtn.isSelected()) {
                methodName = "DirectBuffer";
            }
            else if(indirectBufferBtn.isSelected()) {
                methodName = "IndirectBuffer";
            }
            else if(bufferIOBtn.isSelected()) {
                methodName = "BufferedIOStream";
            }
            else if(programmerManagedBtn.isSelected()) {
                methodName = "ProgrammerManaged";
            }
            else {
            	errorAlert.setHeaderText("Please select method");
            	errorAlert.show();
            }

            if(outputTXTFile != "" && methodName != "") {
	            switch(methodName) {
	                case "DirectBuffer":
	                    DirectBuffer.saveToTextFile(outputTXTFile);
	                    opeartionAlert.show(); 
	                    break;
	                 case "IndirectBuffer":
	                    IndirectBuffer.saveToTextFile(outputTXTFile);
	                    opeartionAlert.show(); 
	                    break;
	                 case "BufferedIOStream":
	                    BufferedIOStream.saveToTextFile(outputTXTFile);
	                    opeartionAlert.show(); 
	                    break;
	                 case "ProgrammerManaged":
	                    ProgrammerManaged.saveToTextFile(outputTXTFile);
	                    opeartionAlert.show(); 
	                    break;
	            }
            }
        });
    }
}
