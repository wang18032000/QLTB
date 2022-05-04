package ptithcm.com.qltb.model;

import java.io.Serializable;

/**
 * Created by apoll on 4/30/2022.
 */
public class ThietBi implements Serializable {
    private String maTB;
    private String tenTB;
    private String ghiChu;
    private String maLoai;
    private String trangThai;
    private String tinhTrang;

    public ThietBi() {
    }

    public ThietBi(String maTB, String tenTB, String ghiChu, String maLoai, String trangThai, String tinhTrang) {
        this.maTB = maTB;
        this.tenTB = tenTB;
        this.ghiChu = ghiChu;
        this.maLoai = maLoai;
        this.trangThai = trangThai;
        this.tinhTrang = tinhTrang;
    }

    public String getMaTB() {
        return maTB;
    }

    public void setMaTB(String maTB) {
        this.maTB = maTB;
    }

    public String getTenTB() {
        return tenTB;
    }

    public void setTenTB(String tenTB) {
        this.tenTB = tenTB;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    @Override
    public String toString() {
        return tenTB;
    }
}
