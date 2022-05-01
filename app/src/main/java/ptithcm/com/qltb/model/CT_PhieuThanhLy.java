package ptithcm.com.qltb.model;

import java.io.Serializable;

/**
 * Created by apoll on 4/30/2022.
 */

public class CT_PhieuThanhLy implements Serializable {
    private String maPTL;
    private String maTB;
    private String donGia;

    public CT_PhieuThanhLy() {
    }

    public CT_PhieuThanhLy(String maPTL, String maTB, String donGia) {
        this.maPTL = maPTL;
        this.maTB = maTB;
        this.donGia = donGia;
    }

    public String getMaPTL() {
        return maPTL;
    }

    public void setMaPTL(String maPTL) {
        this.maPTL = maPTL;
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
}
