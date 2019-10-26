import java.io.File;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class KeyWordSearchApp extends Application {
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		AdditionModel model = new AdditionModel();
		AdditionView view = new AdditionView();
		AdditionController controller = new AdditionController(view, model,primaryStage);
		
		Scene scene = new Scene(view.asParent(),1000,800);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Keyword Search Ranking");
		primaryStage.show();
		
		File selectedFile = new File("imdb.xml");
		model.catergorizeKeywordsToHashtable(selectedFile);
//		File selectedFile = new File("imdb.xml");
//		model.loadDataFromXML(selectedFile);
	
	}

	public static void main(String[] args) {
		launch(args);

	}


}
