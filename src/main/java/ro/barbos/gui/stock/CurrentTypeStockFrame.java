package ro.barbos.gui.stock;

import ro.barbos.gater.dao.StockDAO;
import ro.barbos.gater.dto.TypeStockDTO;
import ro.barbos.gui.GUITools;
import ro.barbos.gui.GUIUtil;
import ro.barbos.gui.GeneralTableDataFrame;
import ro.barbos.gui.tablemodel.DefaultDataTableModel;
import ro.barbos.gui.tablemodel.TypeStockModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CurrentTypeStockFrame extends GeneralTableDataFrame {

    private static final long serialVersionUID = 1L;

	public CurrentTypeStockFrame() {
		setTitle("Stoc current per tip de bustean");
        initGui();
        fetchData();
    }

    @Override
    public void initDataModel() {
        dataModel = new TypeStockModel();
    }

    @Override
    public void fetchData() {
        List<TypeStockDTO> lumberStockTypeDTO = StockDAO
                .getCurrentStockTypeInfo();
        if (lumberStockTypeDTO != null) {
            ((DefaultDataTableModel<TypeStockDTO>) dataModel).setData(lumberStockTypeDTO);
        }
    }

	@Override
	public String getFrameCode() {
		return GUIUtil.TYPE_STOCKS_KEY;
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
