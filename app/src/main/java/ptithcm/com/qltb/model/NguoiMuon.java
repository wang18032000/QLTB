package ptithcm.com.qltb.model;

import java.io.Serializable;

/**
 * Created by apoll on 4/30/2022.
 */

public class NguoiMuon implements Serializable {
    private String maNM;
    private String hoTen;
    private String gioiTinh;
    private String sDT;
    private String cmnd;
    private String loai;

    public NguoiMuon() {
    }

    public NguoiMuon(String maNM, String hoTen, String gioiTinh, String sDT, String cmnd, String loai) {
        this.maNM = maNM;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.sDT = sDT;
        this.cmnd = cmnd;
        this.loai = loai;
    }

    public String getMaNM() {
        return maNM;
    }

    public void setMaNM(String maNM) {
        this.maNM = maNM;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getsDT() {
        return sDT;
    }

    public void setsDT(String sDT) {
        this.sDT = sDT;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    @Override
    public String toString() {
        return hoTen;
    }
}
