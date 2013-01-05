
public class NVector {
	double dx, dy;
	
	public NVector(double dx, double dy){
		this.dx = dx; this.dy = dy;
	}
	
	public void normalize(){
		double length = Math.sqrt(dx*dx + dy*dy) + 0.0;
		dx /= length;
		dy /= length;

	}
	
	public double getDX(){return dx;}
	public double getDY(){return dy;}
}
