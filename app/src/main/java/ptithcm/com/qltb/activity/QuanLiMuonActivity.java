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
import ptithcm.com.qltb.model.NguoiMuon;
import ptithcm.com.qltb.model.NhanVien;
import ptithcm.com.qltb.model.PhieuMuon;
import ptithcm.com.qltb.model.ThietBi;

public class QuanLiMuonActivity extends AppCompatActivity {

    ListView lvPhieuMuon;
    ImageView imgThemPM;
    ArrayAdapter<PhieuMuon> phieuMuonAdapter;
    ArrayAdapter<CT_PhieuMuon> cTPMAdapter;
    ArrayAdapter<ThietBi> thietBiAdapter;
    ArrayAdapter<NguoiMuon> nguoiMuonAdapter;
    NhanVien nhanVien;
    PhieuMuon phieuMuon;
    CT_PhieuMuon ct;
    String MaNV,p,maNM;
    Dialog dialogAdd;
    int kt;
    EditText edtMaNM,edtPhong,edtGC;
    Button btnThemPM;

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

        edtMaNM = dialogAdd.findViewById(R.id.edtMaNM_PM_ADD);
        edtPhong = dialogAdd.findViewById(R.id.edtPhong_PM_ADD);
        edtGC = dialogAdd.findViewById(R.id.edtGC_PM_ADD);
        btnThemPM = dialogAdd.findViewById(R.id.btnThemPM);

        btnThemPM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maNM = edtMaNM.getText().toString();
                p = edtPhong.getText().toString();
                if (kiemtradulieu()){
                    themCTPhieuMuon();
                }

            }
        });
        dialogAdd.show();
    }

    private void themCTPhieuMuon() {
        Random rand = new Random();
        int ranNum = rand.nextInt(100)+1;

        String maPM = "PM"+String.valueOf(ranNum);
        String gc = edtGC.getText().toString();
        kiemTraMuon(maNM);
        if (kt == 0){
            String maNV = nhanVien.getMaNV();
            long millis=System.currentTimeMillis();
            java.sql.Date date = new java.sql.Date(millis);
            String ngay = String.valueOf(date);
            phieuMuon = new PhieuMuon(maPM,p,gc,maNM,maNV,ngay);
            update();
        }
    }

    private boolean kiemtradulieu() {
        if(!maNM.equals("") && !p.equals("")){
            return true;
        }else {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void kiemTraMuon(String ma) {
        kt =0;
        phieuMuonAdapter = new ArrayAdapter<PhieuMuon>(QuanLiMuonActivity.this, android.R.layout.simple_list_item_1);
        getPMTheoMaNM(ma);
        for (int i = 0;i<phieuMuonAdapter.getCount();i++){
            phieuMuon = phieuMuonAdapter.getItem(i);
            cTPMAdapter = new ArrayAdapter<CT_PhieuMuon>(QuanLiMuonActivity.this, android.R.layout.simple_list_item_1);
            getCTFromDB(phieuMuon.getMaPM());
        }

        nguoiMuonAdapter = new ArrayAdapter<NguoiMuon>(QuanLiMuonActivity.this, android.R.layout.simple_list_item_1);
        getNguoiMuonFromDB(maNM);
        if (nguoiMuonAdapter.getCount()==0){
            kt = 1;
            Toast.makeText(QuanLiMuonActivity.this, "Người mượn không tồn tại", Toast.LENGTH_LONG).show();
        }
    }

    private void getNguoiMuonFromDB(String ma) {
        LoginActivity.database = openOrCreateDatabase(LoginActivity.DATABASE_NAME,MODE_PRIVATE, null);
        Cursor cursor = LoginActivity.database.query("NGUOIMUON",null,"MaNM=?",new String[]{ma},null,null,null);
        nguoiMuonAdapter.clear();
        while (cursor.moveToNext()){
            String maNM = cursor.getString(0);
            String ho = cursor.getString(1);
            String ten = cursor.getString(2);
            String gioiTinh = cursor.getString(3);
            String ngaysinh = cursor.getString(4);
            String diachi = cursor.getString(5);
            String cmnd = cursor.getString(6);
            String ghichu = cursor.getString(7);
            String loai = cursor.getString(8);
            NguoiMuon nm = new NguoiMuon(maNM, ho, ten, gioiTinh, ngaysinh, diachi, cmnd, ghichu, loai);
            nguoiMuonAdapter.add(nm);
        }
        cursor.close();
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
                Toast.makeText(QuanLiMuonActivity.this, "Người Mượn Chưa Trả", Toast.LENGTH_LONG).show();
            }
            CT_PhieuMuon ct = new CT_PhieuMuon(maPM, maTB, thoigiantra);
            cTPMAdapter.add(ct);
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
