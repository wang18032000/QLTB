package ptithcm.com.qltb.activity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.media.session.IMediaControllerCallback;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import ptithcm.com.qltb.R;
import ptithcm.com.qltb.model.CT_PhieuMuon;
import ptithcm.com.qltb.model.NguoiMuon;
import ptithcm.com.qltb.model.PhieuMuon;
import ptithcm.com.qltb.model.ThietBi;

public class PhieuMuonActivity extends AppCompatActivity {

    TextView txtMaMN, txtTenNM, txtPhong, txtThoiGianMuon;
    Button btnConfirm;
    ImageView imgThemTB_CTPM;
    ListView lvThietBi;
    ArrayAdapter<CT_PhieuMuon> thietBiAdapter;
    ArrayAdapter<NguoiMuon> nguoiMuonAdapter;
    NguoiMuon nguoiMuon;
    PhieuMuon phieuMuon;
    ThietBi thietbi;
    Dialog dialogThemTB,dialogChiTietTB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phieu_muon);
        addControls();
        addEvents();

    }

    private void addEvents() {
    }

    private void addControls() {
        Intent intent = getIntent();
        phieuMuon = (PhieuMuon) intent.getSerializableExtra("phieumuon");

        txtMaMN = (TextView) findViewById(R.id.txtMaNM_CTPM);
        txtTenNM = (TextView) findViewById(R.id.txtTenNM_CTPM);
        txtPhong = (TextView) findViewById(R.id.txtPhong_CTPM);
        txtThoiGianMuon = (TextView) findViewById(R.id.txtThoigianMuon_CTPM);
        imgThemTB_CTPM = (ImageView) findViewById(R.id.imgThemTB_CTPM);
        lvThietBi = (ListView) findViewById(R.id.lvTB_CTPM);
        thietBiAdapter = new ArrayAdapter<CT_PhieuMuon>(PhieuMuonActivity.this, android.R.layout.simple_list_item_1);
        lvThietBi.setAdapter(thietBiAdapter);
        btnConfirm = (Button) findViewById(R.id.btnHoanTat);

        txtMaMN.setText(phieuMuon.getMaMN());
        txtTenNM.setText(findTen(phieuMuon.getMaMN()));
        txtPhong.setText(phieuMuon.getPhong());
        txtThoiGianMuon.setText(phieuMuon.getThoiGianMuon());
        getThietBiFromDB(phieuMuon.getMaMN());
    }

    private void getThietBiFromDB(String ma) {
        LoginActivity.database = openOrCreateDatabase(LoginActivity.DATABASE_NAME,MODE_PRIVATE, null);
        Cursor cursor = LoginActivity.database.query("CT_PHIEUMUON",null,"MaPM=?",new String[]{ma},null,null,null);
        thietBiAdapter.clear();
        while (cursor.moveToNext()){
            String maPM = cursor.getString(0);
            String maTB = cursor.getString(1);
            String thoigiantra = cursor.getString(2);
            CT_PhieuMuon ct = new CT_PhieuMuon(maPM, maTB, thoigiantra);
            thietBiAdapter.add(ct);
        }
        cursor.close();
    }

    private String findTen(String maMN) {
        nguoiMuonAdapter = new ArrayAdapter<NguoiMuon>(PhieuMuonActivity.this, android.R.layout.simple_list_item_1);
        getNguoiMuonFromDB(maMN);
        nguoiMuon = nguoiMuonAdapter.getItem(0);
        return nguoiMuon.toString();
    }

    private void getNguoiMuonFromDB(String ma) {
        LoginActivity.database = openOrCreateDatabase(LoginActivity.DATABASE_NAME,MODE_PRIVATE, null);
        Cursor cursor = LoginActivity.database.query("NGUOIMUON",null,"MaNM=?",new String[]{ma},null,null,null);
        nguoiMuonAdapter.clear();
        while (cursor.moveToNext()){
            String maNV = cursor.getString(0);
            String ho = cursor.getString(1);
            String ten = cursor.getString(2);
            String gioiTinh = cursor.getString(3);
            String ngaysinh = cursor.getString(4);
            String diachi = cursor.getString(5);
            String cmnd = cursor.getString(6);
            String ghichu = cursor.getString(7);
            String loai = cursor.getString(8);
            nguoiMuon = new NguoiMuon(maNV,ho,ten,gioiTinh,ngaysinh,diachi,cmnd,ghichu,loai);
            nguoiMuonAdapter.add(nguoiMuon);
        }
        cursor.close();
    }
}
