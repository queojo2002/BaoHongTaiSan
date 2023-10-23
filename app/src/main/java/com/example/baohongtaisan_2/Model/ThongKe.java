package com.example.baohongtaisan_2.Model;

public class ThongKe {
    private int TongSo_TaiSan, TongSo_NguoiDung, TongSo_PhongHoc;

    public ThongKe() {
    }

    public ThongKe(int tongSo_TaiSan, int tongSo_NguoiDung, int tongSo_PhongHoc) {
        TongSo_TaiSan = tongSo_TaiSan;
        TongSo_NguoiDung = tongSo_NguoiDung;
        TongSo_PhongHoc = tongSo_PhongHoc;
    }

    public int getTongSo_TaiSan() {
        return TongSo_TaiSan;
    }

    public void setTongSo_TaiSan(int tongSo_TaiSan) {
        TongSo_TaiSan = tongSo_TaiSan;
    }

    public int getTongSo_NguoiDung() {
        return TongSo_NguoiDung;
    }

    public void setTongSo_NguoiDung(int tongSo_NguoiDung) {
        TongSo_NguoiDung = tongSo_NguoiDung;
    }

    public int getTongSo_PhongHoc() {
        return TongSo_PhongHoc;
    }

    public void setTongSo_PhongHoc(int tongSo_PhongHoc) {
        TongSo_PhongHoc = tongSo_PhongHoc;
    }
}
