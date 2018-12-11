package MapViewer;

public class Model {

	private int boatx;
	private int boaty;
	private int axex;
	private int axey;
	private int item;
	
	//private double old_boat_x;
	//private double old_boat_y;
	//private double old_axe_x;
	//private double old_axe_y;
	
	//private double boatNumber;
	private int axeNumber;
	
	//private int oldboatNumber;
	//private int oldaxeNumber;
	
	public void setBoatX(int bx) {
		boatx = bx;
	}
	
	public void setBoatY(int by) {
		boaty = by;
	}
	
	public void setAxeX(int ax) {
		axex = ax;
	}
	
	public void setAxeY(int ay) {
		axey = ay;
	}
	
	public int getAxeX() {
		return axex;
	}
	
	public int getAxeY() {
		return axey;
	}
	
	public int getBoatX() {
		return boatx;
	}
	
	public int getBoatY() {
		return boaty;
	}
	
	public void setItemID(int item) {
		this.item = item;
	}
	
	public int getItemID() {
		return item;
	}
	
	
}
