package ptithcm.com.qltb.model;

import java.io.Serializable;

/**
 * Created by apoll on 4/30/2022.
 */

public class PhieuMuon implements Serializable {
    private String maPM;
    private String thoiGianMuon;
    private String ghiChu;
    private String maMN;
    private String maNV;
    private String maP;


    public PhieuMuon() {
    }

    public PhieuMuon(String maPM, String thoiGianMuon, String ghiChu,  String maNV,String maMN, String maP) {
        this.maPM = maPM;
        this.thoiGianMuon = thoiGianMuon;
        this.ghiChu = ghiChu;
        this.maMN = maMN;
        this.maNV = maNV;
        this.maP = maP;
    }

    public String getMaPM() {
        return maPM;
    }

    public void setMaPM(String maPM) {
        this.maPM = maPM;
    }

    public String getThoiGianMuon() {
        return thoiGianMuon;
    }

    public void setThoiGianMuon(String thoiGianMuon) {
        this.thoiGianMuon = thoiGianMuon;
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

    public String getMaP() {
        return maP;
    }

    public void setMaP(String maP) {
        this.maP = maP;
    }

    @Override
    public String toString() {
        return maMN+" "+thoiGianMuon;
    }
}

