package GUI.nhanviengui;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.text.AbstractDocument;

import java.awt.Color;
import java.awt.Font;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import common.FlexBox;
import common.Util;
import common.Constants;
import common.Date;
import common.FrameDisplayRegistry;
import components.TMoneyField;
import components.UppercaseDocumentFilter;

import DTO.NhanVienDTO;
import BLL.NhanVienBLL;

public class NhanVienFormPanel extends JPanel {
	
	private JTextField maNhanVienInput;
	private JLabel maNhanVienValidation;
	private boolean isValidationMa;
	private JTextField tenNhanVienInput;
	private JLabel tenNhanVienValidation;
	private boolean isValidationTen;
	private JTextField ngayVaoLamInput;
	private JLabel ngayVaoLamValidation;
	private boolean isValidationNgay;
	private String[] valuesCombobox;
	private JComboBox viTriInput;
	private boolean isValidationViTri;
	private JLabel viTriValidation;
	private TMoneyField luongInput;
	private boolean isValidationLuong;
	private JLabel luongValidation;

	private JButton refreshButton;
	private JButton addButton;
	private JButton modifyButton;

	private NhanVienDTO nhanVien;
	private NhanVienDTO nhanVienCache;

	// = 1 la trang thai them nhan vien, 2 la thang trai chinh sua thong tin
	private int stateForm;

	private boolean isChangeValue;

	private FrameDisplayRegistry displayRegistry;
	private Font defaultFont;
	private NhanVienTablePanel tablePanel;

	public NhanVienFormPanel() {
		setLayout(null);
		initVariable();
	}

	private void initVariable() {
		displayRegistry = FrameDisplayRegistry.getInstance();
		maNhanVienInput = new JTextField();
		maNhanVienValidation = new JLabel();
		tenNhanVienInput = new JTextField();
		tenNhanVienValidation = new JLabel();
		ngayVaoLamInput = new JTextField();
		ngayVaoLamValidation = new JLabel();
		valuesCombobox = new String[] {"Chọn vị trí", "Bán hàng", "Quản lý"};
		viTriInput = new JComboBox(valuesCombobox);
		viTriValidation = new JLabel();
		luongInput = new TMoneyField(8);
		luongValidation = new JLabel();
		defaultFont = Util.getFont("Roboto", "NORMAL", 17);

		refreshButton = new JButton("Tạo mới nhân viên");
		addButton = new JButton("Thêm nhân viên");
		modifyButton = new JButton("Chỉnh sửa thông tin");
		stateForm = 0;

		nhanVien = new NhanVienDTO();
		isChangeValue = false;
		isValidationMa = false;
		isValidationTen = false;
		isValidationNgay = false;
		isValidationViTri = false;
		isValidationLuong = false;
	}
	
	public void setTablePanel(NhanVienTablePanel panel) {
		tablePanel = panel;
	}

	public void initComponentInPanel() {
		FlexBox flexBoxForPanel = new FlexBox(this, FlexBox.DIRECTION_COLUMN);	
		flexBoxForPanel.setPadding(15);

		JLabel titleLabel = new JLabel("Thông tin nhân viên");
		setPropertyForTitleLabel(titleLabel);
		flexBoxForPanel.setPosition(titleLabel, 0, 0);

		JLabel maNhanVienLabel = new JLabel("Mã nhân viên");
		setPropertyForLabel(maNhanVienLabel);
		flexBoxForPanel.setPosition(maNhanVienLabel, 15, 15);
		setPropertyForInputField(maNhanVienInput);
		flexBoxForPanel.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 10, maNhanVienInput);
		setPropertyForValidation(maNhanVienValidation);
		flexBoxForPanel.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 5, maNhanVienValidation);

		JLabel tenNhanVienLabel = new JLabel("Tên nhân viên");
		setPropertyForLabel(tenNhanVienLabel);
		flexBoxForPanel.setPosition(tenNhanVienLabel, 15, 15);
		setPropertyForInputField(tenNhanVienInput);
		flexBoxForPanel.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 10, tenNhanVienInput);
		setPropertyForValidation(tenNhanVienValidation);
		flexBoxForPanel.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 5, tenNhanVienValidation);

		JLabel ngayVaoLamLabel = new JLabel("Ngày vào làm");
		setPropertyForLabel(ngayVaoLamLabel);
		flexBoxForPanel.setPosition(ngayVaoLamLabel, 15, 15);
		setPropertyForInputField(ngayVaoLamInput);
		flexBoxForPanel.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 10, ngayVaoLamInput);
		setPropertyForValidation(ngayVaoLamValidation);
		flexBoxForPanel.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 5, ngayVaoLamValidation);

		JLabel viTriLabel = new JLabel("Vị trí");
		setPropertyForLabel(viTriLabel);
		flexBoxForPanel.setPosition(viTriLabel, 15, 15);
		setPropertyForInputField(viTriInput);
		flexBoxForPanel.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 10, viTriInput);
		setPropertyForValidation(viTriValidation);
		flexBoxForPanel.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 5, viTriValidation);
	
		JLabel luongLabel = new JLabel("Lương");
		setPropertyForLabel(luongLabel);
		flexBoxForPanel.setPosition(luongLabel, 15, 15);
		setPropertyForInputField(luongInput);
		flexBoxForPanel.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 10, luongInput);
		setPropertyForValidation(luongValidation);
		flexBoxForPanel.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 5, luongValidation);

		setPropertyForButton(refreshButton);
		flexBoxForPanel.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 100, refreshButton);

		setPropertyForButton(addButton);
		flexBoxForPanel.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 15, addButton);

		setPropertyForButton(modifyButton);
		flexBoxForPanel.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 15, modifyButton);

		AbstractDocument documentMa = (AbstractDocument)maNhanVienInput.getDocument();
		documentMa.setDocumentFilter(new UppercaseDocumentFilter());

		//
		activeDisableInput(maNhanVienInput, maNhanVienValidation);
		activeDisableInput(tenNhanVienInput, tenNhanVienValidation);
		activeDisableInput(ngayVaoLamInput, maNhanVienValidation);
		activeDisableJCombobox(viTriInput, viTriValidation);
		activeDisableInput(luongInput, luongValidation);

		addEventToComponentInPanel();
	}

	public void truyenDuLieuTuBang(NhanVienDTO data) {
		nhanVienCache = data;

		nhanVien.setMaNhanVien(data.getMaNhanVien());
		stateForm = 2;

		activeSuccessInput(maNhanVienInput, maNhanVienValidation);
		maNhanVienInput.setText(data.getMaNhanVien());

		activeEnableInput(tenNhanVienInput, tenNhanVienValidation);
		tenNhanVienInput.setText(data.getTenNhanVien());

		activeEnableInput(ngayVaoLamInput, ngayVaoLamValidation);
		ngayVaoLamInput.setText(data.getNgayVaoLam());

		activeEnableJComboBox(viTriInput, viTriValidation);
		if(data.getViTri().equals("bán hàng")) {
			viTriInput.setSelectedIndex(1);			
		}
		else {
			viTriInput.setSelectedIndex(2);	
		}

		activeEnableInput(luongInput, luongValidation);
		luongInput.setText(Integer.toString(data.getLuong()));
	}

	private void addEventToComponentInPanel() {
		refreshButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				refreshEventButton();
			}
		});

		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addEventButton();
			}
		});

		modifyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modifyEventButton();
			}
		});

		maNhanVienInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					validationMaNhanVien();
				}
			}
		});	

		tenNhanVienInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					validationTenNhanVien();
				}
			}
		});	
		ngayVaoLamInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					validationNgayVaoLam();
				}
			}
		});	
		luongInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					validationLuong();
				}
			}
		});	

		viTriInput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				validationViTri();
			}
		});
	}

	public void refreshEventButton() {
		resetDataInForm();

		stateForm = 1;

		activeEnableInput(maNhanVienInput, maNhanVienValidation);
		activeEnableInput(tenNhanVienInput, tenNhanVienValidation);
		activeEnableInput(ngayVaoLamInput, ngayVaoLamValidation);
		activeEnableJComboBox(viTriInput, viTriValidation);
		activeEnableInput(luongInput, luongValidation);
		isValidationMa = false;
		isValidationTen = false;
		isValidationNgay = false;
		isValidationViTri =false;
		isValidationLuong = false;
	}

	private void addEventButton() {
		if(stateForm != 1) {
			showMessage("Vui lòng bấm vào nút tạo mới nhân viên để có thể thêm dữ liệu");	
			return;
		}	

		validationMaNhanVien();
		validationTenNhanVien();
		validationNgayVaoLam();
		validationViTri();
		validationLuong();

		boolean isValidationForm = isValidationMa && isValidationTen && isValidationNgay && isValidationViTri && isValidationLuong;
		if(isValidationForm == false) {
			return;
		}

		NhanVienBLL.getInstance().addNhanVien(nhanVien);	
		showMessage("Thêm nhân viên mói thành công");	
		resetForm();
		tablePanel.refreshValuesInTable();
	}

	private void modifyEventButton() {
		if(stateForm != 2) {
			showMessage("Vui lòng chọn 1 dòng dữ liệu bên bảng nhân viên để có thể tiến hành chỉnh sửa thông tin dữ liêu");
			return;
		}

		validationTenNhanVien();
		validationNgayVaoLam();
		validationViTri();
		validationLuong();

		boolean isValidationForm = isValidationTen && isValidationNgay && isValidationViTri && isValidationLuong;
		if(isValidationForm == false) {
			return;
		}

		boolean isModifyTen = nhanVien.getTenNhanVien().equals(nhanVienCache.getTenNhanVien());
		boolean isModifyNgay = nhanVien.getNgayVaoLam().equals(nhanVienCache.getNgayVaoLam());
		boolean isModifyViTri = nhanVien.getViTri().equals(nhanVienCache.getViTri());
		boolean isModifyLuong = nhanVien.getLuong().equals(nhanVienCache.getLuong());
		if(isModifyTen && isModifyNgay && isModifyViTri && isModifyLuong) {
			showMessage("Bạn chưa chính sửa thông tin nhân viên");
			return;
		}

		NhanVienBLL.getInstance().updateNhanVien(nhanVien);
		showMessage("Chỉnh sửa thông tin nhân viên thành công");
		resetForm();
		tablePanel.refreshValuesInTable();
	}

	public void deleteEventButton(NhanVienDTO nhanVien) {
		NhanVienBLL.getInstance().deleteNhanVien(nhanVien.getMaNhanVien());
		resetForm();
	}

	private void validationMaNhanVien() {
		if(isValidationMa) {
			return;
		}
		String maNhanVien = maNhanVienInput.getText().trim();

		if(maNhanVien.length() == 0) {
			activeValidationInput(maNhanVienInput, maNhanVienValidation, "Bạn chưa nhập dữ liệu");
			return;
		}

		if(maNhanVien.length() > 8) {
			activeValidationInput(maNhanVienInput, maNhanVienValidation, "Mã nhân viên chỉ được tối đa 8 ký tự");
			return;
		}

		if(NhanVienBLL.getInstance().ExistsNhanVien(maNhanVien)) {
			activeValidationInput(maNhanVienInput, maNhanVienValidation, "Mã nhân viên đã tồn tài");
			return;
		}

		activeSuccessInput(maNhanVienInput, maNhanVienValidation);
		nhanVien.setMaNhanVien(maNhanVien);
		isValidationMa = true;
	}

	private void validationTenNhanVien() {
		if(isValidationTen) {
			return;
		}
		String tenNhanVien = tenNhanVienInput.getText().trim();

		if(tenNhanVien.length() == 0) {
			activeValidationInput(tenNhanVienInput, tenNhanVienValidation, "Bạn chưa nhập dữ liệu");
			return;
		}
	
		activeSuccessInput(tenNhanVienInput, tenNhanVienValidation);
		nhanVien.setTenNhanVien(tenNhanVien);
		isValidationTen = true;
	}

	private void validationNgayVaoLam() {
		if(isValidationNgay) {
			return;
		}
		String thoiGian = ngayVaoLamInput.getText().trim();

		if(thoiGian.length() == 0) {
			activeValidationInput(ngayVaoLamInput, ngayVaoLamValidation, "Bạn chưa nhập dữ liệu");
			return;
		}

		Date date = null;
		try {
			date = new Date(thoiGian);
		}
		catch(Exception ex) {
			activeValidationInput(ngayVaoLamInput, ngayVaoLamValidation, ex.getMessage());
			return;
		}

		activeSuccessInput(ngayVaoLamInput, ngayVaoLamValidation);
		nhanVien.setNgayVaoLam(date.toString());
		isValidationNgay = true;
	}

	private void validationViTri() {
		if(isValidationViTri) {
			return;
		}
		String viTri = "";
		switch(viTriInput.getSelectedIndex()) {
			case 1:
				viTri = "bán hàng";
				break;
			case 2:
				viTri = "quản lí";
				break;
			default:
				activeValidationJCombobox(viTriInput, viTriValidation, "Bạn chưa chọn vị trí");
				return;
		}

		activeSuccessJCombobox(viTriInput, viTriValidation);
		nhanVien.setViTri(viTri);
		isValidationViTri = true;
	}

	private void validationLuong() {
		if(isValidationLuong) {
			return;
		}
		int luong = Integer.parseInt(luongInput.getMoney().toString());

		if(luong < 1000000) {
			activeValidationInput(luongInput, luongValidation, "Lương tối thiểu phải la 1.000.000đ");	
			return;
		}

		activeSuccessInput(luongInput, luongValidation);
		nhanVien.setLuong(luong);
		isValidationLuong = true;
	}

	public void resetForm() {
		resetDataInForm();
		activeDisableInput(maNhanVienInput, maNhanVienValidation);
		activeDisableInput(tenNhanVienInput, tenNhanVienValidation);
		activeDisableInput(ngayVaoLamInput, ngayVaoLamValidation);
		activeDisableJCombobox(viTriInput, viTriValidation);
		activeDisableInput(luongInput, luongValidation);
		isValidationMa = false;
		isValidationTen = false;
		isValidationNgay = false;
		isValidationViTri = false;
		isValidationLuong = false;
	}

	private void resetDataInForm() {
		maNhanVienInput.setText("");
		tenNhanVienInput.setText("");
		ngayVaoLamInput.setText("");
		viTriInput.setSelectedIndex(0);
		luongInput.setText("");
		stateForm = -1;
		nhanVien = new NhanVienDTO();
	}

	private void showMessage(String message) {
		JOptionPane.showMessageDialog(displayRegistry.getMainFrame(), message);
	}

	private void setPropertyForLabel(JLabel label) {
		label.setFont(defaultFont);
		label.setForeground(Color.BLACK);
		label.setSize(Util.getSizeOfString(label.getText(), label.getFont()));
	}

	private void setPropertyForValidation(JLabel label) {
		label.setFont(defaultFont);
		label.setSize(500, 15);
		label.setForeground(Constants.COLOR_MESSAGE_VALIDATION);
	}

	private void setPropertyForTitleLabel(JLabel labelTitle) {
		labelTitle.setFont(Util.getFont("Montserrat", "BOLD", 25));
		labelTitle.setSize(Util.getSizeOfString(labelTitle.getText(), labelTitle.getFont()));
		labelTitle.setForeground(Color.BLACK);
	}

	private void setPropertyForInputField(JComponent input) {
		input.setFont(defaultFont);
		input.setForeground(Constants.COLOR_TEXTFIELD_FOREGROUND);
		input.setSize(500, 35);
	}

	private void activeDisableInput(JTextField input, JLabel validation) {
		input.setBackground(new Color(192, 208, 242));
		input.setBorder(null);
		input.setText("");
		input.setEditable(false);
		validation.setVisible(false);
	}

	private void activeDisableJCombobox(JComboBox input, JLabel validation) {
		input.setBackground(new Color(192, 208, 242));
		input.setBorder(null);
		input.setSelectedIndex(0);
		input.setEnabled(false);
		validation.setVisible(false);
	}

	private void activeEnableInput(JTextField input, JLabel validation) {
		input.setText("");
		input.setBackground(Color.WHITE);
		input.setEditable(true);
		input.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Constants.COLOR_TEXTFIELD_TYPE_BORDER), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		validation.setVisible(false);
	}

	private void activeEnableJComboBox(JComboBox input, JLabel validation) {
		input.setSelectedIndex(0);
		input.setBackground(Color.WHITE);
		input.setEnabled(true);
		input.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Constants.COLOR_TEXTFIELD_TYPE_BORDER), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		validation.setVisible(false);
	}

	private void activeSuccessInput(JTextField input, JLabel validation) {
		input.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Constants.COLOR_TEXTFIELD_SUCCESS_BORDER), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		input.setBackground(Color.WHITE);
		input.setEditable(false);
		validation.setVisible(false);
	}

	private void activeSuccessJCombobox(JComboBox input, JLabel validation) {
		input.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Constants.COLOR_TEXTFIELD_SUCCESS_BORDER), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		input.setBackground(Color.WHITE);
		input.setEnabled(false);
		validation.setVisible(false);
	}

	private void activeValidationInput(JTextField input, JLabel validation, String text) {
		input.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Constants.COLOR_TEXTFIELD_DANGER_BORDER), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		input.setEditable(true);
		validation.setText(text);
		validation.setVisible(true);
	}

	private void activeValidationJCombobox(JComboBox input, JLabel validation, String text) {
		input.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Constants.COLOR_TEXTFIELD_DANGER_BORDER), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		input.setEnabled(true);
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
}
