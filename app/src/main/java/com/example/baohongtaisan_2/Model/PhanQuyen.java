package com.example.baohongtaisan_2.Model;

public class PhanQuyen {
    private int MaPQ;
    private String TenPQ, NgayCapNhat, NgayTao;


    public PhanQuyen() {
    }

    public PhanQuyen(int maPQ, String tenPQ, String ngayCapNhat, String ngayTao) {
        MaPQ = maPQ;
        TenPQ = tenPQ;
        NgayCapNhat = ngayCapNhat;
        NgayTao = ngayTao;
    }

    public int getMaPQ() {
        return MaPQ;
    }

    public void setMaPQ(int maPQ) {
        MaPQ = maPQ;
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
}
