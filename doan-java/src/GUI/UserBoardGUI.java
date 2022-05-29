package GUI;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JOptionPane;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.Cursor;

import components.UserToken;
import common.Util;
import common.Constants;
import common.FlexBox;
import common.FrameDisplayRegistry;

import BLL.AccountBLL;
import DTO.NhanVienDTO;
import DTO.AccountDTO;

public class UserBoardGUI extends JPanel {
	private JPanel contentPanel;

	private boolean isValidationOld;
	private JPasswordField confirmOldPasswordInput;
	private JLabel validatioOld;
	private boolean isValidationNew;
	private JPasswordField newPasswordInput;
	private JLabel validationNew;
	private boolean isValidationConfirm;
	private JPasswordField confirmNewPasswordInput;
	private JLabel validationConfirm;
	private JButton changePasswordButton;
	private Font defaultFont;
	private String oldPasswordAcc;
	private String newPasswordAcc;
	private String maTaiKhoan;
	private FrameDisplayRegistry display;

	public UserBoardGUI(Dimension size) {
		setSize(size);
		setLayout(null);
		initVariable();
		initComponent();
	}

	private void initVariable() {
		contentPanel = new JPanel(null);
		confirmOldPasswordInput = new JPasswordField();
		newPasswordInput = new JPasswordField();
		confirmNewPasswordInput = new JPasswordField();
		changePasswordButton = new JButton("Đổi mật khẩu");
		validatioOld = new JLabel();
		validationNew = new JLabel();
		validationConfirm = new JLabel();
		isValidationNew = false;
		isValidationOld = false;
		isValidationConfirm = false;
		defaultFont = Util.getFont("Roboto", "NORMAL", 17);
		display = FrameDisplayRegistry.getInstance();
	}

	private void initComponent() {
		FlexBox flexBoxForUserBoard = new FlexBox(this, FlexBox.DIRECTION_COLUMN);
		setBackground(Constants.COLOR_SPACE);

		// ================== LABEL TITLE =======================
		JLabel labelTitleBoard = new JLabel("Thông tin tài khoản");
		labelTitleBoard.setFont(Util.getFont("Benchmark", "NORMAL", 35));
		labelTitleBoard.setSize(Util.getSizeOfString(labelTitleBoard.getText(), labelTitleBoard.getFont()));
		labelTitleBoard.setForeground(Constants.COLOR_PRIMARY);
		flexBoxForUserBoard.setPosition(labelTitleBoard, 10, 10);

		JLabel line = new JLabel();
		line.setSize(getWidth(), 2);
		line.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(70, 70, 70)));
		flexBoxForUserBoard.setPosition(line, 10, 0);

		contentPanel.setSize(getWidth(), 0);
		contentPanel.setBackground(Constants.COLOR_SPACE);
		flexBoxForUserBoard.setPosition(FlexBox.ALIGNMENT_REMAINING_SIZE_OF_PANEL, 0, contentPanel);

		initComponentInContentPanel();
	}

	private void initComponentInContentPanel() {
		FlexBox flexBoxForContentPanel = new FlexBox(contentPanel, FlexBox.DIRECTION_ROW);
		flexBoxForContentPanel.setPadding(20);

		JPanel infoUserPanel = new JPanel(null);
		infoUserPanel.setSize(350, 270);
		infoUserPanel.setBackground(Color.WHITE);
		flexBoxForContentPanel.setPosition(infoUserPanel, 0, 0);
		initComponentInInfoUser(infoUserPanel);

		JPanel changePasswordPanel = new JPanel(null);
		changePasswordPanel.setSize(450, 500);
		changePasswordPanel.setBackground(Color.WHITE);
		flexBoxForContentPanel.setPosition(changePasswordPanel, 0, 30);
		initComponentInChangePassword(changePasswordPanel);
	}

	private void initComponentInInfoUser(JPanel panel) {
		FlexBox flexBoxForInfoUser = new FlexBox(panel, FlexBox.DIRECTION_COLUMN);
		flexBoxForInfoUser.setPadding(15);
		NhanVienDTO nhanVien = UserToken.getInstance().getNhanVien();
		AccountDTO account = AccountBLL.getInstance().getOneAccountWithMaNhanVien(nhanVien.getMaNhanVien());
		JLabel nameLabel = new JLabel("Họ và tên: " + nhanVien.getTenNhanVien());
		setPropertyForLabel(nameLabel);
		flexBoxForInfoUser.setPosition(nameLabel, 0, 0);
		
		JLabel viTriLabel = new JLabel("Vị trí: " + nhanVien.getViTri());
		setPropertyForLabel(viTriLabel);
		flexBoxForInfoUser.setPosition(viTriLabel, 10, 0);

		JLabel luongLabel = new JLabel("Lương: " + Util.convertMoneyToString(nhanVien.getLuong()));
		setPropertyForLabel(luongLabel);
		flexBoxForInfoUser.setPosition(luongLabel, 10, 0);

		JLabel usernameLabel = new JLabel("Tên tài khoản: " + account.getTenTaiKhoan());
		setPropertyForLabel(usernameLabel);
		flexBoxForInfoUser.setPosition(usernameLabel, 10, 0);

		oldPasswordAcc = account.getMatKhau();
		maTaiKhoan = account.getMaTaiKhoan();
		JLabel passwordLabel = new JLabel("Mật khẩu: " + "*********");
		setPropertyForLabel(passwordLabel);
		flexBoxForInfoUser.setPosition(passwordLabel, 10, 0);
	}

	private void initComponentInChangePassword(JPanel panel) {
		FlexBox flexBoxForChange = new FlexBox(panel, FlexBox.DIRECTION_COLUMN);
		flexBoxForChange.setPadding(15);

		JLabel titleLabel = new JLabel("Đổi mật khẩu");
		titleLabel.setFont(Util.getFont("Roboto", "BOLD", 20));
		titleLabel.setForeground(Color.BLACK);
		titleLabel.setSize(Util.getSizeOfString(titleLabel.getText(), titleLabel.getFont()));
		flexBoxForChange.setPosition(titleLabel, 0, 0);

		JLabel oldLabel = new JLabel("Xác nhận mật khẩu cũ: ");
		setPropertyForLabel(oldLabel);
		flexBoxForChange.setPosition(oldLabel, 15, 15);
		setPropertyForInputField(confirmOldPasswordInput);
		flexBoxForChange.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 10, confirmOldPasswordInput);
		setPropertyForValidationLabel(validatioOld);
		flexBoxForChange.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 5, validatioOld);
		
		JLabel newLabel = new JLabel("Nhập mật khẩu mới: ");
		setPropertyForLabel(newLabel);
		flexBoxForChange.setPosition(newLabel, 15, 15);
		setPropertyForInputField(newPasswordInput);
		flexBoxForChange.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 10, newPasswordInput);
		setPropertyForValidationLabel(validationNew);
		flexBoxForChange.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 5, validationNew);

		JLabel confirmLabel = new JLabel("Xác nhận mật khẩu mới: ");
		setPropertyForLabel(confirmLabel);
		flexBoxForChange.setPosition(confirmLabel, 15, 15);
		setPropertyForInputField(confirmNewPasswordInput);
		flexBoxForChange.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 10, confirmNewPasswordInput);
		setPropertyForValidationLabel(validationConfirm);
		flexBoxForChange.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 5, validationConfirm);

		changePasswordButton.setFont(defaultFont);
		changePasswordButton.setForeground(Constants.COLOR_BACKGROUND_PRIMARY);
		changePasswordButton.setBackground(Constants.COLOR_PRIMARY);
		changePasswordButton.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Constants.COLOR_PRIMARY));
		changePasswordButton.setSize(175, 45);
		changePasswordButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		changePasswordButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				changePasswordButton.setForeground(Constants.COLOR_PRIMARY);
				changePasswordButton.setBackground(Constants.COLOR_BACKGROUND_PRIMARY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				changePasswordButton.setForeground(Constants.COLOR_BACKGROUND_PRIMARY);
				changePasswordButton.setBackground(Constants.COLOR_PRIMARY);
			}
		});

		changePasswordButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				validationOldPassword();
				validationNewPassword();
				validationConfirmPassword();

				if(!isValidationOld || !isValidationNew || !isValidationConfirm) {
					return;
				}

				AccountBLL.getInstance().capNhatMatKhau(maTaiKhoan, newPasswordAcc);
				JOptionPane.showMessageDialog(display.getMainFrame(), "Đổi mật khẩu thành công");
				refreshChangePassword();
			}
		});
		flexBoxForChange.setPosition(FlexBox.ALIGMENT_PREVIOUS_ELEMENT, 15, changePasswordButton);
	}

	private void refreshChangePassword() {
		confirmOldPasswordInput.setText("");
		confirmNewPasswordInput.setText("");
		newPasswordInput.setText("");
	}

	private void validationOldPassword() {
		String oldPassword = String.valueOf(confirmOldPasswordInput.getPassword());

		if(oldPassword.length() == 0) {
			activeValidation(validatioOld, confirmOldPasswordInput, "Bạn chưa nhập dữ liệu");
			return;
		}

		if(!oldPassword.equals(oldPasswordAcc)) {
			activeValidation(validatioOld, confirmOldPasswordInput, "Mật khẩu cũ không chính xác");
			return;
		}

		activeInput(validatioOld, confirmOldPasswordInput);
		isValidationOld = true;
	}

	private void validationNewPassword() {
		String newPassword = String.valueOf(newPasswordInput.getPassword());

		if(newPassword.length() == 0) {
			activeValidation(validationNew, newPasswordInput, "Bạn chưa nhập dữ liệu");
			return;
		}

		if(vallidationPasswordSpace(newPassword)) {
			activeValidation(validationNew, newPasswordInput, "mật khẩu không được chứa ký tự khoảng trắng");
			return;
		}

		if(validationPasswordKeySpecial(newPassword)) {
			activeValidation(validationNew, newPasswordInput, "Mật khẩu chỉ được chứa số, chữ thường và chữ in hoa");
			return;
		}

		if(newPassword.length() < 8) {
			activeValidation(validationNew, newPasswordInput, "Mật khẩu phải từ 8 ký tự trở lên");
			return;
		}

		if(newPassword.equals(oldPasswordAcc)) {
			activeValidation(validationNew, newPasswordInput, "Mật khâu mới không được trùng với mật khẩu cũ");
			return;
		}

		newPasswordAcc = newPassword;
		activeInput(validationNew, newPasswordInput);
		isValidationNew = true;
	}

	private void validationConfirmPassword() {
		String confirmPassword = String.valueOf(confirmNewPasswordInput.getPassword());

		if(confirmPassword.length() == 0) {
			activeValidation(validationConfirm, confirmNewPasswordInput, "Bạn chưa nhập dữ liệu");
			return;
		}

		if(vallidationPasswordSpace(confirmPassword)) {
			activeValidation(validationConfirm, confirmNewPasswordInput, "mật khẩu không được chứa ký tự khoảng trắng");
			return;
		}

		if(validationPasswordKeySpecial(confirmPassword)) {
			activeValidation(validationConfirm, confirmNewPasswordInput, "Mật khẩu chỉ được chứa số, chữ thường và chữ in hoa");
			return;
		}

		if(!newPasswordAcc.equals(confirmPassword)) {
			activeValidation(validationConfirm, confirmNewPasswordInput, "xác nhận mật khẩu sai");
			return;
		}

		activeInput(validationConfirm, confirmNewPasswordInput);
		isValidationConfirm = true;
	}

	private boolean vallidationPasswordSpace(String password) {
		return password.contains(" ");
	}

	private boolean validationPasswordKeySpecial(String password) {
		boolean isContainKeySpecial = false;
		for(int i = 0; i < password.length(); ++i) {
			int valueASCII = (int)password.charAt(i);
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
		label.setFont(Util.getFont("Roboto", "NORMAL", 18));
		label.setForeground(Color.BLACK);
		label.setSize(Util.getSizeOfString(label.getText(), label.getFont()));
	}

	private void setPropertyForInputField(JPasswordField input) {
		input.setSize(320, 35);
		input.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Constants.COLOR_PRIMARY));
		input.setFont(defaultFont);
		input.setForeground(Constants.COLOR_PRIMARY);
	}

	private void setPropertyForValidationLabel(JLabel label) {
		label.setFont(defaultFont);
		label.setForeground(Constants.COLOR_MESSAGE_VALIDATION);
		label.setSize(350, 20);
		label.setVisible(false);
	}

	private void activeValidation(JLabel label, JPasswordField input, String message) {
		input.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Constants.COLOR_TEXTFIELD_DANGER_BORDER));
		label.setVisible(true);
		label.setText(message);
	}

	private void activeInput(JLabel label, JPasswordField input) {
		input.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Constants.COLOR_PRIMARY));
		label.setVisible(false);
	}

}
