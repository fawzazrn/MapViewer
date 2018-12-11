package MapViewer;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
	private boolean isAxeChosen = false;
	private boolean isBoatChosen = false;
	
//	private int checkAxebutton = 0;
//	private int checkBoatbutton = 0;

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
	@FXML Label Console;
	@FXML Label item_label;
	@FXML Label prompt_map;
	
	//this function is to initialize the tile size to 16
	//the LoadMap() function is called to load the map on the window
	public Controller() {
		tileSize = 16;
		m = new TileMap(tileSize);
		model = new Model();
		LoadMap();
	}

	//the initialize() function does the job to get the number of rows and columns from the 
	//object TileMap 
	//mapcanvas is an object of Canvas that is used to display the map
	//
	@FXML
	public void initialize() {
		Rownum = m.getNumRows();
		Colnum = m.getNumCols();
		gc = mapcanvas.getGraphicsContext2D();
		drawMap(gc, Rownum, Colnum);
		Console.setText("Select either Axe or Boat to be placed");
	}
	
	//Function to load the map from Tileset
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
				if(isAxeChosen == true) {
					addAxe(x, y);
					
				}
				if(isBoatChosen == true) {
					addBoat(x, y);
				}
				
				//error handling for if the user has not selected any items
				//prints out label 
			}
			if(m.getType(y, x) != 0) {
				Console.setText("Please set item on the grass tiles");
			}
		
	}

	//sets the new location of the axe and calls the drawItem function to draw the image of 
	//axe onto the map
	public void addAxe(int x, int y) {
		//checkAxebutton = 0;
		int oldx = model.getAxeX();
		int oldy = model.getAxeY();
		Image sprite = SwingFXUtils.toFXImage(m.getSquaresImage(oldy,oldx), null);
		
		gc.drawImage(sprite, oldx*tileSize, oldy*tileSize);
		
		model.setAxeX(x);
		model.setAxeY(y);
		
		drawItem(x, y);
	}
	
	//sets the new location of boat axe and calls the drawItem function to draw the image of 
	//boat onto the map
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
	

	//if axe button pressed, sets item chosen to axe
	@FXML
	public void onAxeAction(){
		isAxeChosen = true;
		isBoatChosen = false;
		System.out.println("Axe is selected");
		model.setItemID(0);
		Console.setText("\nSet the axe on any of the grass tiles.");
		
	}
	
	//if boat button pressed, sets item chosen to boat
	@FXML
	public void onBoatAction(){
		isAxeChosen = false;
		isBoatChosen = true;
		System.out.println("Boat is selected");
		model.setItemID(1);
		Console.setText("\nSet the boat on any of the grass tiles");
	}
	
	
	@FXML
	public void onSaveMap() {
		if((isAxeChosen == false )&&(isBoatChosen == false)) {
			Console.setText("\nPlease set the axe or boat position first");
		}
	
		else {
			Write2File("~/SettingFile/axe.txt", model.getAxeX(), model.getAxeY());
			Write2File("~/SettingFile/boat.txt", model.getBoatX(), model.getBoatY());
			Console.setText("\nCo-ordinates saved.");
		}
	}
}
