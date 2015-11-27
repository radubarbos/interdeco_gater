package ro.barbos.gater.cutprocessor.strategy;

import ro.barbos.gater.cutprocessor.*;
import ro.barbos.gater.cutprocessor.diagram.*;
import ro.barbos.gater.model.LumberLog;
import ro.barbos.gater.model.Product;

import java.awt.*;
import java.util.*;
import java.util.List;

public class RotateOne180CutStrategy extends AbstractCutStrategy {

    double leftSegmentSize = 0;
    double bottomSegmentSize = 0;

    double leftCutLimit;
    double rightCutLimit;

    public CutDiagram cutLumberLog(LumberLog lumberLog, List<Product> products, List<Integer> targetPieces) {
        CutDiagram diagram = new CutDiagram();
        diagram.cutInfo = new CutDiagramInfo();
        Map<String, Color> colors = getColorMap(products);
        diagram.cutInfo.colors = colors;
        ProductsCutData cuttingState = CuterManager.analize(products, targetPieces);
        if(cuttingState.getMetaData().size() == 0) {
            return diagram;
        }
        cuttingState.setLengthOptimization(lengthOptimization);
        LumberLogCutContext lumberContext = new LumberLogCutContext(lumberLog, CutterSettings.EDGE_TOLERANCE);
        lumberContext.setCutInfo(diagram);

        double currentYPosition = -lumberContext.getRadius();
        currentYPosition += CutterSettings.GATER_THICK;

        double startMultibladeMatch = (lumberContext.getRadius() - lumberContext.getHalfSquare())/2;
        startMultibladeMatch = - lumberContext.getRadius() + startMultibladeMatch;

        leftCutLimit = -1*lumberLog.getSmallRadius()/2;
        rightCutLimit = lumberLog.getSmallRadius()/2;
        Map<String, Object> multiBladeData = getBestInnerMultilade(startMultibladeMatch, lumberContext.getHalfSquare(), CutterSettings.GATER_THICK, lumberContext.getRadius(), lumberContext.getInsideSquareSide(), products, targetPieces, lumberLog);
        if(multiBladeData.size() > 0) {
            cuttingState = (ProductsCutData)multiBladeData.get("CUT_STATE");
            double startMultiBlade = (double)multiBladeData.get("START");
            double endMultiBlade = (double)multiBladeData.get("END");
            double leftMultiBlade = (double)multiBladeData.get("LEFT");
            double rightMultiBlade = (double)multiBladeData.get("RIGHT");
            if(leftMultiBlade != 0) {
                leftCutLimit = leftMultiBlade;
            }
            if(rightMultiBlade != 0) {
                rightCutLimit = rightMultiBlade;
            }
            //draw left segment
           // cutEdgeSegment(leftMultiBlade, lumberContext, cuttingState, diagram, colors, -1, 3);
           // diagram.gaterCutFlow.add(new GaterRotate());
           // diagram.gaterCutFlow.add(new GaterRotate());
            //draw right segment
           // cutEdgeSegment(-1*rightMultiBlade, lumberContext, cuttingState, diagram, colors, -1, 1);
            diagram.gaterCutFlow.add(new GaterRotate());

            //draw bottom
            cutEdgeSegment(endMultiBlade, lumberContext, cuttingState, diagram, colors, 1, 2);

            //draw top segment
            cutEdgeSegment(startMultiBlade, lumberContext, cuttingState, diagram, colors, -1, 0);

            diagram.steps.setMultiBlade((LumberLogSegmentSteps)multiBladeData.get("CUTS"));

            List<Product> productsToCut = (List<Product>)multiBladeData.get("PRODUCT_LIST");
            List<MultibladeCutSlide> multiBladeSlides = (List<MultibladeCutSlide>)multiBladeData.get("MULTIBLADE_SLIDE");
            if(multiBladeSlides != null) {
                for(int index =0; index < multiBladeSlides.size(); index++) {
                    Product productToCut = productsToCut.get(index);
                    MultibladeCutSlide multiBladeSlide = multiBladeSlides.get(index);
                    multiBladeSlide.color = colors.get(productToCut.getName());
                    diagram.multiBladeSlides.add(multiBladeSlide);
                }
            }

        }
        CuterManager.setCutLayoutData(lumberLog, diagram.cutInfo, cuttingState);
        diagram.steps.compileSteps();
        return diagram;
    }

    private Map<String, Object> getBestInnerMultilade(double start, double end, double step, double radius, double innerSquare, List<Product> products, List<Integer> targetPieces, LumberLog lumberLog) {
        Map<String, Object> results = new HashMap<String, Object>();
        double bestMatchCoverage = 0;
        double bestVolumeCoverage = 0;
        double bestMatchStart = start;
        double bestMatchEnd = 0;
        double bestMatchLeft = 0;
        double bestMatchRight = 0;
        double currentPosition = start;
        List<Product> multibladeProductCutList = null;
        List<MultibladeCutSlide> multiBladeSlides = null;
        ProductsCutData bestCuttingState = null;
        LumberLogSegmentSteps segmentSteps = new LumberLogSegmentSteps();

        while(currentPosition < end) {
            for(int index = 0; index < products.size(); index++) {
                if(products.get(index).getLength() > lumberLog.getRealLength()) {
                    continue;
                }
                ProductsCutData cuttingState = CuterManager.analize(products, targetPieces);
                cuttingState.setLengthOptimization(lengthOptimization);
                double tempPosition = currentPosition;
                double tempCoverage = 0;
                double tempVolumeCoverage = 0;
                double tempEndPosition = 0;
                double tempLeftPosition = 0;
                double tempRightPosition = 0;
                List<Product> tempMultibladeProductCutList = new ArrayList<>();
                List<MultibladeCutSlide> tempMultiBladeSlides = new ArrayList<>();
                List<Double> bestCuts = new ArrayList<>();

                int gaterCut = computeGaterCut(radius, tempPosition, null);
                Product productToCut = products.get(index);
                tempMultibladeProductCutList.add(productToCut);
                int nextGuterCut = computeGaterCut(radius, tempPosition + productToCut.getWidth(), null);
                int pieces = getMultibladePieces(Math.min(gaterCut, nextGuterCut), productToCut.getThick());
                cuttingState.addCutPieces(productToCut, pieces, lumberLog.getPlate());
                MultibladeCutSlide multiBladeSlide = createMultiBladeSlide(Math.min(gaterCut, nextGuterCut), tempPosition, pieces, productToCut);
                multiBladeSlide.phase = CutPhase.THIRD;
                tempMultiBladeSlides.add(multiBladeSlide);
                double[] pos = getLeftRightPositions(multiBladeSlide);
                if(pos[0]<tempLeftPosition) tempLeftPosition = pos[0];
                if(pos[1]>tempRightPosition) tempRightPosition = pos[1];
                tempPosition += productToCut.getWidth();
                tempEndPosition = tempPosition;
                bestCuts.add(productToCut.getWidth().doubleValue());
                tempPosition += step;
                double coverageSurface = productToCut.getWidth() * productToCut.getThick();
                tempCoverage += pieces * coverageSurface;
                tempVolumeCoverage += coverageSurface * productToCut.getLength();
                while(tempPosition < end) {
                    gaterCut = computeGaterCut(radius, tempPosition, null);
                    CutContext cutContext = new CutContext();
                    cutContext.lumberLogRadius = radius;
                    cutContext.yPosition = tempPosition;
                    cutContext.lengthLimit = lumberLog.getRealLength();
                    cutContext.currentCut = gaterCut;
                    cutContext.innerSquare = innerSquare;
                    cutContext.widthLimit = innerSquare - tempPosition;
                    productToCut = cuttingState.getBestMultibladeProduct(cutContext);
                    if(productToCut != null) {
                        nextGuterCut = computeGaterCut(radius, tempPosition + productToCut.getWidth());
                        double smallestCut = Math.min(gaterCut, nextGuterCut);
                        pieces = getMultibladePieces(smallestCut, productToCut.getThick());
                        cuttingState.addCutPieces(productToCut, pieces, lumberLog.getPlate());
                        coverageSurface = productToCut.getWidth() * productToCut.getThick();
                        tempCoverage += pieces * coverageSurface;
                        tempVolumeCoverage += coverageSurface * productToCut.getLength();
                        tempMultibladeProductCutList.add(productToCut);
                        multiBladeSlide = createMultiBladeSlide(smallestCut, tempPosition, pieces, productToCut);
                        multiBladeSlide.phase = CutPhase.THIRD;
                        tempMultiBladeSlides.add(multiBladeSlide);
                        pos = getLeftRightPositions(multiBladeSlide);
                        if(pos[0]<tempLeftPosition) tempLeftPosition = pos[0];
                        if(pos[1]>tempRightPosition) tempRightPosition = pos[1];
                        tempPosition += productToCut.getWidth();
                        tempEndPosition = tempPosition;
                        double lastMultibladeCut = lumberLog.getSmallRadius()/2 + tempPosition;
                        bestCuts.add(productToCut.getWidth().doubleValue());
                    }
                    tempPosition += step;
                }
                if(((tempCoverage > bestMatchCoverage) && !lengthOptimization.doOptimization) || (lengthOptimization.doOptimization && (tempVolumeCoverage > bestVolumeCoverage))) {
                    bestMatchCoverage = tempCoverage;
                    bestVolumeCoverage = tempVolumeCoverage;
                    bestMatchStart = currentPosition;
                    bestMatchEnd = tempEndPosition;
                    bestMatchLeft = tempLeftPosition;
                    bestMatchRight = tempRightPosition;
                    multibladeProductCutList = tempMultibladeProductCutList;
                    multiBladeSlides = tempMultiBladeSlides;
                    bestCuttingState = cuttingState;
                    segmentSteps.setCuts(bestCuts);
                }
            }
            currentPosition += step;
        }
        if(bestMatchCoverage > 0) {
            results.put("START", bestMatchStart);
            results.put("END", bestMatchEnd);
            results.put("LEFT", bestMatchLeft);
            results.put("RIGHT", bestMatchRight);
            results.put("PRODUCT_LIST", multibladeProductCutList);
            results.put("MULTIBLADE_SLIDE", multiBladeSlides);
            results.put("CUT_STATE", bestCuttingState);
            results.put("CUTS", segmentSteps);
        }

        return results;
    }

    private double[] getLeftRightPositions(MultibladeCutSlide multiBlade) {
        double[] pos = new double[2];
        pos[0] = multiBlade.x;
        pos[1] = multiBlade.x + (multiBlade.pieces * (multiBlade.pieceWidth + CutterSettings.MULTIBLADE)) - CutterSettings.MULTIBLADE;
        return pos;
    }

    private void cutEdgeSegment(double start, LumberLogCutContext lumberContext, ProductsCutData cuttingState,
                                CutDiagram diagram, Map<String, Color> colors, int direction, int segmentPosition) {
        double yPosition = start;
        double end = direction * lumberContext.getRadius();
        double lastCut = 0;
        LumberLogSegmentSteps segmentSteps = new LumberLogSegmentSteps();
        CutPhase phase = null;
        if(segmentPosition == 3) {
            diagram.steps.setLeft(segmentSteps);
            phase = CutPhase.FIRST;
        }
        if(segmentPosition == 2) {
            diagram.steps.setBottom(segmentSteps);
            phase = CutPhase.SECOND;
        }
        if(segmentPosition == 1) {
            diagram.steps.setRight(segmentSteps);
            phase = CutPhase.SECOND;
        }
        if(segmentPosition == 0) {
            diagram.steps.setTop(segmentSteps);
            phase = CutPhase.FIRST;
        }

        if(segmentPosition == 3) {
            leftSegmentSize = lumberContext.getLumberLog().getSmallRadius()/2 - Math.abs(yPosition);
        }
        if(segmentPosition == 2) {
            bottomSegmentSize = lumberContext.getLumberLog().getSmallRadius()/2 - Math.abs(yPosition);
        }

        // as a temporary cut math error because of double computing
        //for now add a extra offset as blade thick around multi blade positions
        //not for top segment, because will mess up the rest of the calculation
        if(segmentPosition != 0) {
            yPosition += direction * CutterSettings.MULTIBLADE_TOLERANCE;
        }
        double startSegmentCut = yPosition;
        while( direction == -1 ? (yPosition > end) : (yPosition < end)) {
            yPosition += direction * CutterSettings.GATER_THICK;
            if(segmentSteps.cuts.size() == 0) {
                //set the most inner cut, before the multi blade
                startSegmentCut = yPosition;
            }
            int gaterCut = computeGaterCut(lumberContext.getRadius(), yPosition);
            if(segmentPosition == 2) {
                //for bottom adjust cut on the right and left side
                gaterCut = (int)Math.min(gaterCut, rightCutLimit - leftCutLimit);
                gaterCut += 5;
            }
            CutContext cutContext = lumberContext.createCutContext(gaterCut, yPosition);
            cutContext.cutDirection = direction;
            Product productToCut = cuttingState.getBestMatchSmallestProduct(cutContext);
            if(productToCut != null) {
                if(direction == -1) yPosition += direction * productToCut.getThick();
                GaterSlide cutSlide = createGaterSlide(productToCut);
                if(phase != null) cutSlide.phase = phase;
                cutSlide.y = yPosition;
                diagram.gaterCutFlow.add(cutSlide);
                if(direction == 1) yPosition += direction * productToCut.getThick();
                int nextGaterCut = computeGaterCut(lumberContext.getRadius(), yPosition);
                if(segmentPosition == 2) {
                    //for bottom trim to left and right, can't use the entire circle
                    nextGaterCut = (int)Math.min(nextGaterCut, rightCutLimit - leftCutLimit);
                }
                int pieces = getMultibladePieces(nextGaterCut, productToCut.getWidth());
                cuttingState.addCutPieces(productToCut, pieces, lumberContext.getLumberLog().getPlate());
                cutSlide.pieces = pieces;
                setProductXInGaterSlide(cutSlide, productToCut.getWidth());
                cutSlide.color = colors.get(productToCut.getName());
                lastCut = yPosition + direction * CutterSettings.GATER_THICK;
                segmentSteps.addCut(productToCut.getThick().doubleValue());
            }
            else {
                break;
            }
        }
        if(segmentSteps.cuts.size() > 0) {
            lastCut = lumberContext.getLumberLog().getSmallRadius()/2 - Math.abs(lastCut);
            double startGaterCut = CuterManager.computeStartFromTop(lumberContext.getLumberLogDiameter(), lastCut);
            if(segmentPosition == 1) {
                //right
                startGaterCut -= leftSegmentSize;
            }
            else if(segmentPosition == 0) {
                startGaterCut -= bottomSegmentSize;
            }
            segmentSteps.addCut(startGaterCut);
            Collections.reverse(segmentSteps.cuts);
        }
        else {
            lastCut = lumberContext.getLumberLog().getSmallRadius()/2 - Math.abs(startSegmentCut);
            double startGaterCut = CuterManager.computeStartFromTop(lumberContext.getLumberLogDiameter(), lastCut);

            //no cuts, taie calota, adjust just top and right
            if(segmentPosition == 0) {
                startGaterCut -= bottomSegmentSize;
            }
            else if(segmentPosition == 1) {
                startGaterCut -= leftSegmentSize;
            }
            segmentSteps.addCut(startGaterCut);
        }
    }

    @Override
    public int getStrategyCode() {
        return 6;
    }
}
