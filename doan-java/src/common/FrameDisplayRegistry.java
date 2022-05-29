package common;

import javax.swing.JFrame;

/**
 * Lớp được tạo ra với mục đích quản lý việc hiện thị của các Frame, chương trình chỉ sẽ hiển thị mỗi lần 1 Frame chính duy nhất khi chuyển sang khác Frame khác thì lớp này tự động quản lý cũng như dispose Frame cũ mà ko cần phải truyền tham số Frame mới qua Frame cũ để Frame mới dispose Frame cũ. Không chỉ thế lớp này còn quản lý cho việc hiển thị Frame phụ(như bảng chọn dữ liệu).
 * */

public class FrameDisplayRegistry {
	private static FrameDisplayRegistry instance = null;

	private JFrame mainFrame;
	private JFrame subFrame;

	private FrameDisplayRegistry() {
		this.mainFrame = null;
		this.subFrame = null;
	}

	public static FrameDisplayRegistry getInstance() {
		if(instance == null) {
			instance = new FrameDisplayRegistry();
		}
		return instance;
	}

	public JFrame getMainFrame() {
		return mainFrame;
	}

	/**
	 * Hàm dùng để đăng ký hiển thị Jframe chính, tự động dispose Frame chính cũ và hiển thị Frame chính mới lên màn hình.
	 * @param frame Frame cần đăng ký hiển thị
	 * */
	public void mainFrameRegistry(JFrame frame) {
		if(mainFrame == null) {
			this.mainFrame = frame;
		}
		else {
			mainFrame.dispose();
			this.mainFrame = frame;
		}

		frame.setVisible(true);
	}

	/**
	 * Hàm dùng để đăng ký hiển thị Frame phụ(ví dụ như hiển thị 1 bảng sản phẩm để người dùng chọn) và hàm này sẽ cài đặt các sự kiện tương tác giữa Frame chính và Frame phụ. Người dùng chỉ được tiếp tục thao tác với Frame chính khi mà Frame phụ hoàn thành việc chọn dữ liệu hoặc người dùng thoát Frame phụ.
	 * mỗi lần chỉ có thể hiển thị 1 Frame phụ duy nhất, để đăng ký tiếp tục thì Frame phụ phải được dispose(null) mới có thể đăng ký
	 * @param frame Frame can dang ky hien thi
	 * */
	public void subFrameRegistry(JFrame frame) {
		if(this.mainFrame == null) {
			return;
		}

		if(this.subFrame != null) {
			return;
		}

		this.subFrame = frame;
		this.subFrame.setVisible(true);
		this.subFrame.requestFocus();
		setTheInteractionBetweenTheSubFrameAndMainFrame();	
	}

	public void unSubFrameRegistry() {
		if(subFrame != null) {
			this.mainFrame.setEnabled(true);
			this.subFrame.setVisible(false);
			this.subFrame = null;
		}
	}

	private void setTheInteractionBetweenTheSubFrameAndMainFrame() {
		this.mainFrame.setEnabled(false);	

		this.subFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.subFrame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				unSubFrameRegistry();
			}
		});
	}
}
