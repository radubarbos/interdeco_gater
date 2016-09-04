package ro.barbos.gater.model;

import org.junit.Test;
import ro.barbos.TestUtils;
import ro.barbos.gater.data.MetricTools;

import static org.junit.Assert.assertEquals;

/**
 * Created by radu on 9/4/2016.
 */
public class LumberLogTransportEntryCostMatrixTest {

    /**
     * Checks the cost of 2 lumbers
     * lumber 1 = 100X100X1000 type 0 class 0
     * lumber 2 = 100X100X2000 type 0 class 1
     * <p/>
     * len     type  class
     * 1000   |    1   |
     * 2000   |       | 2
     */
    @Test
    public void testCostWith2LumbersSameStack() {
        LumberLogTransportEntry entry = new LumberLogTransportEntry();
        LumberLog lumber1 = TestUtils.createLumberLog(100D, 100D, 1000D, 0, 0, 0);
        LumberLog lumber2 = TestUtils.createLumberLog(100D, 100D, 2000D, 0, 1, 0);
        lumber1.setVolume(1D * MetricTools.MILIMETER_VOLUME_SCALE);
        lumber2.setVolume(2D * MetricTools.MILIMETER_VOLUME_SCALE);
        entry.addLumberLog(lumber1);
        entry.addLumberLog(lumber2);
        LumberLogTransportEntryCostMatrix matrix = new LumberLogTransportEntryCostMatrix();
        matrix.init(entry);

        LumberLogTransportEntryCostMatrixKey key1 = new LumberLogTransportEntryCostMatrixKey(lumber1.getLength().longValue(), lumber1.getLumberClass().intValue(),
                lumber1.getLumberType().intValue());
        LumberLogTransportEntryCostMatrixKey key2 = new LumberLogTransportEntryCostMatrixKey(lumber2.getLength().longValue(), lumber2.getLumberClass().intValue(),
                lumber2.getLumberType().intValue());
        matrix.getCellData().get(key1).setCost(100);//1RON
        matrix.getCellData().get(key2).setCost(200);//2RON

        matrix.calculateTotalCost();

        assertEquals(5D, matrix.calculateTotalCost(), 0.01);

    }

    /**
     * Checks the cost of 3 lumbers
     * lumber 1 = 100X100X1000 type 0 class 0
     * lumber 2 = 100X100X2000 type 0 class 1
     * lumber 3 = 200X200X2000 type 0 class 0
     * <p/>
     * len     type  class
     * 1000   | 0x0=   1   |
     * 2000   |  0x0= 1     |0x1= 2
     */
    @Test
    public void testCostWith3LumbersDiffClasses() {
        LumberLogTransportEntry entry = new LumberLogTransportEntry();
        LumberLog lumber1 = TestUtils.createLumberLog(100D, 100D, 1000D, 0, 0, 0);
        LumberLog lumber2 = TestUtils.createLumberLog(100D, 100D, 2000D, 0, 1, 0);
        LumberLog lumber3 = TestUtils.createLumberLog(200D, 200D, 2000D, 0, 0, 0);
        lumber1.setVolume(1D * MetricTools.MILIMETER_VOLUME_SCALE);
        lumber2.setVolume(2D * MetricTools.MILIMETER_VOLUME_SCALE);
        lumber3.setVolume(4D * MetricTools.MILIMETER_VOLUME_SCALE);
        entry.addLumberLog(lumber1);
        entry.addLumberLog(lumber2);
        entry.addLumberLog(lumber3);
        LumberLogTransportEntryCostMatrix matrix = new LumberLogTransportEntryCostMatrix();
        matrix.init(entry);

        LumberLogTransportEntryCostMatrixKey key1 = new LumberLogTransportEntryCostMatrixKey(lumber1.getLength().longValue(), lumber1.getLumberClass().intValue(),
                lumber1.getLumberType().intValue());
        LumberLogTransportEntryCostMatrixKey key2 = new LumberLogTransportEntryCostMatrixKey(lumber2.getLength().longValue(), lumber2.getLumberClass().intValue(),
                lumber2.getLumberType().intValue());
        LumberLogTransportEntryCostMatrixKey key3 = new LumberLogTransportEntryCostMatrixKey(lumber3.getLength().longValue(), lumber3.getLumberClass().intValue(),
                lumber3.getLumberType().intValue());
        matrix.getCellData().get(key1).setCost(100);//1RON
        matrix.getCellData().get(key2).setCost(200);//2RON
        matrix.getCellData().get(key3).setCost(100);//2RON

        assertEquals(9D, matrix.calculateTotalCost(), 0.01);

    }

    /**
     * Checks the cost of 3 lumbers
     * lumber 1 = 100X100X1000 type 0 class 0
     * lumber 2 = 100X100X2000 type 0 class 1
     * lumber 3 = 100X100X3000 type 0 class 2
     * <p/>
     * len     type  class
     * 1000   | 0x0=   1   |
     * 2000   |      |0x1= 2
     * 3000   |      |   | 0x2 = 5
     */
    @Test
    public void testCostWith3LumbersDiffLengthAndClasses() {
        LumberLogTransportEntry entry = new LumberLogTransportEntry();
        LumberLog lumber1 = TestUtils.createLumberLog(100D, 100D, 1000D, 0, 0, 0);
        LumberLog lumber2 = TestUtils.createLumberLog(100D, 100D, 2000D, 0, 1, 0);
        LumberLog lumber3 = TestUtils.createLumberLog(100D, 100D, 3000D, 0, 2, 0);
        lumber1.setVolume(1D * MetricTools.MILIMETER_VOLUME_SCALE);
        lumber2.setVolume(2D * MetricTools.MILIMETER_VOLUME_SCALE);
        lumber3.setVolume(3D * MetricTools.MILIMETER_VOLUME_SCALE);
        entry.addLumberLog(lumber1);
        entry.addLumberLog(lumber2);
        entry.addLumberLog(lumber3);
        LumberLogTransportEntryCostMatrix matrix = new LumberLogTransportEntryCostMatrix();
        matrix.init(entry);

        LumberLogTransportEntryCostMatrixKey key1 = new LumberLogTransportEntryCostMatrixKey(lumber1.getLength().longValue(), lumber1.getLumberClass().intValue(),
                lumber1.getLumberType().intValue());
        LumberLogTransportEntryCostMatrixKey key2 = new LumberLogTransportEntryCostMatrixKey(lumber2.getLength().longValue(), lumber2.getLumberClass().intValue(),
                lumber2.getLumberType().intValue());
        LumberLogTransportEntryCostMatrixKey key3 = new LumberLogTransportEntryCostMatrixKey(lumber3.getLength().longValue(), lumber3.getLumberClass().intValue(),
                lumber3.getLumberType().intValue());
        matrix.getCellData().get(key1).setCost(100);//1RON
        matrix.getCellData().get(key2).setCost(200);//2RON
        matrix.getCellData().get(key3).setCost(300);//2RON

        assertEquals(14D, matrix.calculateTotalCost(), 0.01);

    }
}
