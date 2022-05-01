package ptithcm.com.qltb.model;

import java.io.Serializable;
import java.io.StringReader;

/**
 * Created by apoll on 4/30/2022.
 */

public class LoaiThietBi implements Serializable {
    private String maLoai;
    private String tenLoai;

    public LoaiThietBi() {
    }

    public LoaiThietBi(String maLoai, String tenLoai) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
    }

    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    @Override
    public String toString() {
        return "LoaiThietBi{" +
                "tenLoai='" + tenLoai + '\'' +
                '}';
    }
}
