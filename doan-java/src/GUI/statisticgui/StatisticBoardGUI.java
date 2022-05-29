package GUI.statisticgui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;


import java.awt.Dimension;
import java.awt.Color;


import common.Util;
import common.Constants;
import common.FlexBox;


public class StatisticBoardGUI extends JPanel {
	private JPanel contentPanel;
	private StatisticDoanhThuPanel statisticDoanhThuPanel;

	public StatisticBoardGUI(Dimension size) {
		setSize(size);
		setLayout(null);
		initVariable();
		initComponent();
	}
	
	private void initVariable() {
		contentPanel = new JPanel(null);
		statisticDoanhThuPanel = new StatisticDoanhThuPanel();
	}

	private void initComponent() {
		FlexBox flexBoxForStatisticBoard = new FlexBox(this, FlexBox.DIRECTION_COLUMN);
		setBackground(Constants.COLOR_SPACE);

		// ================== LABEL TITLE =======================
		JLabel labelTitleBoard = new JLabel("Thống kê");
		labelTitleBoard.setFont(Util.getFont("Benchmark", "NORMAL", 35));
		labelTitleBoard.setSize(Util.getSizeOfString(labelTitleBoard.getText(), labelTitleBoard.getFont()));
		labelTitleBoard.setForeground(Constants.COLOR_PRIMARY);
		flexBoxForStatisticBoard.setPosition(labelTitleBoard, 10, 10);

		JLabel line = new JLabel();
		line.setSize(getWidth(), 2);
		line.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(70, 70, 70)));
		flexBoxForStatisticBoard.setPosition(line, 10, 0);

		// ================== COMPONENT IN CONTENT BOARD ===========================
		contentPanel.setSize(getWidth(), 0);
		contentPanel.setBackground(Constants.COLOR_SPACE);
		flexBoxForStatisticBoard.setPosition(FlexBox.ALIGNMENT_REMAINING_SIZE_OF_PANEL, 0, contentPanel);

		FlexBox flexBoxForContentPanel = new FlexBox(contentPanel, FlexBox.DIRECTION_COLUMN);

		statisticDoanhThuPanel.setBackground(Constants.COLOR_SPACE);
		flexBoxForContentPanel.setPositionWithinPercentSize(100, 0, statisticDoanhThuPanel);
		statisticDoanhThuPanel.initComponent();
	}
}
