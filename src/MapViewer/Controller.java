package MapViewer;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import com.neet.DiamondHunter.TileMap.TileMap;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.input.MouseEvent;

public class Controller {

	private TileMap m = new TileMap(16);
	private int tileSize;
	private Image axe;
	private Image boat;
	
	int Rownum = 40;
	int Colnum = 40;
	@FXML Canvas mapcanvas;
	@FXML GridPane grid;
	@FXML private Button AxeButton;	//Button initialization used to place the axe
	@FXML private Button BoatButton; //Button initialization used to place the boat
	@FXML Label OutputConsoleLabel;	//Text area initialization to test the buttons(not in final prototype) 
	@FXML Label coLabel;

	public Controller() {
		LoadMap();
	}

	@FXML
	public void initialize() {
		int numRows = m.getNumRows();
		int numCols = m.getNumCols();
		GraphicsContext g = mapcanvas.getGraphicsContext2D();
		drawMap(g, numRows, numCols);
		//loadItems();
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
		int x = (int) event.getX() / m.getTileSize();
		int y = (int) event.getY() / m.getTileSize();
		
		coLabel.setText("(" + x + ", " + y + ")");
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

	private void loadItems(String location) {
		BufferedImage items;
		try {
			items = ImageIO.read(getClass().getResourceAsStream(location));
			axe = SwingFXUtils.toFXImage(items.getSubimage(16, 16, tileSize, tileSize), null);
			boat = SwingFXUtils.toFXImage(items.getSubimage(0, 16, tileSize, tileSize), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
