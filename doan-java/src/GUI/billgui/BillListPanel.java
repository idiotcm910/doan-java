package GUI.billgui;

import common.Util;
import common.FlexBox;
import common.Constants;
import common.FrameDisplayRegistry;
import common.Date;

import components.AppTable;
import components.NumberDocumentFilter;

import BLL.BillBLL;
import BLL.BillDetailsBLL;
import DTO.HoaDonDTO;
import DTO.BillDetailsDTO;

import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JCheckBox;
import javax.swing.text.AbstractDocument;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;


class BillListPanel extends JPanel {
	BillBLL billBLL = BillBLL.getInstance();
	BillDetailsBLL billDetailsBLL = BillDetailsBLL.getInstance();
	private int indexRowSelectionCache = -1;
	private AppTable tableBillList;
	private AppTable tableBillDetailsList;
	private DefaultTableModel tableModelBillDetails; 
	private DefaultTableModel tableModelBill;
	private ArrayList<HoaDonDTO> arrayHoaDon;
	private JPanel searchBills;
	private JPanel searchBillDetails;
	private FrameDisplayRegistry displayRegistry;


	// tim kiem bang hoa don
	private JPanel billPanel;
	private JTextField inputSearchBillsDayFrom;
	private JLabel dayFromValidation;
	private JTextField inputSearchBillsDayTo;
	private JLabel dayToValidation;
	private String[] valueComboboxBills;
	private JComboBox inputSearchTongTien;
	private JTextField inputSearchNhanVien;
	private JRadioButton radioKhachHang;
	private JCheckBox checkBoxKhachHang;
	private JTextField inputSearchKhachHang;
	private JButton searchBillsButton;
	private JButton cancelSearchBillsButton;
	private String dayFrom, dayTo, maNhanVien, maKhachHang;
	private int tongTienMin, tongTienMax;
	private boolean hasConditionSearch;
	private boolean wasSearch;
	private boolean hasErrorSearch;

	private JComboBox comboxMethodSearchBill;

	// tim kiem bang chi tiet hoa don
	private JPanel billDetailsPanel;
	private JTextField inputSearchBillsDetailsMaSanPham;
	private JTextField inputSearchBillsDetailsSoLuongFrom;
	private JLabel soLuongFromValidation;
	private JTextField inputSearchBillsDetailsSoLuongTo;
	private JLabel soLuongToValidation;
	private JRadioButton radioKhuyenMai;
	private JCheckBox checkBoxKhuyenMai;
	private JTextField inputSearchBillsDetailsKhuyenMai;
	String maSanPham;
	String maKhuyenMai;
	int soLuongFrom;
	int soLuongTo;
	private boolean hasConditionSearchDetails;
	private boolean wasSearchDetails;
	private boolean hasErrorSearchDetails;
	private JButton searchDetailsButton;
	private JButton cancelSearchDetailsButton;

	private JComboBox comboxMethodSearchBillDetails;
	private String maHoaDonCache;

	private Font defaultFont;
	public BillListPanel() {
		setLayout(null);
		initVariable();
	}

	private void initVariable() {
		defaultFont = Util.getFont("Roboto", "NORMAL", 18);
		billPanel = new JPanel(null);
		billDetailsPanel = new JPanel(null);
		displayRegistry = FrameDisplayRegistry.getInstance();
		searchBills = new JPanel(null);
		searchBillDetails = new JPanel(null);
		inputSearchBillsDayTo = new JTextField();
		inputSearchBillsDayFrom = new JTextField();
		inputSearchNhanVien = new JTextField();
		inputSearchKhachHang = new JTextField();
		valueComboboxBills = new String[] {"Vui lòng chọn khoảng tiền", "0đ - 20.000đ", "20.000đ - 50.000đ", "50.000đ - 100.000đ", "100.000đ trở lên"};
		inputSearchTongTien = new JComboBox(valueComboboxBills);
		searchBillsButton = new JButton("Tìm kiếm");
		cancelSearchBillsButton = new JButton("Hủy tìm kiếm");
		dayFrom = "";
		dayTo = "";
		maNhanVien = "";
		maKhachHang = "";
		tongTienMin = -1;
		tongTienMax = -1;
		hasConditionSearch = false;
		wasSearch = false;
		inputSearchBillsDetailsMaSanPham = new JTextField("");
		inputSearchBillsDetailsSoLuongTo = new JTextField("");
		inputSearchBillsDetailsSoLuongFrom = new JTextField("");
		maSanPham = "";
		maKhuyenMai = "";
		soLuongFrom = 0;
		soLuongTo = 0;
		hasConditionSearchDetails = false;
		wasSearchDetails = false;
		searchDetailsButton = new JButton("Tìm kiếm");
		cancelSearchDetailsButton = new JButton("Hủy tìm kiếm");
		maHoaDonCache = "";
		hasErrorSearch = true;
		hasErrorSearchDetails = true;

		checkBoxKhachHang = new JCheckBox("Có mã");
		checkBoxKhuyenMai = new JCheckBox("Có mã");
		radioKhachHang = new JRadioButton();
		dayFromValidation = new JLabel();
		dayToValidation = new JLabel();
		soLuongToValidation = new JLabel();
		soLuongFromValidation = new JLabel();
		inputSearchBillsDetailsKhuyenMai = new JTextField();

		String[] valueStringMethodSearchBill = new String[] {"HOẶC (Có 1 trong tất)", "VÀ (có tất cả)"};
		comboxMethodSearchBill = new JComboBox(valueStringMethodSearchBill);	
		comboxMethodSearchBillDetails = new JComboBox(valueStringMethodSearchBill);
		radioKhuyenMai = new JRadioButton();
	}

	public void initComponent() {
		FlexBox flexBoxForPanel = new FlexBox(this, FlexBox.DIRECTION_ROW);
		flexBoxForPanel.setPadding(20);

		billPanel.setBackground(Constants.COLOR_SPACE);
		flexBoxForPanel.setPositionWithinPercentSize(49, 0, billPanel);

		billDetailsPanel.setBackground(Constants.COLOR_SPACE);
		flexBoxForPanel.setPositionWithinPercentSize(100, 30, billDetailsPanel);

		initComponentInBillPanel();
		addEventToBillPanel();
		initComponentInBillDetailsPanel();
		addEventToBillDetailsPanel();
		refreshPanel();
	}

	private void initComponentInBillPanel() {
		FlexBox flexBoxForPanel = new FlexBox(billPanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForPanel.setPadding(10);

		//========================================== bang du lieu
		JPanel firstPanel = new JPanel(null);	
		firstPanel.setBackground(Color.WHITE);
		flexBoxForPanel.setPositionWithinPercentSize(45, 0, firstPanel);

		FlexBox flexBoxForFirstPanel = new FlexBox(firstPanel, FlexBox.DIRECTION_COLUMN);
		JLabel titleLabelTalbe = new JLabel("Danh sách hóa đơn");
		setPropertyForTitleLabel(titleLabelTalbe);
		flexBoxForFirstPanel.setPosition(titleLabelTalbe, 0, 0);

		JPanel tablePanel = new JPanel(null);
		tablePanel.setBackground(firstPanel.getBackground());
		flexBoxForFirstPanel.setPosition(FlexBox.ALIGNMENT_REMAINING_SIZE_OF_PANEL, 0, tablePanel);
		FlexBox flexBoxForTable = new FlexBox(tablePanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForTable.setPadding(15);

		String columnNames[] = {"Mã hóa đơn", "Ngày nhập thông tin", "Tỗng tiền", "Mã nhân viên", "Mã khách hàng"};
		int columnSize[] = {15, 25, 20, 20, 20};
		JScrollPane scroll = new JScrollPane();
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		flexBoxForTable.setPosition(FlexBox.ALIGNMENT_REMAINING_SIZE_OF_PANEL, 10, scroll);
		tableBillList = new AppTable(columnNames, columnSize, scroll.getSize());
		scroll.getViewport().add(tableBillList);

		// ============================================================ tim kiem
		JPanel secondPanel = new JPanel(null);
		secondPanel.setBackground(Color.WHITE);
		flexBoxForPanel.setPositionWithinPercentSize(100, 20, secondPanel);

		FlexBox flexBoxForSecond = new FlexBox(secondPanel, FlexBox.DIRECTION_ROW);

		JPanel inputPanel = new JPanel(null);
		inputPanel.setBackground(Color.WHITE);
		flexBoxForSecond.setPositionWithinPercentSize(70, 0, inputPanel);
		FlexBox flexBoxForInput = new FlexBox(inputPanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForInput.setPadding(10);

		JLabel titleLabel = new JLabel("Tìm kiếm");
		setPropertyForTitleLabel(titleLabel);
		flexBoxForInput.setPosition(titleLabel, 0, 0);

		JLabel labelFrom = new JLabel("Từ ngày: ");
		setPropertyForLabel(labelFrom);
		flexBoxForInput.setPosition(labelFrom, 0, 0);
		setPropertyForInputField(inputSearchBillsDayFrom);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, inputSearchBillsDayFrom);
		setPropertyForValidation(dayFromValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, dayFromValidation);

		JLabel labelTo = new JLabel("Đến ngày: ");
		setPropertyForLabel(labelTo);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelTo);
		setPropertyForInputField(inputSearchBillsDayTo);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, inputSearchBillsDayTo);
		setPropertyForValidation(dayToValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, dayToValidation);

		JLabel labelTen = new JLabel("Theo mã nhân viên: ");
		setPropertyForLabel(labelTen);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelTen);
		setPropertyForInputField(inputSearchNhanVien);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, inputSearchNhanVien);

		JLabel tongTienLabel = new JLabel("Theo khoảng tiền: ");
		setPropertyForLabel(tongTienLabel);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, tongTienLabel);
		setPropertyForInputField(inputSearchTongTien);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, inputSearchTongTien);

		JLabel labelMaKhachHang = new JLabel("Theo mã khách hàng");
		setPropertyForLabel(labelMaKhachHang);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 6, labelMaKhachHang);
		JPanel khachHangPanel = new JPanel(null);
		khachHangPanel.setBackground(billPanel.getBackground());
		khachHangPanel.setSize(0, 30);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, khachHangPanel);
		FlexBox flexBoxForMaKhachHang = new FlexBox(khachHangPanel, FlexBox.DIRECTION_ROW);
		setPropertyForRadioButton(radioKhachHang);
		flexBoxForMaKhachHang.setPositionWithinPercentSize(10, 0, radioKhachHang);
		setPropertyForCheckBox(checkBoxKhachHang);
		flexBoxForMaKhachHang.setPositionWithinPercentSize(20, 0, checkBoxKhachHang);
		setPropertyForInputField(inputSearchKhachHang);
		flexBoxForMaKhachHang.setPositionWithinPercentSize(100, 10, inputSearchKhachHang);

		JPanel buttonPanel = new JPanel(null);
		buttonPanel.setBackground(Color.WHITE);
		buttonPanel.setSize(365, 45);
		flexBoxForSecond.setPositionWithinPercentSize(100, 25, buttonPanel);
		FlexBox flexBoxForButton = new FlexBox(buttonPanel, FlexBox.DIRECTION_COLUMN);		
		setPropertyForButton(searchBillsButton);
		flexBoxForButton.setPosition(searchBillsButton, 50, 0);
		setPropertyForButton(cancelSearchBillsButton);
		flexBoxForButton.setPosition(cancelSearchBillsButton, 15, 0);
		JLabel labelComboboxMethod = new JLabel("Phương thức tìm kiếm");
		setPropertyForLabel(labelComboboxMethod);
		flexBoxForButton.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 10, labelComboboxMethod);
		setPropertyForInputField(comboxMethodSearchBill);
		comboxMethodSearchBill.setSize(0, 50);
		flexBoxForButton.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, comboxMethodSearchBill);	

		handleCheckBoxKhachHang();
		handleRadioKhachHang();
	}

	private void addEventToBillPanel() {
		searchBillsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleSearchButton();	
			}
		});

		cancelSearchBillsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleCancelSearchButton();	
			}
		});

		checkBoxKhachHang.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleCheckBoxKhachHang();
			}
		});

		radioKhachHang.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				handleRadioKhachHang();
			}
		});

		tableBillList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(tableBillList.getSelectedRow() != -1) {
					maHoaDonCache = tableBillList.getValueAt(tableBillList.getSelectedRow(), 0).toString();
					handleCancelSearchDetailsButton();
				}	
			}
		});
	}

	private void initComponentInBillDetailsPanel() {
		FlexBox flexBoxForPanel = new FlexBox(billDetailsPanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForPanel.setPadding(10);

		//========================================== bang du lieu
		JPanel firstPanel = new JPanel(null);	
		firstPanel.setBackground(Color.WHITE);
		flexBoxForPanel.setPositionWithinPercentSize(45, 0, firstPanel);

		FlexBox flexBoxForFirstPanel = new FlexBox(firstPanel, FlexBox.DIRECTION_COLUMN);
		JLabel titleLabelTalbe = new JLabel("Danh sách hóa đơn");
		setPropertyForTitleLabel(titleLabelTalbe);
		flexBoxForFirstPanel.setPosition(titleLabelTalbe, 0, 0);

		JPanel tablePanel = new JPanel(null);
		tablePanel.setBackground(firstPanel.getBackground());
		flexBoxForFirstPanel.setPosition(FlexBox.ALIGNMENT_REMAINING_SIZE_OF_PANEL, 0, tablePanel);
		FlexBox flexBoxForTable = new FlexBox(tablePanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForTable.setPadding(15);

		String[] columnNames = new String[] {"Mã hóa đơn",  "Mã sản phẩm", "Mã khuyến mãi", "Số lượng", "Đơn giá", "Thành tiền"};
		int[] columnSize = new int[] {15, 30, 15, 10, 15, 15};
		JScrollPane scroll = new JScrollPane();
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		flexBoxForTable.setPosition(FlexBox.ALIGNMENT_REMAINING_SIZE_OF_PANEL, 10, scroll);
		tableBillDetailsList = new AppTable(columnNames, columnSize, scroll.getSize());
		scroll.getViewport().add(tableBillDetailsList);

		// ============================================================ tim kiem
		JPanel secondPanel = new JPanel(null);
		secondPanel.setBackground(Color.WHITE);
		flexBoxForPanel.setPositionWithinPercentSize(100, 20, secondPanel);

		FlexBox flexBoxForSecond = new FlexBox(secondPanel, FlexBox.DIRECTION_ROW);

		JPanel inputPanel = new JPanel(null);
		inputPanel.setBackground(Color.WHITE);
		flexBoxForSecond.setPositionWithinPercentSize(70, 0, inputPanel);
		FlexBox flexBoxForInput = new FlexBox(inputPanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForInput.setPadding(10);

		JLabel titleLabel = new JLabel("Tìm kiếm");
		setPropertyForTitleLabel(titleLabel);
		flexBoxForInput.setPosition(titleLabel, 0, 0);

		JLabel labelFrom = new JLabel("Số lượng từ: ");
		setPropertyForLabel(labelFrom);
		flexBoxForInput.setPosition(labelFrom, 0, 0);
		setPropertyForInputField(inputSearchBillsDetailsSoLuongFrom);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, inputSearchBillsDetailsSoLuongFrom);
		setPropertyForValidation(soLuongFromValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, soLuongFromValidation);

		JLabel labelTo = new JLabel("Đến: ");
		setPropertyForLabel(labelTo);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelTo);
		setPropertyForInputField(inputSearchBillsDetailsSoLuongTo);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, inputSearchBillsDetailsSoLuongTo);
		setPropertyForValidation(soLuongToValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, soLuongToValidation);

		JLabel labelTen = new JLabel("Theo mã sản phẩm: ");
		setPropertyForLabel(labelTen);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelTen);
		setPropertyForInputField(inputSearchBillsDetailsMaSanPham);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, inputSearchBillsDetailsMaSanPham);

		JLabel labelMaKhachHang = new JLabel("Theo mã khuyến mãi");
		setPropertyForLabel(labelMaKhachHang);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 6, labelMaKhachHang);
		JPanel khachHangPanel = new JPanel(null);
		khachHangPanel.setBackground(billDetailsPanel.getBackground());
		khachHangPanel.setSize(0, 30);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, khachHangPanel);
		FlexBox flexBoxForMaKhachHang = new FlexBox(khachHangPanel, FlexBox.DIRECTION_ROW);
		setPropertyForRadioButton(radioKhuyenMai);
		flexBoxForMaKhachHang.setPositionWithinPercentSize(10, 0, radioKhuyenMai);
		setPropertyForCheckBox(checkBoxKhuyenMai);
		flexBoxForMaKhachHang.setPositionWithinPercentSize(20, 0, checkBoxKhuyenMai);
		setPropertyForInputField(inputSearchBillsDetailsKhuyenMai);
		flexBoxForMaKhachHang.setPositionWithinPercentSize(100, 10, inputSearchBillsDetailsKhuyenMai);

		JPanel buttonPanel = new JPanel(null);
		buttonPanel.setBackground(Color.WHITE);
		buttonPanel.setSize(365, 45);
		flexBoxForSecond.setPositionWithinPercentSize(100, 25, buttonPanel);
		FlexBox flexBoxForButton = new FlexBox(buttonPanel, FlexBox.DIRECTION_COLUMN);		
		setPropertyForButton(searchDetailsButton);
		flexBoxForButton.setPosition(searchDetailsButton, 50, 0);
		setPropertyForButton(cancelSearchDetailsButton);
		flexBoxForButton.setPosition(cancelSearchDetailsButton, 15, 0);
		setPropertyForInputField(comboxMethodSearchBillDetails);
		comboxMethodSearchBillDetails.setSize(0, 50);
		flexBoxForButton.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, comboxMethodSearchBillDetails);	

		AbstractDocument documentSoLuongFrom = (AbstractDocument)inputSearchBillsDetailsSoLuongFrom.getDocument();
		AbstractDocument documentSoLuongTo = (AbstractDocument)inputSearchBillsDetailsSoLuongTo.getDocument();
		documentSoLuongFrom.setDocumentFilter(new NumberDocumentFilter());
		documentSoLuongTo.setDocumentFilter(new NumberDocumentFilter());

		handleCheckBoxKhuyenMai();
		handleRadioKhuyenMai();
	}

	private void addValuesToBillsTable(ArrayList<HoaDonDTO> bills) {
		DefaultTableModel tableModel = (DefaultTableModel)tableBillList.getModel();
		tableModel.setRowCount(0);
		for(HoaDonDTO bill : bills) {
			tableModel.addRow(bill.toArray());
		}
	}

	private void addEventToBillDetailsPanel() {
		searchDetailsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleSearchDetailsButton();	
			}
		});

		cancelSearchDetailsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleCancelSearchDetailsButton();	
			}
		});

		checkBoxKhuyenMai.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleCheckBoxKhuyenMai();
			}
		});

		radioKhuyenMai.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				handleRadioKhuyenMai();
			}
		});

	}

	private void addValuesToDetailsTable(ArrayList<BillDetailsDTO> billDetails) {
		DefaultTableModel table = (DefaultTableModel)tableBillDetailsList.getModel();
		table.setRowCount(0);
		for(BillDetailsDTO billDetail : billDetails) {
			table.addRow(billDetail.toArray());
		}
	}

	private void handleRadioKhachHang() {
		if(radioKhachHang.isSelected()) {
			checkBoxKhachHang.setEnabled(true);
			inputSearchKhachHang.setEnabled(true);
			handleCheckBoxKhachHang();
		}
		else {
			checkBoxKhachHang.setEnabled(false);
			inputSearchKhachHang.setEnabled(false);
			maKhachHang = "";
		}
	}

	private void handleRadioKhuyenMai() {
		if(radioKhuyenMai.isSelected()) {
			checkBoxKhuyenMai.setEnabled(true);
			inputSearchBillsDetailsKhuyenMai.setEnabled(true);
			handleCheckBoxKhuyenMai();
		}
		else {
			checkBoxKhuyenMai.setEnabled(false);
			inputSearchBillsDetailsKhuyenMai.setEnabled(false);
			maKhuyenMai = "";
		}
	}

	private void handleCheckBoxKhuyenMai() {
		if(checkBoxKhuyenMai.isSelected() == false) {
			inputSearchBillsDetailsKhuyenMai.setText("Không có");
			inputSearchBillsDetailsKhuyenMai.setEditable(false);
			maKhuyenMai = "NULL";
		}
		else {
			inputSearchBillsDetailsKhuyenMai.setText("");
			inputSearchBillsDetailsKhuyenMai.setEditable(true);
			maKhuyenMai = "NOTNULL";
		}
		hasConditionSearchDetails = true;
		hasErrorSearchDetails = false;
	}

	private void handleSearchButton() {
		handleSearchTime();
		handleSearchTongTien();
		handleSearchNhanVien();
		handleSearchKhachHang();

		if(hasErrorSearch) {
			return;
		}

		if(!hasConditionSearch) {
			showMessage("Bạn chưa điền dữ liệu ở phần điều kiện tìm kiếm");
			return;
		}

		ArrayList<HoaDonDTO> result = null;
		
		if(comboxMethodSearchBill.getSelectedIndex() == 0) {
			result = billBLL.timKiemNangCaoHoaDonTheoOR(dayTo, dayFrom, tongTienMin, tongTienMax, maNhanVien, maKhachHang);
		}
		else {
			result = billBLL.timKiemNangCaoHoaDonTheoAND(dayTo, dayFrom, tongTienMin, tongTienMax, maNhanVien, maKhachHang);
		}
		if(result.size() == 0) {
			showMessage("Không tim thấy dữ liệu theo những điều kiện bạn đã điền ở phần tìm kiếm");
		}
		else {
			addValuesToBillsTable(result);
		}
		resetValueInSearchBills();
		wasSearch = true;
	}

	private void handleCancelSearchButton() {
		resetValueInSearchBills();
		addValuesToBillsTable(billBLL.getAllBill());
	}

	private void handleCancelSearchDetailsButton() {
		resetValueInSearchDetails();
		addValuesToDetailsTable(billDetailsBLL.getAllBillDetails(maHoaDonCache));
	}

	private void handleSearchDetailsButton() {
		if(maHoaDonCache.length() == 0) {
			showMessage("Chưa có dữ liệu ở bảng chi tiết hóa đơn nên không thể tiến hành tìm kiếm");
			return;
		}

		handleSearchMaSanPham();
		handleSearchSoLuong();
		handleSearchKhuyenMai();
		
		if(hasErrorSearchDetails) {
			return;
		}

		if(!hasConditionSearchDetails) {
			showMessage("Bạn chưa điền dữ liệu ở phần điều kiện tìm kiếm chi tiết hóa đơn");
			return;
		}

		ArrayList<BillDetailsDTO> result = null;
		if(comboxMethodSearchBillDetails.getSelectedIndex() == 0) {
			result = billDetailsBLL.timKiemChiTietHoaDonNangCaoTheoOR(soLuongFrom, soLuongTo, maSanPham, maKhuyenMai, maHoaDonCache);
		}
		else {
			result = billDetailsBLL.timKiemChiTietHoaDonNangCaoTheoAND(soLuongFrom, soLuongTo, maSanPham, maKhuyenMai, maHoaDonCache);
		}
		if(result.size() == 0) {
			showMessage("Không tim thấy dữ liệu theo những điều kiện bạn đã điền ở phần tìm kiếm chi tiết hóa đơn");
		}
		else {
			addValuesToDetailsTable(result);
		}
		resetValueInSearchDetails();
		wasSearchDetails = true;
	}

	private void resetValueInSearchBills() {
		dayTo = "";
		dayFrom = "";
		maNhanVien = "";
		maKhachHang = "";
		tongTienMin = -1;
		tongTienMax = -1;
		inputSearchBillsDayFrom.setText("");
		inputSearchBillsDayTo.setText("");
		inputSearchTongTien.setSelectedIndex(0);
		inputSearchNhanVien.setText("");
		checkBoxKhachHang.setSelected(false);
		handleCheckBoxKhachHang();
		radioKhachHang.setSelected(false);
		handleRadioKhachHang();
		hasConditionSearch = false;
		hasErrorSearch = true;
	}

	private void resetValueInSearchDetails() {
		soLuongTo = -1;
		soLuongFrom = -1;
		maSanPham = "";
		maKhuyenMai = "";
		inputSearchBillsDetailsMaSanPham.setText("");
		inputSearchBillsDetailsSoLuongTo.setText("");
		inputSearchBillsDetailsSoLuongFrom.setText("");
		hasConditionSearchDetails = false;
		hasErrorSearchDetails = true;
		checkBoxKhuyenMai.setSelected(false);
		handleCheckBoxKhuyenMai();
		radioKhuyenMai.setSelected(false);
		handleRadioKhuyenMai();
	}

	private void handleCheckBoxKhachHang() {
		if(checkBoxKhachHang.isSelected() == false) {
			inputSearchKhachHang.setText("Không có");
			inputSearchKhachHang.setEditable(false);
			maKhachHang = "NULL";
		}
		else {
			inputSearchKhachHang.setText("");
			inputSearchKhachHang.setEditable(true);
			maKhachHang = "NOTNULL";
		}
		hasConditionSearch = true;
		hasErrorSearch = false;
	}

	private void handleSearchTime() {
		String dayFromStr = inputSearchBillsDayFrom.getText();
		String dayToStr = inputSearchBillsDayTo.getText();

		if(dayFromStr.length() == 0 && dayToStr.length() == 0) {
			return;
		}

		if(dayFromStr.length() == 0 && dayToStr.length() != 0) {
			activeValidationInput(inputSearchBillsDayFrom, dayFromValidation, "Bạn chưa nhập dữ liệu vào ô 'từ ngày' ở phần tìm kiếm hóa đơn");
			return;
		}

		if(dayFromStr.length() != 0 && dayToStr.length() == 0) {
			activeValidationInput(inputSearchBillsDayTo, dayToValidation, "Bạn chưa nhập dữ liệu vào ô 'Đến ngày' ở phần tìm kiếm hóa đơn");
			return;
		}
		
		// kiem tra xem du lieu nhap co dung cu phap ngay thang nam khong (yyyy-mm-dd)
		try {
			new Date(dayFromStr);
		}
		catch(Exception ex) {
			activeValidationInput(inputSearchBillsDayFrom, dayFromValidation, "Dữ liệu ô 'từ ngày' ở phần tìm kiếm của hóa đơn phải theo cấu trúc yyyy-mm-dd");
			return;
		}

		try {
			new Date(dayToStr);
		}
		catch(Exception ex) {
			activeValidationInput(inputSearchBillsDayTo, dayToValidation, "Dữ liệu ô 'đến ngày' ở phần tìm kiếm của hóa đơn phải theo cấu trúc yyyy-mm-dd");
			return;
		}

		dayFrom = dayFromStr;
		dayTo = dayToStr;
		activeEnableInput(inputSearchBillsDayTo, dayToValidation);
		activeEnableInput(inputSearchBillsDayFrom, dayFromValidation);
		hasConditionSearch = true;
		hasErrorSearch = false;
	}

	private void handleSearchTongTien() {
		int min = 0, max = 0;
		switch(inputSearchTongTien.getSelectedIndex()) {
			case 1:
				min = 0;
				max = 20000;
				break;
			case 2:
				min = 20000;
				max = 20000;
				break;
			case 3:
				min = 50000;
				max = 100000;
				break;
			case 4:
				min = 100000;
				max = 100000000;
				break;
			default:
				min = -1;
				max = -1;
		}

		if(min != -1 && max != -1) {
			tongTienMin = min;
			tongTienMax = max;
			hasConditionSearch = true;
			hasErrorSearch = false;
		}
	}

	private void handleSearchNhanVien() {
		if(inputSearchNhanVien.getText().length() == 0) {
			return;
		}

		maNhanVien = inputSearchNhanVien.getText();
		hasConditionSearch = true;
		hasErrorSearch = false;
	}

	private void handleSearchKhachHang() {
		if(maKhachHang.equals("NULL")) {
			return;
		}

		if(maKhachHang.equals("NOTNULL") && inputSearchKhachHang.getText().length() == 0) {
			return;
		}

		if(maKhachHang.length() == 0) {
			return;
		}

		maKhachHang = inputSearchKhachHang.getText().trim();
		hasConditionSearch = true;
		hasErrorSearch = false;
	}

	private void handleSearchMaSanPham() {
		if(inputSearchBillsDetailsMaSanPham.getText().length() == 0) {
			return;
		}

		maSanPham = inputSearchBillsDetailsMaSanPham.getText();
		hasConditionSearchDetails = true;
		hasErrorSearchDetails = false;
	}

	private void handleSearchSoLuong() {
		String soLuongFromStr = inputSearchBillsDetailsSoLuongFrom.getText();
		String soLuongToStr = inputSearchBillsDetailsSoLuongTo.getText();

		if(soLuongFromStr.length() == 0 && soLuongToStr.length() == 0) {
			return;
		}

		if(soLuongFromStr.length() == 0 && soLuongToStr.length() != 0) {
			activeValidationInput(inputSearchBillsDetailsSoLuongFrom, soLuongFromValidation, "Bạn chưa nhập dữ liệu");
			hasErrorSearchDetails = true;
			return;
		}

		if(soLuongFromStr.length() != 0 && soLuongToStr.length() == 0) {
			activeValidationInput(inputSearchBillsDetailsSoLuongTo, soLuongToValidation, "Bạn chưa nhập dữ liêu");
			hasErrorSearchDetails = true;
			return;
		}

		int min = 0, max = 0;
		min = Integer.parseInt(soLuongFromStr);
		max = Integer.parseInt(soLuongToStr);

		boolean hasErrorNumber = false;
		if(min == 0) {
			activeValidationInput(inputSearchBillsDetailsSoLuongFrom, soLuongFromValidation, "Dữ liệu phải lớn hơn 0");
			hasErrorNumber = true;
		}

		if(max == 0) {
			activeValidationInput(inputSearchBillsDetailsSoLuongTo, soLuongToValidation, "Dữ liệu phải lớn hơn 0");
			hasErrorNumber = true;
		}

		if(hasErrorNumber) {
			hasErrorSearchDetails = true;
			return;
		}

		if(min > max) {
			activeValidationInput(inputSearchBillsDetailsSoLuongFrom, soLuongFromValidation, "Dữ liệu phải nhỏ hơn ô số lượng 'Đến'");
			hasErrorSearchDetails = true;
			return;
		}
		soLuongFrom = min;
		soLuongTo = max;
		activeEnableInput(inputSearchBillsDetailsSoLuongFrom, soLuongFromValidation);
		activeEnableInput(inputSearchBillsDetailsSoLuongTo, soLuongToValidation);
		hasConditionSearchDetails = true;
		hasErrorSearchDetails = false;
	}

	private void handleSearchKhuyenMai() {
		if(maKhuyenMai.equals("NULL")) {
			return;
		}

		if(maKhuyenMai.equals("NOTNULL") && inputSearchBillsDetailsKhuyenMai.getText().length() == 0) {
			return;
		}

		if(maKhuyenMai.length() == 0) {
			return;
		}

		maKhuyenMai = inputSearchBillsDetailsKhuyenMai.getText().trim();
		hasConditionSearch = true;
		hasErrorSearch = false;
	}

	public void refreshPanel() {
		arrayHoaDon = billBLL.getAllBill();	
		for(HoaDonDTO bill : arrayHoaDon) {
			tableModelBill = (DefaultTableModel)tableBillList.getModel();
			tableModelBill.addRow(bill.toArray());
		}

		tableModelBillDetails = (DefaultTableModel)tableBillDetailsList.getModel();
		tableModelBillDetails.setRowCount(0);

		resetValueInSearchBills();
		resetValueInSearchDetails();
	}

	private void setPropertyForLabel(JLabel label) {
		label.setFont(defaultFont);
		label.setForeground(Color.BLACK);
		label.setSize(Util.getSizeOfString(label.getText(), label.getFont()));
	}

	private void setPropertyForTitleLabel(JLabel labelTitle) {
		labelTitle.setFont(Util.getFont("Montserrat", "BOLD", 25));
		labelTitle.setSize(Util.getSizeOfString(labelTitle.getText(), labelTitle.getFont()));
		labelTitle.setForeground(Color.BLACK);
	}

	private void activeValidationInput(JTextField input, JLabel validation, String text) {
		input.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Constants.COLOR_TEXTFIELD_DANGER_BORDER), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		input.setEditable(true);
		validation.setText(text);
		validation.setVisible(true);
	}

	private void activeEnableInput(JTextField input, JLabel validation) {
		input.setText("");
		input.setBackground(Color.WHITE);
		input.setForeground(Constants.COLOR_TEXTFIELD_FOREGROUND);
		input.setEditable(true);
		input.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Constants.COLOR_TEXTFIELD_TYPE_BORDER), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		validation.setVisible(false);
	}

	private void setPropertyForInputField(JComponent input) {
		input.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Constants.COLOR_TEXTFIELD_TYPE_BORDER), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		input.setFont(defaultFont);
		input.setBackground(Color.WHITE);
		input.setForeground(Constants.COLOR_TEXTFIELD_FOREGROUND);
		input.setSize(450, 30);
	}

	private void setPropertyForButton(JButton button) {
		button.setFont(defaultFont);
		button.setForeground(Constants.COLOR_BACKGROUND_PRIMARY);
		button.setBackground(Constants.COLOR_PRIMARY);
		button.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Constants.COLOR_PRIMARY));
		button.setSize(175, 40);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				button.setForeground(Constants.COLOR_PRIMARY);
				button.setBackground(Constants.COLOR_BACKGROUND_PRIMARY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				button.setForeground(Constants.COLOR_BACKGROUND_PRIMARY);
				button.setBackground(Constants.COLOR_PRIMARY);
			}
		});
	}

	private void setPropertyForValidation(JLabel label) {
		label.setFont(defaultFont);
		label.setSize(0, 15);
		label.setForeground(Constants.COLOR_MESSAGE_VALIDATION);
	}

	private void setPropertyForRadioButton(JRadioButton radio) {
		radio.setBackground(Color.WHITE);
		radio.setSize(30, 30);
		radio.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	private void setPropertyForCheckBox(JCheckBox checkBox) {
		checkBox.setFont(defaultFont);
		checkBox.setBackground(Color.WHITE);
		checkBox.setForeground(Color.BLACK);
		checkBox.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Constants.COLOR_TEXTFIELD_TYPE_BORDER));
		checkBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	private void showMessage(String message) {
		JOptionPane.showMessageDialog(displayRegistry.getMainFrame(), message);
	}
}
