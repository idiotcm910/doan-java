package GUI.phieunhapgui;


import common.Util;
import common.FlexBox;
import common.Constants;
import common.FrameDisplayRegistry;
import common.Date;

import components.AppTable;
import components.NumberDocumentFilter;
import components.UppercaseDocumentFilter;

import BLL.PhieuNhapBLL;
import BLL.ChiTietPhieuNhapBLL;
import DTO.PhieuNhapDTO;
import DTO.ChiTietPhieuNhapDTO;

import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.text.AbstractDocument;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JComboBox;

import java.awt.Cursor;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class PhieuNhapTablePanel extends JPanel {
	private int indexSelectionRow;
	private PhieuNhapBLL phieuNhapBLL;
	private ChiTietPhieuNhapBLL chiTietPhieuNhapBLL;
	private FrameDisplayRegistry displayRegistry;

	// ========== bang phieu nhap
	private JPanel phieuNhapPanel;
	private AppTable phieuNhapTable;

	private JTextField inputSearchPhieuNhapDayFrom;
	private JLabel dayFromValidation;
	private JTextField inputSearchPhieuNhapDayTo;
	private JLabel dayToValidation;
	private JTextField inputSearchPhieuNhapNhaCungCap;
	private JTextField inputSearchPhieuNhapNhanVien;
	private JComboBox inputSearchPhieuNhapTongTien;

	private JButton searchPhieuNhapButton;
	private JButton cancelSearchPhieuNhapButton;
	private JComboBox chooseMethodSearchPhieuNhap;
	private String dayFrom, dayTo;
	private String maNhaCungCap;
	private String maNhanVien;
	private int minPN;
	private int maxPN;
	private boolean hasConditionSearchPN;
	private boolean hasErrorSearchPN;

	// ================== bang chi tiet phieu nhap
	private JPanel chiTietPhieuNhapPanel;
	private AppTable chiTietPhieuNhapTable;

	private JTextField inputSearchChiTietPhieuNhapSanPham;
	private JTextField inputSearchChiTietPhieuNhapSoLuongFrom;
	private JLabel soLuongFromValidation;
	private JTextField inputSearchChiTietPhieuNhapSoLuongTo;
	private JLabel soLuongToValidation;
	private JComboBox inputSearchChiTietPhieuNhapTongTien;
	private JComboBox chooseMethodSearchChiTietPhieuNhap;

	private String maSanPham;
	private int soLuongFrom; // %
	private int soLuongTo;
	private int minCTPN;
	private int maxCTPN;
	private boolean hasConditionSearchCTPN;
	private boolean hasErrorSearchCTPN;
	private JButton searchChiTietPhieuNhapButton;
	private JButton cancelSearchChiTietPhieuNhapButton;

	private Font defaultFont;

	public PhieuNhapTablePanel() {
		setLayout(null);
		initVariable();
	}

	private void initVariable() {
		indexSelectionRow = -1;
		phieuNhapBLL = PhieuNhapBLL.getInstance();
		chiTietPhieuNhapBLL = ChiTietPhieuNhapBLL.getInstance();
		displayRegistry = FrameDisplayRegistry.getInstance();
		defaultFont = Util.getFont("Roboto", "NORMAL", 18);
		String[] valueStringMethodSearch = new String[] {"HO???C (C?? 1 trong t???t c???)", "V?? (c?? t???t c???)"};

		// bang phieu nhap
		phieuNhapPanel = new JPanel(null);
		inputSearchPhieuNhapDayFrom = new JTextField();
		dayFromValidation = new JLabel();
		inputSearchPhieuNhapDayTo = new JTextField();
		dayToValidation = new JLabel();
		inputSearchPhieuNhapNhaCungCap = new JTextField();
		inputSearchPhieuNhapNhanVien = new JTextField();
		String[] valueCombobox = new String[] {"Vui l??ng ch???n kho???ng ti???n", "0?? - 20.000??", "20.000?? - 50.000??", "50.000?? - 100.000??", "100.000?? tr??? l??n"};
		inputSearchPhieuNhapTongTien = new JComboBox(valueCombobox);
		searchPhieuNhapButton = new JButton("T??m ki???m");
		cancelSearchPhieuNhapButton = new JButton("H???y t??m ki???m");
		chooseMethodSearchPhieuNhap = new JComboBox(valueStringMethodSearch);
		dayFrom = "";
		dayTo = "";
		maNhaCungCap = "";
		maNhanVien = "";
		minPN = 0;
		maxPN = 0;
		hasConditionSearchPN = false;
		hasErrorSearchPN = false;

		// bang chi tiet phieu nhap
		chiTietPhieuNhapPanel = new JPanel(null);
		inputSearchChiTietPhieuNhapSanPham = new JTextField();
		inputSearchChiTietPhieuNhapSoLuongFrom = new JTextField();
		inputSearchChiTietPhieuNhapSoLuongTo = new JTextField();
		soLuongFromValidation = new JLabel();
		soLuongToValidation = new JLabel();
		inputSearchChiTietPhieuNhapTongTien = new JComboBox(valueCombobox);

		maSanPham = "";
		soLuongFrom = 0;
		soLuongTo = 0;
		minCTPN = 0;
		maxCTPN = 0;
		hasConditionSearchCTPN = false;
		hasErrorSearchCTPN = false;
		searchChiTietPhieuNhapButton = new JButton("T??m ki???m");
		cancelSearchChiTietPhieuNhapButton = new JButton("H???y t??m ki???m");
		chooseMethodSearchChiTietPhieuNhap = new JComboBox(valueStringMethodSearch);
	}

	public void initComponentInPanel() {
		FlexBox flexBoxForPanel = new FlexBox(this, FlexBox.DIRECTION_ROW);
		flexBoxForPanel.setPadding(20);

		phieuNhapPanel.setBackground(Constants.COLOR_SPACE);
		flexBoxForPanel.setPositionWithinPercentSize(49, 0, phieuNhapPanel);
		initComponentInPhieuNhapPanel();
		addEventToPhieuNhap();

		chiTietPhieuNhapPanel.setBackground(Constants.COLOR_SPACE);
		flexBoxForPanel.setPositionWithinPercentSize(100, 20, chiTietPhieuNhapPanel);
		initComponentInChiTietPhieuNhapPanel();
		addEventToChiTietPhieuNhap();

		refreshPhieuNhapTable();
	}

	// ===================================== PHIEU NHAP ==============================================================================
	private void initComponentInPhieuNhapPanel() {
		FlexBox flexBoxForPanel = new FlexBox(phieuNhapPanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForPanel.setPadding(10);

		//========================================== bang du lieu
		JPanel firstPanel = new JPanel(null);	
		firstPanel.setBackground(Color.WHITE);
		flexBoxForPanel.setPositionWithinPercentSize(54, 0, firstPanel);

		FlexBox flexBoxForFirstPanel = new FlexBox(firstPanel, FlexBox.DIRECTION_COLUMN);
		JLabel titleLabelTalbe = new JLabel("Danh s??ch phi???u nh???p");
		setPropertyForTitleLabel(titleLabelTalbe);
		flexBoxForFirstPanel.setPosition(titleLabelTalbe, 0, 0);

		JPanel tablePanel = new JPanel(null);
		tablePanel.setBackground(firstPanel.getBackground());
		flexBoxForFirstPanel.setPosition(FlexBox.ALIGNMENT_REMAINING_SIZE_OF_PANEL, 0, tablePanel);
		FlexBox flexBoxForTable = new FlexBox(tablePanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForTable.setPadding(15);

		String columnNames[] = {"M?? phi???u", "M?? nh?? cung c???p", "M?? nh??n vi??n", "T???ng ti???n", "Ng??y nh???p"};
		int columnSize[] = {15, 25, 20, 20, 20};
		JScrollPane scroll = new JScrollPane();
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		flexBoxForTable.setPosition(FlexBox.ALIGNMENT_REMAINING_SIZE_OF_PANEL, 10, scroll);
		phieuNhapTable = new AppTable(columnNames, columnSize, scroll.getSize());
		scroll.getViewport().add(phieuNhapTable);

		// ============================================================ tim kiem
		JPanel secondPanel = new JPanel(null);
		secondPanel.setBackground(Color.WHITE);
		flexBoxForPanel.setPositionWithinPercentSize(100, 20, secondPanel);

		FlexBox flexBoxForSecond = new FlexBox(secondPanel, FlexBox.DIRECTION_ROW);

		JPanel inputPanel = new JPanel(null);
		inputPanel.setBackground(Color.WHITE);
		flexBoxForSecond.setPositionWithinPercentSize(55, 0, inputPanel);
		FlexBox flexBoxForInput = new FlexBox(inputPanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForInput.setPadding(10);

		JLabel titleLabel = new JLabel("T??m ki???m");
		setPropertyForTitleLabel(titleLabel);
		flexBoxForInput.setPosition(titleLabel, 0, 0);

		JLabel labelFrom = new JLabel("T??? ng??y: ");
		setPropertyForLabel(labelFrom);
		flexBoxForInput.setPosition(labelFrom, 0, 0);
		setPropertyForInputField(inputSearchPhieuNhapDayFrom);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, inputSearchPhieuNhapDayFrom);
		setPropertyForValidation(dayFromValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 2, dayFromValidation);

		JLabel labelTo = new JLabel("?????n ng??y: ");
		setPropertyForLabel(labelTo);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelTo);
		setPropertyForInputField(inputSearchPhieuNhapDayTo);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, inputSearchPhieuNhapDayTo);
		setPropertyForValidation(dayToValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 2, dayToValidation);

		JLabel labelTen = new JLabel("Theo m?? nh?? cung c???p: ");
		setPropertyForLabel(labelTen);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelTen);
		setPropertyForInputField(inputSearchPhieuNhapNhaCungCap);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, inputSearchPhieuNhapNhaCungCap);

		JLabel labelNhanVien = new JLabel("Theo m?? nh??n vi??n: ");
		setPropertyForLabel(labelNhanVien);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelNhanVien);
		setPropertyForInputField(inputSearchPhieuNhapNhanVien);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, inputSearchPhieuNhapNhanVien);

		JLabel labelTongTien = new JLabel("Theo s??? ti???n nh???p: ");
		setPropertyForLabel(labelTongTien);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelTongTien);
		setPropertyForInputField(inputSearchPhieuNhapTongTien);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, inputSearchPhieuNhapTongTien);

		JPanel buttonPanel = new JPanel(null);
		buttonPanel.setBackground(Color.WHITE);
		buttonPanel.setSize(365, 45);
		flexBoxForSecond.setPositionWithinPercentSize(100, 25, buttonPanel);
		FlexBox flexBoxForButton = new FlexBox(buttonPanel, FlexBox.DIRECTION_COLUMN);		
		setPropertyForButton(searchPhieuNhapButton);
		flexBoxForButton.setPosition(searchPhieuNhapButton, 50, 0);
		setPropertyForButton(cancelSearchPhieuNhapButton);
		flexBoxForButton.setPosition(cancelSearchPhieuNhapButton, 15, 0);
		JLabel labelMethod = new JLabel("Ph????ng th???c t??m ki???m:");
		setPropertyForLabel(labelMethod);
		flexBoxForButton.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 15, labelMethod);
		setPropertyForInputField(chooseMethodSearchPhieuNhap);
		chooseMethodSearchPhieuNhap.setSize(300, 35);
		flexBoxForButton.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 5, chooseMethodSearchPhieuNhap);	

		AbstractDocument documentNhaCungCap = (AbstractDocument)inputSearchPhieuNhapNhaCungCap.getDocument();
		documentNhaCungCap.setDocumentFilter(new UppercaseDocumentFilter());
		AbstractDocument documentNhanVien = (AbstractDocument)inputSearchPhieuNhapNhanVien.getDocument();
		documentNhanVien.setDocumentFilter(new UppercaseDocumentFilter());
	}

	private void addEventToPhieuNhap() {
		searchPhieuNhapButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleButtonSearchPhieuNhap();	
			}
		});

		cancelSearchPhieuNhapButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleButtonCancelPhieuNhap();
			}
		});

		phieuNhapTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = phieuNhapTable.getSelectedRow();

				if(index == indexSelectionRow) {
					return;
				}

				indexSelectionRow = index;
				refreshChiTietPhieuNhapTable();
			}
		});
	}

	public void refreshPhieuNhapTable() {
		addValuesInPhieuNhapTable(phieuNhapBLL.getAllPhieuNhap());
	}

	private void addValuesInPhieuNhapTable(ArrayList<PhieuNhapDTO> data) {
		DefaultTableModel tableModel = (DefaultTableModel)phieuNhapTable.getModel();	

		tableModel.setRowCount(0);
		for(PhieuNhapDTO dto : data) {
			tableModel.addRow(dto.toArrayString());
		}

		// cho bang chi tiet khuyen mai ve 0 du lieu
		addValuesToChiTietPhieuNhapTable(new ArrayList<ChiTietPhieuNhapDTO>());
		indexSelectionRow = -1;
	}

	private void handleButtonSearchPhieuNhap() {
		validationTime();
		validationNhaCungCap();
		validationNhanVien();
		validationTongTienphieuNhap();

		if(!hasConditionSearchPN && !hasErrorSearchPN) {
			showMessage("B???n ch??a nh???p ??i???u ki???n ????? c?? th??? t??m ki???m d??? li???u phi???u nh???p");
			return;
		}

		if(hasErrorSearchPN) {
			hasErrorSearchPN = false;
			return;
		}

		ArrayList<PhieuNhapDTO> result = null;
		if(chooseMethodSearchPhieuNhap.getSelectedIndex() == 0) {
			result = phieuNhapBLL.timKiemNangCaoTheoOR(dayFrom, dayTo, maNhaCungCap, maNhanVien, minPN, maxPN);
		}
		else {
			result = phieuNhapBLL.timKiemNangCaoTheoAND(dayFrom, dayTo, maNhaCungCap, maNhanVien, minPN, maxPN);
		}

		if(result.size() == 0) {
			showMessage("Kh??ng t??m th???y d??? li???u c???n t??m ki???m");
			return;
		}

		showMessage("T??m th???y d??? li???u phi???u nh???p th??nh c??ng");
		addValuesInPhieuNhapTable(result);
		resetDataFormSearchPhieuNhap();
	}

	private void handleButtonCancelPhieuNhap() {
		resetFormSearchPhieuNhap();
		refreshPhieuNhapTable();
	}

	private void resetDataFormSearchPhieuNhap() {
		hasConditionSearchPN = false;
		hasErrorSearchPN = false;
		maNhaCungCap = "";
		maNhanVien = "";
		dayFrom = "";
		dayTo = "";
		minPN = 0;
		maxPN = 0;
	}

	private void resetFormSearchPhieuNhap() {
		inputSearchPhieuNhapDayFrom.setText("");
		inputSearchPhieuNhapDayTo.setText("");
		inputSearchPhieuNhapNhaCungCap.setText("");
		inputSearchPhieuNhapNhanVien.setText("");
		inputSearchPhieuNhapTongTien.setSelectedIndex(0);
		dayFromValidation.setVisible(false);
		dayToValidation.setVisible(false);
		resetDataFormSearchPhieuNhap();
	}

	private void validationTime() {
		String dayFromStr = inputSearchPhieuNhapDayFrom.getText().trim();
		String dayToStr = inputSearchPhieuNhapDayTo.getText().trim();

		if(dayFromStr.length() == 0 && dayToStr.length() == 0) {
			activeSuccessInput(inputSearchPhieuNhapDayFrom, dayFromValidation);
			activeSuccessInput(inputSearchPhieuNhapDayTo, dayToValidation);
			return;
		}

		if(dayFromStr.length() == 0 && dayToStr.length() != 0) {
			activeValidationInput(inputSearchPhieuNhapDayFrom, dayFromValidation, "B???n ch??a nh???p d??? li???u");
			hasErrorSearchPN = true;
			return;
		}

		if(dayFromStr.length() != 0 && dayToStr.length() == 0) {
			activeValidationInput(inputSearchPhieuNhapDayTo, dayToValidation, "B???n ch??a nh???p d??? li???u");
			hasErrorSearchPN = true;
			return;
		}
		
		// kiem tra xem du lieu nhap co dung cu phap ngay thang nam khong (yyyy-mm-dd)
		boolean hasErrorParseDate = false;
		Date dateFrom = new Date(), dateTo = new Date();
		try {
			dateFrom = new Date(dayFromStr);
		}
		catch(Exception ex) {
			activeValidationInput(inputSearchPhieuNhapDayFrom, dayFromValidation, ex.getMessage());
			hasErrorParseDate = true;
		}

		try {
			dateTo = new Date(dayToStr);
		}
		catch(Exception ex) {
			activeValidationInput(inputSearchPhieuNhapDayTo, dayToValidation, ex.getMessage());
			hasErrorParseDate = true;
		}

		if(hasErrorParseDate) {
			hasErrorSearchPN = true;
			return;
		}

		if(Date.countDayInTwoDate(dateFrom, dateTo) < 1) {
			activeValidationInput(inputSearchPhieuNhapDayFrom, dayFromValidation, "th???i gian ph???i nh??? h??n th???i gian ??'?????n ng??y: '");
			hasErrorSearchPN = true;
			return;
		}


		dayFrom = dayFromStr;
		dayTo = dayToStr;
		hasErrorSearchPN = false;
		hasConditionSearchPN = true;
		activeSuccessInput(inputSearchPhieuNhapDayFrom, dayFromValidation);
		activeSuccessInput(inputSearchPhieuNhapDayTo, dayToValidation);
	}

	private void validationNhaCungCap() {
		String nhaCungCapPhieuNhap = inputSearchPhieuNhapNhaCungCap.getText().trim();

		if(nhaCungCapPhieuNhap.length() == 0) {
			return ;
		}

		maNhaCungCap = nhaCungCapPhieuNhap;
		hasConditionSearchPN = true;
	}

	private void validationNhanVien() {
		String nhanVienPhieuNhap = inputSearchPhieuNhapNhanVien.getText().trim();

		if(nhanVienPhieuNhap.length() == 0) {
			return ;
		}

		maNhanVien = nhanVienPhieuNhap;
		hasConditionSearchPN = true;
	}

	private void validationTongTienphieuNhap() {
		int min = 0, max = 0;
		switch(inputSearchPhieuNhapTongTien.getSelectedIndex()) {
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
			minPN = min;
			maxPN = max;
			hasConditionSearchPN = true;
		}
	}

	// =================================================== CHI TIET PHIEU NHAP ==================================================
	private void initComponentInChiTietPhieuNhapPanel() {
		FlexBox flexBoxForPanel = new FlexBox(chiTietPhieuNhapPanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForPanel.setPadding(10);

		//========================================== bang du lieu
		JPanel firstPanel = new JPanel(null);	
		firstPanel.setBackground(Color.WHITE);
		flexBoxForPanel.setPositionWithinPercentSize(54, 0, firstPanel);

		FlexBox flexBoxForFirstPanel = new FlexBox(firstPanel, FlexBox.DIRECTION_COLUMN);
		JLabel titleLabelTalbe = new JLabel("Danh s??ch chi ti???t phi???u nh???p");
		setPropertyForTitleLabel(titleLabelTalbe);
		flexBoxForFirstPanel.setPosition(titleLabelTalbe, 0, 0);

		JPanel tablePanel = new JPanel(null);
		tablePanel.setBackground(firstPanel.getBackground());
		flexBoxForFirstPanel.setPosition(FlexBox.ALIGNMENT_REMAINING_SIZE_OF_PANEL, 0, tablePanel);
		FlexBox flexBoxForTable = new FlexBox(tablePanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForTable.setPadding(15);

		String columnNames[] = {"M?? phi???u", "M?? s???n ph???m", "S??? l?????ng", "????n gi??", "Th??nh ti???n"};
		int columnSize[] = {15, 25, 20, 20, 20};
		JScrollPane scroll = new JScrollPane();
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		flexBoxForTable.setPosition(FlexBox.ALIGNMENT_REMAINING_SIZE_OF_PANEL, 10, scroll);
		chiTietPhieuNhapTable = new AppTable(columnNames, columnSize, scroll.getSize());
		scroll.getViewport().add(chiTietPhieuNhapTable);

		// ============================================================ tim kiem
		JPanel secondPanel = new JPanel(null);
		secondPanel.setBackground(Color.WHITE);
		flexBoxForPanel.setPositionWithinPercentSize(100, 20, secondPanel);

		FlexBox flexBoxForSecond = new FlexBox(secondPanel, FlexBox.DIRECTION_ROW);

		JPanel inputPanel = new JPanel(null);
		inputPanel.setBackground(Color.WHITE);
		flexBoxForSecond.setPositionWithinPercentSize(55, 0, inputPanel);
		FlexBox flexBoxForInput = new FlexBox(inputPanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForInput.setPadding(10);

		JLabel titleLabel = new JLabel("T??m ki???m");
		setPropertyForTitleLabel(titleLabel);
		flexBoxForInput.setPosition(titleLabel, 0, 0);

		JLabel labelFrom = new JLabel("S??? l?????ng t???: ");
		setPropertyForLabel(labelFrom);
		flexBoxForInput.setPosition(labelFrom, 0, 0);
		setPropertyForInputField(inputSearchChiTietPhieuNhapSoLuongFrom);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, inputSearchChiTietPhieuNhapSoLuongFrom);
		setPropertyForValidation(soLuongFromValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 2, soLuongFromValidation);

		JLabel labelTo = new JLabel("?????n: ");
		setPropertyForLabel(labelTo);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelTo);
		setPropertyForInputField(inputSearchChiTietPhieuNhapSoLuongTo);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, inputSearchChiTietPhieuNhapSoLuongTo);
		setPropertyForValidation(soLuongToValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 2, soLuongToValidation);

		JLabel labelTen = new JLabel("Theo m?? s???n ph???m: ");
		setPropertyForLabel(labelTen);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelTen);
		setPropertyForInputField(inputSearchChiTietPhieuNhapSanPham);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, inputSearchChiTietPhieuNhapSanPham);

		JLabel labelNhanVien = new JLabel("Theo s??? ti???n nh???p: ");
		setPropertyForLabel(labelNhanVien);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelNhanVien);
		setPropertyForInputField(inputSearchChiTietPhieuNhapTongTien);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, inputSearchChiTietPhieuNhapTongTien);


		JPanel buttonPanel = new JPanel(null);
		buttonPanel.setBackground(Color.WHITE);
		buttonPanel.setSize(365, 45);
		flexBoxForSecond.setPositionWithinPercentSize(100, 25, buttonPanel);
		FlexBox flexBoxForButton = new FlexBox(buttonPanel, FlexBox.DIRECTION_COLUMN);		
		setPropertyForButton(searchChiTietPhieuNhapButton);
		flexBoxForButton.setPosition(searchChiTietPhieuNhapButton, 50, 0);
		setPropertyForButton(cancelSearchChiTietPhieuNhapButton);
		flexBoxForButton.setPosition(cancelSearchChiTietPhieuNhapButton, 15, 0);
		JLabel labelMethod = new JLabel("Ph????ng th???c t??m ki???m:");
		setPropertyForLabel(labelMethod);
		flexBoxForButton.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 15, labelMethod);
		setPropertyForInputField(chooseMethodSearchChiTietPhieuNhap);
		chooseMethodSearchChiTietPhieuNhap.setSize(300, 35);
		flexBoxForButton.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 5, chooseMethodSearchChiTietPhieuNhap);	

		AbstractDocument documentSoLuongFrom = (AbstractDocument)inputSearchChiTietPhieuNhapSoLuongFrom.getDocument();
		documentSoLuongFrom.setDocumentFilter(new NumberDocumentFilter());
		AbstractDocument documentSoLuongTo = (AbstractDocument)inputSearchChiTietPhieuNhapSoLuongTo.getDocument();
		documentSoLuongTo.setDocumentFilter(new NumberDocumentFilter());
	}

	private void addEventToChiTietPhieuNhap() {
		searchChiTietPhieuNhapButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleButtonSearchChiTietPhieuNhap();	
			}
		});

		cancelSearchChiTietPhieuNhapButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleButtonCancelChiTietPhieuNhap();
			}
		});
	}

	private void handleButtonSearchChiTietPhieuNhap() {
		if(indexSelectionRow == -1) {
			showMessage("Hi???n t???i danh s??ch chi ti???t phi???u nh???p ch??a c?? d??? li???u, vui l??ng ch???n 1 d??ng b??n b???ng khuy???n m??i ????? c?? th??? hi???n th??? d??? li???u");
			return;
		}

		validationSanPham();
		validationSoLuong();
		validationTongTienChiTietPhieuNhap();

		if(!hasConditionSearchCTPN && !hasErrorSearchCTPN) {
			showMessage("B???n ch??a nh???p ??i???u ki???n ????? c?? th??? t??m ki???m d??? li???u chi ti???t phi???u nh???p");
			return;
		}

		if(hasErrorSearchCTPN) {
			hasErrorSearchCTPN = false;
			return;
		}

		String maPhieuNhap = phieuNhapBLL.getAllPhieuNhap().get(indexSelectionRow).getMaPhieu();
		ArrayList<ChiTietPhieuNhapDTO> result = null;
		if(chooseMethodSearchChiTietPhieuNhap.getSelectedIndex() == 0) {
			result = chiTietPhieuNhapBLL.timKiemNangCaoTheoOR(maSanPham, soLuongFrom, soLuongTo, minCTPN, maxCTPN, maPhieuNhap.trim());
		}
		else {
			result = chiTietPhieuNhapBLL.timKiemNangCaoTheoAND(maSanPham, soLuongFrom, soLuongTo, minCTPN, maxCTPN, maPhieuNhap.trim());
		}

		if(result.size() == 0) {
			showMessage("Kh??ng t??m th???y d??? li???u c???n t??m ki???m");
			return;
		}

		showMessage("T??m th???y d??? li???u chi ti???t phi???u nh???p th??nh c??ng");
		addValuesToChiTietPhieuNhapTable(result);
		resetDataFormSearchChiTietPhieuNhap();
	}

	private void handleButtonCancelChiTietPhieuNhap() {
		resetFormSearchChiTietPhieuNhap();
		refreshChiTietPhieuNhapTable();
	}

	private void resetDataFormSearchChiTietPhieuNhap() {
		hasConditionSearchCTPN = false;
		hasErrorSearchCTPN = false;

		maSanPham = "";
		soLuongFrom = 0;
		soLuongTo = 0;
		minCTPN = 0;
		maxCTPN = 0;
	}

	private void resetFormSearchChiTietPhieuNhap() {
		inputSearchChiTietPhieuNhapSoLuongFrom.setText("");
		inputSearchChiTietPhieuNhapSoLuongTo.setText("");
		inputSearchChiTietPhieuNhapSanPham.setText("");
		inputSearchChiTietPhieuNhapTongTien.setSelectedIndex(0);
		soLuongFromValidation.setVisible(false);
		soLuongToValidation.setVisible(false);
		resetDataFormSearchChiTietPhieuNhap();
	}

	private void refreshChiTietPhieuNhapTable() {
		if(indexSelectionRow == -1) {
			addValuesToChiTietPhieuNhapTable(new ArrayList<ChiTietPhieuNhapDTO>());
			return;
		}

		String maPhieuNhap = phieuNhapBLL.getAllPhieuNhap().get(indexSelectionRow).getMaPhieu();
		addValuesToChiTietPhieuNhapTable(chiTietPhieuNhapBLL.getAllChiTietPhieuNhap(maPhieuNhap.trim()));
	}

	private void addValuesToChiTietPhieuNhapTable(ArrayList<ChiTietPhieuNhapDTO> array) {
		DefaultTableModel tableModel = (DefaultTableModel)chiTietPhieuNhapTable.getModel();

		tableModel.setRowCount(0);
		for(ChiTietPhieuNhapDTO km : array) {
			tableModel.addRow(km.toArrayString());
		}
	}

	private void validationSanPham() {
		String sanPhamPhieuNhap = inputSearchChiTietPhieuNhapSanPham.getText().trim();

		if(sanPhamPhieuNhap.length() == 0) {
			return ;
		}

		maSanPham = sanPhamPhieuNhap;
		hasConditionSearchCTPN = true;
	}

	private void validationSoLuong() {
		String soLuongFromStr = inputSearchChiTietPhieuNhapSoLuongFrom.getText().trim();
		String soLuongToStr = inputSearchChiTietPhieuNhapSoLuongTo.getText().trim();

		if(soLuongFromStr.length() == 0 && soLuongToStr.length() == 0) {
			activeSuccessInput(inputSearchChiTietPhieuNhapSoLuongTo, soLuongFromValidation);
			activeSuccessInput(inputSearchChiTietPhieuNhapSoLuongFrom, soLuongToValidation);
			return;
		}

		if(soLuongFromStr.length() == 0 && soLuongToStr.length() != 0) {
			activeValidationInput(inputSearchChiTietPhieuNhapSoLuongFrom, soLuongFromValidation, "B???n ch??a nh???p d??? li???u");
			hasErrorSearchCTPN = true;
			return;
		}

		if(soLuongFromStr.length() != 0 && soLuongToStr.length() == 0) {
			activeValidationInput(inputSearchChiTietPhieuNhapSoLuongTo, soLuongToValidation, "B???n ch??a nh???p d??? li???u");
			hasErrorSearchCTPN = true;
			return;
		}
		
		int numberFrom = 0, numberTo = 0;

		boolean hasErrorParseNumber = false;
		try {
			numberFrom = Integer.parseInt(soLuongFromStr);
		}
		catch(Exception ex) {
			activeValidationInput(inputSearchChiTietPhieuNhapSoLuongFrom, soLuongFromValidation, "D??? li???u ph???i l?? s???");
			hasErrorParseNumber = true;
		}

		try {
			numberTo = Integer.parseInt(soLuongToStr);
		}
		catch(Exception ex) {
			activeValidationInput(inputSearchChiTietPhieuNhapSoLuongTo, soLuongToValidation, "D??? li???u ph???i l?? s???");
			hasErrorParseNumber = true;
		}

		if(hasErrorParseNumber) {
			hasErrorSearchCTPN = true;
			return;
		}
	

		boolean hasErrorNumber = false;
		if(numberFrom < 1) {
			activeValidationInput(inputSearchChiTietPhieuNhapSoLuongFrom, soLuongFromValidation, "D??? li???u ph???i l?? s?? l???n h??n 0");
			hasErrorNumber = true;
		}

		if(numberTo < 1) {
			activeValidationInput(inputSearchChiTietPhieuNhapSoLuongTo, soLuongToValidation, "D??? li???u ph???i l?? s?? l???n h??n 0");
			hasErrorNumber = true;
		}

		if(hasErrorNumber) {
			hasErrorSearchCTPN = true;
			return;
		}

		if(numberFrom > numberTo) {
			activeValidationInput(inputSearchChiTietPhieuNhapSoLuongFrom, soLuongFromValidation, "D??? li???u ??? ?? 'T???:' ph???i nh??? h??n ?? '?????n:'");
			hasErrorSearchCTPN = true;
			return;
		}
		
		soLuongFrom = numberFrom;
		soLuongTo = numberTo;
		activeSuccessInput(inputSearchChiTietPhieuNhapSoLuongFrom, soLuongFromValidation);
		activeSuccessInput(inputSearchChiTietPhieuNhapSoLuongTo, soLuongToValidation);
		hasErrorSearchCTPN = false;
		hasConditionSearchCTPN = true;
	}

	private void validationTongTienChiTietPhieuNhap() {
		int min = 0, max = 0;
		switch(inputSearchChiTietPhieuNhapTongTien.getSelectedIndex()) {
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
			minCTPN = min;
			maxCTPN = max;
			hasConditionSearchCTPN = true;
		}
	}

	public void refreshPanel() {
		refreshPhieuNhapTable();
	}

	// =====================================================================================================================
	private void activeValidationInput(JTextField input, JLabel validation, String message) {
		input.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Constants.COLOR_TEXTFIELD_DANGER_BORDER));
		validation.setText(message);
		validation.setVisible(true);
	}

	private void activeSuccessInput(JTextField input, JLabel validation) {
		input.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Constants.COLOR_PRIMARY));
		validation.setVisible(false);
	}

	private void setPropertyForLabel(JLabel label) {
		label.setFont(defaultFont);
		label.setForeground(Color.BLACK);
		label.setSize(Util.getSizeOfString(label.getText(), label.getFont()));
	}

	private void setPropertyForValidation(JLabel label) {
		label.setFont(defaultFont);
		label.setForeground(Constants.COLOR_MESSAGE_VALIDATION);
		label.setSize(250, 15);
	}

	private void setPropertyForTitleLabel(JLabel labelTitle) {
		labelTitle.setFont(Util.getFont("Montserrat", "BOLD", 25));
		labelTitle.setSize(Util.getSizeOfString(labelTitle.getText(), labelTitle.getFont()));
		labelTitle.setForeground(Color.BLACK);
	}

	private void setPropertyForInputField(JComponent input) {
		input.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Constants.COLOR_PRIMARY));
		input.setFont(defaultFont);
		input.setForeground(Color.BLACK);
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

	private void showMessage(String message) {
		JOptionPane.showMessageDialog(displayRegistry.getMainFrame(), message);
	}
}
