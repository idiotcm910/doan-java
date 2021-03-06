package GUI.khuyenmaigui;

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
import javax.swing.JComboBox;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

import java.util.ArrayList;

import common.*;
import components.UppercaseDocumentFilter;
import components.AppTable;
import components.NumberDocumentFilter;
import DTO.KhuyenMaiDTO;
import DTO.ChiTietKhuyenMaiDTO;
import DTO.ProductDTO;
import BLL.KhuyenMaiBLL;
import BLL.ChiTietKhuyenMaiBLL;
import BLL.ProductBLL;

class KhuyenMaiFormPanel extends JPanel {
	private Font defaultFont;
	private FrameDisplayRegistry displayRegistry;
	private KhuyenMaiBLL kmBLL;
	private ChiTietKhuyenMaiBLL ctkmBLL;
	private ProductBLL productBLL;

	// form khuyen mai
	private JPanel khuyenMaiFormPanel;		
	private JTextField maKhuyenMaiInput;
	private JLabel maKhuyenMaiValidation;
	private boolean isValidMa;
	private JTextField tenKhuyenMaiInput;
	private JLabel tenKhuyenMaiValidation;
	private boolean isValidTen;
	private JTextField ngayBatDauInput;
	private JLabel ngayBatDauValidation;
	private boolean isValidNgayBatDau;
	private JTextField ngayKetThucInput;
	private JLabel ngayKetThucValidation;
	private boolean isValidNgayKetThuc;
	private JButton initKhuyenMaiButton;
	private JButton addKhuyenMaiButton;
	private JButton refreshKhuyenMaiButton;
	private KhuyenMaiDTO khuyenMaiCache;

	// form chi tiet khuyen mai
	private JPanel chiTietKhuyenMaiFormPanel;	
	private JTextField maChiTietKhuyenMaiInput;
	private JLabel maChiTietKhuyenMaiValidation;
	private JTextField phanTramKhuyenMaiInput;
	private JLabel phanTramKhuyenMaiValidation;
	private boolean isValidPhanTram;
	private JTextField maSanPhamInput;
	private JButton chooseMaSanPham;
	private boolean isValidMaSanPham;

	// frame choose ma san pham
	private JFrame frameChooseMaSanPham;
	private AppTable tableFrame;
	private JTextField searchMaSanPhamInput;

	private JLabel maSanPhamValidation;
	private JButton initChiTietKhuyenMaiButton;
	private JButton addChiTietKhuyenMaiButton;
	private ChiTietKhuyenMaiDTO chiTietKhuyenMaiCache;
	private ChiTietKhuyenMaiDTO chiTietKhuyenMaiCacheModify;
	private ArrayList<ChiTietKhuyenMaiDTO> mangChiTietKhuyenMai;

	// bang du lieu chi tiet khuyen mai da nhap
	private JPanel chiTietKhuyenMaiTablePanel;
	private JButton deleteChiTietKhuyenMaiButton;
	private JButton modifyChiTietKhuyenMaiButton;
	private AppTable tableChiTietKhuyenMai;
	private int indexSelectionRow;


	private boolean hasInitKhuyenMai;
	private boolean hasInitChiTietKhuyenMai;
	private boolean hasDataChiTietKhuyenMai;
	private boolean hasModifyChiTietKhuyenMai;
	public KhuyenMaiFormPanel() {
		setLayout(null);
		initVariable();
	}

	private void initVariable() {
		defaultFont = Util.getFont("Roboto", "NORMAL", 18);
		displayRegistry = FrameDisplayRegistry.getInstance();
		kmBLL = KhuyenMaiBLL.getInstance();
		ctkmBLL = ChiTietKhuyenMaiBLL.getInstance();
		productBLL = ProductBLL.getInstance();

		// form khuy???n m??i
		khuyenMaiFormPanel = new JPanel(null);
		maKhuyenMaiInput = new JTextField();
		maKhuyenMaiValidation = new JLabel();
		tenKhuyenMaiInput = new JTextField();
		tenKhuyenMaiValidation = new JLabel();
		ngayBatDauInput = new JTextField();
		ngayBatDauValidation = new JLabel();
		ngayKetThucInput = new JTextField();
		ngayKetThucValidation = new JLabel();
		initKhuyenMaiButton = new JButton("Kh???i t???o khuy???n m??i");
		addKhuyenMaiButton = new JButton("Th??m khuy???n m??i");
		refreshKhuyenMaiButton = new JButton("T???o m???i khuy???n m??i");
		khuyenMaiCache = new KhuyenMaiDTO();

		//form chi ti???t khuy???n m??i
		chiTietKhuyenMaiFormPanel = new JPanel(null);
		maChiTietKhuyenMaiInput = new JTextField();
		maChiTietKhuyenMaiValidation = new JLabel();
		phanTramKhuyenMaiInput = new JTextField();
		phanTramKhuyenMaiValidation = new JLabel();
		maSanPhamInput = new JTextField();
		chooseMaSanPham = new JButton("...");

		frameChooseMaSanPham = new JFrame();
		searchMaSanPhamInput = new JTextField();

		maSanPhamValidation = new JLabel();
		initChiTietKhuyenMaiButton = new JButton("Kh???i t???o chi ti???t khuy???n m??i");
		addChiTietKhuyenMaiButton = new JButton("Th??m m???i chi ti???t khuy???n m??i");
		mangChiTietKhuyenMai = new ArrayList<ChiTietKhuyenMaiDTO>();

		// table chi ti???t khuy???n m??i
		chiTietKhuyenMaiTablePanel = new JPanel(null);
		deleteChiTietKhuyenMaiButton = new JButton("X??a chi ti???t khuy???n m??i");
		modifyChiTietKhuyenMaiButton = new JButton("Ch???nh s???a chi ti???t khuy???n m??i");
		indexSelectionRow = -1;

		hasInitKhuyenMai = false;
		hasInitChiTietKhuyenMai = false;
		hasDataChiTietKhuyenMai = false;
		hasModifyChiTietKhuyenMai = false;
	}

	public void initComponentInPanel() {
		setBackground(Constants.COLOR_SPACE);
		FlexBox flexBoxForPanel = new FlexBox(this, FlexBox.DIRECTION_ROW);
		flexBoxForPanel.setPadding(25);
		
		khuyenMaiFormPanel.setBackground(Color.WHITE);
		flexBoxForPanel.setPositionWithinPercentSize(30, 0, khuyenMaiFormPanel);

		chiTietKhuyenMaiFormPanel.setBackground(Color.WHITE);
		flexBoxForPanel.setPositionWithinPercentSize(30, 30, chiTietKhuyenMaiFormPanel);

		chiTietKhuyenMaiTablePanel.setBackground(Color.WHITE);
		flexBoxForPanel.setPositionWithinPercentSize(35, 30, chiTietKhuyenMaiTablePanel);

		initComponentInKhuyenMaiForm();
		addEventToKhuyenMaiForm();
		initComponentInChiTietKhuyenMaiForm();
		addEventToChiTietKhuyenMaiForm();
		initComponentInChiTietKhuyenMaiTable();
		addEventToChiTietKhuyenMaiTable();
	}

	// ============================= FORM KHUYEN MAI =========================================================
	private void initComponentInKhuyenMaiForm() {
		FlexBox flexBoxForPanel = new FlexBox(khuyenMaiFormPanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForPanel.setPadding(10);

		JLabel titleLabelTalbe = new JLabel("Th??ng tin khuy???n m??i");
		setPropertyForTitleLabel(titleLabelTalbe);
		flexBoxForPanel.setPosition(titleLabelTalbe, 0, 0);

		JPanel inputPanel = new JPanel(null);
		inputPanel.setBackground(khuyenMaiFormPanel.getBackground());
		flexBoxForPanel.setPositionWithinPercentSize(60, 10, inputPanel);

		FlexBox flexBoxForInput = new FlexBox(inputPanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForInput.setPadding(10);

		JLabel labelmaKhuyenMai = new JLabel("M?? khuy???n m??i");
		setPropertyForLabel(labelmaKhuyenMai);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelmaKhuyenMai);
		setPropertyForInputField(maKhuyenMaiInput);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, maKhuyenMaiInput);
		setPropertyForValidation(maKhuyenMaiValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, maKhuyenMaiValidation);

		JLabel labelTenKhuyenMai = new JLabel("T??n khuy???n m??i");
		setPropertyForLabel(labelTenKhuyenMai);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelTenKhuyenMai);
		setPropertyForInputField(tenKhuyenMaiInput);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, tenKhuyenMaiInput);
		setPropertyForValidation(tenKhuyenMaiValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, tenKhuyenMaiValidation);

		JLabel labelNgayBatDau = new JLabel("Ng??y b???t ?????u");
		setPropertyForLabel(labelNgayBatDau);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelNgayBatDau);
		setPropertyForInputField(ngayBatDauInput);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, ngayBatDauInput);
		setPropertyForValidation(ngayBatDauValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, ngayBatDauValidation);

		JLabel labelNgayKetThuc = new JLabel("Ng??y k???t th??c");
		setPropertyForLabel(labelNgayKetThuc);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelNgayKetThuc);
		setPropertyForInputField(ngayKetThucInput);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, ngayKetThucInput);
		setPropertyForValidation(ngayKetThucValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, ngayKetThucValidation);

		JPanel buttonPanel = new JPanel(null);
		buttonPanel.setBackground(khuyenMaiFormPanel.getBackground());
		flexBoxForPanel.setPositionWithinPercentSize(100, 10, buttonPanel);

		FlexBox flexBoxForButton = new FlexBox(buttonPanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForButton.setPadding(10);
		setPropertyForButton(initKhuyenMaiButton);
		flexBoxForButton.setPosition(initKhuyenMaiButton, 0, 0);
		setPropertyForButton(addKhuyenMaiButton);
		flexBoxForButton.setPosition(addKhuyenMaiButton, 15, 0);
		setPropertyForButton(refreshKhuyenMaiButton);
		flexBoxForButton.setPosition(refreshKhuyenMaiButton, 15, 0);
		refreshFormKhuyenMai();

		AbstractDocument document = (AbstractDocument)maKhuyenMaiInput.getDocument();
		document.setDocumentFilter(new UppercaseDocumentFilter());
	}
	
	private void addEventToKhuyenMaiForm() {
		initKhuyenMaiButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleEventButtonInitKhuyenMai();
			}
		});

		refreshKhuyenMaiButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				refreshAllForm();
			}
		});

		addKhuyenMaiButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleEventAddKhuyenMai();
			}
		});

		maKhuyenMaiInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					handleEventButtonInitKhuyenMai();
				}
			}
		});	
		tenKhuyenMaiInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					validationTenKhuyenMai();
				}
			}
		});	
		ngayBatDauInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					validationNgayBatDau();
				}
			}
		});	
		ngayKetThucInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					validationNgayKetThuc();
				}
			}
		});	
	}	

	private	void handleEventButtonInitKhuyenMai() {
		if(hasInitKhuyenMai) {
			showMessage("Khuy???n m??i ???? ???????c kh???i t???o, ????? kh???i t???o m???i vui l??ng b???m v??o n??t l??m m???i khuy???n m??i");
			return;
		}

		validationMaKhuyenMai();
		if(!isValidMa) {
			return;		
		}	

		activeEnableInput(tenKhuyenMaiInput, tenKhuyenMaiValidation);
		activeEnableInput(ngayBatDauInput, ngayBatDauValidation);
		activeEnableInput(ngayKetThucInput, ngayKetThucValidation);
		hasInitKhuyenMai = true;
	}

	private void handleEventAddKhuyenMai() {
		if(!hasInitKhuyenMai) {
			showMessage("B???n ch??a kh???i t???o khuy???n m??i");
			return;
		}

		validationTenKhuyenMai();
		validationNgayBatDau();
		validationNgayKetThuc();
		boolean isValidForm = isValidTen && isValidNgayBatDau && isValidNgayKetThuc;
		if(!isValidForm) {
			return;
		}

		if(!hasDataChiTietKhuyenMai) {
			showMessage("B???n ch??a c?? d??? li???u ??? chi ti???t khuy???n m??i");
			return;
		}

		kmBLL.addKhuyenMai(khuyenMaiCache);
		ctkmBLL.addChiTietKhuyenMai(mangChiTietKhuyenMai);	
		showMessage("B???n th??m khuy???n m??i th??nh c??ng");
		refreshAllForm();
	}

	private void validationMaKhuyenMai() {
		if(isValidMa) {
			return;
		}
		String maKhuyenMai = maKhuyenMaiInput.getText().trim();

		if(maKhuyenMai.length() == 0) {
			activeValidationInput(maKhuyenMaiInput, maKhuyenMaiValidation, "B???n ch??a nh???p d??? li???u");
			return ;
		}

		if(kmBLL.kiemTraMaKhuyenMai(maKhuyenMai)) {
			activeValidationInput(maKhuyenMaiInput, maKhuyenMaiValidation, "M?? khuy???n m??i ???? t???n t???i");
			return ;
		}	

		activeSuccessInput(maKhuyenMaiInput, maKhuyenMaiValidation);
		khuyenMaiCache.setMaKhuyenMai(maKhuyenMai);
		isValidMa = true;
	}
	
	private void validationTenKhuyenMai() {
		if(isValidTen) {
			return;
		}
		String tenKhuyenMai = tenKhuyenMaiInput.getText().trim();

		if(tenKhuyenMai.length() == 0) {
			activeValidationInput(tenKhuyenMaiInput, tenKhuyenMaiValidation, "B???n ch??a nh???p d??? li???u");
			return ;
		}

		if(kmBLL.kiemTraTenKhuyenMai(tenKhuyenMai)) {
			activeValidationInput(tenKhuyenMaiInput, tenKhuyenMaiValidation, "T??n khuy???n m??i ???? t???n t???i");
			return ;
		}

		activeSuccessInput(tenKhuyenMaiInput, tenKhuyenMaiValidation);
		khuyenMaiCache.setTenKhuyenMai(tenKhuyenMai);
		isValidTen = true;
	}

	private void validationNgayBatDau() {
		if(isValidNgayBatDau) {
			return;
		}
		String dayFromStr = ngayBatDauInput.getText().trim();

		if(dayFromStr.length() == 0) {
			activeValidationInput(ngayBatDauInput, ngayBatDauValidation, "B???n ch??a nh???p d??? li???u");
			return ;
		}

		// kiem tra xem du lieu nhap co dung cu phap ngay thang nam khong (yyyy-mm-dd)
		Date dateFrom = new Date();
		try {
			dateFrom = new Date(dayFromStr);
		}
		catch(Exception ex) {
			activeValidationInput(ngayBatDauInput, ngayBatDauValidation, ex.getMessage());
			return ;
		}

		khuyenMaiCache.setNgayBatDau(dateFrom.toString());
		activeSuccessInput(ngayBatDauInput, ngayBatDauValidation);
		isValidNgayBatDau = true;
	}

	private void validationNgayKetThuc() {
		if(isValidNgayKetThuc) {
			return;
		}
		String dayToStr = ngayKetThucInput.getText().trim();

		if(dayToStr.length() == 0) {
			activeValidationInput(ngayKetThucInput, ngayKetThucValidation, "B???n ch??a nh???p d??? li???u");
			return ;
		}

		// kiem tra xem du lieu nhap co dung cu phap ngay thang nam khong (yyyy-mm-dd)
		Date dateTo = new Date();
		try {
			dateTo = new Date(dayToStr);
		}
		catch(Exception ex) {
			activeValidationInput(ngayKetThucInput, ngayKetThucValidation, ex.getMessage());
			return ;
		}

		boolean isErrorNgayBatDau = false;
		Date dateFrom = new Date();
		try {
			dateFrom = new Date(ngayBatDauInput.getText());
		}
		catch(Exception ex) {
			isErrorNgayBatDau = true;	
		}

		if(!isErrorNgayBatDau && Date.countDayInTwoDate(dateFrom, dateTo) < 1) {
			activeValidationInput(ngayKetThucInput, ngayKetThucValidation, "ph???i l???n h??n so v???i th???i gian c???a 'ng??y b???t ?????u:'");
			return ;
		}

		khuyenMaiCache.setNgayKetThuc(dateTo.toString());
		activeSuccessInput(ngayKetThucInput, ngayKetThucValidation);
		isValidNgayKetThuc = true;
	}

	private void refreshFormKhuyenMai() {
		khuyenMaiCache = new KhuyenMaiDTO();
		hasInitKhuyenMai = false;

		activeEnableInput(maKhuyenMaiInput, maKhuyenMaiValidation);
		activeDisableInput(tenKhuyenMaiInput, tenKhuyenMaiValidation);
		activeDisableInput(ngayBatDauInput, ngayBatDauValidation);
		activeDisableInput(ngayKetThucInput, ngayKetThucValidation);
		isValidMa = false;
		isValidTen = false;
		isValidNgayBatDau = false;
		isValidNgayKetThuc = false;
	}

	private void refreshAllForm() {
		mangChiTietKhuyenMai = new ArrayList<ChiTietKhuyenMaiDTO>();
		refreshFormKhuyenMai();
		refreshFormChiTietKhuyenMai();
		refreshDataFormChiTietKhuyenMai();
		refreshTable();
		refreshDataTable();
	}
	// ============================= FORM CHI TIET KHUYEN MAI =================================================
	private void initComponentInChiTietKhuyenMaiForm() {
		FlexBox flexBoxForPanel = new FlexBox(chiTietKhuyenMaiFormPanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForPanel.setPadding(10);

		JLabel titleLabelTalbe = new JLabel("Th??ng tin chi ti???t khuy???n m??i");
		setPropertyForTitleLabel(titleLabelTalbe);
		flexBoxForPanel.setPosition(titleLabelTalbe, 0, 0);

		JPanel inputPanel = new JPanel(null);
		inputPanel.setBackground(khuyenMaiFormPanel.getBackground());
		flexBoxForPanel.setPositionWithinPercentSize(60, 10, inputPanel);

		FlexBox flexBoxForInput = new FlexBox(inputPanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForInput.setPadding(10);

		JLabel labelmaKhuyenMai = new JLabel("M?? khuy???n m??i");
		setPropertyForLabel(labelmaKhuyenMai);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelmaKhuyenMai);
		setPropertyForInputField(maChiTietKhuyenMaiInput);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, maChiTietKhuyenMaiInput);
		setPropertyForValidation(maChiTietKhuyenMaiValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, maChiTietKhuyenMaiValidation);

		JLabel labelTenKhuyenMai = new JLabel("t??? l??? khuy???n m??i(%)");
		setPropertyForLabel(labelTenKhuyenMai);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelTenKhuyenMai);
		setPropertyForInputField(phanTramKhuyenMaiInput);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, phanTramKhuyenMaiInput);
		setPropertyForValidation(phanTramKhuyenMaiValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, phanTramKhuyenMaiValidation);

		JLabel labelNgayBatDau = new JLabel("M?? s???n ph???m");
		setPropertyForLabel(labelNgayBatDau);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelNgayBatDau);
		
		JPanel maSanPhamPanel = new JPanel(null);
		maSanPhamPanel.setBackground(chiTietKhuyenMaiFormPanel.getBackground());
		maSanPhamPanel.setSize(0, 35);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, maSanPhamPanel);
		FlexBox flexBoxForMaSanPham = new FlexBox(maSanPhamPanel, FlexBox.DIRECTION_ROW);
		setPropertyForInputField(maSanPhamInput);
		flexBoxForMaSanPham.setPositionWithinPercentSize(90, 0, maSanPhamInput);
		chooseMaSanPham.setCursor(new Cursor(Cursor.HAND_CURSOR));
		chooseMaSanPham.setBackground(chiTietKhuyenMaiFormPanel.getBackground());
		flexBoxForMaSanPham.setPositionWithinPercentSize(100, 5, chooseMaSanPham);
		setPropertyForValidation(maSanPhamValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, maSanPhamValidation);

		JPanel buttonPanel = new JPanel(null);
		buttonPanel.setBackground(khuyenMaiFormPanel.getBackground());
		flexBoxForPanel.setPositionWithinPercentSize(100, 10, buttonPanel);

		FlexBox flexBoxForButton = new FlexBox(buttonPanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForButton.setPadding(10);
		setPropertyForButton(initChiTietKhuyenMaiButton);
		flexBoxForButton.setPosition(initChiTietKhuyenMaiButton, 0, 0);
		setPropertyForButton(addChiTietKhuyenMaiButton);
		flexBoxForButton.setPosition(addChiTietKhuyenMaiButton, 15, 0);

		activeDisableInput(maChiTietKhuyenMaiInput, maChiTietKhuyenMaiValidation);
		activeDisableInput(phanTramKhuyenMaiInput, phanTramKhuyenMaiValidation);
		activeDisableInput(maSanPhamInput, maSanPhamValidation);
		createWindowChooseMaSanPham();
		chooseMaSanPham.setEnabled(false);
		AbstractDocument documentPhanTramKhuyenMai = (AbstractDocument)phanTramKhuyenMaiInput.getDocument();
		documentPhanTramKhuyenMai.setDocumentFilter(new NumberDocumentFilter());
		AbstractDocument documentMaSanPham = (AbstractDocument)maSanPhamInput.getDocument();
		documentMaSanPham.setDocumentFilter(new UppercaseDocumentFilter());
	}

	private void addEventToChiTietKhuyenMaiForm() {
		initChiTietKhuyenMaiButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleEventInitChiTietKhuyenMai();
			}
		});

		addChiTietKhuyenMaiButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleEventAddChiTietKhuyenMai();
			}
		});

		chooseMaSanPham.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				displayRegistry.subFrameRegistry(frameChooseMaSanPham);
				refreshFrameChooseMaSanPham();
			}
		});
		phanTramKhuyenMaiInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					validationPhanTramKhuyenMai();
				}
			}
		});	
		maSanPhamInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					validationMaSanPham();
				}
			}
		});	
	}

	private void handleEventInitChiTietKhuyenMai() {
		if(!hasInitKhuyenMai) {
			showMessage("B???n ch??a kh???i t???o khuy???n m??i");
			return;
		}

		activeSuccessInput(maChiTietKhuyenMaiInput, maChiTietKhuyenMaiValidation);	
		maChiTietKhuyenMaiInput.setText(khuyenMaiCache.getMaKhuyenMai());

		activeEnableInput(phanTramKhuyenMaiInput, phanTramKhuyenMaiValidation);
		activeEnableInput(maSanPhamInput, maSanPhamValidation);
		chooseMaSanPham.setEnabled(true);
		hasInitChiTietKhuyenMai = true;
		hasModifyChiTietKhuyenMai = false;
		chiTietKhuyenMaiCache = new ChiTietKhuyenMaiDTO();
		chiTietKhuyenMaiCache.setMaKhuyenMai(khuyenMaiCache.getMaKhuyenMai());
	}

	private void handleEventAddChiTietKhuyenMai() {
		if(!hasInitChiTietKhuyenMai) {
			showMessage("Ban ch??a kh???i t???o chi ti???t khuy???n m??i");
			return;
		}

		validationPhanTramKhuyenMai();
		validationMaSanPham();

		boolean isValidForm = isValidMaSanPham && isValidPhanTram;
		if(!isValidForm) {
			return;
		}

		mangChiTietKhuyenMai.add(chiTietKhuyenMaiCache);
		showMessage("Th??m chi ti???t khuy???n m??i th??nh c??ng");
		refreshFormChiTietKhuyenMai();
		refreshDataFormChiTietKhuyenMai();
		hasDataChiTietKhuyenMai = true;
		hasInitChiTietKhuyenMai = false;
		refreshTable();
	}

	private void handleEventModifyChiTietKhuyenMai() {
		if(!hasDataChiTietKhuyenMai) {
			showMessage("B???n ch??a c?? d??? li???u chi ti???t Khuy???n m??i vui l??ng th??m m???i");
			return;
		}

		if(!hasModifyChiTietKhuyenMai) {
			showMessage("Vui l??ng ch???n 1 d??? li???u b??n b???ng ????? c?? th??? ti???n h??nh ch???nh s???a d??? li???u");
			return;
		}

		validationMaSanPham();
		validationPhanTramKhuyenMai();
		boolean isValidForm = isValidMaSanPham && isValidPhanTram;
		if(!isValidForm) {
			return;
		}

		boolean isSamePhanTram = chiTietKhuyenMaiCacheModify.getPhanTramKhuyenMai() == chiTietKhuyenMaiCache.getPhanTramKhuyenMai();
		boolean isSameMaSanPham = chiTietKhuyenMaiCacheModify.getMaSanPham().equals(chiTietKhuyenMaiCache.getMaSanPham());

		if(isSamePhanTram && isSameMaSanPham) {
			activeEnableInput(phanTramKhuyenMaiInput, phanTramKhuyenMaiValidation);
			phanTramKhuyenMaiInput.setText(Integer.toString(chiTietKhuyenMaiCacheModify.getPhanTramKhuyenMai()));

			activeEnableInput(maSanPhamInput, maSanPhamValidation);
			maSanPhamInput.setText(chiTietKhuyenMaiCacheModify.getMaSanPham());
			chooseMaSanPham.setEnabled(true);
			showMessage("B???n ch??a ch???nh s???a th??ng tin chi ti???t s???n ph???m");
			return;
		}

		mangChiTietKhuyenMai.set(indexSelectionRow, chiTietKhuyenMaiCache);
		showMessage("B???n ch???nh s???a th??ng tin chi ti???t khuy???n m??i th??nh c??ng");
		refreshFormChiTietKhuyenMai();
		refreshDataFormChiTietKhuyenMai();
		hasModifyChiTietKhuyenMai = false;
		indexSelectionRow = -1;
		refreshTable();
	}

	private void handleEventDeleteChiTietKhuyenMai() {
		if(!hasDataChiTietKhuyenMai) {
			showMessage("B???n ch??a c?? d??? li???u chi ti???t khuy???n m??i l??ng th??m m???i");
			return;
		}

		if(indexSelectionRow == -1) {
			showMessage("Vui l??ng ch???n 1 d??? li???u b??n b???ng ????? c?? th??? ti???n h??nh x??a d??? li???u");
			return;
		}

		mangChiTietKhuyenMai.remove(indexSelectionRow);
		showMessage("B???n x??a th??ng tin chi ti???t khuy???n m??i th??nh c??ng");
		refreshFormChiTietKhuyenMai();
		refreshDataFormChiTietKhuyenMai();
		indexSelectionRow = -1;
		hasModifyChiTietKhuyenMai = false;
		refreshTable();
	}

	private void chuyenDuLieuSangForm(ChiTietKhuyenMaiDTO data) {
		refreshDataFormChiTietKhuyenMai();
		chiTietKhuyenMaiCacheModify = data.clone();	

		chiTietKhuyenMaiCache = new ChiTietKhuyenMaiDTO();
		chiTietKhuyenMaiCache.setMaKhuyenMai(chiTietKhuyenMaiCacheModify.getMaKhuyenMai());
		hasModifyChiTietKhuyenMai = true;
		hasInitChiTietKhuyenMai = false;

		activeSuccessInput(maChiTietKhuyenMaiInput, maChiTietKhuyenMaiValidation);	
		maChiTietKhuyenMaiInput.setText(chiTietKhuyenMaiCacheModify.getMaKhuyenMai());

		activeEnableInput(phanTramKhuyenMaiInput, phanTramKhuyenMaiValidation);
		phanTramKhuyenMaiInput.setText(Integer.toString(chiTietKhuyenMaiCacheModify.getPhanTramKhuyenMai()));

		activeEnableInput(maSanPhamInput, maSanPhamValidation);
		maSanPhamInput.setText(chiTietKhuyenMaiCacheModify.getMaSanPham());

		chooseMaSanPham.setEnabled(true);
	}

	private void validationPhanTramKhuyenMai() {
		if(isValidPhanTram) {
			return;
		}
		String phanTramKhuyenMaiString = phanTramKhuyenMaiInput.getText().trim();	

		if(phanTramKhuyenMaiString.length() == 0) {
			activeValidationInput(phanTramKhuyenMaiInput, phanTramKhuyenMaiValidation, "B???n ch??a nh???p d??? li???u");
			return ;
		}

		int phanTramKhuyenMai = 0;
		boolean isErrorParseNumber = false;
		try {
			phanTramKhuyenMai = Integer.parseInt(phanTramKhuyenMaiString);
		}
		catch(Exception ex) {
			isErrorParseNumber = true;
		}

		if(phanTramKhuyenMai < 1 || phanTramKhuyenMai > 100) {
			isErrorParseNumber = true;
		}

		if(isErrorParseNumber) {
			activeValidationInput(phanTramKhuyenMaiInput, phanTramKhuyenMaiValidation, "D??? li???u ph???i la s??? trong kho???ng t??? 1 ?????n 100");
			return ;
		}
		
		activeSuccessInput(phanTramKhuyenMaiInput, phanTramKhuyenMaiValidation);
		chiTietKhuyenMaiCache.setPhanTramKhuyenMai(phanTramKhuyenMai);
		isValidPhanTram = true;
		return ;
	}

	private void validationMaSanPham() {
		if(isValidMaSanPham) {
			return;
		}
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

		boolean isExistInList = false;
		for(ChiTietKhuyenMaiDTO ctkm : mangChiTietKhuyenMai) {
			if(ctkm.getMaSanPham().equals(maSanPham)) {
				isExistInList = true;
				break;
			}
		}

		if(isExistInList && !hasModifyChiTietKhuyenMai) {
			activeValidationInput(maSanPhamInput, maSanPhamValidation, "M?? s???n ph???m ???? c?? trong danh s??ch");
			return ;
		}

		activeSuccessInput(maSanPhamInput, maSanPhamValidation);
		maSanPhamInput.setText(product.getMaSanPham());
		maSanPhamInput.setToolTipText("T??n s???n ph???m: " + product.getTenSanPham());
		chiTietKhuyenMaiCache.setMaSanPham(product.getMaSanPham());
		chooseMaSanPham.setEnabled(false);
		isValidMaSanPham = true;
	}

	private void refreshFormChiTietKhuyenMai() {
		activeDisableInput(maChiTietKhuyenMaiInput, maChiTietKhuyenMaiValidation);
		activeDisableInput(phanTramKhuyenMaiInput, phanTramKhuyenMaiValidation);
		activeDisableInput(maSanPhamInput, maSanPhamValidation);
		maSanPhamInput.setToolTipText("");
		chooseMaSanPham.setEnabled(false);
	}

	private void refreshDataFormChiTietKhuyenMai() {
		hasInitChiTietKhuyenMai = false;
		hasModifyChiTietKhuyenMai = false;
		hasDataChiTietKhuyenMai = false;
		isValidPhanTram = false;
		isValidMaSanPham = false;
		chiTietKhuyenMaiCacheModify = null;
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
		tableFrame = new AppTable(columnsName, sizePerColumn, scrollSearchTable.getSize());
		scrollSearchTable.getViewport().add(tableFrame);

		DefaultTableModel tableFrameModel = (DefaultTableModel)tableFrame.getModel();
		setPropertyForButton(submitButton);
		flexBoxForPanel.setPosition(FlexBox.ALIGMENT_CENTER, 15, submitButton);

		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tableFrame.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(frameChooseMaSanPham, "B???n ch??a ch???n s???n ph???m, vui l??ng nh???n chu???t v??o 1 d??ng d??? li???u ??? b???ng ????? ch???n s???n ph???m");
					return;
				}

				String maSanPham = tableFrameModel.getValueAt(tableFrame.getSelectedRow(), 0).toString();
				maSanPhamInput.setText(maSanPham);
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
					JOptionPane.showMessageDialog(frameChooseMaSanPham, "Kh??ng t??m th???y d??? li???u");
					return;
				}

				tableFrameModel.setRowCount(0);
				for(ProductDTO product : result) {
					tableFrameModel.addRow(product.toArrayString());
				}
			}
		});
	}

	private void refreshFrameChooseMaSanPham() {
		searchMaSanPhamInput.setText("");

		DefaultTableModel tableFrameModel = (DefaultTableModel)tableFrame.getModel();
		for(ProductDTO product : ProductBLL.getInstance().getAllProduct()) {
			tableFrameModel.addRow(product.toArrayString());
		}
	}

	// ====================================== BANG CHI TIET KHUYEN MAI =====================================

	private void initComponentInChiTietKhuyenMaiTable() {
		FlexBox flexBoxForPanel = new FlexBox(chiTietKhuyenMaiTablePanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForPanel.setPadding(10);

		JLabel titleLabelTalbe = new JLabel("Chi ti???t khuy???n m??i ???? th??m");
		setPropertyForTitleLabel(titleLabelTalbe);
		flexBoxForPanel.setPosition(titleLabelTalbe, 0, 0);

		JPanel tablePanel = new JPanel(null);
		tablePanel.setBackground(chiTietKhuyenMaiTablePanel.getBackground());
		flexBoxForPanel.setPositionWithinPercentSize(60, 10, tablePanel);

		FlexBox flexBoxForTable = new FlexBox(tablePanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForTable.setPadding(10);

		JScrollPane scroll = new JScrollPane();
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		flexBoxForTable.setPositionWithinPercentSize(100, 0, scroll);	
		String columnNames[] = {"M?? khuy???n m??i", "T??? l??? khuy???n m??i", "M?? s???n ph???m"};
		int columnSize[] = {35, 35, 30};
		tableChiTietKhuyenMai = new AppTable(columnNames, columnSize, scroll.getSize());
		scroll.getViewport().add(tableChiTietKhuyenMai);

		JPanel buttonPanel = new JPanel(null);
		buttonPanel.setBackground(khuyenMaiFormPanel.getBackground());
		flexBoxForPanel.setPositionWithinPercentSize(100, 10, buttonPanel);

		FlexBox flexBoxForButton = new FlexBox(buttonPanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForButton.setPadding(10);
		setPropertyForButton(deleteChiTietKhuyenMaiButton);
		flexBoxForButton.setPosition(deleteChiTietKhuyenMaiButton, 0, 0);
		setPropertyForButton(modifyChiTietKhuyenMaiButton);
		flexBoxForButton.setPosition(modifyChiTietKhuyenMaiButton, 15, 0);
	}

	private void addEventToChiTietKhuyenMaiTable() {
		tableChiTietKhuyenMai.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				handleClickRowTable();
			}
		});

		modifyChiTietKhuyenMaiButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleEventModifyChiTietKhuyenMai();
			}
		});

		deleteChiTietKhuyenMaiButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleEventDeleteChiTietKhuyenMai();
			}
		});
	}

	private void handleClickRowTable() {
		int index = tableChiTietKhuyenMai.getSelectedRow();
		
		if(index == -1) {
			return;
		}

		if(index == indexSelectionRow) {
			return;
		}

		indexSelectionRow = index;

		chuyenDuLieuSangForm(mangChiTietKhuyenMai.get(indexSelectionRow));
	}

	private void refreshTable() {
		DefaultTableModel tableModel = (DefaultTableModel)tableChiTietKhuyenMai.getModel();
		tableModel.setRowCount(0);

		for(ChiTietKhuyenMaiDTO ctkm : mangChiTietKhuyenMai) {
			tableModel.addRow(ctkm.toArrayString());
		}
	}

	private void refreshDataTable() {
		indexSelectionRow = -1;
	}

	// ====================================================================================================
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
		input.setBorder(null);
		input.setText("");
		input.setEditable(false);
		validation.setVisible(false);
	}

	private void activeEnableInput(JTextField input, JLabel validation) {
		input.setText("");
		input.setBackground(Color.WHITE);
		input.setEditable(true);
		input.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Constants.COLOR_TEXTFIELD_TYPE_BORDER), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		validation.setVisible(false);
	}

	private void activeSuccessInput(JTextField input, JLabel validation) {
		input.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Constants.COLOR_TEXTFIELD_SUCCESS_BORDER), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		input.setBackground(Color.WHITE);
		input.setEditable(false);
		validation.setVisible(false);
	}

	private void activeValidationInput(JTextField input, JLabel validation, String text) {
		input.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Constants.COLOR_TEXTFIELD_DANGER_BORDER), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		input.setEditable(true);
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
