package com.example.baohongtaisan_2.Model;

public class BaoHong {
    private int MaBL, MaPB, MaND, TrangThai, TinhTrang;
    private String Mota, HinhAnh, NgayCapNhat, NgayTao, TenP, TenTS, token, HoVaTen, Email;

    // TrangThai: 0 - Chua bao hong lan nao
    // TrangThai: 1 - Da Gui
    // TrangThai: 2 - Da Tiep Nhan
    // TrangThai: 3 - Dang Sua Chua
    // TrangThai: 4 - Sua Thanh Cong
    //TrangThai: 5 - Sua khong thanh cong

    // TinhTrang: 0 - chua bao hong
    // TinhTrang: 1 -
    // TinhTrang: 2 -
    // TinhTrang: 3 -
    // TinhTrang: 4 -

    public BaoHong() {
    }

    public BaoHong(int maBL, int maPB, int maND, int trangThai, int tinhTrang, String mota, String hinhAnh, String ngayCapNhat, String ngayTao, String tenP, String tenTS, String token, String hoVaTen, String email) {
        MaBL = maBL;
        MaPB = maPB;
        MaND = maND;
        TrangThai = trangThai;
        TinhTrang = tinhTrang;
        Mota = mota;
        HinhAnh = hinhAnh;
        NgayCapNhat = ngayCapNhat;
        NgayTao = ngayTao;
        TenP = tenP;
        TenTS = tenTS;
        this.token = token;
        HoVaTen = hoVaTen;
        Email = email;
    }

    public BaoHong(int maBL, int maPB, int maND, int trangThai, int tinhTrang, String mota, String hinhAnh, String ngayCapNhat,
                   String ngayTao, String tenP, String tenTS, String token) {
        MaBL = maBL;
        MaPB = maPB;
        MaND = maND;
        TrangThai = trangThai;
        TinhTrang = tinhTrang;
        Mota = mota;
        HinhAnh = hinhAnh;
        NgayCapNhat = ngayCapNhat;
        NgayTao = ngayTao;
        TenP = tenP;
        TenTS = tenTS;
        this.token = token;
    }

    public BaoHong(int maBL, int maPB, int maND, int trangThai, int tinhTrang, String mota, String hinhAnh, String ngayCapNhat, String ngayTao, String tenP, String tenTS) {
        MaBL = maBL;
        MaPB = maPB;
        MaND = maND;
        TrangThai = trangThai;
        TinhTrang = tinhTrang;
        Mota = mota;
        HinhAnh = hinhAnh;
        NgayCapNhat = ngayCapNhat;
        NgayTao = ngayTao;
        TenP = tenP;
        TenTS = tenTS;
    }

    public BaoHong(int maBL, int maPB, int maND, int trangThai, int tinhTrang, String mota, String hinhAnh, String ngayCapNhat, String ngayTao) {
        MaBL = maBL;
        MaPB = maPB;
        MaND = maND;
        TrangThai = trangThai;
        TinhTrang = tinhTrang;
        Mota = mota;
        HinhAnh = hinhAnh;
        NgayCapNhat = ngayCapNhat;
        NgayTao = ngayTao;
    }

    public String getHoVaTen() {
        return HoVaTen;
    }

    public void setHoVaTen(String hoVaTen) {
        HoVaTen = hoVaTen;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getMaBL() {
        return MaBL;
    }

    public void setMaBL(int maBL) {
        MaBL = maBL;
    }

    public int getMaPB() {
        return MaPB;
    }

    public void setMaPB(int maPB) {
        MaPB = maPB;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int trangThai) {
        TrangThai = trangThai;
    }

    public int getTinhTrang() {
        return TinhTrang;
    }

    public void setTinhTrang(int tinhTrang) {
        TinhTrang = tinhTrang;
    }

    public String getMota() {
        return Mota;
    }

    public void setMota(String mota) {
        Mota = mota;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public String getNgayCapNhat() {
        return NgayCapNhat;
    }

    public void setNgayCapNhat(String ngayCapNhat) {
        NgayCapNhat = ngayCapNhat;
    }

    public String getNgayTao() {
        return NgayTao;
    }

    public void setNgayTao(String ngayTao) {
        NgayTao = ngayTao;
    }

    public int getMaND() {
        return MaND;
    }

    public void setMaND(int maND) {
        MaND = maND;
    }

    public String getTenP() {
        return TenP;
    }

    public void setTenP(String tenP) {
        TenP = tenP;
    }

    public String getTenTS() {
        return TenTS;
    }

    public void setTenTS(String tenTS) {
        TenTS = tenTS;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
