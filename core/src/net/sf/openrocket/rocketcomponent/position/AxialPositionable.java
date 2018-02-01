package net.sf.openrocket.rocketcomponent.position;

<<<<<<< HEAD
import net.sf.openrocket.rocketcomponent.RocketComponent.Position;
=======
>>>>>>> 82b78face9dcf23dd7e115d4e80d9bc4a3daafc8

public interface AxialPositionable {

	public double getAxialOffset();
	
	public void setAxialOffset(final double newAxialOffset);
	
<<<<<<< HEAD
	public Position getAxialPositionMethod( );
	
	public void setAxialPositionMethod( Position newMethod );
=======
	public AxialMethod getAxialMethod( );
	
	public void setAxialMethod( AxialMethod newMethod );
>>>>>>> 82b78face9dcf23dd7e115d4e80d9bc4a3daafc8
}
