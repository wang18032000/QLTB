package ptithcm.com.qltb.activity;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import ptithcm.com.qltb.R;
import ptithcm.com.qltb.model.ChucVu;
import ptithcm.com.qltb.model.NhanVien;
import ptithcm.com.qltb.model.TaiKhoan;
import ptithcm.com.qltb.model.ThietBi;

public class UserActivity extends AppCompatActivity {

    TextView txtNV;
    Button btnMuon, btnQLTB, btnQLNM, btnTK;
    ArrayAdapter<NhanVien> nvAdapter;
    ArrayAdapter<ChucVu> cvAdapter;
    TaiKhoan taiKhoan;
    NhanVien nhanVien;
    ChucVu chucVu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnMuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, QuanLiMuonActivity.class);
                intent.putExtra("nhanvien",nhanVien);
                startActivity(intent);
            }
        });
        btnQLTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, QuanLiThietBiActivity.class);
                intent.putExtra("nhanvien",nhanVien);
                startActivity(intent);
            }
        });
        btnQLNM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, QuanLiNguoiMuonActivity.class);
                startActivity(intent);
            }
        });
        btnTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, ThongKeActivity.class);
                intent.putExtra("nhanvien",nhanVien);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        Intent intent =  getIntent();
        taiKhoan = (TaiKhoan) intent.getSerializableExtra("taikhoan");

        txtNV = (TextView) findViewById(R.id.txtNV);
        btnMuon = (Button) findViewById(R.id.btnMuon);
        btnQLTB = (Button) findViewById(R.id.btnQLTB);
        btnQLNM = (Button) findViewById(R.id.btnQLNM);
        btnTK = (Button) findViewById(R.id.btnTK);
        String u = taiKhoan.getUsername();
        nvAdapter = new ArrayAdapter<NhanVien>(UserActivity.this, android.R.layout.simple_list_item_1);
        getNhanVienFromDB(u);
        nhanVien = nvAdapter.getItem(0);
        cvAdapter = new ArrayAdapter<ChucVu>(UserActivity.this, android.R.layout.simple_list_item_1);
        getChucVuFromDB(nhanVien.getMaCV());
        chucVu = cvAdapter.getItem(0);
        txtNV.setText(chucVu.getTenCV()+" "+nhanVien.getHo()+" "+nhanVien.getTen());

    }

    private void getChucVuFromDB(String ma) {
        LoginActivity.database = openOrCreateDatabase(LoginActivity.DATABASE_NAME,MODE_PRIVATE, null);
        Cursor cursor = LoginActivity.database.query("CHUCVU",null,"MaCV=?",new String[]{ma},null,null,null);
        cvAdapter.clear();
        while (cursor.moveToNext()){
            String maCV = cursor.getString(0);
            String tenCV = cursor.getString(1);
            ChucVu chucVu = new ChucVu(maCV,tenCV);
            cvAdapter.add(chucVu);
        }
        cursor.close();
    }

    private void getNhanVienFromDB(String u) {
        LoginActivity.database = openOrCreateDatabase(LoginActivity.DATABASE_NAME,MODE_PRIVATE, null);
        Cursor cursor = LoginActivity.database.query("NHANVIEN",null,"UserName=?",new String[]{u},null,null,null);
        nvAdapter.clear();
        while (cursor.moveToNext()){
            String maNV = cursor.getString(0);
            String ho = cursor.getString(1);
            String ten = cursor.getString(2);
            String gioiTinh = cursor.getString(3);
            String cmnd = cursor.getString(4);
            String sdt = cursor.getString(5);
            String username = cursor.getString(6);
            String macv = cursor.getString(7);
            NhanVien nv = new NhanVien(maNV, ho, ten, gioiTinh, cmnd, sdt, username, macv);
            nvAdapter.add(nv);
        }
        cursor.close();
    }

}
