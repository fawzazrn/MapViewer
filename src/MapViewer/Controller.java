package MapViewer;

import com.neet.DiamondHunter.TileMap.TileMap;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Controller {

	private Map map;
	private TileMap m;
	private Canvas mapcanvas;
	private GraphicsContext graphics;
	
	public Controller() {
		
		map = new Map();
		
	}
	
	public void initialize() {
	
		graphics = mapcanvas.getGraphicsContext2D();
		map.drawMap(m.getNumRows(), m.getNumCols());
		
		
	}
}
