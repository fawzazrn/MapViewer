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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class Controller {

	private TileMap m = new TileMap(16);
	private Model model = new Model();
	//private int tileSize;
	//private Image axe;
	//private Image boat;
	private double item;
	
	int Rownum = 40;
	int Colnum = 40;
	@FXML Canvas mapcanvas;
	@FXML GridPane grid;
	@FXML AnchorPane grid_anchor_pane;
	@FXML private Button AxeButton;	//Button initialization used to place the axe
	@FXML private Button BoatButton; //Button initialization used to place the boat
	@FXML private Button LoadMapButton; //Button to load Map
	@FXML Label OutputConsoleLabel;	//Text area initialization to test the buttons(not in final prototype) 
	@FXML Label coLabel;
	@FXML Label item_label;

	public Controller() {
		LoadMap();
	}

	@FXML
	public void initialize() {
		int numRows = m.getNumRows();
		int numCols = m.getNumCols();
		GraphicsContext g = mapcanvas.getGraphicsContext2D();
		drawMap(g, numRows, numCols);
		loadItems();
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

	public void updateCoords(MouseEvent event) {
		double x = (int) event.getX() / m.getTileSize();
		double y = (int) event.getY() / m.getTileSize();
		
		coLabel.setText("(" + x + ", " + y + ")");
	}
	
	public void onMapMouseClick(MouseEvent event) throws Exception {
		Double xx = event.getX() / m.getTileSize();
		Double yy = event.getY() / m.getTileSize();
		
		if(model.getItemID()==0) {
			//System.out.println(xx + " " + yy);
			addAxe(Math.round(xx), Math.round(yy));
		}
	}

	public void GenGridPane() {		//Function to generate the GridPane
		for (int i = 0 ;i < Rownum;i++) {
			ColumnConstraints column = new ColumnConstraints();
			column.setPercentWidth(2.5);
			grid.getColumnConstraints().add(column);
		}
		
		for (int i = 0;i < Colnum;i++) {
			RowConstraints Row = new RowConstraints();
			Row.setPercentHeight(2.5);
			grid.getRowConstraints().add(Row);
		}
	}


	public void addAxe(double x, double y) {
		double x_tile = x;
		double y_tile = y;
		
		//System.out.println("x tile = " + x_tile + " " + y_tile);
		if(model.getaxeNumber()<=0) {
			model.setAxeX(x_tile);
			model.setAxeY(y_tile);
			updateMap(model.getAxeX(), model.getAxeY(), model.getItemID());
			model.setaxeNumber(1);
			
		}
		
	}
	
	public void updateMap(double x, double y, double item) {
		
		BufferedImage sprite;
		if(item == 0) {
			sprite = Content.ITEMS[1][0];
			//System.out.println("Test");
		}
		
		else {
			sprite = Content.ITEMS[1][1];
		}
		
		//converts BUFFEREDIMAGE to an image
		ImageView item_image_view = new ImageView();
		Image spriteImage = SwingFXUtils.toFXImage(sprite, null);
		item_image_view.setImage(spriteImage);
		
		
		grid_anchor_pane.getChildren().add(item_image_view);
		item_image_view.setX(x);
		item_image_view.setY(y);
		
		
	}
	
	
	private void loadItems() {
		
		AxeButton.setOnAction((event) -> {
			item=0; //item selected is the axe
			System.out.println("Axe is selected");
			model.setItemID(item);
		});
		
		BoatButton.setOnAction((event) -> {
			item=1;
			System.out.println("Boat is selected");
			model.setItemID(item);
		});
		
	}
	
	
	/*
	private void loadItems(String location) {
		BufferedImage items;
		try {
			items = ImageIO.read(getClass().getResourceAsStream(location));
			axe = SwingFXUtils.toFXImage(items.getSubimage(16, 16, tileSize, tileSize), null);
			boat = SwingFXUtils.toFXImage(items.getSubimage(0, 16, tileSize, tileSize), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
	
	


}
