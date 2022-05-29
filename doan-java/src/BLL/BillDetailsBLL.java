package BLL;
import DTO.BillDetailsDTO;
import DAL.BillDetailsDAL;
import java.util.ArrayList;

public class BillDetailsBLL {
	private static BillDetailsBLL instance = null;

	private ArrayList<BillDetailsDTO> billDetails;
	private BillDetailsDAL billDetailsDAL;
	private ProductBLL productBLL;

	private BillDetailsBLL() {
		billDetailsDAL = new BillDetailsDAL();
		billDetails = billDetailsDAL.getAllBillDetails();
		productBLL = ProductBLL.getInstance();
	}

	public static BillDetailsBLL getInstance() {
		if(instance == null) {
			instance = new BillDetailsBLL();
		}

		return instance;
	}

	public  ArrayList<BillDetailsDTO> getAllBillDetails(String maHoaDon) {
		ArrayList<BillDetailsDTO> result = new ArrayList<BillDetailsDTO>();

		for(BillDetailsDTO billDetail : billDetails) {
			if(billDetail.getMaHoaDon().equals(maHoaDon)) {
				result.add(billDetail);
			}
		}

		return result;
	}

	public void insertBillDetails(BillDetailsDTO chiTietHoaDonData) {
		billDetails.add(chiTietHoaDonData);
		productBLL.updateProductQuantity(chiTietHoaDonData.getMaSanPham(), chiTietHoaDonData.getSoLuong());
		billDetailsDAL.insertBillDetails(chiTietHoaDonData);
	}

	public void insertAllBillDetails(ArrayList<BillDetailsDTO> data) {
		for(BillDetailsDTO dto : data) {
			insertBillDetails(dto);
		}
	}

	public ArrayList<BillDetailsDTO> timKiemChiTietHoaDonNangCaoTheoAND(int min, int max, String maSanPham, String maKhuyenMai, String maHoaDon) {
		ArrayList<BillDetailsDTO> result = new ArrayList<BillDetailsDTO>();

		boolean hasConditionSoLuong = min != -1 && max != -1;
		boolean hasCondtionSanPham = maSanPham.length() != 0;

		boolean isCheckConditions;
		for(BillDetailsDTO billDetail : getAllBillDetails(maHoaDon)) {
			isCheckConditions = true;

			if(hasConditionSoLuong) {
				isCheckConditions = (billDetail.getSoLuong() >= min && billDetail.getSoLuong() <= max)? true : false;
			}

			if(isCheckConditions && hasCondtionSanPham) {
				isCheckConditions = (billDetail.getMaSanPham().contains(maSanPham))? true : false;
			}


			if(isCheckConditions) {
				String maKhuyenMaiString = billDetail.getMaKhuyenMai();
				if(maKhuyenMai.equals("NOTNULL")) {
					isCheckConditions = (!maKhuyenMaiString.equals("NULL") && maKhuyenMaiString.contains(""))? true : false;	
				}
				else {
					isCheckConditions = (maKhuyenMaiString.contains(maKhuyenMai))? true : false;	
				}
			}

			if(isCheckConditions) {
				result.add(billDetail);
			}
		}

		return result;
	} 

	public ArrayList<BillDetailsDTO> timKiemChiTietHoaDonNangCaoTheoOR(int min, int max, String maSanPham, String maKhuyenMai, String maBillDetails) {
		ArrayList<BillDetailsDTO> result = new ArrayList<BillDetailsDTO>();
		
		boolean hasConditionSoLuong = min != -1 && max != -1;
		boolean hasCondtionSanPham = maSanPham.length() != 0;
		boolean hasConditionKhuyenMai = maKhuyenMai.length() != 0;

		if(hasConditionSoLuong) {
			ArrayList<BillDetailsDTO> resultSoLuong = timKiemChiTietHoaDonNangCaoTheoAND(min, max, "", "", maBillDetails);
			result = ORHaiMang(result, resultSoLuong);
		}

		if(hasCondtionSanPham) {
			ArrayList<BillDetailsDTO> resultSanPham = timKiemChiTietHoaDonNangCaoTheoAND(-1, -1, maSanPham, "", maBillDetails);
			result = ORHaiMang(result, resultSanPham);
		}

		if(hasConditionKhuyenMai) {
			ArrayList<BillDetailsDTO> resultKhuyenMai = timKiemChiTietHoaDonNangCaoTheoAND(-1, -1, "", maKhuyenMai, maBillDetails);
			result = ORHaiMang(result, resultKhuyenMai);
		}

		return result;
	}

	public ArrayList<BillDetailsDTO> ORHaiMang(ArrayList<BillDetailsDTO> arrayFirst, ArrayList<BillDetailsDTO> arraySecond) {
		ArrayList<BillDetailsDTO> result = new ArrayList<BillDetailsDTO>();

		ArrayList<BillDetailsDTO> mangGop = new ArrayList<BillDetailsDTO>();
		mangGop.addAll(arrayFirst);
		mangGop.addAll(arraySecond);

		for(BillDetailsDTO hoaDonGop : mangGop) {
			boolean hasSame = false;

			for(BillDetailsDTO hoaDonResult : result) {
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
