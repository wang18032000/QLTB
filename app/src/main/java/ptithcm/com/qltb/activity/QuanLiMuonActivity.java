package ptithcm.com.qltb.activity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import ptithcm.com.qltb.R;
import ptithcm.com.qltb.model.NhanVien;
import ptithcm.com.qltb.model.PhieuMuon;

public class QuanLiMuonActivity extends AppCompatActivity {

    ListView lvPhieuMuon;
    ImageView imgThemPM;
    ArrayAdapter<PhieuMuon> phieuMuonAdapter;
    NhanVien nhanVien;
    PhieuMuon phieuMuon;
    String MaNV;
    Dialog dialogInfo,dialogAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_li_muon);
        addcontrols();
        addEvents();
    }

    private void addEvents() {
        lvPhieuMuon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                phieuMuon = phieuMuonAdapter.getItem(i);
                Intent intent = new Intent(QuanLiMuonActivity.this, PhieuMuonActivity.class);
                intent.putExtra("phieumuon",phieuMuon);
                startActivity(intent);
            }
        });
        imgThemPM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuanLiMuonActivity.this, PhieuMuonActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addcontrols() {
        Intent intent = getIntent();
        nhanVien = (NhanVien) intent.getSerializableExtra("nhanvien");
        MaNV = nhanVien.getMaNV();

        lvPhieuMuon = (ListView) findViewById(R.id.lvPhieuMuon);
        phieuMuonAdapter = new ArrayAdapter<PhieuMuon>(QuanLiMuonActivity.this, android.R.layout.simple_list_item_1);
        lvPhieuMuon.setAdapter(phieuMuonAdapter);
        imgThemPM = (ImageView) findViewById(R.id.imgThemPM);

        getPhieuMuonFromDB(MaNV);

    }

    private void getPhieuMuonFromDB(String ma) {
        LoginActivity.database = openOrCreateDatabase(LoginActivity.DATABASE_NAME,MODE_PRIVATE, null);
        Cursor cursor = LoginActivity.database.query("PHIEUMUON",null,"MaNV=?",new String[]{ma},null,null,null);
        phieuMuonAdapter.clear();
        while (cursor.moveToNext()){
            String maPM = cursor.getString(0);
            String phong = cursor.getString(1);
            String ghiChu = cursor.getString(2);
            String maMN = cursor.getString(3);
            String maNV = cursor.getString(4);
            String thoiGian = cursor.getString(5);
            PhieuMuon pm = new PhieuMuon(maPM, phong, ghiChu, maMN, maNV, thoiGian);
            phieuMuonAdapter.add(pm);
        }
        cursor.close();
    }

}
