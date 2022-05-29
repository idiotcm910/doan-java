package BLL;

import DAL.PhieuNhapDAL;
import DTO.PhieuNhapDTO;
import common.Date;
import java.util.ArrayList;

public class PhieuNhapBLL {
	private static PhieuNhapBLL instance = null;
	private PhieuNhapDAL phieuNhapDAL;
	private ArrayList<PhieuNhapDTO> phieuNhaps;

	private PhieuNhapBLL() {
		this.phieuNhapDAL = new PhieuNhapDAL();
		phieuNhaps = phieuNhapDAL.getAllPhieuNhap();
	}

	public static PhieuNhapBLL getInstance() {
		if(instance == null) {
			instance = new PhieuNhapBLL();
		}
		return instance;
	}

	public ArrayList<PhieuNhapDTO> getAllPhieuNhap() {
		return phieuNhaps;
	}

	public void addPhieuNhap(PhieuNhapDTO pn) {
		phieuNhaps.add(pn);
		phieuNhapDAL.InsertPhieuNhap(pn);
	}

	public boolean kiemTraMaPhieu(String maPhieu) {
		boolean isSame = false;

		for(PhieuNhapDTO pn : phieuNhaps) {
			if(pn.getMaPhieu().trim().equals(maPhieu)) {
				isSame = true;
				break;
			}
		}

		return isSame;
	}

	public ArrayList<PhieuNhapDTO> timKiemNangCaoTheoAND(String dayFrom, String dayTo, String maNhaCungCap, String maNhanVien, int min, int max) {
		ArrayList<PhieuNhapDTO> result = new ArrayList<PhieuNhapDTO>();

		boolean hasConditionTime = dayFrom.length() != 0 && dayTo.length() != 0;
		boolean hasConditionNhaCungCap = maNhaCungCap.length() != 0;
		boolean hasConditionNhanVien = maNhanVien.length() != 0;
		boolean hasConditionTongTien = min != 0 && max != 0;

		try {
			Date dateFrom = null, dateTo = null, dateCurrent = null;
			if(hasConditionTime) {
				dateFrom = new Date(dayFrom);
				dateTo = new Date(dayTo);
			}

			boolean hasCondition = false;
			for(PhieuNhapDTO dto : phieuNhaps) {
				hasCondition = true;
				if(hasConditionTime) {
					dateCurrent = new Date(dto.getNgayNhap());	

					hasCondition = (Date.checkDateInTwoDate(dateCurrent, dateFrom, dateTo))? true : false;
				}

				if(hasCondition && hasConditionNhaCungCap) {
					hasCondition = (dto.getMaNhaCungCap().contains(maNhaCungCap))? true : false;
				}

				if(hasCondition && hasConditionNhanVien) {
					hasCondition = (dto.getMaNhanVien().contains(maNhanVien))? true : false;
				}

				if(hasCondition && hasConditionTongTien) {
					hasCondition = (dto.getTongTien() >= min && dto.getTongTien() <= max)? true : false;
				}

				if(hasCondition) {
					result.add(dto);
				}
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}

		return result;
	}


	public ArrayList<PhieuNhapDTO> timKiemNangCaoTheoOR(String dayFrom, String dayTo, String maNhaCungCap, String maNhanVien, int min, int max) {
		ArrayList<PhieuNhapDTO> result = new ArrayList<PhieuNhapDTO>();

		boolean hasConditionTime = dayFrom.length() != 0 && dayTo.length() != 0;
		boolean hasConditionNhaCungCap = maNhaCungCap.length() != 0;
		boolean hasConditionNhanVien = maNhanVien.length() != 0;
		boolean hasConditionTongTien = min != 0 && max != 0;

		if(hasConditionTime) {
			ArrayList<PhieuNhapDTO> array = timKiemNangCaoTheoAND(dayFrom, dayTo, "", "", 0, 0);
			result = ORHaiMang(result, array);
		}

		if(hasConditionNhaCungCap) {
			ArrayList<PhieuNhapDTO> array = timKiemNangCaoTheoAND("", "", maNhaCungCap, "", 0, 0);
			result = ORHaiMang(result, array);
		}

		if(hasConditionNhanVien) {
			ArrayList<PhieuNhapDTO> array = timKiemNangCaoTheoAND("", "", "", maNhanVien, 0, 0);
			result = ORHaiMang(result, array);
		}

		if(hasConditionTongTien) {
			ArrayList<PhieuNhapDTO> array = timKiemNangCaoTheoAND("", "", "", "", min, max);
			result = ORHaiMang(result, array);
		}

		return result;
	}

	public ArrayList<PhieuNhapDTO> ORHaiMang(ArrayList<PhieuNhapDTO> arrayFirst, ArrayList<PhieuNhapDTO> arraySecond) {
		ArrayList<PhieuNhapDTO> result = new ArrayList<PhieuNhapDTO>();

		ArrayList<PhieuNhapDTO> mangGop = new ArrayList<PhieuNhapDTO>();
		mangGop.addAll(arrayFirst);
		mangGop.addAll(arraySecond);

		if(arrayFirst.size() == 0 || arraySecond.size() == 0) {
			return mangGop;
		}

		for(PhieuNhapDTO hoaDonGop : mangGop) {
			boolean hasSame = false;

			for(PhieuNhapDTO hoaDonResult : result) {
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

	public ArrayList<PhieuNhapDTO> thongKeTheoNgay(String dayFrom, String dayTo) {
		ArrayList<PhieuNhapDTO> result = new ArrayList<PhieuNhapDTO>();
		
		try {
			Date dateFrom = new Date(dayFrom);
			Date dateTo = new Date(dayTo);
			
			for(PhieuNhapDTO phieuNhap : phieuNhaps) {
				Date datePhieuNhap = new Date(phieuNhap.getNgayNhap());		
				if(Date.checkDateInTwoDate(datePhieuNhap, dateFrom, dateTo)) {
					result.add(phieuNhap);
				}
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}

		return result;
	}


	public int thongKeChiTheoNgay(String dayFrom, String dayTo) {
		int total = 0;
	
		ArrayList<PhieuNhapDTO> result = thongKeTheoNgay(dayFrom, dayTo);
	
		for(PhieuNhapDTO phieuNhap : result) {
			total += phieuNhap.getTongTien();
		}
	
		return total;
	}
	
	public int thongKeChiTrungBinhMoiNgay(String dayFrom, String dayTo) {
		int total = 0;
		
		ArrayList<PhieuNhapDTO> result = thongKeTheoNgay(dayFrom, dayTo);
		try {
			Date dateFrom = new Date(dayFrom);
			Date dateTo = new Date(dayTo);
			int soNgayThongKe = Date.countDayInTwoDate(dateFrom, dateTo);
			
			for(PhieuNhapDTO phieuNhap : result) {
				total += phieuNhap.getTongTien();
			}
	
			total /= soNgayThongKe;
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return total;
	}

	public int thongKePhieuNhapLonNhatTheoNgay(String dayFrom, String dayTo) {
		ArrayList<PhieuNhapDTO> result = thongKeTheoNgay(dayFrom, dayTo);
		int max = result.get(0).getTongTien();
		for(int i = 1; i < result.size(); ++i) {
			max = (result.get(i).getTongTien() > max)? result.get(i).getTongTien() : max;
		}
		return max;
	}

	public int thongKePhieuNhapThapNhapTheoNgay(String dayFrom, String dayTo) {
		ArrayList<PhieuNhapDTO> result = thongKeTheoNgay(dayFrom, dayTo);
		int min = result.get(0).getTongTien();
		for(int i = 1; i < result.size(); ++i) {
			min = (result.get(i).getTongTien() < min)? result.get(i).getTongTien() : min;
		}
		return min;
	}

	public ArrayList<PhieuNhapDTO> thongKeTheoNam(int year) {
		ArrayList<PhieuNhapDTO> result = new ArrayList<PhieuNhapDTO>();

		try {
			for(PhieuNhapDTO phieuNhap : phieuNhaps) {
				Date date = new Date(phieuNhap.getNgayNhap());
				if(date.getYear() == year) {
					result.add(phieuNhap);
				}
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}

		return result;
	}

	public int thongKeChiTheoNam(int year) {
		int total = 0;

		ArrayList<PhieuNhapDTO> result = thongKeTheoNam(year);
		for(PhieuNhapDTO phieuNhap : result) {
			total += phieuNhap.getTongTien();
		}

		return total;
	}

	public int thongKeChiTrungBinhMotThangTrongNam(int year) {
		int total = 0;

		ArrayList<PhieuNhapDTO> result = thongKeTheoNam(year);
		for(PhieuNhapDTO phieuNhap : result) {
			total += phieuNhap.getTongTien();
		}

		return total / 12;
	}

	public int thongKePhieuNhapLonNhatTheoNam(int year) {
		ArrayList<PhieuNhapDTO> result = thongKeTheoNam(year);
		int max = result.get(0).getTongTien();
		for(int i = 1; i < result.size(); ++i) {
			max = (result.get(i).getTongTien() > max)? result.get(i).getTongTien() : max;
		}
		return max;
	}

	public int thongKePhieuNhapThapNhapTheoNam(int year) {
		ArrayList<PhieuNhapDTO> result = thongKeTheoNam(year);
		int min = result.get(0).getTongTien();
		for(int i = 1; i < result.size(); ++i) {
			min = (result.get(i).getTongTien() < min)? result.get(i).getTongTien() : min;
		}
		return min;
	}

	public ArrayList<PhieuNhapDTO> thongKeTheoThang(int month, int year) {
		ArrayList<PhieuNhapDTO> result = new ArrayList<PhieuNhapDTO>();

		try {
			for(PhieuNhapDTO phieuNhap : phieuNhaps) {
				Date date = new Date(phieuNhap.getNgayNhap());
				if(date.getYear() == year && date.getMonth() == month) {
					result.add(phieuNhap);
				}
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}

		return result;
	}

	public int thongKeChiTheoThang(int month, int year) {
		int total = 0;

		ArrayList<PhieuNhapDTO> result = thongKeTheoThang(month, year);
		for(PhieuNhapDTO phieuNhap : result) {
			total += phieuNhap.getTongTien();
		}

		return total;
	}

	public int thongKeChiTrungBinhMotNgayTrongThang(int month, int year) {
		int total = 0;

		ArrayList<PhieuNhapDTO> result = thongKeTheoThang(month, year);
		for(PhieuNhapDTO phieuNhap : result) {
			total += phieuNhap.getTongTien();
		}

		return total / Date.countDayInMonth(year, month);
	}

	public int thongKePhieuNhapLonNhatTheoThang(int month, int year) {
		ArrayList<PhieuNhapDTO> result = thongKeTheoThang(month, year);
		int max = result.get(0).getTongTien();
		for(int i = 1; i < result.size(); ++i) {
			max = (result.get(i).getTongTien() > max)? result.get(i).getTongTien() : max;
		}
		return max;
	}

	public int thongKePhieuNhapThapNhapTheoThang(int month, int year) {
		ArrayList<PhieuNhapDTO> result = thongKeTheoThang(month, year);
		int min = result.get(0).getTongTien();
		for(int i = 1; i < result.size(); ++i) {
			min = (result.get(i).getTongTien() < min)? result.get(i).getTongTien() : min;
		}
		return min;
	}
}

