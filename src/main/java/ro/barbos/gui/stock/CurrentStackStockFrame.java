package ro.barbos.gui.stock;

import ro.barbos.gater.dao.StockDAO;
import ro.barbos.gater.dto.LumberStackInfoDTO;
import ro.barbos.gui.GUITools;
import ro.barbos.gui.GUIUtil;
import ro.barbos.gui.GeneralTableDataFrame;
import ro.barbos.gui.tablemodel.DefaultDataTableModel;
import ro.barbos.gui.tablemodel.StackStockModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CurrentStackStockFrame extends GeneralTableDataFrame {

	
	private static final long serialVersionUID = 1L;
	
	public CurrentStackStockFrame() {
		setTitle("Stoc current per stiva");
        initGui();
        fetchData();
    }

    @Override
    public void initDataModel() {
        dataModel = new StackStockModel();
    }

    @Override
    public void fetchData() {
        List<LumberStackInfoDTO> lumberStacksDTO = StockDAO.getCurrentStackInfo();
        if (lumberStacksDTO != null) {
            ((DefaultDataTableModel<LumberStackInfoDTO>) dataModel).setData(lumberStacksDTO);
        }
    }

    public void rowAction(int row) {
        if (row != -1) {
            LumberStackInfoDTO stack = ((DefaultDataTableModel<LumberStackInfoDTO>) dataModel).getRecord(row);
            new StackLumberLogFrame(stack);
        }
    }

	@Override
	public String getFrameCode() {
		return GUIUtil.STACKS_STOCKS_KEY;
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
