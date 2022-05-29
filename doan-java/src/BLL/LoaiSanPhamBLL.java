package BLL;

import DAL.LoaiSanPhamDAL;
import DTO.LoaiSanPhamDTO;
import java.util.ArrayList;

public class LoaiSanPhamBLL {
	private static LoaiSanPhamBLL instance = null;
	private LoaiSanPhamDAL loaiSPDAL;
	private ArrayList<LoaiSanPhamDTO> mangLoaiSP;

	private LoaiSanPhamBLL() {
		this.loaiSPDAL = new LoaiSanPhamDAL();
		mangLoaiSP = loaiSPDAL.getAllLoaiSanPham();
	}
	public static LoaiSanPhamBLL getInstance() {
		if(instance == null) {
			instance = new LoaiSanPhamBLL();
		}
		return instance;
	}
	
	public ArrayList<LoaiSanPhamDTO> getAllLoaiSP() {
		return mangLoaiSP;
	}

	public LoaiSanPhamDTO getLoaiSP(String maLoai) {
		LoaiSanPhamDTO loaiSP = null;
		for(LoaiSanPhamDTO lsp : mangLoaiSP) {
			if(lsp.getMaLoai().equals(maLoai)) {
				loaiSP = lsp;
			}
		}
		return loaiSP;
	}

	public ArrayList<LoaiSanPhamDTO> timKiemLoaiSP(String maLoai) {
		ArrayList<LoaiSanPhamDTO> ketqua = new ArrayList<LoaiSanPhamDTO>();
		for(LoaiSanPhamDTO lsp : mangLoaiSP) {
			if(lsp.getMaLoai().contains(maLoai)) {
				ketqua.add(lsp);
			}
		}
		return ketqua;
	}

}
