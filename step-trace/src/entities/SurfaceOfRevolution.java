package entities;

import utils.RegExp;

public class SurfaceOfRevolution extends AbstractEntity implements SurfaceGeometry {
	
public static final String _SURFACE_OF_REVOLUTION = "SURFACE_OF_REVOLUTION";
	
	private Axis2Placement3D axis2Placement3D;
	private BSplineCurveKnots bSplineCurveKnots;

	// SURFACE_OF_REVOLUTION(NAME,CURVE,POSITION);
	public SurfaceOfRevolution(String lineId) {
		super(lineId);
		String val = RegExp.getValueBetweenDoubleParentheses(linesMap.get(lineId));
		String surfVal = linesMap.get(lineId);
		axis2Placement3D = new Axis2Placement3D(RegExp.getParameter(surfVal, 2, 4), this);
		bSplineCurveKnots = new BSplineCurveKnots(RegExp.getParameter(surfVal, 1, 8));
	}
	
	@Override
	public String getEntityName() {
		return _SURFACE_OF_REVOLUTION;
	}

	public Axis2Placement3D getAxis2Placement3D() {
		return axis2Placement3D;
	}
	
	public BSplineCurveKnots BSplineCurveKnots() {
		return bSplineCurveKnots;
	}
	
	@Override
	public Direction getDirection() {
		return axis2Placement3D.getAxis();
	}
}
