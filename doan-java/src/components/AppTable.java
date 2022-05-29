package components;

import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Dimension;
import java.awt.Color;

import common.Util;
import common.Constants;


public class AppTable extends JTable {
	private String[] columnsName;
	private int[] sizePerColumn;

	public AppTable(String[] columnsName, int[]sizePerColumn, Dimension size) {
		super();
		this.columnsName = columnsName;
		this.sizePerColumn = sizePerColumn;
		setSize(size);
		initComponent();
	}		

	private void initComponent() {
		initTableHeader();
		initTableBody();
		setSizeCellTable();
	}

	private void initTableBody() {
		DefaultTableModel dtm = new DefaultTableModel(columnsName, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		setModel(dtm);
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setFillsViewportHeight(true);
		setBorder(null);
		setRowHeight(35);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setRowSelectionAllowed(true);
		setColumnSelectionAllowed(false);
		setForeground(Constants.COLOR_TEXT_TASKBAR);
		setGridColor(new Color(119, 136, 153));
		setShowVerticalLines(false);
		setFont(Util.getFont("Roboto", "NORMAL", 12));
		setSelectionBackground(getBackground());
		setSelectionForeground(Constants.COLOR_PRIMARY);
	}

	private void initTableHeader() {
		JTableHeader header = getTableHeader();
		header.setPreferredSize(new Dimension(getWidth(), 45));
		header.setResizingAllowed(false);
		header.setBackground(Constants.COLOR_PRIMARY);
		header.setForeground(Constants.COLOR_BACKGROUND_PRIMARY);
		header.setBorder(null);
		header.setFont(Util.getFont("Roboto", "NORMAL", 14));
	}

	private void setSizeCellTable() {
		for(int i = 0; i < sizePerColumn.length; ++i) {
			DefaultTableCellRenderer render = new DefaultTableCellRenderer();
			render.setHorizontalAlignment(SwingConstants.CENTER);
			int columnWidth = Util.calculatePrecent(sizePerColumn[i], getWidth());

			getColumnModel().getColumn(i).setPreferredWidth(columnWidth);
			getColumnModel().getColumn(i).setCellRenderer(render);
		}
	}

}
