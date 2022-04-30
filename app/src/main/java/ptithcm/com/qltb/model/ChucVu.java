package ptithcm.com.qltb.model;

import java.io.Serializable;

/**
 * Created by apoll on 4/30/2022.
 */

public class ChucVu implements Serializable {
    private String maCV;
    private String tenCV;

    public ChucVu() {
    }

    public ChucVu(String maCV, String tenCV) {
        this.maCV = maCV;
        this.tenCV = tenCV;
    }

    public String getMaCV() {
        return maCV;
    }

    public void setMaCV(String maCV) {
        this.maCV = maCV;
    }

    @Override
    public String toString() {
        return tenCV ;
    }
}
