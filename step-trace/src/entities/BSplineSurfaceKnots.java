package entities;

import utils.RegExp;


public class BSplineSurfaceKnots extends AbstractEntity implements SurfaceGeometry {
	
	public static final String _B_SPLINE_SURFACE_WITH_KNOTS = "B_SPLINE_SURFACE_WITH_KNOTS";
	
	private Axis2Placement3D axis2Placement3D;
	private BSplineSurfaceKnots bSplineSurfaceKnots;

	// SURFACE_OF_REVOLUTION(NAME,CURVE,POSITION);
	public BSplineSurfaceKnots(String lineId) {
		super(lineId);
		String val = RegExp.getValueBetweenDoubleParentheses(linesMap.get(lineId));
		String surfVal = linesMap.get(lineId);
		axis2Placement3D = new Axis2Placement3D(RegExp.getParameter(surfVal, 2, 4), this);
		bSplineSurfaceKnots = new BSplineSurfaceKnots(RegExp.getParameter(surfVal, 1, 13));
	}
	
	@Override
	public String getEntityName() {
		return _B_SPLINE_SURFACE_WITH_KNOTS;
	}

	public Axis2Placement3D getAxis2Placement3D() {
		return axis2Placement3D;
	}
	
	public BSplineCurveKnots BSplineCurveKnots() {
		return bSplineSurfaceKnots;
	}
	
	@Override
	public Direction getDirection() {
		return axis2Placement3D.getAxis();
	}
}
