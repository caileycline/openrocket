package net.sf.openrocket.rocketcomponent.position;

public interface AnglePositionable {

<<<<<<< HEAD
	public double getAngularOffset();
	
	public void setAngularOffset(final double angle);
	
//	public Position getAnglePositionMethod( );
//	public void setAnglePositionMethod( Position newMethod );
=======
	public double getAngleOffset();
	
	public void setAngleOffset(final double angle);
	
	public AngleMethod getAngleMethod( );
	public void setAngleMethod( final AngleMethod newMethod );	
>>>>>>> 82b78face9dcf23dd7e115d4e80d9bc4a3daafc8
}
