package MapViewer;

public class Model {

	/*
	 * This class consists of setter and getter methods 
	 * that set the position of the X and Y co-ordinates of
	 * axe and boat 
	 * 
	 */
	
	private int boatx;
	private int boaty;
	private int axex;
	private int axey;
	private int item;
	
	//sets the X position of the boat
	public void setBoatX(int bx) {
		boatx = bx;
	}
	
	//sets the Y position of the boat
	public void setBoatY(int by) {
		boaty = by;
	}
	
	//sets the X position of the axe
	public void setAxeX(int ax) {
		axex = ax;
	}
	
	//sets the Y position of the axe
	public void setAxeY(int ay) {
		axey = ay;
	}
	
	//getter method for X position of axe
	public int getAxeX() {
		return axex;
	}
	
	//getter method for Y position of axe
	public int getAxeY() {
		return axey;
	}
	
	//getter method for X position of boat
	public int getBoatX() {
		return boatx;
	}
	
	//getter method fot Y position of boat
	public int getBoatY() {
		return boaty;
	}
	
	//setter method to set the item ID of selected item
	public void setItemID(int item) {
		this.item = item;
	}
	
	//getter method to get the item ID of selected item
	public int getItemID() {
		return item;
	}
	
	
}
