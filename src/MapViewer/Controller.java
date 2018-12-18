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

	
	//The controller constructor to run the code 
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
		//line to save the mapcanvas of type GraphicsContext
		gc = mapcanvas.getGraphicsContext2D();
		drawMap(gc, Rownum, Colnum);
		isAxeChosen = false;
		addBoat(4,12);
		isAxeChosen = true;
		addAxe(37,26);
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
		//nested for loop to print out a 40x40 grid of images specified by rcimage
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				//converting BufferedImage returned by getSquaresImage to Image type
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
		
		//if the radio button is clicked
		if(RadioClicked == true)
			mapcanvas.getGraphicsContext2D().strokeRect(oldx*tileSize, oldy*tileSize, 16, 16);//Draws the gridlines 

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
		
		//if the radio button is clicked
		if(RadioClicked == true)
		mapcanvas.getGraphicsContext2D().strokeRect(oldx*tileSize, oldy*tileSize, 16, 16);//Draws the gridlines

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
			//creates a new file
			File file = new File(filePath);

			// if file does not exists, then create it
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			//create a print stream
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
		//sets the fill and the stroke color
		mapcanvas.getGraphicsContext2D().setFill(Color.TRANSPARENT);
		mapcanvas.getGraphicsContext2D().setStroke(Color.GRAY);
		//if the radio button is not clicked
		if(RadioClicked == false) {
			for (int row = 0; row < 40; row++) {
				for (int col = 0; col < 40; col++) {
					//print out the rectangle for each tile
					mapcanvas.getGraphicsContext2D().strokeRect(row * 16, col * 16, 16, 16);
				}
			}
			RadioClicked = true;}
		else {
			//if radio button is unselected 
			RadioClicked = false;
			for (int row1 = 0; row1 < 40; row1++) {
				for (int col1 = 0; col1 < 40; col1++) {
					//draws the tile 
					BufferedImage rcImage = m.getSquaresImage(row1, col1);
					Image tile = SwingFXUtils.toFXImage(rcImage, null);
					//print out the rectangle for each tile
					gc.drawImage(tile, col1 * 16, row1 * 16);
				}
			}
			
			BufferedImage Asprite;
			Asprite = Content.ITEMS[1][1];	
			Image AspriteImage = SwingFXUtils.toFXImage(Asprite, null);
			//draws the axe back onto the canvas
			gc.drawImage(AspriteImage,model.getAxeX()*tileSize, model.getAxeY()*tileSize);
			
			BufferedImage Bsprite;
			Bsprite = Content.ITEMS[1][0];	
			Image BspriteImage = SwingFXUtils.toFXImage(Bsprite, null);
			//draws the boat back onto the canvas
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
			Write2File("Resources/SettingFile/axe.txt", model.getAxeX(), model.getAxeY());
			Write2File("Resources/SettingFile/boat.txt", model.getBoatX(), model.getBoatY());
			
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
		
		isAxeChosen = false;
		addBoat(4,12);
		isAxeChosen = true;
		addAxe(37,26);
		
		int xBoat = model.getBoatX();
		int yBoat = model.getBoatY();
		
		int xAxe = model.getAxeX();
		int yAxe = model.getAxeY();
		
		//if the radio button is clicked
		if(RadioClicked == true) {
			//draws the rectangle on the boat's position on the reseted canvas
			mapcanvas.getGraphicsContext2D().strokeRect(xBoat * tileSize, yBoat * tileSize, 16, 16);
			//draws the rectangle on the axe's position on the reseted canvas
			mapcanvas.getGraphicsContext2D().strokeRect(xAxe * tileSize, yAxe * tileSize, 16, 16);
			
		}
		
		Write2File("Resources/SettingFile/axe.txt", model.getAxeX(), model.getAxeY());
		Write2File("Resources/SettingFile/boat.txt", model.getBoatX(), model.getBoatY());
		
		Console.setText("Map has been set to default");
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


