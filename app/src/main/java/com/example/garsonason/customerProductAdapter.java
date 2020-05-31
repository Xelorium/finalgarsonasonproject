package com.example.garsonason;

public class customerProductAdapter {
    private String date;
    private String urunAdi;
    private String urunFiyat;
    private String urunTipi;

    public String getUrunDurumu() {
        return urunDurumu;
    }

    public void setUrunDurumu(String urunDurumu) {
        this.urunDurumu = urunDurumu;
    }

    private String urunDurumu;

    public customerProductAdapter() {

    }

    public customerProductAdapter(String date, String urunAdi, String urunFiyat, String urunTipi, String urunDurumu) {
        this.date = date;
        this.urunAdi = urunAdi;
        this.urunFiyat = urunFiyat;
        this.urunTipi = urunTipi;
        this.urunDurumu = urunDurumu;
    }

    public String getkayitTarihi() {
        return date;
    }

    public String geturunAdi() {
        return urunAdi;
    }

    public String geturunFiyat() {
        return urunFiyat;
    }

    public String geturunTipi() {
        return urunTipi;
    }



}
