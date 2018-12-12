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

public class Controller {
	private int tileSize;
	private boolean isAxeChosen = false;
	private boolean isBoatChosen = false;
	private boolean RadioClicked = false;
	
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
				if(isAxeChosen == true) {
					addAxe(x, y);
					
				}
				if(isBoatChosen == true) {
					addBoat(x, y);
				}
			}
			if(m.getType(y, x) != 0) {
				Console.setText("Please set item on the grass tiles");
				Console.setPrefWidth(167);
			    Console.setWrapText(true);
			}
		
	}

	//sets the new location of the axe and calls the draw function
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
	
	//sets the new location of the boat and calls the draw function
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
	
	//draws the item onto the map
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
		Console.setText("Set the axe on any of the grass tiles.");
		Console.setPrefWidth(167);
	    Console.setWrapText(true);
		
	}
	
	@FXML
	public void onRadioClicked() {
		
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
	//if boat button pressed, sets item chosen to boat
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
	@FXML
	public void onSaveMap() {
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
	
	//When reset map button is pressed, takes away the current axe and boat on the map
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
	
	@FXML
	public void onStartGame() {
		Main.ps.hide();
		Game.main(null);
		
		
	}
}
