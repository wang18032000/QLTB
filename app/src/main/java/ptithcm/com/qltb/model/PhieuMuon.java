package ptithcm.com.qltb.model;

import java.io.Serializable;

/**
 * Created by apoll on 4/30/2022.
 */

public class PhieuMuon implements Serializable {
    private String maPM;
    private String phong;
    private String ghiChu;
    private String maMN;
    private String maNV;
    private String thoiGianMuon;

    public PhieuMuon() {
    }

    public PhieuMuon(String maPM, String phong, String ghiChu, String maMN, String maNV, String thoiGianMuon) {
        this.maPM = maPM;
        this.phong = phong;
        this.ghiChu = ghiChu;
        this.maMN = maMN;
        this.maNV = maNV;
        this.thoiGianMuon = thoiGianMuon;
    }

    public String getMaPM() {
        return maPM;
    }

    public void setMaPM(String maPM) {
        this.maPM = maPM;
    }

    public String getPhong() {
        return phong;
    }

    public void setPhong(String phong) {
        this.phong = phong;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getMaMN() {
        return maMN;
    }

    public void setMaMN(String maMN) {
        this.maMN = maMN;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getThoiGianMuon() {
        return thoiGianMuon;
    }

    public void setThoiGianMuon(String thoiGianMuon) {
        this.thoiGianMuon = thoiGianMuon;
    }

    @Override
    public String toString() {
        return phong+" - "+thoiGianMuon;
    }
}

