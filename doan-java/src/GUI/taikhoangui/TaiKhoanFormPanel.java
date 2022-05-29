package GUI.taikhoangui;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JComponent;

import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

import components.AppTable;
import common.FlexBox;
import common.Util;
import common.Constants;
import common.Date;
import common.FrameDisplayRegistry;

import DTO.AccountDTO;
import DTO.NhanVienDTO;
import BLL.AccountBLL;

public class TaiKhoanFormPanel extends JPanel {
	private JTextField maTaiKhoanInput;
	private JLabel maTaiKhoanValidation;
	private boolean isValidationMa;

	private JTextField tenTaiKhoanInput;
	private JLabel tenTaiKhoanValidation;
	private boolean isValidationTen;

	private JTextField matKhauInput;
	private JLabel matKhauValidation;
	private boolean isValidationMK;

	private JTextField emailInput;
	private boolean isValidationEmail;
	private JLabel emailValidation;

	private JTextField soDienThoaiInput;
	private boolean isValidationSDT;
	private JLabel soDienThoaiValidation;

	private JTextField maNhanVienInput;
	private boolean isValidationMaNV;
	private JLabel maNhanVienValidation;

	private JButton initButton;
	private JButton addButton;
	private JButton deleteButton;

	private FrameDisplayRegistry displayRegistry;
	private Font defaultFont;

	private NhanVienDTO nhanVienChuaCoTaiKhoan;
	private AccountDTO account;

	private int stateForm;

	TaiKhoanTablePanel tablePanel;
	public TaiKhoanFormPanel() {
		setLayout(null);
		initVariable();
	}

	private void initVariable() {
		defaultFont = Util.getFont("Roboto", "NORMAL", 17);
		displayRegistry = FrameDisplayRegistry.getInstance();
		account = new AccountDTO();
		stateForm = -1;

		maTaiKhoanInput = new JTextField();
		tenTaiKhoanInput = new JTextField();
		matKhauInput = new JTextField();
		emailInput = new JTextField();
		soDienThoaiInput = new JTextField();
		maNhanVienInput = new JTextField();

		maTaiKhoanValidation = new JLabel();
		tenTaiKhoanValidation = new JLabel();
		matKhauValidation = new JLabel();
		emailValidation = new JLabel();
		soDienThoaiValidation = new JLabel();
		maNhanVienValidation = new JLabel();

		isValidationMa = false;
		isValidationTen = false;
		isValidationMK = false;
		isValidationEmail = false;
		isValidationSDT = false;
		isValidationMaNV = false;

		initButton = new JButton("Khởi tạo tài khoản");
		addButton = new JButton("Thêm tài khoản");
		deleteButton = new JButton("Xóa tài khoản");
	}

	public void setTablePanel(TaiKhoanTablePanel panel) {
		tablePanel = panel;
	}

	public void initComponentInPanel() {
		FlexBox flexBoxForPanel = new FlexBox(this, FlexBox.DIRECTION_COLUMN);	
		flexBoxForPanel.setPadding(15);

		JLabel titleLabel = new JLabel("Thông tin tài khoản");
		setPropertyForTitleLabel(titleLabel);
		flexBoxForPanel.setPosition(titleLabel, 0, 0);

		JLabel maTaiKhoanLabel = new JLabel("Mã tài khoản");
		setPropertyForLabel(maTaiKhoanLabel);
		flexBoxForPanel.setPosition(maTaiKhoanLabel, 10, 15);
		setPropertyForInputField(maTaiKhoanInput);
		flexBoxForPanel.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 10, maTaiKhoanInput);
		setPropertyForValidation(maTaiKhoanValidation);
		flexBoxForPanel.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 5, maTaiKhoanValidation);

		JLabel tenTaiKhoanLabel = new JLabel("Tên tài khoản");
		setPropertyForLabel(tenTaiKhoanLabel);
		flexBoxForPanel.setPosition(tenTaiKhoanLabel, 10, 15);
		setPropertyForInputField(tenTaiKhoanInput);
		flexBoxForPanel.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 10, tenTaiKhoanInput);
		setPropertyForValidation(tenTaiKhoanValidation);
		flexBoxForPanel.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 5, tenTaiKhoanValidation);

		JLabel matKhauLabel = new JLabel("Mật khẩu mặc định");
		setPropertyForLabel(matKhauLabel);
		flexBoxForPanel.setPosition(matKhauLabel, 10, 15);
		setPropertyForInputField(matKhauInput);
		flexBoxForPanel.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 10, matKhauInput);
		setPropertyForValidation(matKhauValidation);
		flexBoxForPanel.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 5, matKhauValidation);

		JLabel emailLabel = new JLabel("Email");
		setPropertyForLabel(emailLabel);
		flexBoxForPanel.setPosition(emailLabel, 10, 15);
		setPropertyForInputField(emailInput);
		flexBoxForPanel.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 10, emailInput);
		setPropertyForValidation(emailValidation);
		flexBoxForPanel.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 5, emailValidation);
	
		JLabel soDienThoaiLabel = new JLabel("Số điện thoại");
		setPropertyForLabel(soDienThoaiLabel);
		flexBoxForPanel.setPosition(soDienThoaiLabel, 10, 15);
		setPropertyForInputField(soDienThoaiInput);
		flexBoxForPanel.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 10, soDienThoaiInput);
		setPropertyForValidation(soDienThoaiValidation);
		flexBoxForPanel.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 5, soDienThoaiValidation);

		JLabel maNhanVienLabel = new JLabel("Mã nhân viên");
		setPropertyForLabel(maNhanVienLabel);
		flexBoxForPanel.setPosition(maNhanVienLabel, 10, 15);
		setPropertyForInputField(maNhanVienInput);
		flexBoxForPanel.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 10, maNhanVienInput);
		setPropertyForValidation(maNhanVienValidation);
		flexBoxForPanel.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 5, maNhanVienValidation);

		setPropertyForButton(initButton);
		flexBoxForPanel.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 5, initButton);

		setPropertyForButton(addButton);
		flexBoxForPanel.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 15, addButton);

		setPropertyForButton(deleteButton);
		flexBoxForPanel.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 15, deleteButton);

		//
		activeDisableInput(maTaiKhoanInput, maTaiKhoanValidation);
		activeDisableInput(tenTaiKhoanInput, tenTaiKhoanValidation);
		activeDisableInput(matKhauInput, matKhauValidation);
		activeDisableInput(emailInput, emailValidation);
		activeDisableInput(soDienThoaiInput, soDienThoaiValidation);
		activeDisableInput(maNhanVienInput, maNhanVienValidation);

		addEventToPanel();
	}

	private void addEventToPanel() {
		initButton.addActionListener(new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent e) {
				nhanVienChuaCoTaiKhoan = tablePanel.chuyenDuLieuBangNhanVienChuaCoTaiKhoan();
				if(nhanVienChuaCoTaiKhoan == null) {
					showMessage("Vui lòng chọn 1 dòng dữ liệu bên bảng các nhân viên chưa có tài khoản(nếu có nhân viên chưa có tài khoản sẽ hiện thị bên bảng đó) để có thê tiến hành khởi tạo tài khoản cho nhân viên đó");
					return;
				}

				activeEnableInput(maTaiKhoanInput, maTaiKhoanValidation);
				activeEnableInput(tenTaiKhoanInput, tenTaiKhoanValidation);
				activeEnableInput(emailInput, emailValidation);
				activeEnableInput(soDienThoaiInput, soDienThoaiValidation);
				
				matKhauInput.setText("12345678");	
				activeSuccessInput(matKhauInput, matKhauValidation);
				maNhanVienInput.setText(nhanVienChuaCoTaiKhoan.getMaNhanVien());
				activeSuccessInput(maNhanVienInput, maNhanVienValidation);

				account.setMatKhau("12345678");
				account.setMaNhanVien(nhanVienChuaCoTaiKhoan.getMaNhanVien());

				stateForm = 1;
			}
		});

		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(stateForm != 1) {
					showMessage("Vui lòng bấm khởi tạo tài khoản để có thể thêm tài khoản");
					return;
				}

				validationMaTaiKhoan();
				validationTenTaiKhoan();
				validationEmail();
				validationSoDienThoai();

				boolean isValidationForm = isValidationMa && isValidationTen && isValidationEmail && isValidationSDT;
				if(!isValidationForm) {
					return;
				}

				AccountBLL.getInstance().addAcount(account);
				showMessage("Thêm tài khoản cho nhân viên: " + nhanVienChuaCoTaiKhoan.getTenNhanVien() + " thành công");
				resetForm();
				tablePanel.refreshTableAccounts();
				tablePanel.refreshTableStaffsNotAccount();
			}
		});

		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AccountDTO accDeleted = tablePanel.chuyenDuLieuBangTaiKhoan();

				if(accDeleted == null) {
					showMessage("Vui lòng chọn 1 dòng dữ liệu ở bên bảng tài khoản để có thể tiến hành xóa tài khoản");
					return;
				}

				AccountBLL.getInstance().deleteAccount(accDeleted.getMaTaiKhoan());
				showMessage("Xóa tài khoản thành công");
				tablePanel.refreshTableAccounts();
				tablePanel.refreshTableStaffsNotAccount();
			}
		});

		maTaiKhoanInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					validationMaTaiKhoan();
				}
			}
		});	

		tenTaiKhoanInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					validationTenTaiKhoan();
				}
			}
		});	
		soDienThoaiInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					validationSoDienThoai();
				}
			}
		});	
		emailInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					validationEmail();
				}
			}
		});	
	}

	public void resetForm() {
		maTaiKhoanInput.setText("");
		activeDisableInput(maTaiKhoanInput, maTaiKhoanValidation);

		tenTaiKhoanInput.setText("");
		activeDisableInput(tenTaiKhoanInput, tenTaiKhoanValidation);

		matKhauInput.setText("");
		activeDisableInput(matKhauInput, matKhauValidation);

		emailInput.setText("");
		activeDisableInput(emailInput, emailValidation);

		soDienThoaiInput.setText("");
		activeDisableInput(soDienThoaiInput, soDienThoaiValidation);

		maNhanVienInput.setText("");
		activeDisableInput(maNhanVienInput, maNhanVienValidation);

		account = new AccountDTO();
		stateForm = -1;
		isValidationMa = false;
		isValidationTen = false;
		isValidationSDT = false;
		isValidationEmail = false;
	}

	private void validationMaTaiKhoan() {
		if(isValidationMa) {
			return;
		}
		String maTaiKhoan = maTaiKhoanInput.getText().trim();

		if(maTaiKhoan.length() == 0) {
			activeValidationInput(maTaiKhoanInput, maTaiKhoanValidation, "Bạn chưa nhập dữ liệu");
			return;
		}

		if(maTaiKhoan.length() > 8) {
			activeValidationInput(maTaiKhoanInput, maTaiKhoanValidation, "Mã tài khoản chỉ được tối đa 8 ký tự");
			return;
		}

		if(AccountBLL.getInstance().ExistsAccount(maTaiKhoan)) {
			activeValidationInput(maTaiKhoanInput, maTaiKhoanValidation, "Mã nhân viên đã tồn tài");
			return;
		}

		activeSuccessInput(maTaiKhoanInput, maTaiKhoanValidation);
		account.setMaTaiKhoan(maTaiKhoan);
		isValidationMa = true;
	}

	private void validationTenTaiKhoan() {
		if(isValidationTen) {
			return;
		}
		String tenTaiKhoan = tenTaiKhoanInput.getText().trim();

		if(tenTaiKhoan.length() == 0) {
			activeValidationInput(tenTaiKhoanInput, tenTaiKhoanValidation, "Bạn chưa nhập dữ liệu");
			return;
		}

		if(tenTaiKhoan.contains(" ")) {
			System.out.println("123123");
			activeValidationInput(tenTaiKhoanInput, tenTaiKhoanValidation, "Không được chứa khoảng trắng");
			return;
		}
	
		if(validationStringKeySpecial(tenTaiKhoan)) {
			activeValidationInput(tenTaiKhoanInput, tenTaiKhoanValidation, "Tên tài khoản chỉ được chứa ký tự và số");
			return;
		}

		activeSuccessInput(tenTaiKhoanInput, tenTaiKhoanValidation);
		account.setTenTaiKhoan(tenTaiKhoan);
		isValidationTen = true;
	}

	private void validationEmail() {
		if(isValidationEmail) {
			return;
		}
		String email = emailInput.getText().trim();

		if(email.length() == 0) {
			activeValidationInput(emailInput, emailValidation, "Bạn chưa nhập dữ liệu");
			return;
		}

		activeSuccessInput(emailInput, emailValidation);
		account.setEmail(email);
		isValidationEmail = true;
	}

	private void validationSoDienThoai() {
		if(isValidationSDT) {
			return;
		}
		String soDienThoai = soDienThoaiInput.getText().trim();

		if(soDienThoai.length() == 0) {
			activeValidationInput(soDienThoaiInput, soDienThoaiValidation, "Bạn chưa nhập dữ liệu");
			return;
		}

		boolean isNotContainFullNumberChar = false;

		for(int i = 0; i < soDienThoai.length(); ++i) {
			int valueASCII = (int)soDienThoai.charAt(i);
			if(valueASCII < 48 || valueASCII > 57) {
				isNotContainFullNumberChar = true;
				break;
			}
		}

		if(isNotContainFullNumberChar) {
			activeValidationInput(soDienThoaiInput, soDienThoaiValidation, "Số điện thoại chỉ được chứa ký tự số");
			return;
		}

		if(soDienThoai.length() != 10) {
			activeValidationInput(soDienThoaiInput, soDienThoaiValidation, "Số điện thoại phải đủ 10 ký tự");
			return;
		}

		activeSuccessInput(soDienThoaiInput, soDienThoaiValidation);
		account.setSoDienThoai(soDienThoai);
		isValidationSDT = true;
	}

	private boolean validationStringKeySpecial(String str) {
		boolean isContainKeySpecial = false;
		
		for(int i = 0; i < str.length(); ++i) {
			int valueASCII = (int)str.charAt(i);

			if(valueASCII < 48) {
				isContainKeySpecial = true;
				break;
			}

			if(valueASCII > 57 && valueASCII < 65) {
				isContainKeySpecial = true;
				break;
			}
			
			if(valueASCII > 90 && valueASCII < 97) {
				isContainKeySpecial = true;
				break;
			}

			if(valueASCII > 122) {
				isContainKeySpecial = true;
				break;
			}
		}
		return isContainKeySpecial;
	}

	private void setPropertyForLabel(JLabel label) {
		label.setFont(defaultFont);
		label.setForeground(Color.BLACK);
		label.setSize(Util.getSizeOfString(label.getText(), label.getFont()));
	}

	private void setPropertyForValidation(JLabel label) {
		label.setFont(defaultFont);
		label.setSize(450, 15);
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
