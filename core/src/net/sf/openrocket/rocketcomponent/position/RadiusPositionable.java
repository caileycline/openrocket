package net.sf.openrocket.rocketcomponent.position;

public interface RadiusPositionable {
	
<<<<<<< HEAD
	public double getRadialOffset();
	public boolean getAutoRadialOffset();	
	public void setRadialOffset(final double radius);
=======
	public double getOuterRadius();
	
	public double getRadiusOffset();
	public void setRadiusOffset(final double radius);
	
	public RadiusMethod getRadiusMethod();
	public void setRadiusMethod( final RadiusMethod method );
	
	/**
	 * Equivalent to:
	 * `instance.setRadiusMethod(); instance.setRadiusOffset()`
	 * 
	 * @param radius
	 * @param method
	 */
	public void setRadius( final RadiusMethod method, final double radius );
>>>>>>> 82b78face9dcf23dd7e115d4e80d9bc4a3daafc8
	
}
