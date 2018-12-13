package MapViewer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class Main extends Application{
	public static Stage ps;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		ps = primaryStage;
		BorderPane pane = new BorderPane();
		pane = FXMLLoader.load(getClass().getResource("View.fxml"));
		
		Platform.setImplicitExit(false);
		
		Scene scene = new Scene(pane);

	    ps.setScene(scene);
		ps.setTitle("Map-Viewer");
		ps.setScene(scene);
		ps.setResizable(false);
		ps.show();
		
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
