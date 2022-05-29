package GUI;

import javax.swing.*;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import javax.swing.JPasswordField;
import javax.swing.JOptionPane;
import common.FlexBox;
import common.FrameDisplayRegistry;
import components.UserToken;
import BLL.AccountBLL;
import GUI.MainGUI;

import common.Constants;
import common.Util;

public class LoginFrame extends JFrame {
	private JTextField inputUsername;
	private boolean isValidationUsername;
	private JLabel labelValidationUsername;
	private JPasswordField inputPassword;
	private boolean isValidationPassword;
	private JLabel labelValidationPassword;
	private Font defaultFont;
	private AccountBLL accountBLL;
	private FrameDisplayRegistry displayRegistry;

	public LoginFrame() {
		setSize(600, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setBackground(Color.WHITE);
		setLayout(null);
		initVariable();
		initComponent();
		setVisible(true);
	}

	private void initVariable() {
		inputUsername = new JTextField();
		inputPassword = new JPasswordField();
		defaultFont = Util.getFont("Roboto", "NORMAL", 15);
		labelValidationUsername = new JLabel("123");
		labelValidationPassword = new JLabel("123");
		isValidationUsername = false;
		isValidationPassword = false;
		accountBLL = AccountBLL.getInstance();
		displayRegistry = FrameDisplayRegistry.getInstance();
	}

	private void initComponent() {
		Font defaultFont = Util.getFont(Constants.FONT_DEFAULT, "NORMAL", 18);
		
		FlexBox flexBoxForLoginFrame = new FlexBox(this, FlexBox.DIRECTION_COLUMN);
		flexBoxForLoginFrame.setPadding(20);

		JLabel logo = new JLabel( Util.getImage("./src/assets/images/logo.png", 100, 75), SwingConstants.CENTER);
		logo.setSize(logo.getPreferredSize());
		flexBoxForLoginFrame.setPosition(FlexBox.ALIGMENT_CENTER, 0, logo);

		JLabel logoTitle = new JLabel("MINISHOP");
		logoTitle.setFont(Util.getFont(Constants.FONT_TITLE, "BOLD", 30));
		logoTitle.setSize(Util.getSizeOfString(logoTitle.getText(), logoTitle.getFont()));
		logoTitle.setForeground(Constants.COLOR_PRIMARY);
		logoTitle.setBackground(Color.RED);
		flexBoxForLoginFrame.setPosition(FlexBox.ALIGMENT_CENTER, 10, logoTitle);

		JLabel titleForm = new JLabel("LOGIN", SwingConstants.CENTER);
		titleForm.setFont(Util.getFont(Constants.FONT_DEFAULT, "BOLD", 30));
		titleForm.setSize(Util.getSizeOfString(titleForm.getText(), titleForm.getFont()));
		titleForm.setForeground(Constants.COLOR_PRIMARY);
		flexBoxForLoginFrame.setPosition(FlexBox.ALIGMENT_CENTER, 80, titleForm);

		JPanel panelInputUsername = new JPanel(null);
		panelInputUsername.setSize(250, 90);
		flexBoxForLoginFrame.setPosition(FlexBox.ALIGMENT_CENTER, 40, panelInputUsername);
		FlexBox flexBoxForUsername = new FlexBox(panelInputUsername, FlexBox.DIRECTION_COLUMN);
		JLabel titleUsername = new JLabel("Tài khoản:");
		setPropertyForLabel(titleUsername);
		flexBoxForUsername.setPosition(titleUsername, 0, 0);
		setPropertyForInput(inputUsername);
		flexBoxForUsername.setPosition(inputUsername, 10, 0);
		setPropertyForLabelValidation(labelValidationUsername);
		flexBoxForUsername.setPosition(labelValidationUsername, 5, 0);

		JPanel panelInputPassword = new JPanel(null);
		panelInputPassword.setSize(250, 90);
		flexBoxForLoginFrame.setPosition(FlexBox.ALIGMENT_CENTER, 40, panelInputPassword);
		FlexBox flexBoxForPassword = new FlexBox(panelInputPassword, FlexBox.DIRECTION_COLUMN);
		JLabel titlePassword = new JLabel("Mật khẩu:");
		setPropertyForLabel(titlePassword);
		flexBoxForPassword.setPosition(titlePassword, 0, 0);
		setPropertyForInput(inputPassword);
		flexBoxForPassword.setPosition(inputPassword, 10, 0);
		setPropertyForLabelValidation(labelValidationPassword);
		flexBoxForPassword.setPosition(labelValidationPassword, 5, 0);

		JButton submitButton = new JButton("Đăng nhập");
		submitButton.setSize(200, 50);
		submitButton.setBackground(Constants.COLOR_PRIMARY);
		submitButton.setForeground(Color.WHITE);
		submitButton.setFocusPainted(false);
		submitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		submitButton.setFont(defaultFont.deriveFont(Font.BOLD, 25f));
		flexBoxForLoginFrame.setPosition(FlexBox.ALIGMENT_CENTER, 80, submitButton);


		// them su kien
		inputUsername.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setPropertyForInputHover(inputUsername, labelValidationUsername);	
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setPropertyForInputNoneHover(inputUsername, labelValidationUsername, isValidationUsername);
			}
		});

		inputUsername.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(isValidationUsername && inputUsername.getText().length() != 0) {
					isValidationUsername = false;
				}
			}
		});

		inputPassword.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setPropertyForInputHover(inputPassword, labelValidationPassword);	
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setPropertyForInputNoneHover(inputPassword, labelValidationPassword, isValidationPassword);
			}
		});

		inputPassword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(isValidationPassword && String.valueOf(inputPassword.getPassword()).trim().length() != 0) {
					isValidationPassword = false;
				}
			}
		});

		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eventLogin();
			}
		});
	}

	private void setPropertyForInput(JComponent input) {
		input.setSize(250, 30);
		input.setFont(defaultFont);
		input.setForeground(Color.BLACK);
		input.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(204, 204, 204)));
	}

	private void setPropertyForLabel(JLabel label) {
		label.setFont(Util.getFont(Constants.FONT_DEFAULT, "BOLD", 17));
		label.setSize(Util.getSizeOfString(label.getText(), label.getFont()));
		label.setForeground(Constants.COLOR_PRIMARY);
	}

	private void setPropertyForLabelValidation(JLabel validation) {
		validation.setFont(defaultFont);
		validation.setSize(250,30);
		validation.setForeground(Constants.COLOR_MESSAGE_VALIDATION);
		validation.setVisible(false);
	}

	private void setPropertyForInputHover(JComponent input, JLabel labelValidation) {
		input.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Constants.COLOR_PRIMARY));
		input.setForeground(Constants.COLOR_PRIMARY);
		labelValidation.setVisible(false);
	}

	private void setPropertyForInputNoneHover(JComponent input, JLabel labelValidation, boolean isValidation) {
		if(isValidation) {
			activeValidation(input, labelValidation, labelValidation.getText());
			return;
		}
		input.setForeground(Color.BLACK);
		input.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(204, 204, 204)));
	}

	private void activeValidation(JComponent input, JLabel labelValidation, String message) {
		input.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Constants.COLOR_TEXTFIELD_DANGER_BORDER));
		labelValidation.setText(message);
		labelValidation.setVisible(true);
	}

	private void eventLogin() {
		String username = inputUsername.getText().trim();
		String password = String.valueOf(inputPassword.getPassword()).trim();

		boolean isError = false;
		if(username.length() == 0) {
			activeValidation(inputUsername, labelValidationUsername, "Bạn chưa nhập tên tài khoản");
			isValidationUsername = true;
			isError = true;
		}

		if(password.length() == 0) {
			activeValidation(inputPassword, labelValidationPassword, "Bạn chưa nhập mật khẩu");
			isValidationPassword = true;
			isError = true;
		}

		if(isError) {
			return;
		}

		String maNhanVien = accountBLL.kiemTraDangNhap(username, password);
		if(maNhanVien.length() == 0) {
			showMessage("Tên đăng nhập hoặc mật khẩu không đúng");
			return;
		}

		UserToken.khoiTaoUserToken(maNhanVien);	
		showMessage("Đăng nhập thành công");
		displayRegistry.mainFrameRegistry(new MainGUI(Toolkit.getDefaultToolkit().getScreenSize()));	
	}

	private void showMessage(String message) {
		JOptionPane.showMessageDialog(displayRegistry.getMainFrame(), message);
	}
}
