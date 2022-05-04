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

import ptithcm.com.qltb.R;
import ptithcm.com.qltb.model.PhieuNhap;
import ptithcm.com.qltb.model.PhieuThanhLy;
import ptithcm.com.qltb.model.ThietBi;

public class ThanhLyActivity extends AppCompatActivity {

    PhieuThanhLy phieuThanhLy;
    TextView txtNgayTL,txtTenTB;
    EditText edtGiaTL;
    ThietBi thietBi;
    Button btnTL;
    ListView lvThietBiTL;
    Dialog dialogTL;

    ArrayAdapter<ThietBi> thietBiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_ly);
        addControls();
        addEvents();
    }

    private void addEvents() {
        lvThietBiTL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                thietBi = thietBiAdapter.getItem(i);
                showDialogCTPTL();
            }
        });
    }

    private void showDialogCTPTL() {
        dialogTL = new Dialog(ThanhLyActivity.this);
        dialogTL.setContentView(R.layout.dialog_ct_ptl);

        txtTenTB = (TextView) dialogTL.findViewById(R.id.txtTenTB_TL);
        edtGiaTL = (EditText) dialogTL.findViewById(R.id.edtGia_TL);
        btnTL = (Button) dialogTL.findViewById(R.id.btnTL);

        txtTenTB.setText(thietBi.getTenTB());

        btnTL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createCTPTL();
                updateTB();
            }
        });
        dialogTL.show();
    }

    private void updateTB() {
        ContentValues values = new ContentValues();
        values.put("MaTB",thietBi.getMaTB());
        values.put("TenTB",thietBi.getTenTB());
        values.put("GhiChu",thietBi.getGhiChu());
        values.put("MaLoai",thietBi.getMaLoai());
        values.put("TrangThai","Da TL");
        values.put("TinhTrang","Da TL");
        int kq = (int) LoginActivity.database.update("THIETBI", values, "MaTB = ?", new String[]{thietBi.getMaTB()});
        if (kq >0){
            Toast.makeText(ThanhLyActivity.this, "Đã cập nhật thiết bị", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(ThanhLyActivity.this, "Có lỗi xảy ra, vui lòng thử lại", Toast.LENGTH_LONG).show();
        }
    }

    private void createCTPTL() {
        ContentValues values = new ContentValues();
        values.put("MaPTL",phieuThanhLy.getMaPTL());
        values.put("MaTB",thietBi.getMaTB());
        values.put("DonGia",edtGiaTL.getText().toString());
        int kq  = (int) LoginActivity.database.insert("CT_PHIEUTHANHLY",null, values);
        if (kq >0){
            Toast.makeText(ThanhLyActivity.this, "Đã Thanh lý", Toast.LENGTH_LONG).show();
            dialogTL.dismiss();
            getThietBiFromDB("CTL");
        }else {
            Toast.makeText(ThanhLyActivity.this, "Có lỗi xảy ra, vui lòng thử lại", Toast.LENGTH_LONG).show();
        }
    }

    private void addControls() {
        Intent intent = getIntent();
        phieuThanhLy = (PhieuThanhLy) intent.getSerializableExtra("phieuThanhLy");

        txtNgayTL = (TextView) findViewById(R.id.txtNgayTL);
        lvThietBiTL = (ListView) findViewById(R.id.lvTB_TL);
        thietBiAdapter = new ArrayAdapter<ThietBi>(ThanhLyActivity.this, android.R.layout.simple_list_item_1);
        lvThietBiTL.setAdapter(thietBiAdapter);
        getThietBiFromDB("CTL");
    }

    private void getThietBiFromDB(String T) {
        LoginActivity.database = openOrCreateDatabase(LoginActivity.DATABASE_NAME, MODE_PRIVATE, null);
        Cursor cursor = LoginActivity.database.query("THIETBI", null,"TinhTrang = ?",new String[]{T},null,null,null);
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
