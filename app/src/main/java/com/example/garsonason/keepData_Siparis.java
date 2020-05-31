package com.example.garsonason;

public class keepData_Siparis {

    private String durum;
    private String siparis;
    private String tarih;


    public keepData_Siparis() {

    }

    public keepData_Siparis(String durum, String siparis, String tarih) {

        this.durum = durum;
        this.siparis = siparis;
        this.tarih = tarih;

    }


    public String getDurum() {
        return durum;
    }

    public String getSiparis() {
        return siparis;
    }

    public String getTarih() {
        return tarih;
    }


}


