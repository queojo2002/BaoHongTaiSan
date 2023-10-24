package com.example.baohongtaisan_2.Model;

public class TaiSan {
    private int MaTS;
    private String TenTS, HinhAnh, GhiChu, HangSanXuat, NgayCapNhat, NgayTao;
    private int GiaTri, SLNhapVao, SLHienCon, PhamChat, NamSanXuat;
    private String TenLTS, TenNTS;

    public TaiSan() {
    }


    public TaiSan(int maTS, String tenTS, String hinhAnh, String ghiChu, String hangSanXuat, String ngayCapNhat, String ngayTao, int giaTri, int SLNhapVao, int SLHienCon, int phamChat, int namSanXuat, String tenLTS, String tenNTS) {
        MaTS = maTS;
        TenTS = tenTS;
        HinhAnh = hinhAnh;
        GhiChu = ghiChu;
        HangSanXuat = hangSanXuat;
        NgayCapNhat = ngayCapNhat;
        NgayTao = ngayTao;
        GiaTri = giaTri;
        this.SLNhapVao = SLNhapVao;
        this.SLHienCon = SLHienCon;
        PhamChat = phamChat;
        NamSanXuat = namSanXuat;
        TenLTS = tenLTS;
        TenNTS = tenNTS;
    }

    public int getMaTS() {
        return MaTS;
    }

    public void setMaTS(int maTS) {
        MaTS = maTS;
    }

    public String getTenTS() {
        return TenTS;
    }

    public void setTenTS(String tenTS) {
        TenTS = tenTS;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String ghiChu) {
        GhiChu = ghiChu;
    }

    public String getHangSanXuat() {
        return HangSanXuat;
    }

    public void setHangSanXuat(String hangSanXuat) {
        HangSanXuat = hangSanXuat;
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

    public int getGiaTri() {
        return GiaTri;
    }

    public void setGiaTri(int giaTri) {
        GiaTri = giaTri;
    }

    public int getSLNhapVao() {
        return SLNhapVao;
    }

    public void setSLNhapVao(int SLNhapVao) {
        this.SLNhapVao = SLNhapVao;
    }

    public int getSLHienCon() {
        return SLHienCon;
    }

    public void setSLHienCon(int SLHienCon) {
        this.SLHienCon = SLHienCon;
    }

    public int getPhamChat() {
        return PhamChat;
    }

    public void setPhamChat(int phamChat) {
        PhamChat = phamChat;
    }

    public int getNamSanXuat() {
        return NamSanXuat;
    }

    public void setNamSanXuat(int namSanXuat) {
        NamSanXuat = namSanXuat;
    }

    public String getTenLTS() {
        return TenLTS;
    }

    public void setTenLTS(String tenLTS) {
        TenLTS = tenLTS;
    }

    public String getTenNTS() {
        return TenNTS;
    }

    public void setTenNTS(String tenNTS) {
        TenNTS = tenNTS;
    }
}
