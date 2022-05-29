package GUI.khuyenmaigui;

import common.Util;
import common.FlexBox;
import common.Constants;
import common.FrameDisplayRegistry;
import common.Date;

import components.AppTable;

import BLL.KhuyenMaiBLL;
import BLL.ChiTietKhuyenMaiBLL;
import DTO.KhuyenMaiDTO;
import DTO.ChiTietKhuyenMaiDTO;

import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JComponent;

import java.awt.Cursor;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class KhuyenMaiTablePanel extends JPanel {
		
	private JPanel khuyenMaiPanel;
	private int indexSelectionRow;
	private AppTable khuyenMaiTable;
	private JPanel chiTietKhuyenMaiPanel;
	private AppTable chiTietKhuyenMaiTable;
	private KhuyenMaiBLL khuyenMaiBLL;
	private ChiTietKhuyenMaiBLL chiTietKhuyenMaiBLL;
	private FrameDisplayRegistry displayRegistry;

	// tim kiem bang khuyen mai
	private JTextField inputSearchKhuyenMaiDayFrom;
	private JLabel dayFromValidation;
	private JTextField inputSearchKhuyenMaiDayTo;
	private JLabel dayToValidation;
	private JTextField inputSearchKhuyenMaiTen;
	private JLabel tenValidation;
	private JButton searchKhuyenMaiButton;
	private JButton cancelSearchKhuyenMaiButton;
	private String dayFrom, dayTo;
	private String ten;
	private boolean wasSearch;
	private boolean hasConditionSearchKM;
	private boolean hasErrorSearchKM;
	private JComboBox chooseMethodSearchKhuyenMai;

	// tim kiem bang chi tiet khuyen mai
	private JTextField inputSearchChiTietKhuyenMaiTiLeFrom;
	private JTextField inputSearchChiTietKhuyenMaiTiLeTo;
	private JLabel tiLeValidation;
	private JTextField inputSearchChiTietKhuyenMaiMaSanPham;
	private JLabel maSanPhamValidation;
	private String maSanPham;
	private int tiLeFrom; // %
	private int tiLeTo;
	private boolean wasSearchChiTietKhuyenMai;
	private boolean hasConditionSearchCTKM;
	private boolean hasErrorSearchCTKM;
	private JButton searchChiTietKhuyenMaiButton;
	private JButton cancelSearchChiTietKhuyenMaiButton;
	private JComboBox chooseMethodSearchChiTietKhuyenMai;

	private Font defaultFont;
	public KhuyenMaiTablePanel() {
		setLayout(null);
		initVariable();
	}

	private void initVariable() {
		indexSelectionRow = -1;
		khuyenMaiBLL = KhuyenMaiBLL.getInstance();
		chiTietKhuyenMaiBLL = ChiTietKhuyenMaiBLL.getInstance();
		defaultFont = Util.getFont("Roboto", "NORMAL", 17);
		khuyenMaiPanel = new JPanel(null);
		chiTietKhuyenMaiPanel = new JPanel(null);
		displayRegistry = FrameDisplayRegistry.getInstance();
		String[] valueStringMethodSearch = new String[] {"HOẶC (Có 1 trong tất cả)", "VÀ (có tất cả)"};

		inputSearchKhuyenMaiDayTo = new JTextField();
		dayToValidation = new JLabel();
		inputSearchKhuyenMaiDayFrom = new JTextField();
		dayFromValidation = new JLabel();
		inputSearchKhuyenMaiTen = new JTextField();
		tenValidation = new JLabel();
		searchKhuyenMaiButton = new JButton("Tìm kiếm");
		cancelSearchKhuyenMaiButton = new JButton("Hủy tìm kiếm");
		dayFrom = "";
		dayTo = "";
		ten = "";
		wasSearch = false;
		hasConditionSearchKM = false;
		hasErrorSearchKM = false;
		chooseMethodSearchKhuyenMai = new JComboBox(valueStringMethodSearch);

		inputSearchChiTietKhuyenMaiMaSanPham = new JTextField();
		maSanPhamValidation = new JLabel();
		inputSearchChiTietKhuyenMaiTiLeFrom = new JTextField();
		inputSearchChiTietKhuyenMaiTiLeTo = new JTextField();
		tiLeValidation = new JLabel();
		maSanPham = "";
		tiLeFrom = 0;
		tiLeTo = 0;
		searchChiTietKhuyenMaiButton = new JButton("Tìm kiếm");
		cancelSearchChiTietKhuyenMaiButton = new JButton("Hủy tìm kiếm");
		wasSearchChiTietKhuyenMai = false;
		hasConditionSearchCTKM = false;
		hasErrorSearchCTKM = false;
		chooseMethodSearchChiTietKhuyenMai = new JComboBox(valueStringMethodSearch);
	}

	public void initComponentInPanel() {
		FlexBox flexBoxForPanel = new FlexBox(this, FlexBox.DIRECTION_ROW);
		flexBoxForPanel.setPadding(20);

		khuyenMaiPanel.setBackground(Constants.COLOR_SPACE);
		flexBoxForPanel.setPositionWithinPercentSize(49, 0, khuyenMaiPanel);
		initComponentInKhuyenMaiPanel();
		addEventToKhuyenMai();

		chiTietKhuyenMaiPanel.setBackground(Constants.COLOR_SPACE);
		flexBoxForPanel.setPositionWithinPercentSize(100, 30, chiTietKhuyenMaiPanel);
		initComponentInChiTietKhuyenMaiPanel();
		addEventToChiTietKhuyenMai();

		refreshKhuyenMaiTable();
	}

	private void initComponentInKhuyenMaiPanel() {
		FlexBox flexBoxForPanel = new FlexBox(khuyenMaiPanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForPanel.setPadding(10);

		//========================================== bang du lieu
		JPanel firstPanel = new JPanel(null);	
		firstPanel.setBackground(Color.WHITE);
		flexBoxForPanel.setPositionWithinPercentSize(58, 0, firstPanel);

		FlexBox flexBoxForFirstPanel = new FlexBox(firstPanel, FlexBox.DIRECTION_COLUMN);
		JLabel titleLabelTalbe = new JLabel("Danh sách khuyến mãi");
		setPropertyForTitleLabel(titleLabelTalbe);
		flexBoxForFirstPanel.setPosition(titleLabelTalbe, 0, 0);

		JPanel tablePanel = new JPanel(null);
		tablePanel.setBackground(firstPanel.getBackground());
		flexBoxForFirstPanel.setPosition(FlexBox.ALIGNMENT_REMAINING_SIZE_OF_PANEL, 0, tablePanel);
		FlexBox flexBoxForTable = new FlexBox(tablePanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForTable.setPadding(15);

		String columnNames[] = {"Mã khuyến mãi", "Ten khuyến mãi", "Ngày bắt đầu", "Ngày kết thúc"};
		int columnSize[] = {20, 30, 25, 25};
		JScrollPane scroll = new JScrollPane();
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		flexBoxForTable.setPosition(FlexBox.ALIGNMENT_REMAINING_SIZE_OF_PANEL, 10, scroll);
		khuyenMaiTable = new AppTable(columnNames, columnSize, scroll.getSize());
		scroll.getViewport().add(khuyenMaiTable);

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
		setPropertyForInputField(inputSearchKhuyenMaiDayFrom);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, inputSearchKhuyenMaiDayFrom);
		setPropertyForValidation(dayFromValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 2, dayFromValidation);

		JLabel labelTo = new JLabel("Đến ngày: ");
		setPropertyForLabel(labelTo);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelTo);
		setPropertyForInputField(inputSearchKhuyenMaiDayTo);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, inputSearchKhuyenMaiDayTo);
		setPropertyForValidation(dayToValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 2, dayToValidation);

		JLabel labelTen = new JLabel("Theo tên: ");
		setPropertyForLabel(labelTen);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelTen);
		setPropertyForInputField(inputSearchKhuyenMaiTen);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, inputSearchKhuyenMaiTen);
		setPropertyForValidation(tenValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 2, tenValidation);

		JPanel buttonPanel = new JPanel(null);
		buttonPanel.setBackground(Color.WHITE);
		buttonPanel.setSize(365, 45);
		flexBoxForSecond.setPositionWithinPercentSize(100, 25, buttonPanel);
		FlexBox flexBoxForButton = new FlexBox(buttonPanel, FlexBox.DIRECTION_COLUMN);		
		setPropertyForButton(searchKhuyenMaiButton);
		flexBoxForButton.setPosition(searchKhuyenMaiButton, 50, 0);
		setPropertyForButton(cancelSearchKhuyenMaiButton);
		flexBoxForButton.setPosition(cancelSearchKhuyenMaiButton, 15, 0);
		JLabel labelMethod = new JLabel("Phương thức tìm kiếm:");
		setPropertyForLabel(labelMethod);
		flexBoxForButton.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 15, labelMethod);
		setPropertyForJCombobox(chooseMethodSearchKhuyenMai);
		flexBoxForButton.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 5, chooseMethodSearchKhuyenMai);	

	}

	private void addEventToKhuyenMai() {
		searchKhuyenMaiButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleButtonSearchKhuyenMai();	
			}
		});

		cancelSearchKhuyenMaiButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleButtonCancelKhuyenMai();
			}
		});

		khuyenMaiTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = khuyenMaiTable.getSelectedRow();

				if(index == indexSelectionRow) {
					return;
				}

				indexSelectionRow = index;
				refreshChiTietKhuyenMaiTable();
			}
		});
	}

	public void refreshKhuyenMaiTable() {
		addValuesInKhuyenMaiTable(khuyenMaiBLL.getAllKhuyenMai());
	}

	private void addValuesInKhuyenMaiTable(ArrayList<KhuyenMaiDTO> data) {
		DefaultTableModel tableModel = (DefaultTableModel)khuyenMaiTable.getModel();	

		tableModel.setRowCount(0);
		for(KhuyenMaiDTO dto : data) {
			tableModel.addRow(dto.toArrayString());
		}

		// cho bang chi tiet khuyen mai ve 0 du lieu
		addValuesToChiTietKhuyenMaiTable(new ArrayList<ChiTietKhuyenMaiDTO>());
		indexSelectionRow = -1;
	}
	
	private void handleButtonSearchKhuyenMai() {
		validationTime();
		validationTen();

		if(!hasConditionSearchKM && !hasErrorSearchKM) {
			showMessage("Bạn chưa nhập điều kiện để có thể tìm kiếm dữ liệu khuyến mãi");
			return;
		}

		if(hasErrorSearchKM) {
			hasErrorSearchKM = false;
			return;
		}

		ArrayList<KhuyenMaiDTO> result = null;
		if(chooseMethodSearchKhuyenMai.getSelectedIndex() == 0) {
			result = khuyenMaiBLL.timKiemNangCaoTheoOR(dayFrom, dayTo, ten);
		}
		else {
			result = khuyenMaiBLL.timKiemNangCaoTheoAND(dayFrom, dayTo, ten);
		}

		if(result.size() == 0) {
			showMessage("Không tìm thấy dữ liệu cần tìm kiếm");
			return;
		}

		showMessage("Tìm kiếm khuyến mãi thành công");
		addValuesInKhuyenMaiTable(result);
		resetDataFormSearchKhuyenMai();
	}

	private void handleButtonCancelKhuyenMai() {
		resetFormSearchKhuyenMai();
		refreshKhuyenMaiTable();
	}

	private void resetDataFormSearchKhuyenMai() {
		hasConditionSearchKM = false;
		hasErrorSearchKM = false;
		dayTo = "";
		dayFrom = "";
		ten = "";	
	}

	private void resetFormSearchKhuyenMai() {
		inputSearchKhuyenMaiDayFrom.setText("");
		inputSearchKhuyenMaiDayTo.setText("");
		inputSearchKhuyenMaiTen.setText("");
		dayFromValidation.setVisible(false);
		dayToValidation.setVisible(false);
		tenValidation.setVisible(false);
		resetDataFormSearchKhuyenMai();
	}

	private void validationTime() {
		String dayFromStr = inputSearchKhuyenMaiDayFrom.getText().trim();
		String dayToStr = inputSearchKhuyenMaiDayTo.getText().trim();

		if(dayFromStr.length() == 0 && dayToStr.length() == 0) {
			activeSuccessInput(inputSearchKhuyenMaiDayFrom, dayFromValidation);
			activeSuccessInput(inputSearchKhuyenMaiDayTo, dayToValidation);
			return;
		}

		if(dayFromStr.length() == 0 && dayToStr.length() != 0) {
			activeValidationInput(inputSearchKhuyenMaiDayFrom, dayFromValidation, "Bạn chưa nhập dữ liệu");
			hasErrorSearchKM = true;
			return;
		}

		if(dayFromStr.length() != 0 && dayToStr.length() == 0) {
			activeValidationInput(inputSearchKhuyenMaiDayTo, dayToValidation, "Bạn chưa nhập dữ liệu");
			hasErrorSearchKM = true;
			return;
		}
		
		// kiem tra xem du lieu nhap co dung cu phap ngay thang nam khong (yyyy-mm-dd)
		boolean hasErrorParseDate = false;
		Date dateFrom = new Date(), dateTo = new Date();
		try {
			dateFrom = new Date(dayFromStr);
		}
		catch(Exception ex) {
			activeValidationInput(inputSearchKhuyenMaiDayFrom, dayFromValidation, ex.getMessage());
			hasErrorParseDate = true;
		}

		try {
			dateTo = new Date(dayToStr);
		}
		catch(Exception ex) {
			activeValidationInput(inputSearchKhuyenMaiDayTo, dayToValidation, ex.getMessage());
			hasErrorParseDate = true;
		}

		if(hasErrorParseDate) {
			hasErrorSearchKM = true;
			return;
		}

		if(Date.countDayInTwoDate(dateFrom, dateTo) < 1) {
			activeValidationInput(inputSearchKhuyenMaiDayFrom, dayFromValidation, "Thời gian phải nhỏ hơn so với thời gian của 'Đến ngày: '");
			hasErrorSearchKM = true;
			return;
		}


		dayFrom = dayFromStr;
		dayTo = dayToStr;
		hasErrorSearchKM = false;
		hasConditionSearchKM = true;
		activeSuccessInput(inputSearchKhuyenMaiDayFrom, dayFromValidation);
		activeSuccessInput(inputSearchKhuyenMaiDayTo, dayToValidation);
	}

	private void validationTen() {
		String tenKhuyenMai = inputSearchKhuyenMaiTen.getText().trim();

		if(tenKhuyenMai.length() == 0) {
			return ;
		}

		ten = tenKhuyenMai;
		hasConditionSearchKM = true;
		hasErrorSearchKM = false;
		activeSuccessInput(inputSearchKhuyenMaiTen, tenValidation);
	}

	private void initComponentInChiTietKhuyenMaiPanel() {
		FlexBox flexBoxForPanel = new FlexBox(chiTietKhuyenMaiPanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForPanel.setPadding(10);


		//=============================================================== bang du lieu
		JPanel firstPanel = new JPanel(null);
		firstPanel.setBackground(Color.WHITE);
		flexBoxForPanel.setPositionWithinPercentSize(58, 0, firstPanel);

		FlexBox flexBoxForFirstPanel = new FlexBox(firstPanel, FlexBox.DIRECTION_COLUMN);

		JLabel titleLabelTalbe = new JLabel("Danh sách chi tiết khuyến mãi");
		setPropertyForTitleLabel(titleLabelTalbe);
		flexBoxForFirstPanel.setPosition(titleLabelTalbe, 0, 0);

		JPanel tablePanel = new JPanel(null);
		tablePanel.setBackground(firstPanel.getBackground());
		flexBoxForFirstPanel.setPosition(FlexBox.ALIGNMENT_REMAINING_SIZE_OF_PANEL, 0, tablePanel);
		FlexBox flexBoxForTable = new FlexBox(tablePanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForTable.setPadding(15);

		String columnNames[] = {"Mã khuyến mãi", "Tỉ lệ khuyến mãi", "Mã sản phẩm"};
		int columnSize[] = {35, 35, 30};
		JScrollPane scroll = new JScrollPane();
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		flexBoxForTable.setPosition(FlexBox.ALIGNMENT_REMAINING_SIZE_OF_PANEL, 10, scroll);
		chiTietKhuyenMaiTable = new AppTable(columnNames, columnSize, scroll.getSize());
		scroll.getViewport().add(chiTietKhuyenMaiTable);

		// ============================================================== tim kiem
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
		
		JLabel labelTiLe = new JLabel("Tỉ lệ phần trâm khuyến mãi(%): ");
		setPropertyForLabel(labelTiLe);
		flexBoxForInput.setPosition(labelTiLe, 0, 0);

		JPanel tiLePanel = new JPanel(null);
		tiLePanel.setBackground(secondPanel.getBackground());
		tiLePanel.setSize(0, 30);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, tiLePanel);

		FlexBox flexBoxForTiLe = new FlexBox(tiLePanel, FlexBox.DIRECTION_ROW);
		JLabel labelTiLeFrom = new JLabel("Từ:");
		setPropertyForLabel(labelTiLeFrom);
		flexBoxForTiLe.setPositionWithinPercentSize(10, 0, labelTiLeFrom);
		setPropertyForInputField(inputSearchChiTietKhuyenMaiTiLeFrom);
		flexBoxForTiLe.setPositionWithinPercentSize(35, 10, inputSearchChiTietKhuyenMaiTiLeFrom);
		JLabel labelTiLeTo = new JLabel("Đến: ");
		setPropertyForLabel(labelTiLeTo);
		flexBoxForTiLe.setPositionWithinPercentSize(10, 10, labelTiLeTo);
		setPropertyForInputField(inputSearchChiTietKhuyenMaiTiLeTo);
		flexBoxForTiLe.setPositionWithinPercentSize(35, 10, inputSearchChiTietKhuyenMaiTiLeTo);
		setPropertyForValidation(tiLeValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 2, tiLeValidation);

		JLabel labelMaSanPham = new JLabel("Mã sản phẩm: ");
		setPropertyForLabel(labelMaSanPham);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelMaSanPham);
		setPropertyForInputField(inputSearchChiTietKhuyenMaiMaSanPham);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, inputSearchChiTietKhuyenMaiMaSanPham);
		setPropertyForValidation(maSanPhamValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 2, maSanPhamValidation);

		JPanel buttonPanel = new JPanel(null);
		buttonPanel.setSize(365, 45);
		buttonPanel.setBackground(Color.WHITE);
		flexBoxForSecond.setPositionWithinPercentSize(100, 20, buttonPanel);
		FlexBox flexBoxForButton = new FlexBox(buttonPanel, FlexBox.DIRECTION_COLUMN);		
		setPropertyForButton(searchChiTietKhuyenMaiButton);
		flexBoxForButton.setPosition(searchChiTietKhuyenMaiButton, 50, 0);
		setPropertyForButton(cancelSearchChiTietKhuyenMaiButton);
		flexBoxForButton.setPosition(cancelSearchChiTietKhuyenMaiButton, 15, 0);
		JLabel labelMethod = new JLabel("Phương thức tìm kiếm:");
		setPropertyForLabel(labelMethod);
		flexBoxForButton.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 15, labelMethod);
		setPropertyForJCombobox(chooseMethodSearchChiTietKhuyenMai);
		flexBoxForButton.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 5, chooseMethodSearchChiTietKhuyenMai);	
	}

	private void addEventToChiTietKhuyenMai() {
		searchChiTietKhuyenMaiButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleButtonSearchChiTietKhuyenMai();	
			}
		});

		cancelSearchChiTietKhuyenMaiButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleButtonCancelChiTietKhuyenMai();
			}
		});
	}

	private void handleButtonSearchChiTietKhuyenMai() {
		if(indexSelectionRow == -1) {
			showMessage("Hiện tại danh sách chi tiết khuyến mãi chưa có dữ liệu, vui lòng chọn 1 dòng bên bảng khuyến mãi để có thể hiển thị dữ liệu");
			return;
		}

		validationTiLe();
		validationMaSanPham();

		if(!hasConditionSearchCTKM && !hasErrorSearchCTKM) {
			showMessage("Bạn chưa nhập điều kiện để có thể tìm kiếm dữ liệu chi tiết khuyến mãi");
			return;
		}

		if(hasErrorSearchCTKM) {
			hasErrorSearchCTKM = false;
			return;
		}

		String maKhuyenMai = khuyenMaiBLL.getAllKhuyenMai().get(indexSelectionRow).getMaKhuyenMai();
		ArrayList<ChiTietKhuyenMaiDTO> result = null;
		if(chooseMethodSearchChiTietKhuyenMai.getSelectedIndex() == 0) {
			result = chiTietKhuyenMaiBLL.timKiemNangCaoTheoOR(maKhuyenMai, tiLeFrom, tiLeTo, maSanPham);
		}
		else {
			result = chiTietKhuyenMaiBLL.timKiemNangCaoTheoAND(maKhuyenMai, tiLeFrom, tiLeTo, maSanPham);
		}

		if(result.size() == 0) {
			showMessage("Không tìm thấy dữ liệu cần tìm kiếm");
			return;
		}

		showMessage("Tìm kiếm khuyến mãi thành công");
		addValuesToChiTietKhuyenMaiTable(result);
		resetDataFormSearchChiTietKhuyenMai();
	}

	private void handleButtonCancelChiTietKhuyenMai() {
		resetFormSearchChiTietKhuyenMai();
		refreshChiTietKhuyenMaiTable();
	}

	private void resetDataFormSearchChiTietKhuyenMai() {
		hasConditionSearchCTKM = false;
		hasErrorSearchCTKM = false;
		tiLeFrom = 0;
		tiLeTo = 0;
		maSanPham = "";
	}

	private void resetFormSearchChiTietKhuyenMai() {
		inputSearchChiTietKhuyenMaiTiLeFrom.setText("");
		inputSearchChiTietKhuyenMaiTiLeTo.setText("");
		inputSearchChiTietKhuyenMaiMaSanPham.setText("");
		tiLeValidation.setVisible(false);
		maSanPhamValidation.setVisible(false);
		resetDataFormSearchChiTietKhuyenMai();
	}

	private void validationTiLe() {
		String tiLeFromStr = inputSearchChiTietKhuyenMaiTiLeFrom.getText().trim();
		String tiLeToStr = inputSearchChiTietKhuyenMaiTiLeTo.getText().trim();

		if(tiLeFromStr.length() == 0 && tiLeToStr.length() == 0) {
			activeSuccessInput(inputSearchChiTietKhuyenMaiTiLeTo, tiLeValidation);
			activeSuccessInput(inputSearchChiTietKhuyenMaiTiLeFrom, tiLeValidation);
			return;
		}

		if(tiLeFromStr.length() == 0 && tiLeToStr.length() != 0) {
			activeValidationInput(inputSearchChiTietKhuyenMaiTiLeFrom, tiLeValidation, "Bạn chưa nhập dữ liệu");
			hasErrorSearchKM = true;
			return;
		}

		if(tiLeFromStr.length() != 0 && tiLeToStr.length() == 0) {
			activeValidationInput(inputSearchChiTietKhuyenMaiTiLeTo, tiLeValidation, "Bạn chưa nhập dữ liệu");
			hasErrorSearchKM = true;
			return;
		}
		
		int numberFrom = 0, numberTo = 0;

		boolean hasErrorParseNumber = false;
		try {
			numberFrom = Integer.parseInt(tiLeFromStr);
		}
		catch(Exception ex) {
			activeValidationInput(inputSearchChiTietKhuyenMaiTiLeFrom, tiLeValidation, "Dữ liệu phải là số");
			hasErrorParseNumber = true;
		}

		try {
			numberTo = Integer.parseInt(tiLeToStr);
		}
		catch(Exception ex) {
			activeValidationInput(inputSearchChiTietKhuyenMaiTiLeTo, tiLeValidation, "Dữ liệu phải là số");
			hasErrorParseNumber = true;
		}

		if(hasErrorParseNumber) {
			hasErrorSearchCTKM = true;
			return;
		}
	

		boolean hasErrorNumber = false;
		if(numberFrom < 1 || numberFrom > 100) {
			activeValidationInput(inputSearchChiTietKhuyenMaiTiLeFrom, tiLeValidation, "Dữ liệu phải là sô từ 1 đến 100");
			hasErrorNumber = true;
		}

		if(numberTo < 1 || numberTo > 100) {
			activeValidationInput(inputSearchChiTietKhuyenMaiTiLeTo, tiLeValidation, "Dữ liệu phải là sô từ 1 đến 100");
			hasErrorNumber = true;
		}

		if(hasErrorNumber) {
			hasErrorSearchCTKM = true;
			return;
		}

		if(numberFrom > numberTo) {
			activeValidationInput(inputSearchKhuyenMaiDayFrom, tiLeValidation, "Dữ liệu ở ô 'Từ: ' phải có giá trị nhỏ hơn ô 'Đến: '");
			hasErrorSearchCTKM = true;
			return;
		}
		
		tiLeFrom = numberFrom;
		tiLeTo = numberTo;
		activeSuccessInput(inputSearchChiTietKhuyenMaiTiLeFrom, tiLeValidation);
		activeSuccessInput(inputSearchChiTietKhuyenMaiTiLeTo, tiLeValidation);
		hasErrorSearchCTKM = false;
		hasConditionSearchCTKM = true;
	}

	private void validationMaSanPham() {
		String maSanPhamStr = inputSearchChiTietKhuyenMaiMaSanPham.getText().trim();

		if(maSanPhamStr.length() == 0) {
			return;
		}

		maSanPham = maSanPhamStr;
		activeSuccessInput(inputSearchChiTietKhuyenMaiMaSanPham, maSanPhamValidation);
		hasErrorSearchCTKM = false;
		hasConditionSearchCTKM = true;
	}

	private void refreshChiTietKhuyenMaiTable() {
		if(indexSelectionRow == -1) {
			addValuesToChiTietKhuyenMaiTable(new ArrayList<ChiTietKhuyenMaiDTO>());
			return;
		}

		String maKhuyenMai = khuyenMaiBLL.getAllKhuyenMai().get(indexSelectionRow).getMaKhuyenMai();
		addValuesToChiTietKhuyenMaiTable(chiTietKhuyenMaiBLL.getAllChiTietKhuyenMai(maKhuyenMai));
	}

	private void addValuesToChiTietKhuyenMaiTable(ArrayList<ChiTietKhuyenMaiDTO> array) {
		DefaultTableModel tableModel = (DefaultTableModel)chiTietKhuyenMaiTable.getModel();

		tableModel.setRowCount(0);
		for(ChiTietKhuyenMaiDTO km : array) {
			tableModel.addRow(km.toArrayString());
		}
	}


// ============================================================================================================
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

	private void setPropertyForJCombobox(JComboBox input) {
		input.setFont(defaultFont);
		input.setForeground(Constants.COLOR_TEXTFIELD_FOREGROUND);
		input.setSize(300, 35);
	}

	private void showMessage(String message) {
		JOptionPane.showMessageDialog(displayRegistry.getMainFrame(), message);
	}
}
