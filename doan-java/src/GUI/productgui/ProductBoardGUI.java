package GUI.productgui;


import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BorderFactory;


import java.awt.Dimension;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import common.Util;
import common.Constants;
import common.FlexBox;


public class ProductBoardGUI extends JPanel {
	private JPanel contentPanel;
	// left side
	private JPanel leftSidePanel;
	private ProductFormPanel formPanel;
	// right side
	private JPanel rightSidePanel;
	private ProductTablePanel tablePanel;
	private ProductSearchPanel searchPanel;

	// 248, 249, 253
	Color spaceColor = new Color(239, 240, 249);
	
	public ProductBoardGUI(Dimension size) {
		setSize(size);
		setLayout(null);
		initVariable();
		initComponent();
	}

	private void initVariable() {	
		contentPanel = new JPanel(null);
		leftSidePanel = new JPanel(null);
		rightSidePanel = new JPanel(null);
		formPanel = new ProductFormPanel();
		searchPanel = new ProductSearchPanel();
		tablePanel = new ProductTablePanel();
		formPanel.setTablePanel(tablePanel);
		tablePanel.setFormPanel(formPanel);
		searchPanel.setTablePanel(tablePanel);
	}


	private void initComponent() {
		FlexBox flexBoxForProductBoard = new FlexBox(this, FlexBox.DIRECTION_COLUMN);
		setBackground(Constants.COLOR_SPACE);

		// ================== LABEL TITLE =======================
		JLabel labelTitleBoard = new JLabel("Sản phẩm");
		labelTitleBoard.setFont(Util.getFont("Benchmark", "NORMAL", 35));
		labelTitleBoard.setSize(Util.getSizeOfString(labelTitleBoard.getText(), labelTitleBoard.getFont()));
		labelTitleBoard.setForeground(Constants.COLOR_PRIMARY);
		flexBoxForProductBoard.setPosition(labelTitleBoard, 10, 10);

		JLabel line = new JLabel();
		line.setSize(getWidth(), 2);
		line.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(70, 70, 70)));
		flexBoxForProductBoard.setPosition(line, 10, 0);

		contentPanel.setSize(getWidth(), 0);
		contentPanel.setBackground(spaceColor);
		flexBoxForProductBoard.setPosition(FlexBox.ALIGNMENT_REMAINING_SIZE_OF_PANEL, 0, contentPanel);


		// init component for content
		FlexBox flexBoxForContentPanel = new FlexBox(contentPanel, FlexBox.DIRECTION_ROW);
		flexBoxForContentPanel.setPadding(20);
		leftSidePanel.setBackground(contentPanel.getBackground());
		flexBoxForContentPanel.setPositionWithinPercentSize(40, 0, leftSidePanel);
		rightSidePanel.setBackground(contentPanel.getBackground());
		// set 100% de FlexBox tu dong. set Width cua right side bang khoang trong con lai trong content panel
		flexBoxForContentPanel.setPositionWithinPercentSize(100, 30, rightSidePanel);

		initComponentForLeftSide();
		initComponentForRightSide();
	}

	private void initComponentForLeftSide() {
		FlexBox flexBoxForLeftSideContent = new FlexBox(leftSidePanel, FlexBox.DIRECTION_COLUMN);				
		formPanel.setBackground(Color.WHITE);
		flexBoxForLeftSideContent.setPositionWithinPercentSize(100, 0, formPanel);
		formPanel.initComponentInPanel();
	}

	public void refreshPanel() {
		tablePanel.initValuesTable();
	}

	private void initComponentForRightSide() {
		FlexBox flexBoxForRightSideContent = new FlexBox(rightSidePanel, FlexBox.DIRECTION_COLUMN);				

		tablePanel.setBackground(Color.WHITE);
		flexBoxForRightSideContent.setPositionWithinPercentSize(85, 0, tablePanel);
		tablePanel.initComponentInPanel();
		tablePanel.initValuesTable();


		// set 100% de FlexBox tu dong. set Width cua right side bang khoang trong con lai trong content panel
		searchPanel.setBackground(Color.WHITE);
		flexBoxForRightSideContent.setPositionWithinPercentSize(100, 15, searchPanel);
		searchPanel.initComponentInPanel();
	}
}
