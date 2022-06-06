package ptithcm.com.qltb.model;

import java.io.Serializable;

/**
 * Created by apoll on 4/30/2022.
 */
public class ThietBi implements Serializable {
    private String maTB;
    private String tenTB;
    private String maTT;
    private String maLoai;
    private String maP;

    public ThietBi() {
    }

    public ThietBi(String maTB, String tenTB, String maTT, String maLoai, String maP) {
        this.maTB = maTB;
        this.tenTB = tenTB;
        this.maTT = maTT;
        this.maLoai = maLoai;
        this.maP = maP;
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

    public String getMaTT() {
        return maTT;
    }

    public void setMaTT(String maTT) {
        this.maTT = maTT;
    }

    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }

    public String getMaP() {
        return maP;
    }

    public void setMaP(String maP) {
        this.maP = maP;
    }

    @Override
    public String toString() {
        return tenTB;
    }
}
