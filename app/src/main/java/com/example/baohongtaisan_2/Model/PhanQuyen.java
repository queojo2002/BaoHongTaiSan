package com.example.baohongtaisan_2.Model;

public class PhanQuyen {
    private String MaPQ, TenPQ, NgayCapNhat, NgayTao;


    public PhanQuyen() {
    }


    public PhanQuyen(String maPQ, String tenPQ, String ngayCapNhat, String ngayTao) {
        MaPQ = maPQ;
        TenPQ = tenPQ;
        NgayCapNhat = ngayCapNhat;
        NgayTao = ngayTao;
    }


    public PhanQuyen(String maPQ, String tenPQ) {
        MaPQ = maPQ;
        TenPQ = tenPQ;
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

    public String getMaPQ() {
        return MaPQ;
    }

    public void setMaPQ(String maPQ) {
        MaPQ = maPQ;
    }
}
