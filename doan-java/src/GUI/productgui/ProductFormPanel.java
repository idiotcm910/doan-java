package GUI.productgui;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import javax.swing.JFileChooser;

import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;
import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;

import components.ImageFilter;
import components.AppTable;
import components.TMoneyField;
import components.UppercaseDocumentFilter;
import components.ReadSettings;
import common.FlexBox;
import common.Util;
import common.Constants;
import common.Date;
import common.FrameDisplayRegistry;

import DTO.ProductDTO;
import DTO.LoaiSanPhamDTO;
import DTO.NhaSanXuatDTO;
import BLL.ProductBLL;
import BLL.LoaiSanPhamBLL;
import BLL.NhaSanXuatBLL;

class ProductFormPanel extends JPanel {
	// product form
	private JPanel productFormPanel;

	private ProductBLL productBLL;
	private LoaiSanPhamBLL loaiSanPhamBLL;
	private NhaSanXuatBLL nhaSanXuatBLL;
	private ProductTablePanel tablePanel;
	private FrameDisplayRegistry displayRegistry = FrameDisplayRegistry.getInstance();
	private Font defaultFont;

	private JTextField maSanPhamInput;
	private JLabel maSanPhamValidation;
	private boolean isValidMaSanPham;
	private JTextField tenSanPhamInput;
	private JLabel tenSanPhamValidation;
	private boolean isValidTenSanPham;
	private TMoneyField donGiaInput;
	private JLabel donGiaValidation;
	private boolean isValidDonGia;

	private JTextField loaiSanPhamInput;
	private JLabel loaiSanPhamValidation;
	private boolean isValidLoaiSanPham;
	private JButton chooseLoaiSanPham;
	private JFrame frameChooseLoaiSanPham;
	private AppTable tableFrameLoaiSanPham;
	private JTextField searchLoaiSanPhamInput;


	private JTextField ngaySanXuatInput;
	private JLabel ngaySanXuatValidation;
	private boolean isValidNgaySanXuat;
	private JTextField hanSuDungInput;
	private JLabel hanSuDungValidation;
	private boolean isValidHanSuDung;

	private JTextField maNhaSanXuatInput;
	private JLabel maNhaSanXuatValidation;
	private boolean isValidMaNhaSanXuat;
	private JButton chooseMaNhaSanXuat;
	private JFrame frameChooseMaNhaSanXuat;
	private AppTable tableFrameMaNhaSanXuat;
	private JTextField searchMaNhaSanXuatInput;

	private JButton chooseImage;
	private JLabel productImage;
	private JLabel imageValidation;
	private boolean isValidImage;

	private ProductDTO productCache;
	private ProductDTO productModifyCache;

	private JButton addProductButton;
	private JButton refreshProductButton;
	private JButton deleteProductButton;
	private JButton modifyProductButton;

	private boolean hasInitProduct;
	private boolean hasModifyProduct;
	private boolean wasModifyImage;

	private Path pathImage;
	private String pathImageProduct;
	public ProductFormPanel() {
		setLayout(null);
		initVariable();
	}

	public void setTablePanel(ProductTablePanel panel) {
		this.tablePanel = panel;
	}

	private void initVariable() {
		productBLL = ProductBLL.getInstance();
		loaiSanPhamBLL = LoaiSanPhamBLL.getInstance();
		nhaSanXuatBLL = NhaSanXuatBLL.getInstance();
		defaultFont = Util.getFont("Roboto", "NORMAL", 18);
		pathImageProduct = ReadSettings.getInstance().getConfig("pathProductImage");

		productFormPanel = new JPanel(null);
		maSanPhamInput = new JTextField();
		maSanPhamValidation = new JLabel();
		isValidMaSanPham = false;
		tenSanPhamInput = new JTextField();
		tenSanPhamValidation = new JLabel();
		isValidTenSanPham = false;
		donGiaInput = new TMoneyField(8);
		donGiaValidation = new JLabel();
		isValidDonGia = false;
		loaiSanPhamInput = new JTextField();
		loaiSanPhamValidation = new JLabel();
		isValidLoaiSanPham = false;
		frameChooseLoaiSanPham = new JFrame();
		chooseLoaiSanPham = new JButton("...");
		searchLoaiSanPhamInput = new JTextField();
		ngaySanXuatInput = new JTextField();
		ngaySanXuatValidation = new JLabel();
		isValidNgaySanXuat = false;
		hanSuDungInput = new JTextField();
		hanSuDungValidation = new JLabel();
		isValidHanSuDung = false;
		maNhaSanXuatInput = new JTextField();
		maNhaSanXuatValidation = new JLabel();
		isValidMaNhaSanXuat = false;
		frameChooseMaNhaSanXuat = new JFrame();
		chooseMaNhaSanXuat = new JButton("...");
		searchMaNhaSanXuatInput = new JTextField();
		imageValidation = new JLabel();
		chooseImage = new JButton("Chọn ảnh");
		isValidImage = false;
		addProductButton = new JButton("Thêm mới sản phẩm");
		refreshProductButton = new JButton("Làm mới sản phẩm");
		deleteProductButton = new JButton("Xóa sản phẩm");
		modifyProductButton = new JButton("Chỉnh sửa sản phẩm");
	}

	public void initComponentInPanel() {
		initComponentInProductForm();
		addEventToForm();
	}

	private void initComponentInProductForm() {
		FlexBox flexBoxForPanel = new FlexBox(this, FlexBox.DIRECTION_COLUMN);
		flexBoxForPanel.setPadding(10);

		JLabel titleLabelTalbe = new JLabel("Thông tin sản phẩm");
		setPropertyForTitleLabel(titleLabelTalbe);
		flexBoxForPanel.setPosition(titleLabelTalbe, 0, 0);

		JPanel inputPanel = new JPanel(null);
		inputPanel.setBackground(this.getBackground());
		flexBoxForPanel.setPositionWithinPercentSize(79, 10, inputPanel);

		FlexBox flexBoxForInput = new FlexBox(inputPanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForInput.setPadding(10);

		JLabel labelmaSanPham = new JLabel("Mã sản phẩm");
		setPropertyForLabel(labelmaSanPham);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelmaSanPham);
		setPropertyForInputField(maSanPhamInput);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, maSanPhamInput);
		setPropertyForValidation(maSanPhamValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, maSanPhamValidation);

		JLabel labelTenSanPham = new JLabel("Tên sản phẩm");
		setPropertyForLabel(labelTenSanPham);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelTenSanPham);
		setPropertyForInputField(tenSanPhamInput);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, tenSanPhamInput);
		setPropertyForValidation(tenSanPhamValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, tenSanPhamValidation);

		JLabel labelDonGia = new JLabel("Đơn giá");
		setPropertyForLabel(labelDonGia);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelDonGia);
		setPropertyForInputField(donGiaInput);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, donGiaInput);
		setPropertyForValidation(donGiaValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, donGiaValidation);

		JLabel labelLoaiSanPham = new JLabel("Loại sản phẩm");
		setPropertyForLabel(labelLoaiSanPham);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelLoaiSanPham);
		JPanel loaiSanPhamPanel = new JPanel(null);
		loaiSanPhamPanel.setBackground(this.getBackground());
		loaiSanPhamPanel.setSize(0, 26);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, loaiSanPhamPanel);
		FlexBox flexBoxForLoaiSanPham = new FlexBox(loaiSanPhamPanel, FlexBox.DIRECTION_ROW);
		setPropertyForInputField(loaiSanPhamInput);
		flexBoxForLoaiSanPham.setPositionWithinPercentSize(90, 0, loaiSanPhamInput);
		chooseLoaiSanPham.setCursor(new Cursor(Cursor.HAND_CURSOR));
		chooseLoaiSanPham.setBackground(this.getBackground());
		flexBoxForLoaiSanPham.setPositionWithinPercentSize(100, 5, chooseLoaiSanPham);
		setPropertyForValidation(loaiSanPhamValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, loaiSanPhamValidation);

		JLabel labelNgaySanXuat = new JLabel("Ngày sản xuất");
		setPropertyForLabel(labelNgaySanXuat);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelNgaySanXuat);
		setPropertyForInputField(ngaySanXuatInput);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, ngaySanXuatInput);
		setPropertyForValidation(ngaySanXuatValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, ngaySanXuatValidation);

		JLabel labelHanSuDung = new JLabel("Hạn sử dụng");
		setPropertyForLabel(labelHanSuDung);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelHanSuDung);
		setPropertyForInputField(hanSuDungInput);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, hanSuDungInput);
		setPropertyForValidation(hanSuDungValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, hanSuDungValidation);

		JLabel labelMaNhaSanXuat = new JLabel("Mã nhà sản xuất");
		setPropertyForLabel(labelMaNhaSanXuat);
		JPanel maNhaSanXuatPanel = new JPanel(null);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelMaNhaSanXuat);
		maNhaSanXuatPanel.setBackground(this.getBackground());
		maNhaSanXuatPanel.setSize(0, 26);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, maNhaSanXuatPanel);
		FlexBox flexBoxForMaNhaSanXuat = new FlexBox(maNhaSanXuatPanel, FlexBox.DIRECTION_ROW);
		setPropertyForInputField(maNhaSanXuatInput);
		flexBoxForMaNhaSanXuat.setPositionWithinPercentSize(90, 0, maNhaSanXuatInput);
		chooseMaNhaSanXuat.setCursor(new Cursor(Cursor.HAND_CURSOR));
		chooseMaNhaSanXuat.setBackground(this.getBackground());
		flexBoxForMaNhaSanXuat.setPositionWithinPercentSize(100, 5, chooseMaNhaSanXuat);
		setPropertyForValidation(maNhaSanXuatValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, maNhaSanXuatValidation);


		JLabel labelImage = new JLabel("Ảnh sản phẩm");
		setPropertyForLabel(labelImage);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, labelImage);
		JPanel imagePanel = new JPanel(null);
		imagePanel.setBackground(this.getBackground());
		imagePanel.setSize(0, 75);
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, imagePanel);
		FlexBox flexBoxForImage = new FlexBox(imagePanel, FlexBox.DIRECTION_ROW);
		productImage = new JLabel("", SwingConstants.CENTER);
		setPropertyForLabelImage(productImage);
		flexBoxForImage.setPositionWithinPercentSize(35, 0, productImage);
		setPropertyForButtonImage(chooseImage);
		flexBoxForImage.setPosition(chooseImage, 0, 15);
		setPropertyForValidation(imageValidation);	
		flexBoxForInput.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 4, imageValidation);

		JPanel buttonPanel = new JPanel(null);
		buttonPanel.setBackground(this.getBackground());
		flexBoxForPanel.setPositionWithinPercentSize(100, 10, buttonPanel);

		FlexBox flexBoxForButton = new FlexBox(buttonPanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForButton.setPadding(10);

		JPanel sideLeftButtonPanel = new JPanel(null);
		sideLeftButtonPanel.setBackground(this.getBackground());
		sideLeftButtonPanel.setSize(0, 45);
		flexBoxForButton.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 0, sideLeftButtonPanel);
		FlexBox flexBoxForLeft = new FlexBox(sideLeftButtonPanel, FlexBox.DIRECTION_ROW);
		setPropertyForButton(addProductButton);
		flexBoxForLeft.setPositionWithinPercentSize(49, 0, addProductButton);
		setPropertyForButton(refreshProductButton);
		flexBoxForLeft.setPositionWithinPercentSize(100, 15, refreshProductButton);

		JPanel sideRightButtonPanel = new JPanel(null);
		sideRightButtonPanel.setBackground(this.getBackground());
		sideRightButtonPanel.setSize(0, 45);
		flexBoxForButton.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 15, sideRightButtonPanel);
		FlexBox flexBoxForRight = new FlexBox(sideRightButtonPanel, FlexBox.DIRECTION_ROW);
		setPropertyForButton(modifyProductButton);
		flexBoxForRight.setPositionWithinPercentSize(49, 0, modifyProductButton);

		createWindowChooseLoaiSanPham();
		createWindowChooseMaNhaSanXuat();
		refreshFormSanPham();

		AbstractDocument document = (AbstractDocument)maSanPhamInput.getDocument();
		document.setDocumentFilter(new UppercaseDocumentFilter());
		AbstractDocument documentLoaiSanPham = (AbstractDocument)loaiSanPhamInput.getDocument();
		documentLoaiSanPham.setDocumentFilter(new UppercaseDocumentFilter());
		AbstractDocument documentNhaSanXuat = (AbstractDocument)maNhaSanXuatInput.getDocument();
		documentNhaSanXuat.setDocumentFilter(new UppercaseDocumentFilter());
	}

	private void addEventToForm() {
		addProductButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleAddProduct();	
			}
		});

		refreshProductButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleRefreshProduct();	
			}
		});

		modifyProductButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleModifyProduct();	
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
		tenSanPhamInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					validationTenSanPham();
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
		loaiSanPhamInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					validationLoaiSanPham();
				}
			}
		});
		ngaySanXuatInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					validationNgaySanXuat();
				}
			}
		});
		hanSuDungInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					validationHanSuDung();
				}
			}
		});
		maNhaSanXuatInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					validationNhaSanXuat();
				}
			}
		});

		chooseLoaiSanPham.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				displayRegistry.subFrameRegistry(frameChooseLoaiSanPham);
				refreshFrameChooseLoaiSanPham();
			}
		});

		chooseMaNhaSanXuat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				displayRegistry.subFrameRegistry(frameChooseMaNhaSanXuat);
				refreshFrameChooseMaNhaSanXuat();
			}
		});

		chooseImage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleEventChooseImage();
			}
		});
	}

	private void handleAddProduct() {
		if(!hasInitProduct) {
			showMessage("Bạn chưa tạo mới sản phẩm");
			return;
		}

		validationMaSanPham();
		validationTenSanPham();
		validationLoaiSanPham();
		validationNhaSanXuat();
		validationNgaySanXuat();
		validationHanSuDung();
		validationDonGia();
		validationImage();
		boolean isValidForm = isValidMaSanPham && isValidTenSanPham && isValidLoaiSanPham && isValidMaNhaSanXuat && isValidNgaySanXuat && isValidHanSuDung && isValidDonGia && isValidImage;

		if(!isValidForm) {
			return;
		}

		productCache.setSoLuong(0);
		productBLL.addProduct(productCache);

		copyImage();

		showMessage("Thêm sản phẩm thành công, vui lòng nhâp phiếu nhập hàng để có thể nhập số lượng sản phẩm");
		refreshFormSanPham();
		tablePanel.initValuesTable();
	}

	private void handleRefreshProduct() {
		refreshFormSanPham();
	}

	private void handleModifyProduct() {
		if(!hasModifyProduct) {
			showMessage("Vui lòng chọn 1 dòng dữ liệu bên bảng để có thể tiến hành chỉnh sửa thông tin");
			return;
		}

		if(productCache.equals(productModifyCache) && !wasModifyImage) {
			showMessage("Bạn chưa chỉnh sửa thông tin dữ liệu");
			return;
		}	
	
		validationTenSanPham();
		validationLoaiSanPham();
		validationNhaSanXuat();
		validationNgaySanXuat();
		validationHanSuDung();
		validationDonGia();
		validationImage();
		boolean isValidForm = isValidMaSanPham && isValidTenSanPham && isValidLoaiSanPham && isValidMaNhaSanXuat && isValidNgaySanXuat && isValidHanSuDung && isValidDonGia && isValidImage;

		if(!isValidForm) {
			return;
		}

		if(wasModifyImage) {
			copyImage();
		}

		productBLL.updateProduct(productCache);
		showMessage("Chỉnh sửa thông tin dữ liệu có mã: " + productCache.getMaSanPham() + " thành công");
		refreshFormSanPham();
		tablePanel.initValuesTable();
	}

	private void copyImage() {
		String pathDirImage = pathImageProduct + productCache.getImageUrl();

		if(hasModifyProduct && !productModifyCache.getImageUrl().equals("NULL")) {
			File file = new File(pathImageProduct + productModifyCache.getImageUrl());
			if(file.exists()) {
				file.delete();
			}
		}

		Path dest = Paths.get(pathDirImage);
		try {
			Files.copy(pathImage, dest, StandardCopyOption.REPLACE_EXISTING);
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
	}

	public void chuyenDuLieu(ProductDTO product) {
		refreshFormSanPham();

		productModifyCache = product;
		productCache = product.clone();

		activeSuccessInput(maSanPhamInput, maSanPhamValidation);
		maSanPhamInput.setText(product.getMaSanPham());
		activeEnableInput(tenSanPhamInput, tenSanPhamValidation);
		tenSanPhamInput.setText(product.getTenSanPham());
		activeEnableInput(donGiaInput, donGiaValidation);
		donGiaInput.setText(product.getDonGiaSanPham());
		activeEnableInput(loaiSanPhamInput, loaiSanPhamValidation);
		loaiSanPhamInput.setText(product.getLoaiSanPham());
		activeEnableInput(ngaySanXuatInput, ngaySanXuatValidation);
		ngaySanXuatInput.setText(product.getNgaySanXuat());
		activeEnableInput(hanSuDungInput, hanSuDungValidation);
		hanSuDungInput.setText(product.getHanSuDung());
		activeEnableInput(maNhaSanXuatInput, maNhaSanXuatValidation);
		maNhaSanXuatInput.setText(product.getMaNhaSanXuat());

		isValidMaSanPham = true;
		hasModifyProduct = true;
		hasInitProduct = false;
		wasModifyImage = false;
		if(product.getImageUrl().equals("NULL")) {
			isValidImage = false;
			productImage.setText("Không có ảnh");
			return;
		}
		
		String pathDirImage = pathImageProduct + product.getImageUrl();
		File image = new File(pathDirImage);
		if(!image.exists()) {
			productBLL.refreshUrlImage(product.getMaSanPham());
			isValidImage = false;
			productImage.setText("Không có ảnh");
			return;
		}

		productImage.setText("");
		productImage.setIcon(Util.getImage(pathDirImage, (int)productImage.getWidth(), (int)productImage.getHeight()));
		isValidImage = true;
		
	}

	private void refreshFormSanPham() {
		activeEnableInput(maSanPhamInput, maSanPhamValidation);
		activeEnableInput(tenSanPhamInput, tenSanPhamValidation);
		activeEnableInput(donGiaInput, donGiaValidation);
		activeEnableInput(loaiSanPhamInput, loaiSanPhamValidation);
		activeEnableInput(ngaySanXuatInput, ngaySanXuatValidation);
		activeEnableInput(hanSuDungInput, hanSuDungValidation);
		activeEnableInput(maNhaSanXuatInput, maNhaSanXuatValidation);
		productImage.setIcon(null);
		productImage.revalidate();
		productCache = new ProductDTO();
		productModifyCache = null;
		chooseMaNhaSanXuat.setEnabled(true);
		chooseLoaiSanPham.setEnabled(true);
		chooseImage.setEnabled(true);
		loaiSanPhamInput.setToolTipText("");
		maNhaSanXuatInput.setToolTipText("");
		refreshDataFormSanPham();
	}

	private void refreshDataFormSanPham() {
		hasInitProduct = true;
		hasModifyProduct = false;
		isValidMaSanPham = false;
		isValidTenSanPham = false;
		isValidDonGia = false;
		isValidLoaiSanPham = false;
		isValidNgaySanXuat = false;
		isValidHanSuDung = false;
		isValidMaNhaSanXuat = false;
		isValidImage = false;
	}

	// ========================================== XU LY SU KIEN NHAP CUA INPUT ====================================
	private void validationMaSanPham() {
		if(isValidMaSanPham) {
			return;
		}

		String maSanPham = maSanPhamInput.getText();
		if(maSanPham.length() < 1) {
			activeValidationInput(maSanPhamInput, maSanPhamValidation, "Bạn chưa nhập mã sản phẩm");
			return;
		}

		if(productBLL.indexOf(maSanPham) != -1) {
			activeValidationInput(maSanPhamInput, maSanPhamValidation, "Mã sản phẩm đã bị trùng");
			return;
		}

		if(maSanPham.length() < 3 || maSanPham.length() > 6) {
			activeValidationInput(maSanPhamInput, maSanPhamValidation, "Mã sản phẩm phải từ 3 đến 6 ký tự");	
			return;
		}

		productCache.setMaSanPham(maSanPham);
		activeSuccessInput(maSanPhamInput, maSanPhamValidation);
		isValidMaSanPham = true;
	}

	private void validationTenSanPham() {
		if(isValidTenSanPham) {
			return;
		}
		String tenSanPham = tenSanPhamInput.getText();
		if(tenSanPham.length() < 1) {
			activeValidationInput(tenSanPhamInput, tenSanPhamValidation, "Bạn chưa nhập tên sản phẩm");
			return;
		}

		if(productBLL.soSanhTenSanPham(tenSanPham) != null && !hasModifyProduct) {
			activeValidationInput(tenSanPhamInput, tenSanPhamValidation, "Tên sản phẩm bạn nhập đã bị trùng");
			return;
		}

		productCache.setTenSanPham(tenSanPham);
		isValidTenSanPham = true;
		activeSuccessInput(tenSanPhamInput, tenSanPhamValidation);
	}

	private void validationDonGia() {
		if(isValidDonGia) {
			return;
		}

		if(donGiaInput.getText().length() == 0) {
			activeValidationInput(donGiaInput, donGiaValidation, "Bạn chưa nhập đơn giá sản phậm");
			return;
		}

		if(donGiaInput.getMoney() > 10000000) {
			activeValidationInput(donGiaInput, donGiaValidation, "Đơn giá chỉ từ 1đ đến 1.000.000đ");
			return;
		}

		productCache.setDonGiaSanPham(Integer.parseInt(donGiaInput.getMoney().toString()));
		isValidDonGia = true;
		activeSuccessInput(donGiaInput, donGiaValidation);
	}

	private void validationLoaiSanPham() {
		if(isValidLoaiSanPham) {
			return;
		}

		String maLoaiSanPham = loaiSanPhamInput.getText().trim();
		if(maLoaiSanPham.length() == 0) {
			activeValidationInput(loaiSanPhamInput, loaiSanPhamValidation, "Bạn chưa nhập loại sản phẩm");
			return;
		}

		LoaiSanPhamDTO loaiSanPham = loaiSanPhamBLL.getLoaiSP(maLoaiSanPham);
		if(loaiSanPham == null) {
			activeValidationInput(loaiSanPhamInput, loaiSanPhamValidation, "Không tìm thấy mã loại sản phẩm bạn vừa nhập");
			return;
		}

		productCache.setLoaiSanPham(maLoaiSanPham);
		loaiSanPhamInput.setText(loaiSanPham.getTen());
		loaiSanPhamInput.setToolTipText("Mã loại sản phẩm: " + loaiSanPham.getMaLoai());
		isValidLoaiSanPham = true;
		chooseLoaiSanPham.setEnabled(false);
		activeSuccessInput(loaiSanPhamInput, loaiSanPhamValidation);
	}

	private void validationNgaySanXuat() {
		if(isValidNgaySanXuat) {
			return;
		}

		String ngaySanXuat = ngaySanXuatInput.getText().trim();

		if(ngaySanXuat.length() == 0) {
			activeValidationInput(ngaySanXuatInput, ngaySanXuatValidation, "Vui lòng nhập ngày sản xuất");
			return;
		}

		Date dateSanXuat = null;
		try {
			dateSanXuat = new Date(ngaySanXuat);
		}
		catch(Exception ex) {
			activeValidationInput(ngaySanXuatInput, ngaySanXuatValidation, ex.getMessage());
			return;
		}

		productCache.setNgaySanXuat(dateSanXuat.toString());	
		ngaySanXuatInput.setText(dateSanXuat.toString());
		isValidNgaySanXuat = true;
		activeSuccessInput(ngaySanXuatInput, ngaySanXuatValidation);
	}

	private void validationHanSuDung() {
		if(isValidHanSuDung) {
			return;
		}

		if(!isValidNgaySanXuat) {
			activeValidationInput(hanSuDungInput, hanSuDungValidation, "Vui lòng nhập ngày sản xuất trước hạn sử dụng");
			return;
		}

		String hanSuDung = hanSuDungInput.getText().trim();
		
		if(hanSuDung.length() == 0) {
			activeValidationInput(hanSuDungInput, hanSuDungValidation, "Vui lòng nhập hạn sử dụng");
			return;
		}

		Date dateHanSuDung = null;
		try {
			Date dateSanXuat = new Date(productCache.getNgaySanXuat());
			dateHanSuDung = new Date(hanSuDung);
			
			boolean isLess = false;
			if(dateHanSuDung.getYear() == dateSanXuat.getYear()) {
				if(dateHanSuDung.getMonth() - dateSanXuat.getMonth() < 6) {
					isLess = true;
				}
			}

			if(isLess != true && dateHanSuDung.getYear() < dateSanXuat.getYear()) {
				isLess = true;
			}

			if(isLess != true && dateHanSuDung.getYear() - dateSanXuat.getYear() == 1) {
				if(dateHanSuDung.getMonth() < dateSanXuat.getMonth() && (12 - dateSanXuat.getMonth()) + dateHanSuDung.getMonth() < 6) {
					isLess = true;
				}
			}

			if(isLess == true) {
				throw new Exception("Hạn sử dụng phải tối thiểu 6 tháng");	
			}
		}
		catch(Exception ex) {
			activeValidationInput(hanSuDungInput, hanSuDungValidation, ex.getMessage());
			return;
		}

		productCache.setHanSuDung(dateHanSuDung.toString());
		hanSuDungInput.setText(dateHanSuDung.toString());
		activeSuccessInput(hanSuDungInput, hanSuDungValidation);
		isValidHanSuDung = true;
	}

	private void validationNhaSanXuat() {
		if(isValidMaNhaSanXuat) {
			return;
		}

		String maNhaSanXuat = maNhaSanXuatInput.getText().trim();
		if(maNhaSanXuat.length() < 1) {
			activeValidationInput(maNhaSanXuatInput, maNhaSanXuatValidation, "Bạn chưa nhập mã nhà sản xuất");
			return;
		}

		NhaSanXuatDTO nhaSanXuat = nhaSanXuatBLL.getNSX(maNhaSanXuat);
		if(nhaSanXuat == null) {
			activeValidationInput(maNhaSanXuatInput, maNhaSanXuatValidation, "Không tìm thấy mã nhà sản xuất bạn vừa nhập");
			return;
		}
		
		productCache.setMaNhaSanXuat(maNhaSanXuat);
		maNhaSanXuatInput.setText(nhaSanXuat.getTen());
		maNhaSanXuatInput.setToolTipText("Mã nhà sản xuất: " + nhaSanXuat.getMaNhaSanXuat());
		isValidMaNhaSanXuat = true;
		chooseMaNhaSanXuat.setEnabled(false);
		activeSuccessInput(maNhaSanXuatInput, maNhaSanXuatValidation);
	}

	private void validationImage() {
		if(!isValidImage) {
			imageValidation.setText("Bạn chưa nhập hình ảnh cho sản phẩm");
			imageValidation.setVisible(true);
			return;
		}
	
		imageValidation.setVisible(false);
		isValidImage = true;
	}

	private void handleEventChooseImage() {
		JFileChooser file = new JFileChooser();	
		file.setCurrentDirectory(new File(System.getProperty("user.home")));
		file.addChoosableFileFilter(new ImageFilter());
		file.setAcceptAllFileFilterUsed(false);
		int res = file.showSaveDialog(null);

		if(res == JFileChooser.APPROVE_OPTION) {
			if(!isValidMaSanPham) {
				validationMaSanPham();
				return;
			}
			File selFile = file.getSelectedFile();
			pathImage = selFile.toPath();
			isValidImage = true;
			productCache.setImageUrl(productCache.getMaSanPham().toLowerCase() + Util.getExtensionFile(selFile));
			productImage.setText("");
			productImage.setIcon(Util.getImage(selFile.getPath(), (int)productImage.getWidth(), (int)productImage.getHeight()));
			productImage.revalidate();
			if(hasModifyProduct) {
				wasModifyImage = true;
			}
			validationImage();
		}
	}

	private void createWindowChooseLoaiSanPham() {
		JPanel framePanel = new JPanel(null);
		JPanel searchPanel = new JPanel(null);
		JScrollPane scrollSearchTable = new JScrollPane();
		JButton submitButton = new JButton("Chon");
		JButton searchButton = new JButton("Search"); // nut tim kiem du lieu
												  //
		frameChooseLoaiSanPham.setSize(800, 650);
		frameChooseLoaiSanPham.setLayout(null);
		frameChooseLoaiSanPham.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		frameChooseLoaiSanPham.setLocationRelativeTo(null);
		frameChooseLoaiSanPham.setResizable(false);
		frameChooseLoaiSanPham.setVisible(false);

		framePanel.setSize(frameChooseLoaiSanPham.getSize());
		framePanel.setLocation(0, 0);
		framePanel.setBackground(Color.WHITE);
		frameChooseLoaiSanPham.getContentPane().add(framePanel);

		FlexBox flexBoxForPanel = new FlexBox(framePanel, FlexBox.DIRECTION_COLUMN);	
		flexBoxForPanel.setPadding(10);

		searchPanel.setSize(500, 40);
		searchPanel.setBackground(Color.WHITE);
		flexBoxForPanel.setPosition(FlexBox.ALIGMENT_CENTER, 0, searchPanel);
		FlexBox flexBoxForSearchPanel = new FlexBox(searchPanel, FlexBox.DIRECTION_ROW);
		setPropertyForInputField(searchLoaiSanPhamInput);
		flexBoxForSearchPanel.setPositionWithinPercentSize(75, 0, searchLoaiSanPhamInput);
		setPropertyForButton(searchButton);
		flexBoxForSearchPanel.setPositionWithinPercentSize(100, 10, searchButton);

		String[] columnsName = {"Mã loại", "Tên"};
		int[] sizePerColumn = {50, 50};
		scrollSearchTable.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		flexBoxForPanel.setPositionWithinPercentSize(75, 10, scrollSearchTable);
		tableFrameLoaiSanPham = new AppTable(columnsName, sizePerColumn, scrollSearchTable.getSize());
		scrollSearchTable.getViewport().add(tableFrameLoaiSanPham);

		DefaultTableModel tableFrameLoaiSanPhamModel = (DefaultTableModel)tableFrameLoaiSanPham.getModel();
		setPropertyForButton(submitButton);
		flexBoxForPanel.setPosition(FlexBox.ALIGMENT_CENTER, 15, submitButton);

		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tableFrameLoaiSanPham.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(frameChooseLoaiSanPham, "Bạn chưa chọn loại sản phẩm, vui lòng nhấn chuột vào 1 dòng dữ liệu ở bảng để chọn loại sản phẩm");
					return;
				}

				String loaiSanPham = tableFrameLoaiSanPhamModel.getValueAt(tableFrameLoaiSanPham.getSelectedRow(), 0).toString();
				loaiSanPhamInput.setText(loaiSanPham);
				validationLoaiSanPham();
				displayRegistry.unSubFrameRegistry();
			}
		});

		searchButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String textInput = searchLoaiSanPhamInput.getText();
				
				if(textInput.length() == 0) {
					return;
				}
				
				
				ArrayList<LoaiSanPhamDTO> result = loaiSanPhamBLL.timKiemLoaiSP(searchLoaiSanPhamInput.getText());

				if(result.size() == 0) {
					JOptionPane.showMessageDialog(frameChooseLoaiSanPham, "Không tìm thấy dữ liệu");
					return;
				}

				tableFrameLoaiSanPhamModel.setRowCount(0);
				for(LoaiSanPhamDTO lsp : result) {
					tableFrameLoaiSanPhamModel.addRow(lsp.toArrayString());
				}
			}
		});
	}

	private void refreshFrameChooseLoaiSanPham() {
		searchLoaiSanPhamInput.setText("");

		DefaultTableModel tableFrameLoaiSanPhamModel = (DefaultTableModel)tableFrameLoaiSanPham.getModel();
		tableFrameLoaiSanPhamModel.setRowCount(0);
		for(LoaiSanPhamDTO lsp : loaiSanPhamBLL.getAllLoaiSP()) {
			tableFrameLoaiSanPhamModel.addRow(lsp.toArrayString());	
		}
	}

	private void createWindowChooseMaNhaSanXuat() {
		JPanel framePanel = new JPanel(null);
		JPanel searchPanel = new JPanel(null);
		JScrollPane scrollSearchTable = new JScrollPane();
		JButton submitButton = new JButton("Chon");
		JButton searchButton = new JButton("Search"); // nut tim kiem du lieu
												  //
		frameChooseMaNhaSanXuat.setSize(800, 650);
		frameChooseMaNhaSanXuat.setLayout(null);
		frameChooseMaNhaSanXuat.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		frameChooseMaNhaSanXuat.setLocationRelativeTo(null);
		frameChooseMaNhaSanXuat.setResizable(false);
		frameChooseMaNhaSanXuat.setVisible(false);

		framePanel.setSize(frameChooseMaNhaSanXuat.getSize());
		framePanel.setLocation(0, 0);
		framePanel.setBackground(Color.WHITE);
		frameChooseMaNhaSanXuat.getContentPane().add(framePanel);

		FlexBox flexBoxForPanel = new FlexBox(framePanel, FlexBox.DIRECTION_COLUMN);	
		flexBoxForPanel.setPadding(10);

		searchPanel.setSize(500, 40);
		searchPanel.setBackground(Color.WHITE);
		flexBoxForPanel.setPosition(FlexBox.ALIGMENT_CENTER, 0, searchPanel);
		FlexBox flexBoxForSearchPanel = new FlexBox(searchPanel, FlexBox.DIRECTION_ROW);
		setPropertyForInputField(searchMaNhaSanXuatInput);
		flexBoxForSearchPanel.setPositionWithinPercentSize(75, 0, searchMaNhaSanXuatInput);
		setPropertyForButton(searchButton);
		flexBoxForSearchPanel.setPositionWithinPercentSize(100, 10, searchButton);

		String[] columnsName = {"Mã", "Tên", "Địa chỉ"};
		int[] sizePerColumn = {15, 35, 50};
		scrollSearchTable.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		flexBoxForPanel.setPositionWithinPercentSize(75, 10, scrollSearchTable);
		tableFrameMaNhaSanXuat = new AppTable(columnsName, sizePerColumn, scrollSearchTable.getSize());
		scrollSearchTable.getViewport().add(tableFrameMaNhaSanXuat);

		DefaultTableModel tableFrameMaNhaSanXuatModel = (DefaultTableModel)tableFrameMaNhaSanXuat.getModel();
		setPropertyForButton(submitButton);
		flexBoxForPanel.setPosition(FlexBox.ALIGMENT_CENTER, 15, submitButton);

		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tableFrameMaNhaSanXuat.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(frameChooseMaNhaSanXuat, "Bạn chưa chọn nhà sản xuât, vui lòng nhấn chuột vào 1 dòng dữ liệu ở bảng để chọn nhà sản xuất");
					return;
				}

				String maNhaSanXuat = tableFrameMaNhaSanXuatModel.getValueAt(tableFrameMaNhaSanXuat.getSelectedRow(), 0).toString();
				maNhaSanXuatInput.setText(maNhaSanXuat);
				validationNhaSanXuat();
				displayRegistry.unSubFrameRegistry();
			}
		});

		searchButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String textInput = searchMaNhaSanXuatInput.getText();
				
				if(textInput.length() == 0) {
					return;
				}
				
				
				ArrayList<NhaSanXuatDTO> result = nhaSanXuatBLL.timKiemNSX(searchMaNhaSanXuatInput.getText());

				if(result.size() == 0) {
					JOptionPane.showMessageDialog(frameChooseMaNhaSanXuat, "Không tìm thấy dữ liệu");
					return;
				}

				tableFrameMaNhaSanXuatModel.setRowCount(0);
				for(NhaSanXuatDTO nsx : result) {
					tableFrameMaNhaSanXuatModel.addRow(nsx.toArrayString());
				}
			}
		});
	}

	private void refreshFrameChooseMaNhaSanXuat() {
		searchMaNhaSanXuatInput.setText("");

		DefaultTableModel tableFrameMaNhaSanXuatModel = (DefaultTableModel)tableFrameMaNhaSanXuat.getModel();
		tableFrameMaNhaSanXuatModel.setRowCount(0);
		for(NhaSanXuatDTO nsx : nhaSanXuatBLL.getAllNSX()) {
			tableFrameMaNhaSanXuatModel.addRow(nsx.toArrayString());
		}
	}

	// =============================================================================================================

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
		input.setFont(Util.getFont("Roboto", "NORMAL", 16));
		input.setForeground(Constants.COLOR_TEXTFIELD_FOREGROUND);
		input.setSize(450, 26);
	}

	private void setPropertyForLabelImage(JLabel image) {
		image.setSize(100, 75);
		image.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
	}

	private void setPropertyForButtonImage(JButton button) {
		button.setFont(defaultFont);
		button.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
		button.setSize(150, 30);
		button.setForeground(Color.BLACK);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setBackground(this.getBackground());
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

	// ============================================================================================================
}
