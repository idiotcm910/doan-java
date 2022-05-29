package GUI.taikhoangui;

import components.AppTable;
import DTO.AccountDTO;
import BLL.AccountBLL;
import DTO.NhanVienDTO;
import BLL.NhanVienBLL;

import common.FlexBox;
import common.Util;
import common.FrameDisplayRegistry;
import common.Constants;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Cursor;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.table.*;

public class TaiKhoanTablePanel extends JPanel {
	
	private JPanel tableAccountsPanel;
	AppTable tableAccounts;

	private JPanel tableStaffsNotAccountPanel;
	AppTable tableStaffsNotAccount;

	private NhanVienBLL nhanVienBLL;
	private AccountBLL accountBLL;

	public TaiKhoanTablePanel() {
		setLayout(null);
		initVariable();
	}

	private void initVariable() {
		tableAccountsPanel = new JPanel(null);
		tableStaffsNotAccountPanel = new JPanel(null);
		nhanVienBLL = NhanVienBLL.getInstance();
		accountBLL = AccountBLL.getInstance();
	}

	public void initComponentInPanel() {
		FlexBox flexBoxForPanel = new FlexBox(this, FlexBox.DIRECTION_COLUMN);

		flexBoxForPanel.setPositionWithinPercentSize(50, 0, tableAccountsPanel);
		initComponentInTableAccountsPanel();

		flexBoxForPanel.setPositionWithinPercentSize(48, 0, tableStaffsNotAccountPanel);
		initComponentInTableStaffsNotAccountPanel();
	}

	private void initComponentInTableAccountsPanel() {
		tableAccountsPanel.setBackground(Color.WHITE);
		FlexBox flexBoxForPanel = new FlexBox(tableAccountsPanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForPanel.setPadding(15);

		JLabel titleLabel = new JLabel("Bảng danh sách tài khoản");
		titleLabel.setFont(Util.getFont("Montserrat", "BOLD", 20));
		titleLabel.setForeground(Color.BLACK);
		titleLabel.setSize(Util.getSizeOfString(titleLabel.getText(), titleLabel.getFont()));
		flexBoxForPanel.setPosition(titleLabel, 0, 0);

		String[] columnsName = new String[] {"Mã tài khoản", "Tên tài khoản", "Mật khẩu", "Email", "Số điện thoại", "Mã nhân viên"};
		int[] columnSize = new int[] {15, 22, 13, 21, 15, 15};
		JScrollPane scroll = new JScrollPane();
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		flexBoxForPanel.setPositionWithinPercentSize(85, 15, scroll);
		tableAccounts = new AppTable(columnsName, columnSize, scroll.getSize());
		scroll.getViewport().add(tableAccounts);

		refreshTableAccounts();
	}

	public AccountDTO chuyenDuLieuBangTaiKhoan() {
		AccountDTO result = null;
		
		int index = tableAccounts.getSelectedRow();
		if(index != -1) {
			result = AccountBLL.getInstance().getAllAccounts().get(index);
		}

		return result;
	}

	public void refreshTableAccounts() {
		DefaultTableModel tableModel = (DefaultTableModel)tableAccounts.getModel();

		tableModel.setRowCount(0);
		ArrayList<AccountDTO> accounts = accountBLL.getAllAccounts();
		for(AccountDTO acc : accounts) {
			tableModel.addRow(acc.toArrayString());
		}
	}

	private void initComponentInTableStaffsNotAccountPanel() {
		tableStaffsNotAccountPanel.setBackground(Color.WHITE);
		FlexBox flexBoxForPanel = new FlexBox(tableStaffsNotAccountPanel, FlexBox.DIRECTION_COLUMN);
		flexBoxForPanel.setPadding(15);

		JLabel titleLabel = new JLabel("Danh sách nhân viên chưa có tài khoản");
		titleLabel.setFont(Util.getFont("Montserrat", "BOLD", 20));
		titleLabel.setForeground(Color.BLACK);
		titleLabel.setSize(Util.getSizeOfString(titleLabel.getText(), titleLabel.getFont()));
		flexBoxForPanel.setPosition(titleLabel, 0, 0);

		String[] columnsName = new String[] {"Mã nhân viên", "Tên nhân viên", "Ngày vào làm", "Vị trí", "Lương"};
		int[] columnSize = new int[] {15, 35, 15, 20, 15};
		JScrollPane scroll = new JScrollPane();
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		flexBoxForPanel.setPositionWithinPercentSize(85, 15, scroll);
		tableStaffsNotAccount = new AppTable(columnsName, columnSize, scroll.getSize());
		scroll.getViewport().add(tableStaffsNotAccount);

		refreshTableStaffsNotAccount();
	}

	public void refreshTableStaffsNotAccount() {
		DefaultTableModel tableModel = (DefaultTableModel)tableStaffsNotAccount.getModel();

		tableModel.setRowCount(0);
		ArrayList<NhanVienDTO> staffs = nhanVienBLL.getAllNhanVienNotAccount();
		for(NhanVienDTO nhanVien : staffs) {
			tableModel.addRow(nhanVien.toArrayString());
		}
	}

	public NhanVienDTO chuyenDuLieuBangNhanVienChuaCoTaiKhoan() {
		NhanVienDTO result = null;
		
		int index = tableStaffsNotAccount.getSelectedRow();
		if(index != -1) {
			result = NhanVienBLL.getInstance().getAllNhanVienNotAccount().get(index);
		}

		return result;
	}
}
