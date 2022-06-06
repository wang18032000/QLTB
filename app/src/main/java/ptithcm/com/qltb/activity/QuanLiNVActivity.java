package ptithcm.com.qltb.activity;

import android.app.Dialog;
import android.content.ContentValues;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import ptithcm.com.qltb.R;
import ptithcm.com.qltb.model.NhanVien;
import ptithcm.com.qltb.model.PhanQuyen;

public class QuanLiNVActivity extends AppCompatActivity {
    ArrayAdapter<NhanVien> nvAdaptor;
    NhanVien nhanVien;
    ImageView imgThemNV;
    ListView lvNhanVien;
    TextView txtMa;
    EditText edtHoTen, edtGioiTinh, edtSDT, edtCMND, edtUser, edtPass;
    Spinner spPhanQuyen;
    Dialog dialogNV, dialogThemNV;
    Button btnCapNhat, btnThem;
    ArrayAdapter<PhanQuyen> pqAdapter;
    PhanQuyen phanQuyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_li_nv);
        addControls();
        addEvents();
    }

    private void addEvents() {
        imgThemNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogThemNV();
            }
        });
        lvNhanVien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                nhanVien = nvAdaptor.getItem(i);
                showDialogNhanVien();
            }
        });
    }

    private void showDialogNhanVien() {
        dialogNV = new Dialog(QuanLiNVActivity.this);
        dialogNV.setContentView(R.layout.dialog_ct_nhan_vien);
        txtMa = (TextView) dialogNV.findViewById(R.id.txtMaNV_detail);
        edtHoTen = (EditText) dialogNV.findViewById(R.id.edtHoNV_detail);
        edtGioiTinh = (EditText) dialogNV.findViewById(R.id.edtGTNV_detail);
        edtSDT = (EditText) dialogNV.findViewById(R.id.edtSdt_detail);
        edtCMND = (EditText) dialogNV.findViewById(R.id.edtCMNDNV_detail);
        btnCapNhat = dialogNV.findViewById(R.id.btnCapNhatNV);

        txtMa.setText(nhanVien.getMaNV());
        edtHoTen.setText(nhanVien.getHoTen());
        edtGioiTinh.setText(nhanVien.getGioiTinh());
        edtSDT.setText(nhanVien.getSdt());
        edtCMND.setText(nhanVien.getCmnd());

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (kiemtradulieuCapNhat()){
                    update();
                }
            }
        });
        dialogNV.show();

    }

    private boolean kiemtradulieuCapNhat() {
        String ho = edtHoTen.getText().toString();
        String gt = edtGioiTinh.getText().toString();
        String cmnd = edtCMND.getText().toString();
        String sdt = edtSDT.getText().toString();
        if (!ho.equals("") && !gt.equals("") && !sdt.equals("") && !cmnd.equals("")){
            if (!gt.equals("Nam") && !gt.equals("Nu")){
                Toast.makeText(QuanLiNVActivity.this, "Giới tính không xác định", Toast.LENGTH_LONG).show();
                return false;
            }
            return true;
        }else {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void update() {
        ContentValues values = new ContentValues();
        values.put("MaNV", nhanVien.getMaNV());
        values.put("HoTen", edtHoTen.getText().toString());
        values.put("GioiTinh", edtGioiTinh.getText().toString());
        values.put("SDT", edtSDT.getText().toString());
        values.put("CMND", edtCMND.getText().toString());
        values.put("UserName",nhanVien.getUsername());
        int kq = (int) LoginActivity.database.update("NHANVIEN",values,"MaNV = ?",new String[]{nhanVien.getMaNV()});
        if (kq >0){
            getNhanVienFromDB();
            dialogNV.dismiss();
            Toast.makeText(QuanLiNVActivity.this, "Đã cập nhật", Toast.LENGTH_LONG).show();
        }else
            Toast.makeText(QuanLiNVActivity.this, "Có lỗi xảy ra, vui lòng thử lại", Toast.LENGTH_LONG).show();
    }

    private void showDialogThemNV() {
        dialogThemNV = new Dialog(QuanLiNVActivity.this);
        dialogThemNV.setContentView(R.layout.dialog_them_nhan_vien);

        edtHoTen = (EditText) dialogThemNV.findViewById(R.id.edtHoNV_add);
        edtGioiTinh = (EditText) dialogThemNV.findViewById(R.id.edtGTNV_add);
        edtCMND = (EditText) dialogThemNV.findViewById(R.id.edtCMNDNV_add);
        edtSDT = (EditText) dialogThemNV.findViewById(R.id.edtSdt_add);
        edtUser = (EditText) dialogThemNV.findViewById(R.id.edtUser_add);
        edtPass = (EditText) dialogThemNV.findViewById(R.id.edtPass_add);
        btnThem = (Button) dialogThemNV.findViewById(R.id.btnThemNV);

        spPhanQuyen = (Spinner) dialogThemNV.findViewById(R.id.spQuyen_add);
        pqAdapter = new ArrayAdapter<PhanQuyen>(QuanLiNVActivity.this, android.R.layout.simple_list_item_1);
        pqAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPhanQuyen.setAdapter(pqAdapter);

        getPhanQuyenFromDB();

        spPhanQuyen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                phanQuyen = new PhanQuyen();
                phanQuyen = pqAdapter.getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                phanQuyen = new PhanQuyen();
                phanQuyen = pqAdapter.getItem(0);
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (kiemtradulieuThem()) {
                    addUser();
                    add();
                }
            }
        });
        dialogThemNV.show();
    }

    private boolean kiemtradulieuThem() {
        String ho = edtHoTen.getText().toString();
        String gt = edtGioiTinh.getText().toString();
        String cmnd = edtCMND.getText().toString();
        String sdt = edtSDT.getText().toString();
        String u = edtUser.getText().toString();
        String p = edtPass.getText().toString();
        if (!ho.equals("")  && !gt.equals("") && !sdt.equals("") && !u.equals("") && !cmnd.equals("") && !p.equals("") ){
            if (!gt.equals("Nam") && !gt.equals("Nu")){
                Toast.makeText(QuanLiNVActivity.this, "Giới tính không xác định", Toast.LENGTH_LONG).show();
                return false;
            }
            return true;
        }else {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void addUser() {
        ContentValues values = new ContentValues();
        values.put("UserName",edtUser.getText().toString());
        values.put("Password",edtPass.getText().toString());
        values.put("MaPQ",phanQuyen.getMaPQ().toString());
        int kq = (int) LoginActivity.database.insert("TAIKHOAN", null, values);
        if (kq >0){
            Toast.makeText(QuanLiNVActivity.this, "Đã thêm tài khoản", Toast.LENGTH_LONG).show();
        }else
            Toast.makeText(QuanLiNVActivity.this, "Có lỗi xảy ra, vui lòng thử lại", Toast.LENGTH_LONG).show();

    }

    private void add() {
        ContentValues values = new ContentValues();
        values.put("MaNV", getMaNV());
        values.put("HoTen", edtHoTen.getText().toString());
        values.put("GioiTinh", edtGioiTinh.getText().toString());
        values.put("SDT", edtSDT.getText().toString());
        values.put("CMND", edtCMND.getText().toString());
        values.put("UserName",edtUser.getText().toString());
        int kq = (int) LoginActivity.database.insert("NHANVIEN",null, values);
        if (kq >0) {
            getNhanVienFromDB();
            dialogThemNV.dismiss();
            Toast.makeText(QuanLiNVActivity.this, "Đã thêm nhân viên", Toast.LENGTH_LONG).show();
        }else
            Toast.makeText(QuanLiNVActivity.this, "Có lỗi xảy ra, vui lòng thử lại", Toast.LENGTH_LONG).show();
    }

    private String getMaNV() {
        Random rand = new Random();
        int ranNum = rand.nextInt(100)+1;
        return "NV"+String.valueOf(ranNum);
    }

    private void getPhanQuyenFromDB() {
        LoginActivity.database = openOrCreateDatabase(LoginActivity.DATABASE_NAME,MODE_PRIVATE, null);
        Cursor cursor = LoginActivity.database.rawQuery("SELECT * FROM PHANQUYEN",null);
        pqAdapter.clear();
        while (cursor.moveToNext()){
            String ma = cursor.getString(0);
            String ten = cursor.getString(1);
            PhanQuyen pq = new PhanQuyen(ma,ten);
            pqAdapter.add(pq);
        }
        cursor.close();
    }

    private void addControls() {
        imgThemNV = (ImageView) findViewById(R.id.imgThemNV);
        lvNhanVien =(ListView) findViewById(R.id.lvNhanVien);
        nvAdaptor = new ArrayAdapter<NhanVien>(QuanLiNVActivity.this, android.R.layout.simple_list_item_1);
        lvNhanVien.setAdapter(nvAdaptor);
        getNhanVienFromDB();
    }

    private void getNhanVienFromDB() {
        LoginActivity.database = openOrCreateDatabase(LoginActivity.DATABASE_NAME,MODE_PRIVATE, null);
        Cursor cursor = LoginActivity.database.rawQuery("SELECT * FROM NHANVIEN",null);
        nvAdaptor.clear();
        while (cursor.moveToNext()){
            String maNV = cursor.getString(0);
            String hoten = cursor.getString(1);
            String gioiTinh = cursor.getString(2);
            String sdt = cursor.getString(3);
            String cmnd = cursor.getString(4);
            String username = cursor.getString(5);
            NhanVien nv = new NhanVien(maNV, hoten, gioiTinh, sdt, cmnd, username);
            nvAdaptor.add(nv);
        }
        cursor.close();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_option_menu, menu);

        MenuItem mnuSearch = menu.findItem(R.id.mnuSearch);
        SearchView searchView = (SearchView) mnuSearch.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                nvAdaptor.getFilter().filter(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
