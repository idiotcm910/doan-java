package BLL;

import DAL.ProductDAL;
import DTO.ProductDTO;
import java.util.ArrayList;
import common.Date;

public class ProductBLL {
	private static ProductBLL instance = null;

	private ProductDAL productDAL;
	private ArrayList<ProductDTO> productArray;

	private ProductBLL() {
		this.productDAL = new ProductDAL();
		productArray = productDAL.getAllProduct();
	}

	public static ProductBLL getInstance() {
		if(instance == null) {
			instance = new ProductBLL();
		}
		return instance;
	}

	public void refreshUrlImage(String maSanPham) {
		int index = indexOf(maSanPham);

		productArray.get(index).setImageUrl("NULL");
		productDAL.refreshImageUrlProduct(maSanPham);
	}

	public ArrayList<ProductDTO> getAllProduct() {
		return productArray;
	}

	public ArrayList<ProductDTO> getAllProductHasQuantity() {
		ArrayList<ProductDTO> result = new ArrayList<ProductDTO>();

		for(ProductDTO dto : productArray) {
			if(dto.getSoLuong() > 0) {
				result.add(dto);
			}
		}

		return result;
	}

	public ProductDTO getProduct(int index) {
		return productArray.get(index).clone();
	}

	public boolean addProduct(ProductDTO productData) {
		boolean isSuccess = false;

		if(productData != null) {
			ProductDTO product = productData.clone();
			productArray.add(product);
			isSuccess = productDAL.insertOneProduct(productData);
		}	

		return isSuccess;
	}

	/**
	 * mã sản phẩm sẽ không được phép thay đổi.
	 * */
	public boolean updateProduct(ProductDTO productData) {
		boolean isSuccess = false;

		int index = indexOf(productData.getMaSanPham());
		if(index != -1) {
			ProductDTO product = productData.clone();
			// cap nhat lai tren bll
			productArray.set(index, product);
			productDAL.updateProduct(product.getMaSanPham(), product);
			isSuccess = true;
		}

		return isSuccess;
	}

	public boolean updateProductQuantity(String maSanPham, int quantity) {
		boolean isSuccess = false;

		int index = indexOf(maSanPham);
		if(index != -1) {
			ProductDTO product = productArray.get(index);
			// cap nhat lai tren BLL
			int newQuantity = (quantity >= product.getSoLuong())? 0 : product.getSoLuong() - quantity;
			product.setSoLuong(newQuantity);
			productDAL.updateProductQuantity(maSanPham, product.getSoLuong());
			isSuccess = true;
		}

		return isSuccess;
	}

	public boolean updateProductQuantityIncrease(String maSanPham, int quantity) {
		boolean isSuccess = false;

		int index = indexOf(maSanPham);
		if(index != -1) {
			ProductDTO product = productArray.get(index);
			// cap nhat lai tren BLL
			int newQuantity = product.getSoLuong() + quantity;
			product.setSoLuong(newQuantity);
			productDAL.updateProductQuantity(maSanPham, product.getSoLuong());
			isSuccess = true;
		}

		return isSuccess;
	}

	public boolean deleteProduct(String maSanPham) {
		boolean isSuccess = false;

		int index = indexOf(maSanPham);
		if(index != -1) {
			productArray.remove(index);
			isSuccess = true;
		}

		return isSuccess;
	}

	public int indexOf(String maSanPham) {
		int index = -1;

		for(int i = 0; i < productArray.size(); ++i) {
			if(productArray.get(i).getMaSanPham().equals(maSanPham)) {
				index = i;
				break;
			}
		}

		return index;
	}

	public ProductDTO soSanhMaSanPham(String str) {
		ProductDTO productReturn = null;

		for(ProductDTO product : productArray) {
			if(product.getMaSanPham().trim().equals(str.trim())) {
				productReturn = product;
				break;
			}
		}

		return productReturn;
	}

	public ProductDTO soSanhTenSanPham(String str) {
		ProductDTO productReturn = null;

		for(ProductDTO product : productArray) {
			if(product.getTenSanPham().equals(str)) {
				productReturn = product;
				break;
			}
		}

		return productReturn;
	}

	public ArrayList<ProductDTO> timKiemVoiMaSanPham(String str) {
		return timKiemDuLieu("maSanPham", str);
	}

	public ArrayList<ProductDTO> timKiemVoiTenSanPham(String str) {
		return timKiemDuLieu("tenSanPham", str);
	}

	public ArrayList<ProductDTO> timKiemVoiLoaiSanPham(String str) {
		return timKiemDuLieu("loaiSanPham", str);
	}

	public ArrayList<ProductDTO> timKiemVoiNamSanXuat(String str) {
		return timKiemDuLieu("ngaySanXuat", str);
	}

	public ArrayList<ProductDTO> timKiemVoiNamHanSuDung(String str) {
		return timKiemDuLieu("hanSuDung", str);
	}

	public ArrayList<ProductDTO> timKiemVoiMaNhaSanXuat(String str) {
		return timKiemDuLieu("maNhaSanXuat", str);
	}

	public ArrayList<ProductDTO> timKiemVoiSoLuong(int min, int max) {
		return timKiemDuLieuVoiThuocTinhSo("soLuong", min, max);
	}
	
	public ArrayList<ProductDTO> timKiemVoiDonGia(int min, int max) {
		return timKiemDuLieuVoiThuocTinhSo("donGia", min, max);
	}
	/* 
	 * chi ap dung duoc voi cai thuoc tinh la String, con nhung thuoc tinh int thi se phai tim kiem
	 * trong khoang gia tri nen se co ham rieng de xu ly
	 * */
	private ArrayList<ProductDTO> timKiemDuLieu(String thuocTinhCanTimKiem, String str) {
		ArrayList<ProductDTO> result = new ArrayList<ProductDTO>();

		for(ProductDTO product : productArray) {
			switch(thuocTinhCanTimKiem) {
				case "maSanPham":
					if(product.getMaSanPham().contains(str)) {
						result.add(product);
					}
					break;
				case "tenSanPham":
					if(product.getTenSanPham().contains(str)) {
						result.add(product);
					}
					break;
				case "loaiSanPham":
					if(product.getLoaiSanPham().contains(str)) {
						result.add(product);
					}
					break;
				case "ngaySanXuat":
					try {
						Date ngaySanXuat = new Date(product.getNgaySanXuat());
						if(ngaySanXuat.getYear() == Integer.parseInt(str)) {
							result.add(product);
						}
					}
					catch(Exception ex) {

					}
					break;
				case "hanSuDung":
					try {
						Date namHetHan = new Date(product.getHanSuDung());
						if(namHetHan.getYear() == Integer.parseInt(str)) {
							result.add(product);
						}
					}
					catch(Exception ex) {

					}
					break;
				case "maNhaSanXuat":
					if(product.getMaNhaSanXuat().contains(str)) {
						result.add(product);
					}
					break;
			}
		}

		return result;
	}

	private ArrayList<ProductDTO> timKiemDuLieuVoiThuocTinhSo(String thuocTinh, int min, int max) {
		ArrayList<ProductDTO> result = new ArrayList<ProductDTO>();

		for(ProductDTO product : productArray) {
			switch(thuocTinh) {
				case "soLuong":
					if(product.getSoLuong() > min && product.getSoLuong() < max) {
						result.add(product);
					}
					break;
				default:
					if(product.getDonGiaSanPham() > min && product.getDonGiaSanPham() < max) {
						result.add(product);
					}
			}
		}

		return result;
	}	
}
