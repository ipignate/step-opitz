package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import keepers.CartesianPointKeeper;
import keepers.ClosedShellKeeper;
import utils.RegExp;

public class ClosedShell extends AbstractEntity {

	public final static String _CLOSED_SHELL = "CLOSED_SHELL";
	private List<AdvancedFace> list = new ArrayList<AdvancedFace>();
	private int throughHolesCount, auxiliaryHolesCount;
	
	public List<AdvancedFace> getAdvancedFaces() {
		return list;
	}

	// CLOSED_SHELL ( 'NONE', ( #22, #19, #24, #23, #21, #20 ) ) 
	public ClosedShell(String lineId) {
		super(lineId);
		String val = RegExp.getValueBetweenDoubleParentheses(linesMap.get(lineId));
		for (String advFaceId : Arrays.asList(val.split(","))) {
			AdvancedFace af = new AdvancedFace(advFaceId.trim());
			boolean isDuplicate = false;
			if (af.getSurfGeometry() instanceof CylindricalSurface) {
				for (AdvancedFace afInner : list) {
					List<EdgeCurve> l1 = af.getFaceOuterBound().getAllCircleEdgeCurves();
					List<EdgeCurve> l2 = afInner.getFaceOuterBound().getAllCircleEdgeCurves();
					if (afInner.getSurfGeometry() instanceof CylindricalSurface
							&& afInner.getSurfGeometry().equals(af.getSurfGeometry())
							&& l1.size() == 2
							&& l2.size() == 2
							&& (l1.get(0).getEdgeGeometry().getDirection().equals(l2.get(0).getEdgeGeometry().getDirection()) || l1.get(0)
									.getEdgeGeometry().getDirection().equals(l2.get(1).getEdgeGeometry().getDirection()))
							&& (l1.get(1).getEdgeGeometry().getDirection().equals(l2.get(0).getEdgeGeometry().getDirection())
							|| l1.get(1).getEdgeGeometry().getDirection().equals(l2.get(1).getEdgeGeometry().getDirection()))) {
						isDuplicate = true;
						break;
					}
				}
			}
			if (!isDuplicate) {
				list.add(af);
			}
		}
		ClosedShellKeeper.set(this);
		markThroughHoles();
		markAuxiliaryHoles();
	}

	@Override
	public String getEntityName() {
		return _CLOSED_SHELL;
	}
	
	/*public void getParallel() {
		for (AdvancedFace af : list) {
			Axis2Placement3D a2p3D = af.getSurfGeometry().getAxis2Placement3D();
			for (AdvancedFace aFinner : list) {
				if (af.getLineId().equals(aFinner.getLineId())) {
					continue;
				}
				Axis2Placement3D a2p3DInner = aFinner.getSurfGeometry().getAxis2Placement3D();
				if (a2p3D.getAxis().equals(a2p3DInner.getAxis()) && a2p3D.getRef_direction().equals(a2p3DInner.getRef_direction())) {
					System.out.println("da " + af.getLineId() + ", " + aFinner.getLineId());
				}
			}
		}
	}*/
	
	public AdvancedFace getBottomPlane() {
		for (AdvancedFace af : list) {
			Axis2Placement3D a2p3D = af.getSurfGeometry().getAxis2Placement3D();
			if (af.getSurfGeometry() instanceof Plane
					&& a2p3D.getCartesianPoint().getY() == CartesianPointKeeper.getMaxShapeMeasures().minY && a2p3D.getAxis().isYOriented()) {
				return af;
			}
		}
		return null;
	}
	
	public AdvancedFace getFrontPlane() {
		for (AdvancedFace af : list) {
			Axis2Placement3D a2p3D = af.getSurfGeometry().getAxis2Placement3D();
			if (af.getSurfGeometry() instanceof Plane
					&& a2p3D.getCartesianPoint().getZ() == CartesianPointKeeper.getMaxShapeMeasures().maxZ && a2p3D.getAxis().isZOriented()) {
				return af;
			}
		}
		return null;
	}
	
	public AdvancedFace getBackPlane() {
		for (AdvancedFace af : list) {
			Axis2Placement3D a2p3D = af.getSurfGeometry().getAxis2Placement3D();
			if (af.getSurfGeometry() instanceof Plane
					&& a2p3D.getCartesianPoint().getZ() == CartesianPointKeeper.getMaxShapeMeasures().minZ && a2p3D.getAxis().isZOriented()) {
				return af;
			}
		}
		return null;
	}
	
	public AdvancedFace getTopPlane() {
		for (AdvancedFace af : list) {
			Axis2Placement3D a2p3D = af.getSurfGeometry().getAxis2Placement3D();
			if (af.getSurfGeometry() instanceof Plane
					&& a2p3D.getCartesianPoint().getY() == CartesianPointKeeper.getMaxShapeMeasures().maxY && a2p3D.getAxis().isYOriented()) {
				return af;
			}
		}
		return null;
	}
	
	public AdvancedFace getLeftPlane() {
		for (AdvancedFace af : list) {
			Axis2Placement3D a2p3D = af.getSurfGeometry().getAxis2Placement3D();
			if (af.getSurfGeometry() instanceof Plane
					&& a2p3D.getCartesianPoint().getX() == CartesianPointKeeper.getMaxShapeMeasures().minX && a2p3D.getAxis().isXOriented()) {
				return af;
			}
		}
		return null;
	}
	
	public AdvancedFace getRightPlane() {
		for (AdvancedFace af : list) {
			Axis2Placement3D a2p3D = af.getSurfGeometry().getAxis2Placement3D();
			if (af.getSurfGeometry() instanceof Plane
					&& a2p3D.getCartesianPoint().getX() == CartesianPointKeeper.getMaxShapeMeasures().maxX && a2p3D.getAxis().isXOriented()) {
				return af;
			}
		}
		return null;
	}
	
	public int getYOrientedPlaneFacesCount() {
		int res = 0;
		Set<Float> set = new HashSet<Float>();
		for (AdvancedFace af : list) {
			if (af.getSurfGeometry().getDirection().isYOriented() && af.getSurfGeometry() instanceof Plane) {
				float yValue = af.getSurfGeometry().getAxis2Placement3D().getCartesianPoint().getY();
				if (!set.contains(yValue)) {
					res++;
				}
				set.add(yValue);
			}
		}		
		return res;
	}
	
	public boolean hasUpperMachining() {
		if (getTopPlane() != null) {
			for (AdvancedFace af : list) {
				if (af.getSurfGeometry() instanceof CylindricalSurface && af.getSurfGeometry().getDirection().isZXOriented()) {
					return true;
				}
			}		
		}
		return false;
	}
	
	public AdvancedFace getAdvancedFaceByFaceBoundId(String id) {
		for (AdvancedFace af : list) {
			if (af.getFaceOuterBound().getLineId().equals(id)) {
				return af;
			}
		}
		return null;
	}
	
	public boolean isAllPlanes() {
		boolean res = true;
		for (AdvancedFace af : list) {
			res &= af.isPlane();
		}
		if (res) {
			System.out.println("all faces are planes");
		}
		return res;
	}
	
	// Gets all cylindrical surfaces even in groove (there are 2 cylinr), even
	// in flat rectangle with circular deviation, even with upper machining.
	// For non-rotational we don't care about amount of these cylinrSurfaces,
	// but for rotational - care
	public List<AdvancedFace> getCylindricalSurfacesWithoutThroughHoles() {
		List<AdvancedFace> res = new ArrayList<AdvancedFace>();
		for (AdvancedFace af : list) {
			if (af.getSurfGeometry() instanceof CylindricalSurface && !af.isThroughHole && !af.isAuxiliaryHole) {
				res.add(af);
			}
		}
		return res;
	}
	
	public int getThroughHolesCount() {
		return throughHolesCount;
	}
	
	private void markThroughHoles() {
		if (getBackPlane() != null && getFrontPlane() != null) {
			for (FaceBound f : getBackPlane().getFaceInnerBound()) {
				if (f.isCircle() && !f.isAdjacentMarkedAsThroughHole() && f.hasOppositeCircle(getFrontPlane())) {
					f.markAdjacentAsThroughHole();
					throughHolesCount++;
				}
			}
		}
		if (getLeftPlane() != null && getRightPlane() != null) {
			for (FaceBound f : getLeftPlane().getFaceInnerBound()) {
				if (f.isCircle() && !f.isAdjacentMarkedAsThroughHole() && f.hasOppositeCircle(getRightPlane())) {
					f.markAdjacentAsThroughHole();
					throughHolesCount++;
				}
			}
		}
		if (getTopPlane() != null && getBottomPlane() != null) {
			for (FaceBound f : getTopPlane().getFaceInnerBound()) {
				if (f.isCircle() && !f.isAdjacentMarkedAsThroughHole() && f.hasOppositeCircle(getBottomPlane())) {
					f.markAdjacentAsThroughHole();
					throughHolesCount++;
				}
			}
		} 
	}
	
	private int getInnerCirclesCountForOnePlane(AdvancedFace af) {
		int res = 0;
		for (FaceBound fb : af.getFaceInnerBound()) {
			if (fb.isCircle() && fb.getAdjacentCylinder() != null) {
				fb.getAdjacentCylinder().isAuxiliaryHole = true;
				res++;
			}
		}
		return res;
	}
	
	private boolean isNotFoundOrHasNoInnerCircles(AdvancedFace plane) {
		return plane == null || getInnerCirclesCountForOnePlane(plane) == 0;
	}
	
	public int getAuxiliaryHolesCount() {
		return auxiliaryHolesCount;
	}
	
	private void markAuxiliaryHoles() {
		int res = 0;
		if (getFrontPlane() != null && isNotFoundOrHasNoInnerCircles(getBackPlane())) {
			res += getInnerCirclesCountForOnePlane(getFrontPlane());
		} else if (getBackPlane() != null && isNotFoundOrHasNoInnerCircles(getFrontPlane())) {
			res += getInnerCirclesCountForOnePlane(getBackPlane());
		} else if (getLeftPlane() != null && isNotFoundOrHasNoInnerCircles(getRightPlane())) {
			res += getInnerCirclesCountForOnePlane(getLeftPlane());
		} else if (getRightPlane() != null && isNotFoundOrHasNoInnerCircles(getLeftPlane())) {
			res += getInnerCirclesCountForOnePlane(getRightPlane());
		} else if (getTopPlane() != null && isNotFoundOrHasNoInnerCircles(getBottomPlane())) {
			res += getInnerCirclesCountForOnePlane(getTopPlane());
		} else if (getBottomPlane() != null && isNotFoundOrHasNoInnerCircles(getTopPlane())) {
			res += getInnerCirclesCountForOnePlane(getBottomPlane());
		}
		auxiliaryHolesCount = res;
	}

}
