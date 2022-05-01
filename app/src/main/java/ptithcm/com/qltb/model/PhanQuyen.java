package ptithcm.com.qltb.model;

import java.io.Serializable;

/**
 * Created by apoll on 4/30/2022.
 */

public class PhanQuyen implements Serializable {

    private String maPQ;
    private String tenPQ;

    public PhanQuyen() {
    }

    public PhanQuyen(String maPQ, String tenPQ) {
        this.maPQ = maPQ;
        this.tenPQ = tenPQ;
    }

    public String getMaPQ() {
        return maPQ;
    }

    public void setMaPQ(String maPQ) {
        this.maPQ = maPQ;
    }

    public String getTenPQ() {
        return tenPQ;
    }

    public void setTenPQ(String tenPQ) {
        this.tenPQ = tenPQ;
    }
}
