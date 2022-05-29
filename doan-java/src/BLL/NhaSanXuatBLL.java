package BLL;

import DAL.NhaSanXuatDAL;
import DTO.NhaSanXuatDTO;
import java.util.ArrayList;

public class NhaSanXuatBLL {
	private static NhaSanXuatBLL instance = null;
	private NhaSanXuatDAL nsxDAL;
	private ArrayList<NhaSanXuatDTO> mangNSX;

	private NhaSanXuatBLL() {
		this.nsxDAL = new NhaSanXuatDAL();
		mangNSX = nsxDAL.getAllNhaSanXuat();
	}
	public static NhaSanXuatBLL getInstance() {
		if(instance == null) {
			instance = new NhaSanXuatBLL();
		}
		return instance;
	}

	public ArrayList<NhaSanXuatDTO> getAllNSX() {
		return mangNSX;
	}

	public NhaSanXuatDTO getNSX(String maNSX) {
		NhaSanXuatDTO nsx = null;
		for(NhaSanXuatDTO nsxDTO : mangNSX) {
			if(nsxDTO.getMaNhaSanXuat().equals(maNSX)) {
				nsx = nsxDTO;
				break;
			}
		}
		return nsx;
	}

	public ArrayList<NhaSanXuatDTO> timKiemNSX(String maNSX) {
		ArrayList<NhaSanXuatDTO> result = new ArrayList<NhaSanXuatDTO>();

		for(NhaSanXuatDTO nsxDTO : mangNSX) {
			if(nsxDTO.getMaNhaSanXuat().contains(maNSX)) {
				result.add(nsxDTO);
			}
		}

		return result;
	}
}
