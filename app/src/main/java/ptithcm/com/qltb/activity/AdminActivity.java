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

public class AdminActivity extends AppCompatActivity {
    TextView txtAD;
    Button btnQLNV, btnQLTB_AD, btnQLMN_AD;
    ArrayAdapter<NhanVien> nvAdapter;
    ArrayAdapter<ChucVu> cvAdapter;
    TaiKhoan taiKhoan;
    NhanVien nhanVien;
    ChucVu chucVu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        addControls();
        addEvents();
    }
    private void addEvents() {
        btnQLNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, QuanLiMuonActivity.class);
                intent.putExtra("nhanvien",nhanVien);
                startActivity(intent);
            }
        });
        btnQLTB_AD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, QuanLiThietBiActivity.class);
                intent.putExtra("nhanvien",nhanVien);
                startActivity(intent);
            }
        });
        btnQLMN_AD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, QuanLiMuonActivity.class);
                intent.putExtra("nhanvien",nhanVien);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        Intent intent =  getIntent();
        taiKhoan = (TaiKhoan) intent.getSerializableExtra("taikhoan");

        txtAD = (TextView) findViewById(R.id.txtAD);
        btnQLNV = (Button) findViewById(R.id.btnQLNV);
        btnQLTB_AD = (Button) findViewById(R.id.btnQLTB_ad);
        btnQLMN_AD = (Button) findViewById(R.id.btnQLNM_ad);
        String u = taiKhoan.getUsername();
        nvAdapter = new ArrayAdapter<NhanVien>(AdminActivity.this, android.R.layout.simple_list_item_1);
        getNhanVienFromDB(u);
        nhanVien = nvAdapter.getItem(0);
        cvAdapter = new ArrayAdapter<ChucVu>(AdminActivity.this, android.R.layout.simple_list_item_1);
        getChucVuFromDB(nhanVien.getMaCV());
        chucVu = cvAdapter.getItem(0);
        txtAD.setText(chucVu.getTenCV()+" "+nhanVien.getHo()+" "+nhanVien.getTen());

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
