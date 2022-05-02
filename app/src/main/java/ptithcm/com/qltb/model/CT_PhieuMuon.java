package ptithcm.com.qltb.model;

import java.io.Serializable;

/**
 * Created by apoll on 4/30/2022.
 */

public class CT_PhieuMuon implements Serializable {
    private String maPM;
    private String maTB;
    private String thoiGianTra;

    public CT_PhieuMuon() {
    }

    public CT_PhieuMuon(String maPM, String maTB, String thoiGianTra) {
        this.maPM = maPM;
        this.maTB = maTB;
        this.thoiGianTra = thoiGianTra;
    }

    public String getMaPM() {
        return maPM;
    }

    public void setMaPM(String maPM) {
        this.maPM = maPM;
    }

    public String getMaTB() {
        return maTB;
    }

    public void setMaTB(String maTB) {
        this.maTB = maTB;
    }

    public String getThoiGianTra() {
        return thoiGianTra;
    }

    public void setThoiGianTra(String thoiGianTra) {
        this.thoiGianTra = thoiGianTra;
    }

    @Override
    public String toString() {
        return maTB;
    }
}

