package net.sf.openrocket.rocketcomponent.location;

import net.sf.openrocket.rocketcomponent.location.Locatable.Method;

public class Location implements Locatable {
	protected double x = 0;
	protected double y = 0;
	protected double z = 0;
	// pseudo axes:
	//     angle (theta): 
	//			theta = 0 extends along the +y axis
	//          theta indicates clockwise rotation around the x axis  
	//     radius is distance from the x-axis in the y-z plane.
	
	protected Method rotationMethod;
	protected Method axialMethod;
	protected Method radialMethod;
	
	protected Method[] allowedAxialMethods;
	protected Method[] allowedRadialMethods;
	protected Method[] allowedRotationMethods;
	
	public Location(){
		setAllowableMethods( 
				Method.axialCenterStackValues(), 
				Method.fixedValue(), 
				Method.fixedValue());
	}
	
	public Location( final double _x, final double _y, final double _z){
		this();
		x = _x;
		y = _y;
		z = _z;
	}
	
	// returns a three-vector: {x,y,z}
	@Override
	public double[] getAsOrthogonal(){ 
		return new double[]{x,y,z};
	}
	
	// returns a three-vector: {x, r, theta}
	@Override
	public double[] getAsCylindrical(){
		final double theta = Math.atan2(y,z);
		final double r = Math.hypot(y,z);
		return new double[]{ x, r, theta};
	}
	
	@Override
	public double getCoordinateOffset( final Locatable.Axis _axis ){
		switch(_axis){
			case X: case AXIAL: 
				return x;
			case Y: 
				return y;
			case Z: 
				return z;
			case RADIAL:
				return Math.hypot(y,z);
			case ANGULAR:
				return Math.atan2(y,z);
			default:
				return Double.NaN;
		}
	}
	
	@Override
	public Locatable.Method getCoordinateMethod( final Locatable.Axis _axis ){
		switch(_axis){
		case X: case AXIAL: 
			return axialMethod;
		case RADIAL:
			return radialMethod;
		case ANGULAR:
			return rotationMethod;
		default:
			return Method.ABSOLUTE;
		}
	}
	
	@Override
	public void setOrthogonalCoordinates( final double x, final double y, final double z){
		setX(x); 
		setY(y);
		setZ(z);
	}

	@Override
	public void setCylindricalCoordinates( final double x, final double radius, final double angle ){
		setX(x);
		setRadius(radius);
		setRotation(angle);
	}
	
	@Override
	public void setSingleCoordinate( final Locatable.Axis _axis, final Locatable.Method _method, final double _value ){
		switch(_axis){
			case X: case AXIAL: 
				x = _value; break;
		    case Y:
		    	y = _value; break;
		    case Z: 
		    	z = _value; break;
			case RADIAL:
				setRadius( _value); break;
			case ANGULAR:
				setRotation( _value); break;
			default:
				// ignore.
		}
	}
	
	@Override
	public void setAllowableMethods( final Method[] axialMethods, final Method[] radialMethods, final Method[] rotationMethods){ 
		this.allowedAxialMethods = axialMethods;
		this.allowedRadialMethods = radialMethods;
		this.allowedRotationMethods = rotationMethods;
	}

	// get a specific coordinate value via a method other than the stored method. 
	public static double getAxialOffsetAs( final Locatable.Method asMethod, final double thisLength, final Locatable other, final double otherLength){
		return Double.NaN;
	}

	public static double getRadiusOffsetAs( final Locatable.Method asMethod, final double thisRadius, final Locatable other, final double otherRadius){
		return Double.NaN;
	}
	
	public static double getRotationOffset( final Locatable.Method asMethod, final Locatable other){
		return Double.NaN;
	}
	

	
	// ================== Suggested Presets ==================
	// should probably be moved elsewhere:
	// perhaps each of these should be a specific sub-class of Locatable ? 
	
	// used for centerline BodyTubes, NoseCones, Transitions
	final void applyCenterStackPreset(){
		setAllowableMethods(Method.axialCenterStackValues(), 
							Method.fixedValue(), 
							Method.fixedValue());
	}
		
	// used for various interior components: flight-computers, parachutes, mounting rings, etc 
	final void applyInteriorPreset(){
		setAllowableMethods(Method.axialRelativeValues(), 
							Method.fixedValue(), 
							Method.fixedValue());
	}
	
    // mount on the outside of a body-tube: fins, rail-button, launch-lugs, etc
	final void applyBodyMountPreset(){
		setAllowableMethods(Method.axialRelativeValues(), 
							Method.radialOnValue(), 
							Method.possibleAngularValues());
	}
	
	final void applyFreeformPreset(){
		setAllowableMethods(Method.absoluteValue(), 
							Method.absoluteValue(), 
							Method.absoluteValue());
	}
	
	
	// ================== Private Methods ================== 
	
	protected void setX( final double _x){
		this.x = _x;
	}
	protected void setY( final double _y){
		this.y = _y;
	}
	protected void setZ( final double _z){
		this.z = _z;
	}
	
	protected void setRadius( final double r){
		final double theta = getRotation();
		this.y = r*Math.cos(theta);
		this.z = r*Math.sin(theta);
	}
	
	protected void setRotation( final double theta){
		final double radius = getRadius();
		this.y = radius*Math.cos(theta);
		this.z = radius*Math.sin(theta);
	}
	
	protected double getRadius(){
		return Math.hypot(y,z);
	}
	
	protected double getRotation(){
		return Math.atan2(y,z);
	}
	
	
}
