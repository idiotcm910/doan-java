package BLL;

import DAL.NhaCungCapDAL;
import DTO.NhaCungCapDTO;
import java.util.ArrayList;

public class NhaCungCapBLL {
	private static NhaCungCapBLL instance = null;
	private NhaCungCapDAL nccDAL;
	private ArrayList<NhaCungCapDTO> mangNCC;

	private NhaCungCapBLL() {
		this.nccDAL = new NhaCungCapDAL();
		mangNCC = nccDAL.getAllNhaCungCap();
	}
	public static NhaCungCapBLL getInstance() {
		if(instance == null) {
			instance = new NhaCungCapBLL();
		}
		return instance;
	}

	public ArrayList<NhaCungCapDTO> getAllNCC() {
		return mangNCC;
	}

	public NhaCungCapDTO getNCC(String maNCC) {
		NhaCungCapDTO ncc = null;
		for(NhaCungCapDTO nccDTO : mangNCC) {
			if(nccDTO.getMaNhaCungCap().equals(maNCC)) {
				ncc = nccDTO;
				break;
			}
		}
		return ncc;
	}

	public boolean kiemTraMaNhaCungCap(String maNCC) {
		boolean isSame = false;	

		for(NhaCungCapDTO nccDTO : mangNCC) {
			if(nccDTO.getMaNhaCungCap().equals(maNCC)) {
				isSame = true;
				break;
			}
		}

		return isSame;
	}

	public ArrayList<NhaCungCapDTO> timKiemNCCTheoTen(String ten) {
		ArrayList<NhaCungCapDTO> result = new ArrayList<NhaCungCapDTO>();

		for(NhaCungCapDTO nccDTO : mangNCC) {
			if(nccDTO.getTen().contains(ten)) {
				result.add(nccDTO);
			}
		}

		return result;
	}
}
