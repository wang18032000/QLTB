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
import android.widget.TextView;
import android.widget.Toast;

import ptithcm.com.qltb.R;
import ptithcm.com.qltb.model.NguoiMuon;

public class QuanLiNguoiMuonActivity extends AppCompatActivity {

    ArrayAdapter<NguoiMuon> nguoiMuonAdapter;
    NguoiMuon nguoiMuon;
    ImageView imgThemNM;
    ListView lvNguoiMuon;
    TextView txtMa;
    EditText edtMa, edtHoTen, edtGioiTinh, edtSDT, edtCMND, edtLoai;
    Dialog dialogNM,dialogThemNM;
    Button btnCapNhat, btnThem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_li_nguoi_muon);
        addControls();
        addEvents();
    }

    private void addEvents() {
        imgThemNM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogThemNM();
            }
        });
        lvNguoiMuon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                nguoiMuon = nguoiMuonAdapter.getItem(i);
                showDialogNguoiMuon();
            }
        });
    }

    private void showDialogNguoiMuon() {
        dialogNM = new Dialog(QuanLiNguoiMuonActivity.this);
        dialogNM.setContentView(R.layout.dialog_ct_nguoi_muon);

        txtMa = (TextView) dialogNM.findViewById(R.id.txtMaNM_detail);
        edtHoTen = (EditText) dialogNM.findViewById(R.id.edtHoNM_detail);
        edtGioiTinh = (EditText) dialogNM.findViewById(R.id.edtGTNM_detail);
        edtSDT =(EditText) dialogNM.findViewById(R.id.edtDCNM_detail);
        edtCMND = (EditText) dialogNM.findViewById(R.id.edtCMNDNM_detail);
        edtLoai = (EditText) dialogNM.findViewById(R.id.edtLoaiNM_detail);
        btnCapNhat = dialogNM.findViewById(R.id.btnCapNhatNM);

        txtMa.setText(nguoiMuon.getMaNM());
        edtHoTen.setText(nguoiMuon.getHoTen());
        edtGioiTinh.setText(nguoiMuon.getGioiTinh());
        edtSDT.setText(nguoiMuon.getsDT());
        edtCMND.setText(nguoiMuon.getCmnd());
        edtLoai.setText(nguoiMuon.getLoai());

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (kiemtradulieu()){
                    update();
                }
            }
        });
        dialogNM.show();
    }

    private boolean kiemtradulieu() {
        String ho = edtHoTen.getText().toString();
        String gt = edtGioiTinh.getText().toString();
        String dc = edtSDT.getText().toString();
        String cmnd = edtCMND.getText().toString();
        String loai = edtLoai.getText().toString();
        if (!ho.equals("") && !gt.equals("") && !dc.equals("") && !cmnd.equals("") && !loai.equals("")){
            if (!gt.equals("Nam") && !gt.equals("Nu")){
                Toast.makeText(QuanLiNguoiMuonActivity.this, "Giới tính không xác định", Toast.LENGTH_LONG).show();
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
        values.put("MaNM", txtMa.getText().toString());
        values.put("HoTen", edtHoTen.getText().toString());
        values.put("GioiTinh", edtGioiTinh.getText().toString());
        values.put("SDT", edtSDT.getText().toString());
        values.put("CMND", edtCMND.getText().toString());
        values.put("Loai",edtLoai.getText().toString());
        int kq = (int) LoginActivity.database.update("NGUOIMUON",values,"MaNM = ?",new String[]{txtMa.getText().toString()});
        if (kq >0){
            getNguoiMuonFromDB();
            dialogNM.dismiss();
            Toast.makeText(QuanLiNguoiMuonActivity.this, "Đã cập nhật", Toast.LENGTH_LONG).show();
        }else
            Toast.makeText(QuanLiNguoiMuonActivity.this, "Có lỗi xảy ra, vui lòng thử lại", Toast.LENGTH_LONG).show();
    }

    private void showDialogThemNM() {
        dialogThemNM = new Dialog(QuanLiNguoiMuonActivity.this);
        dialogThemNM.setContentView(R.layout.dialog_them_nguoi_muon);

        edtMa = (EditText) dialogThemNM.findViewById(R.id.edtMaNM_add);
        edtHoTen = (EditText) dialogThemNM.findViewById(R.id.edtHoNM_add);
        edtGioiTinh = (EditText) dialogThemNM.findViewById(R.id.edtGTNM_add);
        edtSDT =(EditText) dialogThemNM.findViewById(R.id.edtDCNM_add);
        edtCMND = (EditText) dialogThemNM.findViewById(R.id.edtCMNDNM_add);
        edtLoai = (EditText) dialogThemNM.findViewById(R.id.edtLoaiNM_add);
        btnThem = dialogThemNM.findViewById(R.id.btnThêmNM);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (kiemtradulieu()){
                    add();
                }
            }
        });
        dialogThemNM.show();
    }

    private void add() {
        ContentValues values = new ContentValues();
        values.put("MaNM", edtMa.getText().toString());
        values.put("HoTen", edtHoTen.getText().toString());
        values.put("GioiTinh", edtGioiTinh.getText().toString());
        values.put("SDT", edtSDT.getText().toString());
        values.put("CMND", edtCMND.getText().toString());
        values.put("Loai",edtLoai.getText().toString());
        int kq = (int) LoginActivity.database.insert("NGUOIMUON",null, values);
        if (kq >0) {
            getNguoiMuonFromDB();
            dialogThemNM.dismiss();
            Toast.makeText(QuanLiNguoiMuonActivity.this, "Đã thêm người mượn", Toast.LENGTH_LONG).show();
        }else
            Toast.makeText(QuanLiNguoiMuonActivity.this, "Có lỗi xảy ra, vui lòng thử lại", Toast.LENGTH_LONG).show();
    }

    private void addControls() {
        imgThemNM = (ImageView) findViewById(R.id.imgThemNM);
        lvNguoiMuon =(ListView) findViewById(R.id.lvNguoiMuon);
        nguoiMuonAdapter = new ArrayAdapter<NguoiMuon>(QuanLiNguoiMuonActivity.this, android.R.layout.simple_list_item_1);
        lvNguoiMuon.setAdapter(nguoiMuonAdapter);
        getNguoiMuonFromDB();
    }
    private void getNguoiMuonFromDB() {
        LoginActivity.database = openOrCreateDatabase(LoginActivity.DATABASE_NAME,MODE_PRIVATE, null);
        Cursor cursor = LoginActivity.database.rawQuery("SELECT * FROM NGUOIMUON",null);
        nguoiMuonAdapter.clear();
        while (cursor.moveToNext()){
            String maNM = cursor.getString(0);
            String ho = cursor.getString(1);
            String gioiTinh = cursor.getString(2);
            String sdt = cursor.getString(3);
            String cmnd = cursor.getString(4);
            String loai = cursor.getString(5);
            NguoiMuon nm = new NguoiMuon(maNM, ho, gioiTinh, sdt, cmnd, loai);
            nguoiMuonAdapter.add(nm);
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
                nguoiMuonAdapter.getFilter().filter(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
