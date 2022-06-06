package ptithcm.com.qltb.model;

import java.io.Serializable;

/**
 * Created by apoll on 5/31/2022.
 */

public class TrangThai implements Serializable {
    private String maTT;
    private String tenTT;

    public TrangThai() {
    }

    public TrangThai(String maTT, String tenTT) {
        this.maTT = maTT;
        this.tenTT = tenTT;
    }

    public String getMaTT() {
        return maTT;
    }

    public void setMaTT(String maTT) {
        this.maTT = maTT;
    }

    public String getTenTT() {
        return tenTT;
    }

    public void setTenTT(String tenTT) {
        this.tenTT = tenTT;
    }

    @Override
    public String toString() {
        return tenTT;
    }
}
