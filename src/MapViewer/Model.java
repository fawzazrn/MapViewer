package MapViewer;

public class Model {

	private double boatx;
	private double boaty;
	private double axex;
	private double axey;
	private double item;
	
	//private double old_boat_x;
	//private double old_boat_y;
	//private double old_axe_x;
	//private double old_axe_y;
	
	//private double boatNumber;
	private double axeNumber;
	
	//private int oldboatNumber;
	//private int oldaxeNumber;
	
	public void setBoatX(double bx) {
		boatx = bx;
	}
	
	public void setBoatY(double by) {
		boaty = by;
	}
	
	public void setAxeX(double ax) {
		axex = ax;
	}
	
	public void setAxeY(double ay) {
		axey = ay;
	}
	
	public double getAxeX() {
		return axex;
	}
	
	public double getAxeY() {
		return axey;
	}
	
	public double getBoatX() {
		return boatx;
	}
	
	public double getBoatY() {
		return boaty;
	}
	
	public void setaxeNumber(double axeNumber) {
		this.axeNumber = axeNumber;
	}
	
	public double getaxeNumber() {
		return axeNumber;
	}
	
	public void setItemID(double item) {
		this.item = item;
	}
	
	public double getItemID() {
		return item;
	}
	
	
}
