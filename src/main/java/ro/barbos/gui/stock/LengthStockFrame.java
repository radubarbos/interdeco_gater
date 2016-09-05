package ro.barbos.gui.stock;

import ro.barbos.gater.dao.StockDAO;
import ro.barbos.gater.dto.LengthStockDTO;
import ro.barbos.gui.GUITools;
import ro.barbos.gui.GUIUtil;
import ro.barbos.gui.GeneralTableDataFrame;
import ro.barbos.gui.tablemodel.DefaultDataTableModel;
import ro.barbos.gui.tablemodel.stock.LengthStockModel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by radu on 9/4/2016.
 */
public class LengthStockFrame extends GeneralTableDataFrame {

    public LengthStockFrame() {
        setTitle("Stoc current per lungime");
        initGui();
        fetchData();
    }

    @Override
    public JPanel initToolbar() {
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEADING));
        toolbar.add(createCSVExportButton());
        toolbar.add(createPrintButton());
        return toolbar;
    }

    @Override
    public void initDataModel() {
        dataModel = new LengthStockModel();
    }

    @Override
    public void fetchData() {
        ((DefaultDataTableModel<LengthStockDTO>) dataModel).setData(StockDAO.getCurrentStockLengthInfo());
    }

    @Override
    public String getFrameCode() {
        return GUIUtil.LENGTH_STOCK_KEY;
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
