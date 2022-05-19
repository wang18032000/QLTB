package ptithcm.com.qltb.activity;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import ptithcm.com.qltb.R;
import ptithcm.com.qltb.model.PhieuNhap;
import ptithcm.com.qltb.model.PhieuThanhLy;

public class DanhSachPhieuThanhLyActivity extends AppCompatActivity {

    ListView lvPhieuNhap;
    ArrayAdapter<PhieuThanhLy> phieuTLAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_phieu_thanh_ly);
        addControls();
        addEvents();
    }

    private void addEvents() {

    }

    private void addControls() {
        lvPhieuNhap = (ListView) findViewById(R.id.lvPhieuTL);
        phieuTLAdapter = new ArrayAdapter<PhieuThanhLy>(DanhSachPhieuThanhLyActivity.this, android.R.layout.simple_list_item_1);
        lvPhieuNhap.setAdapter(phieuTLAdapter);

        getPhieuTLDB();
    }

    private void getPhieuTLDB() {
        LoginActivity.database = openOrCreateDatabase(LoginActivity.DATABASE_NAME,MODE_PRIVATE, null);
        Cursor cursor = LoginActivity.database.query("PHIEUTHANHLY",null,null,null,null,null,null);
        phieuTLAdapter.clear();
        while (cursor.moveToNext()){
            String maPN = cursor.getString(0);
            String thoigian = cursor.getString(1);
            String maNV = cursor.getString(2);
            PhieuThanhLy ptl = new PhieuThanhLy(maPN,thoigian,maNV);
            phieuTLAdapter.add(ptl);
        }
        cursor.close();
    }
}
