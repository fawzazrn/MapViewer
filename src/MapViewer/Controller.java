package MapViewer;

import java.awt.image.BufferedImage;
import com.neet.DiamondHunter.Manager.Content;
import com.neet.DiamondHunter.TileMap.TileMap;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class Controller {
	private int tileSize;
	private int item;
	private GraphicsContext gc;
	private TileMap m;
	private Model model;
	int Rownum;
	int Colnum;
	
	int temp_axe[][];
	int temp_boat[][];
	
	@FXML Canvas mapcanvas;
	@FXML private Button AxeButton;	//Button initialization used to place the axe
	@FXML private Button BoatButton; //Button initialization used to place the boat
	@FXML private Button LoadMapButton; //Button to load Map
	@FXML Label OutputConsoleLabel;	//Text area initialization to test the buttons(not in final prototype) 
	@FXML Label coLabel;
	@FXML Label item_label;

	public Controller() {
		tileSize = 16;
		m = new TileMap(tileSize);
		model = new Model();
		LoadMap();
	}

	@FXML
	public void initialize() {
		Rownum = m.getNumRows();
		Colnum = m.getNumCols();
		gc = mapcanvas.getGraphicsContext2D();
		drawMap(gc, Rownum, Colnum);
	}
	
	//Function to load the map from file
	private void LoadMap() {
		m.loadTiles("/Tilesets/testtileset.gif");
		m.loadMap("/Maps/testmap.map");
	}
	
	//Function to draw the map onto the canvas
	public void drawMap(GraphicsContext g, int numRows, int numCols) {
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				BufferedImage rcImage = m.getSquaresImage(row, col);
				Image tile = SwingFXUtils.toFXImage(rcImage, null);
				g.drawImage(tile, col * 16, row * 16);
			}
		}
	}

	//Updates the co-ordinates as you hover the mouse over the map
	public void updateCoords(MouseEvent event) {
		int col = (int) event.getX() / tileSize;
		int row = (int) event.getY() / tileSize;
		coLabel.setText("(" + col + ", " + row + ")");
	}
	
	//Place item on map when mouse clicks on it.
	public void onMapMouseClick(MouseEvent event) throws Exception {
		int x = (int) event.getX() / tileSize; //gets column position
		int y = (int) event.getY() / tileSize;//gets row position
		if(m.getType(y, x) == 0) {
			if(item == 0) {
				addAxe(x, y);
				
			}
			if(item == 1) {
				addBoat(x, y);
			}
		}
	}

	//sets the new location of the axe and calls the draw function
	public void addAxe(int x, int y) {
		int oldx = model.getAxeX();
		int oldy = model.getAxeY();
		Image sprite = SwingFXUtils.toFXImage(m.getSquaresImage(oldy,oldx), null);
		gc.drawImage(sprite, oldx*tileSize, oldy*tileSize);
		model.setAxeX(x);
		model.setAxeY(y);
		
		drawItem(x, y);
	}
	
	//sets the new location of the boat and calls the draw function
	public void addBoat(int x, int y) {
		int oldx = model.getBoatX();
		int oldy = model.getBoatY();
		Image sprite = SwingFXUtils.toFXImage(m.getSquaresImage(oldy,oldx), null);
		gc.drawImage(sprite, oldx*tileSize, oldy*tileSize);
		model.setBoatX(x);
		model.setBoatY(y);
		
		drawItem(x, y);
	}
	
	//draws the item onto the map
	public void drawItem(int x, int y) {
		BufferedImage sprite;
		if(item == 0) {
			sprite = Content.ITEMS[1][1];
			System.out.println("Test");
		}
		else {
			sprite = Content.ITEMS[1][0];
			System.out.println("Test2");
		}
		
		//converts BUFFEREDIMAGE to an image
		Image spriteImage = SwingFXUtils.toFXImage(sprite, null);
		gc.drawImage(spriteImage,x*tileSize,y*tileSize);
	}
	
	//if axe button pressed, sets item chosen to axe
	@FXML
	public void onAxeAction(){
		item = 0;
		System.out.println("Axe is selected");
		model.setItemID(item);
	}
	
	//if boat button pressed, sets item chosen to boat
	@FXML
	public void onBoatAction(){
		item = 1;
		System.out.println("Boat is selected");
		model.setItemID(item);
	}
	@FXML
	public void onLoadMap() {
		
	}



}
