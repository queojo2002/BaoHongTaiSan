package com.example.baohongtaisan_2.Model;

public class Phong {

    private int MaP, MaLP, MaKVP;
    private String TenP, TenKVP, TenLP, NgayCapNhat, NgayTao;


    public Phong() {
    }


    public Phong(int maP, int maLP, int maKVP, String tenP, String tenKVP, String tenLP, String ngayCapNhat, String ngayTao) {
        MaP = maP;
        MaLP = maLP;
        MaKVP = maKVP;
        TenP = tenP;
        TenKVP = tenKVP;
        TenLP = tenLP;
        NgayCapNhat = ngayCapNhat;
        NgayTao = ngayTao;
    }

    public int getMaP() {
        return MaP;
    }

    public void setMaP(int maP) {
        MaP = maP;
    }

    public int getMaLP() {
        return MaLP;
    }

    public void setMaLP(int maLP) {
        MaLP = maLP;
    }

    public int getMaKVP() {
        return MaKVP;
    }

    public void setMaKVP(int maKVP) {
        MaKVP = maKVP;
    }

    public String getTenP() {
        return TenP;
    }

    public void setTenP(String tenP) {
        TenP = tenP;
    }

    public String getTenKVP() {
        return TenKVP;
    }

    public void setTenKVP(String tenKVP) {
        TenKVP = tenKVP;
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
}
