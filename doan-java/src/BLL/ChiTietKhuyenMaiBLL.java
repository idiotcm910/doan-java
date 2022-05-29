package BLL;

import DAL.ChiTietKhuyenMaiDAL;
import DTO.ChiTietKhuyenMaiDTO;
import java.util.ArrayList;

public class ChiTietKhuyenMaiBLL {
	private static ChiTietKhuyenMaiBLL instance = null;
	private ChiTietKhuyenMaiDAL ctkmDAL;
	private ArrayList<ChiTietKhuyenMaiDTO> ctkms;

	private ChiTietKhuyenMaiBLL() {
		this.ctkmDAL = new ChiTietKhuyenMaiDAL();
		ctkms = ctkmDAL.getAllChiTietKhuyenMai();
	}
	public static ChiTietKhuyenMaiBLL getInstance() {
		if(instance == null) {
			instance = new ChiTietKhuyenMaiBLL();
		}
		return instance;
	}

	public ArrayList<ChiTietKhuyenMaiDTO> getAllChiTietKhuyenMai(String maKhuyenMai) {
		ArrayList<ChiTietKhuyenMaiDTO> result = new ArrayList<ChiTietKhuyenMaiDTO>();

		for(ChiTietKhuyenMaiDTO chtkm : ctkms) {
			if(chtkm.getMaKhuyenMai().equals(maKhuyenMai)) {
				result.add(chtkm);
			}
		}
		return result;
	}

	public ChiTietKhuyenMaiDTO kiemTraMaSanPhamTrongKhuyenMai(String maKhuyenMai, String maSanPham) {
		ChiTietKhuyenMaiDTO result = null;

		for(ChiTietKhuyenMaiDTO ctkm : getAllChiTietKhuyenMai(maKhuyenMai)) {
			if(ctkm.getMaSanPham().equals(maSanPham)) {
				result = ctkm;
				break;
			}
		}

		return result;
	}

	public void addChiTietKhuyenMai(ChiTietKhuyenMaiDTO ctkm) {
		ctkms.add(ctkm);
		ctkmDAL.insertChiTietKhuyenMai(ctkm);
	}

	public void addChiTietKhuyenMai(ArrayList<ChiTietKhuyenMaiDTO> mang) {
		for(ChiTietKhuyenMaiDTO ctkm : mang) {
			ctkms.add(ctkm);
			ctkmDAL.insertChiTietKhuyenMai(ctkm);
		}
	}

	public ArrayList<ChiTietKhuyenMaiDTO> timKiemNangCaoTheoAND(String maKhuyenMai, int tiLeFrom, int tiLeTo, String maSanPham) {
		ArrayList<ChiTietKhuyenMaiDTO> result = new ArrayList<ChiTietKhuyenMaiDTO>();

		boolean hasSearchTiLe = tiLeFrom != 0 && tiLeTo != 0;
		boolean hasSearchMaSanPham = maSanPham.length() != 0;

		boolean hasData = true;
		for(ChiTietKhuyenMaiDTO ctkm : getAllChiTietKhuyenMai(maKhuyenMai)) {
			if(hasSearchTiLe) {
				if(ctkm.getPhanTramKhuyenMai() < tiLeFrom || ctkm.getPhanTramKhuyenMai() > tiLeTo) {
					hasData = false;
				}
			}

			if(hasSearchMaSanPham && hasData) {
				if(!ctkm.getMaSanPham().contains(maSanPham)) {
					hasData = false;
				}
			}

			if(hasData) {
				result.add(ctkm);
			}

			hasData = true;
		}

		return result;
	}

	public ArrayList<ChiTietKhuyenMaiDTO> timKiemNangCaoTheoOR(String maKhuyenMai, int tiLeFrom, int tiLeTo, String maSanPham) {
		ArrayList<ChiTietKhuyenMaiDTO> result = new ArrayList<ChiTietKhuyenMaiDTO>();
	
		boolean hasSearchTiLe = tiLeFrom != 0 && tiLeTo != 0;
		boolean hasSearchMaSanPham = maSanPham.length() != 0;
	
		if(hasSearchTiLe) {
			ArrayList<ChiTietKhuyenMaiDTO> array = timKiemNangCaoTheoAND(maKhuyenMai, tiLeFrom, tiLeTo, "");
			result = ORHaiMang(result, array);
		}
	
		if(hasSearchMaSanPham) {
			ArrayList<ChiTietKhuyenMaiDTO> array = timKiemNangCaoTheoAND(maKhuyenMai, 0, 0, maSanPham);
			result = ORHaiMang(result, array);
		}
	
		return result;
	}
	
	public ArrayList<ChiTietKhuyenMaiDTO> ORHaiMang(ArrayList<ChiTietKhuyenMaiDTO> arrayFirst, ArrayList<ChiTietKhuyenMaiDTO> arraySecond) {
		ArrayList<ChiTietKhuyenMaiDTO> result = new ArrayList<ChiTietKhuyenMaiDTO>();
	
		ArrayList<ChiTietKhuyenMaiDTO> mangGop = new ArrayList<ChiTietKhuyenMaiDTO>();
		mangGop.addAll(arrayFirst);
		mangGop.addAll(arraySecond);
	
		if(arrayFirst.size() == 0 || arraySecond.size() == 0) {
			return mangGop;
		}
	
		for(ChiTietKhuyenMaiDTO hoaDonGop : mangGop) {
			boolean hasSame = false;
	
			for(ChiTietKhuyenMaiDTO hoaDonResult : result) {
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
}
