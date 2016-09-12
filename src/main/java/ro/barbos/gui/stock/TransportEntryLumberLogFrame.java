package ro.barbos.gui.stock;

import ro.barbos.gater.dao.StockDAO;
import ro.barbos.gater.dto.LumberLogFilterDTO;
import ro.barbos.gater.model.LumberLogStockEntry;
import ro.barbos.gater.model.LumberLogTransportEntry;
import ro.barbos.gui.GUITools;
import ro.barbos.gui.GUIUtil;
import ro.barbos.gui.GeneralTableDataFrame;
import ro.barbos.gui.tablemodel.FullStockModel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by radu on 9/12/2016.
 */
public class TransportEntryLumberLogFrame extends GeneralTableDataFrame {

    private LumberLogTransportEntry transportEntry;

    public TransportEntryLumberLogFrame(LumberLogTransportEntry transportEntry) {
        this.transportEntry = transportEntry;
        if (GUIUtil.container.isFrameSet(GUIUtil.TRANSPORT_LUMBER_ENTRIES_KEY)) {
            GUIUtil.container.closeFrame(GUIUtil.TRANSPORT_LUMBER_ENTRIES_KEY);
        }
        setTitle("Busteni din receptia din data de " + transportEntry.getEntryDate());
        initGui();
        fetchData();
        GUIUtil.container.addFrame(this, GUIUtil.TRANSPORT_LUMBER_ENTRIES_KEY);
    }

    @Override
    public void initDataModel() {
        dataModel = new FullStockModel();
    }

    @Override
    public void fetchData() {
        LumberLogFilterDTO filter = new LumberLogFilterDTO();
        filter.setTransportEntryId(transportEntry.getId());
        java.util.List<LumberLogStockEntry> lumberLogs = StockDAO.getCurrentLumbersLogs(filter);
        ((FullStockModel) dataModel).setLumberLogs(lumberLogs);
    }

    @Override
    public String getFrameCode() {
        return GUIUtil.TRANSPORT_LUMBER_ENTRIES_KEY;
    }

    @Override
    public ImageIcon getFrameIcon() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ImageIcon getIconifiedIcon() {
        Image image = GUITools.getImage("/ro/barbos/gui/resources/chart32.png");
        return new ImageIcon(image);
    }

}
