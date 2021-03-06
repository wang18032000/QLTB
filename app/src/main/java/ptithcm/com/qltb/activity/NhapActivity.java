package ptithcm.com.qltb.activity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import ptithcm.com.qltb.model.CT_PhieuMuon;
import ptithcm.com.qltb.model.CT_PhieuNhap;
import ptithcm.com.qltb.model.LoaiThietBi;
import ptithcm.com.qltb.model.PhieuNhap;
import ptithcm.com.qltb.model.Phong;
import ptithcm.com.qltb.model.ThietBi;

public class NhapActivity extends AppCompatActivity {

    PhieuNhap phieuNhap;
    LoaiThietBi selectedLoai;
    ThietBi thietBi;
    TextView txtNgayNhap,txtTongNhap,txtM;
    Spinner spLoai, spPhong;
    ImageView imgThemTBNhap;
    EditText edtTen,edtGia;
    Button btnNhapTB;
    ListView lvTBNhap;
    int Tong = 0;
    ArrayAdapter<Phong> phongAdapter;
    ArrayAdapter<LoaiThietBi> loaiAdapter;
    ArrayAdapter<CT_PhieuNhap> ctPNAdapter;
    ArrayAdapter<LoaiThietBi> loaiArrayAdapter;
    Dialog dialogNhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhap);
        addControls();
        addEvents();
    }

    private void addEvents() {
        imgThemTBNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogNhapTB();
            }
        });
    }

    private void showDialogNhapTB() {
        dialogNhap = new Dialog(NhapActivity.this);
        dialogNhap.setContentView(R.layout.dialog_nhap_tb);

        edtTen = (EditText) dialogNhap.findViewById(R.id.edtTenTB_Nhap);
        edtGia = (EditText) dialogNhap.findViewById(R.id.edtGia);
        btnNhapTB = (Button) dialogNhap.findViewById(R.id.btnNhapTB);
        spLoai = (Spinner) dialogNhap.findViewById(R.id.spLoaiTBadd);
        spPhong = (Spinner) dialogNhap.findViewById(R.id.spPhong_Nhapadd);

        loaiArrayAdapter = new ArrayAdapter<LoaiThietBi>(NhapActivity.this, android.R.layout.simple_spinner_item);
        loaiArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLoai.setAdapter(loaiArrayAdapter);
        getLoaiTBFromDB();

        phongAdapter = new ArrayAdapter<Phong>(NhapActivity.this, android.R.layout.simple_spinner_item);
        phongAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPhong.setAdapter(phongAdapter);
        getPhongFromDB();

        spLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                selectedLoai = loaiArrayAdapter.getItem(i);
//                txtM.setText(selectedLoai.getMaLoai());
                thietBi.setMaLoai(loaiArrayAdapter.getItem(i).getMaLoai());

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
        btnNhapTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (kiemtradulieu()){
                    createThietBi();
                    createCTNhap();
                }
            }
        });

        dialogNhap.show();
    }

    private void getPhongFromDB() {
        LoginActivity.database = openOrCreateDatabase(LoginActivity.DATABASE_NAME, MODE_PRIVATE, null);
        Cursor cursor = LoginActivity.database.rawQuery("SELECT * FROM PHONG", null);
        phongAdapter.clear();
        while (cursor.moveToNext()) {
            String ma = cursor.getString(0);
            String loai = cursor.getString(1);
            Phong p = new Phong(ma, loai);
            phongAdapter.add(p);
        }
        cursor.close();
    }

    private boolean kiemtradulieu() {
        String ten = edtTen.getText().toString();
        String gia = edtGia.getText().toString();

        if(!ten.equals("") && !gia.equals("")){
            final int s = gia.length();
            for (int i =0; i<s;i++){
                if (!Character.isDigit(gia.charAt(i))){
                    Toast.makeText(this, "Vui l??ng nh???p ????ng ki???u d??? li???u", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            return true;
        }else {
            Toast.makeText(this, "Vui l??ng nh???p ????? th??ng tin", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void getLoaiTBFromDB() {
        LoginActivity.database = openOrCreateDatabase(LoginActivity.DATABASE_NAME, MODE_PRIVATE, null);
        Cursor cursor = LoginActivity.database.rawQuery("SELECT * FROM LOAI", null);
        loaiArrayAdapter.clear();
        while (cursor.moveToNext()) {
            String ma = cursor.getString(0);
            String ten = cursor.getString(1);
            LoaiThietBi loai = new LoaiThietBi(ma, ten);
            loaiArrayAdapter.add(loai);
        }
        cursor.close();
    }

    private void createCTNhap() {
        ContentValues values = new ContentValues();
        values.put("MaPN",phieuNhap.getMaPN());
        values.put("MaTB",layMa(edtTen.getText().toString()));
        values.put("DonGia",edtGia.getText().toString());
        int kq  = (int) LoginActivity.database.insert("CT_PHIEUNHAP",null, values);
        if (kq >0){
            Toast.makeText(NhapActivity.this, "???? nh???p", Toast.LENGTH_LONG).show();
            dialogNhap.dismiss();
            getCTPN(phieuNhap.getMaPN());
            Tong += Integer.parseInt(edtGia.getText().toString());
            txtTongNhap.setText(String.valueOf(Tong)+ " VND");
        }else {
            Toast.makeText(NhapActivity.this, "C?? l???i x???y ra, vui l??ng th??? l???i", Toast.LENGTH_LONG).show();
        }
    }

    private void createThietBi() {
        ContentValues values = new ContentValues();
        values.put("MaTB",layMa(edtTen.getText().toString()));
        values.put("TenTB",edtTen.getText().toString());
        values.put("MaTT","SC");
        values.put("MaLoai",thietBi.getMaLoai().toString());
        values.put("MaP", thietBi.getMaP());
        int kq = (int) LoginActivity.database.insert("THIETBI",null,values);
        if (kq >0){

        }else {
            Toast.makeText(NhapActivity.this, "C?? l???i x???y ra, vui l??ng th??? l???i", Toast.LENGTH_LONG).show();
        }
    }

    public String layMa(String str){
        String ten = "";
        String[] tu = str.split(" ");
        Random rand = new Random();
        int ranNum = rand.nextInt(100)+1;
        for (String s : tu) {
            if (!s.equals("") && !s.equals(null)) {
                ten+=String.valueOf(s.charAt(0));
                ten.toUpperCase();
            }
        }
        return ten+String.valueOf(ranNum);
    }

    private void addControls() {
        Intent intent = getIntent();
        phieuNhap = (PhieuNhap) intent.getSerializableExtra("phieuNhap");

        thietBi = new ThietBi();
        txtNgayNhap = (TextView) findViewById(R.id.txtNgayNhap);
        txtTongNhap = (TextView) findViewById(R.id.txtTongNhap);
        imgThemTBNhap = (ImageView) findViewById(R.id.imgThemTB_Nhap);
        lvTBNhap = (ListView) findViewById(R.id.lvTB_Nhap);
        ctPNAdapter = new ArrayAdapter<CT_PhieuNhap>(NhapActivity.this, android.R.layout.simple_list_item_1);
        lvTBNhap.setAdapter(ctPNAdapter);

        txtNgayNhap.setText(phieuNhap.getThoiGianNhap());
        getCTPN(phieuNhap.getMaPN());
        txtTongNhap.setText(String.valueOf(Tong)+ " VND");
    }

    private void getCTPN(String ma) {
        LoginActivity.database = openOrCreateDatabase(LoginActivity.DATABASE_NAME,MODE_PRIVATE, null);
        Cursor cursor = LoginActivity.database.query("CT_PHIEUNHAP",null,"MaPN=?",new String[]{ma},null,null,null);
        ctPNAdapter.clear();
        while (cursor.moveToNext()){
            String maPN = cursor.getString(0);
            String maTB = cursor.getString(1);
            String dongia = cursor.getString(2);

            CT_PhieuNhap ct = new CT_PhieuNhap(maPN, maTB, dongia);
            ctPNAdapter.add(ct);
        }
        cursor.close();
    }
}
