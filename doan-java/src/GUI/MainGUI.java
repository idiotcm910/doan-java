package GUI;

import common.Constants;
import common.FlexBox;
import common.Util;
import components.ReadSettings;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;

import GUI.billgui.BillBoardGUI;
import GUI.productgui.ProductBoardGUI;
import GUI.statisticgui.StatisticBoardGUI;
import GUI.nhanviengui.NhanVienBoardGUI;
import GUI.taikhoangui.TaiKhoanBoardGUI;
import GUI.khuyenmaigui.KhuyenMaiBoardGUI;
import GUI.phieunhapgui.PhieuNhapBoardGUI;
import GUI.DashboardGUI;
import common.FrameDisplayRegistry;
import components.UserToken;

public class MainGUI extends JFrame {
	private int indexFlag;
	private String urlFolderIcons;
	private String textButtons[];
	private JButton arrayButton[];
	private String nameIcons[];
	private JPanel taskBarPanel;
	private JPanel contentPanel;
	private Dimension sizeTaskBarPanel;
	private Dimension sizeContentPanel;

	private ProductBoardGUI productBoard;
	private StatisticBoardGUI statisticBoard;
	private BillBoardGUI billBoard;
	private UserBoardGUI userBoard;
	private NhanVienBoardGUI nhanVienBoard;
	private TaiKhoanBoardGUI taiKhoanBoard;
	private KhuyenMaiBoardGUI khuyenMaiBoard;
	private PhieuNhapBoardGUI phieuNhapBoard;
	private DashboardGUI dashboard;
	private JPanel panelFlag;

	private JButton logoutButton;
	private String nameIconLogout;

	private FrameDisplayRegistry displayRegistry;
	private UserToken token;

	public MainGUI(Dimension size) {
		setSize(size);
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initVariable();
		initComponent();
	}

	private void initVariable() {
		this.indexFlag = -1;
		urlFolderIcons = ReadSettings.getInstance().getConfig("pathDirIcons") + "%s.png";
		taskBarPanel = new JPanel(null);
		contentPanel = new JPanel(null);
		textButtons = new String[] {"Trang Chủ", "Sản Phẩm", "Thống Kê", "Hóa Đơn", "Tài khoản", "Nhân viên", "Khuyến mãi", "Phiếu nhập hàng", "Thông tin"};
		nameIcons = new String[] {"home", "product", "bar-chart", "bill", "user", "staff", "discount", "import", "portrait"};
		arrayButton = new JButton[textButtons.length];
		// lay chieu cao cua thanh dieu huong chua nut thoat chuong trinh
		int widthTopBar;
		JFrame frame = new JFrame();
		frame.pack();
		widthTopBar = frame.getInsets().top;
		frame.dispose();

		sizeTaskBarPanel = new Dimension(Util.calculatePrecent(15, getWidth()), getHeight() - widthTopBar);
		sizeContentPanel = new Dimension((int)(getWidth() - sizeTaskBarPanel.getWidth()), getHeight() -widthTopBar);

		logoutButton = new JButton();
		nameIconLogout = "logout";
		displayRegistry = FrameDisplayRegistry.getInstance();
		token = UserToken.getInstance();
	}

	private void initComponent() {
		FlexBox flexBoxForFrame = new FlexBox(this, FlexBox.DIRECTION_ROW);

		initComponentInTaskBar();
		flexBoxForFrame.setPosition(taskBarPanel, 0, 0);

		initComponentInContentPanel();
		flexBoxForFrame.setPosition(contentPanel, 0, 0);
	}

	private void initComponentInTaskBar() {
		taskBarPanel.setSize(sizeTaskBarPanel);
		taskBarPanel.setBackground(Color.WHITE);
		FlexBox flex = new FlexBox(taskBarPanel, FlexBox.DIRECTION_COLUMN);
		flex.setPadding(10);
		
		JLabel labelLogo = new JLabel(Util.getImage(ReadSettings.getInstance().getConfig("pathDirImage") + "logo.png", 50, 50));
		labelLogo.setSize(50, 50);
		flex.setPosition(FlexBox.ALIGMENT_CENTER, 0, labelLogo);
		
		JLabel labelTitle = new JLabel("MINISHOP");
		labelTitle.setForeground(Constants.COLOR_PRIMARY);
		labelTitle.setFont(Util.getFont("Hensa", "NORMAL", 35));
		labelTitle.setSize(Util.getSizeOfString(labelTitle.getText(), labelTitle.getFont()));
		flex.setPosition(FlexBox.ALIGMENT_CENTER, 10, labelTitle);
		
		
		for(int i = 0; i < textButtons.length - 1; ++i) {
			int x0Button = (i == 0)? 50 : 15;
			arrayButton[i] = new JButton(textButtons[i]);
			// set property for JButton
			this.setPropertyForButton(arrayButton[i], nameIcons[i]);
		
			if(indexFlag == i) {
				this.setPropertyForButtonActive(arrayButton[i], nameIcons[i]);
			}
			else {
				// add mouse event for JButton
				this.addMouseEventForButton(arrayButton[i], nameIcons[i], i);	
			}
		
			// set position for JButton
			flex.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, x0Button, arrayButton[i]);
		}

		JLabel line = new JLabel();
		line.setSize(0, 2);
		line.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(70, 70, 70)));
		flex.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 170, line);

		arrayButton[8] = new JButton("Thông tin");
		setPropertyForButton(arrayButton[8], nameIcons[8]);
		addMouseEventForButton(arrayButton[8], nameIcons[8], 8);
		flex.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 5, arrayButton[8]);

		logoutButton.setText("Đăng xuất");		
		setPropertyForButton(logoutButton, nameIconLogout);
		addMouseEventForButton(logoutButton, nameIconLogout, 9);
		flex.setPosition(FlexBox.ALIGMENT_WIDTH_PARENT, 15, logoutButton);
		
	}

	private void initComponentInContentPanel() {
		contentPanel.setSize(sizeContentPanel);
		contentPanel.setBackground(Color.RED);

		handleEventButton(0);
	}
	
	private void initPanel(JPanel panel) {
		panel.setLocation(0, 0);
		contentPanel.add(panel);
		panel.setVisible(false);
	}

	private void setPropertyForButtonActive(JButton button, String nameIcon) {

		String urlIconActive = String.format(this.urlFolderIcons, nameIcon + "-active");
		button.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createMatteBorder(0, 7, 0, 0, Constants.COLOR_PRIMARY),
					BorderFactory.createEmptyBorder(5, 10, 5, 10)
		));
		button.setIcon(Util.getImage(urlIconActive, 25, 25));	
		button.setForeground(Constants.COLOR_PRIMARY);
	}

	private void setPropertyForButtonNoneActive(JButton button, String nameIcon) {
		String urlIcon = String.format(this.urlFolderIcons, nameIcon);
		button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));	
		button.setForeground(Constants.COLOR_TEXT_TASKBAR);
		button.setIcon(Util.getImage(urlIcon, 25, 25));
	}

	private void setPropertyForButton(JButton button, String nameIcon) {
		String urlIcon = String.format(this.urlFolderIcons, nameIcon);
		button.setSize(0, 50);
		button.setFocusable(false);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setIcon(Util.getImage(urlIcon, 25, 25));
		button.setBackground(Color.WHITE);
		button.setForeground(Constants.COLOR_TEXT_TASKBAR);
		button.setFont(Util.getFont("Roboto", "NORMAL", 20));
		button.setIconTextGap(10);
		button.setHorizontalAlignment(SwingConstants.LEFT);
		button.setVerticalAlignment(JButton.CENTER);
		button.setVerticalTextPosition(JButton.CENTER);
		button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));	
	}

	private void addMouseEventForButton(JButton button, String nameIcon, int index) {
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent ev) {
				if(index != indexFlag) {
					setPropertyForButtonActive(button, nameIcon);
				}
			}

			@Override
			public void mouseExited(MouseEvent ev) {
				if(index != indexFlag) {
					button.setForeground(Constants.COLOR_TEXT_TASKBAR);
					button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));	
					button.setIcon(Util.getImage(String.format(urlFolderIcons, nameIcon), 25, 25));
				}
			}
		});

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleEventButton(index);
			}
		});
	}

	private void handleEventButton(int index) {
		JPanel oldPanel = panelFlag;

		boolean isNotPermissed = false;
		switch(index) {
			case 0:
				if(dashboard == null) {
					dashboard = new DashboardGUI(sizeContentPanel);
					initPanel(dashboard);
				}
				dashboard.refreshPanel();
				panelFlag = dashboard;
				break;
			case 1:
				if(token.isPermissed(1)) {
					if(productBoard == null) {
						productBoard = new ProductBoardGUI(sizeContentPanel);
						initPanel(productBoard);
					}

					productBoard.refreshPanel();
					panelFlag = productBoard;
					break;
				}
				isNotPermissed = true;
				break;
			case 2:
				if(token.isPermissed(2)) {
					if(statisticBoard == null) {
						statisticBoard = new StatisticBoardGUI(sizeContentPanel);
						initPanel(statisticBoard);
					}

					panelFlag = statisticBoard;
					break;
				}
				isNotPermissed = true;
				break;
			case 3:
				if(token.isPermissed(3)) {
					if(billBoard == null) {
						billBoard = new BillBoardGUI(sizeContentPanel);
						initPanel(billBoard);
					}

					panelFlag = billBoard;
					break;
				}
				isNotPermissed = true;
				break;
			case 4:
				if(token.isPermissed(4)) {
					if(taiKhoanBoard == null) {
						taiKhoanBoard = new TaiKhoanBoardGUI(sizeContentPanel);
						initPanel(taiKhoanBoard);
					}

					taiKhoanBoard.refreshPanel();
					panelFlag = taiKhoanBoard;
					break;
				}

				isNotPermissed = true;
				break;
			case 5:
				if(token.isPermissed(5)) {
					if(nhanVienBoard == null) {
						nhanVienBoard = new NhanVienBoardGUI(sizeContentPanel);
						initPanel(nhanVienBoard);
					}

					nhanVienBoard.refreshPanel();
					panelFlag = nhanVienBoard;
					break;
				}	
				isNotPermissed = true;
				break;
			case 6:
				if(token.isPermissed(6)) {
					if(khuyenMaiBoard == null) {
						khuyenMaiBoard = new KhuyenMaiBoardGUI(sizeContentPanel);
						initPanel(khuyenMaiBoard);
					}

					panelFlag = khuyenMaiBoard;
					break;
				}
				isNotPermissed = true;
				break;
			case 7:
				if(token.isPermissed(7)) {
					if(phieuNhapBoard == null) {
						phieuNhapBoard = new PhieuNhapBoardGUI(sizeContentPanel);
						initPanel(phieuNhapBoard);
					}

					panelFlag = phieuNhapBoard;
					break;
				}	

				isNotPermissed = true;
				break;
			case 8:
				if(userBoard == null) {
					userBoard = new UserBoardGUI(sizeContentPanel);
					initPanel(userBoard);
				}

				panelFlag = userBoard;
				break;
			case 9:
				UserToken.refreshToken();	
				displayRegistry.mainFrameRegistry(new LoginFrame());
				return;
		}

		if(isNotPermissed) {
			showMessagePermissed();
			return;
		}

		if(oldPanel != null) {
			oldPanel.setVisible(false);
		}

		if(indexFlag != -1) {
			setPropertyForButtonNoneActive(arrayButton[indexFlag], nameIcons[indexFlag]);
		}

		setPropertyForButtonActive(arrayButton[index], nameIcons[index]);
		indexFlag = index;
		panelFlag.setVisible(true);
	}

	private void showMessagePermissed() {
		JOptionPane.showMessageDialog(displayRegistry.getMainFrame(), "Bạn không có quyền truy cập");
	}
}
