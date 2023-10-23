package com.example.baohongtaisan_2.Model;

import androidx.annotation.NonNull;

public class LoaiPhong {

    private int MaLP;

    private String TenLP, NgayCapNhat, NgayTao;


    public LoaiPhong() {
    }

    public LoaiPhong(int maLP, String tenLP, String ngayCapNhat, String ngayTao) {
        MaLP = maLP;
        TenLP = tenLP;
        NgayCapNhat = ngayCapNhat;
        NgayTao = ngayTao;
    }

    public int getMaLP() {
        return MaLP;
    }

    public void setMaLP(int maLP) {
        MaLP = maLP;
    }

    public String getTenLP() {
        return TenLP;
    }

    public void setTenLP(String tenLP) {
        TenLP = tenLP;
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

    @NonNull
    @Override
    public String toString() {
        return getTenLP();
    }
}
