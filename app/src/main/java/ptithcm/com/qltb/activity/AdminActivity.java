package ptithcm.com.qltb.activity;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class AdminActivity extends AppCompatActivity {
    TextView txtAD, txtMaAD;
    Button btnQLNV, btnQLTB_AD, btnQLMN_AD;
    ArrayAdapter<NhanVien> nvAdapter;
    TaiKhoan taiKhoan;
    NhanVien nhanVien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        addControls();
        addEvents();
    }
    private void addEvents() {
    }

    private void addControls() {
        Intent intent =  getIntent();
        taiKhoan = (TaiKhoan) intent.getSerializableExtra("taikhoan");

        txtAD = (TextView) findViewById(R.id.txtAD);
        txtMaAD = (TextView) findViewById(R.id.txtMaAD);
        String u = taiKhoan.getUsername();
        nvAdapter = new ArrayAdapter<NhanVien>(AdminActivity.this, android.R.layout.simple_list_item_1);
        getNhanVienFromDB(u);
        nhanVien = nvAdapter.getItem(0);
        txtAD.setText(nhanVien.getHoTen());
        txtMaAD.setText(nhanVien.getUsername());

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_admin, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnuQLNV:
                Intent intent = new Intent(AdminActivity.this, QuanLiNVActivity.class);
                intent.putExtra("nhanvien",nhanVien);
                startActivity(intent);
                break;
            case R.id.mnuQLTB_ad:
                intent = new Intent(AdminActivity.this, QuanLiThietBiActivity.class);
                intent.putExtra("nhanvien",nhanVien);
                startActivity(intent);
                break;
            case R.id.mnuQLNM_ad:
                intent = new Intent(AdminActivity.this, QuanLiNguoiMuonActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
