package MapViewer;

import java.awt.image.BufferedImage;

import com.neet.DiamondHunter.TileMap.TileMap;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;


public class Controller {

	private TileMap m = new TileMap(16);
	@FXML Canvas mapcanvas;
	
	public Controller() {
		LoadMap();
	}
	
	public void initialize() {
		int numRows = m.getNumRows();
		int numCols = m.getNumCols();
		GraphicsContext g = mapcanvas.getGraphicsContext2D();
		drawMap(g, numRows, numCols);
	}
	
	
	private void LoadMap() {
		m.loadTiles("/Tilesets/testtileset.gif");
		m.loadMap("/Maps/testmap.map");
	}

	
	
	public void drawMap(GraphicsContext g, int numRows, int numCols) {
	
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				
				BufferedImage rcImage = m.getSquaresImage(row, col);
				Image tile = SwingFXUtils.toFXImage(rcImage, null);
				g.drawImage(tile, col * 16, row * 16);
			}
		}
	}
}
