package entities;

import utils.RegExp;
import java.util.ArrayList;


public class BSplineSurfaceKnots extends AbstractEntity implements SurfaceGeometry {
	
	public static final String _B_SPLINE_SURFACE_WITH_KNOTS = "B_SPLINE_SURFACE_WITH_KNOTS";
	private BSplineSurfaceKnots bSplineSurfaceKnots;

	// SURFACE_OF_REVOLUTION(NAME,CURVE,POSITION);
	public BSplineSurfaceKnots(String lineId) {
		super(lineId);
		String surfVal = linesMap.get(lineId);
		ArrayList<String> cps = utils.RegExp.getHashParams(surfVal);
	}
	
	@Override
	public String getEntityName() {
		return _B_SPLINE_SURFACE_WITH_KNOTS;
	}
	
	public BSplineSurfaceKnots BSplineCurveKnots() {
		return bSplineSurfaceKnots;
	}
	
	@Override
	public Direction getDirection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Axis2Placement3D getAxis2Placement3D() {
		// TODO Auto-generated method stub
		return null;
	}
}
