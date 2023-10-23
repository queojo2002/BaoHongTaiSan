package com.example.baohongtaisan_2.Model;

import androidx.annotation.NonNull;

public class KhuVucPhong {
    private int MaKVP;
    private String TenKVP, NgayCapNhat, NgayTao;

    public KhuVucPhong() {
    }

    public KhuVucPhong(int maKVP, String tenKVP, String ngayCapNhat, String ngayTao) {
        MaKVP = maKVP;
        TenKVP = tenKVP;
        NgayCapNhat = ngayCapNhat;
        NgayTao = ngayTao;
    }

    public int getMaKVP() {
        return MaKVP;
    }

    public void setMaKVP(int maKVP) {
        MaKVP = maKVP;
    }

    public String getTenKVP() {
        return TenKVP;
    }

    public void setTenKVP(String tenKVP) {
        TenKVP = tenKVP;
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
        return getTenKVP();
    }
}
