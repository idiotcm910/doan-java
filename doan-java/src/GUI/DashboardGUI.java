package GUI;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JOptionPane;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.Cursor;

import components.UserToken;
import common.Util;
import common.Constants;
import common.FlexBox;
import common.FrameDisplayRegistry;

import BLL.ProductBLL;
import BLL.PhieuNhapBLL;
import BLL.BillBLL;

public class DashboardGUI extends JPanel {
	private JPanel contentPanel;
	private JLabel countProduct;
	private JLabel countBill;
	private JLabel countPhieuNhap;
	private ProductBLL productBLL;
	private PhieuNhapBLL phieuNhapBLL;
	private BillBLL billBLL;

	public DashboardGUI(Dimension size) {
		setSize(size);
		setLayout(null);
		initVariable();
		initComponent();
	}

	private void initVariable() {
		contentPanel = new JPanel(null);
		countProduct = new JLabel();
		countBill = new JLabel();
		countPhieuNhap = new JLabel();
		productBLL = ProductBLL.getInstance();
		phieuNhapBLL = PhieuNhapBLL.getInstance();
		billBLL = BillBLL.getInstance();
	}

	private void initComponent() {
		FlexBox flexBoxForUserBoard = new FlexBox(this, FlexBox.DIRECTION_COLUMN);
		setBackground(Constants.COLOR_SPACE);

		// ================== LABEL TITLE =======================
		JLabel labelTitleBoard = new JLabel("Trang chủ");
		labelTitleBoard.setFont(Util.getFont("Benchmark", "NORMAL", 35));
		labelTitleBoard.setSize(Util.getSizeOfString(labelTitleBoard.getText(), labelTitleBoard.getFont()));
		labelTitleBoard.setForeground(Constants.COLOR_PRIMARY);
		flexBoxForUserBoard.setPosition(labelTitleBoard, 10, 10);

		JLabel line = new JLabel();
		line.setSize(getWidth(), 2);
		line.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(70, 70, 70)));
		flexBoxForUserBoard.setPosition(line, 10, 0);

		contentPanel.setSize(getWidth(), 0);
		contentPanel.setBackground(Constants.COLOR_SPACE);
		flexBoxForUserBoard.setPosition(FlexBox.ALIGNMENT_REMAINING_SIZE_OF_PANEL, 0, contentPanel);

		initComponentInContentPanel();
		refreshPanel();
	}

	private void initComponentInContentPanel() {
		FlexBox flexBoxForContentPanel = new FlexBox(contentPanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForContentPanel.setPadding(20);
		
		setPropertyForLabel(countProduct);
		flexBoxForContentPanel.setPosition(countProduct, 0, 0);
		setPropertyForLabel(countBill);
		flexBoxForContentPanel.setPosition(countBill, 10, 0);
		setPropertyForLabel(countPhieuNhap);
		flexBoxForContentPanel.setPosition(countPhieuNhap, 10, 0);
	}

	public void refreshPanel() {
		countProduct.setText("Số sản phẩm hiện có: " + Integer.toString(productBLL.getAllProduct().size()));
		countBill.setText("Số hóa đơn đã bán: " + Integer.toString(billBLL.getAllBill().size()));
		countPhieuNhap.setText("Số hóa đơn phiếu nhập đã nhập: " + Integer.toString(phieuNhapBLL.getAllPhieuNhap().size()));
	}

	private void setPropertyForLabel(JLabel label) {
		label.setFont(Util.getFont("Roboto", "NORMAL", 30));
		label.setForeground(Color.BLACK);
		label.setSize(600, 100);
	}
}
