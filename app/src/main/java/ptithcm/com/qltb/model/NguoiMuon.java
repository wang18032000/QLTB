package ptithcm.com.qltb.model;

import java.io.Serializable;

/**
 * Created by apoll on 4/30/2022.
 */

public class NguoiMuon implements Serializable {
    private String maNM;
    private String ho;
    private String ten;
    private String gioiTinh;
    private String ngaySinh;
    private String diaChi;
    private String cmnd;
    private String ghiChu;
    private String loai;

    public NguoiMuon() {
    }

    public NguoiMuon(String maNM, String ho, String ten, String gioiTinh, String ngaySinh, String diaChi, String cmnd, String ghiChu, String loai) {
        this.maNM = maNM;
        this.ho = ho;
        this.ten = ten;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.cmnd = cmnd;
        this.ghiChu = ghiChu;
        this.loai = loai;
    }

    public String getMaNM() {
        return maNM;
    }

    public void setMaNM(String maNM) {
        this.maNM = maNM;
    }

    public String getHo() {
        return ho;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    @Override
    public String toString() {
        return  ho+" "+ten;
    }
}
