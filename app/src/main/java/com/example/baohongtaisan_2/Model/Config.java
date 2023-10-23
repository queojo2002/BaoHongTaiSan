package com.example.baohongtaisan_2.Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Config {
    private static Config instance;
    private final LocalDateTime now;
    private final DateTimeFormatter formatter;
    private String NgayHienTai;


    private Config() {
        now = LocalDateTime.now();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        NgayHienTai = now.format(formatter);
    }

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }


    public String getNgayHienTai() {
        return NgayHienTai;
    }

    public void setNgayHienTai(String ngayHienTai) {
        NgayHienTai = ngayHienTai;
    }
}
