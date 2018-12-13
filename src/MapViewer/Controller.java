package MapViewer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
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
	private boolean isAxeChosen = false; //Determines if Axe will be the item to be set on map
	private boolean isBoatChosen = false; //Determines if Boat will be the item to be set on map

	private GraphicsContext gc;
	private TileMap m;
	private Model model;
	int Rownum;
	int Colnum;
	
	@FXML Canvas mapcanvas;
	@FXML private Button AxeButton;	//Button initialization used to place the axe
	@FXML private Button BoatButton; //Button initialization used to place the boat
	@FXML private Button LoadMapButton; //Button to load Map
	@FXML Label OutputConsoleLabel;	//Text area initialization to test the buttons(not in final prototype) 
	@FXML Label coLabel;
	@FXML Label Console;
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
		Console.setText("Select either Axe or Boat to be placed");
		Console.setPrefWidth(167);
	    Console.setWrapText(true);
	}
	
	/**
	 * Loads map file and its tileset from a designated file path
	 */
	private void LoadMap() {
		m.loadTiles("/Tilesets/testtileset.gif");
		m.loadMap("/Maps/testmap.map");
	}
	
	/**
	 * 
	 * Draws the map onto the canvas
	 * 
	 * @param g
	 * @param numRows
	 * @param numCols
	 */
	public void drawMap(GraphicsContext g, int numRows, int numCols) {
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				BufferedImage rcImage = m.getSquaresImage(row, col);
				Image tile = SwingFXUtils.toFXImage(rcImage, null);
				g.drawImage(tile, col * 16, row * 16);
			}
		}
	}

	/**
	 * 
	 * Gets and displays co-ordinates of the mouse cursor position on the map 
	 * 
	 * @param event
	 */
	public void updateCoords(MouseEvent event) {
		int col = (int) event.getX() / tileSize;
		int row = (int) event.getY() / tileSize;
		coLabel.setText("(" + col + ", " + row + ")");
	}
	
	/**
	 * 
	 * Places item on map on mouse click event
	 * 
	 * Item that will be set on map is decided by values of isAxeChosen and isBoatChosen
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void onMapMouseClick(MouseEvent event) throws Exception {
		int x = (int) event.getX() / tileSize; //gets column position
		int y = (int) event.getY() / tileSize;//gets row position
			
			//Item can only be placed on grass tiles
			if(m.getType(y, x) == 0) {
				if(isAxeChosen == true) {
					addAxe(x, y);
					
				}
				if(isBoatChosen == true) {
					addBoat(x, y);
				}
			}
			//If statement used to check if user clicks on a non-grass tile
			if(m.getType(y, x) != 0) {
				Console.setText("Please set item on the grass tiles");
				Console.setPrefWidth(167);
			    Console.setWrapText(true);
			}
		
	}
	
	/**
	 * 
	 * Sets the new location of the axe and calls the draw function 
	 * 
	 * Draw function places axe image on the designated co-ordinates
	 * 
	 * @param x
	 * @param y
	 */
	public void addAxe(int x, int y) {
		int oldx = model.getAxeX();
		int oldy = model.getAxeY();
		Image sprite = SwingFXUtils.toFXImage(m.getSquaresImage(oldy,oldx), null);
		
		gc.drawImage(sprite, oldx*tileSize, oldy*tileSize);
		
		model.setAxeX(x);
		model.setAxeY(y);
		
		drawItem(x, y);
	}
	
	/**
	 * 
	 * Sets the new location of the boat and calls the draw function
	 * 
	 * Draw function places boat image on the designated co-ordinates
	 * 
	 * @param x
	 * @param y
	 */
	public void addBoat(int x, int y) {
		int oldx = model.getBoatX();
		int oldy = model.getBoatY();
		Image sprite = SwingFXUtils.toFXImage(m.getSquaresImage(oldy,oldx), null);
		gc.drawImage(sprite, oldx*tileSize, oldy*tileSize);
		model.setBoatX(x);
		model.setBoatY(y);
		
		drawItem(x, y);
	}
	
	/**
	 * 
	 * Draws an item on specific co-ordinates on the map
	 * 
	 * @param x
	 * @param y
	 */
	public void drawItem(int x, int y) {
		BufferedImage sprite;
		if(isAxeChosen == true) {
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
	
	
	/**
	 * 
	 * Stores the co-ordinates of an item into a file in a designated file path
	 * 
	 * @param filePath
	 * @param rowIndex
	 * @param colIndex
	 */
	public void Write2File(String filePath, int rowIndex, int colIndex) {
		try {
			File file = new File(filePath);

			// if file does not exists, then create it
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			PrintStream ps = new PrintStream(file);
			ps.println(rowIndex);
			ps.println(colIndex);

			ps.close();

		} catch (IOException x) {
			System.out.println("Error: " + x);
			x.printStackTrace();
		}
	}
	
	/**
	 * Sets Axe as the item to be placed on the map when the Set Axe button is pressed
	 */
	@FXML
	public void onAxeAction(){
		isAxeChosen = true;
		isBoatChosen = false;
		System.out.println("Axe is selected");
		model.setItemID(0);
		Console.setText("Set the axe on any of the grass tiles.");
		Console.setPrefWidth(167);
	    Console.setWrapText(true);
		
	}
	
	/**
	 * Sets Boat as the item to be placed on the map when the Set Boat button is pressed
	 */
	@FXML
	public void onBoatAction(){
		isAxeChosen = false;
		isBoatChosen = true;
		System.out.println("Boat is selected");
		model.setItemID(1);
		Console.setText("Set the boat on any of the grass tiles");
		Console.setPrefWidth(167);
	    Console.setWrapText(true);
	}
	
	/**
	 * Saves co-ordinates of Axe and Boat into a text file in the Resources folder 
	 * 
	 * Co-ordinates are saved when Save Map button is pressed
	 */
	@FXML
	public void onSaveMap() {
		//Co-ordinates can only be saved if both Axe and Boat are set on the map
		//Otherwise, MapViewer outputs a message and co-ordinates will not be saved.
		if((isAxeChosen == false )&&(isBoatChosen == false)) {
			Console.setText("\nPlease set the axe or boat position first");
			Console.setPrefWidth(167);
		    Console.setWrapText(true);
		}
		else {
			Write2File("~/SettingFile/axe.txt", model.getAxeX(), model.getAxeY());
			Write2File("~/SettingFile/boat.txt", model.getBoatX(), model.getBoatY());
			Console.setText("Co-ordinates saved.");
			Console.setPrefWidth(167);
		    Console.setWrapText(true);
		}
	}
	
	/**
	 * Axe and Boat sprites are erased from the map and their X and Y co-ordinates in their
	 * respective text files are set to 0
	 */
	@FXML
	public void onReset() {
		int xBoat = model.getBoatX();
		int yBoat = model.getBoatY();
		int xAxe = model.getAxeX();
		int yAxe = model.getAxeY();
		
		Image sprite1 = SwingFXUtils.toFXImage(m.getSquaresImage(yBoat,xBoat), null);
		Image sprite2 = SwingFXUtils.toFXImage(m.getSquaresImage(yAxe,xAxe), null);
		gc.drawImage(sprite1, xBoat * tileSize, yBoat * tileSize);
		gc.drawImage(sprite2, xAxe * tileSize, yAxe * tileSize);

		model.setAxeX(0);
		model.setAxeY(0);
		model.setBoatX(0);
		model.setBoatY(0);
		
		Write2File("~/SettingFile/axe.txt", xAxe, yAxe);
		Write2File("~/SettingFile/boat.txt", xBoat, yBoat);
		
		Console.setText("Map has been reset");
		Console.setPrefWidth(167);
	    Console.setWrapText(true);
	}
}
