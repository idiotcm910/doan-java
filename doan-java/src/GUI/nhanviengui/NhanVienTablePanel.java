package GUI.nhanviengui;

import components.AppTable;
import DTO.NhanVienDTO;
import BLL.NhanVienBLL;

import common.FlexBox;
import common.Util;
import common.FrameDisplayRegistry;
import common.Constants;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Cursor;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.table.*;

public class NhanVienTablePanel extends JPanel {
	// tranh truowng hop nguoi dung click 1 row nhieu lan nhung van chay su kien
	private int indexRowSelectionCache;

	// truyen form panel qua de tuong tac du lieu giua form va table
	private NhanVienFormPanel formPanel;
	private NhanVienBLL nhanVienBLL;
	private AppTable table;
	private JButton deleteButton;
	private FrameDisplayRegistry displayRegistry;

	public NhanVienTablePanel() {
		setLayout(null);
		initVariable();
	}

	private void initVariable() {
		indexRowSelectionCache = -1;
		nhanVienBLL = NhanVienBLL.getInstance();
		deleteButton = new JButton("Xóa nhân viên");
		displayRegistry = FrameDisplayRegistry.getInstance();
	}

	public void setFormPanel(NhanVienFormPanel panel) {
		formPanel = panel;
	}

	public void initComponentInPanel() {
		FlexBox flexBoxForTablePanel = new FlexBox(this, FlexBox.DIRECTION_COLUMN);
		flexBoxForTablePanel.setPadding(10);

		JLabel labelTitlePanel = new JLabel("Danh sách nhân viên");
		labelTitlePanel.setFont(Util.getFont("Montserrat", "BOLD", 25));
		labelTitlePanel.setSize(Util.getSizeOfString(labelTitlePanel.getText(), labelTitlePanel.getFont()));
		labelTitlePanel.setForeground(Color.BLACK);
		flexBoxForTablePanel.setPosition(labelTitlePanel, 0, 0);

		String[] columnsName = new String[] {"Mã nhân viên", "Tên nhân viên", "Ngày vào làm", "Vị trí", "Lương"};
		int[] columnSize = new int[] {15, 35, 15, 20, 15};
		JScrollPane scroll = new JScrollPane();
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		flexBoxForTablePanel.setPositionWithinPercentSize(85, 15, scroll);
		table = new AppTable(columnsName, columnSize, scroll.getSize());
		scroll.getViewport().add(table);

		setPropertyForButton(deleteButton);
		flexBoxForTablePanel.setPosition(deleteButton, 15, 0);

		addEventToComponentInPanel();

		refreshValuesInTable();
	}

	private void addEventToComponentInPanel() {
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

		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleEventDeleteButton();
			}
		});
	}

	public void refreshValuesInTable() {
		addValuesInTable(nhanVienBLL.getAllNhanVien());
	}

	public void addValuesInTable(ArrayList<NhanVienDTO> staffs) {
		DefaultTableModel tableModel = (DefaultTableModel)table.getModel();

		tableModel.setRowCount(0);
		for(NhanVienDTO nhanVien : staffs) {
			tableModel.addRow(nhanVien.toArrayString());
		}
	}

	private void handleEventSelectionRow(int index) {
		formPanel.truyenDuLieuTuBang(nhanVienBLL.getAllNhanVien().get(index));		
	}
	private void handleEventDeleteButton() {
		if(table.getSelectedRow() == -1) {
			showMessage("Bạn vui lòng chọn dòng dữ liệu ở bảng để có thể xóa");
			return;
		}
		
		formPanel.deleteEventButton(nhanVienBLL.getAllNhanVien().get(table.getSelectedRow()));
		showMessage("Xóa nhân viên thành công");
		refreshValuesInTable();
	}

	private void showMessage(String message) {
		JOptionPane.showMessageDialog(displayRegistry.getMainFrame(), message);
	}

	private void setPropertyForButton(JButton button) {
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setFont(Util.getFont("Roboto", "NORMAL", 16));
		button.setForeground(Color.WHITE);
		button.setBackground(Constants.COLOR_PRIMARY);
		button.setSize(200, 50);
		button.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, button.getBackground()));

		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				button.setForeground(Constants.COLOR_PRIMARY);
				button.setBackground(Color.WHITE);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				button.setForeground(Color.WHITE);
				button.setBackground(Constants.COLOR_PRIMARY);
			}
		});
	}
}
