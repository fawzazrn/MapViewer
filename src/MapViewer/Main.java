package MapViewer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent; 
import javafx.event.EventHandler; 


public class Main extends Application{
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			BorderPane pane = new BorderPane();
			pane = FXMLLoader.load(getClass().getResource("View.fxml"));
			
			Scene scene = new Scene(pane);

		    primaryStage.setScene(scene);
		    primaryStage.show();
			primaryStage.setTitle("Map-Viewer");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
			
		} 
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
