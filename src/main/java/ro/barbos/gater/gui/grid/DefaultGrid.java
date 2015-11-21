package ro.barbos.gater.gui.grid;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ro.barbos.gui.renderer.GeneralTableRenderer;
import ro.barbos.gui.tablemodel.GeneralAbstractTableModel;

public class DefaultGrid extends JPanel implements GridModelListener {

	private static final long serialVersionUID = 1L;
	private boolean showPaginationTool = true;
	private boolean addDefaultRenderer = true;

	private GeneralAbstractTableModel data;
	private JTable table;
	private DefaultGridInfoPanel paginationTool;

	public DefaultGrid(GeneralAbstractTableModel data) {
		this.data = data;
		setLayout(new BorderLayout());

		table = new JTable(data);
		if (addDefaultRenderer) {
			GeneralTableRenderer renderer = new GeneralTableRenderer();
			for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
				table.getColumnModel().getColumn(i).setCellRenderer(renderer);
			}
		}
		add(new JScrollPane(table),BorderLayout.CENTER);
		if(showPaginationTool) {
			paginationTool = new DefaultGridInfoPanel(this);
			add(paginationTool, BorderLayout.SOUTH);
		}
		data.addGridModelListener(this);
	}

	public void setData(GeneralAbstractTableModel data) {
		this.data = data;
	}
	
	public void showNextPage() {
		data.nextPage();
	}
	
    public void showPreviousPage() {
		data.prevPage();
	}
    
    public JTable getTable() {
    	return table;
    }

	/* (non-Javadoc)
	 * @see ro.barbos.gater.gui.grid.GridModelListener#gridDataChanged(ro.barbos.gater.gui.grid.GridModelEvent)
	 */
	@Override
	public void gridDataChanged(GridModelEvent event) {
		GeneralAbstractTableModel data = event.model;
		if(showPaginationTool) {
			paginationTool.displayDbInfo(data.currentPage, data.pages, data.count);
		}
	}
    
    
}
