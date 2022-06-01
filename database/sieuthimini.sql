--
        -- Cơ sở dữ liệu: `sieuthimini`
        --
        CREATE DATABASE sieuthimini;
		GO
        USE sieuthimini;

        -- --------------------------------------------------------
        -- Cấu trúc bảng cho bảng `chitiethoadon`
        --

        CREATE TABLE chitiethoadon (
        MaHoaDon varchar(10) NOT NULL,
        MaSanPham varchar(10) NOT NULL,
        MaKhuyenMai varchar(10),
        SoLuong int NOT NULL,
        DonGia int NOT NULL,
        ThanhTien int NOT NULL
        ) 

        --
        -- Đang đổ dữ liệu cho bảng `chitiethoadon`
        --
        INSERT INTO chitiethoadon (MaHoaDon, MaSanPham, MaKhuyenMai, SoLuong, Dongia, ThanhTien) VALUES
        ('HD100', 'SP1', NULL, 20, 7000, 140000),
        ('HD100', 'SP10', NULL, 5, 7000, 35000),
        ('HD100', 'SP28', NULL, 50, 7000, 350000),
        ('HD101', 'SP20', NULL, 1, 7000, 120000),
        ('HD101', 'SP22', NULL, 25, 7000, 1750000),
        ('HD101', 'SP24', NULL, 5, 10000, 50000),
        ('HD102', 'SP11', NULL, 2, 7000, 14000),
        ('HD102', 'SP12', NULL, 20, 30000,160000),
        ('HD102', 'SP6', NULL, 2 , 120000, 240000),
        ('HD103', 'SP15', NULL, 2, 50000, 100000),
        ('HD103', 'SP20', NULL, 2 , 7000, 350000),
        ('HD103', 'SP25', NULL, 50, 7000, 350000),
        ('HD104', 'SP21', NULL, 25, 7500, 187500),
        ('HD104', 'SP27', NULL, 5, 4000, 20000),
        ('HD105', 'SP16', NULL, 5, 23000, 115000),
        ('HD105', 'SP22', NULL, 5, 7000, 35000),
        ('HD105', 'SP29', NULL, 2, 7000, 14000),
        ('HD106', 'SP28', NULL, 1, 120000, 120000),
        ('HD106', 'SP20', NULL, 2, 45000, 90000);
        
		-- --------------------------------------------------------
		--
        -- Cấu trúc bảng cho bảng `chitietkhuyenmai`
        --

        CREATE TABLE chitietkhuyenmai (
        MaKhuyenMai varchar(10) NOT NULL,
        KhuyenMai tinyint NOT NULL,
        MaSanPham varchar(10) NOT NULL
       
        ) 

        --
        -- Đang đổ dữ liệu cho bảng `chitietkhuyenmai`
        --
        INSERT INTO chitietkhuyenmai (MaKhuyenMai, KhuyenMai, MaSanPham) VALUES
        ('KM1', 1 , 'SP12'),
        ('KM1', 3 , 'SP23'),
        ('KM1', 20 , 'SP25'),
        ('KM1', 15 , 'SP10'),
        ('KM2', 23 ,'SP23'),
        ('KM2', 21 ,'SP30'),
        ('KM3', 43 ,'SP25'),
        ('KM3', 45 ,'SP29');
  		--
        -- Cấu trúc bảng cho bảng `chitietphieunhaphang`
        --

        CREATE TABLE chitietphieunhaphang (
        MaPhieu varchar(10) NOT NULL,
        MaSanPham varchar(10) NOT NULL,
        SoLuong INT NOT NULL,
		DonGia INT NOT NULL,
		ThanhTien BIGINT NOT NULL
       
        ) 

        --
        -- Đang đổ dữ liệu cho bảng `chitietphieunhaphang`
        --
        INSERT INTO chitietphieunhaphang (MaPhieu, MaSanPham, SoLuong, DonGia, ThanhTien) VALUES
        ('MP001', 'SP29' , 1000, 5500, 7000000),
        ('MP002', 'SP28' , 1000, 6000, 6000000),
		('MP003', 'SP21' , 1200, 5000, 6000000),
		('MP004', 'SP19' , 350, 110000, 38500000),
		('MP005', 'SP20' , 400, 100000, 40000000),
		('MP006', 'SP22' , 1000, 5000,5000000),
		('MP007', 'SP26' , 450, 38000, 17100000),
		('MP008', 'SP27' , 1000, 25000, 25000000),
		('MP009', 'SP12' , 400, 5000, 2000000),
		('MP010', 'SP14' , 300, 35000, 10500000);
		--
        -- Cấu trúc bảng cho bảng `hoadon`
        --

        CREATE TABLE hoadon (
        MaHoaDon varchar(10) NOT NULL,
		Ngaylap date  NOT NULL,
		TongTien INT NOT NULL,
        MaNhanVien varchar(10) NOT NULL,
        MaKhachHang varchar(10)
        ) 

        --
        -- Đang đổ dữ liệu cho bảng `hoadon`
        --
        INSERT INTO hoadon (MaHoaDon, NgayLap, TongTien, MaNhanVien, MaKhachHang) VALUES
        ('HD100', '2022-02-10', 525000, 'NV102' , 'KH111' ),
		('HD101', '2022-03-15', 345000, 'NV101' , 'KH114' ),
		('HD102' , '2022-03-12', 209000, 'NV101' , 'KH115'),
		('HD103', '2022-10-02', 400000, 'NV101' , 'KH116' ),
		('HD104' , '2021-12-28', 207500, 'NV102' , 'KH118'),
		('HD105' , '2022-01-06', 164000, 'NV102' , 'KH117'),
		('HD106', '2022-02-01', 460000, 'NV101' , 'KH112' ),
		('HD107' , '2021-11-10', 135000, 'NV102' , NULL),
		('HD108' , '2021-10-23', 95000, 'NV101' , NULL),
		('HD109', '2022-02-10', 105000, 'NV102' , NULL),
		('HD110' , '2022-01-12', 99000, 'NV101' , 'KH116'),
		('HD111' , '2022-04-10', 177500, 'NV101' , 'KH117'),
		('HD112' , '2022-04-12', 108000, 'NV102' , 'KH116'),
		('HD113' , '2022-03-29', 125000, 'NV101' , 'KH117'),
		('HD114' , '2022-02-10', 74000, 'NV102' , 'KH113');

		--
        -- Cấu trúc bảng cho bảng `khachhang`
        --

        CREATE TABLE khachhang (
        MaKhachHang varchar(10) NOT NULL,
        Ho nvarchar(50) NOT NULL,
        Ten nvarchar(50) NOT NULL,
		Phanloaithanhvien varchar(45)  NOT NULL
        ) 

        --
        -- Đang đổ dữ liệu cho bảng `khachhang `
        --
        INSERT INTO khachhang (MaKhachHang, Ho, Ten, Phanloaithanhvien) VALUES
        ('KH111', N'Phạm Thị Thảo' , N'Anh' ,N'hạng bạc' ),
		('KH112', N'Cao Minh' , N'Đức' ,N'hạng bạc' ),
		('KH113', N'Nguyễn Đăng' , N'Khoa' ,N'hạng bạch kim' ),
		('KH114', N'Nguyễn Phước' , N'Lợi' ,N'hạng đồng' ),
		('KH115', N'Nguyễn Tuấn' , N'Vũ' ,N'hạng bạch kim' ),
		('KH116', N'Nguyễn Thị Yến' , N'Nhi' ,N'hạng vàng' ),
		('KH117', N'Đặng Thị Hoàng' , N'Oanh' ,N'hạng bạch kim' ),
		('KH118', N'Nguyễn Thụy Mai' , N'Quỳnh' ,N'hạng vàng' ),
        ('KH119', N'Võ Thị Tùng' , N'Thảo' ,N'hạng bạch kim' ),
		('KH120', N'Hồ Thị Minh' , N'Thư' ,N'hạng đồng' ),
		('KH121', N'Nguyễn Trọng' , N'Trí' ,N'hạng vàng' ),
		('KH122', N'Hồ Thị Cẩm' , N'Tú' ,N'hạng đồng' ),
		('KH123', N'Đoàn Huy' , N'Tùng' ,N'hạng vàng' );

		--
        -- Cấu trúc bảng cho bảng `khuyenmai`
        --

        CREATE TABLE khuyenmai (
        MaKhuyenMai varchar(10) NOT NULL,
        TenKhuyenMai nvarchar(100) NOT NULL,
        NgayBatDau date NOT NULL,
		NgayKetThuc date  NOT NULL
        ) 

        --
        -- Đang đổ dữ liệu cho bảng `khuyenmai `
        --
        INSERT INTO khuyenmai (MaKhuyenMai, TenKhuyenMai, NgayBatDau, NgayKetThuc) VALUES
        ('KM1', N'Sale nước 16%' , '2022-03-21' ,'2022-04-29'),
		('KM2', N'Giảm 10% dầu neptune' , '2022-03-27' ,'2022-05-01'),
		('KM3', N'Giảm 20% bánh tipo' , '2022-04-01' ,'2022-05-28'),
		('KM4', N'Dầu gội sunsilk giảm giá 25%' , '2022-02-21' ,'2022-05-26'),
		('KM5', N'Sale bánh orion marine 30%' , '2022-03-12' , '2022-06-01'),
		('KM6', N'Dầu gội clear giảm sập sàn 40%' , '2022-02-12' ,'2022-05-30');
		--
        -- Cấu trúc bảng cho bảng `loaisanpham`
        --

        CREATE TABLE loaisanpham (
        MaLoai varchar(10) NOT NULL,
        Ten nvarchar(50) NOT NULL
        ) 

        --
        -- Đang đổ dữ liệu cho bảng `loaisanpham`
        --
        INSERT INTO loaisanpham (MaLoai, Ten) VALUES
        ('L1', N'Rau- Củ- Trái cây'),
		('L2', N'Thịt- Trứng- Hải sản'),
		('L3', N'Thực phẩm chế biến'),
		('L4', N'Thực phẩm đông lạnh'),
		('L5', N'Thực phẩm khô- gia vị'),
		('L6', N'Bánh kẹo- đồ ăn vặt'),
		('L7', N'Sữa các loai'),
		('L8', N'Đồ uống giải khát'),
		('L9', N'Hóa mỹ phẩm'),
		('L10', N'Chăm  sóc cá nhân'),
		('L11', N'Chăm sóc mẹ và bé');
		--
        -- Cấu trúc bảng cho bảng `nhacungcap`
        --

        CREATE TABLE nhacungcap (
        MaNhaCungCap varchar(10) NOT NULL,
        Ten nvarchar(100) NOT NULL,
		DiaChi nvarchar(200) NOT NULL
        ) 

        --
        -- Đang đổ dữ liệu cho bảng `nhacungcap`
        --
        INSERT INTO nhacungcap (MaNhaCungCap, Ten, DiaChi) VALUES
        ('NCC1', N'Đại lý mì gói Thành phố Hồ Chí Minh' ,N'186 Phú Định, Phường 16, Quận 8, Thành phố Hồ Chí Minh'),
		('NCC2', N'Đại Lý Sữa Giao Linh' ,N'20P Nguyễn Thị Tần, Phường 3, Quận 8, Thành phố Hồ Chí Minh'),
		('NCC3', N'Đại Lý Nước Giải Khát 32' ,N'32 Nguyễn Thái Bình, Phường Nguyễn Thái Bình, Quận 1, Thành phố Hồ Chí Minh'),
		('NCC4', N'Bánh Kẹo Nhập Khẩu' ,N'430 Hồng Bàng, Phường 16, Quận 11, Thành phố Hồ Chí Minh'),
		('NCC5', N'Dầu Gội Cao Cấp' ,N'9A Đ. Số 385, Hiệp Phú, Quận 9, Thành phố Hồ Chí Minh'),
		('NCC6', N'Đại Lý Dầu ăn 324' ,N'574 Sư Vạn Hạnh, Phường 10, quận 10, Thành phố Hồ Chí Minh');
		--
        -- Cấu trúc bảng cho bảng `nhanvien`
        --

        CREATE TABLE nhanvien (
        MaNhanVien varchar(10) NOT NULL,
        TenNhanVien nvarchar(50) NOT NULL,
		NgayVaoLam date NOT NULL,
		ViTri nvarchar(50) NOT NULL,
		Luong INT NOT NULL
        ) 

        --
        -- Đang đổ dữ liệu cho bảng `nhanvien`
        --
        INSERT INTO nhanvien (MaNhanVien, TenNhanVien, NgayVaoLam, ViTri, Luong) VALUES
		('NV101', N'Nguyễn Bảo Phương' , '2021-10-10', N'bán hàng' , 3000000 ),
		('NV102', N'Lê Anh Quân' , '2020-12-10', N'bán hàng' , 3000000 ),
		('NV110', N'Nguyễn Lê Như Quỳnh' , '2018-02-02', N'quản lí' , 10000000 );

--
        -- Cấu trúc bảng cho bảng `taikhoan`
        --

        CREATE TABLE taikhoan ( 
        MaTaiKhoan varchar(10) NOT NULL,
        TenTaiKhoan varchar(50) NOT NULL,
		MatKhau varchar(50) NOT NULL ,
		Email varchar(50) NOT NULL,
		SoDienThoai varchar(20) NOT NULL ,
		MaNhanVien varchar(10) NOT NULL
	

	) 

        --
        -- Đang đổ dữ liệu cho bảng `taikhoan`
        --
        INSERT INTO taikhoan (MaTaiKhoan, TenTaiKhoan, MatKhau, Email,  SoDienThoai, MaNhanVien) VALUES

		(12123451, 'nhanvien','12345678','nguyenbaophuong@gmail.com', 0365434436,'NV101'),
		(12123452, 'leanhquan','rg379965','leanhquan112@gmail.com', 0368448557,'NV102'),
		(12123453, 'quanly','12345678','lnq123@gmail.com', 0369455433,'NV110');
	
		--
        -- Cấu trúc bảng cho bảng `nhasanxuat`
        --

        CREATE TABLE nhasanxuat (
        MaNhaSanXuat nvarchar(10) NOT NULL,
        Ten nvarchar(50) NOT NULL,
		DiaChi nvarchar(100) NOT NULL
	) 

        --
        -- Đang đổ dữ liệu cho bảng `nhansanxuat`
        --
        INSERT INTO nhasanxuat (MaNhaSanXuat, Ten, DiaChi) VALUES
		('NSX1', N'Công ty TNHH nước giải khát PepsiCo Việt Nam ' ,N'485 Song Hành XL Hà Nội, Phường Linh Trung, Thủ Đức,  Thành phố Hồ Chí Minh' ),
		('NSX11', N'Công ty cổ phần Miliket Việt nam ' ,N'22 Đào Duy Anh, Phường 9, Phú Nhuận, Thành phố Hồ Chí Minh' ),
		('NSX12', N'Công ty TNHH dầu thực vật Calofic ' ,N'229 Quang Trung, TT. Bình Định, An Nhơn, Bình Định ' ),
		('NSX13', N'Tập đoàn dầu Olive ' ,N'235 Đ. Nguyễn Văn Cừ, Phường Nguyễn Cư Trinh, Quận 1, Thành phố Hồ Chí Minh' ),
		('NSX15', N'Công ty cổ phần dầu thực vật Tường An' ,N'75 Đ. Vĩnh Hội, Phường 4, Quận 4, Thành phố Hồ Chí Minh ' ),
		('NSX16', N'Công ty cổ phần thực phẩm Sữa TH True Milk' ,N'24 Trần Đình Xu, Phường Cô Giang, Quận 1, Thành phố Hồ Chí Minh ' ),
		('NSX17', N'Công ty cổ phần thực phẩm Vianamilk' ,N'1406A, Đường 3 Tháng 2, Phường 2, Quận 11, Phường 2, Quận 11, Thành phố Hồ Chí Minh ' ),
		('NSX18', N'Công ty cổ phần thực phẩm Sữa DaLatmilk' ,N'453/111 Lê Văn Sỹ, Phường 12, Quận 3, Thành phố Hồ Chí Minh ' ),
		('NSX19', N'Công ty cổ phần thực phẩm Sữa Susu Việt Nam' ,N'271 Đường Hậu Giang, Phường 5, Quận 6, Thành phố Hồ Chí Minh ' ),
		('NSX20', N'Tập đoàn Mondelez International' ,N'138-142 Hai Bà Trưng, phường Đakao, Quận 1, Thành phố  Hồ Chí Minh ' ),
		('NSX21', N'Công ty cổ phần BIBICA' ,N'443 Lý Thường Kiệt, Phường 8, Quận Tân Bình, Thành phố Hồ Chí Minh ' ),
		('NSX24', N'Công ty cổ phần bánh kẹo Marine Việt Nam' ,N'3 Cao Bá Nhạ, Phường Nguyễn Cư Trinh, Quận 1, Thành phố Hồ Chí Minh ' ),
		('NSX26', N'Tập đoàn đa quốc gia Uninever' ,N'714 Đoàn Văn Bơ, Phường 18, Quận 4, Thành phố Hồ Chí Minh ' ),
		('NSX27', N'Công ty P&G Việt Nam' ,N'567 Đ. Kha Vạn Cân, Linh Đông, Thủ Đức, Thành phố Hồ Chí Minh' ),
		('NSX28', N'Tập đoàn dược phẩm Rohto-Mentholatum' ,N'23 Đ. Huỳnh Văn Nghệ, Phường 15, Tân Bình, Thành phố Hồ Chí Minh' ),
		('NSX5', N'Công ty cổ phần thực phẩm Á Châu' ,N'88 Đồng Khởi, Phường Bến Nghé, Quận 1,  Thành phố Hồ Chí Minh ' ),
		('NSX6', N'Công ty cổ phần Acecook Việt nam' ,N'272 Ngô Thời Nhiệm, Phường 2, Quận 3,  Thành phố Hồ Chí Minh ' ),
		('NSX8', N'Công ty cổ phần Uniben Việt nam' ,N'259B Hai Bà Trưng, Phường 6,Quận 3,  Thành phố Hồ Chí Minh ' ),
		('NSX9', N'Công ty cổ phần Omachi Việt nam' ,N'186 Phú Định, Phường 16, Quận 8, Thành phố Hồ Chí Minh ' );
		--
        -- Cấu trúc bảng cho bảng `phieunhaphang`
        --

        CREATE TABLE phieunhaphang (
        MaPhieu varchar(10) NOT NULL,
        MaNhaCungCap varchar(10) NOT NULL,
		MaNhanVien varchar(10) NOT NULL ,
		TongTien int NOT NULL,
		NgayNhap date NOT NULL

	) 

        --
        -- Đang đổ dữ liệu cho bảng `phieunhaphang`
        --
        INSERT INTO phieunhaphang (MaPhieu, MaNhaCungCap, MaNhanVien, TongTien,  NgayNhap) VALUES
		('MP001', 'NCC2', 'NV110' ,7000000 ,'2021-10-10' ),
		('MP002', 'NCC3', 'NV110' ,6000000 ,'2021-11-02' ),
		('MP003', 'NCC3', 'NV110' ,6000000 ,'2021-11-11' ),
		('MP004', 'NCC5', 'NV110' ,38500000 ,'2021-12-03' ),
		('MP005', 'NCC5', 'NV110' ,40000000 ,'2021-12-13' ),
		('MP006', 'NCC1', 'NV110' ,5000000 ,'2021-12-19' ),
		('MP007', 'NCC6', 'NV110' ,17100000 ,'2021-12-29' ),
		('MP008', 'NCC6', 'NV110' ,25000000 ,'2022-01-02' ),
		('MP009', 'NCC2', 'NV110' ,2000000 ,'2022-02-19'),
		('MP010', 'NCC4', 'NV110' ,10500000 ,'2022-03-15' );

		--
        -- Cấu trúc bảng cho bảng `sanpham`
        --

        CREATE TABLE sanpham (
        MaSanPham varchar(10) NOT NULL,
        TenSanPham nvarchar(50) NOT NULL,
		SoLuong int NOT NULL ,
		DonGiaSanPham int NOT NULL,
		LoaiSanPham varchar(10) NOT NULL ,
		NgaySanXuat date NOT NULL,
		HanSuDung date NOT NULL ,
		MaNhaSanXuat nvarchar(10) NOT NULL,
        ImageUrl varchar(100)
	) 

        --
        -- Đang đổ dữ liệu cho bảng `sanpham`
        --
        INSERT INTO sanpham (MaSanPham, TenSanPham, SoLuong, DonGiaSanPham,  LoaiSanPham, NgaySanXuat,  HanSuDung,  MaNhaSanXuat, ImageUrl) VALUES
		('SP1', N'Nước Cocacola', 1500 ,7000 ,'L8','2021-10-12', '2024-12-10', 'NSX1', NULL),
		('SP10', N'Sữa Dalat milk', 400 ,7000 ,'L7','2021-04-20', '2024-10-23', 'NSX18', NULL),
		('SP11', N'Sữa Susu', 400 ,5000 ,'L7','2021-02-14', '2024-12-03', 'NSX19', NULL),
		('SP12', N'Nước pepsi', 1000 ,8000 ,'L8','2021-02-11', '2024-01-13', 'NSX1', NULL),
		('SP13', N'Bánh oreo', 300 ,7500 ,'L6','2022-01-02', '2024-06-09', 'NSX20', NULL),
		('SP14', N'Bánh quasure', 300 ,40000 ,'L6','2021-09-08', '2024-12-20', 'NSX21', NULL),
		('SP15', N'Bánh cracket', 200 ,30000 ,'L6','2022-01-23', '2024-12-03', 'NSX21', NULL),
		('SP16', N'Bánh tipo', 150 ,23000 ,'L6','2021-10-19', '2024-05-09', 'NSX21', NULL),
		('SP17', N'Bánh libra', 400 ,10000 ,'L6','2022-01-02', '2024-12-03', 'NSX24', NULL),
		('SP18', N'Bánh orion marine', 500 ,15000 ,'L6','2021-12-08', '2024-10-09', 'NSX24', NULL),
		('SP19', N'Dầu gội done', 200 ,137000 ,'L10','2021-10-12', '2024-12-10', 'NSX26', NULL),
		('SP20', N'Dầu gội pantene', 350 ,120000 ,'L10','2021-10-12', '2024-12-10', 'NSX27', NULL),
		('SP21', N'Nước mirinda', 1200 ,7500 ,'L8','2022-12-11', '2024-03-01', 'NSX1', NULL),
		('SP22', N'Mì siukay', 1000 ,7000 ,'L5','2021-06-05', '2021-05-05', 'NSX6', NULL),
		('SP23', N'Mì miliket', 500 ,2500 ,'L5','2021-12-02', '2024-10-02', 'NSX11', NULL),
		('SP24', N'Dầu neptune', 1000 ,42000 ,'L5','2022-01-02', '2024-12-02', 'NSX12', NULL),
		('SP25', N'Dầu oliu', 1200 ,50000 ,'L5','2021-03-04',' 2024-05-06', 'NSX13', NULL),
		('SP26', N'Dầu simply', 450 ,45000 ,'L5','2021-03-20', '2024-05-09', 'NSX12', NULL),
		('SP27', N'Dầu tường an', 1000 ,34000 ,'L5','2021-03-21','2024-10-12', 'NSX15', NULL),
		('SP28', N'Sữa TH true milk', 1000 ,7000 ,'L7','2022-03-04','2024-09-04', 'NSX16', NULL),
		('SP29', N'Sữa vinamilk', 1000,7000 ,'L7','2021-10-12', '2024-12-10', 'NSX17', NULL),
		('SP30', N'Dầu gội sunsilk', 400 ,96000 ,'L10','2022-09-07', '2024-08-09', 'NSX26', NULL),
		('SP31 ', N'Dầu gội clear', 600 ,125000 ,'L10','2021-03-15', '2024-11-09', 'NSX26', NULL),
		('SP32', N'Dầu gội selsun', 400 ,115000 ,'L10','2021-06-12', '2024-10-11', 'NSX28', NULL),
		('SP4', N'Nước sting', 700 ,10000 ,'L8','2022-11-12', '2024-05-11', 'NSX1', NULL),
		('SP5', N'Mì gấu đỏ', 500 ,2500 ,'L5','2022-01-12','2024-04-05', 'NSX5', NULL),
		('SP6', N'Mì hảo hảo', 1000 ,3500 ,'L5','2022-01-01','2024-03-09', 'NSX6', NULL),
		('SP7', N'Mì lẩu thái', 700 ,4000 ,'L5','2022-02-12', '2024-12-20', 'NSX12', NULL),
		('SP8', N'Mì 3 miền', 1000 ,4000 ,'L5','2022-02-12', '2024-12-03', 'NSX8', NULL),
		('SP9', N'Mì omachi', 1000 ,5000 ,'L5','2022-01-12', '2024-05-04', 'NSX9', NULL);

		
		
ALTER TABLE chitietkhuyenmai ADD PRIMARY KEY(MaKhuyenMai, MaSanPham)
	GO

	ALTER TABLE chitietphieunhaphang ADD PRIMARY KEY(MaPhieu, MaSanPham)
	GO

	ALTER TABLE hoadon ADD PRIMARY KEY(MaHoaDon)
	GO

	ALTER TABLE khachhang ADD PRIMARY KEY(MaKhachHang)
	GO

	ALTER TABLE khuyenmai ADD PRIMARY KEY(MaKhuyenMai)
	GO

	ALTER TABLE loaisanpham ADD PRIMARY KEY(MaLoai)
	GO

	ALTER TABLE nhacungcap ADD PRIMARY KEY(MaNhaCungCap)
	GO

	ALTER TABLE nhanvien ADD PRIMARY KEY(MaNhanVien)
	GO

	ALTER TABLE taikhoan ADD PRIMARY KEY(MaTaiKhoan, MaNhanVien)
	GO

	ALTER TABLE nhasanxuat ADD PRIMARY KEY(MaNhaSanXuat)
	GO

	ALTER TABLE phieunhaphang ADD PRIMARY KEY(MaPhieu)
	GO

	ALTER TABLE sanpham ADD PRIMARY KEY(MaSanPham)
	GO

	ALTER TABLE chitiethoadon 
		ADD CONSTRAINT FK_CTHD_MAHD FOREIGN KEY(MaHoaDon) REFERENCES hoadon(MaHoaDon);
	GO
	ALTER TABLE chitiethoadon
		ADD CONSTRAINT FK_CTHD_MASP FOREIGN KEY(MaSanPham) REFERENCES sanpham(MaSanPham);
	GO
	ALTER TABLE chitiethoadon
		ADD CONSTRAINT FK_CTHD_MAKM FOREIGN KEY(MaKhuyenMai) REFERENCES khuyenmai(MaKhuyenMai);
	GO

	ALTER TABLE chitietkhuyenmai 
		ADD CONSTRAINT FK_KM_MAKM FOREIGN KEY(MaKhuyenMai) REFERENCES khuyenmai(MaKhuyenMai);
	GO
	ALTER TABLE chitietkhuyenmai
		ADD CONSTRAINT FK_KM_MASP FOREIGN KEY(MaSanPham) REFERENCES sanpham(MaSanPham);
	GO

	ALTER TABLE chitietphieunhaphang 
		ADD CONSTRAINT FK_PN_MAPN FOREIGN KEY(MaPhieu) REFERENCES phieunhaphang(MaPhieu);
	GO
	ALTER TABLE chitietphieunhaphang 
		ADD CONSTRAINT FK_PN_MASP FOREIGN KEY(MaSanPham) REFERENCES sanpham(MaSanPham);
	GO

	ALTER TABLE hoadon 
		ADD CONSTRAINT FK_HD_MANV FOREIGN KEY(MaNhanVien) REFERENCES nhanvien(MaNhanVien);
	GO
	ALTER TABLE hoadon
		ADD CONSTRAINT FK_HD_MAKH FOREIGN KEY(MaKhachHang) REFERENCES khachhang(MaKhachHang);
	GO

	ALTER TABLE phieunhaphang 
		ADD CONSTRAINT FK_PNH_MANCC FOREIGN KEY(MaNhaCungCap) REFERENCES nhacungcap(MaNhaCungCap);
	GO
	ALTER TABLE phieunhaphang
		ADD CONSTRAINT FK_PNH_MANV FOREIGN KEY(MaNhanVien) REFERENCES nhanvien(MaNhanVien);
	GO

	ALTER TABLE sanpham 
		ADD CONSTRAINT FK_SP_LSP FOREIGN KEY(LoaiSanPham) REFERENCES loaisanpham(MaLoai);
	GO
	ALTER TABLE sanpham 
		ADD CONSTRAINT FK_SP_NSX FOREIGN KEY(MaNhaSanXuat) REFERENCES nhasanxuat(MaNhaSanXuat);
	GO

	ALTER TABLE taikhoan
		ADD CONSTRAINT FK_TK_MANV FOREIGN KEY(MaNhanVien) REFERENCES nhanvien(MaNhanVien);
	GO
