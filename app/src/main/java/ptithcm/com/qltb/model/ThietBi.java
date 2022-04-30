package ptithcm.com.qltb.model;

/**
 * Created by apoll on 4/30/2022.
 */
public class ThietBi {
    private static ThietBi ourInstance = new ThietBi();

    public static ThietBi getInstance() {
        return ourInstance;
    }

    private ThietBi() {
    }
}
