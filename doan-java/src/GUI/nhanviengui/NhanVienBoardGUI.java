package GUI.nhanviengui;


import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;


import java.awt.Dimension;
import java.awt.Color;

import common.Util;
import common.Constants;
import common.FlexBox;

public class NhanVienBoardGUI extends JPanel {
	private JPanel contentPanel;
	private NhanVienFormPanel formPanel;
	private NhanVienTablePanel tablePanel;

	public NhanVienBoardGUI(Dimension size) {
		setSize(size);
		setLayout(null);
		initVariable();
		initComponent();
	}

	private void initVariable() {
		contentPanel = new JPanel(null);
		formPanel = new NhanVienFormPanel();
		tablePanel = new NhanVienTablePanel();

		tablePanel.setFormPanel(formPanel);
		formPanel.setTablePanel(tablePanel);
	}

	private void initComponent() {
		FlexBox flexBoxForNhanVienBoard = new FlexBox(this, FlexBox.DIRECTION_COLUMN);
		setBackground(Constants.COLOR_SPACE);

		// ================== LABEL TITLE =======================
		JLabel labelTitleBoard = new JLabel("Nhân viên");
		labelTitleBoard.setFont(Util.getFont("Benchmark", "NORMAL", 35));
		labelTitleBoard.setSize(Util.getSizeOfString(labelTitleBoard.getText(), labelTitleBoard.getFont()));
		labelTitleBoard.setForeground(Constants.COLOR_PRIMARY);
		flexBoxForNhanVienBoard.setPosition(labelTitleBoard, 10, 10);

		JLabel line = new JLabel();
		line.setSize(getWidth(), 2);
		line.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(70, 70, 70)));
		flexBoxForNhanVienBoard.setPosition(line, 10, 0);

		contentPanel.setSize(getWidth(), 0);
		contentPanel.setBackground(Constants.COLOR_SPACE);
		flexBoxForNhanVienBoard.setPosition(FlexBox.ALIGNMENT_REMAINING_SIZE_OF_PANEL, 0, contentPanel);
		initComponentContentPanel();
	}

	private void initComponentContentPanel() {
		FlexBox flexBoxForContentPanel = new FlexBox(contentPanel, FlexBox.DIRECTION_ROW);
		flexBoxForContentPanel.setPadding(30);

		formPanel.setBackground(Color.WHITE);
		flexBoxForContentPanel.setPositionWithinPercentSize(38, 0, formPanel);
		formPanel.initComponentInPanel();

		tablePanel.setBackground(Color.WHITE);
		flexBoxForContentPanel.setPositionWithinPercentSize(100, 40, tablePanel);
		tablePanel.initComponentInPanel();
	}

	public void refreshPanel() {
		tablePanel.refreshValuesInTable();
		formPanel.refreshEventButton();
	}
}
