package com.example.baohongtaisan_2.Model;

public class IsLogin {
    private static IsLogin instance;
    private int MaND, MaDV, MaCD, MaPQ;
    private String TenDangNhap, MatKhau, HoVaTen, Email, DiaChi, SoDienThoai, TenCD, TenDV, TenPQ, NgayCapNhat, NgayTao, uid, token;

    private IsLogin() {
    }

    public static IsLogin getInstance() {
        if (instance == null) {
            instance = new IsLogin();
        }
        return instance;
    }

    public static void setInstance(IsLogin instance) {
        IsLogin.instance = instance;
    }

    public void setLoggedInUser(int maND, int maDV, int maCD, int maPQ, String tenDangNhap, String matKhau, String hoVaTen, String email, String diaChi, String soDienThoai, String tenCD, String tenDV, String tenPQ, String ngayCapNhat, String ngayTao, String Uid, String Token) {
        this.MaND = maND;
        this.MaDV = maDV;
        this.MaCD = maCD;
        this.MaPQ = maPQ;
        this.TenDangNhap = tenDangNhap;
        this.MatKhau = matKhau;
        this.HoVaTen = hoVaTen;
        this.Email = email;
        this.DiaChi = diaChi;
        this.SoDienThoai = soDienThoai;
        this.TenCD = tenCD;
        this.TenDV = tenDV;
        this.TenPQ = tenPQ;
        this.NgayCapNhat = ngayCapNhat;
        this.NgayTao = ngayTao;
        this.uid = Uid;
        this.token = Token;
    }

    public int getMaND() {
        return MaND;
    }

    public void setMaND(int maND) {
        MaND = maND;
    }

    public int getMaDV() {
        return MaDV;
    }

    public void setMaDV(int maDV) {
        MaDV = maDV;
    }

    public int getMaCD() {
        return MaCD;
    }

    public void setMaCD(int maCD) {
        MaCD = maCD;
    }

    public int getMaPQ() {
        return MaPQ;
    }

    public void setMaPQ(int maPQ) {
        MaPQ = maPQ;
    }

    public String getTenDangNhap() {
        return TenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        TenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
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

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getSoDienThoai() {
        return SoDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        SoDienThoai = soDienThoai;
    }

    public String getTenCD() {
        return TenCD;
    }

    public void setTenCD(String tenCD) {
        TenCD = tenCD;
    }

    public String getTenDV() {
        return TenDV;
    }

    public void setTenDV(String tenDV) {
        TenDV = tenDV;
    }

    public String getTenPQ() {
        return TenPQ;
    }

    public void setTenPQ(String tenPQ) {
        TenPQ = tenPQ;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

