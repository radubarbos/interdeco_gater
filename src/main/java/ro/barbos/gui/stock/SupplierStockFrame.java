package ro.barbos.gui.stock;

import ro.barbos.gater.dao.StockDAO;
import ro.barbos.gater.dto.SupplierStockDTO;
import ro.barbos.gui.GUITools;
import ro.barbos.gui.GUIUtil;
import ro.barbos.gui.GeneralTableDataFrame;
import ro.barbos.gui.tablemodel.DefaultDataTableModel;
import ro.barbos.gui.tablemodel.stock.SupplierStockModel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by radu on 9/4/2016.
 */
public class SupplierStockFrame extends GeneralTableDataFrame {

    public SupplierStockFrame() {
        setTitle("Stoc current per furnizor");
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
        dataModel = new SupplierStockModel();
    }

    @Override
    public void fetchData() {
        ((DefaultDataTableModel<SupplierStockDTO>) dataModel).setData(StockDAO.getCurrentStockSupplierInfo());
    }

    @Override
    public String getFrameCode() {
        return GUIUtil.SUPPLIER_STOCKS_KEY;
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
