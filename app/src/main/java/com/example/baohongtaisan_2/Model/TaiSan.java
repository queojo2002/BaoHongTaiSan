package com.example.baohongtaisan_2.Model;

public class TaiSan {
    private String MaTS, TenTS, HinhAnh, GhiChu, HangSanXuat, NgayCapNhat, NgayTao;
    private int GiaTri, SLNhapVao, SLHienCon, PhamChat, NamSanXuat;
    private LoaiTaiSan loaiTaiSan;
    private NhomTaiSan nhomTaiSan;

    public TaiSan() {
    }


    public TaiSan(String maTS, String tenTS, String hinhAnh, String ghiChu, String hangSanXuat, int giaTri, int phamChat, int namSanXuat, LoaiTaiSan loaiTaiSan, NhomTaiSan nhomTaiSan) {
        MaTS = maTS;
        TenTS = tenTS;
        HinhAnh = hinhAnh;
        GhiChu = ghiChu;
        HangSanXuat = hangSanXuat;
        GiaTri = giaTri;
        PhamChat = phamChat;
        NamSanXuat = namSanXuat;
        this.loaiTaiSan = loaiTaiSan;
        this.nhomTaiSan = nhomTaiSan;
    }


    public TaiSan(String maTS, String tenTS, String hinhAnh, String ghiChu, String hangSanXuat, String ngayCapNhat, String ngayTao, int giaTri, int slNhapVao, int slHienCon, int phamChat, int namSanXuat, LoaiTaiSan loaiTaiSan, NhomTaiSan nhomTaiSan) {
        MaTS = maTS;
        TenTS = tenTS;
        HinhAnh = hinhAnh;
        GhiChu = ghiChu;
        HangSanXuat = hangSanXuat;
        NgayCapNhat = ngayCapNhat;
        NgayTao = ngayTao;
        GiaTri = giaTri;
        SLNhapVao = slNhapVao;
        SLHienCon = slHienCon;
        PhamChat = phamChat;
        NamSanXuat = namSanXuat;
        this.loaiTaiSan = loaiTaiSan;
        this.nhomTaiSan = nhomTaiSan;
    }


    public String getMaTS() {
        return MaTS;
    }

    public void setMaTS(String maTS) {
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

    public LoaiTaiSan getLoaiTaiSan() {
        return loaiTaiSan;
    }

    public void setLoaiTaiSan(LoaiTaiSan loaiTaiSan) {
        this.loaiTaiSan = loaiTaiSan;
    }

    public NhomTaiSan getNhomTaiSan() {
        return nhomTaiSan;
    }

    public void setNhomTaiSan(NhomTaiSan nhomTaiSan) {
        this.nhomTaiSan = nhomTaiSan;
    }
}
