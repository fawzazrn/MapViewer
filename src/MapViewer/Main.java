package MapViewer;

import com.neet.DiamondHunter.TileMap.TileMap;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;


public class Main extends Application{
	
	private TileMap tilemap;
	private Map map;
	private FXMLLoader loader;
	
	public void start(Stage primaryStage) throws Exception {
		
		Stage stage = new Stage();
		tilemap = new TileMap(16);
	
		Parent content = FXMLLoader.load(getClass().getResource("View.fxml"));
		
		stage.setScene(new Scene(content));
		stage.setTitle("Map Viewer");
		stage.setResizable(false);
		stage.show();
		
		
	}

	
	
	
	public static void main(String[] args) {
		launch(args);
	}

}
