package com.example.garsonason;

public class keepData_2 {

    private String adres;
    private String ePosta;
    private String kullaniciAdi;
    private String kullaniciTuru;
    private String puan;
    private String sifre;
    private String telNo;
    private String isletmeKodu;


    public keepData_2() {

    }

    public keepData_2(String adres, String ePosta, String kullaniciAdi, String kullaniciTuru, String puan, String sifre, String telNo, String isletmeKodu) {

        this.adres = adres;
        this.ePosta = ePosta;
        this.kullaniciAdi = kullaniciAdi;
        this.kullaniciTuru = kullaniciTuru;
        this.puan = puan;
        this.sifre = sifre;
        this.telNo = telNo;
        this.isletmeKodu = isletmeKodu;
    }

    public String getAdres() {
        return adres;

    }

    public String getePosta() {
        return ePosta;
    }

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public String getKullaniciTuru() {
        return kullaniciTuru;
    }

    public String getPuan() {
        return puan;
    }

    public String getSifre() {
        return sifre;
    }

    public String getTelNo() {
        return telNo;
    }

    public String getIsletmeKodu() {
        return isletmeKodu;
    }
}
