package ptithcm.com.qltb.model;

import java.io.Serializable;

/**
 * Created by apoll on 4/30/2022.
 */

public class CT_PhieuNhap implements Serializable {
    private String maPN;
    private String maTB;
    private String donGia;

    public CT_PhieuNhap() {
    }

    public CT_PhieuNhap(String maPN, String maTB, String donGia) {
        this.maPN = maPN;
        this.maTB = maTB;
        this.donGia = donGia;
    }

    public String getMaPN() {

        return maPN;
    }

    public void setMaPN(String maPN) {
        this.maPN = maPN;
    }

    public String getMaTB() {
        return maTB;
    }

    public void setMaTB(String maTB) {
        this.maTB = maTB;
    }

    public String getDonGia() {
        return donGia;
    }

    public void setDonGia(String donGia) {
        this.donGia = donGia;
    }

    @Override
    public String toString() {
        return maTB +" - " + donGia +" VND";
    }
}
