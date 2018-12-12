package MapViewer;

/**
 * 
 * This class consists of setter and getter methods 
 * for the position of the boat and axe
 * as well as the selected item ID
 * 
 */

public class Model implements ModelInterface{

	private int boatx;
	private int boaty;
	private int axex;
	private int axey;
	private int item;
	
	//setter method for X co-ordinate of Boat
	public void setBoatX(int bx) {
		boatx = bx;
	}
	
	//setter method for Y co-ordinate of Boat
	public void setBoatY(int by) {
		boaty = by;
	}
	
	//setter method for X co-ordinate of Axe
	public void setAxeX(int ax) {
		axex = ax;
	}
	
	//setter method for Y co-ordinate of Axe 
	public void setAxeY(int ay) {
		axey = ay;
	}
	
	//getter method for X co-ordinate of Axe
	public int getAxeX() {
		return axex;
	}
	
	//getter method for Y co-ordinate of Axe
	public int getAxeY() {
		return axey;
	}
	
	//getter method for X co-ordinate of Boat
	public int getBoatX() {
		return boatx;
	}
	
	//getter method for Y co-ordinate of Boat
	public int getBoatY() {
		return boaty;
	}
	
	//setter method for the item ID when an item is selected
	public void setItemID(int item) {
		this.item = item;
	}
	
	//getter method for the item ID
	public int getItemID() {
		return item;
	}
	
	
}
