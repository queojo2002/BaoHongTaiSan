package com.example.baohongtaisan_2.Model;

public class ChucDanh {
    private int MaCD;
    private String TenCD;
    private String MoTaCD;
    private String NgayCapNhat;
    private String NgayTao;


    public ChucDanh() {
    }

    public ChucDanh(int maCD, String tenCD, String moTaCD, String ngayCapNhat, String ngayTao) {
        MaCD = maCD;
        TenCD = tenCD;
        MoTaCD = moTaCD;
        NgayCapNhat = ngayCapNhat;
        NgayTao = ngayTao;
    }


    public int getMaCD() {
        return MaCD;
    }

    public void setMaCD(int maCD) {
        MaCD = maCD;
    }

    public String getTenCD() {
        return TenCD;
    }

    public void setTenCD(String tenCD) {
        TenCD = tenCD;
    }

    public String getMoTaCD() {
        return MoTaCD;
    }

    public void setMoTaCD(String moTaCD) {
        MoTaCD = moTaCD;
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
