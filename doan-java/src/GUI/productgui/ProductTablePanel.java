package GUI.productgui;

import components.AppTable;
import DTO.ProductDTO;
import BLL.ProductBLL;

import common.FlexBox;
import common.Util;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.*;


class ProductTablePanel extends JPanel {
	// tranh truowng hop nguoi dung click 1 row nhieu lan nhung van chay su kien
	private int indexRowSelectionCache;

	// truyen form panel qua de tuong tac du lieu giua form va table
	private ProductFormPanel formPanel;

	private DefaultTableModel tableModel;
	private ArrayList<ProductDTO> array;
	private ProductBLL productBLL;
	private AppTable table;

  public ProductTablePanel() {
  	setLayout(null);
  	initVariable();
  }

	public void setFormPanel(ProductFormPanel formPanel) {
		this.formPanel = formPanel;
	}

	public void initVariable() {
		productBLL = ProductBLL.getInstance();
		indexRowSelectionCache = -1;
	}

	public void initValuesTable() {
		array = productBLL.getAllProduct();
		addValuesTable(array);
	}

	public void addValuesTable(ArrayList<ProductDTO> products) {
		tableModel.setRowCount(0);
		indexRowSelectionCache = -1;
		for(ProductDTO product : products) {
			tableModel.addRow(product.toArrayString());
		}
	}

	public void initComponentInPanel() {
		FlexBox flexBoxForTablePanel = new FlexBox(this, FlexBox.DIRECTION_COLUMN);
		flexBoxForTablePanel.setPadding(10);

		JLabel labelTitlePanel = new JLabel("Danh sách sản phẩm");
		labelTitlePanel.setFont(Util.getFont("Montserrat", "BOLD", 25));
		labelTitlePanel.setSize(Util.getSizeOfString(labelTitlePanel.getText(), labelTitlePanel.getFont()));
		labelTitlePanel.setForeground(Color.BLACK);
		flexBoxForTablePanel.setPosition(labelTitlePanel, 0, 0);

		String[] columnsName = new String[] {"Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Đơn giá", "Loại", "Ngày sản xuất", "Hạn sử dụng", "Mã nhà sản xuất"};
		int[] columnSize = new int[] {10, 18, 7, 7, 5, 20, 20, 13};
		JScrollPane scroll = new JScrollPane();
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		flexBoxForTablePanel.setPosition(FlexBox.ALIGNMENT_REMAINING_SIZE_OF_PANEL, 10, scroll);
		table = new AppTable(columnsName, columnSize, scroll.getSize());
		scroll.getViewport().add(table);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int indexRowSelection = table.getSelectedRow();

				if(indexRowSelection == indexRowSelectionCache) {
					return;
				}

				indexRowSelectionCache = indexRowSelection;
				handleEventSelectionRow(indexRowSelection);
			}
		});

		tableModel = (DefaultTableModel)table.getModel();
	}

	private void handleEventSelectionRow(int indexRow) {
		ProductDTO productData = productBLL.getProduct(indexRow);
		formPanel.chuyenDuLieu(productData);
	}
	
	public void setSelectionRow(ProductDTO product) {
		int index = productBLL.indexOf(product.getMaSanPham());	
		if(index != -1) {
			table.changeSelection(indexRowSelectionCache, index, false, true);
		}
	}
}

