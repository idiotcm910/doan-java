package BLL;

import DAL.CustomerDAL;
import DTO.CustomerDTO;
import java.util.ArrayList;

public class CustomerBLL {
	private static CustomerBLL instance = null;

	private ArrayList<CustomerDTO> customers;
	private CustomerDAL customerDAL;

	private CustomerBLL() {
		this.customerDAL = new CustomerDAL();
		customers = customerDAL.getAllCustomer();
	}

	public static CustomerBLL getInstance() {
		if(instance == null) {
			instance = new CustomerBLL();
		}
		return instance;
	}

	public ArrayList<CustomerDTO> getAllCustomer() {
		return customers;
	}

	public ArrayList<CustomerDTO> searchCustomerTheoMaVaTenDungOR(String str) {
		ArrayList<CustomerDTO> result = new ArrayList<CustomerDTO>();

		ArrayList<CustomerDTO> resultMa = searchCustomerTheoMa(str);
		result.addAll(resultMa);

		ArrayList<CustomerDTO> resultTen = searchCustomerTheoTen(str);
		result.addAll(resultTen);

		return result;
	}

	public ArrayList<CustomerDTO> searchCustomerTheoMa(String ma) {
		ArrayList<CustomerDTO> result = new ArrayList<CustomerDTO>();

		for(CustomerDTO customer : customers) {
			if(customer.getMaKhachHang().contains(ma)) {
				result.add(customer);
			}
		}

		return result;
	}

	public ArrayList<CustomerDTO> searchCustomerTheoTen(String ten) {
		ArrayList<CustomerDTO> result = new ArrayList<CustomerDTO>();

		for(CustomerDTO customer : customers) {
			String hoVaTen = customer.getHo() + " " + customer.getTen();
			if(hoVaTen.contains(ten)) {
				result.add(customer);
			}
		}

		return result;
	}

	public CustomerDTO getOneCustomer(String maKhachHang) {	
		CustomerDTO result = null;
			
		for(CustomerDTO customer : customers) {
			if(customer.getMaKhachHang().equals(maKhachHang)) {
				result = customer;	
				break;
			}	
		}

		return result;
	}
}
