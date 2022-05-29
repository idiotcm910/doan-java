package BLL;

import DAL.BillDAL;
import DTO.HoaDonDTO;
import DTO.ProductDTO;
import DTO.BillDetailsDTO;
import common.Date;
import java.util.ArrayList;

public class BillBLL {
	private static BillBLL instance = null;
	private BillDAL billDAL;
	private ArrayList<HoaDonDTO> bills;

	private BillBLL() {
		this.billDAL = new BillDAL();
		bills = billDAL.getAllBill();
	}

	public static BillBLL getInstance() {
		if(instance == null) {
			instance = new BillBLL();
		}
		return instance;
	}

	public ArrayList<HoaDonDTO> getAllBill() {
		return bills;
	}

	public void insertBill(HoaDonDTO hoaDonData) {
		bills.add(hoaDonData);
		billDAL.InsertBill(hoaDonData);
	}

	public boolean kiemTraMaHoaDon(String maHoaDon) {
		boolean isExist = false;	

		for(HoaDonDTO bill : bills) {
			if(bill.getMaHoaDon().equals(maHoaDon)) {
				isExist = true;
				break;
			}
		}

		return isExist;
	}
	

	public ArrayList<HoaDonDTO> thongKeTheoNgay(String dayFrom, String dayTo) {
		ArrayList<HoaDonDTO> result = new ArrayList<HoaDonDTO>();
		
		try {
			Date dateFrom = new Date(dayFrom);
			Date dateTo = new Date(dayTo);
			for(HoaDonDTO bill : bills) {
				Date dateBill = new Date(bill.getNgayNhapThongTin());
				if(Date.checkDateInTwoDate(dateBill, dateFrom, dateTo)) {
					result.add(bill);
				}			
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}

		return result;
	}

	public ArrayList<HoaDonDTO> timKiemNangCaoHoaDonTheoAND(String dayTo, String dayFrom, int tongTienMin, int tongTienMax, String maNhanVien, String maKhachHang) {
		ArrayList<HoaDonDTO> result = new ArrayList<HoaDonDTO>();
		
		boolean hasConditionTime = dayTo.length() != 0 && dayFrom.length() != 0;
		boolean hasConditionTongTien = tongTienMin != -1 && tongTienMax != -1;

		Date dateFrom = null;
		Date dateTo = null;
		if(hasConditionTime) {
			try {
				dateFrom = new Date(dayFrom);
				dateTo = new Date(dayTo);
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}


		boolean isCheckConditions;
		for(HoaDonDTO bill : bills) {
			isCheckConditions = true;
			if(hasConditionTime) {
				try {
					Date date = new Date(bill.getNgayNhapThongTin());
					isCheckConditions = (Date.checkDateInTwoDate(date, dateFrom, dateTo))? true : false;
				}
				catch(Exception ex) {
					ex.printStackTrace();
				}
			}	

			if(hasConditionTongTien && isCheckConditions) {
				isCheckConditions = (bill.getTongTien() >= tongTienMin && bill.getTongTien() <= tongTienMax)? true : false;
			}

			if(isCheckConditions) {
				String maKhachHangString = bill.getMaKhachHang();
				if(maKhachHang.equals("NOTNULL")) {
					isCheckConditions = (!maKhachHangString.equals("NULL") && maKhachHangString.contains(""))? true : false;	
				}
				else {
					isCheckConditions = (maKhachHangString.contains(maKhachHang))? true : false;	
				}
			}

			if(isCheckConditions) {
				isCheckConditions = (bill.getMaNhanVien().contains(maNhanVien))? true : false;
			}

			if(isCheckConditions == true) {
				result.add(bill);
			}
		}

		return result;
	}

	public ArrayList<HoaDonDTO> timKiemNangCaoHoaDonTheoOR(String dayTo, String dayFrom, int tongTienMin, int tongTienMax, String maNhanVien, String maKhachHang) {
		ArrayList<HoaDonDTO> result = new ArrayList<HoaDonDTO>();
		
		boolean hasConditionTime = dayTo.length() != 0 && dayFrom.length() != 0;
		boolean hasConditionTongTien = tongTienMin != -1 && tongTienMax != -1;
		boolean hasConditionNhanVien = maNhanVien.length() != 0;
		boolean hasConditionKhachHang = maKhachHang.length() != 0;

		if(hasConditionTime) {
			ArrayList<HoaDonDTO> resultTime = timKiemNangCaoHoaDonTheoAND(dayTo, dayFrom, -1, -1, "", "");
			result = ORHaiMang(result, resultTime);
		}

		if(hasConditionTongTien) {
			ArrayList<HoaDonDTO> resulTongTien = timKiemNangCaoHoaDonTheoAND("", "", tongTienMin, tongTienMax, "", "");
			result = ORHaiMang(result, resulTongTien);
		}

		if(hasConditionNhanVien) {
			ArrayList<HoaDonDTO> resultNhanVien = timKiemNangCaoHoaDonTheoAND("", "", -1, -1, maNhanVien, "");
			result = ORHaiMang(result, resultNhanVien);
		}

		if(hasConditionKhachHang) {
			ArrayList<HoaDonDTO> resultKhachHang = timKiemNangCaoHoaDonTheoAND("", "", -1, -1, "", maKhachHang);
			result = ORHaiMang(result, resultKhachHang);
		}

		return result;
	}

	public ArrayList<HoaDonDTO> ORHaiMang(ArrayList<HoaDonDTO> arrayFirst, ArrayList<HoaDonDTO> arraySecond) {
		ArrayList<HoaDonDTO> result = new ArrayList<HoaDonDTO>();

		ArrayList<HoaDonDTO> mangGop = new ArrayList<HoaDonDTO>();
		mangGop.addAll(arrayFirst);
		mangGop.addAll(arraySecond);

		for(HoaDonDTO hoaDonGop : mangGop) {
			boolean hasSame = false;

			for(HoaDonDTO hoaDonResult : result) {
				if(hoaDonGop.equals(hoaDonResult)) {
					hasSame = true;
					break;
				}
			}

			if(!hasSame) {
				result.add(hoaDonGop);
			}
		}

		return result;
	}

	public int thongKeDoanhThuTheoNgay(String dayFrom, String dayTo) {
		int total = 0;

		ArrayList<HoaDonDTO> result = thongKeTheoNgay(dayFrom, dayTo);

		for(HoaDonDTO bill : result) {
			total += bill.getTongTien();
		}

		return total;
	}

	public ArrayList<ArrayList<String>> thongKeSanPhamCoTrongDuLieuHoaDon(ArrayList<HoaDonDTO> data) {
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();

		if(data.size() == 0) {
			return result;
		}

		ArrayList<ProductDTO> products = ProductBLL.getInstance().getAllProduct();
		BillDetailsBLL detailsBLL = BillDetailsBLL.getInstance();
		int[] flags = new int[products.size()];
		for(int i = 0; i < flags.length; ++i) {
			flags[i] = 0;
		}

		// Dếm xem mỗi sản phẩm xuất hiện bao nhiêu lần trong chi tiết hóa đơn
		for(HoaDonDTO hoaDon : data) {
			
			for(BillDetailsDTO billDetail : detailsBLL.getAllBillDetails(hoaDon.getMaHoaDon())) {
				
				for(int i = 0; i < products.size(); ++i) {
					if(products.get(i).getMaSanPham().equals(billDetail.getMaSanPham())) {
						flags[i] += 1;
						break;
					}
				}
			}
		}

		ArrayList<Integer> resultSapXep = new ArrayList<Integer>();
		for(int i = 0; i < flags.length; ++i) {
			if(flags[i] != 0) {
				resultSapXep.add(i);
			}
		}

		// sap xep lai
		for(int i = 0; i < resultSapXep.size() - 1; ++i) {
			for(int j = i + 1; j < resultSapXep.size(); ++j) {
				if(flags[resultSapXep.get(i)] < flags[resultSapXep.get(j)]) {
					int temp = resultSapXep.get(i);
					resultSapXep.set(i, resultSapXep.get(j));
					resultSapXep.set(j, temp);
				}
			}	
		}
		
		// đổ dữ liệu vào bảng
		for(int i = 0; i < resultSapXep.size(); ++i) {
			ProductDTO product = products.get(resultSapXep.get(i));

			ArrayList<String> dataRow = new ArrayList<String>();
			dataRow.add(Integer.toString(i + 1));
			dataRow.add(product.getMaSanPham());
			dataRow.add(product.getTenSanPham());
			dataRow.add(product.getLoaiSanPham());
			dataRow.add(Integer.toString(flags[resultSapXep.get(i)]));
			result.add(dataRow);
		}

		return result;
	}

	public int thongKeDoanhThuTrungBinhMoiNgay(String dayFrom, String dayTo) {
		int total = 0;
		
		try {
			Date dateFrom = new Date(dayFrom);
			Date dateTo = new Date(dayTo);

			ArrayList<HoaDonDTO> result = thongKeTheoNgay(dayFrom, dayTo);
			int soNgayThongKe = Date.countDayInTwoDate(dateFrom, dateTo);
			
			for(HoaDonDTO bill : result) {
				total += bill.getTongTien();
			}

			total /= soNgayThongKe;
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return total;
	}

	public int thongKeHoaDonCaoNhatTheoNgay(String dayFrom, String dayTo) {
		ArrayList<HoaDonDTO> result = thongKeTheoNgay(dayFrom, dayTo);
		int max = result.get(0).getTongTien();
		for(int i = 1; i < result.size(); ++i) {
			max = (result.get(i).getTongTien() > max)? result.get(i).getTongTien() : max;
		}
		return max;
	}

	public int thongKeHoaDonThapNhatTheoNgay(String dayFrom, String dayTo) {
		ArrayList<HoaDonDTO> result = thongKeTheoNgay(dayFrom, dayTo);
		int min = result.get(0).getTongTien();
		for(int i = 1; i < result.size(); ++i) {
			min = (result.get(i).getTongTien() < min)? result.get(i).getTongTien() : min;
		}
		return min;
	}

	public ArrayList<HoaDonDTO> thongKeTheoNam(int year) {
		ArrayList<HoaDonDTO> result = new ArrayList<HoaDonDTO>();

		try {
			for(HoaDonDTO bill : bills) {
				Date date = new Date(bill.getNgayNhapThongTin());
				if(date.getYear() == year) {
					result.add(bill);
				}
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}

		return result;
	}
















	public ArrayList<HoaDonDTO> abc(int year) {
		ArrayList<HoaDonDTO> result = new ArrayList<HoaDonDTO>();

		// 2022-2-2
		try {
			for(HoaDonDTO bill : bills) {
				Date dateBill = new Date(bill.getNgayNhapThongTin());

				if(dateBill.getYear() == year) {
					result.add(bill);
				}
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}

		return result;
	}





























	public int thongKeDoanhThuTheoNam(int year) {
		int total = 0;

		ArrayList<HoaDonDTO> result = thongKeTheoNam(year);
		for(HoaDonDTO bill : result) {
			total += bill.getTongTien();
		}

		return total;
	}

	public int thongKeDoanhThuTrungBinhMotThangTheoNam(int year) {
		int total = 0;

		ArrayList<HoaDonDTO> result = thongKeTheoNam(year);
		for(HoaDonDTO bill : result) {
			total += bill.getTongTien();
		}

		return total / 12;
	}

	public int thongKeHoaDonCaoNhatTheoNam(int year) {
		ArrayList<HoaDonDTO> result = thongKeTheoNam(year);
		int max = result.get(0).getTongTien();
		for(int i = 1; i < result.size(); ++i) {
			max = (result.get(i).getTongTien() > max)? result.get(i).getTongTien() : max;
		}
		return max;
	}

	public int thongKeHoaDonThapNhatTheoNam(int year) {
		ArrayList<HoaDonDTO> result = thongKeTheoNam(year);
		int min = result.get(0).getTongTien();
		for(int i = 1; i < result.size(); ++i) {
			min = (result.get(i).getTongTien() < min)? result.get(i).getTongTien() : min;
		}
		return min;
	}
	public ArrayList<HoaDonDTO> thongKeTheoThang(int month, int year) {
		ArrayList<HoaDonDTO> result = new ArrayList<HoaDonDTO>();

		try {
			for(HoaDonDTO bill : bills) {
				Date date = new Date(bill.getNgayNhapThongTin());
				if(date.getYear() == year && date.getMonth() == month) {
					result.add(bill);
				}
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}

		return result;
	}

	public int thongKeDoanhThuTheoThang(int month, int year) {
		int total = 0;

		ArrayList<HoaDonDTO> result = thongKeTheoThang(month, year);
		for(HoaDonDTO bill : result) {
			total += bill.getTongTien();
		}

		return total;
	}

	public int thongKeDoanhThuTrungBinhMotNgayTheoThang(int month, int year) {
		int total = 0;

		ArrayList<HoaDonDTO> result = thongKeTheoThang(month, year);
		for(HoaDonDTO bill : result) {
			total += bill.getTongTien();
		}

		return total / Date.countDayInMonth(year, month);
	}

	public int thongKeHoaDonCaoNhatTheoThang(int month, int year) {
		ArrayList<HoaDonDTO> result = thongKeTheoThang(month, year);
		int max = result.get(0).getTongTien();
		for(int i = 1; i < result.size(); ++i) {
			max = (result.get(i).getTongTien() > max)? result.get(i).getTongTien() : max;
		}
		return max;
	}

	public int thongKeHoaDonThapNhatTheoThang(int month, int year) {
		ArrayList<HoaDonDTO> result = thongKeTheoThang(month, year);
		int min = result.get(0).getTongTien();
		for(int i = 1; i < result.size(); ++i) {
			min = (result.get(i).getTongTien() < min)? result.get(i).getTongTien() : min;
		}
		return min;
	}
}

