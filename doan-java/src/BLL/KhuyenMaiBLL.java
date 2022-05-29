package BLL;

import DAL.KhuyenMaiDAL;
import DTO.KhuyenMaiDTO;
import java.util.ArrayList;
import common.Date;

public class KhuyenMaiBLL {
	private static KhuyenMaiBLL instance = null;
	private KhuyenMaiDAL kmDAL;
	private ArrayList<KhuyenMaiDTO> kms;

	private KhuyenMaiBLL() {
		this.kmDAL = new KhuyenMaiDAL();
		kms = kmDAL.getAllKhuyenMai();
	}
	public static KhuyenMaiBLL getInstance() {
		if(instance == null) {
			instance = new KhuyenMaiBLL();
		}
		return instance;
	}

	public ArrayList<KhuyenMaiDTO> getAllKhuyenMai() {
		return kms;
	}

	public void addKhuyenMai(KhuyenMaiDTO km) {
		kms.add(km);
		kmDAL.insertKhuyenMai(km);
	}

	public ArrayList<KhuyenMaiDTO> timKiemKhuyenMaiTheoMaSanPham(String maSanPham, String ngayNhapHoaDon) {
		ArrayList<KhuyenMaiDTO> result = new ArrayList<KhuyenMaiDTO>();
		ChiTietKhuyenMaiBLL ctkmBLL = ChiTietKhuyenMaiBLL.getInstance();
		try {
			Date currentDate = new Date(ngayNhapHoaDon);

			for(KhuyenMaiDTO dto : kms) {
				Date fromDate = new Date(dto.getNgayBatDau());
				Date toDate = new Date(dto.getNgayKetThuc());
				boolean isCheckDateKhuyenMai = Date.checkDateInTwoDate(currentDate, fromDate, toDate);
				System.out.println("Flag1" + isCheckDateKhuyenMai);

				if(!isCheckDateKhuyenMai) {
					continue;
				}

				boolean hasChiTietKhuyenMai = ctkmBLL.kiemTraMaSanPhamTrongKhuyenMai(dto.getMaKhuyenMai(), maSanPham) != null;
				if(!hasChiTietKhuyenMai) {
					continue;
				}

				System.out.println("Flag2" + hasChiTietKhuyenMai);
				result.add(dto);
			}	
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}

		return result;
	}

	public ArrayList<KhuyenMaiDTO> timKiemNangCaoTheoAND(String dayFrom, String dayTo, String ten) {
		ArrayList<KhuyenMaiDTO> result = new ArrayList<KhuyenMaiDTO>();

		try {
			boolean hasSearchTime = false;
			boolean hasSearchTen = ten.length() != 0;

			// kiem tra xem co tim kiem o phan thoi gian khong
			Date dateFrom = new Date(), dateTo = new Date();
			if(dayFrom.length() != 0 && dayTo.length() != 0) {
				dateFrom = new Date(dayFrom);
				dateTo = new Date(dayTo);
				hasSearchTime = true;
			}

			boolean searchHasData = true;
			for(KhuyenMaiDTO khuyenMai : kms) {
				searchHasData = true;
				if(hasSearchTime) {
					Date dateFromKhuyenMai = new Date(khuyenMai.getNgayBatDau());	
					Date dateToKhuyenMai = new Date(khuyenMai.getNgayKetThuc());

					if(Date.countDayInTwoDate(dateFrom, dateFromKhuyenMai) < 1 || Date.countDayInTwoDate(dateToKhuyenMai, dateTo) < 1) {
						searchHasData = false;	
					}
				}

				if(hasSearchTen && searchHasData) {
					if(!khuyenMai.getTenKhuyenMai().contains(ten)) {
						searchHasData = false;
					}
				}

				if(searchHasData) {
					result.add(khuyenMai);
				}

			}
		}
		catch(Exception ex) {

		}

		return result;
	}

	public ArrayList<KhuyenMaiDTO> timKiemNangCaoTheoOR(String dayFrom, String dayTo, String ten) {
		ArrayList<KhuyenMaiDTO> result = new ArrayList<KhuyenMaiDTO>();

		boolean hasConditionTime = dayFrom.length() != 0 && dayTo.length() != 0;
		boolean hasConditionTen = ten.length() != 0;

		if(hasConditionTime) {
			ArrayList<KhuyenMaiDTO> array = timKiemNangCaoTheoAND(dayFrom, dayTo, "");
			result = ORHaiMang(result, array);
		}

		if(hasConditionTen) {
			ArrayList<KhuyenMaiDTO> array = timKiemNangCaoTheoAND("", "", ten);
			result = ORHaiMang(result, array);
		}

		return result;
	}

	public ArrayList<KhuyenMaiDTO> ORHaiMang(ArrayList<KhuyenMaiDTO> arrayFirst, ArrayList<KhuyenMaiDTO> arraySecond) {
		ArrayList<KhuyenMaiDTO> result = new ArrayList<KhuyenMaiDTO>();

		ArrayList<KhuyenMaiDTO> mangGop = new ArrayList<KhuyenMaiDTO>();
		mangGop.addAll(arrayFirst);
		mangGop.addAll(arraySecond);

		if(arrayFirst.size() == 0 || arraySecond.size() == 0) {
			return mangGop;
		}

		for(KhuyenMaiDTO hoaDonGop : mangGop) {
			boolean hasSame = false;

			for(KhuyenMaiDTO hoaDonResult : result) {
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

	public KhuyenMaiDTO getOneKhuyenMai(String maKhuyenMai) {
		KhuyenMaiDTO result = null;

		for(KhuyenMaiDTO km : kms) {
			if(km.getMaKhuyenMai().equals(maKhuyenMai)) {
				result = km;
				break;
			}
		}

		return result;
	}

	public boolean kiemTraMaKhuyenMai(String maKhuyenMai) {
		boolean isExist = false;

		for(KhuyenMaiDTO km : kms) {
			if(km.getMaKhuyenMai().equals(maKhuyenMai)) {
				isExist = true;
				break;
			}
		}

		return isExist;
	}

	public boolean kiemTraTenKhuyenMai(String tenKhuyenMai) {
		boolean isExist = false;

		for(KhuyenMaiDTO km : kms) {
			if(km.getTenKhuyenMai().equals(tenKhuyenMai)) {
				isExist = true;
				break;
			}
		}

		return isExist;
	}
}
