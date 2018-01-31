package net.sf.openrocket.rocketcomponent;

import java.util.ArrayList;
import java.util.Collection;

import net.sf.openrocket.l10n.Translator;
import net.sf.openrocket.startup.Application;
import net.sf.openrocket.util.BugException;
import net.sf.openrocket.util.Coordinate;

public class PodSet extends ComponentAssembly implements RingInstanceable {
	
	private static final Translator trans = Application.getTranslator();
	//private static final Logger log = LoggerFactory.getLogger(PodSet.class);
	
	protected int instanceCount = 2;

	protected double angularSeparation = Math.PI;
	protected double angularPosition_rad = 0;
	protected boolean autoRadialPosition = false;
	protected double radialPosition_m = 0;
	
	public PodSet() {
		this.instanceCount = 2;
		this.relativePosition = Position.BOTTOM;
	}
	
	@Override
	public boolean allowsChildren() {
		return true;
	}
	
	@Override
	public String getComponentName() {
		//// Stage
		return trans.get("PodSet.PodSet");
	}
	
	
	// not strictly accurate, but this should provide an acceptable estimate for total vehicle size 
	@Override
	public Collection<Coordinate> getComponentBounds() {
		Collection<Coordinate> bounds = new ArrayList<Coordinate>(8);
		double x_min = Double.MAX_VALUE;
		double x_max = Double.MIN_VALUE;
		double r_max = 0;
		
		Coordinate[] instanceLocations = this.getLocations();
		
		for (Coordinate currentInstanceLocation : instanceLocations) {
			if (x_min > (currentInstanceLocation.x)) {
				x_min = currentInstanceLocation.x;
			}
			if (x_max < (currentInstanceLocation.x + this.length)) {
				x_max = currentInstanceLocation.x + this.length;
			}
			if (r_max < (this.getRadialOffset())) {
				r_max = this.getRadialOffset();
			}
		}
		addBound(bounds, x_min, r_max);
		addBound(bounds, x_max, r_max);
		
		return bounds;
	}
	
	/**
	 * Check whether the given type can be added to this component.  A Stage allows
	 * only BodyComponents to be added.
	 *
	 * @param type The RocketComponent class type to add.
	 *
	 * @return Whether such a component can be added.
	 */
	@Override
	public boolean isCompatible(Class<? extends RocketComponent> type) {
		return BodyComponent.class.isAssignableFrom(type);
	}

		
	@Override
	public double getInstanceAngleIncrement(){
		return angularSeparation;
	}
	
	@Override
	public double[] getInstanceAngles(){
		final double baseAngle = getAngularOffset();
		final double incrAngle = getInstanceAngleIncrement();
		
		double[] result = new double[ getInstanceCount()]; 
		for( int i=0; i<getInstanceCount(); ++i){
			result[i] = baseAngle + incrAngle*i;
		}
		
		return result;
	}
	
	@Override
	public Coordinate[] getInstanceOffsets(){
		checkState();
		
		Coordinate[] toReturn = new Coordinate[this.instanceCount];
		final double[] angles = getInstanceAngles();
		for (int instanceNumber = 0; instanceNumber < this.instanceCount; instanceNumber++) {
			final double curY = this.radialPosition_m * Math.cos(angles[instanceNumber]);
			final double curZ = this.radialPosition_m * Math.sin(angles[instanceNumber]);
			toReturn[instanceNumber] = new Coordinate(0, curY, curZ );
		}
		
		return toReturn;
	}
	
	@Override
	public boolean isAfter() {
		return false;
	}
	
	/** 
	 * Stages may be positioned relative to other stages. In that case, this will set the stage number 
	 * against which this stage is positioned.
	 * 
	 * @return the stage number which this stage is positioned relative to
	 */
	public int getRelativeToStage() {
		if (null == this.parent) {
			return -1;
		} else if (this.parent instanceof PodSet) {
			return this.parent.parent.getChildPosition(this.parent);
		}
		
		return -1;
	}
	
	@Override
	public double getAxialOffset() {
		double returnValue = Double.NaN;
		
		if (this.isAfter()){
			// remember the implicit (this instanceof Stage)
			throw new BugException("found a Stage on centerline, but not positioned as AFTER.  Please fix this! " + this.getName() + "  is " + this.getRelativePosition().name());
		} else {
			returnValue = super.asPositionValue(this.relativePosition);
		}
		
		if (0.000001 > Math.abs(returnValue)) {
			returnValue = 0.0;
		}
		
		return returnValue;
	}

	@Override
	public double getAngularOffset() {
		return this.angularPosition_rad;
	}

	@Override
	public String getPatternName(){
		return (this.getInstanceCount() + "-ring");
	}
	
	@Override
	public boolean getAutoRadialOffset(){
		return this.autoRadialPosition;
	}
	
	public void setAutoRadialOffset( final boolean enabled ){
		this.autoRadialPosition = enabled;
		fireComponentChangeEvent(ComponentChangeEvent.BOTH_CHANGE);	
	}

	@Override
	public double getRadialOffset() {
		return this.radialPosition_m;
	}
	
	@Override
	public int getInstanceCount() {
		return this.instanceCount;
	}
	
	
	@Override 
	public void setInstanceCount( final int newCount ){
		mutex.verify();
		if ( newCount < 1) {
			// there must be at least one instance....   
			return;
		}
		
        this.instanceCount = newCount;
        this.angularSeparation = Math.PI * 2 / this.instanceCount;
        fireComponentChangeEvent(ComponentChangeEvent.BOTH_CHANGE);
	}
	
	@Override
	protected StringBuilder toDebugDetail() {
		StringBuilder buf = super.toDebugDetail();
		//		if (-1 == this.getRelativeToStage()) {
		//			System.err.println("      >>refStageName: " + null + "\n");
		//		} else {
		//			Stage refStage = (Stage) this.parent;
		//			System.err.println("      >>refStageName: " + refStage.getName() + "\n");
		//			System.err.println("      ..refCenterX: " + refStage.position.x + "\n");
		//			System.err.println("      ..refLength: " + refStage.getLength() + "\n");
		//		}
		return buf;
	}

	@Override
	public void setAngularOffset(double angle_rad) {
		mutex.verify();
		this.angularPosition_rad = angle_rad;
		fireComponentChangeEvent(ComponentChangeEvent.BOTH_CHANGE);		
	}

	@Override
	public void setRadialOffset(double radius_m) {
		mutex.verify();
		this.radialPosition_m = radius_m;
		fireComponentChangeEvent(ComponentChangeEvent.BOTH_CHANGE);
	}
	
	@Override
	protected void update(){
		super.update();

		if( this.autoRadialPosition){
			if( null == this.parent ){
				this.radialPosition_m = this.getOuterRadius();
			}else if( BodyTube.class.isAssignableFrom(this.parent.getClass())) {
				BodyTube parentBody = (BodyTube)this.parent;
				this.radialPosition_m = this.getOuterRadius() + parentBody.getOuterRadius();				
			}
		}
	}
}
