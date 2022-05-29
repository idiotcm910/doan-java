package GUI.phieunhapgui;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.text.AbstractDocument;
import javax.swing.ScrollPaneConstants;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.ArrayList;

import common.*;
import components.UppercaseDocumentFilter;
import components.AppTable;
import components.NumberDocumentFilter;
import components.TMoneyField;
import DTO.PhieuNhapDTO;
import DTO.ChiTietPhieuNhapDTO;
import DTO.ProductDTO;
import DTO.NhaCungCapDTO;
import BLL.NhaCungCapBLL;
import BLL.PhieuNhapBLL;
import BLL.ChiTietPhieuNhapBLL;
import BLL.ProductBLL;
import components.UserToken;


public class PhieuNhapFormPanel extends JPanel {
	private Font defaultFont;
	private FrameDisplayRegistry displayRegistry;
	private PhieuNhapBLL pnBLL;
	private ChiTietPhieuNhapBLL ctpnBLL;
	private ProductBLL productBLL;
	private NhaCungCapBLL nccBLL;
	private UserToken token;

	// form phieu nhap
	private JPanel phieuNhapFormPanel;		
	private JTextField maPhieuNhapInput;
	private JLabel maPhieuNhapValidation;
	private boolean isValidMaPhieuNhap;
	private JTextField maNhaCungCapInput;
	private JLabel maNhaCungCapValidation;
	private boolean isValidMaNhaCungCap;
	private JButton chooseMaNhaCungCap;
	// frame choose ma khuyen mai
	private JFrame frameChooseMaNhaCungCap;
	private AppTable tableFrameMaNhaCungCap;
	private JTextField searchMaNhaCungCapInput;

	private JTextField maNhanVienInput;
	private JLabel maNhanVienValidation;
	private TMoneyField tongTienInput;
	private JLabel tongTienValidation;
	private JTextField ngayNhapInput;
	private JLabel ngayNhapValidation;
	private boolean isValidNgayNhap;

	private JButton initPhieuNhapButton;
	private JButton addPhieuNhapButton;
	private JButton refreshPhieuNhapButton;

	PhieuNhapDTO phieuNhapCache;

	// form chi tiet phieu nhap
	JPanel chiTietPhieuNhapFormPanel;
	private JTextField maChiTietPhieuNhapInput;
	private JLabel maChiTietPhieuNhapValidation;
	private JTextField sanPhamInput;
	private JLabel sanPhamValidation;
	private boolean isValidMaSanPham;
	private JButton chooseMaSanPham;
	// frame choose ma khuyen mai
	private JFrame frameChooseMaSanPham;
	private AppTable tableFrameMaSanPham;
	private JTextField searchMaSanPhamInput;

	private JTextField soLuongInput;
	private JLabel soLuongValidation;
	private boolean isValidSoLuong;
	private TMoneyField donGiaInput;
	private JLabel donGiaValidation;
	private boolean isValidDonGia;
	private TMoneyField thanhTienInput;
	private JLabel thanhTienValidation;
	
	private JButton initChiTietPhieuNhapButton;
	private JButton addChiTietPhieuNhapButton;

	private ChiTietPhieuNhapDTO chiTietPhieuNhapCache;
	private ChiTietPhieuNhapDTO chiTietPhieuNhapModifyCache;
	private ArrayList<ChiTietPhieuNhapDTO> mangChiTietPhieuNhap;

	// bang du lieu chi tiet khuyen mai da nhap
	private JPanel chiTietPhieuNhapTablePanel;
	private JButton deleteChiTietPhieuNhapButton;
	private JButton modifyChiTietPhieuNhapButton;
	private AppTable tableChiTietPhieuNhap;
	private int indexSelectionRow;


	private boolean hasInitPhieuNhap;
	private boolean hasInitChiTietPhieuNhap;
	private boolean hasDataChiTietPhieuNhap;
	private boolean hasModifyChiTietPhieuNhap;
	public PhieuNhapFormPanel() {
		setLayout(null);
		initVariable();
	}

	private void initVariable() {
		defaultFont = Util.getFont("Roboto", "NORMAL", 18);	
		displayRegistry = FrameDisplayRegistry.getInstance();
		pnBLL = PhieuNhapBLL.getInstance();
		ctpnBLL = ChiTietPhieuNhapBLL.getInstance();
		productBLL = ProductBLL.getInstance();
		nccBLL = NhaCungCapBLL.getInstance();
		token = UserToken.getInstance();

		// form phieu nhap
		phieuNhapFormPanel = new JPanel(null);
		maPhieuNhapInput = new JTextField();
		maPhieuNhapValidation = new JLabel();
		isValidMaPhieuNhap = false;
		maNhaCungCapInput = new JTextField();
		maNhaCungCapValidation = new JLabel();
		isValidMaNhaCungCap = false;
		chooseMaNhaCungCap = new JButton("...");
		frameChooseMaNhaCungCap = new JFrame();
		searchMaNhaCungCapInput = new JTextField();
		maNhanVienInput = new JTextField();
		maNhanVienValidation = new JLabel();
		tongTienInput = new TMoneyField(8);
		tongTienValidation = new JLabel();	
		ngayNhapInput = new JTextField();
		ngayNhapValidation = new JLabel();
		isValidNgayNhap = false;
		initPhieuNhapButton = new JButton("Khởi tạo phiếu nhập");
		addPhieuNhapButton = new JButton("Thêm mới phiếu nhập");
		refreshPhieuNhapButton = new JButton("Làm mới phiếu nhập");

		// form chi tiet phieu nhap
		chiTietPhieuNhapFormPanel = new JPanel(null);
		maChiTietPhieuNhapInput = new JTextField();
		maChiTietPhieuNhapValidation = new JLabel();
		sanPhamInput = new JTextField();
		sanPhamValidation = new JLabel();
		isValidMaSanPham = false;
		chooseMaSanPham = new JButton("...");
		frameChooseMaSanPham = new JFrame();
		searchMaSanPhamInput = new JTextField();
		soLuongInput = new JTextField();
		soLuongValidation = new JLabel();
		isValidSoLuong = false;
		donGiaInput = new TMoneyField(8);
		donGiaValidation = new JLabel();
		thanhTienInput = new TMoneyField(8);
		thanhTienValidation = new JLabel();
		
		initChiTietPhieuNhapButton = new JButton("Khởi tạo chi tiết phiếu nhập");
		addChiTietPhieuNhapButton = new JButton("Thêm mới chi tiết phiếu nhập");		
		
		// bang du lieu
		chiTietPhieuNhapTablePanel = new JPanel(null);
		deleteChiTietPhieuNhapButton = new JButton("Xóa chi tiết phiếu nhập");
		modifyChiTietPhieuNhapButton = new JButton("Chỉnh sửa phiếu nhập");
		indexSelectionRow = -1;

		hasInitPhieuNhap = false;
		hasInitChiTietPhieuNhap = false;
		hasDataChiTietPhieuNhap = false;
		hasModifyChiTietPhieuNhap = false;
	}

	public void initComponentInPanel() {
		setBackground(Constants.COLOR_SPACE);
		FlexBox flexBoxForPanel = new FlexBox(this, FlexBox.DIRECTION_ROW);
		flexBoxForPanel.setPadding(25);
		
		phieuNhapFormPanel.setBackground(Color.WHITE);
		flexBoxForPanel.setPositionWithinPercentSize(30, 0, phieuNhapFormPanel);

		chiTietPhieuNhapFormPanel.setBackground(Color.WHITE);
		flexBoxForPanel.setPositionWithinPercentSize(30, 30, chiTietPhieuNhapFormPanel);

		chiTietPhieuNhapTablePanel.setBackground(Color.WHITE);
		flexBoxForPanel.setPositionWithinPercentSize(35, 30, chiTietPhieuNhapTablePanel);

		initComponentInPhieuNhapForm();
		addEventToPhieuNhapForm();

		initComponentInChiTietPhieuNhapForm();
		addEventToChiTietPhieuNhapForm();

		initComponentInChiTietPhieuNhapTable();
		addEventToChiTietPhieuNhapTable();
	}

	// ====================================== Phieu Nhap Form =========================================================
	private void initComponentInPhieuNhapForm() {
		FlexBox flexBoxForPanel = new FlexBox(phieuNhapFormPanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForPanel.setPadding(10);

		JLabel titleLabelTalbe = new JLabel("Thông tin phiếu nhập");
		setPropertyForTitleLabel(titleLabelTalbe);
		flexBoxForPanel.setPosition(titleLabelTalbe, 0, 0);

		JPanel inputPanel = new JPanel(null);
		inputPanel.setBackground(phieuNhapFormPanel.getBackground());
		flexBoxForPanel.setPositionWithinPercentSize(60, 10, inputPanel);

		FlexBox flexBoxForInput = new FlexBox(inputPanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForInput.setPadding(10);

		JLabel labelmaPhieuNhap = new JLabel("Mã phiếu nhập");
		setPropertyForLabel(labelmaPhieuNhap);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelmaPhieuNhap);
		setPropertyForInputField(maPhieuNhapInput);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, maPhieuNhapInput);
		setPropertyForValidation(maPhieuNhapValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, maPhieuNhapValidation);

		JLabel labelTenPhieuNhap = new JLabel("Mã nhà cung cấp");
		setPropertyForLabel(labelTenPhieuNhap);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelTenPhieuNhap);
		JPanel maNhaCungCapPanel = new JPanel(null);
		maNhaCungCapPanel.setBackground(phieuNhapFormPanel.getBackground());
		maNhaCungCapPanel.setSize(0, 35);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, maNhaCungCapPanel);
		FlexBox flexBoxForMaNhaCungCap = new FlexBox(maNhaCungCapPanel, FlexBox.DIRECTION_ROW);
		setPropertyForInputField(maNhaCungCapInput);
		flexBoxForMaNhaCungCap.setPositionWithinPercentSize(90, 0, maNhaCungCapInput);
		chooseMaNhaCungCap.setCursor(new Cursor(Cursor.HAND_CURSOR));
		chooseMaNhaCungCap.setBackground(phieuNhapFormPanel.getBackground());
		flexBoxForMaNhaCungCap.setPositionWithinPercentSize(100, 5, chooseMaNhaCungCap);
		setPropertyForValidation(maNhaCungCapValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, maNhaCungCapValidation);

		JLabel labelNgayBatDau = new JLabel("Mã nhân viên");
		setPropertyForLabel(labelNgayBatDau);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelNgayBatDau);
		setPropertyForInputField(maNhanVienInput);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, maNhanVienInput);
		setPropertyForValidation(maNhanVienValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, maNhanVienValidation);

		JLabel labelNgayKetThuc = new JLabel("Thời gian nhập");
		setPropertyForLabel(labelNgayKetThuc);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelNgayKetThuc);
		setPropertyForInputField(ngayNhapInput);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, ngayNhapInput);
		setPropertyForValidation(ngayNhapValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, ngayNhapValidation);

		JLabel labelTongTien = new JLabel("Tổng tiền");
		setPropertyForLabel(labelTongTien);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelTongTien);
		setPropertyForInputField(tongTienInput);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, tongTienInput);
		setPropertyForValidation(tongTienValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, tongTienValidation);

		JPanel buttonPanel = new JPanel(null);
		buttonPanel.setBackground(phieuNhapFormPanel.getBackground());
		flexBoxForPanel.setPositionWithinPercentSize(100, 10, buttonPanel);

		FlexBox flexBoxForButton = new FlexBox(buttonPanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForButton.setPadding(10);
		setPropertyForButton(initPhieuNhapButton);
		flexBoxForButton.setPosition(initPhieuNhapButton, 0, 0);
		setPropertyForButton(addPhieuNhapButton);
		flexBoxForButton.setPosition(addPhieuNhapButton, 15, 0);
		setPropertyForButton(refreshPhieuNhapButton);
		flexBoxForButton.setPosition(refreshPhieuNhapButton, 15, 0);

		refreshFormPhieuNhap();
		refreshDataPhieuNhapForm();
		createWindowChooseMaNhaCungCap();

		AbstractDocument document = (AbstractDocument)maPhieuNhapInput.getDocument();
		document.setDocumentFilter(new UppercaseDocumentFilter());
		AbstractDocument documentNCC = (AbstractDocument)maNhaCungCapInput.getDocument();
		documentNCC.setDocumentFilter(new UppercaseDocumentFilter());
	}

	private void addEventToPhieuNhapForm() {
		initPhieuNhapButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleEventInitPhieuNhap();
			}
		});		

		addPhieuNhapButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleEventAddPhieuNhap();
			}
		});		

		refreshPhieuNhapButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleEventRefreshPhieuNhap();
			}
		});		

		chooseMaNhaCungCap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				displayRegistry.subFrameRegistry(frameChooseMaNhaCungCap);
				refreshFrameChooseMaNhaCungCap();
			}
		});

		maPhieuNhapInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					handleEventInitPhieuNhap();
				}
			}
		});	

		maNhaCungCapInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					validationMaNhaCungCap();
				}
			}
		});	

		ngayNhapInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					validationNgayNhap();
				}
			}
		});	
	}

	private void handleEventInitPhieuNhap() {
		if(hasInitPhieuNhap) {
			showMessage("Vui lòng bấm nút làm mới phiếu nhập");
			return;
		}

		validationMaPhieu();

		if(isValidMaPhieuNhap) {
			hasInitPhieuNhap = true;
			xuLySuKienSauKhiNhapMaPhieu();
		}
	}

	private void handleEventAddPhieuNhap() {
		if(!hasInitPhieuNhap) {
			showMessage("Vui lòng khởi tạo phiếu nhập");
			return;
		}	

		validationNgayNhap();
		validationMaNhaCungCap();
		boolean isValidForm = isValidNgayNhap && isValidMaNhaCungCap;

		if(!isValidForm) {
			return;
		}

		if(!hasDataChiTietPhieuNhap) {
			showMessage("Bạn chưa thêm dữ liệu chi tiết phiếu nhập");
			return;
		}
		
		pnBLL.addPhieuNhap(phieuNhapCache);
		ctpnBLL.addChiTietPhieuNhap(mangChiTietPhieuNhap);
		
		for(ChiTietPhieuNhapDTO ctpn : mangChiTietPhieuNhap) {
			productBLL.updateProductQuantityIncrease(ctpn.getMaSanPham(), ctpn.getSoLuong());
		}

		showMessage("Thêm mới phiếu nhập thành công");
		handleEventRefreshPhieuNhap();
	}

	private void handleEventRefreshPhieuNhap() {
		refreshFormPhieuNhap();
		refreshFormChiTietPhieuNhap();
		refreshTable();
	}

	private void refreshFormPhieuNhap() {
		activeEnableInput(maPhieuNhapInput, maPhieuNhapValidation);
		activeDisableInput(maNhaCungCapInput, maNhaCungCapValidation);
		activeDisableInput(maNhanVienInput, maNhanVienValidation);
		activeDisableInput(tongTienInput, tongTienValidation);
		activeDisableInput(ngayNhapInput, ngayNhapValidation);

		chooseMaNhaCungCap.setEnabled(false);
		refreshDataPhieuNhapForm();
	}

	private void refreshDataPhieuNhapForm() {
		hasInitPhieuNhap = false;
		hasDataChiTietPhieuNhap = false;
		phieuNhapCache = null;
		mangChiTietPhieuNhap = null;
	}

	private void createWindowChooseMaNhaCungCap() {
		JPanel framePanel = new JPanel(null);
		JPanel searchPanel = new JPanel(null);
		JScrollPane scrollSearchTable = new JScrollPane();
		JButton submitButton = new JButton("Chon");
		JButton searchButton = new JButton("Search"); // nut tim kiem du lieu
												  //
		frameChooseMaNhaCungCap.setSize(800, 650);
		frameChooseMaNhaCungCap.setLayout(null);
		frameChooseMaNhaCungCap.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		frameChooseMaNhaCungCap.setLocationRelativeTo(null);
		frameChooseMaNhaCungCap.setResizable(false);
		frameChooseMaNhaCungCap.setVisible(false);

		framePanel.setSize(frameChooseMaNhaCungCap.getSize());
		framePanel.setLocation(0, 0);
		framePanel.setBackground(Color.WHITE);
		frameChooseMaNhaCungCap.getContentPane().add(framePanel);

		FlexBox flexBoxForPanel = new FlexBox(framePanel, FlexBox.DIRECTION_COLUMN);	
		flexBoxForPanel.setPadding(10);

		searchPanel.setSize(500, 40);
		searchPanel.setBackground(Color.WHITE);
		flexBoxForPanel.setPosition(FlexBox.ALIGMENT_CENTER, 0, searchPanel);
		FlexBox flexBoxForSearchPanel = new FlexBox(searchPanel, FlexBox.DIRECTION_ROW);
		setPropertyForInputField(searchMaNhaCungCapInput);
		flexBoxForSearchPanel.setPositionWithinPercentSize(75, 0, searchMaNhaCungCapInput);
		setPropertyForButton(searchButton);
		flexBoxForSearchPanel.setPositionWithinPercentSize(100, 10, searchButton);

		String[] columnsName = {"Mã", "Tên", "Địa chỉ"};
		int[] sizePerColumn = {15, 40, 45};
		scrollSearchTable.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		flexBoxForPanel.setPositionWithinPercentSize(75, 10, scrollSearchTable);
		tableFrameMaNhaCungCap = new AppTable(columnsName, sizePerColumn, scrollSearchTable.getSize());
		scrollSearchTable.getViewport().add(tableFrameMaNhaCungCap);

		DefaultTableModel tableFrameMaNhaCungCapModel = (DefaultTableModel)tableFrameMaNhaCungCap.getModel();
		setPropertyForButton(submitButton);
		flexBoxForPanel.setPosition(FlexBox.ALIGMENT_CENTER, 15, submitButton);

		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tableFrameMaNhaCungCap.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(frameChooseMaNhaCungCap, "Bạn chưa chọn nhà cung cấp, vui lòng nhấn chuột vào 1 dòng dữ liệu ở bảng để chọn nhà cung cấp");
					return;
				}

				String maNhaCungCap = tableFrameMaNhaCungCapModel.getValueAt(tableFrameMaNhaCungCap.getSelectedRow(), 0).toString();
				maNhaCungCapInput.setText(maNhaCungCap);
				validationMaNhaCungCap();
				displayRegistry.unSubFrameRegistry();
			}
		});

		searchButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String textInput = searchMaNhaCungCapInput.getText();
				
				if(textInput.length() == 0) {
					return;
				}
				
				
				ArrayList<NhaCungCapDTO> result = nccBLL.timKiemNCCTheoTen(searchMaNhaCungCapInput.getText());

				if(result.size() == 0) {
					JOptionPane.showMessageDialog(frameChooseMaNhaCungCap, "Không tìm thấy dữ liệu");
					return;
				}

				tableFrameMaNhaCungCapModel.setRowCount(0);
				for(NhaCungCapDTO ncc : result) {
					tableFrameMaNhaCungCapModel.addRow(ncc.toArrayString());
				}
			}
		});
	}


	private void refreshFrameChooseMaNhaCungCap() {
		searchMaNhaCungCapInput.setText("");

		DefaultTableModel tableFrameMaNhaCungCapModel = (DefaultTableModel)tableFrameMaNhaCungCap.getModel();
		tableFrameMaNhaCungCapModel.setRowCount(0);
		for(NhaCungCapDTO ncc : nccBLL.getAllNCC()) {
			tableFrameMaNhaCungCapModel.addRow(ncc.toArrayString());
		}
	}

	private void validationMaPhieu() {
		String maPhieu = maPhieuNhapInput.getText().trim();

		maNhaCungCapInput.setEditable(false);

		if(maPhieu.length() == 0) {
			activeValidationInput(maPhieuNhapInput, maPhieuNhapValidation, "Bạn chưa nhập dữ liệu");
			return;
		}

		if(pnBLL.kiemTraMaPhieu(maPhieu)) {
			activeValidationInput(maPhieuNhapInput, maPhieuNhapValidation, "Mã phiếu nhập đã tồn tại");
			return;
		}	

		if(maPhieu.length() > 8 || maPhieu.length() < 4) {
			activeValidationInput(maPhieuNhapInput, maPhieuNhapValidation, "Mã phiếu nhập chỉ từ 4 đến 8 ký tự ");
			return;
		}

		phieuNhapCache = new PhieuNhapDTO();
		phieuNhapCache.setMaPhieu(maPhieu);
		activeSuccessInput(maPhieuNhapInput, maPhieuNhapValidation);
		isValidMaPhieuNhap = true;
	}

	private void xuLySuKienSauKhiNhapMaPhieu() {
		mangChiTietPhieuNhap = new ArrayList<ChiTietPhieuNhapDTO>();

		activeEnableInput(maNhaCungCapInput, maNhaCungCapValidation);	
		chooseMaNhaCungCap.setEnabled(true);

		activeSuccessInput(maNhanVienInput, maNhanVienValidation);
		phieuNhapCache.setMaNhanVien(token.getNhanVien().getMaNhanVien());
		maNhanVienInput.setText(phieuNhapCache.getMaNhanVien());

		activeSuccessInput(tongTienInput, tongTienValidation);
		phieuNhapCache.setTongTien(0);
		tongTienInput.setText(2323);
		tongTienInput.setText("");

		activeEnableInput(ngayNhapInput, ngayNhapValidation);
		hasInitPhieuNhap = true;
	}

	private void validationMaNhaCungCap() {
		String maNhaCungCap = maNhaCungCapInput.getText().trim();

		maNhaCungCapInput.setEditable(false);

		if(maNhaCungCap.length() == 0) {
			activeValidationInput(maNhaCungCapInput, maNhaCungCapValidation, "Bạn chưa nhập dữ liêu");
			return;
		}

		if(!nccBLL.kiemTraMaNhaCungCap(maNhaCungCap)) {
			activeValidationInput(maNhaCungCapInput, maNhaCungCapValidation, "Mã nhà cung cấp không tồn tại");
			return;
		}

		phieuNhapCache.setMaNhaCungCap(maNhaCungCap);
		chooseMaNhaCungCap.setEnabled(false);
		activeSuccessInput(maNhaCungCapInput, maNhaCungCapValidation);
		isValidMaNhaCungCap = true;
	}

	private void validationNgayNhap() {
		String ngayNhap = ngayNhapInput.getText().trim();

		ngayNhapInput.setEditable(false);

		if(ngayNhap.length() == 0) {
			activeValidationInput(ngayNhapInput, ngayNhapValidation, "Bạn chưa nhập dữ liêu");
			return;
		}

		Date dateNhap = null;
		try {
			 dateNhap = new Date(ngayNhap);
		}
		catch(Exception ex) {
			activeValidationInput(ngayNhapInput, ngayNhapValidation, ex.getMessage());
			return;
		}

		phieuNhapCache.setNgayNhap(ngayNhap);
		ngayNhapInput.setText(dateNhap.toString());
		activeSuccessInput(ngayNhapInput, ngayNhapValidation);
		isValidNgayNhap = true;
	}

	// ======================================================= CHI TIET PHIEU NHAP ==================================================

	private void initComponentInChiTietPhieuNhapForm() {
		FlexBox flexBoxForPanel = new FlexBox(chiTietPhieuNhapFormPanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForPanel.setPadding(10);

		JLabel titleLabelTalbe = new JLabel("Thông tin chi tiết phiếu nhập");
		setPropertyForTitleLabel(titleLabelTalbe);
		flexBoxForPanel.setPosition(titleLabelTalbe, 0, 0);

		JPanel inputPanel = new JPanel(null);
		inputPanel.setBackground(chiTietPhieuNhapFormPanel.getBackground());
		flexBoxForPanel.setPositionWithinPercentSize(60, 10, inputPanel);

		FlexBox flexBoxForInput = new FlexBox(inputPanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForInput.setPadding(10);

		JLabel labelmaChiTietPhieuNhap = new JLabel("Mã phiếu nhập");
		setPropertyForLabel(labelmaChiTietPhieuNhap);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelmaChiTietPhieuNhap);
		setPropertyForInputField(maChiTietPhieuNhapInput);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, maChiTietPhieuNhapInput);
		setPropertyForValidation(maChiTietPhieuNhapValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, maChiTietPhieuNhapValidation);

		JLabel labelTenChiTietPhieuNhap = new JLabel("Mã sản phẩm");
		setPropertyForLabel(labelTenChiTietPhieuNhap);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelTenChiTietPhieuNhap);
		JPanel sanPhamPanel = new JPanel(null);
		sanPhamPanel.setBackground(chiTietPhieuNhapFormPanel.getBackground());
		sanPhamPanel.setSize(0, 35);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, sanPhamPanel);
		FlexBox flexBoxForMaSanPham = new FlexBox(sanPhamPanel, FlexBox.DIRECTION_ROW);
		setPropertyForInputField(sanPhamInput);
		flexBoxForMaSanPham.setPositionWithinPercentSize(90, 0, sanPhamInput);
		chooseMaSanPham.setCursor(new Cursor(Cursor.HAND_CURSOR));
		chooseMaSanPham.setBackground(chiTietPhieuNhapFormPanel.getBackground());
		flexBoxForMaSanPham.setPositionWithinPercentSize(100, 5, chooseMaSanPham);
		setPropertyForValidation(sanPhamValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, sanPhamValidation);

		JLabel labelNgayBatDau = new JLabel("Số lượng");
		setPropertyForLabel(labelNgayBatDau);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelNgayBatDau);
		setPropertyForInputField(soLuongInput);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, soLuongInput);
		setPropertyForValidation(soLuongValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, soLuongValidation);

		JLabel labelNgayKetThuc = new JLabel("Đơn giá");
		setPropertyForLabel(labelNgayKetThuc);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelNgayKetThuc);
		setPropertyForInputField(donGiaInput);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, donGiaInput);
		setPropertyForValidation(donGiaValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, donGiaValidation);

		JLabel labelThanhTien = new JLabel("Thành tiền");
		setPropertyForLabel(labelThanhTien);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelThanhTien);
		setPropertyForInputField(thanhTienInput);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, thanhTienInput);
		setPropertyForValidation(thanhTienValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, thanhTienValidation);

		JPanel buttonPanel = new JPanel(null);
		buttonPanel.setBackground(phieuNhapFormPanel.getBackground());
		flexBoxForPanel.setPositionWithinPercentSize(100, 10, buttonPanel);

		FlexBox flexBoxForButton = new FlexBox(buttonPanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForButton.setPadding(10);
		setPropertyForButton(initChiTietPhieuNhapButton);
		flexBoxForButton.setPosition(initChiTietPhieuNhapButton, 0, 0);
		setPropertyForButton(addChiTietPhieuNhapButton);
		flexBoxForButton.setPosition(addChiTietPhieuNhapButton, 15, 0);

		refreshFormChiTietPhieuNhap();
		createWindowChooseMaSanPham();

		AbstractDocument document = (AbstractDocument)sanPhamInput.getDocument();
		document.setDocumentFilter(new UppercaseDocumentFilter());
		AbstractDocument documentSoLuong = (AbstractDocument)soLuongInput.getDocument();
		documentSoLuong.setDocumentFilter(new NumberDocumentFilter());
	}

	private void addEventToChiTietPhieuNhapForm() {
		initChiTietPhieuNhapButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleEventInitChiTietPhieuNhap();
			}
		});	

		addChiTietPhieuNhapButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleEventAddChiTietPhieuNhap();
			}
		});	

		chooseMaSanPham.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				displayRegistry.subFrameRegistry(frameChooseMaSanPham);
				refreshFrameChooseMaSanPham();
			}
		});	

		sanPhamInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					validationMaSanPham();
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
		donGiaInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					validationDonGia();
				}
			}
		});	
	}

	private void chuyenDuLieu(ChiTietPhieuNhapDTO dto) {
		refreshDataChiTietPhieuNhap();	
		hasInitChiTietPhieuNhap = false;
		hasModifyChiTietPhieuNhap = true;
		
		chiTietPhieuNhapModifyCache = dto;
		chiTietPhieuNhapCache = dto.clone();

		activeSuccessInput(maChiTietPhieuNhapInput, maChiTietPhieuNhapValidation);
		maChiTietPhieuNhapInput.setText(dto.getMaPhieu());

		activeEnableInput(sanPhamInput, sanPhamValidation);
		sanPhamInput.setText(dto.getMaSanPham());
		chooseMaSanPham.setEnabled(true);

		activeEnableInput(soLuongInput, soLuongValidation);
		soLuongInput.setText(Integer.toString(dto.getSoLuong()));

		activeEnableInput(donGiaInput, donGiaValidation);
		donGiaInput.setText(dto.getDonGia());

		activeSuccessInput(thanhTienInput, thanhTienValidation);
		thanhTienInput.setText(dto.getThanhTien());
	}

	private void handleEventInitChiTietPhieuNhap() {
		if(!hasInitPhieuNhap) {
			showMessage("Bạn chưa khởi tạo phiếu nhập");
			return;
		}

		if(hasInitChiTietPhieuNhap) {
			refreshFormChiTietPhieuNhap();
		}

		chiTietPhieuNhapCache = new ChiTietPhieuNhapDTO();
		hasInitChiTietPhieuNhap = true;
		hasModifyChiTietPhieuNhap = false;
		
		activeSuccessInput(maChiTietPhieuNhapInput, maChiTietPhieuNhapValidation);
		chiTietPhieuNhapCache.setMaPhieu(phieuNhapCache.getMaPhieu());
		maChiTietPhieuNhapInput.setText(chiTietPhieuNhapCache.getMaPhieu());

		activeEnableInput(sanPhamInput, sanPhamValidation);
		chooseMaSanPham.setEnabled(true);

		activeEnableInput(soLuongInput, soLuongValidation);
		chiTietPhieuNhapCache.setSoLuong(0);

		activeEnableInput(donGiaInput, donGiaValidation);
		chiTietPhieuNhapCache.setDonGia(0);

		activeSuccessInput(thanhTienInput, thanhTienValidation);
		chiTietPhieuNhapCache.setThanhTien(0);
		thanhTienInput.setText(123);
		thanhTienInput.setText("");
	}

	private void handleEventAddChiTietPhieuNhap() {
		if(!hasInitChiTietPhieuNhap) {
			showMessage("Bạn chưa khởi tạo chi tiết phiếu nhập");
			return;
		}

		validationMaSanPham();
		validationDonGia();
		validationSoLuong();

		boolean isValidForm = isValidMaSanPham && isValidDonGia && isValidSoLuong;

		if(!isValidForm) {
			return;
		}

		mangChiTietPhieuNhap.add(chiTietPhieuNhapCache);
		showMessage("Thêm mới chi tiết phiếu nhập thành công");
		hasDataChiTietPhieuNhap = true;
		phieuNhapCache.setTongTien(phieuNhapCache.getTongTien() + chiTietPhieuNhapCache.getThanhTien());
		tongTienInput.setText(phieuNhapCache.getTongTien());
		refreshFormChiTietPhieuNhap();
		refreshTable();
	}

	private void handleEventModifyChiTietPhieuNhap() {
		if(!hasDataChiTietPhieuNhap) {
			showMessage("Chưa có dữ liệu chi tiết phiếu nhập");
			return;
		}
	
		if(!hasModifyChiTietPhieuNhap) {
			showMessage("Vui lòng chọn 1 dòng dữ liệu bên bảng dữ liệu để có thể tiến hành chỉnh sửa dữ liệu");
			return;
		}

		if(chiTietPhieuNhapModifyCache.equals(chiTietPhieuNhapCache)) {
			showMessage("Bạn chưa chỉnh sửa dữ liệu");
			return;
		}

		validationMaSanPham();
		validationDonGia();
		validationSoLuong();

		boolean isValidForm = isValidMaSanPham && isValidDonGia && isValidSoLuong;

		if(!isValidForm) {
			return;
		}

		phieuNhapCache.setTongTien((phieuNhapCache.getTongTien() - chiTietPhieuNhapModifyCache.getThanhTien()) + chiTietPhieuNhapCache.getThanhTien());
		tongTienInput.setText(phieuNhapCache.getTongTien());
		mangChiTietPhieuNhap.set(indexSelectionRow, chiTietPhieuNhapCache);
		showMessage("chỉnh sửa dữ liệu chi tiết phiếu nhập mã: " + chiTietPhieuNhapModifyCache.getMaPhieu() + " thành công");
		refreshFormChiTietPhieuNhap();
		refreshTable();
	}

	private void handleEventDeleteChiTietPhieuNhap() {
		if(!hasDataChiTietPhieuNhap) {
			showMessage("Chưa có dữ liệu chi tiết phiếu nhập");
			return;
		}

		if(!hasModifyChiTietPhieuNhap) {
			showMessage("Vui lòng chọn 1 dòng dữ liệu bên bảng dữ liệu để có thể tiến hành xóa dữ liệu dữ liệu");
			return;
		}

		phieuNhapCache.setTongTien(phieuNhapCache.getTongTien() - chiTietPhieuNhapModifyCache.getThanhTien());
		if(phieuNhapCache.getTongTien() == 0) {
			tongTienInput.setText("");
		}
		else {
			tongTienInput.setText(phieuNhapCache.getTongTien());
		}
		mangChiTietPhieuNhap.remove(indexSelectionRow);
		showMessage("xóa dữ liệu chi tiết phiếu nhập mã: " + chiTietPhieuNhapModifyCache.getMaPhieu() + " thành công");
		refreshFormChiTietPhieuNhap();
		refreshTable();
	}

	private void refreshFormChiTietPhieuNhap() {
		activeDisableInput(maChiTietPhieuNhapInput, maChiTietPhieuNhapValidation);
		activeDisableInput(sanPhamInput, sanPhamValidation);
		activeDisableInput(soLuongInput, soLuongValidation);
		activeDisableInput(donGiaInput, donGiaValidation);
		activeDisableInput(thanhTienInput, thanhTienValidation);
		chooseMaSanPham.setEnabled(false);

		hasInitChiTietPhieuNhap = false;
		hasModifyChiTietPhieuNhap = false;

		refreshDataChiTietPhieuNhap();
	}

	private void refreshDataChiTietPhieuNhap() {
		sanPhamInput.setToolTipText("");
		isValidMaSanPham = false;
		isValidDonGia = false;
		isValidSoLuong = false;
		chiTietPhieuNhapCache = null;
		chiTietPhieuNhapModifyCache = null;
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

		String[] columnsName = {"Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Giá"};
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
					JOptionPane.showMessageDialog(frameChooseMaSanPham, "Bạn chưa chọn sản phẩm, vui lòng nhấn chuột vào 1 dòng dữ liệu ở bảng để chọn sản phẩm");
					return;
				}

				String maSanPham = tableFrameMaSanPhamModel.getValueAt(tableFrameMaSanPham.getSelectedRow(), 0).toString();
				sanPhamInput.setText(maSanPham);
				sanPhamInput.setToolTipText("Tên sản phẩm: " + tableFrameMaSanPhamModel.getValueAt(tableFrameMaSanPham.getSelectedRow(), 1).toString());
				validationMaSanPham();
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
					JOptionPane.showMessageDialog(frameChooseMaSanPham, "Không tìm thấy dữ liệu");
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
		for(ProductDTO product : ProductBLL.getInstance().getAllProduct()) {
			tableFrameMaSanPhamModel.addRow(product.toArrayString());
		}
	}

	private void validationMaSanPham() {
		if(isValidMaSanPham) {
			return;
		}

		sanPhamInput.setEditable(false);

		String maSanPham = sanPhamInput.getText().trim();

		if(maSanPham.length() == 0) {
			activeValidationInput(sanPhamInput, sanPhamValidation, "Bạn chưa nhập dữ liệu");
			return;
		}

		if(productBLL.soSanhMaSanPham(maSanPham) == null) {
			activeValidationInput(sanPhamInput, sanPhamValidation, "Mã sản phẩm bạn nhập không tồn tại");
			return;
		}

		boolean isExist = false;
		for(ChiTietPhieuNhapDTO ctpn : mangChiTietPhieuNhap) {
			if(ctpn.getMaSanPham().equals(maSanPham)) {
				activeValidationInput(sanPhamInput, sanPhamValidation, "Mã sản phẩm đã có sẳn");
				isExist = true;
				break;
			}
		}

		if(isExist) {
			activeValidationInput(sanPhamInput, sanPhamValidation, "Mã sản phẩm đã có sẳn");
			return;
		}

		chiTietPhieuNhapCache.setMaSanPham(maSanPham);
		chooseMaSanPham.setEnabled(false);
		activeSuccessInput(sanPhamInput, sanPhamValidation);
		isValidMaSanPham = true;

		if(isValidDonGia) {
			isValidDonGia = false;
			validationDonGia();
		}	
	}

	private void validationDonGia() {
		if(isValidDonGia) {
			return;
		}

		donGiaInput.setEditable(false);

		int donGia = Integer.parseInt(donGiaInput.getMoney().toString());

		if(donGia == 0) {
			activeValidationInput(donGiaInput, donGiaValidation, "Bạn chưa nhập dữ liệu");
			return;
		}

		if(isValidMaSanPham) {
			int donGiaSanPham = productBLL.soSanhMaSanPham(chiTietPhieuNhapCache.getMaSanPham()).getDonGiaSanPham();
			if(donGia >= donGiaSanPham) {
				activeValidationInput(donGiaInput, donGiaValidation, "Đơn giá phải nhỏ hơn giá sản phẩm(" + Util.convertMoneyToString(donGiaSanPham) + ")");
				return;
			}
		}

		chiTietPhieuNhapCache.setDonGia(donGia);
		activeSuccessInput(donGiaInput, donGiaValidation);
		isValidDonGia = true;

		if(chiTietPhieuNhapCache.getSoLuong() != 0) {
			chiTietPhieuNhapCache.setThanhTien(donGia * chiTietPhieuNhapCache.getSoLuong());
			thanhTienInput.setText(chiTietPhieuNhapCache.getThanhTien());
		}
	}

	private void validationSoLuong() {
		if(isValidSoLuong) {
			return;
		}

		soLuongInput.setEditable(false);

		String soLuongStr = soLuongInput.getText().trim();

		if(soLuongStr.length() == 0) {
			activeValidationInput(soLuongInput, soLuongValidation, "Bạn chưa nhập dữ liệu");
			return;
		}

		int soLuong = Integer.parseInt(soLuongStr);

		if(soLuong < 1 || soLuong > 100000) {
			activeValidationInput(soLuongInput, soLuongValidation, "Vui lòng nhập số lượng trong khoảng từ 1 đến 100000");
			return;
		}

		chiTietPhieuNhapCache.setSoLuong(soLuong);
		activeSuccessInput(soLuongInput, soLuongValidation);
		isValidSoLuong = true;

		if(chiTietPhieuNhapCache.getDonGia() != 0) {
			chiTietPhieuNhapCache.setThanhTien(soLuong * chiTietPhieuNhapCache.getDonGia());
			thanhTienInput.setText(chiTietPhieuNhapCache.getThanhTien());
		}
	}

	// ==================================================================== bang du lieu =====================================================
	private void initComponentInChiTietPhieuNhapTable() {
		FlexBox flexBoxForPanel = new FlexBox(chiTietPhieuNhapTablePanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForPanel.setPadding(10);

		JLabel titleLabelTalbe = new JLabel("Chi tiết phiếu nhập đã thêm");
		setPropertyForTitleLabel(titleLabelTalbe);
		flexBoxForPanel.setPosition(titleLabelTalbe, 0, 0);

		JPanel tablePanel = new JPanel(null);
		tablePanel.setBackground(chiTietPhieuNhapTablePanel.getBackground());
		flexBoxForPanel.setPositionWithinPercentSize(60, 10, tablePanel);

		FlexBox flexBoxForTable = new FlexBox(tablePanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForTable.setPadding(10);

		JScrollPane scroll = new JScrollPane();
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		flexBoxForTable.setPositionWithinPercentSize(100, 0, scroll);	
		String columnNames[] = {"Mã phiếu", "Mã sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"};
		int columnSize[] = {20, 20, 20, 20, 20};
		tableChiTietPhieuNhap = new AppTable(columnNames, columnSize, scroll.getSize());
		scroll.getViewport().add(tableChiTietPhieuNhap);

		JPanel buttonPanel = new JPanel(null);
		buttonPanel.setBackground(chiTietPhieuNhapTablePanel.getBackground());
		flexBoxForPanel.setPositionWithinPercentSize(100, 10, buttonPanel);

		FlexBox flexBoxForButton = new FlexBox(buttonPanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForButton.setPadding(10);
		setPropertyForButton(deleteChiTietPhieuNhapButton);
		flexBoxForButton.setPosition(deleteChiTietPhieuNhapButton, 0, 0);
		setPropertyForButton(modifyChiTietPhieuNhapButton);
		flexBoxForButton.setPosition(modifyChiTietPhieuNhapButton, 15, 0);
	}

	private void addEventToChiTietPhieuNhapTable() {
		tableChiTietPhieuNhap.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				handleClickRowTable();	
			}
		});

		deleteChiTietPhieuNhapButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleEventDeleteChiTietPhieuNhap();
			}
		});

		modifyChiTietPhieuNhapButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleEventModifyChiTietPhieuNhap();
			}
		});
	}

	private void handleClickRowTable() {
		int index = tableChiTietPhieuNhap.getSelectedRow();
		
		if(index == -1) {
			return;
		}

		if(index == indexSelectionRow) {
			return;
		}

		indexSelectionRow = index;

		chuyenDuLieu(mangChiTietPhieuNhap.get(indexSelectionRow));
	}

	private void refreshTable() {
		indexSelectionRow = -1;
		DefaultTableModel tableModel = (DefaultTableModel)tableChiTietPhieuNhap.getModel();
		tableModel.setRowCount(0);

		if(mangChiTietPhieuNhap != null) {
			for(ChiTietPhieuNhapDTO ctpn : mangChiTietPhieuNhap) {
				tableModel.addRow(ctpn.toArrayString());
			}
		}
	}













	// ======================================================================================================================
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
		input.setForeground(new Color(192, 208, 242));
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
		input.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Constants.COLOR_TEXTFIELD_TYPE_BORDER), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		validation.setVisible(false);
	}

	private void activeSuccessInput(JTextField input, JLabel validation) {
		input.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Constants.COLOR_TEXTFIELD_SUCCESS_BORDER), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		input.setBackground(Color.WHITE);
		input.setForeground(Constants.COLOR_TEXTFIELD_FOREGROUND);
		input.setEditable(false);
		validation.setVisible(false);
	}

	private void activeValidationInput(JTextField input, JLabel validation, String text) {
		input.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Constants.COLOR_TEXTFIELD_DANGER_BORDER), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		input.setEditable(true);
		input.setForeground(Constants.COLOR_TEXTFIELD_FOREGROUND);
		validation.setText(text);
		validation.setVisible(true);
	}

	private void setPropertyForButton(JButton button) {
		button.setFont(defaultFont);
		button.setForeground(Constants.COLOR_BACKGROUND_PRIMARY);
		button.setBackground(Constants.COLOR_PRIMARY);
		button.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Constants.COLOR_PRIMARY));
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

	private void showMessage(String message) {
		JOptionPane.showMessageDialog(displayRegistry.getMainFrame(), message);
	}
}
