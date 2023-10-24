package com.example.baohongtaisan_2.Model;

public class NotificationDataBaoHong {
    private int MaND;
    private String TenTS, TenP, TrangThai, TinhTrang, MoTa;


    public NotificationDataBaoHong() {
    }

    public NotificationDataBaoHong(int maND, String tenTS, String tenP, String trangThai, String tinhTrang, String moTa) {
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

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String trangThai) {
        TrangThai = trangThai;
    }

    public String getTinhTrang() {
        return TinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        TinhTrang = tinhTrang;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String moTa) {
        MoTa = moTa;
    }

}
