package MapViewer;

import java.awt.image.BufferedImage;

import com.neet.DiamondHunter.TileMap.TileMap;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Map {
	
	private TileMap m = new TileMap(16);
	private int numRows;
	private int numCols;
	private Canvas mapcanvas;

public void onLoadMap() {
	
	m.loadTiles("/Tilesets/testtileset.gif");
	m.loadMap("/Maps/testmap.map");
	
	int numRows = m.getNumRows();
	int numCols = m.getNumCols();
	
	drawMap(numRows, numCols);
	
}

public void drawMap(int row, int col) {

	
	for (row = 0; row < numRows; row++) {
		for (col = 0; col < numCols; col++) {
			
			BufferedImage rcImage = m.getSquaresImage(row, col);
			ImageView pictureview = new ImageView();
			Image finalrcImage = SwingFXUtils.toFXImage(rcImage, null);
			pictureview.setImage(finalrcImage);
			
	
		}
	}
}

}





			
			
			
			
			
			
