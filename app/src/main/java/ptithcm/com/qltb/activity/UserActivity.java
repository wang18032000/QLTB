package ptithcm.com.qltb.activity;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import ptithcm.com.qltb.R;
import ptithcm.com.qltb.model.NhanVien;
import ptithcm.com.qltb.model.TaiKhoan;
import ptithcm.com.qltb.model.ThietBi;

public class UserActivity extends AppCompatActivity {

    TextView txtNV;
    Button btnMuon, btnQLTB, btnQLNM, btnTK;
    ArrayAdapter<NhanVien> nvAdapter;
    TaiKhoan taiKhoan;
    NhanVien nhanVien;

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
        txtNV.setText(nhanVien.getHoTen());

    }

    private void getNhanVienFromDB(String u) {
        LoginActivity.database = openOrCreateDatabase(LoginActivity.DATABASE_NAME,MODE_PRIVATE, null);
        Cursor cursor = LoginActivity.database.query("NHANVIEN",null,"UserName=?",new String[]{u},null,null,null);
        nvAdapter.clear();
        while (cursor.moveToNext()){
            String maNV = cursor.getString(0);
            String hoten = cursor.getString(1);
            String gioiTinh = cursor.getString(2);
            String sdt = cursor.getString(3);
            String cmnd = cursor.getString(4);
            String username = cursor.getString(5);
            NhanVien nv = new NhanVien(maNV, hoten, gioiTinh, cmnd, sdt, username);
            nvAdapter.add(nv);
        }
        cursor.close();
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.mnulogout:
//                getApplicationContext().getSharedPreferences("lastUser", 0).edit().clear().commit();
//                UserActivity.this.finish();
//                Intent intent = new Intent(UserActivity.this, LoginActivity.class);
//                startActivity(intent);
//                break;
//
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
