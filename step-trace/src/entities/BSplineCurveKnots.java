package entities;

import utils.CommonUtils;
import utils.RegExp;
import java.util.ArrayList;
import java.util.List;

import keepers.CartesianPointKeeper;

public class BSplineCurveKnots extends AbstractEntity implements EdgeGeometry {
	
	public static final String _B_SPLINE_CURVE_WITH_KNOTS = "B_SPLINE_CURVE_WITH_KNOTS";
	private List <CartesianPoint> cartesianPoints;
	private CartesianPoint cp;
	private Direction dir;
	
	// B_SPLINE_CURVE_KNOTS(name,degree,(control points),curve form,curve closed,self intersect,(knot multiplicities),(knots),knots specification)
	public BSplineCurveKnots(String lineId) {
		super(lineId);
		String bSplineCurveVal = linesMap.get(lineId);
		ArrayList<String> bSplineCurveIds = RegExp.getHashParams(bSplineCurveVal);
		cartesianPoints = new ArrayList<CartesianPoint>();
		
		// handle the Cartesian points from the BSplineCurveIds parameters obtained
		String bSplineCurvePointId = bSplineCurveIds.get(0);
		String lineVal = linesMap.get(bSplineCurvePointId);
		String pointId = RegExp.getParameter(lineVal, 2, 3);
		cp = CartesianPointKeeper.getCartesianPoint(CommonUtils.getCartesianPointIdFromVertexPointId(pointId, linesMap));
		System.out.println(cp);
	}

	@Override
	public String getEntityName() {
		return _B_SPLINE_CURVE_WITH_KNOTS;
	}
	
	@Override
	public Direction getDirection() {
		return dir;
	}
}