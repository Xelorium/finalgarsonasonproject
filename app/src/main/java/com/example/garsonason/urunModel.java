package com.example.garsonason;

import java.io.Serializable;

public class urunModel implements Serializable {


    public String urunAdi;
    public Integer miktar;
    public Integer fiyat;
    public String durum;
    public String siparisId;


    public urunModel(String urunAdi, Integer miktar, Integer fiyat, String durum, String siparisId) {
        this.urunAdi = urunAdi;
        this.miktar = miktar;
        this.fiyat = fiyat;
        this.durum = durum;
        this.siparisId = siparisId;

    }


    public String getSiparisId() {
        return siparisId;
    }

    public void setSiparisId(String siparisId) {
        this.siparisId = siparisId;
    }

    public String getDurum() {
        return durum;
    }

    public void setDurum(String durum) {
        this.durum = durum;
    }

    public String getUrunAdi() {
        return urunAdi;
    }

    public void setUrunAdi(String urunAdi) {
        this.urunAdi = urunAdi;
    }

    public Integer getMiktar() {
        return miktar;
    }

    public void setMiktar(Integer miktar) {
        this.miktar = miktar;
    }

    public Integer getFiyat() {
        return fiyat;
    }

    public void setFiyat(Integer fiyat) {
        this.fiyat = fiyat;
    }


    public urunModel() {
    }
}
