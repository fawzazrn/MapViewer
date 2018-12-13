package MapViewer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import com.neet.DiamondHunter.Main.Game;
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
import javafx.scene.paint.Color;

public class Controller {
	private int tileSize;
	private boolean isAxeChosen = false; //Determines if Axe will be the item to be set on map
	private boolean isBoatChosen = false; //Determines if Boat will be the item to be set on map

	private boolean RadioClicked = false;
	
	private GraphicsContext gc;
	private TileMap m;
	private Model model;
	
	int Rownum;
	int Colnum;

	@FXML Canvas mapcanvas;
	@FXML private Button AxeButton;	//Button initialization used to place the axe
	@FXML private Button BoatButton; //Button initialization used to place the boat
	@FXML private Button LoadMapButton; //Button to load Map
	//@FXML Label OutputConsoleLabel;	//Text area initialization to test the buttons(not in final prototype) 
	@FXML Label coLabel;
	@FXML Label Console;
	@FXML Label item_label;
	@FXML public Button StartGameButton;

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
					Console.setText("Set the axe on any of the grass tiles.");
					addAxe(x, y);
					
				}
				if(isBoatChosen == true) {
					Console.setText("Set the boat on any of the grass tiles.");
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
		if(RadioClicked == true)
		mapcanvas.getGraphicsContext2D().strokeRect(oldx*tileSize, oldy*tileSize, 16, 16);

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
		
		if(RadioClicked == true)
		mapcanvas.getGraphicsContext2D().strokeRect(oldx*tileSize, oldy*tileSize, 16, 16);

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
		}
		else {
			sprite = Content.ITEMS[1][0];

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
		model.setItemID(0);
		Console.setText("Set the axe on any of the grass tiles.");
		Console.setPrefWidth(167);
	    Console.setWrapText(true);
		
	}	
	
	/**
	 * This is a radio button method that allows the user to see the grid lines
	 * of the map, when the button is selected.
	 */
	@FXML
	public void onRadioClicked() {
		mapcanvas.getGraphicsContext2D().setFill(Color.TRANSPARENT);
		mapcanvas.getGraphicsContext2D().setStroke(Color.GRAY);
		if(RadioClicked == false) {
			for (int row = 0; row < 40; row++) {
				for (int col = 0; col < 40; col++) {
					mapcanvas.getGraphicsContext2D().strokeRect(row * 16, col * 16, 16, 16);
				}
			}
			RadioClicked = true;}
		else {
			RadioClicked = false;
			for (int row1 = 0; row1 < 40; row1++) {
				for (int col1 = 0; col1 < 40; col1++) {
					BufferedImage rcImage = m.getSquaresImage(row1, col1);
					Image tile = SwingFXUtils.toFXImage(rcImage, null);
					gc.drawImage(tile, col1 * 16, row1 * 16);
									}
			}
			
			BufferedImage Asprite;
			Asprite = Content.ITEMS[1][1];	
			Image AspriteImage = SwingFXUtils.toFXImage(Asprite, null);
			gc.drawImage(AspriteImage,model.getAxeX()*tileSize, model.getAxeY()*tileSize);
			
			BufferedImage Bsprite;
			Bsprite = Content.ITEMS[1][0];	
			Image BspriteImage = SwingFXUtils.toFXImage(Bsprite, null);
			gc.drawImage(BspriteImage,model.getBoatX()*tileSize, model.getBoatY()*tileSize);
		}
	}
	
	/**
	 * Sets Boat as the item to be placed on the map when the Set Boat button is pressed
	 */
	@FXML
	public void onBoatAction(){
		isAxeChosen = false;
		isBoatChosen = true;
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
			Console.setText("Please set the axe or boat position first");
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
		if(RadioClicked == true) {
			mapcanvas.getGraphicsContext2D().strokeRect(xBoat * tileSize, yBoat * tileSize, 16, 16);
			mapcanvas.getGraphicsContext2D().strokeRect(xAxe * tileSize, yAxe * tileSize, 16, 16);
		}
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
	
	/**
	 * When Start Button is pressed, hides the map viewer window and opens the
	 * main Diamond Hunter game.
	 */
	@FXML
	public void onStartGame() {
		Main.ps.hide();
		Game.main(null);
		
		
	}
}
