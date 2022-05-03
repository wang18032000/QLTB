package ptithcm.com.qltb.activity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import ptithcm.com.qltb.R;
import ptithcm.com.qltb.model.CT_PhieuMuon;
import ptithcm.com.qltb.model.CT_PhieuNhap;
import ptithcm.com.qltb.model.PhieuNhap;

public class NhapActivity extends AppCompatActivity {

    PhieuNhap phieuNhap;
    TextView txtNgayNhap,txtTongNhap;
    ImageView imgThemTBNhap;
    EditText edtTen,edtGia;
    Button btnNhapTB;
    ListView lvTBNhap;
    int Tong;
    ArrayAdapter<CT_PhieuNhap> ctPNAdapter;
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

        btnNhapTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createThietBi();
                createCTNhap();
            }
        });

        dialogNhap.show();
    }

    private void addControls() {
        Intent intent = getIntent();
        phieuNhap = (PhieuNhap) intent.getSerializableExtra("phieuNhap");

        txtNgayNhap = (TextView) findViewById(R.id.txtNgayNhap);
        txtTongNhap = (TextView) findViewById(R.id.txtTongNhap);
        imgThemTBNhap = (ImageView) findViewById(R.id.imgThemTB_Nhap);
        lvTBNhap = (ListView) findViewById(R.id.lvTB_Nhap);
        ctPNAdapter = new ArrayAdapter<CT_PhieuNhap>(NhapActivity.this, android.R.layout.simple_list_item_1);
        lvTBNhap.setAdapter(ctPNAdapter);

        txtNgayNhap.setText(phieuNhap.getThoiGianNhap());
        getCTPN(phieuNhap.getMaPN());
        txtTongNhap.setText(String.valueOf(Tong));
    }

    private void getCTPN(String ma) {
        LoginActivity.database = openOrCreateDatabase(LoginActivity.DATABASE_NAME,MODE_PRIVATE, null);
        Cursor cursor = LoginActivity.database.query("CT_PHIEUMUON",null,"MaPM=?",new String[]{ma},null,null,null);
        ctPNAdapter.clear();
        while (cursor.moveToNext()){
            String maPN = cursor.getString(0);
            String maTB = cursor.getString(1);
            String dongia = cursor.getString(2);
            Tong += Integer.parseInt(dongia);
            CT_PhieuNhap ct = new CT_PhieuNhap(maPN, maTB, dongia);
            ctPNAdapter.add(ct);
        }
        cursor.close();
    }
}
