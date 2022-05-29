package BLL;

import DAL.ChiTietPhieuNhapDAL;
import DTO.ChiTietPhieuNhapDTO;
import java.util.ArrayList;

public class ChiTietPhieuNhapBLL {
	private static ChiTietPhieuNhapBLL instance = null;
	private ChiTietPhieuNhapDAL ctpnDAL;
	private ArrayList<ChiTietPhieuNhapDTO> mangChiTietPhieuNhap;

	private ChiTietPhieuNhapBLL() {
		this.ctpnDAL = new ChiTietPhieuNhapDAL();
		mangChiTietPhieuNhap = ctpnDAL.getAllChiTietPhieuNhap();
	}
	public static ChiTietPhieuNhapBLL getInstance() {
		if(instance == null) {
			instance = new ChiTietPhieuNhapBLL();
		}
		return instance;
	}

	public ArrayList<ChiTietPhieuNhapDTO> getAllChiTietPhieuNhap(String maPhieuNhap) {
		ArrayList<ChiTietPhieuNhapDTO> result = new ArrayList<ChiTietPhieuNhapDTO>();

		for(ChiTietPhieuNhapDTO dto : mangChiTietPhieuNhap) {
			if(dto.getMaPhieu().equals(maPhieuNhap)) {
				result.add(dto);
			}
		}
		return result;
	}

	public ArrayList<ChiTietPhieuNhapDTO> timKiemNangCaoTheoAND(String maSanPham, int soLuongFrom, int soLuongTo, int min, int max, String maPhieu) {
		ArrayList<ChiTietPhieuNhapDTO> result = new ArrayList<ChiTietPhieuNhapDTO>();
	
		boolean hasConditionSanPham = maSanPham.length() != 0;
		boolean hasConditionSoLuong = soLuongFrom != 0 && soLuongTo != 0;
		boolean hasConditionTongTien = min != 0 && max != 0;
	
		boolean hasCondition = false;
		for(ChiTietPhieuNhapDTO dto : getAllChiTietPhieuNhap(maPhieu)) {
			hasCondition = true;
			if(hasConditionSanPham) {
				hasCondition = (dto.getMaSanPham().equals(maSanPham))? true : false;
			}

			if(hasCondition && hasConditionSoLuong) {
				hasCondition = (dto.getSoLuong() >= soLuongFrom && dto.getSoLuong() <= soLuongTo)? true : false;
			}

			if(hasCondition && hasConditionTongTien) {
				hasCondition = (dto.getThanhTien() >= min && dto.getThanhTien() <= max)? true : false;
			}

			if(hasCondition) {
				result.add(dto);
			}
		}
	
		return result;
	}
	
	
	public ArrayList<ChiTietPhieuNhapDTO> timKiemNangCaoTheoOR(String maSanPham, int soLuongFrom, int soLuongTo, int min, int max, String maPhieu) {
		ArrayList<ChiTietPhieuNhapDTO> result = new ArrayList<ChiTietPhieuNhapDTO>();
	
		boolean hasConditionSanPham = maSanPham.length() != 0;
		boolean hasConditionSoLuong = soLuongFrom != 0 && soLuongTo != 0;
		boolean hasConditionTongTien = min != 0 && max != 0;
	
		if(hasConditionSanPham) {
			ArrayList<ChiTietPhieuNhapDTO> array = timKiemNangCaoTheoAND(maSanPham, 0, 0, 0, 0, maPhieu);
			result = ORHaiMang(result, array);
		}
	
		if(hasConditionSoLuong) {
			ArrayList<ChiTietPhieuNhapDTO> array = timKiemNangCaoTheoAND("", soLuongFrom, soLuongTo, 0, 0, maPhieu);
			result = ORHaiMang(result, array);
		}
	
		if(hasConditionTongTien) {
			ArrayList<ChiTietPhieuNhapDTO> array = timKiemNangCaoTheoAND("", 0, 0, min, max, maPhieu);
			result = ORHaiMang(result, array);
		}
	
		return result;
	}
	
	public ArrayList<ChiTietPhieuNhapDTO> ORHaiMang(ArrayList<ChiTietPhieuNhapDTO> arrayFirst, ArrayList<ChiTietPhieuNhapDTO> arraySecond) {
		ArrayList<ChiTietPhieuNhapDTO> result = new ArrayList<ChiTietPhieuNhapDTO>();
	
		ArrayList<ChiTietPhieuNhapDTO> mangGop = new ArrayList<ChiTietPhieuNhapDTO>();
		mangGop.addAll(arrayFirst);
		mangGop.addAll(arraySecond);
	
		if(arrayFirst.size() == 0 || arraySecond.size() == 0) {
			return mangGop;
		}
	
		for(ChiTietPhieuNhapDTO hoaDonGop : mangGop) {
			boolean hasSame = false;
	
			for(ChiTietPhieuNhapDTO hoaDonResult : result) {
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


	public ChiTietPhieuNhapDTO kiemTraMaSanPhamTrongChiTietPhieuNhap(String maChiTietPhieuNhap, String maSanPham) {
		ChiTietPhieuNhapDTO result = null;

		for(ChiTietPhieuNhapDTO ctkm : getAllChiTietPhieuNhap(maChiTietPhieuNhap)) {
			if(ctkm.getMaSanPham().equals(maSanPham)) {
				result = ctkm;
				break;
			}
		}

		return result;
	}

	public void addChiTietPhieuNhap(ChiTietPhieuNhapDTO ctpn) {
		mangChiTietPhieuNhap.add(ctpn);
		ctpnDAL.insertChiTietPhieuNhap(ctpn);
	}

	public void addChiTietPhieuNhap(ArrayList<ChiTietPhieuNhapDTO> mang) {
		for(ChiTietPhieuNhapDTO ctpn : mang) {
			addChiTietPhieuNhap(ctpn);
		}
	}

}
