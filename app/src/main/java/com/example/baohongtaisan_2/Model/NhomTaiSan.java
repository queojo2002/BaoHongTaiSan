package com.example.baohongtaisan_2.Model;

public class NhomTaiSan {
    private String MaNTS, TenNTS, NgayCapNhat, NgayTao;


    public NhomTaiSan() {
    }

    public NhomTaiSan(String maNTS, String tenNTS) {
        MaNTS = maNTS;
        TenNTS = tenNTS;
    }

    public NhomTaiSan(String maNTS, String tenNTS, String ngayCapNhat, String ngayTao) {
        MaNTS = maNTS;
        TenNTS = tenNTS;
        NgayCapNhat = ngayCapNhat;
        NgayTao = ngayTao;
    }

    public String getMaNTS() {
        return MaNTS;
    }

    public void setMaNTS(String maNTS) {
        MaNTS = maNTS;
    }

    public String getTenNTS() {
        return TenNTS;
    }

    public void setTenNTS(String tenNTS) {
        TenNTS = tenNTS;
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
