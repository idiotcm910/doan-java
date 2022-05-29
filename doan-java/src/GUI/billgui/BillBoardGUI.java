package GUI.billgui;


import common.Constants;
import common.Util;
import common.FlexBox;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;

import components.ReadSettings;

public class BillBoardGUI extends JPanel {
	private JPanel contentPanel;	
	private BillListPanel billListPanel;
	private AddBillPanel billAddPanel;

	private int indexNavigationItemCache;
	private String formatUrlIcons;

	public BillBoardGUI(Dimension size) {
		setSize(size);
		setLayout(null);
		initVariable();
		initComponent();
	}	

	private void initVariable() {
		formatUrlIcons = ReadSettings.getInstance().getConfig("pathDirIcons") + "%s.png";
		contentPanel = new JPanel(null);
		billListPanel = new BillListPanel();
		billAddPanel = new AddBillPanel();
		indexNavigationItemCache = 0;
	}

	private void initComponent() {
		FlexBox flexBoxForBillBoard = new FlexBox(this, FlexBox.DIRECTION_COLUMN);
		setBackground(Constants.COLOR_SPACE);

		// ================== LABEL TITLE =======================
		JLabel labelTitleBoard = new JLabel("Hóa đơn");
		labelTitleBoard.setFont(Util.getFont("Benchmark", "NORMAL", 35));
		labelTitleBoard.setSize(Util.getSizeOfString(labelTitleBoard.getText(), labelTitleBoard.getFont()));
		labelTitleBoard.setForeground(Constants.COLOR_PRIMARY);
		flexBoxForBillBoard.setPosition(labelTitleBoard, 10, 10);

		// add content table
		JPanel navigationPanel = new JPanel(null);
		navigationPanel.setSize(getWidth(), 50);
		navigationPanel.setBackground(Constants.COLOR_SPACE);
		flexBoxForBillBoard.setPosition(navigationPanel, 15, 0);	

		flexBoxForBillBoard.setPosition(FlexBox.ALIGNMENT_REMAINING_SIZE_OF_PANEL, 0, contentPanel);

		billListPanel.setSize(contentPanel.getSize());
		billListPanel.setLocation(0, 0);
		billListPanel.setBackground(Constants.COLOR_SPACE);
		billListPanel.initComponent();
		billListPanel.setVisible(false);

		billAddPanel.setSize(contentPanel.getSize());
		billAddPanel.setLocation(0, 0);
		billAddPanel.setBackground(Constants.COLOR_SPACE);
		billAddPanel.initComponent();
		billAddPanel.setVisible(false);

		// add navigation bar
		setContentNavigationBar(navigationPanel);
		contentPanel.add(billListPanel);
		contentPanel.add(billAddPanel);
	}

	/* ===========================================================================================
	 
	 * =============================== NAVIGATION BAR =============================================
	 * */

	private void setContentNavigationBar(JPanel navigationPanel) {
		FlexBox flexBoxForContentPanel = new FlexBox(navigationPanel, FlexBox.DIRECTION_ROW);

		navigationPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(237, 237, 237)));
		navigationPanel.setBackground(Constants.COLOR_SPACE);

		String[] buttonTexts = {"Danh sách hóa đơn", "Thêm hóa đơn"};
		String[] iconsUrl = {String.format(formatUrlIcons, "list") , String.format(formatUrlIcons, "add")};
		String[] iconsActiveUrl = {String.format(formatUrlIcons, "list-active"), String.format(formatUrlIcons, "add-active")};
		JButton[] navigationButtons = new JButton[2];

		for(int i = 0; i < navigationButtons.length; ++i) {
			navigationButtons[i] = new JButton(buttonTexts[i]);
			JButton button = navigationButtons[i];
			ImageIcon buttonIcon = Util.getImage(iconsUrl[i], 20, 20);
			ImageIcon buttonIconActive = Util.getImage(iconsActiveUrl[i], 28, 28);

			// set property for button
			button.setFont(Util.getFont("Roboto", "BOLD", 18));
			button.setIconTextGap(15);
			button.setFocusable(false);
			button.setCursor(new Cursor(Cursor.HAND_CURSOR));
			button.setBackground(Constants.COLOR_SPACE);

			if(indexNavigationItemCache == i) {
				setPropertyForNavigationButtonActive(button, buttonIconActive);
				EventClickNavigationButton(indexNavigationItemCache);
			}
			else {
				setPropertyForNavigationButtonNoneActive(button, buttonIcon);
			}

			int index = i;	
			//set event hover for button
			button.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent ev) {
					if(index == indexNavigationItemCache) {
						return;
					}
					
					setPropertyForNavigationButtonActive(button, buttonIconActive);	
				}
			
				@Override
				public void mouseExited(MouseEvent ev) {
					if(index == indexNavigationItemCache) {
						return;
					}

					setPropertyForNavigationButtonNoneActive(button, buttonIcon);
				}

				@Override
				public void mouseClicked(MouseEvent ev) {
					setPropertyForNavigationButtonActive(button, buttonIconActive);
					setPropertyForNavigationButtonNoneActive(navigationButtons[indexNavigationItemCache], Util.getImage(iconsUrl[indexNavigationItemCache], 20, 20));
					indexNavigationItemCache = index;
					EventClickNavigationButton(index);

				}
			});

			button.setSize((int)button.getPreferredSize().getWidth(), (int)navigationPanel.getHeight());

			// add to panel
			flexBoxForContentPanel.setPosition(button, 0, 30);
		}
	}

	private void setPropertyForNavigationButtonActive(JButton button, ImageIcon icon) {
		button.setForeground(Constants.COLOR_PRIMARY);
		button.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Constants.COLOR_PRIMARY));
		button.setIcon(icon);
		button.setSize((int)button.getPreferredSize().getWidth(), button.getHeight());
	}

	private void setPropertyForNavigationButtonNoneActive(JButton button, ImageIcon icon) {
		button.setForeground(Constants.COLOR_TEXT_TASKBAR);
		button.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, new Color(237, 237, 237)));
		button.setIcon(icon);
	}

	private void EventClickNavigationButton(int indexButton) {
		if(indexButton == 0) {
			billListPanel.setVisible(true);
			billListPanel.refreshPanel();
			billAddPanel.setVisible(false);
		}
		if(indexButton == 1) {
			billAddPanel.setVisible(true);
			billListPanel.setVisible(false);
		}
	}

	public void showGUI() {
		setVisible(true);
	}
}

