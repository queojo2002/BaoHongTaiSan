package com.example.baohongtaisan_2.Model;

public class LoaiTaiSan {
    private int MaLTS;
    private String TenLTS, NgayCapNhat, NgayTao;


    public LoaiTaiSan() {
    }

    public LoaiTaiSan(int maLTS, String tenLTS) {
        MaLTS = maLTS;
        TenLTS = tenLTS;
    }

    public LoaiTaiSan(int maLTS, String tenLTS, String ngayCapNhat, String ngayTao) {
        MaLTS = maLTS;
        TenLTS = tenLTS;
        NgayCapNhat = ngayCapNhat;
        NgayTao = ngayTao;
    }

    public int getMaLTS() {
        return MaLTS;
    }

    public void setMaLTS(int maLTS) {
        MaLTS = maLTS;
    }

    public String getTenLTS() {
        return TenLTS;
    }

    public void setTenLTS(String tenLTS) {
        TenLTS = tenLTS;
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
