package common;

import java.util.StringTokenizer;

public class Date {
	private int day;
	private int month;
	private int year;

	// cac ham xu ly Date
	public static int countDayInMonth(int year, int month) {
		int count = 0;
		switch(month) {
			case 4:
			case 6:
			case 9:
			case 11:
				count = 30;
				break;
			case 2:
				count = (year % 4 == 0 && year % 100 != 0)? 29 : 28;
				break;
			default:
				count = 31;	
		}

		return count;
	}

	/**
	 * Tính tổng số ngày từ khoảng thời gian a đến khoảng thời gian b
	 * a phải có khoảng thời gian xác định trước b, nếu ko sẽ trả vê -1
	 * */
	public static int countDayInTwoDate(Date one, Date two) {
		if(one.getYear() > two.getYear()) {
			return -1;
		}

		int countDay = Date.countDayInYear(two) - Date.countDayInYear(one);

		if(one.getYear() == two.getYear() && countDay < 1) {
			return -1;	
		}

		for(int i = 1; i <= two.getYear() - one.getYear(); ++i) {
			int year = two.getYear() - i;	
			countDay += (year % 4 == 0 && year % 100 != 0)? 366 : 365;
		}

		return countDay;
	}

	public static int countDayInYear(Date date) {
		int count = date.getDay();

		for(int i = 1; i < date.getMonth(); ++i) {
			switch(i) {
				case 4:
				case 6:
				case 9:
				case 11:
					count += 30;
					break;
				case 2:
					count += (date.getYear() % 4 == 0 && date.getYear() % 100 != 0)? 29 : 28;
					break;
				default:
					count += 31;
			}
		}

		return count;
	}

	/**
	 * Kiểm tra xem 1 ngày có nằm trong khoảng từ thời gian a đến thời gian b không.
	 * a, b là khoảng thời gian (ví dụ a = 2022-02-1)
	 * */
	public static boolean checkDateInTwoDate(Date check, Date min, Date max) {
		boolean isHas = false;
		int testDay = Date.countDayInTwoDate(min, check);
		int testDay2 = Date.countDayInTwoDate(check, max);
		if(testDay != -1 && testDay2 != -1) {
			isHas = true;
		}

		return isHas;
	}

	public Date() {
		this.day = 1;
		this.month = 1;
		this.year = 2000;
	}

	public Date(int day, int month, int year) {
		this.day = day;
		this.month = month;
		this.year = year;
	}

	/**
	 * dateFormmat: dateFormat
	 * yyyy-MM-dd
	 * */
	public Date(String dateFormat) throws Exception {
		StringTokenizer st = new StringTokenizer(dateFormat, "-");
		if(st.countTokens() != 3) {
			throw new Exception("Dữ liệu phải theo cấu trúc: yyyy-MM-dd");
		}

		int dayCache = 0, monthCache = 0, yearCache = 0;

		try {
			yearCache = Integer.parseInt(st.nextToken());
			monthCache = Integer.parseInt(st.nextToken());
			dayCache = Integer.parseInt(st.nextToken());
		}
		catch(Exception e) {
			throw new Exception("Dữ liệu phải là số");
		}

		validationDayInMonth(dayCache, monthCache, yearCache);

		if(monthCache < 1 || monthCache > 13) {
			throw new Exception("tháng phải là số từ 1 đến 12");
		}

		if(yearCache < 1000) {
			throw new Exception("năm phải là số lớn hơn 1000");
		}
		
		this.day = dayCache;
		this.month = monthCache;
		this.year = yearCache;
	}

	private void validationDayInMonth(int day, int month, int year) throws Exception {

		int max = 0;
		switch(month) {
			case 4:
			case 6:
			case 9:
			case 11:
				max = 30;
				break;
			case 2:
				max = (year % 4 == 0 && year % 100 != 0)? 29 : 28;
				break;
			default:
				max = 31;	
		}

		if(max != 0 && (day > max || day < 1)) {
			throw new Exception("Số ngày trong tháng " + month + " phải nhỏ hơn " + max);
		}
	}

	public String toString() {
		String dayStr = (day < 10)? "0" + day : Integer.toString(day);
		String monthStr = (month < 10)? "0" + month : Integer.toString(month);
		
		return year + "-" + monthStr + "-" + dayStr;
	}

	/**
	 * Hàm sẽ kiểm tra xem đối tượng Date hiện tại có mốc thời gian ở phía trước đối tượng Date đối số hay không
	 * @return nếu co thì trả về true.
	 * */
	public boolean isLessThan(Date another) {
		if(this.year < another.getYear()) {
			return true;
		}

		if(this.year > another.getYear()) {
			return false;
		}

		if(this.month < another.getMonth()) {
			return true;
		}

		if(this.month > another.getMonth()) {
			return false;	
		}

		if(this.day < another.getDay()) {
			return true;
		}

		return false;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getDay() {
		return day;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getMonth() {
		return month;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getYear() {
		return year;
	}

}
