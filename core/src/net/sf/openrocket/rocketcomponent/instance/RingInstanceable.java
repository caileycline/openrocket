package net.sf.openrocket.rocketcomponent.instance;

public interface RingInstanceable extends Instanceable {

	// note: separation is usually is set == 2*pi / instance_count 
	public double getAngleSeparation();
	public boolean getAutoAngleSeparation();
	
	// note: this only takes effect if automatic angle separation is disabled ( angleMethod == Locatable.Method.ABSOLUTE )  
	public void setAngleSeparation(final double angle);
	
	
	
//  These are actually Location variables... just get the location and set them on the location, directly? 
//  BUT we still want to enforce that these exist in said components... how do we do that? 
	
//	public double getRadialOffset();
//	public Locatable.Method getRadialMethod();
//	public boolean getAutoRadialOffset();
//	// ^^ gets / sets vv 
//	public void setAutoRadialOffset( final boolean auto );
//	public void setRadialOffset(final double radius);
//	public void setRadialMethod( RocketComponent.Position method);
//	
//
//	public double getAngularOffset();
//	public Locatable.Method getAngularMethod();
//	// ^^ gets / sets vv
//	public void setAngularOffset(final double angle);
//	public void setAngularMethod( RocketComponent.Position method);
	
}
