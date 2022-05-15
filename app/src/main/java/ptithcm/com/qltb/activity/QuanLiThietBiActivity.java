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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import ptithcm.com.qltb.R;
import ptithcm.com.qltb.model.NhanVien;
import ptithcm.com.qltb.model.PhieuMuon;
import ptithcm.com.qltb.model.PhieuNhap;
import ptithcm.com.qltb.model.PhieuThanhLy;
import ptithcm.com.qltb.model.ThietBi;

public class QuanLiThietBiActivity extends AppCompatActivity {

    NhanVien nhanVien;
    PhieuNhap phieuNhap;
    PhieuThanhLy phieuThanhLy;
    String MaNV,MaPN,MaPTL;
    Button btnNhap, btnThanhLy, btnCapNhat;
    ListView lvThietBi;
    ArrayAdapter<ThietBi> thietBiAdapter;
    ThietBi thietBi;
    Dialog dialogCTTB;
    EditText edtTen, edtGhiChu,edtTrangThai,edtTinhTrang;
    TextView txtMa;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_li_thiet_bi);
        addControlS();
        addEvents();
    }

    private void addEvents() {
        btnNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPhieuNhap(MaNV);
                Intent intent = new Intent(QuanLiThietBiActivity.this, NhapActivity.class);
                intent.putExtra("phieuNhap",phieuNhap);
                startActivity(intent);
            }
        });

        btnThanhLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPhieuThanhLy(MaNV);
                Intent intent = new Intent(QuanLiThietBiActivity.this, ThanhLyActivity.class);
                intent.putExtra("phieuThanhLy",phieuThanhLy);
                startActivity(intent);
            }
        });
        lvThietBi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                thietBi = thietBiAdapter.getItem(i);
                showChiTietTB();
            }
        });
    }

    private void createPhieuNhap(String ma) {
        phieuNhap = new PhieuNhap();
        phieuNhap.setMaPN(getMaP());
        phieuNhap.setThoiGianNhap(getToday());
        phieuNhap.setGhiChu("");
        phieuNhap.setMaNV(ma);
        addPhieuNhap();
    }

    private void addPhieuNhap(){
        ContentValues values = new ContentValues();
        values.put("MaPN",phieuNhap.getMaPN());
        values.put("ThoiGianNhap",phieuNhap.getThoiGianNhap());
        values.put("GhiChu",phieuNhap.getGhiChu());
        values.put("MaNV",phieuNhap.getMaNV());
        int kq = (int) LoginActivity.database.insert("PHIEUNHAP", null, values);
        if (kq > 0){
            Toast.makeText(QuanLiThietBiActivity.this, "Đã tạo phiếu nhập", Toast.LENGTH_LONG).show();
        }else Toast.makeText(QuanLiThietBiActivity.this, "Có lỗi xảy ra, vui lòng thử lại", Toast.LENGTH_LONG).show();
    }

    private void createPhieuThanhLy(String ma) {
        phieuThanhLy =  new PhieuThanhLy();
        phieuThanhLy.setMaPTL(getMaP());
        phieuThanhLy.setThoiGianTL(getToday());
        phieuThanhLy.setMaNV(ma);
        addPhieuThanhLy();
    }

    private void addPhieuThanhLy(){
        ContentValues values = new ContentValues();
        values.put("MaPTL",phieuThanhLy.getMaPTL());
        values.put("ThoiGian",phieuThanhLy.getThoiGianTL());
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
        edtGhiChu = (EditText) dialogCTTB.findViewById(R.id.edtGhiChu_detail);
        edtTrangThai = (EditText) dialogCTTB.findViewById(R.id.edtTrangThai_detail);
        edtTinhTrang = (EditText) dialogCTTB.findViewById(R.id.edtTinhTrang_detail);
        btnCapNhat =(Button) dialogCTTB.findViewById(R.id.btnCapNhat);

        txtMa.setText(thietBi.getMaTB());
        edtTen.setText(thietBi.getTenTB());
        edtGhiChu.setText(thietBi.getGhiChu());
        edtTrangThai.setText(thietBi.getTrangThai());
        edtTinhTrang.setText(thietBi.getTinhTrang());

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (kiemtradulieu()){
                    update();
                }
            }
        });
        dialogCTTB.show();
    }

    private boolean kiemtradulieu() {
        String ten = edtTen.getText().toString();
        String trT =edtTrangThai.getText().toString();
        String tTr =edtTinhTrang.getText().toString();

        if(!ten.equals("") && !trT.equals("") && !tTr.equals("")){
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
        values.put("Ghichu",edtGhiChu.getText().toString());
        values.put("MaLoai",thietBi.getMaLoai());
        values.put("TrangThai",edtTrangThai.getText().toString());
        values.put("TinhTrang",edtTinhTrang.getText().toString());

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
        
        btnNhap = (Button) findViewById(R.id.btnNhap);
        btnThanhLy = (Button) findViewById(R.id.btnThanhLy);
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
            String ghichu = cursor.getString(2);
            String maloai = cursor.getString(3);
            String tinhtrang = cursor.getString(5);
            String trangthai = cursor.getString(4);
            ThietBi tb = new ThietBi(matb, tentb, ghichu, maloai,trangthai, tinhtrang);
            thietBiAdapter.add(tb);
        }
        cursor.close();
    }
}
