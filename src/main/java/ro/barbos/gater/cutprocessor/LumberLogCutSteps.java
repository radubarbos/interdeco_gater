package ro.barbos.gater.cutprocessor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ro.barbos.gater.cutprocessor.diagram.GaterCutStep;

public class LumberLogCutSteps implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private LumberLogSegmentSteps top;
	private LumberLogSegmentSteps right;
	private LumberLogSegmentSteps bottom;
	private LumberLogSegmentSteps left;
	private LumberLogSegmentSteps multiBlade;
	
	private List<GaterCutStep> stepSequence;
	
	public void compileSteps() {
		
		stepSequence = new ArrayList<>();
		
		boolean left = this.left != null && this.left.cuts.size()>0;
		boolean bottom = this.bottom != null && this.bottom.cuts.size()>0;
		boolean right = this.right != null && this.right.cuts.size()>1;
		boolean top = this.top != null && this.top.cuts.size()>0;
		boolean multiBlade = this.multiBlade != null;
		
		if(left) {
			List<Double> segmentSteps = this.left.cuts;
			for(int index = 0; index < segmentSteps.size(); index++) {
				GaterCutStep compliedStep = new GaterCutStep();
				if(index == 0) {
					compliedStep.isStart = true;
				}
				compliedStep.value = segmentSteps.get(index);
				stepSequence.add(compliedStep);
			}
			
		}
		if(left && right) {
			GaterCutStep compliedStep = new GaterCutStep();
			compliedStep.isRotate = true;
			compliedStep.value = 180D;
			stepSequence.add(compliedStep);
		}
		else if(left && bottom) {
			GaterCutStep compliedStep = new GaterCutStep();
			compliedStep.isRotate = true;
			compliedStep.value = 90D;
			stepSequence.add(compliedStep);
		}
		
		if(right) {
			List<Double> segmentSteps = this.right.cuts;
			for(int index = 0; index < segmentSteps.size(); index++) {
				GaterCutStep compliedStep = new GaterCutStep();
				if(index == 0) {
					compliedStep.isStart = true;
				}
				compliedStep.value = segmentSteps.get(index);
				stepSequence.add(compliedStep);
			}
		}
		if(right && (bottom || top)) {
			GaterCutStep compliedStep = new GaterCutStep();
			compliedStep.isRotate = true;
			compliedStep.value = 90D;
			stepSequence.add(compliedStep);
		}
		if(bottom) {
			List<Double> segmentSteps = this.bottom.cuts;
			for(int index = 0; index < segmentSteps.size(); index++) {
				GaterCutStep compliedStep = new GaterCutStep();
				if(index == 0) {
					compliedStep.isStart = true;
				}
				compliedStep.value = segmentSteps.get(index);
				stepSequence.add(compliedStep);
			}
			GaterCutStep compliedStep = new GaterCutStep();
			compliedStep.isRotate = true;
			compliedStep.value = 180D;
			stepSequence.add(compliedStep);
		}
		//show top segment
		if(top) {
			List<Double> segmentSteps = this.top.cuts;
			for(int index = 0; index < segmentSteps.size(); index++) {
				GaterCutStep compliedStep = new GaterCutStep();
				if(index == 0) {
					compliedStep.isStart = true;
				}
				compliedStep.value = segmentSteps.get(index);
				stepSequence.add(compliedStep);
			}
		}
		if(multiBlade) {
			List<Double> segmentSteps = this.multiBlade.cuts;
			double endMultiBlade = segmentSteps.size();
			if(bottom) endMultiBlade--;
			for(int index =0; index < endMultiBlade; index++) {
				GaterCutStep compliedStep = new GaterCutStep();
				compliedStep.value = segmentSteps.get(index);
				stepSequence.add(compliedStep);
			}
		}
	}
	
	/**
	 * @return the top
	 */
	public LumberLogSegmentSteps getTop() {
		return top;
	}
	/**
	 * @param top the top to set
	 */
	public void setTop(LumberLogSegmentSteps top) {
		this.top = top;
	}
	/**
	 * @return the right
	 */
	public LumberLogSegmentSteps getRight() {
		return right;
	}
	/**
	 * @param right the right to set
	 */
	public void setRight(LumberLogSegmentSteps right) {
		this.right = right;
	}
	/**
	 * @return the bottom
	 */
	public LumberLogSegmentSteps getBottom() {
		return bottom;
	}
	/**
	 * @param bottom the bottom to set
	 */
	public void setBottom(LumberLogSegmentSteps bottom) {
		this.bottom = bottom;
	}
	/**
	 * @return the left
	 */
	public LumberLogSegmentSteps getLeft() {
		return left;
	}
	/**
	 * @param left the left to set
	 */
	public void setLeft(LumberLogSegmentSteps left) {
		this.left = left;
	}
	/**
	 * @return the multiBlade
	 */
	public LumberLogSegmentSteps getMultiBlade() {
		return multiBlade;
	}
	/**
	 * @param multiBlade the multiBlade to set
	 */
	public void setMultiBlade(LumberLogSegmentSteps multiBlade) {
		this.multiBlade = multiBlade;
	}
	/**
	 * @return the stepSequence
	 */
	public List<GaterCutStep> getStepSequence() {
		return stepSequence;
	}
	/**
	 * @param stepSequence the stepSequence to set
	 */
	public void setStepSequence(List<GaterCutStep> stepSequence) {
		this.stepSequence = stepSequence;
	}
	
	
	
}
