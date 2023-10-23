package com.example.baohongtaisan_2.Model;

public class DonVi {
    private int MaDV;
    private String TenDV,MoTaDV, NgayCapNhat, NgayTao;

    public DonVi() {
    }

    public DonVi(int maDV, String tenDV, String moTaDV, String ngayCapNhat, String ngayTao) {
        MaDV = maDV;
        TenDV = tenDV;
        MoTaDV = moTaDV;
        NgayCapNhat = ngayCapNhat;
        NgayTao = ngayTao;
    }

    public int getMaDV() {
        return MaDV;
    }

    public void setMaDV(int maDV) {
        MaDV = maDV;
    }

    public String getTenDV() {
        return TenDV;
    }

    public void setTenDV(String tenDV) {
        TenDV = tenDV;
    }

    public String getMoTaDV() {
        return MoTaDV;
    }

    public void setMoTaDV(String moTaDV) {
        MoTaDV = moTaDV;
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
