
public class NVector {
	double dx, dy;
	
	public NVector(double dx, double dy){
		this.dx = dx; this.dy = dy;
	}
	
	public void normalize(){
		dx =  (dx/Math.sqrt(dx*dx + dy*dy));
		dy =  (dy/Math.sqrt(dx*dx + dy*dy));
	}
	
	public double getDX(){return dx;}
	public double getDY(){return dy;}
}
