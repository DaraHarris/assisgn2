import javafx.application.Application;

public class ReadTxtFile extends FindKeywordGUI {

	@Override
	public void start(Stage primaryStage) throws Exception{
		FindKeywordGUI view = new FindKeywordGUI();
		DataModel model = new DataModel();
		Controller controller = new Controller(view, model);
		
		Scene scene = new Scene(view.asParent(), 1680, 900);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Find keyword from txt file");
		primaryStage.show();
	}

	public static void main(String[] args) {

		launch(args);
	}
}
