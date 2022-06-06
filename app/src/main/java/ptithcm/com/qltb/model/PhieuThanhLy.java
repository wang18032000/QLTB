package ptithcm.com.qltb.model;

import java.io.Serializable;

/**
 * Created by apoll on 4/30/2022.
 */

public class PhieuThanhLy implements Serializable {
    private String maPTL;
    private String thoiGianTL;
    private String ghiChu;
    private String maNV;

    public PhieuThanhLy() {
    }

    public PhieuThanhLy(String maPTL, String thoiGianTL, String ghiChu, String maNV) {
        this.maPTL = maPTL;
        this.thoiGianTL = thoiGianTL;
        this.ghiChu = ghiChu;
        this.maNV = maNV;
    }

    public String getMaPTL() {
        return maPTL;
    }

    public void setMaPTL(String maPTL) {
        this.maPTL = maPTL;
    }

    public String getThoiGianTL() {
        return thoiGianTL;
    }

    public void setThoiGianTL(String thoiGianTL) {
        this.thoiGianTL = thoiGianTL;
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

    @Override
    public String toString() {
        return maPTL+" "+thoiGianTL;
    }
}
