package net.sf.openrocket.rocketcomponent;

import net.sf.openrocket.rocketcomponent.position.AnglePositionable;
<<<<<<< HEAD
=======
import net.sf.openrocket.rocketcomponent.position.AngleMethod;
import net.sf.openrocket.rocketcomponent.position.RadiusMethod;
>>>>>>> 82b78face9dcf23dd7e115d4e80d9bc4a3daafc8
import net.sf.openrocket.rocketcomponent.position.RadiusPositionable;

public interface RingInstanceable extends Instanceable, AnglePositionable, RadiusPositionable {

	@Override
<<<<<<< HEAD
	public double getAngularOffset();
	@Override
	public void setAngularOffset(final double angle);
	
	public double getInstanceAngleIncrement();
	
	public double[] getInstanceAngles();
	
	
	@Override
	public double getRadialOffset();
	@Override
	public boolean getAutoRadialOffset();
	@Override
	public void setRadialOffset(final double radius);
=======
	public double getAngleOffset();
	@Override
	public void setAngleOffset( final double angle);
	@Override
	public AngleMethod getAngleMethod();
	@Override
	public void setAngleMethod( final AngleMethod method );
	
	public double getInstanceAngleIncrement();
	
	public double[] getInstanceAngles();
	
	
	@Override
	public double getRadiusOffset();
	@Override
	public void setRadiusOffset( final double radius);
	@Override
	public RadiusMethod getRadiusMethod();
	@Override
	public void setRadiusMethod( final RadiusMethod method );
>>>>>>> 82b78face9dcf23dd7e115d4e80d9bc4a3daafc8
	
}
