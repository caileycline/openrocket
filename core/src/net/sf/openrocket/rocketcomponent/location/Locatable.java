package net.sf.openrocket.rocketcomponent.location;

import net.sf.openrocket.startup.Application;

// ================== Coordinates ==================
//		
//		OrthogonalCoordinates:
//			public double x;
//			public double y;
//			public double z;
//		
//		
//		CylindricalCoordinates:
//			public double x;
//			public double radius;
//			public double angle;
//		
//

public interface Locatable {
	public enum Axis { 
		X( Application.getTranslator().get("RocketComponent.Axis.X")),
		Y( Application.getTranslator().get("RocketComponent.Axis.Y")),
		Z( Application.getTranslator().get("RocketComponent.Axis.Z")),
		AXIAL( Application.getTranslator().get("RocketComponent.Axis.AXIAL")),
		RADIAL( Application.getTranslator().get("RocketComponent.Axis.RADIAL")),
		ANGULAR( Application.getTranslator().get("RocketComponent.Axis.ANGULAR"));
		
		static final int ERROR_DIMENSION = -1;
		static final int DIMENSION_COUNT = 3; // i.e. we position things in 3-dimensional space
		
		private final String title;
		
		Axis( String title) {
			this.title = title;
		}
		
		@Override
		public String toString() {
			return title;
		}
		
	}
	
	public enum Method {
		// This Method is in absolute Method, measured from the rocket's origin.
		//    The origin is the tip of the nose cone.
		ABSOLUTE(Application.getTranslator().get("RocketComponent.Method.ABSOLUTE")),
		// Method to touch previous sibling component, in the increasing coordinate direction
		AFTER(Application.getTranslator().get("RocketComponent.Method.AFTER")),
		// This axis is measured from the bottom of the parent component to the bottom of this component
		BOTTOM(Application.getTranslator().get("RocketComponent.Method.BOTTOM")),
		// This coordinate does not change.
		FIXED(Application.getTranslator().get("RocketComponent.Method.FIXED")),
		// This axis is measured from the middle of the parent component to the middle of this component
		MIDDLE(Application.getTranslator().get("RocketComponent.Method.MIDDLE")),
		// Parent values are mirrored across the x-axis.
		// n.b. Only applicable for children of off-axis components
		MIRROR_XY(Application.getTranslator().get("RocketComponent.Method.MIRROR_XY")),
		// Method this component on the outside of the parent component
		//    => equivalent to 'autoRadius()'
		ON(Application.getTranslator().get("RocketComponent.Method.ON")),
		// Method this component relative to parent's zero 
		RELATIVE(Application.getTranslator().get("RocketComponent.Method.RELATIVE")),
		// This axis is measured from the top of the parent component to the top of this component
		TOP(Application.getTranslator().get("RocketComponent.Method.TOP"));
		
		private final String title;
		
		Method(String title) {
			this.title = title;
		}
		
		@Override
		public String toString() {
			return title;
		}
		
		// this set is here more for documentation than actual use.
		static final Method[] possibleAngularValues(){ return new Method[]{ Method.ABSOLUTE, Method.MIRROR_XY, Method.RELATIVE };}
		static final Method[] possibleAxialValues(){ return new Method[]{ Method.ABSOLUTE, Method.TOP, Method.MIDDLE, Method.BOTTOM }; }
		static final Method[] possibleRadialValues(){ return new Method[]{ Method.ABSOLUTE, Method.ON}; }
		static final Method[] possibleXYValues(){ return new Method[]{ Method.ABSOLUTE}; }

		// these are descriptive subsets of the methods above
		static final Method[] absoluteValue(){ return new Method[]{ Method.ABSOLUTE }; }
		static final Method[] axialCenterStackValues(){ return new Method[]{ Method.AFTER }; }
		static final Method[] axialRelativeValues(){ return new Method[]{ Method.ABSOLUTE, Method.TOP, Method.MIDDLE, Method.BOTTOM }; }
		static final Method[] fixedValue(){ return new Method[]{ Method.FIXED};}
		static final Method[] radialOnValue(){ return new Method[]{ Method.ON}; }
		
		
	}
	
	// ================== Class Methods ================== 
	
	// returns a three-vector: {x,y,z}
	public double[] getAsOrthogonal();

	// returns a three-vector: {x, radius, rotation}
	public double[] getAsCylindrical();
	
	public double getCoordinateOffset( final Locatable.Axis _axis );
	
	public Locatable.Method getCoordinateMethod( final Locatable.Axis _axis );

	public void setAllowableMethods( final Method[] axialMethods, final Method[] radialMethods, final Method[] angularMethods);
	public void setSingleCoordinate( final Locatable.Axis _axis, final Locatable.Method _method, final double _value ); 
	
	public void setOrthogonalCoordinates( final double x, final double y, final double z);
	public void setCylindricalCoordinates( final double x, final double radius, final double angle);
}
