package GUI.billgui;

import DTO.BillDetailsDTO;
import DTO.HoaDonDTO;
import DTO.ProductDTO;
import DTO.CustomerDTO;
import DTO.KhuyenMaiDTO;
import BLL.ProductBLL;
import BLL.BillBLL;
import BLL.CustomerBLL;
import BLL.KhuyenMaiBLL;
import BLL.BillDetailsBLL;
import BLL.ChiTietKhuyenMaiBLL;

import common.FlexBox;
import common.Constants;
import common.Util;
import common.FrameDisplayRegistry;
import components.UserToken;
import components.AppTable;
import components.UppercaseDocumentFilter;
import components.NumberDocumentFilter;
import components.TMoneyField;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Cursor;
import java.util.ArrayList;



class AddBillPanel extends JPanel {
	private Font defaultFont;
	private FrameDisplayRegistry displayRegistry;
	private BillBLL billBLL;
	private BillDetailsBLL billDetailsBLL;
	private ProductBLL productBLL;
	private CustomerBLL customerBLL;
	private KhuyenMaiBLL khuyenMaiBLL;
	private ChiTietKhuyenMaiBLL chiTietKMBLL;
	private UserToken token;

	// form hoa don
	private JPanel hoaDonFormPanel;		

	private JTextField maHoaDonInput;
	private JLabel maHoaDonValidation;
	private JTextField ngayNhapThongTinInput;
	private JLabel ngayNhapThongTinValidation;
	private TMoneyField tongTienInput;
	private JLabel tongTienValidation;
	private JTextField nhanVienInput;
	private JLabel nhanVienValidation;
	private JCheckBox checkBoxKhachHang;
	private JTextField khachHangInput;
	private JLabel khachHangValidation;
	private JButton chooseMaKhachHang;
	private boolean isValidMaKhachHang;
	// frame choose ma khach hang
	private JFrame frameChooseMaKhachHang;
	private AppTable tableFrameMaKhachHang;
	private JTextField searchMaKhachHangInput;

	private JButton initHoaDonButton;
	private JButton addHoaDonButton;
	private JButton refreshHoaDonButton;
	private HoaDonDTO hoaDonCache;

	// form chi tiet khuyen mai
	private JPanel chiTietHoaDonFormPanel;	

	private JTextField maChiTietHoaDonInput;
	private JLabel maChiTietHoaDonValidation;
	private JTextField maSanPhamInput;
	private JLabel maSanPhamValidation;
	private JButton chooseMaSanPham;
	private boolean isValidMaSanPham;
	// frame choose ma san pham
	private JFrame frameChooseMaSanPham;
	private AppTable tableFrameMaSanPham;
	private JTextField searchMaSanPhamInput;

	private JCheckBox checkBoxMaKhuyenMai;
	private JTextField maKhuyenMaiInput;
	private JLabel maKhuyenMaiValidation;
	private JButton chooseMaKhuyenMai;
	private boolean isValidMaKhuyenMai;
	// frame choose ma khuyen mai
	private JFrame frameChooseMaKhuyenMai;
	private AppTable tableFrameMaKhuyenMai;
	private JTextField searchMaKhuyenMaiInput;


	private JTextField soLuongInput;
	private JLabel soLuongValidation;
	private boolean isValidSoLuong;
	private TMoneyField donGiaInput;
	private JLabel donGiaValidation;
	private TMoneyField thanhTienInput;
	private JLabel thanhTienValidation;

	private JButton initChiTietHoaDonButton;
	private JButton addChiTietHoaDonButton;
	private BillDetailsDTO chiTietHoaDonCache;
	private BillDetailsDTO chiTietHoaDonCacheModify;
	private ArrayList<BillDetailsDTO> mangChiTietHoaDon;

	// bang du lieu chi tiet khuyen mai da nhap
	private JPanel chiTietHoaDonTablePanel;
	private JButton deleteChiTietHoaDonButton;
	private JButton modifyChiTietHoaDonButton;
	private AppTable tableChiTietHoaDon;
	private int indexSelectionRow;


	private boolean hasInitHoaDon;
	private boolean hasInitChiTietHoaDon;
	private boolean hasDataChiTietHoaDon;
	private boolean hasModifyChiTietHoaDon;

	public AddBillPanel() {
		setLayout(null);
		initVariable();
	}
	
	private void initVariable() {
		defaultFont = Util.getFont("Roboto", "NORMAL", 18);
		displayRegistry = FrameDisplayRegistry.getInstance();
		billBLL = BillBLL.getInstance();
		billDetailsBLL = BillDetailsBLL.getInstance();
		productBLL = ProductBLL.getInstance();
		customerBLL = CustomerBLL.getInstance();
		khuyenMaiBLL = KhuyenMaiBLL.getInstance();
		chiTietKMBLL = ChiTietKhuyenMaiBLL.getInstance();
		token = UserToken.getInstance();

		// form hoa don
		hoaDonFormPanel = new JPanel(null);
		maHoaDonInput = new JTextField();
		maHoaDonValidation = new JLabel();
		ngayNhapThongTinInput = new JTextField();
		ngayNhapThongTinValidation = new JLabel();
		tongTienInput = new TMoneyField(8);
		tongTienValidation = new JLabel();
		nhanVienInput = new JTextField();
		nhanVienValidation = new JLabel();
		checkBoxKhachHang = new JCheckBox("C?? m??");
		khachHangInput = new JTextField();
		khachHangValidation = new JLabel();
		chooseMaKhachHang = new JButton("...");
		isValidMaKhachHang = false;
		frameChooseMaKhachHang = new JFrame();
		searchMaKhachHangInput = new JTextField();
		initHoaDonButton = new JButton("Kh???i t???o h??a ????n");
		addHoaDonButton = new JButton("Th??m h??a ????n");
		refreshHoaDonButton = new JButton("L??m m???i h??a ????n");

		// form chi tiet hoa don
		chiTietHoaDonFormPanel = new JPanel(null);
		maChiTietHoaDonInput = new JTextField();
		maChiTietHoaDonValidation = new JLabel();
		maSanPhamInput = new JTextField();
		maSanPhamValidation = new JLabel();
		chooseMaSanPham = new JButton("...");
		isValidMaSanPham = false;
		frameChooseMaSanPham = new JFrame();
		searchMaSanPhamInput = new JTextField();
		maKhuyenMaiInput = new JTextField();
		maKhuyenMaiValidation = new JLabel();
		isValidMaKhuyenMai = false;
		chooseMaKhuyenMai = new JButton("...");
		checkBoxMaKhuyenMai = new JCheckBox("C?? m??");
		frameChooseMaKhuyenMai = new JFrame();
		searchMaKhuyenMaiInput = new JTextField();
		soLuongInput = new JTextField();
		soLuongValidation = new JLabel();
		donGiaInput = new TMoneyField(8);
		donGiaValidation = new JLabel();
		thanhTienInput = new TMoneyField(8);
		thanhTienValidation = new JLabel();
		initChiTietHoaDonButton = new JButton("Kh???i t???o chi ti???t h??a ????n");
		addChiTietHoaDonButton = new JButton("Th??m chi ti???t h??a ????n");

		// bang du lieu chi tiet hoa don
		chiTietHoaDonTablePanel = new JPanel(null);
		deleteChiTietHoaDonButton = new JButton("X??a chi ti???t h??a ????n");
		modifyChiTietHoaDonButton = new JButton("Ch???nh s???a chi ti???t h??a ????n");
		indexSelectionRow = -1;
		hasInitHoaDon = false;
		hasInitChiTietHoaDon = false;
		hasDataChiTietHoaDon = false;
		hasModifyChiTietHoaDon = false;
	}

	// ================================== init component in AddBillPanel =============================================
	public void initComponent() {
		setBackground(Constants.COLOR_SPACE);
		FlexBox flexBoxForPanel = new FlexBox(this, FlexBox.DIRECTION_ROW);
		flexBoxForPanel.setPadding(25);
		
		hoaDonFormPanel.setBackground(Color.WHITE);
		flexBoxForPanel.setPositionWithinPercentSize(30, 0, hoaDonFormPanel);

		chiTietHoaDonFormPanel.setBackground(Color.WHITE);
		flexBoxForPanel.setPositionWithinPercentSize(30, 30, chiTietHoaDonFormPanel);

		chiTietHoaDonTablePanel.setBackground(Color.WHITE);
		flexBoxForPanel.setPositionWithinPercentSize(35, 30, chiTietHoaDonTablePanel);

		initComponentInBillForm();
		addEventToHoaDonForm();

		initComponentInBillDetailForm();
		addEventToChiTietHoaDonForm();

		initComponentInBillDetailTable();
		addEventToChiTietHoaDonTable();
	}

	// ================================ form hoa don ========================================================================
	private void initComponentInBillForm() {
		FlexBox flexBoxForPanel = new FlexBox(hoaDonFormPanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForPanel.setPadding(10);

		JLabel titleLabelTalbe = new JLabel("Th??ng tin h??a ????n");
		setPropertyForTitleLabel(titleLabelTalbe);
		flexBoxForPanel.setPosition(titleLabelTalbe, 0, 0);

		JPanel inputPanel = new JPanel(null);
		inputPanel.setBackground(hoaDonFormPanel.getBackground());
		flexBoxForPanel.setPositionWithinPercentSize(70, 10, inputPanel);

		FlexBox flexBoxForInput = new FlexBox(inputPanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForInput.setPadding(10);

		JLabel labelmaHoaDon = new JLabel("M?? h??a ????n");
		setPropertyForLabel(labelmaHoaDon);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelmaHoaDon);
		setPropertyForInputField(maHoaDonInput);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, maHoaDonInput);
		setPropertyForValidation(maHoaDonValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, maHoaDonValidation);

		JLabel labelNgayNhapThongTin = new JLabel("Ng??y nh???p th??ng tin");
		setPropertyForLabel(labelNgayNhapThongTin);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelNgayNhapThongTin);
		setPropertyForInputField(ngayNhapThongTinInput);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, ngayNhapThongTinInput);
		setPropertyForValidation(ngayNhapThongTinValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, ngayNhapThongTinValidation);

		JLabel labelTongTien = new JLabel("T???ng ti???n");
		setPropertyForLabel(labelTongTien);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelTongTien);
		setPropertyForInputField(tongTienInput);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, tongTienInput);
		setPropertyForValidation(tongTienValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, tongTienValidation);

		JLabel labelMaNhanVien = new JLabel("M?? nh??n vi??n");
		setPropertyForLabel(labelMaNhanVien);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelMaNhanVien);
		setPropertyForInputField(nhanVienInput);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, nhanVienInput);
		setPropertyForValidation(nhanVienValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, nhanVienValidation);

		JLabel labelMaKhachHang = new JLabel("M?? kh??ch h??ng");
		setPropertyForLabel(labelMaKhachHang);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelMaKhachHang);

		JPanel khachHangPanel = new JPanel(null);
		khachHangPanel.setBackground(hoaDonFormPanel.getBackground());
		khachHangPanel.setSize(0, 35);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, khachHangPanel);
		FlexBox flexBoxForMaKhachHang = new FlexBox(khachHangPanel, FlexBox.DIRECTION_ROW);
		setPropertyForCheckBox(checkBoxKhachHang);
		flexBoxForMaKhachHang.setPositionWithinPercentSize(20, 0, checkBoxKhachHang);
		setPropertyForInputField(khachHangInput);
		flexBoxForMaKhachHang.setPositionWithinPercentSize(70, 5, khachHangInput);
		chooseMaKhachHang.setCursor(new Cursor(Cursor.HAND_CURSOR));
		chooseMaKhachHang.setBackground(hoaDonFormPanel.getBackground());
		flexBoxForMaKhachHang.setPositionWithinPercentSize(100, 5, chooseMaKhachHang);
		setPropertyForValidation(khachHangValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, khachHangValidation);

		JPanel buttonPanel = new JPanel(null);
		buttonPanel.setBackground(hoaDonFormPanel.getBackground());
		flexBoxForPanel.setPositionWithinPercentSize(100, 10, buttonPanel);

		FlexBox flexBoxForButton = new FlexBox(buttonPanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForButton.setPadding(10);
		setPropertyForButton(initHoaDonButton);
		flexBoxForButton.setPosition(initHoaDonButton, 0, 0);
		setPropertyForButton(addHoaDonButton);
		flexBoxForButton.setPosition(addHoaDonButton, 15, 0);
		setPropertyForButton(refreshHoaDonButton);
		flexBoxForButton.setPosition(refreshHoaDonButton, 15, 0);

		refreshFormHoaDon();

		createWindowChooseMaKhachHang();

		AbstractDocument document = (AbstractDocument)maHoaDonInput.getDocument();
		document.setDocumentFilter(new UppercaseDocumentFilter());
		AbstractDocument documentKhachHang = (AbstractDocument)khachHangInput.getDocument();
		documentKhachHang.setDocumentFilter(new UppercaseDocumentFilter());
	}

	private void addEventToHoaDonForm() {
		initHoaDonButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleEventInitHoaDon();	
			}
		});

		addHoaDonButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleEventAddHoaDon();	
			}
		});

		refreshHoaDonButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleEventRefreshHoaDon();	
			}
		});

		chooseMaKhachHang.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				displayRegistry.subFrameRegistry(frameChooseMaKhachHang);	
				refreshFrameChooseMaKhachHang();
			}
		});

		checkBoxKhachHang.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleCheckBoxMaKhachHang();
			}
		});

		maHoaDonInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					handleEventInitHoaDon();
				}
			}
		});

		khachHangInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					validationMaKhachHang();
				}
			}
		});
	}

	private void handleEventInitHoaDon() {
		if(hasInitHoaDon) {
			showMessage("h??a ????n ???? ???????c kh???i t???o, ????? kh???i t???o m???i vui l??ng b???m n??t l??m m???i h??a ????n");
			return;
		}

		if(!validationMaHoaDon()) {
			return;
		}

		activeEnableInput(khachHangInput, khachHangValidation);
		chooseMaKhachHang.setEnabled(true);
		checkBoxKhachHang.setEnabled(true);
		handleCheckBoxMaKhachHang();

		hoaDonCache.setNgayNhapThongTin(Util.getDateNow());
		hoaDonCache.setTongTien(0);
		hoaDonCache.setMaNhanVien(token.getNhanVien().getMaNhanVien());

		activeSuccessInput(ngayNhapThongTinInput, ngayNhapThongTinValidation);
		ngayNhapThongTinInput.setText(hoaDonCache.getNgayNhapThongTin());
		activeSuccessInput(tongTienInput, tongTienValidation);
		tongTienInput.setText("20");
		tongTienInput.setText("");
		activeSuccessInput(nhanVienInput, nhanVienValidation);
		nhanVienInput.setText(token.getNhanVien().getTenNhanVien());
		nhanVienInput.setToolTipText("M?? nh??n vi??n: " + hoaDonCache.getMaNhanVien());
		
		hasInitHoaDon = true;
		mangChiTietHoaDon = new ArrayList<BillDetailsDTO>();
	}
	
	private void handleEventAddHoaDon() {
		if(!hasInitHoaDon) {
			showMessage("B???n ch??a kh???i t???o h??a ????n");
			return;
		}

		if(!isValidMaKhachHang) {
			validationMaKhachHang();

			if(!isValidMaKhachHang) {
				return;
			}
		}

		if(!hasDataChiTietHoaDon) {
			showMessage("B???n ch??a nh???p d??? li???u ??? chi ti???t h??a ????n");
			return;
		}

		billBLL.insertBill(hoaDonCache);
		billDetailsBLL.insertAllBillDetails(mangChiTietHoaDon);
		showMessage("Th??m h??a ????n th??nh c??ng");
		handleEventRefreshHoaDon();
	}

	private void handleEventRefreshHoaDon() {
		mangChiTietHoaDon = new ArrayList<BillDetailsDTO>();	
		hasDataChiTietHoaDon = false;

		refreshFormHoaDon();
		refreshFormChiTietHoaDon();
		refreshChiTietHoaDonTable();
	}

	private void refreshFormHoaDon() {
		hoaDonCache = new HoaDonDTO();		
		hasInitHoaDon = false;

		activeEnableInput(maHoaDonInput, maHoaDonValidation);
		activeDisableInput(ngayNhapThongTinInput, ngayNhapThongTinValidation);
		activeDisableInput(tongTienInput, tongTienValidation);
		activeDisableInput(nhanVienInput, nhanVienValidation);
		activeDisableInput(khachHangInput, khachHangValidation);
		khachHangInput.setToolTipText("");
		checkBoxKhachHang.setEnabled(false);
		chooseMaKhachHang.setEnabled(false);
	}

	private boolean validationMaHoaDon() {
		String maHoaDon = maHoaDonInput.getText().trim();

		if(maHoaDon.length() == 0) {
			activeValidationInput(maHoaDonInput, maHoaDonValidation, "B???n ch??a nh???p d??? li???u");
			return false;
		}

		if(maHoaDon.length() < 3 || maHoaDon.length() > 6) {
			activeValidationInput(maHoaDonInput, maHoaDonValidation, "H??a ????n ch??? t??? 3 - 6 k?? t???");
			return false;
		}

		if(billBLL.kiemTraMaHoaDon(maHoaDon)) {
			activeValidationInput(maHoaDonInput, maHoaDonValidation, "M?? h??a ????n ???? t???n t???i");
			return false;
		}	

		activeSuccessInput(maHoaDonInput, maHoaDonValidation);
		hoaDonCache.setMaHoaDon(maHoaDon);
		return true;
	}

	private void handleCheckBoxMaKhachHang() {
		if(checkBoxKhachHang.isSelected()) {
			activeEnableInput(khachHangInput, khachHangValidation);
			chooseMaKhachHang.setEnabled(true);
			isValidMaKhachHang = false;
			khachHangInput.setText("");
		}	
		else {
			khachHangInput.setText("Kh??ng c??");
			validationMaKhachHang();
			isValidMaKhachHang = true;
		}
	}

	private boolean validationMaKhachHang() {
		String khachHang = khachHangInput.getText().trim();

		if(khachHang.toLowerCase().equals("kh??ng c??")) {
			hoaDonCache.setMaKhachHang("NULL");
		}
		else {
			CustomerDTO dto = customerBLL.getOneCustomer(khachHang);
			if(dto == null) {
				activeValidationInput(khachHangInput, khachHangValidation, "M?? kh??ch h??ng kh??ng t???n t???i");
				return false;
			}

			khachHangInput.setText(dto.getHo() + " " + dto.getTen());
			khachHangInput.setToolTipText("M?? kh??ch h??ng: " + dto.getMaKhachHang());
			hoaDonCache.setMaKhachHang(dto.getMaKhachHang());
		}


		activeSuccessInput(khachHangInput, khachHangValidation);
		chooseMaKhachHang.setEnabled(false);
		isValidMaKhachHang = true;
		return true;
	}

	private void createWindowChooseMaKhachHang() {
		JPanel framePanel = new JPanel(null);
		JPanel searchPanel = new JPanel(null);
		JScrollPane scrollSearchTable = new JScrollPane();
		JButton submitButton = new JButton("Chon");
		JButton searchButton = new JButton("Search"); // nut tim kiem du lieu
												  //
		frameChooseMaKhachHang.setSize(800, 650);
		frameChooseMaKhachHang.setLayout(null);
		frameChooseMaKhachHang.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		frameChooseMaKhachHang.setLocationRelativeTo(null);
		frameChooseMaKhachHang.setResizable(false);
		frameChooseMaKhachHang.setVisible(false);

		framePanel.setSize(frameChooseMaKhachHang.getSize());
		framePanel.setLocation(0, 0);
		framePanel.setBackground(Color.WHITE);
		frameChooseMaKhachHang.getContentPane().add(framePanel);

		FlexBox flexBoxForPanel = new FlexBox(framePanel, FlexBox.DIRECTION_COLUMN);	
		flexBoxForPanel.setPadding(10);

		searchPanel.setSize(500, 40);
		searchPanel.setBackground(Color.WHITE);
		flexBoxForPanel.setPosition(FlexBox.ALIGMENT_CENTER, 0, searchPanel);
		FlexBox flexBoxForSearchPanel = new FlexBox(searchPanel, FlexBox.DIRECTION_ROW);
		setPropertyForInputField(searchMaKhachHangInput);
		flexBoxForSearchPanel.setPositionWithinPercentSize(75, 0, searchMaKhachHangInput);
		setPropertyForButton(searchButton);
		flexBoxForSearchPanel.setPositionWithinPercentSize(100, 10, searchButton);

		String[] columnsName = {"M?? kh??ch h??ng", "H???", "T??n", "H???ng"};
		int[] sizePerColumn = {15, 25, 30, 30};
		scrollSearchTable.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		flexBoxForPanel.setPositionWithinPercentSize(75, 10, scrollSearchTable);
		tableFrameMaKhachHang = new AppTable(columnsName, sizePerColumn, scrollSearchTable.getSize());
		scrollSearchTable.getViewport().add(tableFrameMaKhachHang);

		DefaultTableModel tableFrameMaKhachHangModel = (DefaultTableModel)tableFrameMaKhachHang.getModel();
		setPropertyForButton(submitButton);
		flexBoxForPanel.setPosition(FlexBox.ALIGMENT_CENTER, 15, submitButton);

		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tableFrameMaKhachHang.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(frameChooseMaKhachHang, "B???n ch??a ch???n kh??ch h??ng, vui l??ng nh???n chu???t v??o 1 d??ng d??? li???u ??? b???ng ????? ch???n kh??ch h??ng");
					return;
				}

				String maKhachHang = tableFrameMaKhachHangModel.getValueAt(tableFrameMaKhachHang.getSelectedRow(), 0).toString();
				hoaDonCache.setMaKhachHang(maKhachHang);	
				khachHangInput.setText(maKhachHang);
				validationMaKhachHang();
				displayRegistry.unSubFrameRegistry();
			}
		});

		searchButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String textInput = searchMaKhachHangInput.getText().trim();
				
				if(textInput.length() == 0) {
					return;
				}
				
				
				ArrayList<CustomerDTO> result = customerBLL.searchCustomerTheoMaVaTenDungOR(textInput);

				if(result.size() == 0) {
					JOptionPane.showMessageDialog(frameChooseMaKhachHang, "Kh??ng t??m th???y d??? li???u");
					return;
				}

				tableFrameMaKhachHangModel.setRowCount(0);
				for(CustomerDTO customer : result) {
					tableFrameMaKhachHangModel.addRow(customer.toArrayString());
				}
			}
		});
	}

	private void refreshFrameChooseMaKhachHang() {
		searchMaKhachHangInput.setText("");

		DefaultTableModel tableFrameMaKhachHangModel = (DefaultTableModel)tableFrameMaKhachHang.getModel();
		for(CustomerDTO customer : customerBLL.getAllCustomer()) {
			tableFrameMaKhachHangModel.addRow(customer.toArrayString());
		}
	}
	// ================================ form chi tiet hoa don ========================================================================
	private void initComponentInBillDetailForm() {
		FlexBox flexBoxForPanel = new FlexBox(chiTietHoaDonFormPanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForPanel.setPadding(10);

		JLabel titleLabelTalbe = new JLabel("Th??ng tin chi ti???t h??a ????n");
		setPropertyForTitleLabel(titleLabelTalbe);
		flexBoxForPanel.setPosition(titleLabelTalbe, 0, 0);

		JPanel inputPanel = new JPanel(null);
		inputPanel.setBackground(hoaDonFormPanel.getBackground());
		flexBoxForPanel.setPositionWithinPercentSize(70, 10, inputPanel);

		FlexBox flexBoxForInput = new FlexBox(inputPanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForInput.setPadding(10);

		JLabel labelmaHoaDon = new JLabel("M?? h??a ????n");
		setPropertyForLabel(labelmaHoaDon);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelmaHoaDon);
		setPropertyForInputField(maChiTietHoaDonInput);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, maChiTietHoaDonInput);
		setPropertyForValidation(maChiTietHoaDonValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, maChiTietHoaDonValidation);

		JLabel labelMaSanPham = new JLabel("M?? S???n ph???m");
		setPropertyForLabel(labelMaSanPham);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelMaSanPham);
		JPanel maSanPhamPanel = new JPanel(null);
		maSanPhamPanel.setBackground(chiTietHoaDonFormPanel.getBackground());
		maSanPhamPanel.setSize(0, 35);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, maSanPhamPanel);
		FlexBox flexBoxForMaSanPham = new FlexBox(maSanPhamPanel, FlexBox.DIRECTION_ROW);
		setPropertyForInputField(maSanPhamInput);
		flexBoxForMaSanPham.setPositionWithinPercentSize(90, 0, maSanPhamInput);
		chooseMaSanPham.setCursor(new Cursor(Cursor.HAND_CURSOR));
		chooseMaSanPham.setBackground(chiTietHoaDonFormPanel.getBackground());
		flexBoxForMaSanPham.setPositionWithinPercentSize(100, 5, chooseMaSanPham);
		setPropertyForValidation(maSanPhamValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, maSanPhamValidation);

		JLabel labelMaKhuyenMai = new JLabel("M?? khuy???n m??i");
		setPropertyForLabel(labelMaKhuyenMai);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelMaKhuyenMai);
		JPanel maKhuyenMaiPanel = new JPanel(null);
		maKhuyenMaiPanel.setBackground(chiTietHoaDonFormPanel.getBackground());
		maKhuyenMaiPanel.setSize(0, 35);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, maKhuyenMaiPanel);
		FlexBox flexBoxForMaKhuyenMai = new FlexBox(maKhuyenMaiPanel, FlexBox.DIRECTION_ROW);
		setPropertyForCheckBox(checkBoxMaKhuyenMai);
		flexBoxForMaKhuyenMai.setPositionWithinPercentSize(20, 0, checkBoxMaKhuyenMai);
		setPropertyForInputField(maKhuyenMaiInput);
		flexBoxForMaKhuyenMai.setPositionWithinPercentSize(70, 5, maKhuyenMaiInput);
		chooseMaKhuyenMai.setCursor(new Cursor(Cursor.HAND_CURSOR));
		chooseMaKhuyenMai.setBackground(chiTietHoaDonFormPanel.getBackground());
		flexBoxForMaKhuyenMai.setPositionWithinPercentSize(100, 5, chooseMaKhuyenMai);
		setPropertyForValidation(maKhuyenMaiValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, maKhuyenMaiValidation);

		JLabel labelSoLuong = new JLabel("s??? l?????ng");
		setPropertyForLabel(labelSoLuong);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelSoLuong);
		setPropertyForInputField(soLuongInput);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, soLuongInput);
		setPropertyForValidation(soLuongValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, soLuongValidation);

		JLabel labelDonGia = new JLabel("????n gi??");
		setPropertyForLabel(labelDonGia);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelDonGia);
		setPropertyForInputField(donGiaInput);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, donGiaInput);
		setPropertyForValidation(donGiaValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, donGiaValidation);

		JLabel labelThanhTien = new JLabel("Th??nh ti???n");
		setPropertyForLabel(labelThanhTien);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelThanhTien);
		setPropertyForInputField(thanhTienInput);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, thanhTienInput);
		setPropertyForValidation(thanhTienValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, thanhTienValidation);

		JPanel buttonPanel = new JPanel(null);
		buttonPanel.setBackground(chiTietHoaDonFormPanel.getBackground());
		flexBoxForPanel.setPositionWithinPercentSize(100, 10, buttonPanel);

		FlexBox flexBoxForButton = new FlexBox(buttonPanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForButton.setPadding(10);
		setPropertyForButton(initChiTietHoaDonButton);
		flexBoxForButton.setPosition(initChiTietHoaDonButton, 0, 0);
		setPropertyForButton(addChiTietHoaDonButton);
		flexBoxForButton.setPosition(addChiTietHoaDonButton, 15, 0);

		refreshFormChiTietHoaDon();

		createWindowChooseMaSanPham();
		createWindowChooseMaKhuyenMai();

		AbstractDocument document = (AbstractDocument)maSanPhamInput.getDocument();
		document.setDocumentFilter(new UppercaseDocumentFilter());
		AbstractDocument documentSoLuong = (AbstractDocument)soLuongInput.getDocument();
		documentSoLuong.setDocumentFilter(new NumberDocumentFilter());
	}

	private void addEventToChiTietHoaDonForm() {
		initChiTietHoaDonButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleEventInitChiTietHoaDon();
			}
		});

		addChiTietHoaDonButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleEventAddChiTietHoaDon();
			}
		});

		chooseMaSanPham.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				displayRegistry.subFrameRegistry(frameChooseMaSanPham);
				refreshFrameChooseMaSanPham();
			}
		});

		chooseMaKhuyenMai.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				displayRegistry.subFrameRegistry(frameChooseMaKhuyenMai);
				refreshFrameChooseMaKhuyenMai();
			}
		});

		checkBoxMaKhuyenMai.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleCheckBoxMaKhuyenMai();
			}
		});

		maSanPhamInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					validationMaSanPham();

					if(isValidMaSanPham) {
						xuLyEventSauKhiThemSanPhamThanhCong();
					}
				}
			}
		});

		soLuongInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					validationSoLuong();
				}
			}
		});
	}

	private void handleEventInitChiTietHoaDon() {
		if(!hasInitHoaDon) {
			showMessage("B???n ch??a kh???i t???o h??a ????n");
			return;
		}

		refreshFormChiTietHoaDon();

		chiTietHoaDonCache = new BillDetailsDTO();
		chiTietHoaDonCache.setMaHoaDon(hoaDonCache.getMaHoaDon());
		chiTietHoaDonCache.setSoLuong(0);

		activeSuccessInput(maChiTietHoaDonInput, maChiTietHoaDonValidation);
		maChiTietHoaDonInput.setText(chiTietHoaDonCache.getMaHoaDon());
		chooseMaSanPham.setEnabled(true);
		activeEnableInput(maSanPhamInput, maSanPhamValidation);
		hasInitChiTietHoaDon = true;
		hasModifyChiTietHoaDon = false;
		chiTietHoaDonCacheModify = null;
	}

	private void handleEventAddChiTietHoaDon() {
		if(!hasInitChiTietHoaDon) {
			showMessage("B???n ch??a kh???i t???o chi ti???t h??a ????n");
			return;
		}


		boolean isError = false;
		if(!isValidMaSanPham) {
			validationMaSanPham();	
			isError = (isValidMaSanPham)? false : true;

			if(!isError) {
				xuLyEventSauKhiThemSanPhamThanhCong();
			}
		}

		if(!isValidSoLuong) {
			validationSoLuong();
			isError = (isValidSoLuong)? false : true;
		}

		if(isError) {
			return;
		}

		hasDataChiTietHoaDon = true;
		hasInitChiTietHoaDon = false;
		mangChiTietHoaDon.add(chiTietHoaDonCache);
		System.out.println(chiTietHoaDonCache.getThanhTien());
		hoaDonCache.setTongTien(hoaDonCache.getTongTien() + chiTietHoaDonCache.getThanhTien());
		tongTienInput.setText(hoaDonCache.getTongTien());
		showMessage("Th??m chi ti???t h??a ????n th??nh c??ng");
		refreshChiTietHoaDonTable();
		refreshFormChiTietHoaDon();
	}

	private void handleEventDeleteChiTietKhuyenMai() {
		if(!hasDataChiTietHoaDon) {
			showMessage("Ch??a c?? d??? li???u chi ti???t h??a ????n, vui l??ng th??m d??? li???u");
			return;
		}
		if(!hasModifyChiTietHoaDon) {
			showMessage("Vui l??ng ch???n 1 d??ng d??? li???u b??n b???ng d??? li???u ????? c?? th??? ch???nh s???a th??ng tin");
			return;
		}

		mangChiTietHoaDon.remove(indexSelectionRow);
		hasModifyChiTietHoaDon = false;
		indexSelectionRow = -1;
		hoaDonCache.setTongTien(hoaDonCache.getTongTien() - chiTietHoaDonCacheModify.getThanhTien());
		System.out.println(hoaDonCache.getTongTien());
		if(hoaDonCache.getTongTien() == 0) {
			tongTienInput.setText("");
		}
		else {
			tongTienInput.setText(hoaDonCache.getTongTien());
		}
		showMessage("X??a th??ng tin chi ti???t h??a ????n s??? " + chiTietHoaDonCache.getMaHoaDon() + " Th??nh c??ng");
		refreshFormChiTietHoaDon();
		refreshChiTietHoaDonTable();
	}

	private void handleCheckBoxMaKhuyenMai() {
		if(checkBoxMaKhuyenMai.isSelected()) {
			activeEnableInput(maKhuyenMaiInput, maKhuyenMaiValidation);
			maKhuyenMaiInput.setEditable(false);
			chooseMaKhuyenMai.setEnabled(true);
			isValidMaKhuyenMai = false;
			maKhuyenMaiInput.setText("");
		}	
		else {
			maKhuyenMaiInput.setText("Kh??ng c??");
			validationMaKhuyenMai();
			isValidMaKhuyenMai = true;
		}
	}

	private void chuyenDuLieuBangSangForm(BillDetailsDTO data) {
		chiTietHoaDonCacheModify = data;	
		chiTietHoaDonCache = chiTietHoaDonCacheModify.clone();

		hasModifyChiTietHoaDon = true;
		hasInitChiTietHoaDon = false;
		
		activeSuccessInput(maChiTietHoaDonInput, maChiTietHoaDonValidation);
		maChiTietHoaDonInput.setText(chiTietHoaDonCacheModify.getMaHoaDon());
		chooseMaSanPham.setEnabled(true);
		activeEnableInput(maSanPhamInput, maSanPhamValidation);
		maSanPhamInput.setText(chiTietHoaDonCacheModify.getMaSanPham());
		maSanPhamInput.setToolTipText("");

		activeSuccessInput(maKhuyenMaiInput, maKhuyenMaiValidation);
		maKhuyenMaiInput.setEditable(false);
		maKhuyenMaiInput.setToolTipText("");
		if(chiTietHoaDonCacheModify.getMaKhuyenMai().equals("NULL")) {
			checkBoxMaKhuyenMai.setSelected(false);
			maKhuyenMaiInput.setText("Kh??ng c??");
		}
		else {
			checkBoxMaKhuyenMai.setSelected(true);
			maKhuyenMaiInput.setText(chiTietHoaDonCacheModify.getMaKhuyenMai());
		}

		ArrayList<KhuyenMaiDTO> result = khuyenMaiBLL.timKiemKhuyenMaiTheoMaSanPham(chiTietHoaDonCache.getMaSanPham(), hoaDonCache.getNgayNhapThongTin());
		if(khuyenMaiBLL.timKiemKhuyenMaiTheoMaSanPham(chiTietHoaDonCache.getMaSanPham(), hoaDonCache.getNgayNhapThongTin()).size() != 0) {
			checkBoxMaKhuyenMai.setEnabled(true);
			chooseMaKhuyenMai.setEnabled(true);
		}
		else {
			checkBoxMaKhuyenMai.setEnabled(false);
			chooseMaKhuyenMai.setEnabled(false);
		}

		activeEnableInput(soLuongInput, soLuongValidation);
		soLuongInput.setText(Integer.toString(chiTietHoaDonCacheModify.getSoLuong()));
		activeSuccessInput(donGiaInput, donGiaValidation);
		donGiaInput.setText(chiTietHoaDonCacheModify.getDonGia());
		activeSuccessInput(thanhTienInput, thanhTienValidation);
		thanhTienInput.setText(chiTietHoaDonCacheModify.getThanhTien());
	}

	private void handleEventModifyChiTietHoaDon() {
		if(!hasDataChiTietHoaDon) {
			showMessage("Ch??a c?? d??? li???u chi ti???t h??a ????n, vui l??ng th??m d??? li???u");
			return;
		}
		if(!hasModifyChiTietHoaDon) {
			showMessage("Vui l??ng ch???n 1 d??ng d??? li???u b??n b???ng d??? li???u ????? c?? th??? ch???nh s???a th??ng tin");
			return;
		}

		if(chiTietHoaDonCacheModify.equals(chiTietHoaDonCache)) {
			showMessage("B???n ch??a ch???nh s???a th??ng tin d??? li???u");
			return;
		}

		boolean isError = false;

		String soLuongCache = soLuongInput.getText();
		if(!isValidMaSanPham) {
			validationMaSanPham();	
			isError = (isValidMaSanPham)? false : true;
		}

		if(!isError) {
			soLuongInput.setText(soLuongCache);
		}

		if(!isValidSoLuong) {
			validationSoLuong();
			isError = (isValidSoLuong)? false : true;
		}

		if(isError) {
			return;
		}

		mangChiTietHoaDon.set(indexSelectionRow, chiTietHoaDonCache);
		hasModifyChiTietHoaDon = false;
		indexSelectionRow = -1;
		hoaDonCache.setTongTien((hoaDonCache.getTongTien() - chiTietHoaDonCacheModify.getThanhTien()) + chiTietHoaDonCache.getThanhTien());
		tongTienInput.setText(hoaDonCache.getTongTien());
		showMessage("Ch???nh s???a th??ng tin chi ti???t h??a ????n s??? " + chiTietHoaDonCache.getMaHoaDon() + " Th??nh c??ng");
		refreshFormChiTietHoaDon();
		refreshChiTietHoaDonTable();
	}


	private void refreshFormChiTietHoaDon() {
		activeDisableInput(maChiTietHoaDonInput, maChiTietHoaDonValidation);
		activeDisableInput(maSanPhamInput, maSanPhamValidation);
		activeDisableInput(maKhuyenMaiInput, maKhuyenMaiValidation);
		activeDisableInput(soLuongInput, soLuongValidation);
		activeDisableInput(donGiaInput, donGiaValidation);
		activeDisableInput(thanhTienInput, thanhTienValidation);
		hasInitChiTietHoaDon = false;
		maSanPhamInput.setToolTipText("");
		chooseMaSanPham.setEnabled(false);
		maKhuyenMaiInput.setToolTipText("");
		chooseMaKhuyenMai.setEnabled(false);
		checkBoxMaKhuyenMai.setEnabled(false);

		isValidMaSanPham = false;
		isValidMaKhuyenMai = false;
		isValidSoLuong = false;
	}

	private void validationMaSanPham() {
		String maSanPham = maSanPhamInput.getText().trim();
	
		if(maSanPham.length() == 0) {
			activeValidationInput(maSanPhamInput, maSanPhamValidation, "B???n ch??a nh???p d??? li???u");
			return ;
		}
	
		ProductDTO product = productBLL.soSanhMaSanPham(maSanPham);
		if(product == null) {
			activeValidationInput(maSanPhamInput, maSanPhamValidation, "M?? s???n ph???m b???n nh???p kh??ng c?? s???n");
			return ;
		}

		if(product.getSoLuong() < 1) {
			activeValidationInput(maSanPhamInput, maSanPhamValidation, "S???n ph???m ???? h???t h??ng");
			return;
		}
	
		activeSuccessInput(maSanPhamInput, maSanPhamValidation);
		maSanPhamInput.setText(product.getTenSanPham());
		maSanPhamInput.setToolTipText("M?? s???n ph???m: " + product.getMaSanPham());
		chiTietHoaDonCache.setMaSanPham(product.getMaSanPham());
		chiTietHoaDonCache.setDonGia(product.getDonGiaSanPham());
		isValidMaSanPham = true;
		chooseMaSanPham.setEnabled(false);
	}

	private void xuLyEventSauKhiThemSanPhamThanhCong() {
		activeSuccessInput(donGiaInput, donGiaValidation);
		donGiaInput.setText(chiTietHoaDonCache.getDonGia());
		activeSuccessInput(thanhTienInput, thanhTienValidation);
		activeEnableInput(soLuongInput, soLuongValidation);
		
		if(hasModifyChiTietHoaDon) {
			soLuongInput.setText(Integer.toString(chiTietHoaDonCacheModify.getSoLuong()));
		}

		thanhTienInput.setText(20);
		thanhTienInput.setText("");

		// ki???m tra xem co m?? khuy???n m??i c???a san ph???m kh??ng
		if(khuyenMaiBLL.timKiemKhuyenMaiTheoMaSanPham(chiTietHoaDonCache.getMaSanPham(), hoaDonCache.getNgayNhapThongTin()).size() != 0) {
			checkBoxMaKhuyenMai.setEnabled(true);
			chooseMaKhuyenMai.setEnabled(true);
		}
		else {
			checkBoxMaKhuyenMai.setSelected(false);
		}

		handleCheckBoxMaKhuyenMai();
	}


	private void validationSoLuong() {
		String soLuongText = soLuongInput.getText().trim();

		if(soLuongText.length() == 0) {
			activeValidationInput(soLuongInput, soLuongValidation, "B???n ch??a nh???p d??? li???u");		
			return;
		}

		int soLuong = Integer.parseInt(soLuongText);	
		int maxSoLuongSanPham = productBLL.soSanhMaSanPham(chiTietHoaDonCache.getMaSanPham()).getSoLuong();
		
		// tru di cac so luong cua san pham da nhap trong chi tiet hoa don
		for(BillDetailsDTO dto : mangChiTietHoaDon) {
			if(dto.getMaSanPham().equals(chiTietHoaDonCache.getMaSanPham())) {
				maxSoLuongSanPham -= dto.getSoLuong();	
			}
		}

		if(!hasModifyChiTietHoaDon && maxSoLuongSanPham < 1) {
			activeValidationInput(soLuongInput, soLuongValidation, "S???n ph???m ???? h???t h??ng");
			return;
		}

		if(hasModifyChiTietHoaDon) {
			maxSoLuongSanPham += mangChiTietHoaDon.get(indexSelectionRow).getSoLuong();
		}

		System.out.println(maxSoLuongSanPham);

		if(soLuong < 1 || soLuong > maxSoLuongSanPham) {
			activeValidationInput(soLuongInput, soLuongValidation, "S??? l?????ng ch??? t??? 1 ?????n " + maxSoLuongSanPham);
			return;
		}

		activeSuccessInput(soLuongInput, soLuongValidation);
		chiTietHoaDonCache.setSoLuong(soLuong);
		chiTietHoaDonCache.setThanhTien(chiTietHoaDonCache.getSoLuong() * chiTietHoaDonCache.getDonGia());
		thanhTienInput.setText(chiTietHoaDonCache.getThanhTien());
		isValidSoLuong = true;
	}

	private void validationMaKhuyenMai() {
		String khuyenMai = maKhuyenMaiInput.getText().trim();

		int donGiaGoc = productBLL.soSanhMaSanPham(chiTietHoaDonCache.getMaSanPham()).getDonGiaSanPham();
		if(khuyenMai.equals("Kh??ng c??")) {
			chiTietHoaDonCache.setMaKhuyenMai("NULL");
			chiTietHoaDonCache.setDonGia(donGiaGoc);
		}
		else {
			KhuyenMaiDTO dto = khuyenMaiBLL.getOneKhuyenMai(khuyenMai);
			maKhuyenMaiInput.setText(dto.getTenKhuyenMai());
			maKhuyenMaiInput.setToolTipText("M?? khuy???n m??i: " + dto.getMaKhuyenMai());
			chiTietHoaDonCache.setMaKhuyenMai(dto.getMaKhuyenMai());

			int tiLe = chiTietKMBLL.kiemTraMaSanPhamTrongKhuyenMai(dto.getMaKhuyenMai(), chiTietHoaDonCache.getMaSanPham()).getPhanTramKhuyenMai();
			chiTietHoaDonCache.setDonGia(donGiaGoc - Util.calculatePrecent(tiLe, donGiaGoc));
		}

		donGiaInput.setText(chiTietHoaDonCache.getDonGia());
		if(chiTietHoaDonCache.getSoLuong() != 0) {
			chiTietHoaDonCache.setThanhTien(chiTietHoaDonCache.getSoLuong() * chiTietHoaDonCache.getDonGia());
			thanhTienInput.setText(chiTietHoaDonCache.getThanhTien());
		}

		activeSuccessInput(maKhuyenMaiInput, maKhuyenMaiValidation);
		chooseMaKhuyenMai.setEnabled(false);
	}
	
	private void createWindowChooseMaSanPham() {
		JPanel framePanel = new JPanel(null);
		JPanel searchPanel = new JPanel(null);
		JScrollPane scrollSearchTable = new JScrollPane();
		JButton submitButton = new JButton("Chon");
		JButton searchButton = new JButton("Search"); // nut tim kiem du lieu
												  //
		frameChooseMaSanPham.setSize(800, 650);
		frameChooseMaSanPham.setLayout(null);
		frameChooseMaSanPham.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		frameChooseMaSanPham.setLocationRelativeTo(null);
		frameChooseMaSanPham.setResizable(false);
		frameChooseMaSanPham.setVisible(false);

		framePanel.setSize(frameChooseMaSanPham.getSize());
		framePanel.setLocation(0, 0);
		framePanel.setBackground(Color.WHITE);
		frameChooseMaSanPham.getContentPane().add(framePanel);

		FlexBox flexBoxForPanel = new FlexBox(framePanel, FlexBox.DIRECTION_COLUMN);	
		flexBoxForPanel.setPadding(10);

		searchPanel.setSize(500, 40);
		searchPanel.setBackground(Color.WHITE);
		flexBoxForPanel.setPosition(FlexBox.ALIGMENT_CENTER, 0, searchPanel);
		FlexBox flexBoxForSearchPanel = new FlexBox(searchPanel, FlexBox.DIRECTION_ROW);
		setPropertyForInputField(searchMaSanPhamInput);
		flexBoxForSearchPanel.setPositionWithinPercentSize(75, 0, searchMaSanPhamInput);
		setPropertyForButton(searchButton);
		flexBoxForSearchPanel.setPositionWithinPercentSize(100, 10, searchButton);

		String[] columnsName = {"M?? s???n ph???m", "T??n s???n ph???m", "S??? l?????ng", "Gi??"};
		int[] sizePerColumn = {15, 30, 25, 30};
		scrollSearchTable.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		flexBoxForPanel.setPositionWithinPercentSize(75, 10, scrollSearchTable);
		tableFrameMaSanPham = new AppTable(columnsName, sizePerColumn, scrollSearchTable.getSize());
		scrollSearchTable.getViewport().add(tableFrameMaSanPham);

		DefaultTableModel tableFrameMaSanPhamModel = (DefaultTableModel)tableFrameMaSanPham.getModel();
		setPropertyForButton(submitButton);
		flexBoxForPanel.setPosition(FlexBox.ALIGMENT_CENTER, 15, submitButton);

		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tableFrameMaSanPham.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(frameChooseMaSanPham, "B???n ch??a ch???n s???n ph???m, vui l??ng nh???n chu???t v??o 1 d??ng d??? li???u ??? b???ng ????? ch???n s???n ph???m");
					return;
				}

				String maSanPham = tableFrameMaSanPhamModel.getValueAt(tableFrameMaSanPham.getSelectedRow(), 0).toString();
				maSanPhamInput.setText(maSanPham);
				validationMaSanPham();
				xuLyEventSauKhiThemSanPhamThanhCong();
				displayRegistry.unSubFrameRegistry();
			}
		});

		searchButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String textInput = searchMaSanPhamInput.getText();
				
				if(textInput.length() == 0) {
					return;
				}
				
				
				ArrayList<ProductDTO> result = productBLL.timKiemVoiMaSanPham(searchMaSanPhamInput.getText());

				if(result.size() == 0) {
					result = productBLL.timKiemVoiTenSanPham(searchMaSanPhamInput.getText());
				}

				if(result.size() == 0) {
					JOptionPane.showMessageDialog(frameChooseMaSanPham, "Kh??ng t??m th???y d??? li???u");
					return;
				}

				tableFrameMaSanPhamModel.setRowCount(0);
				for(ProductDTO product : result) {
					tableFrameMaSanPhamModel.addRow(product.toArrayString());
				}
			}
		});
	}

	private void refreshFrameChooseMaSanPham() {
		searchMaSanPhamInput.setText("");

		DefaultTableModel tableFrameMaSanPhamModel = (DefaultTableModel)tableFrameMaSanPham.getModel();
		tableFrameMaSanPhamModel.setRowCount(0);
		for(ProductDTO product : ProductBLL.getInstance().getAllProductHasQuantity()) {
			tableFrameMaSanPhamModel.addRow(product.toArrayString());
		}
	}
	
	private void createWindowChooseMaKhuyenMai() {
		JPanel framePanel = new JPanel(null);
		JScrollPane scrollSearchTable = new JScrollPane();
		JButton submitButton = new JButton("Chon");
												  //
		frameChooseMaKhuyenMai.setSize(800, 650);
		frameChooseMaKhuyenMai.setLayout(null);
		frameChooseMaKhuyenMai.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		frameChooseMaKhuyenMai.setLocationRelativeTo(null);
		frameChooseMaKhuyenMai.setResizable(false);
		frameChooseMaKhuyenMai.setVisible(false);

		framePanel.setSize(frameChooseMaKhuyenMai.getSize());
		framePanel.setLocation(0, 0);
		framePanel.setBackground(Color.WHITE);
		frameChooseMaKhuyenMai.getContentPane().add(framePanel);

		FlexBox flexBoxForPanel = new FlexBox(framePanel, FlexBox.DIRECTION_COLUMN);	
		flexBoxForPanel.setPadding(10);

		String[] columnsName = {"M?? khuy???n m??i", "T??n khuy???n m??i", "Ng??y b???t ?????u", "Ng??y k???t th??c", "%"};
		int[] sizePerColumn = {20, 30, 20, 20, 10};
		scrollSearchTable.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		flexBoxForPanel.setPositionWithinPercentSize(80, 10, scrollSearchTable);
		tableFrameMaKhuyenMai = new AppTable(columnsName, sizePerColumn, scrollSearchTable.getSize());
		scrollSearchTable.getViewport().add(tableFrameMaKhuyenMai);

		DefaultTableModel tableFrameMaKhuyenMaiModel = (DefaultTableModel)tableFrameMaKhuyenMai.getModel();
		setPropertyForButton(submitButton);
		flexBoxForPanel.setPosition(FlexBox.ALIGMENT_CENTER, 15, submitButton);

		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tableFrameMaKhuyenMai.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(frameChooseMaKhuyenMai, "B???n ch??a ch???n s???n ph???m, vui l??ng nh???n chu???t v??o 1 d??ng d??? li???u ??? b???ng ????? ch???n s???n ph???m");
					return;
				}

				String maKhuyenMai = tableFrameMaKhuyenMaiModel.getValueAt(tableFrameMaKhuyenMai.getSelectedRow(), 0).toString();
				maKhuyenMaiInput.setText(maKhuyenMai);
				validationMaKhuyenMai();
				displayRegistry.unSubFrameRegistry();
			}
		});
	}

	private void refreshFrameChooseMaKhuyenMai() {
		DefaultTableModel tableFrameMaKhuyenMaiModel = (DefaultTableModel)tableFrameMaKhuyenMai.getModel();
		tableFrameMaKhuyenMaiModel.setRowCount(0);
		ArrayList<KhuyenMaiDTO> resultSearch = khuyenMaiBLL.timKiemKhuyenMaiTheoMaSanPham(chiTietHoaDonCache.getMaSanPham(), hoaDonCache.getNgayNhapThongTin());

		for(int i = 0; i < resultSearch.size(); ++i) {
			KhuyenMaiDTO khuyenMai = resultSearch.get(i);
			int tiLe = chiTietKMBLL.kiemTraMaSanPhamTrongKhuyenMai(khuyenMai.getMaKhuyenMai(), chiTietHoaDonCache.getMaSanPham()).getPhanTramKhuyenMai();
			tableFrameMaKhuyenMaiModel.addRow(khuyenMai.toArrayString());
			tableFrameMaKhuyenMaiModel.setValueAt(Integer.toString(tiLe), i, 4);
		}
	}
// ================================================ bang du lieu chi tiet hoa don ===============================

	private void initComponentInBillDetailTable() {
		FlexBox flexBoxForPanel = new FlexBox(chiTietHoaDonTablePanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForPanel.setPadding(10);

		JLabel titleLabelTalbe = new JLabel("Chi ti???t h??a ????n ???? th??m");
		setPropertyForTitleLabel(titleLabelTalbe);
		flexBoxForPanel.setPosition(titleLabelTalbe, 0, 0);

		JPanel tablePanel = new JPanel(null);
		tablePanel.setBackground(chiTietHoaDonTablePanel.getBackground());
		flexBoxForPanel.setPositionWithinPercentSize(70, 10, tablePanel);

		FlexBox flexBoxForTable = new FlexBox(tablePanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForTable.setPadding(10);

		JScrollPane scroll = new JScrollPane();
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		flexBoxForTable.setPositionWithinPercentSize(100, 0, scroll);	
		String[] columnNames = new String[] {"M?? h??a ????n",  "M?? s???n ph???m", "M?? KM", "S??? l?????ng", "????n gi??", "Th??nh ti???n"};
		int[] columnSize = new int[] {15, 20, 20, 15, 15, 15};
		tableChiTietHoaDon = new AppTable(columnNames, columnSize, scroll.getSize());
		scroll.getViewport().add(tableChiTietHoaDon);

		JPanel buttonPanel = new JPanel(null);
		buttonPanel.setBackground(chiTietHoaDonTablePanel.getBackground());
		flexBoxForPanel.setPositionWithinPercentSize(100, 10, buttonPanel);

		FlexBox flexBoxForButton = new FlexBox(buttonPanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForButton.setPadding(10);
		setPropertyForButton(deleteChiTietHoaDonButton);
		flexBoxForButton.setPosition(deleteChiTietHoaDonButton, 0, 0);
		setPropertyForButton(modifyChiTietHoaDonButton);
		flexBoxForButton.setPosition(modifyChiTietHoaDonButton, 15, 0);
	}

	private void addEventToChiTietHoaDonTable() {
		tableChiTietHoaDon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				handleEventClickTable();
			}
		});

		modifyChiTietHoaDonButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleEventModifyChiTietHoaDon();
			}
		});

		deleteChiTietHoaDonButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleEventDeleteChiTietKhuyenMai();
			}
		});
	}

	private void handleEventClickTable() {
		int indexSelection = tableChiTietHoaDon.getSelectedRow();
		if(indexSelection == indexSelectionRow) {
			return;
		}

		BillDetailsDTO cache = new BillDetailsDTO();
		DefaultTableModel tableModel = (DefaultTableModel)tableChiTietHoaDon.getModel();

		cache.setMaHoaDon(tableModel.getValueAt(indexSelection, 0).toString());
		cache.setMaSanPham(tableModel.getValueAt(indexSelection, 1).toString());
		String maKhuyenMai = tableModel.getValueAt(indexSelection, 2).toString();
		maKhuyenMai = (maKhuyenMai.equals("Kh??ng c??"))? "NULL" : maKhuyenMai;
		cache.setMaKhuyenMai(maKhuyenMai);
		cache.setSoLuong(Integer.parseInt(tableModel.getValueAt(indexSelection, 3).toString()));
		cache.setDonGia(Util.convertMoneyStringToInt(tableModel.getValueAt(indexSelection, 4).toString()));
		cache.setThanhTien(Util.convertMoneyStringToInt(tableModel.getValueAt(indexSelection, 5).toString()));
		chuyenDuLieuBangSangForm(cache);
		indexSelectionRow = indexSelection;
	}

	private void refreshChiTietHoaDonTable() {
		DefaultTableModel tableModel = (DefaultTableModel)tableChiTietHoaDon.getModel();

		tableModel.setRowCount(0);
		for(BillDetailsDTO cthd : mangChiTietHoaDon) {
			tableModel.addRow(cthd.toArray());
		}
	}

	// ========================================== xu ly do hoa cho cac component ========================================
	private void setPropertyForLabel(JLabel label) {
		label.setFont(defaultFont);
		label.setForeground(Color.BLACK);
		label.setSize(Util.getSizeOfString(label.getText(), label.getFont()));
	}

	private void setPropertyForValidation(JLabel label) {
		label.setFont(defaultFont);
		label.setSize(0, 15);
		label.setForeground(Constants.COLOR_MESSAGE_VALIDATION);
	}

	private void setPropertyForTitleLabel(JLabel labelTitle) {
		labelTitle.setFont(Util.getFont("Montserrat", "BOLD", 25));
		labelTitle.setSize(Util.getSizeOfString(labelTitle.getText(), labelTitle.getFont()));
		labelTitle.setForeground(Color.BLACK);
	}

	private void setPropertyForInputField(JTextField input) {
		input.setFont(defaultFont);
		input.setForeground(Constants.COLOR_TEXTFIELD_FOREGROUND);
		input.setSize(450, 35);
	}

	private void activeDisableInput(JTextField input, JLabel validation) {
		input.setBackground(new Color(192, 208, 242));
		input.setForeground(input.getBackground());
		input.setBorder(null);
		input.setText("");
		input.setEditable(false);
		validation.setVisible(false);
	}

	private void activeEnableInput(JTextField input, JLabel validation) {
		input.setText("");
		input.setBackground(Color.WHITE);
		input.setForeground(Constants.COLOR_TEXTFIELD_FOREGROUND);
		input.setEditable(true);
		input.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Constants.COLOR_TEXTFIELD_TYPE_BORDER), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		validation.setVisible(false);
	}

	private void activeSuccessInput(JTextField input, JLabel validation) {
		input.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Constants.COLOR_TEXTFIELD_SUCCESS_BORDER), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		input.setForeground(Constants.COLOR_TEXTFIELD_FOREGROUND);
		input.setBackground(Color.WHITE);
		input.setEditable(false);
		validation.setVisible(false);
	}

	private void activeValidationInput(JTextField input, JLabel validation, String text) {
		input.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Constants.COLOR_TEXTFIELD_DANGER_BORDER), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		input.setEditable(true);
		validation.setText(text);
		validation.setVisible(true);
	}

	private void setPropertyForButton(JButton button) {
		button.setFont(defaultFont);
		button.setForeground(Constants.COLOR_BACKGROUND_PRIMARY);
		button.setBackground(Constants.COLOR_PRIMARY);
		button.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Constants.COLOR_PRIMARY));
		button.setSize(280, 45);
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
