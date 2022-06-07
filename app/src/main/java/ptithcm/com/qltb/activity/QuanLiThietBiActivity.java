package ptithcm.com.qltb.activity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import ptithcm.com.qltb.R;
import ptithcm.com.qltb.model.LoaiThietBi;
import ptithcm.com.qltb.model.NhanVien;
import ptithcm.com.qltb.model.PhieuMuon;
import ptithcm.com.qltb.model.PhieuNhap;
import ptithcm.com.qltb.model.PhieuThanhLy;
import ptithcm.com.qltb.model.Phong;
import ptithcm.com.qltb.model.ThietBi;
import ptithcm.com.qltb.model.TrangThai;

public class QuanLiThietBiActivity extends AppCompatActivity {

    NhanVien nhanVien;
    PhieuNhap phieuNhap;
    PhieuThanhLy phieuThanhLy;
    String MaNV,MaPN,MaPTL;
    Button btnNhap, btnThanhLy, btnCapNhat;
    ListView lvThietBi;
    ArrayAdapter<ThietBi> thietBiAdapter;
    ArrayAdapter<TrangThai> trangThaiAdapter;
    ArrayAdapter<LoaiThietBi> loaiAdapter;
    ArrayAdapter<Phong> phongAdapter;
    LoaiThietBi loai;
    Phong phong;
    TrangThai trangThai;
    ThietBi thietBi;
    Dialog dialogCTTB;
    EditText edtTen;
    Spinner spLoai, spPhong, spTrangThai;
    TextView txtMa;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_li_thiet_bi);
        addControlS();
        addEvents();
    }

    private void addEvents() {
        lvThietBi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                thietBi = thietBiAdapter.getItem(i);
                showChiTietTB();
            }
        });
    }

    private void createPhieuNhap(String ma) {
        phieuNhap = new PhieuNhap(getMaP(),getToday(),"",ma);
        addPhieuNhap();
    }

    private void addPhieuNhap(){
        ContentValues values = new ContentValues();
        values.put("MaPN",phieuNhap.getMaPN());
        values.put("ThoiGianN",phieuNhap.getThoiGianNhap());
        values.put("GhiChu",phieuNhap.getGhiChu());
        values.put("MaNV",phieuNhap.getMaNV());

        int kq = (int) LoginActivity.database.insert("PHIEUNHAP", null, values);
        if (kq > 0){
            Toast.makeText(QuanLiThietBiActivity.this, "Đã tạo phiếu nhập", Toast.LENGTH_LONG).show();
        }else
            Toast.makeText(QuanLiThietBiActivity.this, "Có lỗi xảy ra, vui lòng thử lại", Toast.LENGTH_LONG).show();
    }

    private void createPhieuThanhLy(String ma) {
        phieuThanhLy =  new PhieuThanhLy(getMaP(),getToday(),"",ma);
        addPhieuThanhLy();
    }

    private void addPhieuThanhLy(){
        ContentValues values = new ContentValues();
        values.put("MaPTL",phieuThanhLy.getMaPTL());
        values.put("ThoiGianTL",phieuThanhLy.getThoiGianTL());
        values.put("GhiChu",phieuThanhLy.getGhiChu());
        values.put("MaNV",phieuThanhLy.getMaNV());

        int kq = (int) LoginActivity.database.insert("PHIEUTHANHLY", null, values);
        if (kq > 0){
            Toast.makeText(QuanLiThietBiActivity.this, "Đã tạo phiếu thanh lý", Toast.LENGTH_LONG).show();
        }else
            Toast.makeText(QuanLiThietBiActivity.this, "Có lỗi xảy ra, vui lòng thử lại", Toast.LENGTH_LONG).show();
    }

    private String getMaP() {
        Random rand = new Random();
        int ranNum = rand.nextInt(100)+1;
        return "P"+String.valueOf(ranNum);
    }

    private String getToday(){
        long millis=System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        return String.valueOf(date);
    }

    private void showChiTietTB() {
        dialogCTTB = new Dialog(QuanLiThietBiActivity.this);
        dialogCTTB.setContentView(R.layout.dialog_cttb);

        txtMa = (TextView) dialogCTTB.findViewById(R.id.txtMaTB_detail);
        edtTen = (EditText) dialogCTTB.findViewById(R.id.edtTenTB_detail);
        spTrangThai = (Spinner) dialogCTTB.findViewById(R.id.spTrangThai_edit);
        spLoai = (Spinner) dialogCTTB.findViewById(R.id.spLoai_edit);
        spPhong = (Spinner) dialogCTTB.findViewById(R.id.spPhong_edit);
        btnCapNhat =(Button) dialogCTTB.findViewById(R.id.btnCapNhat);

        txtMa.setText(thietBi.getMaTB());
        edtTen.setText(thietBi.getTenTB());
        trangThaiAdapter = new ArrayAdapter<TrangThai>(QuanLiThietBiActivity.this, android.R.layout.simple_list_item_1);
        spTrangThai.setAdapter(trangThaiAdapter);
        getTrangThai();
        loaiAdapter = new ArrayAdapter<LoaiThietBi>(QuanLiThietBiActivity.this, android.R.layout.simple_list_item_1);
        spLoai.setAdapter(loaiAdapter);
        getLoai();
        phongAdapter = new ArrayAdapter<Phong>(QuanLiThietBiActivity.this, android.R.layout.simple_list_item_1);
        spPhong.setAdapter(phongAdapter);
        getPhong();
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (kiemtradulieu()){
                    update();
                }
            }
        });
        spTrangThai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                thietBi.setMaTT(trangThaiAdapter.getItem(i).getMaTT());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                thietBi.setMaLoai(loaiAdapter.getItem(i).getMaLoai());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spPhong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                thietBi.setMaP(phongAdapter.getItem(i).getMaP());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        dialogCTTB.show();
    }

    private void getPhong() {
        LoginActivity.database = openOrCreateDatabase(LoginActivity.DATABASE_NAME, MODE_PRIVATE, null);
        Cursor cursor = LoginActivity.database.rawQuery("SELECT * FROM PHONG", null);
        phongAdapter.clear();
        while (cursor.moveToNext()) {
            String ma = cursor.getString(0);
            String loai = cursor.getString(1);
            if (ma.equals(thietBi.getMaP())){
                Phong p = new Phong(ma, loai);
                phongAdapter.insert(p,0);
            } else {
                Phong p = new Phong(ma, loai);
                phongAdapter.add(p);
            }
        }
        cursor.close();
    }

    private void getTrangThai() {
        LoginActivity.database = openOrCreateDatabase(LoginActivity.DATABASE_NAME, MODE_PRIVATE, null);
        Cursor cursor = LoginActivity.database.rawQuery("SELECT * FROM TRANGTHAI", null);
        trangThaiAdapter.clear();
        while (cursor.moveToNext()) {
            String ma = cursor.getString(0);
            String ten = cursor.getString(1);
//            if (thietBi.getMaTT().equals("DTL")&&(ma.equals("DTL"))) {
//                TrangThai tt = new TrangThai(ma, ten);
//                trangThaiAdapter.add(tt);
//                break;
//            };
//            if (ma.equals("DM")) {
//                TrangThai tt = new TrangThai(ma, ten);
//                trangThaiAdapter.add(tt);
//                break;
//            }else
            if (ma.equals(thietBi.getMaTT())){
                TrangThai tt = new TrangThai(ma, ten);
                trangThaiAdapter.insert(tt,0);
            } else {
                TrangThai tt = new TrangThai(ma, ten);
                trangThaiAdapter.add(tt);
            }
        }
        cursor.close();
    }

    private void getLoai() {
        LoginActivity.database = openOrCreateDatabase(LoginActivity.DATABASE_NAME, MODE_PRIVATE, null);
        Cursor cursor = LoginActivity.database.rawQuery("SELECT * FROM LOAI", null);
        loaiAdapter.clear();
        while (cursor.moveToNext()) {
            String ma = cursor.getString(0);
            String ten = cursor.getString(1);
            if (ma.equals(thietBi.getMaLoai())){
                LoaiThietBi loai = new LoaiThietBi(ma, ten);
                loaiAdapter.insert(loai,0);
            } else {
                LoaiThietBi loai = new LoaiThietBi(ma, ten);
                loaiAdapter.add(loai);
            }
        }
        cursor.close();
    }

    private boolean kiemtradulieu() {
        String ten = edtTen.getText().toString();


        if(!ten.equals("")){
            return true;
        }else {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void update() {
        ContentValues values = new ContentValues();
        values.put("MaTB",thietBi.getMaTB());
        values.put("TenTB",edtTen.getText().toString());
        values.put("MaTT",thietBi.getMaTT());
        values.put("MaLoai",thietBi.getMaLoai());
        values.put("MaP",thietBi.getMaP());

        int kq = LoginActivity.database.update("THIETBI", values, "MaTB = ?", new String[]{thietBi.getMaTB()});
        if (kq != 0){
            getThietBiFromDB();
            dialogCTTB.dismiss();
        }else {
            Toast.makeText(QuanLiThietBiActivity.this, "Có lỗi xảy ra, vui lòng thử lại", Toast.LENGTH_LONG).show();
        }
    }

    private void addControlS() {
        Intent intent = getIntent();
        nhanVien = (NhanVien) intent.getSerializableExtra("nhanvien");
        MaNV = nhanVien.getMaNV();

        lvThietBi = (ListView) findViewById(R.id.lvThietBi);
        thietBiAdapter = new ArrayAdapter<ThietBi>(QuanLiThietBiActivity.this, android.R.layout.simple_list_item_1);
        lvThietBi.setAdapter(thietBiAdapter);
        
        getThietBiFromDB();
    }

    private void getThietBiFromDB() {
        LoginActivity.database = openOrCreateDatabase(LoginActivity.DATABASE_NAME, MODE_PRIVATE, null);
        Cursor cursor = LoginActivity.database.rawQuery("SELECT * FROM THIETBI", null);
        thietBiAdapter.clear();
        while (cursor.moveToNext()) {
            String matb = cursor.getString(0);
            String tentb = cursor.getString(1);
            String matt = cursor.getString(2);
            String maloai = cursor.getString(3);
            String map = cursor.getString(4);
            ThietBi tb = new ThietBi(matb, tentb, matt, maloai, map);
            thietBiAdapter.add(tb);
        }
        cursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_thietbi, menu);

        MenuItem mnuSearch = menu.findItem(R.id.mnuSearch);
        SearchView searchView = (SearchView) mnuSearch.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                thietBiAdapter.getFilter().filter(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnuNhap:
                createPhieuNhap(MaNV);
                Intent intent = new Intent(QuanLiThietBiActivity.this, NhapActivity.class);
                intent.putExtra("phieuNhap",phieuNhap);
                startActivity(intent);
                break;
            case R.id.mnuTL:
                createPhieuThanhLy(MaNV);
                intent = new Intent(QuanLiThietBiActivity.this, ThanhLyActivity.class);
                intent.putExtra("phieuThanhLy",phieuThanhLy);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
