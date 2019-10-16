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
import javafx.Scene.Parent;

public class FindKeywordGUI extends Application {

	private static String methodName = "";				// For saving method name
	private static int buffSize = 0;					// For saving buffer size
	private static String inputTXTFile = "";			// For saving input text file name
	private static String outputTXTFile = "";			// For saving output text file name
	private static String keyword;					// For saving keyword
	File selectedInputFile = new File("");
  File selectedOutputFile = new File("");
  DirectBuffer db = new DirectBuffer();
	IndirectBuffer id = new IndirectBuffer();
	BufferedIOStream bis = new BufferedIOStream();
	ProgrammerManaged pmba = new ProgrammerManaged();

	Label inputLabel;
	TextField inputTextField;
	Button btnInputFile;
	Label outputLabel;
	TextField outputTextField;
	Button btnOutputFile;
	Label keywordLabel;
	TextField keywordTextField;
	Label bufferSizeLabel;
	TextField bufferSizeTextField;
	RadioButton directBufferBtn;
	RadioButton indirectBufferBtn;
	RadioButton bufferIOBtn;
	RadioButton programmerManagedBtn;
	Button searchBtn;
	Button saveOutput;
	HBox buttonPanel;
	Label timeElapsedLabel;
	TextField timeElapsedField;
	Label timeUnit;
	HBox timeMgmtFields;
	VBox labels;
	VBox textFields;
	VBox buttons;
	HBox inputFields;
	VBox radioButtons;
	VBox inputArea;
	Text inputTxtFileArea;
	TextArea inputTextContent;
	VBox inputTXTFileHandling;
	Text outputTxtFileArea;
  TextArea outputTextContent;
	VBox outputTXTFileHandling;
	GridPane grdPane;

	public class FindKeywordGUI(){
		createPaneLayout();
		createLayoutControl();
	}
	public Parent asParent(){
		return grdPane;
	}

	public createPaneLayout(){
		GridPane grdPane = new GridPane();
		inputLabel.setMinHeight(30);
		outputLabel.setMinHeight(30);
		keywordLabel.setMinHeight(30);
		bufferSizeLabel.setMinHeight(30);
		buttonPanel.setSpacing(10);
		timeElapsedLabel.setMinHeight(30);
		timeElapsedField.setMaxWidth(150);
    timeElapsedField.setAlignment(Pos.CENTER_RIGHT);
    timeElapsedField.setEditable(false);
		timeUnit.setMinHeight(30);
	  timeMgmtFields.setSpacing(10);
		labels.setSpacing(10);
		labels.setMinSize(80, 30);
		textFields.setSpacing(10);
		textFields.setMinSize(120, 30);
		buttons.setSpacing(10);
    buttons.setMinSize(100, 30);
		inputFields.setSpacing(20);
		radioButtons.setSpacing(20);
		inputArea.setSpacing(30);
		inputArea.setMinSize(300, 880);
		inputTextContent.setMinSize(660, 810);
		inputTXTFileHandling.setSpacing(10);
    inputTXTFileHandling.setMinSize(660, 890);
    inputTXTFileHandling.setAlignment(Pos.TOP_CENTER);
		outputTextContent.setMinSize(660, 810);
    outputTextContent.setBorder(new Border(new BorderStroke(Color.BLACK,
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    outputTextContent.setWrapText(true);
    outputTextContent.setEditable(false);
		outputTXTFileHandling.setSpacing(10);
    outputTXTFileHandling.setMinSize(660, 890);
    outputTXTFileHandling.setAlignment(Pos.TOP_CENTER);
		grdPane.setHgap(10);
    grdPane.setPadding(new Insets(10,10,10,10));





	}

	public createLayoutControl(){
		Label inputLabel = new Label("Input file:");
    TextField inputTextField= new TextField();
    Button btnInputFile = new Button("Browse");

		Label outputLabel = new Label("Output file:");
    TextField outputTextField= new TextField();
    Button btnOutputFile = new Button("Browse");

		Label keywordLabel = new Label("Keyword:");
    TextField keywordTextField= new TextField();

		Label bufferSizeLabel = new Label("Buffer Size:");
    TextField bufferSizeTextField= new TextField();

		RadioButton directBufferBtn = new RadioButton("Direct Buffer");
		RadioButton indirectBufferBtn = new RadioButton("Indirect Buffer");
		RadioButton bufferIOBtn = new RadioButton("Buffer IO Stream");
		RadioButton programmerManagedBtn = new RadioButton("Programmer Managed");
		ToggleGroup methods = new ToggleGroup();
		directBufferBtn.setToggleGroup(methods);
		indirectBufferBtn.setToggleGroup(methods);
		bufferIOBtn.setToggleGroup(methods);
		programmerManagedBtn.setToggleGroup(methods);

		Button searchBtn = new Button("Search");
    Button saveOutput = new Button("Save Output");
		HBox buttonPanel = new HBox();
    buttonPanel.getChildren().addAll(searchBtn, saveOutput);

		Label timeElapsedLabel = new Label("Elaspse time:");
    TextField timeElapsedField= new TextField();
    Label timeUnit = new Label();
    timeUnit.setText("msec");
    HBox timeMgmtFields = new HBox();
    timeMgmtFields.getChildren().addAll(timeElapsedLabel, timeElapsedField, timeUnit);

		VBox labels = new VBox();
    labels.getChildren().addAll(inputLabel, outputLabel, keywordLabel, bufferSizeLabel);

		VBox textFields = new VBox();
    textFields.getChildren().addAll(inputTextField, outputTextField, keywordTextField, bufferSizeTextField);

		VBox buttons = new VBox();
    buttons.getChildren().addAll(btnInputFile, btnOutputFile);

		HBox inputFields = new HBox();
    inputFields.getChildren().addAll(labels, textFields, buttons);

		VBox radioButtons = new VBox();
		radioButtons.getChildren().addAll(directBufferBtn, indirectBufferBtn, bufferIOBtn, programmerManagedBtn);

		VBox inputArea = new VBox();
    inputArea.getChildren().addAll(inputFields, radioButtons, buttonPanel, timeMgmtFields);

    Text inputTxtFileArea = new Text();
    inputTxtFileArea.setText("Content of selected text file");
    inputTxtFileArea.setFont(Font.font(30));

    TextArea inputTextContent= new TextArea();
    inputTextContent.setBorder(new Border(new BorderStroke(Color.BLACK,
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    inputTextContent.setWrapText(true);
    inputTextContent.setEditable(false);

    VBox inputTXTFileHandling = new VBox();
    inputTXTFileHandling.getChildren().addAll(inputTxtFileArea, inputTextContent);

    Text outputTxtFileArea = new Text();
    outputTxtFileArea.setText("Snippets of data which contains keyword");
    outputTxtFileArea.setFont(Font.font(30));

    TextArea outputTextContent= new TextArea();

    VBox outputTXTFileHandling = new VBox();
    outputTXTFileHandling.getChildren().addAll(outputTxtFileArea, outputTextContent);

    GridPane grdPane = new GridPane();
    grdPane.addColumn(0, inputArea);
    grdPane.addColumn(1, inputTXTFileHandling);
    grdPane.addColumn(2, outputTXTFileHandling);


	}

		public Parent asParent(){
			return grdPane;
		}

    Alert opeartionAlert = new Alert(AlertType.INFORMATION);
    opeartionAlert.setHeaderText("Save operation Successful!");
    opeartionAlert.setTitle("Save File");

    Alert errorAlert = new Alert(AlertType.ERROR);
    errorAlert.setTitle("Error!");

    btnInputFile.setOnAction(e->{
        FileChooser file = new FileChooser();
        file.setTitle("Open File");
        selectedInputFile = file.showOpenDialog(primaryStage);
        inputTXTFile = selectedInputFile.getPath();
        inputTextField.setText(inputTXTFile);
        BufferedIOStream bio = new BufferedIOStream();
        inputTextContent.setText(bio.ReadTxtFileString(inputTXTFile));

    });

    btnOutputFile.setOnAction(e->{
        FileChooser file = new FileChooser();
        file.setTitle("Open File");
        selectedOutputFile = file.showOpenDialog(primaryStage);
        outputTXTFile = selectedOutputFile.getPath();
        outputTextField.setText(outputTXTFile);
    });

    searchBtn.setOnAction(e->{

    	String[] keywords = null;

        if(keywordTextField.getText().length() != 0) {
            keyword = keywordTextField.getText();
            keywords = keyword.split(",");
        }
        else {
            errorAlert.setHeaderText("Please enter keywords");
            errorAlert.show();
        }

        if(keywordTextField.getText().length() != 0) {
        	if(bufferSizeTextField.getText().matches("[0-9]+"))
        		buffSize = Integer.parseInt(bufferSizeTextField.getText());
        	else
        		buffSize = 128;
        }

        if(inputTXTFile == "")
            inputTXTFile = "english.txt";

        if(outputTXTFile == "")
            outputTXTFile = "englishOut.txt";

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

        System.out.println("Input text file path: " + inputTXTFile);
        System.out.println("Output text file path: " + outputTXTFile);
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
		default:
			DirectBuffer.ReadDirectBuffer(buffSize, inputTXTFile, keywords);
			break;
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

        if(outputTXTFile != "" && methodName == "") {
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
