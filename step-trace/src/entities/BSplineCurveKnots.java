package entities;

import java.sql.Array;
import java.util.Arrays;

public class BSplineCurveKnots extends AbstractEntity implements EdgeGeometry {
	
	public static final String _B_SPLINE_CURVE_WITH_KNOTS = "B_SPLINE_CURVE_WITH_KNOTS";
	private Axis2Placement3D axis2Placement3D;

	// B_SPLINE_CURVE_KNOTS(name,degree,(control points),curve form,curve closed,self intersect,(knot multiplicities),(knots),knots specification)
	public BSplineCurveKnots(String lineId) {
		super(lineId);
		String bSplineCurveVal = linesMap.get(lineId);
		String[] cps;
		
		cps = cartesianPoints(bSplineCurveVal);
		//for(String bSplineCp : cartesianPoints(bSplineCurveVal)) {
			//cp = RegExp.getParameter(bSplineCp, 3, 9);
			// System.out.println(bSplineCp);
		//}
	}
	
	public String[] cartesianPoints(String val, int length) {
	System.out.println(val);
	String[] cp = val.split("/#[0-9]+/g");
	int size = cp.length, i=0;
	} 
	
	@Override
	public String getEntityName() {
		return _B_SPLINE_CURVE_WITH_KNOTS;
	}
	
	public Axis2Placement3D getAxis2Placement3D() {
		return axis2Placement3D;
	}
	
	@Override
	public Direction getDirection() {
		return axis2Placement3D.getAxis();
	}
	
}