package ptithcm.com.qltb.model;

import java.io.Serializable;

/**
 * Created by apoll on 4/30/2022.
 */

public class PhieuNhap implements Serializable {
    private String maPN;
    private String thoiGianNhap;
    private String ghiChu;
    private String maNV;

    public PhieuNhap() {
    }

    public PhieuNhap(String maPN, String thoiGianNhap, String ghiChu, String maNV) {
        this.maPN = maPN;
        this.thoiGianNhap = thoiGianNhap;
        this.ghiChu = ghiChu;
        this.maNV = maNV;
    }

    public String getMaPN() {
        return maPN;
    }

    public void setMaPN(String maPN) {
        this.maPN = maPN;
    }

    public String getThoiGianNhap() {
        return thoiGianNhap;
    }

    public void setThoiGianNhap(String thoiGianNhap) {
        this.thoiGianNhap = thoiGianNhap;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }
}
