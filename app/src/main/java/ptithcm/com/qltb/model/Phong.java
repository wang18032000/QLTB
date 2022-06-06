package ptithcm.com.qltb.model;

import java.io.Serializable;

/**
 * Created by apoll on 5/31/2022.
 */

public class Phong implements Serializable {
    private String maP;
    private String loaiP;

    public Phong() {
    }

    public Phong(String maP, String loaiP) {
        this.maP = maP;
        this.loaiP = loaiP;
    }

    public String getMaP() {
        return maP;
    }

    public void setMaP(String maP) {
        this.maP = maP;
    }

    public String getLoaiP() {
        return loaiP;
    }

    public void setLoaiP(String loaiP) {
        this.loaiP = loaiP;
    }

    @Override
    public String toString() {
        return maP;
    }
}
