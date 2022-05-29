package GUI.productgui;

import javax.swing.JPanel;
import common.FlexBox;
import common.Util;
import common.Constants;
import common.FrameDisplayRegistry;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Font;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import DTO.ProductDTO;
import BLL.ProductBLL;
import java.util.ArrayList;

class ProductSearchPanel extends JPanel {
	// truyen form panel qua de tuong tac du lieu giua search panel va table
	private ProductTablePanel tablePanel;
	private ArrayList<ProductDTO> array;
	private ProductBLL productBLL;

	private Font defaultFont;

	// ===================== COMPONENT ===============================
	private JComboBox comboBox;
	private JButton searchButton;
	private JLabel searchLabel;
	private String[] values;

	JPanel contentInputPanel;
	// component panel Input ma san pham, ten san pham, ten loai san pham, Nam san xuat, nam het han, nha san xuat
	JTextField inputField[];

	JPanel[] inputPanel;
	// component panel so luong
	JTextField inputMinSoLuong, inputMaxSoLuong;
	// component panel don gia
	private String[] priceValues;
	private JComboBox comboBoxPriceValues;

	private int indexComboBoxSelection;
	
	private FrameDisplayRegistry displayRegistry;
	

	public ProductSearchPanel() {
		setLayout(null);
		initVariable();
	}

	public void setTablePanel(ProductTablePanel panel) {
		this.tablePanel = panel;
	}

	private void initVariable() {
		productBLL = ProductBLL.getInstance();

		values = new String[] {"Mã sản phẩm", "Tên sản phẩm",  "Tên loại sản phẩm", "Năm sản xuất", "Năm hết hạn sử dụng", "Mã nhà sản xuất", "Số lượng", "đơn giá"};	
		comboBox = new JComboBox(values);
		searchButton = new JButton("Tìm kiếm");
		searchLabel = new JLabel("Tìm kiếm theo:", SwingConstants.CENTER);

		defaultFont = Util.getFont("Roboto", "NORMAL", 15);

		contentInputPanel = new JPanel(null);

		inputPanel = new JPanel[8];
		inputField = new JTextField[6];

		// component panel input
		inputMinSoLuong = new JTextField();
		inputMaxSoLuong = new JTextField();

		priceValues = new String[] {"0 - 10.000đ", "10.000đ - 50.000đ", "50.000đ trở lên"};
		comboBoxPriceValues = new JComboBox(priceValues);

		indexComboBoxSelection = 0;

		displayRegistry = FrameDisplayRegistry.getInstance();
	}

	public void initComponentInPanel() {
		FlexBox flexBoxForSearchPanel = new FlexBox(this, FlexBox.DIRECTION_COLUMN);
		flexBoxForSearchPanel.setPadding(10);

		JLabel labelTitlePanel = new JLabel("Tìm kiếm sản phẩm");
		labelTitlePanel.setFont(Util.getFont("Montserrat", "BOLD", 25));
		labelTitlePanel.setSize(Util.getSizeOfString(labelTitlePanel.getText(), labelTitlePanel.getFont()));
		labelTitlePanel.setForeground(Color.BLACK);
		flexBoxForSearchPanel.setPosition(labelTitlePanel, 0, 0);

		JPanel contentSearchPanel = new JPanel(null);
		contentSearchPanel.setBackground(getBackground());
		flexBoxForSearchPanel.setPosition(FlexBox.ALIGNMENT_REMAINING_SIZE_OF_PANEL, 5, contentSearchPanel);
		initComponentInContentSearchPanel(contentSearchPanel);
	}

	public void initComponentInContentSearchPanel(JPanel panel) {
		FlexBox flexBoxForContentSearchPanel = new FlexBox(panel, FlexBox.DIRECTION_ROW);
		flexBoxForContentSearchPanel.setPadding(10);

		searchLabel.setFont(defaultFont);
		searchLabel.setForeground(Color.BLACK);
		flexBoxForContentSearchPanel.setPositionWithinPercentSize(12, 0, searchLabel);

		comboBox.setFont(defaultFont);
		comboBox.setBackground(panel.getBackground());
		comboBox.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Constants.COLOR_PRIMARY));
		flexBoxForContentSearchPanel.setPositionWithinPercentSize(20, 5, comboBox);

		flexBoxForContentSearchPanel.setPositionWithinPercentSize(53, 5, contentInputPanel);

		for(int i = 0; i < inputPanel.length; ++i) {
			inputPanel[i] = new JPanel(null);
			inputPanel[i].setLocation(0, 0);
			inputPanel[i].setBackground(panel.getBackground());
			inputPanel[i].setSize(contentInputPanel.getSize());
			inputPanel[i].setVisible(false);
			contentInputPanel.add(inputPanel[i]);
		}

		for(int i = 0; i < inputField.length; ++i) {
			initComponentInInputPanel(i);
		}

		initComponentInInputSoLuong(inputPanel[6]);
		initComponentInInputDonGia(inputPanel[7]);

		inputPanel[0].setVisible(true);

		searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		searchButton.setFont(Util.getFont("Roboto", "NORMAL", 16));
		searchButton.setForeground(Color.WHITE);
		searchButton.setBackground(Constants.COLOR_PRIMARY);
		searchButton.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, searchButton.getBackground()));


		searchButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				searchButton.setForeground(Constants.COLOR_PRIMARY);
				searchButton.setBackground(Color.WHITE);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				searchButton.setForeground(Color.WHITE);
				searchButton.setBackground(Constants.COLOR_PRIMARY);
			}
		});
		// set 100 de flexbox tu dong set kich thuoc phu hop voi khoang trong con lai trong panel
		flexBoxForContentSearchPanel.setPositionWithinPercentSize(100, 5, searchButton);

		// xu ly su kien
		addEventToComponentInSearchPanel();
	}

	// component cua input panel theo lua chon cua combobox
	// danh cho cac panel ma san pham, ten san pham, so luong, nam san xuat, nam het han, loai san pham, nha san xuat 
	private void initComponentInInputPanel(int index) {
		FlexBox flexBoxForInputPanel = new FlexBox(inputPanel[index], FlexBox.DIRECTION_ROW);
		flexBoxForInputPanel.setPadding(5);
		inputField[index] = new JTextField();			
		setPropertyForInputField(inputField[index]);
		flexBoxForInputPanel.setPositionWithinPercentSize(99, 0, inputField[index]);
	}

	private void initComponentInInputSoLuong(JPanel panel) {
		FlexBox flexBoxForInputPanel = new FlexBox(panel, FlexBox.DIRECTION_ROW);
		flexBoxForInputPanel.setPadding(5);

		JLabel labelTitle = new JLabel("Số lượng từ: ", SwingConstants.CENTER);
		labelTitle.setFont(defaultFont);
		labelTitle.setForeground(Color.BLACK);
		flexBoxForInputPanel.setPositionWithinPercentSize(25, 0, labelTitle);

		inputMinSoLuong = new JTextField();
		setPropertyForInputField(inputMinSoLuong);
		flexBoxForInputPanel.setPositionWithinPercentSize(30, 0, inputMinSoLuong);

		JLabel spaceTitle = new JLabel("đến", SwingConstants.CENTER);
		spaceTitle.setFont(defaultFont);
		spaceTitle.setForeground(Color.BLACK);
		flexBoxForInputPanel.setPositionWithinPercentSize(15, 0, spaceTitle);

		inputMaxSoLuong = new JTextField();
		setPropertyForInputField(inputMaxSoLuong);
		flexBoxForInputPanel.setPositionWithinPercentSize(30, 0, inputMaxSoLuong);
	}

	private void initComponentInInputDonGia(JPanel panel) {
		FlexBox flexBoxForInputPanel = new FlexBox(panel, FlexBox.DIRECTION_ROW);
		flexBoxForInputPanel.setPadding(10);

		comboBoxPriceValues.setFont(defaultFont);
		comboBoxPriceValues.setBackground(panel.getBackground());
		comboBoxPriceValues.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Constants.COLOR_PRIMARY));
		flexBoxForInputPanel.setPositionWithinPercentSize(100, 0, comboBoxPriceValues);
	}
	

	private void setPropertyForInputField(JTextField input) {
		input.setFont(defaultFont);
		input.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Constants.COLOR_PRIMARY));
		input.setBackground(getBackground());
	}

	private void addEventToComponentInSearchPanel() {
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleEventSelectionComboBox(comboBox.getSelectedIndex());
			}
		});

		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleEventClickSearchButton();
			}
		});
	}

	private void handleEventSelectionComboBox(int index) {
		inputPanel[indexComboBoxSelection].setVisible(false);
		inputPanel[index].setVisible(true);
		indexComboBoxSelection = index;
		resetSearchPanel();
	}

	private void handleEventClickSearchButton() {
		switch(indexComboBoxSelection) {
			case 0:
				handleEventMaSanPham();
				break;
			case 1:
				handleEventTenSanPham();
				break;
			case 2:
				handleEventMaLoaiSanPham();
				break;
			case 3:
				handleEventNamSanXuat();
				break;
			case 4:
				handleEventHanSuDung();
				break;
			case 5:
				handleEventNhaSanXuat();
				break;
			case 6:
				handleEventSoLuong();
				break;
			case 7:
				handleEventDonGia();
				break;
		}
	}

	private void handleEventMaSanPham() {
		String maSanPham = inputField[0].getText();
		if(maSanPham.length() == 0) {
			showMessageError("Bạn chưa nhập mã sản phẩm cần tìm kiếm");	
			return;
		}		

		array = productBLL.timKiemVoiMaSanPham(maSanPham);

		if(array.size() == 0) {
			showMessageError("không tìm thấy mã sản phẩm bạn đã nhập");
			return;
		}

		System.out.println("size: " + array.size());
		
		handleSuccessSearch(array);
	}

	private void handleEventTenSanPham() {
		String tenSanPham = inputField[1].getText();

		if(tenSanPham.length() == 0) {
			showMessageError("Bạn chưa nhập tên sản phẩm cần tìm kiếm");	
			return;
		}		

		array = productBLL.timKiemVoiTenSanPham(tenSanPham);

		if(array.size() == 0) {
			showMessageError("không tìm thấy tên sản phẩm bạn đã nhập");
			return;
		}
		
		handleSuccessSearch(array);
	}

	private void handleEventSoLuong() {
		if(inputMinSoLuong.getText().length() == 0 || inputMaxSoLuong.getText().length() == 0) {
			showMessageError("Ban chưa nhập dữ liệu số lượng để có thể tìm kiếm");
			return;
		}		

		try {
			int min = Integer.parseInt(inputMinSoLuong.getText());
			int max = Integer.parseInt(inputMaxSoLuong.getText());
			System.out.println("test: " + min + " | " + max);

			if(min == 0 || max == 0) {
				showMessageError("dữ liệu nhập vào phải là số lớn hơn 0");
				return;
			}	

			if(min >= max) {
				showMessageError("Dữ liệu ô min phải nhỏ hơn ô max");
				return;
			}

			array = productBLL.timKiemVoiSoLuong(min, max);

			if(array.size() == 0) {
				showMessageError("Không tìm thấy sản phẩm có số lượng từ " + min + " đến " + max);
				return;
			}

			handleSuccessSearch(array);
		}
		catch(Exception ex) {
			showMessageError("Vui lòng nhập dữ liệu là số");
			return;
		}
	}

	private void handleEventDonGia() {
		int min = 0, max = 0;
		switch(comboBoxPriceValues.getSelectedIndex()) {
			case 0:
				max = 10000;
				break;
			case 1:
				min = 10000;
				max = 50000;
				break;
			default:
				min = 50000;
				max = 100000000;
		}

		array = productBLL.timKiemVoiDonGia(min, max);

		if(array.size() == 0) {
			showMessageError("Không tìm thấy sản phẩm có đơn giá từ " + min + " đến " + max);
			return;
		}

		handleSuccessSearch(array);
	}

	private void handleEventMaLoaiSanPham() {
		String loaiSanPham = inputField[2].getText();

		if(loaiSanPham.length() == 0) {
			showMessageError("Bạn chưa nhập ma loại sản phẩm cần tìm kiếm");	
			return;
		}		

		array = productBLL.timKiemVoiLoaiSanPham(loaiSanPham);

		if(array.size() == 0) {
			showMessageError("không tìm thấy ma loại sản phẩm bạn đã nhập");
			return;
		}
		
		handleSuccessSearch(array);
	}

	private void handleEventNamSanXuat() {
		String namSanxuatStr = inputField[3].getText();

		if(namSanxuatStr.length() == 0) {
			showMessageError("Bạn chưa nhập năm sản xuất cần tìm kiếm");
			return;
		}

		try {
			int namSanXuat = Integer.parseInt(namSanxuatStr);

			if(namSanXuat < 1000) {
				showMessageError("năm sản xuất phải là một số lớn hơn 1000");
				return;
			}

			array = productBLL.timKiemVoiNamSanXuat(Integer.toString(namSanXuat));

			if(array.size() == 0) {
				showMessageError("Không tìm thấy sản phẩm có năm sản xuất mà bạn đã nhập");
				return;
			}

			handleSuccessSearch(array);
		}	
		catch(Exception ex) {
			showMessageError("Vui lòng nhập dữ liệu là số");
		}
	}

	private void handleEventHanSuDung() {
		String namHetHanStr = inputField[4].getText();

		if(namHetHanStr.length() == 0) {
			showMessageError("Bạn chưa nhập năm hết hạn sử dụng cần tìm kiếm");
			return;
		}

		try {
			int namHetHan = Integer.parseInt(namHetHanStr);

			if(namHetHan < 1000) {
				showMessageError("năm hết hạn sử dụng phải là một số lớn hơn 1000");
				return;
			}

			array = productBLL.timKiemVoiNamHanSuDung(Integer.toString(namHetHan));

			if(array.size() == 0) {
				showMessageError("Không tìm thấy sản phẩm có năm hết hạn sử dụng mà bạn đã nhập");
				return;
			}

			handleSuccessSearch(array);
		}	
		catch(Exception ex) {
			showMessageError("Vui lòng nhập dữ liệu là số");
		}
	}

	private void handleEventNhaSanXuat() {
		String nhaSanXuatStr = inputField[5].getText();

		if(nhaSanXuatStr.length() == 0) {
			showMessageError("Bạn chưa nhập mã nhà sản xuất cần tìm kiếm");
			return;
		}


		array = productBLL.timKiemVoiMaNhaSanXuat(nhaSanXuatStr);

		if(array.size() == 0) {
			showMessageError("Không tìm thấy sản phẩm có mã nhà sản xuất mà bạn đã nhập");
			return;
		}

		handleSuccessSearch(array);
	}

	private void showMessageError(String str) {
		JOptionPane.showMessageDialog(displayRegistry.getMainFrame(), str, "Lỗi tìm kiếm sản phẩm", JOptionPane.WARNING_MESSAGE);
	}

	private void resetSearchPanel() {
		if(indexComboBoxSelection < 6) {
			inputField[indexComboBoxSelection].setText("");
			return;
		}

		if(indexComboBoxSelection == 6) {
			inputMinSoLuong.setText("");
			inputMaxSoLuong.setText("");
			return;
		}

		comboBoxPriceValues.setSelectedIndex(0);
	}

	private void handleSuccessSearch(ArrayList<ProductDTO> array) {
		tablePanel.addValuesTable(array);	
		resetSearchPanel();
	}
}
