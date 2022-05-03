package ptithcm.com.qltb.activity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Date;
import java.util.Random;

import ptithcm.com.qltb.R;
import ptithcm.com.qltb.model.CT_PhieuMuon;
import ptithcm.com.qltb.model.NhanVien;
import ptithcm.com.qltb.model.PhieuMuon;
import ptithcm.com.qltb.model.ThietBi;

public class QuanLiMuonActivity extends AppCompatActivity {

    ListView lvPhieuMuon;
    ImageView imgThemPM;
    ArrayAdapter<PhieuMuon> phieuMuonAdapter;
    ArrayAdapter<CT_PhieuMuon> cTPMAdapter;
    ArrayAdapter<ThietBi> thietBiAdapter;
    NhanVien nhanVien;
    PhieuMuon phieuMuon;
    CT_PhieuMuon ct;
    String MaNV;
    Dialog dialogAdd;
    int kt;

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
                showDialogThemPM();
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

    private void getPMTheoMaNM(String ma) {
        LoginActivity.database = openOrCreateDatabase(LoginActivity.DATABASE_NAME,MODE_PRIVATE, null);
        Cursor cursor = LoginActivity.database.query("PHIEUMUON",null,"MaNM=?",new String[]{ma},null,null,null);
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

    private void getPhieuMuonFromDB(String ma) {
        LoginActivity.database = openOrCreateDatabase(LoginActivity.DATABASE_NAME,MODE_PRIVATE, null);
        Cursor cursor = LoginActivity.database.query("PHIEUMUON",null,"MaNV=?",new String[]{ma},null,null,null);
        phieuMuonAdapter.clear();
        while (cursor.moveToNext()){
            long millis=System.currentTimeMillis();
            java.sql.Date date = new java.sql.Date(millis);
            String toDay = String.valueOf(date);
            String thoiGian = cursor.getString(5);
            if (thoiGian.equals(toDay)){
                String maPM = cursor.getString(0);
                String phong = cursor.getString(1);
                String ghiChu = cursor.getString(2);
                String maMN = cursor.getString(3);
                String maNV = cursor.getString(4);
                PhieuMuon pm = new PhieuMuon(maPM, phong, ghiChu, maMN, maNV, thoiGian);
                phieuMuonAdapter.add(pm);
            }
        }
        cursor.close();
    }

    private void showDialogThemPM(){
        dialogAdd = new Dialog(QuanLiMuonActivity.this);
        dialogAdd.setContentView(R.layout.dialog_them_pm);

        final EditText edtMaNM = dialogAdd.findViewById(R.id.edtMaNM_PM_ADD);
        final EditText edtPhong = dialogAdd.findViewById(R.id.edtPhong_PM_ADD);
        final EditText edtGC = dialogAdd.findViewById(R.id.edtGC_PM_ADD);
        final Button btnThemPM = dialogAdd.findViewById(R.id.btnThemPM);

        btnThemPM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random rand = new Random();
                int ranNum = rand.nextInt(100)+1;
                String maPM = "PM"+String.valueOf(ranNum);
                String p = edtPhong.getText().toString();
                String gc = edtGC.getText().toString();
                String maNM = edtMaNM.getText().toString();
                // thieu Kiem tra da muon chua
                kiemTraMuon(maNM);
                if (kt == 0){
                String maNV = nhanVien.getMaNV();
                long millis=System.currentTimeMillis();
                java.sql.Date date = new java.sql.Date(millis);
                String ngay = String.valueOf(date);
                phieuMuon = new PhieuMuon(maPM,p,gc,maNM,maNV,ngay);
                update();
                }else {
                    Toast.makeText(QuanLiMuonActivity.this, "Người Mượn Chưa Trả", Toast.LENGTH_LONG).show();
                }

            }
        });

        dialogAdd.show();
    }

    private void kiemTraMuon(String ma) {
        kt =0;
        phieuMuonAdapter = new ArrayAdapter<PhieuMuon>(QuanLiMuonActivity.this, android.R.layout.simple_list_item_1);
        getPMTheoMaNM(ma);
        for (int i = 0;i<phieuMuonAdapter.getCount();i++){
            phieuMuon = phieuMuonAdapter.getItem(i);
            cTPMAdapter = new ArrayAdapter<CT_PhieuMuon>(QuanLiMuonActivity.this, android.R.layout.simple_list_item_1);
            getCTFromDB(phieuMuon.getMaPM());
            for (int j = 0;j<cTPMAdapter.getCount();j++){
                thietBiAdapter = new ArrayAdapter<ThietBi>(QuanLiMuonActivity.this, android.R.layout.simple_list_item_1);
                findTB(cTPMAdapter.getItem(j).getMaTB());
            }
        }
    }

    private void getCTFromDB(String ma) {
        LoginActivity.database = openOrCreateDatabase(LoginActivity.DATABASE_NAME,MODE_PRIVATE, null);
        Cursor cursor = LoginActivity.database.query("CT_PHIEUMUON",null,"MaPM=?",new String[]{ma},null,null,null);
        cTPMAdapter.clear();
        while (cursor.moveToNext()){
            String maPM = cursor.getString(0);
            String maTB = cursor.getString(1);
            String thoigiantra = cursor.getString(2);
            if (thoigiantra.equals("")){
                kt = 1;
            }
            CT_PhieuMuon ct = new CT_PhieuMuon(maPM, maTB, thoigiantra);
            cTPMAdapter.add(ct);
        }
        cursor.close();
    }

    private void findTB(String ma){
        LoginActivity.database = openOrCreateDatabase(LoginActivity.DATABASE_NAME, MODE_PRIVATE, null);
        Cursor cursor = LoginActivity.database.query("THIETBI", null,"MaTB=?",new String[]{ma},null,null,null);
        thietBiAdapter.clear();
        while (cursor.moveToNext()) {
            String matb = cursor.getString(0);
            String tentb = cursor.getString(1);
            String ghichu = cursor.getString(2);
            String maloai = cursor.getString(3);
            String tinhtrang = cursor.getString(5);
            String trangthai = cursor.getString(4);
            if (trangthai.equals("C")){
                kt =1;
            }
            ThietBi thietbi = new ThietBi(matb, tentb, ghichu, maloai,trangthai, tinhtrang);
            thietBiAdapter.add(thietbi);
        }
        cursor.close();
    }

    private void update() {
        ContentValues values = new ContentValues();
        values.put("MaPM",phieuMuon.getMaPM());
        values.put("Phong",phieuMuon.getPhong());
        values.put("GhiChu",phieuMuon.getGhiChu());
        values.put("MaNM",phieuMuon.getMaMN());
        values.put("MaNV",phieuMuon.getMaNV());
        values.put("ThoiGianMuon",phieuMuon.getThoiGianMuon());
        int kq = (int) LoginActivity.database.insert("PHIEUMUON", null, values);
        if (kq > 0 ){
            Toast.makeText(QuanLiMuonActivity.this, "Thêm thành công", Toast.LENGTH_LONG).show();
            dialogAdd.dismiss();
            getPhieuMuonFromDB(MaNV);
        } else {
            Toast.makeText(QuanLiMuonActivity.this, "Có lỗi xảy ra, vui lòng thử lại", Toast.LENGTH_LONG).show();
        }
    }
}
