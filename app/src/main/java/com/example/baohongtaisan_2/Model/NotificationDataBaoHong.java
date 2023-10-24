package com.example.baohongtaisan_2.Model;

public class NotificationDataBaoHong {
    private int MaND;
    private String TenTS, TenP;
    private int TrangThai, TinhTrang;
    private String MoTa;


    public NotificationDataBaoHong() {
    }

    public NotificationDataBaoHong(int maND, String tenTS, String tenP, int trangThai, int tinhTrang, String moTa) {
        MaND = maND;
        TenTS = tenTS;
        TenP = tenP;
        TrangThai = trangThai;
        TinhTrang = tinhTrang;
        MoTa = moTa;
    }

    public int getMaND() {
        return MaND;
    }

    public void setMaND(int maND) {
        MaND = maND;
    }

    public String getTenTS() {
        return TenTS;
    }

    public void setTenTS(String tenTS) {
        TenTS = tenTS;
    }

    public String getTenP() {
        return TenP;
    }

    public void setTenP(String tenP) {
        TenP = tenP;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int trangThai) {
        TrangThai = trangThai;
    }

    public int getTinhTrang() {
        return TinhTrang;
    }

    public void setTinhTrang(int tinhTrang) {
        TinhTrang = tinhTrang;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String moTa) {
        MoTa = moTa;
    }
}
