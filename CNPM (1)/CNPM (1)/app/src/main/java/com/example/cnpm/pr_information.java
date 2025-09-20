package com.example.cnpm;

public class pr_information {
    private String masanpham, tensanpham, mota;
    private int giaca, soluong;

    pr_information(String masanpham, String tensanpham, double giaca, String mota)
    {
        this.masanpham = masanpham;
        this.tensanpham = tensanpham;
        this.giaca = (int) giaca;
        this.mota = mota;
    }
    public pr_information()
    {}

    public String getMasanpham() { return masanpham; }
    public void setMasanpham(String masanpham) { this.masanpham = masanpham; }

    public String getTensanpham() { return tensanpham; }
    public void setTensanpham(String tensanpham) { this.tensanpham = tensanpham; }

    public int getGiaca() { return giaca; }
    public void setGiaca(int giaca) { this.giaca = giaca; }

    public String getMota() { return mota; }
    public void setMota(String mota) { this.mota = mota; }
}

