package GUI.statisticgui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;
import java.util.ArrayList;

import components.AppTable;
import common.FlexBox;
import common.Constants;
import common.Util;
import common.Date;
import common.FrameDisplayRegistry;
import DTO.HoaDonDTO;
import DTO.PhieuNhapDTO;
import BLL.BillBLL;
import BLL.PhieuNhapBLL;

public class StatisticDoanhThuPanel extends JPanel {

	private BillBLL billBLL;
	private PhieuNhapBLL phieuNhapBLL;
	private JPanel inputPanel;
	private String[] valuesComboBox;
	private JPanel[] inputComboBoxPanel;

	// month
	private JTextField inputYearInStatisticMonth;
	private JTextField inputMonthInStatisticMonth;
	private JLabel validationInputYearInStatisticMonth;
	private JLabel validationInputMonthInStatisticMonth;

	private JTextField inputInStatisticYear;
	private JLabel validationInputInStatisticYear;

	private JTextField inputDayFrom;
	private JLabel validationInputDayFrom;
	private JTextField inputDayTo;
	private JLabel validationInputDayTo;
	private int indexValueSelection;
	private JComboBox comboBox;	

	private JButton statisticButton;

	private JLabel tongThuLabel;
	private JLabel tongChiLabel;
	private JLabel tongThuTrungBinhLabel;
	private JLabel tongChiTrungBinhLabel;
	private JLabel tienHoaDonCaoNhat;
	private JLabel tienHoaDonThapNhat;
	private JLabel tienPhieuNhapCaoNhat;
	private JLabel tienPhieuNhapThapNhat;
	private JLabel loiNhuan;
	int tongThu;
	int tongChi;

	private AppTable billTable;
	private AppTable phieuNhapTable;
	private AppTable sanPhamTable;

	FrameDisplayRegistry displayRegistry;

	JPanel tablePanel;

	private Font defaultFont;

	public StatisticDoanhThuPanel() {
		setLayout(null);
		initVariable();
	}

	private void initVariable() {
		billBLL = BillBLL.getInstance();
		phieuNhapBLL = PhieuNhapBLL.getInstance();
		inputPanel = new JPanel(null);
		tablePanel = new JPanel(null);
		valuesComboBox = new String[] {"Ngày", "Tháng", "Năm"};
		inputComboBoxPanel = new JPanel[valuesComboBox.length];
		indexValueSelection = 0;
		comboBox = new JComboBox(valuesComboBox);
		inputYearInStatisticMonth = new JTextField();
		inputMonthInStatisticMonth = new JTextField();
		validationInputMonthInStatisticMonth = new JLabel();
		validationInputYearInStatisticMonth = new JLabel();
		inputInStatisticYear = new JTextField();
		validationInputInStatisticYear = new JLabel();
		inputDayTo = new JTextField();
		validationInputDayTo = new JLabel();
		inputDayFrom = new JTextField();
		validationInputDayFrom = new JLabel();
		defaultFont = Util.getFont("Roboto", "NORMAL", 17);
		tongThuLabel = new JLabel("Tổng thu: ");
		tongChiLabel = new JLabel("Tổng chi: ");
		tongThuTrungBinhLabel = new JLabel("Thu trung bình: ");
		tongChiTrungBinhLabel = new JLabel("Chi trung bình: ");
		tienHoaDonCaoNhat = new JLabel("Tiền hóa đơn cao nhất: ");
		tienHoaDonThapNhat = new JLabel("Tiền hóa đơn thấp nhất: ");
		tienPhieuNhapCaoNhat = new JLabel("Tiền phiếu nhập cao nhất: ");
		tienPhieuNhapThapNhat = new JLabel("Tiền phiếu nhập thấp nhất: ");
		loiNhuan = new JLabel("Lợi nhuận: ");
		displayRegistry = FrameDisplayRegistry.getInstance();
	}

	public void initComponent() {
		FlexBox flexBoxForStatisDoanhThuPanel = new FlexBox(this, FlexBox.DIRECTION_ROW);
		flexBoxForStatisDoanhThuPanel.setPadding(20);
		
		inputPanel.setBackground(Constants.COLOR_BACKGROUND_PRIMARY);
		tablePanel.setBackground(Constants.COLOR_BACKGROUND_PRIMARY);
		flexBoxForStatisDoanhThuPanel.setPositionWithinPercentSize(30, 0, inputPanel);
		initComponentInInputPanel();
		flexBoxForStatisDoanhThuPanel.setPositionWithinPercentSize(100, 40, tablePanel);
		initComponentInTablePanel();
	}

	// =================================== INPUT PANEL =======================================================

	private void initComponentInInputPanel() {
		FlexBox flexBoxForInputPanel = new FlexBox(inputPanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForInputPanel.setPadding(10);

		JLabel labelTitleForm = new JLabel("Doanh thu");
		setPropertyForTitleLabel(labelTitleForm);
		flexBoxForInputPanel.setPosition(labelTitleForm, 0, 0);
		
		JPanel comboxPanel = new JPanel(null);
		comboxPanel.setBackground(inputPanel.getBackground());
		comboxPanel.setSize(400, 40);
		FlexBox flexBoxForComboBoxPanel = new FlexBox(comboxPanel, FlexBox.DIRECTION_ROW);
		JLabel labelComboBox = new JLabel("Thống kê doanh thu theo:", SwingConstants.CENTER);
		setPropertyForLabel(labelComboBox);
		labelComboBox.setSize(0, 40);
		flexBoxForComboBoxPanel.setPositionWithinPercentSize(55, 0, labelComboBox);
		comboBox.setFont(defaultFont);
		comboBox.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Constants.COLOR_PRIMARY));
		flexBoxForComboBoxPanel.setPositionWithinPercentSize(100, 15, comboBox);
		flexBoxForInputPanel.setPosition(FlexBox.ALIGMENT_CENTER, 10, comboxPanel);

		JPanel containerPanel = new JPanel(null);
		containerPanel.setBackground(inputPanel.getBackground());
		containerPanel.setSize(500, 200);
		flexBoxForInputPanel.setPosition(FlexBox.ALIGMENT_CENTER, 20, containerPanel);
		

		for(int i = 0; i < inputComboBoxPanel.length; ++i) {
			inputComboBoxPanel[i] = new JPanel(null);
			inputComboBoxPanel[i].setSize(containerPanel.getSize());
			inputComboBoxPanel[i].setLocation(0, 0);
			inputComboBoxPanel[i].setVisible(false);
			inputComboBoxPanel[i].setBackground(inputPanel.getBackground());
			containerPanel.add(inputComboBoxPanel[i]);
		}

		initComponentInInputDayPanel(0);
		initComponentInInputMonthPanel(1);
		initComponentInInputYearPanel(2);

		inputComboBoxPanel[0].setVisible(true);

		statisticButton = new JButton("Thống kê");
		statisticButton.setFont(defaultFont);
		statisticButton.setForeground(Constants.COLOR_BACKGROUND_PRIMARY);
		statisticButton.setBackground(Constants.COLOR_PRIMARY);
		statisticButton.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Constants.COLOR_PRIMARY));
		statisticButton.setSize(175, 45);
		statisticButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		statisticButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				statisticButton.setForeground(Constants.COLOR_PRIMARY);
				statisticButton.setBackground(Constants.COLOR_BACKGROUND_PRIMARY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				statisticButton.setForeground(Constants.COLOR_BACKGROUND_PRIMARY);
				statisticButton.setBackground(Constants.COLOR_PRIMARY);
			}
		});
		flexBoxForInputPanel.setPosition(FlexBox.ALIGMENT_CENTER, 5, statisticButton);

		int widthLabel = 700;
		setPropertyForTitleLabelTable(tongThuLabel);
		tongThuLabel.setSize(widthLabel, 30);
		flexBoxForInputPanel.setPosition(tongThuLabel, 200, 15);
		setPropertyForTitleLabelTable(tongChiLabel);
		tongChiLabel.setSize(widthLabel, 30);
		flexBoxForInputPanel.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 5, tongChiLabel);
		setPropertyForTitleLabelTable(tongThuTrungBinhLabel);
		tongThuTrungBinhLabel.setSize(widthLabel, 30);
		flexBoxForInputPanel.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 5, tongThuTrungBinhLabel);
		setPropertyForTitleLabelTable(tongChiTrungBinhLabel);
		tongChiTrungBinhLabel.setSize(widthLabel, 30);
		flexBoxForInputPanel.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 5, tongChiTrungBinhLabel);

		setPropertyForTitleLabelTable(tienHoaDonCaoNhat);
		tienHoaDonCaoNhat.setSize(widthLabel, 30);
		flexBoxForInputPanel.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 5, tienHoaDonCaoNhat);

		setPropertyForTitleLabelTable(tienHoaDonThapNhat);
		tienHoaDonThapNhat.setSize(widthLabel, 30);
		flexBoxForInputPanel.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 5, tienHoaDonThapNhat);

		setPropertyForTitleLabelTable(tienPhieuNhapCaoNhat);
		tienPhieuNhapCaoNhat.setSize(widthLabel, 30);
		flexBoxForInputPanel.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 5, tienPhieuNhapCaoNhat);

		setPropertyForTitleLabelTable(tienPhieuNhapThapNhat);
		tienPhieuNhapThapNhat.setSize(widthLabel, 30);
		flexBoxForInputPanel.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 5, tienPhieuNhapThapNhat);

		setPropertyForTitleLabelTable(loiNhuan);
		loiNhuan.setSize(widthLabel, 30);
		flexBoxForInputPanel.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 5, loiNhuan);
		addEventToInputPanel();
	}

	private void addEventToInputPanel() {
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = comboBox.getSelectedIndex();
				if(index != indexValueSelection) {
					handleChooseValueComboBox(index);
				}
			}
		});

		statisticButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleButtonStatistic(comboBox.getSelectedIndex());
			}
		});
	}

	private void handleChooseValueComboBox(int indexPanel) {
		inputComboBoxPanel[indexPanel].setVisible(true);
		inputComboBoxPanel[indexValueSelection].setVisible(false);
		resetInputComboBox(indexValueSelection);
		resetValue();
		indexValueSelection = indexPanel;
	}

	private void handleButtonStatistic(int index) {
		switch(index) {
			case 0:
				handleEventInputDay();
				break;
			case 1:
				handleEventInputMonth();
				break;
			case 2:
				handleEventInputYear();
				break;
		}
	}

	private void handleEventInputDay() {
		if(inputDayFrom.getText().length() == 0 || inputDayTo.getText().length() == 0) {
			activeValidationLabel(validationInputDayTo, "Bạn chưa nhập dữ liệu");
			activeValidationLabel(validationInputDayFrom, "Bạn chưa nhập dữ liệu");
			return;
		}		

		Date dayFrom;
		Date dayTo;
		// kiem tra xem nguoi dung co nhap dung cu phap ngay thang khong
		try {
			dayFrom = new Date(inputDayFrom.getText());
		}
		catch(Exception ex) {
			activeValidationLabel(validationInputDayFrom, ex.getMessage());
			return;
		}

		try {
			dayTo = new Date(inputDayTo.getText());
		}
		catch(Exception ex) {
			activeValidationLabel(validationInputDayTo, ex.getMessage());
			return;
		}

		if(!dayFrom.isLessThan(dayTo)) {
			activeValidationLabel(validationInputDayFrom, "thời gian bắt đầu phải nhỏ hơn thời gian kết thúc");
			return;
		}

		ArrayList<HoaDonDTO> result =  billBLL.thongKeTheoNgay(dayFrom.toString().toString(), dayTo.toString());
		ArrayList<PhieuNhapDTO> resultPhieuNhap = phieuNhapBLL.thongKeTheoNgay(dayFrom.toString(), dayTo.toString());
		if(result.size() == 0 && resultPhieuNhap.size() == 0) {
			showMessage("Không tìm thấy dữ liệu thống kê");	
			return;
		}

		addValuesToBillTable(result);
		addValuesToPhieuNhapTable(resultPhieuNhap);
		addValuesToSanPhamTable(billBLL.thongKeSanPhamCoTrongDuLieuHoaDon(result));
		tongThu = billBLL.thongKeDoanhThuTheoNgay(dayFrom.toString(), dayTo.toString());
		tongChi = phieuNhapBLL.thongKeChiTheoNgay(dayFrom.toString(), dayTo.toString());
		tongThuLabel.setText("Tổng thu: " + Util.convertMoneyToString(tongThu));	
		tongThuTrungBinhLabel.setText("Thu trung bình mỗi ngày: " + Util.convertMoneyToString(billBLL.thongKeDoanhThuTrungBinhMoiNgay(dayFrom.toString(), dayTo.toString())));
		tongChiLabel.setText("Tổng chi: " + Util.convertMoneyToString(tongChi));
		tongChiTrungBinhLabel.setText("Chi trung bình mỗi ngày: " + Util.convertMoneyToString(phieuNhapBLL.thongKeChiTrungBinhMoiNgay(dayFrom.toString(), dayTo.toString())));
		tienHoaDonCaoNhat.setText("Tiền hóa đơn cao nhất: " + Util.convertMoneyToString(billBLL.thongKeHoaDonCaoNhatTheoNgay(dayFrom.toString(), dayTo.toString())));
		tienHoaDonThapNhat.setText("Tiền hóa đơn thấp nhất: " + Util.convertMoneyToString(billBLL.thongKeHoaDonThapNhatTheoNgay(dayFrom.toString(), dayTo.toString())));
		tienPhieuNhapCaoNhat.setText("Tiền phiếu nhập cao nhất: " + Util.convertMoneyToString(phieuNhapBLL.thongKePhieuNhapLonNhatTheoNgay(dayFrom.toString(), dayTo.toString())));
		tienPhieuNhapThapNhat.setText("Tiền phiếu nhập thấp nhất: " + Util.convertMoneyToString(phieuNhapBLL.thongKePhieuNhapThapNhapTheoNgay(dayFrom.toString(), dayTo.toString())));
		loiNhuan.setText("Lợi nhuận: " + Util.convertMoneyToString(tongThu - tongChi));

		resetInputComboBox(0);
	}

	public void handleEventInputMonth() {
		if(inputMonthInStatisticMonth.getText().length() == 0) {
			activeValidationLabel(validationInputMonthInStatisticMonth, "Bạn chưa nhập dữ liệu");
			return;
		}

		if(inputYearInStatisticMonth.getText().length() == 0) {
			activeValidationLabel(validationInputYearInStatisticMonth, "Bạn chưa nhập dữ liệu");
			return;
		}

		int month;
		int year;
		try {
			month = Integer.parseInt(inputMonthInStatisticMonth.getText());
		}
		catch(Exception ex) {
			activeValidationLabel(validationInputMonthInStatisticMonth, "Vui lòng nhập dữ liệu là số");
			return;
		}

		try {
			year = Integer.parseInt(inputYearInStatisticMonth.getText());
		}
		catch(Exception ex) {
			activeValidationLabel(validationInputYearInStatisticMonth, "Vui lòng nhập dữ liệu là số");
			return;
		}

		if(month < 1 || month > 12) {
			activeValidationLabel(validationInputMonthInStatisticMonth, "Vui lòng nhập dữ liệu trong phạm vi từ 1 - 12");
			return;
		}

		if(year < 1000) {
			activeValidationLabel(validationInputYearInStatisticMonth, "Vui lòng nhập năm là số > 1000");
		}

		ArrayList<HoaDonDTO> result =  billBLL.thongKeTheoThang(month, year);
		ArrayList<PhieuNhapDTO> resultPhieuNhap = phieuNhapBLL.thongKeTheoThang(month, year);
		if(result.size() == 0 && resultPhieuNhap.size() == 0) {
			showMessage("Không tìm thấy dữ liệu thống kê");	
			return;
		}

		addValuesToBillTable(result);
		addValuesToPhieuNhapTable(resultPhieuNhap);
		addValuesToSanPhamTable(billBLL.thongKeSanPhamCoTrongDuLieuHoaDon(result));
		tongThu = billBLL.thongKeDoanhThuTheoThang(month, year);
		tongChi = phieuNhapBLL.thongKeChiTheoThang(month, year);
		tongThuLabel.setText("Tổng thu: " + Util.convertMoneyToString(tongThu));
		tongThuTrungBinhLabel.setText("Thu trung bình 1 ngày: " + Util.convertMoneyToString(billBLL.thongKeDoanhThuTrungBinhMotNgayTheoThang(month, year)));
		tongChiLabel.setText("Tổng chi: " + Util.convertMoneyToString(tongChi));
		tongChiTrungBinhLabel.setText("Chi trung bình 1 ngày: " + Util.convertMoneyToString(phieuNhapBLL.thongKeChiTrungBinhMotNgayTrongThang(month, year)));
		tienHoaDonCaoNhat.setText("Tiền hóa đơn cao nhất: " + Util.convertMoneyToString(billBLL.thongKeHoaDonCaoNhatTheoThang(month, year)));
		tienHoaDonThapNhat.setText("Tiền hóa đơn thấp nhất: " + Util.convertMoneyToString(billBLL.thongKeHoaDonThapNhatTheoThang(month, year)));
		tienPhieuNhapCaoNhat.setText("Tiền phiếu nhập cao nhất: " + Util.convertMoneyToString(phieuNhapBLL.thongKePhieuNhapLonNhatTheoThang(month, year)));
		tienPhieuNhapThapNhat.setText("Tiền phiếu nhập thấp nhất: " + Util.convertMoneyToString(phieuNhapBLL.thongKePhieuNhapThapNhapTheoThang(month, year)));
		loiNhuan.setText("Lợi nhuận: " + Util.convertMoneyToString(tongThu - tongChi));
		resetInputComboBox(1);
	}

	public void handleEventInputYear() {

		if(inputInStatisticYear.getText().length() == 0) {
			activeValidationLabel(validationInputInStatisticYear, "Bạn chưa nhập dữ liệu");
			return;
		}

		int year;
		try {
			year = Integer.parseInt(inputInStatisticYear.getText());
		}
		catch(Exception ex) {
			activeValidationLabel(validationInputInStatisticYear, "Vui lòng nhập dữ liệu là số");
			return;
		}

		if(year < 2000) {
			activeValidationLabel(validationInputInStatisticYear, "Vui lòng nhập dữ liệu lớn hơn 2000");
			return;
		}

		ArrayList<HoaDonDTO> result =  billBLL.thongKeTheoNam(year);
		ArrayList<PhieuNhapDTO> resultPhieuNhap = phieuNhapBLL.thongKeTheoNam(year);
		if(result.size() == 0 && resultPhieuNhap.size() == 0) {
			showMessage("Không tìm thấy dữ liệu thống kê");	
			return;
		}

		addValuesToBillTable(result);
		addValuesToPhieuNhapTable(resultPhieuNhap);
		addValuesToSanPhamTable(billBLL.thongKeSanPhamCoTrongDuLieuHoaDon(result));
		billBLL.thongKeSanPhamCoTrongDuLieuHoaDon(result);
		tongThu = billBLL.thongKeDoanhThuTheoNam(year);
		tongChi = phieuNhapBLL.thongKeChiTheoNam(year);
		tongThuLabel.setText("Tổng thu: " + Util.convertMoneyToString(tongThu));	
		tongThuTrungBinhLabel.setText("Thu trung bình 1 tháng: " + Util.convertMoneyToString(billBLL.thongKeDoanhThuTrungBinhMotThangTheoNam(year)));
		tongChiLabel.setText("Tổng chi: " + Util.convertMoneyToString(tongChi));
		tongChiTrungBinhLabel.setText("Chi trung bình 1 tháng: " + Util.convertMoneyToString(phieuNhapBLL.thongKeChiTrungBinhMotThangTrongNam(year)));
		tienHoaDonCaoNhat.setText("Tiền hóa đơn cao nhất: " + Util.convertMoneyToString(billBLL.thongKeHoaDonCaoNhatTheoNam(year)));
		tienHoaDonThapNhat.setText("Tiền hóa đơn thấp nhất: " + Util.convertMoneyToString(billBLL.thongKeHoaDonThapNhatTheoNam(year)));
		tienPhieuNhapCaoNhat.setText("Tiền phiếu nhập cao nhất: " + Util.convertMoneyToString(phieuNhapBLL.thongKePhieuNhapLonNhatTheoNam(year)));
		tienPhieuNhapThapNhat.setText("Tiền phiếu nhập thấp nhất: " + Util.convertMoneyToString(phieuNhapBLL.thongKePhieuNhapThapNhapTheoNam(year)));
		loiNhuan.setText("Lợi nhuận: " + Util.convertMoneyToString(tongThu - tongChi));
		resetInputComboBox(2);
	}

	private void resetInputComboBox(int indexPanel) {
		if(indexPanel == 0) {
			validationInputDayTo.setVisible(false);
			validationInputDayFrom.setVisible(false);
			inputDayTo.setText("");
			inputDayFrom.setText("");
		}

		if(indexPanel == 1) {
			inputMonthInStatisticMonth.setText("");
			inputYearInStatisticMonth.setText("");
			validationInputMonthInStatisticMonth.setVisible(false);
			validationInputYearInStatisticMonth.setVisible(false);
		}

		if(indexPanel == 2) {
			inputInStatisticYear.setText("");
			validationInputInStatisticYear.setVisible(false);
		}
	}

	private void resetValue() {
		tongChiLabel.setText("Tổng thu: ");
		tongThuLabel.setText("Tổng chi: ");
		tongThuTrungBinhLabel.setText("Thu trung bình: ");
		tongChiTrungBinhLabel.setText("Chi trung bình: ");
		tienHoaDonCaoNhat.setText("Tiền hóa đơn cao nhất: ");
		tienHoaDonThapNhat.setText("Tiền hóa đơn thấp nhất: ");
		tienPhieuNhapCaoNhat.setText("Tiền phiếu nhập cao nhất: ");
		tienPhieuNhapThapNhat.setText("Tiền phiếu nhập thấp nhất: ");
		loiNhuan.setText("Lợi nhuận: ");
		resetValueInTable();
	}

	// indexPanl 1 la input Month, 2 la Year
	private void initComponentInInputDayPanel(int indexPanel) {
		FlexBox flexBoxForInputDayPanel = new FlexBox(inputComboBoxPanel[indexPanel], FlexBox.DIRECTION_COLUMN);	
		flexBoxForInputDayPanel.setPadding(20);

		JPanel dayFromPanel = new JPanel(null);
		dayFromPanel.setBackground(inputPanel.getBackground());
		dayFromPanel.setSize(0, 35);
		flexBoxForInputDayPanel.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 0, dayFromPanel);
		FlexBox flexBoxForDayFromPanel = new FlexBox(dayFromPanel, FlexBox.DIRECTION_ROW);
		JLabel labelDayFrom = new JLabel("Từ ngày: ", SwingConstants.CENTER);
		setPropertyForLabel(labelDayFrom);
		labelDayFrom.setSize(0, 35);
		flexBoxForDayFromPanel.setPositionWithinPercentSize(20, 0, labelDayFrom);
		inputDayFrom.setSize(0, 35);
		setPropertyForInputField(inputDayFrom);
		flexBoxForDayFromPanel.setPositionWithinPercentSize(100, 15, inputDayFrom);
		setPropertyForValidationLabel(validationInputDayFrom);
		flexBoxForInputDayPanel.setPosition(validationInputDayFrom, 5, 0);


		JPanel dayToPanel = new JPanel(null);
		dayToPanel.setBackground(inputPanel.getBackground());
		dayToPanel.setSize(0, 35);
		flexBoxForInputDayPanel.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 35, dayToPanel);	
		FlexBox flexBoxForDayToPanel = new FlexBox(dayToPanel, FlexBox.DIRECTION_ROW);
		JLabel labelDayTo = new JLabel("Đến ngày: ", SwingConstants.CENTER);
		setPropertyForLabel(labelDayTo);
		labelDayTo.setSize(0, 35);
		flexBoxForDayToPanel.setPositionWithinPercentSize(20, 0, labelDayTo);
		inputDayTo.setSize(0, 35);
		setPropertyForInputField(inputDayTo);
		flexBoxForDayToPanel.setPositionWithinPercentSize(100, 15, inputDayTo);
		setPropertyForValidationLabel(validationInputDayTo);
		flexBoxForInputDayPanel.setPosition(validationInputDayTo, 5, 0);

	}

	private void initComponentInInputMonthPanel(int indexPanel) {
		FlexBox flexBoxForInputMonthPanel = new FlexBox(inputComboBoxPanel[indexPanel], FlexBox.DIRECTION_COLUMN);	
		flexBoxForInputMonthPanel.setPadding(20);

		JPanel monthPanel = new JPanel(null);
		monthPanel.setBackground(inputPanel.getBackground());
		monthPanel.setSize(0, 35);
		flexBoxForInputMonthPanel.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 0, monthPanel);
		FlexBox flexBoxForMonthPanel = new FlexBox(monthPanel, FlexBox.DIRECTION_ROW);
		JLabel labelMonth = new JLabel("Tháng: ", SwingConstants.CENTER);
		setPropertyForLabel(labelMonth);
		labelMonth.setSize(0, 35);
		flexBoxForMonthPanel.setPositionWithinPercentSize(20, 0, labelMonth);
		inputMonthInStatisticMonth.setSize(0, 35);
		setPropertyForInputField(inputMonthInStatisticMonth);
		flexBoxForMonthPanel.setPositionWithinPercentSize(100, 15, inputMonthInStatisticMonth);
		setPropertyForValidationLabel(validationInputMonthInStatisticMonth);
		flexBoxForInputMonthPanel.setPosition(validationInputMonthInStatisticMonth, 5, 0);


		JPanel yearPanel = new JPanel(null);
		yearPanel.setBackground(inputPanel.getBackground());
		yearPanel.setSize(0, 35);
		flexBoxForInputMonthPanel.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 35, yearPanel);	
		FlexBox flexBoxForYearPanel = new FlexBox(yearPanel, FlexBox.DIRECTION_ROW);
		JLabel labelYear = new JLabel("Năm: ", SwingConstants.CENTER);
		setPropertyForLabel(labelYear);
		labelYear.setSize(0, 35);
		flexBoxForYearPanel.setPositionWithinPercentSize(20, 0, labelYear);
		inputYearInStatisticMonth.setSize(0, 35);
		setPropertyForInputField(inputYearInStatisticMonth);
		flexBoxForYearPanel.setPositionWithinPercentSize(100, 15, inputYearInStatisticMonth);
		setPropertyForValidationLabel(validationInputYearInStatisticMonth);
		flexBoxForInputMonthPanel.setPosition(validationInputYearInStatisticMonth, 5, 0);

	}

	private void initComponentInInputYearPanel(int indexPanel) {
		FlexBox flexBoxForInputPanel = new FlexBox(inputComboBoxPanel[indexPanel], FlexBox.DIRECTION_COLUMN);	
		flexBoxForInputPanel.setPadding(20);

		JPanel panel = new JPanel(null);
		panel.setBackground(inputPanel.getBackground());
		panel.setSize(0, 35);
		flexBoxForInputPanel.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 0, panel);
		FlexBox flexBoxForPanel = new FlexBox(panel, FlexBox.DIRECTION_ROW);
		JLabel label = new JLabel("Năm: ", SwingConstants.CENTER);
		setPropertyForLabel(label);
		label.setSize(0, 35);
		flexBoxForPanel.setPositionWithinPercentSize(20, 0, label);
		inputInStatisticYear.setSize(0, 35);
		setPropertyForInputField(inputInStatisticYear);
		flexBoxForPanel.setPositionWithinPercentSize(100, 15, inputInStatisticYear);
		setPropertyForValidationLabel(validationInputInStatisticYear);
		flexBoxForInputPanel.setPosition(validationInputInStatisticYear, 5, 0);
	}

	private void setPropertyForInputField(JTextField input) {
		input.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Constants.COLOR_PRIMARY));
		input.setFont(defaultFont);
		input.setForeground(Constants.COLOR_PRIMARY);
	}

	private void setPropertyForValidationLabel(JLabel label) {
		label.setFont(defaultFont);
		label.setForeground(Constants.COLOR_MESSAGE_VALIDATION);
		label.setVisible(false);
	}

	private void activeValidationLabel(JLabel label, String message) {
		label.setText(message);
		label.setSize(Util.getSizeOfString(message, label.getFont()));
		label.setVisible(true);
	}

	// =========================================== INPUT TABLE PANEL ===============================================
	private void initComponentInTablePanel() {
		FlexBox flexBoxForTablePanel = new FlexBox(tablePanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForTablePanel.setPadding(10);

		JLabel labelTitleForm = new JLabel("Bảng thống kê doanh thu");
		setPropertyForTitleLabel(labelTitleForm);
		flexBoxForTablePanel.setPosition(labelTitleForm, 0, 0);

		JPanel contentTablePanel = new JPanel(null);
		contentTablePanel.setBackground(tablePanel.getBackground());
		flexBoxForTablePanel.setPosition(FlexBox.ALIGNMENT_REMAINING_SIZE_OF_PANEL, 10, contentTablePanel);

		FlexBox flexBoxForContentTable = new FlexBox(contentTablePanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForContentTable.setPadding(10);

		// ========================== bang hoa don ===================================================
		// label
		JLabel labelBillTable = new JLabel("Bảng hóa đơn");
		setPropertyForTitleLabelTable(labelBillTable);
		flexBoxForContentTable.setPosition(labelBillTable, 0, 0);
		
		// table
		String[] columnNames = new String[] {"Mã hóa đơn", "Ngày bán", "Tổng tiền", "Mã nhân viên", "Mã khách hàng"};
		int[] columnSizes = new int[] {20, 20, 20, 20, 20};
		JScrollPane scroll = new JScrollPane();
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		flexBoxForContentTable.setPositionWithinPercentSize(26, 0, scroll);
		billTable = new AppTable(columnNames, columnSizes, scroll.getSize());
		scroll.getViewport().add(billTable);
		
		// ============================ bang phieu nhap ======================================================
		// label
		JLabel labelPhieuNhapTable = new JLabel("Bảng phiếu nhập");
		setPropertyForTitleLabelTable(labelPhieuNhapTable);
		flexBoxForContentTable.setPosition(labelPhieuNhapTable, 10, 0);

		// table
		columnNames = new String[] {"Mã phiếu nhập", "Mã nhân viên", "Mã nhà cung cấp", "Ngày nhập", "Tổng tiền"};
		columnSizes = new int[] {20, 20, 20, 20, 20};
		JScrollPane scrollPhieuNhap = new JScrollPane();
		scrollPhieuNhap.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		flexBoxForContentTable.setPositionWithinPercentSize(25, 10, scrollPhieuNhap);
		phieuNhapTable = new AppTable(columnNames, columnSizes, scrollPhieuNhap.getSize());
		scrollPhieuNhap.getViewport().add(phieuNhapTable);

		// ================================= bang san pham ===============================================
		// label
		JLabel labelSanPhamTable = new JLabel("Bảng sản phẩm");
		setPropertyForTitleLabelTable(labelSanPhamTable);
		flexBoxForContentTable.setPosition(labelSanPhamTable, 10, 0);

		// table
		columnNames = new String[] {"STT", "Mã sản phẩm", "Tên sản phẩm", "Loại sản phẩm", "Số lượng bán được"};
		columnSizes = new int[] {10, 15, 30, 20, 25};
		JScrollPane scrollSanPham = new JScrollPane();
		scrollSanPham.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		flexBoxForContentTable.setPositionWithinPercentSize(100, 10, scrollSanPham);
		sanPhamTable = new AppTable(columnNames, columnSizes, scrollSanPham.getSize());
		scrollSanPham.getViewport().add(sanPhamTable);
	}

	private void setPropertyForTitleLabel(JLabel labelTitle) {
		labelTitle.setFont(Util.getFont("Montserrat", "BOLD", 25));
		labelTitle.setSize(Util.getSizeOfString(labelTitle.getText(), labelTitle.getFont()));
		labelTitle.setForeground(Color.BLACK);
	}

	private void setPropertyForLabel(JLabel label) {
		label.setFont(defaultFont);
		label.setForeground(Color.BLACK);
	}

	private void setPropertyForTitleLabelTable(JLabel label) {
		label.setFont(Util.getFont("Roboto", "BOLD", 20));
		label.setSize(Util.getSizeOfString(label.getText(), label.getFont()));
		label.setForeground(Color.BLACK);
	}

	private void addValuesToBillTable(ArrayList<HoaDonDTO> bills) {
		DefaultTableModel tableModel = (DefaultTableModel)billTable.getModel();

		tableModel.setRowCount(0);
		for(HoaDonDTO bill : bills) {
			tableModel.addRow(bill.toArray());
		}
	}

	private void addValuesToPhieuNhapTable(ArrayList<PhieuNhapDTO> danhSachPhieuNhap) {
		DefaultTableModel tableModel = (DefaultTableModel)phieuNhapTable.getModel();

		tableModel.setRowCount(0);
		for(PhieuNhapDTO bill : danhSachPhieuNhap) {
			tableModel.addRow(bill.toArrayString());
		}
	}

	private void addValuesToSanPhamTable(ArrayList<ArrayList<String>> danhSach) {
		DefaultTableModel tableModel = (DefaultTableModel)sanPhamTable.getModel();

		tableModel.setRowCount(0);
		for(ArrayList<String> ds : danhSach) {
			tableModel.addRow(ds.toArray());
		}
	}

	private void resetValueInTable() {
		DefaultTableModel tableModel = (DefaultTableModel)billTable.getModel();
		tableModel.setRowCount(0);
		tableModel = (DefaultTableModel)phieuNhapTable.getModel();
		tableModel.setRowCount(0);
		tableModel = (DefaultTableModel)sanPhamTable.getModel();
		tableModel.setRowCount(0);
	}

	private void showMessage(String message) {
		JOptionPane.showMessageDialog(displayRegistry.getMainFrame(), message);
	}
}
