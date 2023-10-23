package com.example.baohongtaisan_2.Model;

public class PhanBo {

    private int MaPB, MaTS, MaND, MaP, SoLuong;
    private String GhiChu, TenTS, TenLTS, TenNTS, TenP, NgayCapNhat, NgayTao;


    public PhanBo() {
    }


    public PhanBo(int maPB, int maTS, int maND, int maP, int soLuong, String ghiChu, String tenTS, String tenLTS, String tenNTS, String tenP, String ngayCapNhat, String ngayTao) {
        MaPB = maPB;
        MaTS = maTS;
        MaND = maND;
        MaP = maP;
        SoLuong = soLuong;
        GhiChu = ghiChu;
        TenTS = tenTS;
        TenLTS = tenLTS;
        TenNTS = tenNTS;
        TenP = tenP;
        NgayCapNhat = ngayCapNhat;
        NgayTao = ngayTao;
    }

    public int getMaPB() {
        return MaPB;
    }

    public void setMaPB(int maPB) {
        MaPB = maPB;
    }

    public int getMaTS() {
        return MaTS;
    }

    public void setMaTS(int maTS) {
        MaTS = maTS;
    }

    public int getMaND() {
        return MaND;
    }

    public void setMaND(int maND) {
        MaND = maND;
    }

    public int getMaP() {
        return MaP;
    }

    public void setMaP(int maP) {
        MaP = maP;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String ghiChu) {
        GhiChu = ghiChu;
    }

    public String getTenTS() {
        return TenTS;
    }

    public void setTenTS(String tenTS) {
        TenTS = tenTS;
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

    public String getTenP() {
        return TenP;
    }

    public void setTenP(String tenP) {
        TenP = tenP;
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
}
