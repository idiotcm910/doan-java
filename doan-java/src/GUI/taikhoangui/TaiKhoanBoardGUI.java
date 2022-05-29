package GUI.taikhoangui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;


import java.awt.Dimension;
import java.awt.Color;

import common.Util;
import common.Constants;
import common.FlexBox;

public class TaiKhoanBoardGUI extends JPanel {
	private JPanel contentPanel;
	private TaiKhoanTablePanel tablePanel;
	private TaiKhoanFormPanel formPanel;

	public TaiKhoanBoardGUI(Dimension size) {
		setSize(size);
		setLayout(null);
		initVariable();
		initComponent();
	}
	
	private void initVariable() {
		contentPanel = new JPanel(null);
		tablePanel = new TaiKhoanTablePanel();
		formPanel = new TaiKhoanFormPanel();

		formPanel.setTablePanel(tablePanel);
	}

	private void initComponent() {
		FlexBox flexBoxForTaiKhoanBoard = new FlexBox(this, FlexBox.DIRECTION_COLUMN);
		setBackground(Constants.COLOR_SPACE);

		// ================== LABEL TITLE =======================
		JLabel labelTitleBoard = new JLabel("Tài khoản");
		labelTitleBoard.setFont(Util.getFont("Benchmark", "NORMAL", 35));
		labelTitleBoard.setSize(Util.getSizeOfString(labelTitleBoard.getText(), labelTitleBoard.getFont()));
		labelTitleBoard.setForeground(Constants.COLOR_PRIMARY);
		flexBoxForTaiKhoanBoard.setPosition(labelTitleBoard, 10, 10);

		JLabel line = new JLabel();
		line.setSize(getWidth(), 2);
		line.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(70, 70, 70)));
		flexBoxForTaiKhoanBoard.setPosition(line, 10, 0);

		contentPanel.setSize(getWidth(), 0);
		contentPanel.setBackground(Constants.COLOR_SPACE);
		flexBoxForTaiKhoanBoard.setPosition(FlexBox.ALIGNMENT_REMAINING_SIZE_OF_PANEL, 0, contentPanel);
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
		formPanel.resetForm();
		tablePanel.refreshTableAccounts();
		tablePanel.refreshTableStaffsNotAccount();
	}
}
