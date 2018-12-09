package MapViewer;

import java.awt.image.BufferedImage;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import com.neet.DiamondHunter.Manager.Content;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class ControllerAssistent {

	public void DisplayLabel(Label OutputConsole,String message) {
		OutputConsole.setText(message);
	}
	
	public void PositionTest(int Col,int Row,Label OutputConsole,String message,GridPane grid) {
		Pane pane = new Pane();
		grid.add(pane, Col, Row);
		
		//Event handler to detect the mouse clicked on the pane
		pane.setOnMouseClicked(e -> {
			DisplayLabel(OutputConsole,message);
		});
	}
	public void GenAxe (GridPane grid,int Col,int Row) {
		HBox IMG = new HBox();
		BufferedImage Axe = Content.ITEMS[1][1];	//Getting Boat tile from ITEMS array in Content class
		Image AxeIMG = SwingFXUtils.toFXImage(Axe, null);
		
		IMG.setAlignment(Pos.CENTER);	//
		grid.add(IMG, Col, Row);
		
		IMG.getChildren().add(new ImageView(AxeIMG));
	}
	
	public void GenBoat (GridPane grid,int Col,int Row) {
		HBox IMG = new HBox();
		BufferedImage Boat = Content.ITEMS[1][0];	//Getting Axe tile from ITEMS array in Content class
		Image BoatIMG = SwingFXUtils.toFXImage(Boat, null);
		
		IMG.setAlignment(Pos.CENTER);	
		grid.add(IMG, Col, Row);
		
		IMG.getChildren().add(new ImageView(BoatIMG));
	}
	
	public void Pos2File(String path,int Row,int Col) {
		try {
			File file = new File(path);
			
			if(!file.exists()) {
				
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			
			PrintStream ps = new PrintStream(path);
			ps.println(Row);
			ps.println(Col);
			ps.close();
		}
		catch (IOException x) {
			System.out.println("Error: " + x);
			x.printStackTrace();
		}
	}
	
	
}
