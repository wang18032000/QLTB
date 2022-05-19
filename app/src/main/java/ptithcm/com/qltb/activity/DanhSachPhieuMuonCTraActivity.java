package ptithcm.com.qltb.activity;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import ptithcm.com.qltb.R;
import ptithcm.com.qltb.model.CT_PhieuMuon;
import ptithcm.com.qltb.model.PhieuMuon;

public class DanhSachPhieuMuonCTraActivity extends AppCompatActivity {

    ListView lvPhieuMuon;
    ArrayAdapter<PhieuMuon> phieuMuonAdapterGet,phieuMuonAdapterPost;
    ArrayAdapter<CT_PhieuMuon> ct_phieuAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_phieu_muon_ctra);
        addControls();
        addEvents();
    }

    private void addEvents() {

    }

    private void addControls() {
        lvPhieuMuon = (ListView) findViewById(R.id.lvPhieuMuonCT);
        ct_phieuAdapter = new ArrayAdapter<CT_PhieuMuon>(DanhSachPhieuMuonCTraActivity.this, android.R.layout.simple_list_item_1);
        phieuMuonAdapterPost = new ArrayAdapter<PhieuMuon>(DanhSachPhieuMuonCTraActivity.this, android.R.layout.simple_list_item_1);
        phieuMuonAdapterGet = new ArrayAdapter<PhieuMuon>(DanhSachPhieuMuonCTraActivity.this, android.R.layout.simple_list_item_1);
        lvPhieuMuon.setAdapter(ct_phieuAdapter);

        getPhieuMuonFromDB();
        for (int i = 0;i < phieuMuonAdapterGet.getCount();i++){

        }

//        ct_phieuAdapter = new ArrayAdapter<CT_PhieuMuon>(DanhSachPhieuMuonCTraActivity.this, android.R.layout.simple_list_item_1);
        getCTFromDB();
    }
    private void getCTFromDB() {
        LoginActivity.database = openOrCreateDatabase(LoginActivity.DATABASE_NAME,MODE_PRIVATE, null);
        Cursor cursor = LoginActivity.database.query("CT_PHIEUMUON",null,null,null,null,null,null);
        ct_phieuAdapter.clear();
        while (cursor.moveToNext()){
            String maPM = cursor.getString(0);
            String maTB = cursor.getString(1);
            String thoigiantra = cursor.getString(2);
            if (thoigiantra.equals("")||thoigiantra.equals(null)){
//                phieuMuonAdapterGet = new ArrayAdapter<PhieuMuon>(DanhSachPhieuMuonCTraActivity.this, android.R.layout.simple_list_item_1);
//                getPhieuMuonFromDB(maPM);
                CT_PhieuMuon ct = new CT_PhieuMuon(maPM, maTB, thoigiantra);
                ct_phieuAdapter.add(ct);
            }

        }
        cursor.close();
    }
    private void getPhieuMuonFromDB() {
        LoginActivity.database = openOrCreateDatabase(LoginActivity.DATABASE_NAME,MODE_PRIVATE, null);
        Cursor cursor = LoginActivity.database.query("PHIEUMUON",null,null,null,null,null,null);
        phieuMuonAdapterGet.clear();
        while (cursor.moveToNext()){
            String maPM = cursor.getString(0);
            String phong = cursor.getString(1);
            String ghiChu = cursor.getString(2);
            String maMN = cursor.getString(3);
            String maNV = cursor.getString(4);
            String thoiGian = cursor.getString(5);
            PhieuMuon pm = new PhieuMuon(maPM, phong, ghiChu, maMN, maNV, thoiGian);
            phieuMuonAdapterGet.add(pm);
        }
        cursor.close();
    }
}
