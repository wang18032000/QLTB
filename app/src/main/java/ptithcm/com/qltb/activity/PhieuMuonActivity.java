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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import ptithcm.com.qltb.R;
import ptithcm.com.qltb.model.CT_PhieuMuon;
import ptithcm.com.qltb.model.NguoiMuon;
import ptithcm.com.qltb.model.PhieuMuon;
import ptithcm.com.qltb.model.ThietBi;
import ptithcm.com.qltb.model.TrangThai;

public class PhieuMuonActivity extends AppCompatActivity {

    TextView txtMaMN, txtTenNM, txtPhong, txtThoiGianMuon;
    ImageView imgThemTB_CTPM;
    ListView lvCTPM;
    ArrayAdapter<CT_PhieuMuon> cTPMAdapter;
    ArrayAdapter<NguoiMuon> nguoiMuonAdapter;
    ArrayAdapter<ThietBi> thietBiAdapter;
    ArrayAdapter<TrangThai> trangThaiAdapter;
    TrangThai trangThai;
    NguoiMuon nguoiMuon;
    PhieuMuon phieuMuon;
    CT_PhieuMuon ct_phieuMuon;
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
        lvCTPM.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ct_phieuMuon = cTPMAdapter.getItem(i);
                showDialogThietBi();
            }
        });
        imgThemTB_CTPM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDiaLogAddThietBi();
            }
        });
    }

    private void addControls() {
        Intent intent = getIntent();
        phieuMuon = (PhieuMuon) intent.getSerializableExtra("phieumuon");

        txtMaMN = (TextView) findViewById(R.id.txtMaNM_CTPM);
        txtTenNM = (TextView) findViewById(R.id.txtTenNM_CTPM);
        txtPhong = (TextView) findViewById(R.id.txtPhong_CTPM);
        txtThoiGianMuon = (TextView) findViewById(R.id.txtThoigianMuon_CTPM);
        imgThemTB_CTPM = (ImageView) findViewById(R.id.imgThemTB_CTPM);

        lvCTPM = (ListView) findViewById(R.id.lvTB_CTPM);
        cTPMAdapter = new ArrayAdapter<CT_PhieuMuon>(PhieuMuonActivity.this, android.R.layout.simple_list_item_1);
        lvCTPM.setAdapter(cTPMAdapter);

        txtMaMN.setText(phieuMuon.getMaMN());
        txtTenNM.setText(findTen(phieuMuon.getMaMN()));
        txtPhong.setText(phieuMuon.getMaP());
        txtThoiGianMuon.setText(phieuMuon.getThoiGianMuon());

        getCTFromDB(phieuMuon.getMaPM());
    }

    private void getCTFromDB(String ma) {
        LoginActivity.database = openOrCreateDatabase(LoginActivity.DATABASE_NAME,MODE_PRIVATE, null);
        Cursor cursor = LoginActivity.database.query("CT_PHIEUMUON",null,"MaPM=?",new String[]{ma},null,null,null);
        cTPMAdapter.clear();
        while (cursor.moveToNext()){
            String maPM = cursor.getString(0);
            String maTB = cursor.getString(1);
            String thoigiantra = cursor.getString(2);
            CT_PhieuMuon ct = new CT_PhieuMuon(maPM, maTB, thoigiantra);
            cTPMAdapter.add(ct);
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
            String maNM = cursor.getString(0);
            String hoten = cursor.getString(1);
            String gioiTinh = cursor.getString(2);
            String sdt = cursor.getString(3);
            String cmnd = cursor.getString(4);
            String loai = cursor.getString(5);
            nguoiMuon = new NguoiMuon(maNM, hoten, gioiTinh, sdt, cmnd, loai);
            nguoiMuonAdapter.add(nguoiMuon);
        }
        cursor.close();
    }

    private void findTB(String ma){
        LoginActivity.database = openOrCreateDatabase(LoginActivity.DATABASE_NAME, MODE_PRIVATE, null);
        Cursor cursor = LoginActivity.database.query("THIETBI", null,"MaTB=?",new String[]{ma},null,null,null);
        thietBiAdapter.clear();
        while (cursor.moveToNext()) {
            String matb = cursor.getString(0);
            String tentb = cursor.getString(1);
            String matt = cursor.getString(2);
            String maloai = cursor.getString(3);
            String map = cursor.getString(4);
            thietbi = new ThietBi(matb, tentb, matt, maloai,map);
            thietBiAdapter.add(thietbi);
        }
        cursor.close();
    }

    private void getTBFromDB(String tt,String p) {
        LoginActivity.database = openOrCreateDatabase(LoginActivity.DATABASE_NAME, MODE_PRIVATE, null);
        Cursor cursor = LoginActivity.database.rawQuery("SELECT * FROM THIETBI WHERE MaTT=?",new String[]{tt});
        thietBiAdapter.clear();
        while (cursor.moveToNext()) {
            String matb = cursor.getString(0);
            String tentb = cursor.getString(1);
            String matt = cursor.getString(2);
            String maloai = cursor.getString(3);
            String map = cursor.getString(4);
            ThietBi tb = new ThietBi(matb, tentb, matt, maloai, map);
            thietBiAdapter.add(tb);
        }
        cursor.close();
        ThietBi tb = new ThietBi();
        ArrayAdapter<ThietBi> tmp;
        tmp = new ArrayAdapter<ThietBi>(PhieuMuonActivity.this, android.R.layout.simple_list_item_1);
        for (int i =0; i < thietBiAdapter.getCount();i++){
            tb = thietBiAdapter.getItem(i);
            if ((thietBiAdapter.getItem(i).getMaP().equals(p))||(thietBiAdapter.getItem(i).getMaP().equals("2A"))){
                tmp.add(tb);
            }
        }
        thietBiAdapter.clear();
        for (int i =0; i < tmp.getCount();i++){
            tb = tmp.getItem(i);
            thietBiAdapter.add(tb);
        }
    }

    private void showDialogThietBi(){
        dialogChiTietTB = new Dialog(PhieuMuonActivity.this);
        dialogChiTietTB.setContentView(R.layout.dialog_chi_tiet_tb_pm);

        final TextView txtTen = dialogChiTietTB.findViewById(R.id.txtTenTBdetail);
        final TextView txtTT = dialogChiTietTB.findViewById(R.id.txtTT_detail);
        final Button btnTra = dialogChiTietTB.findViewById(R.id.btnTra);
        thietBiAdapter = new ArrayAdapter<ThietBi>(PhieuMuonActivity.this, android.R.layout.simple_list_item_1);
        trangThaiAdapter= new ArrayAdapter<TrangThai>(PhieuMuonActivity.this, android.R.layout.simple_list_item_1);
        findTB(ct_phieuMuon.getMaTB());
        thietbi = thietBiAdapter.getItem(0);
        txtTen.setText(thietbi.getTenTB());
        txtTT.setText(findTrangThai(thietbi.getMaTT()));

        btnTra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTB(thietbi,"SC");
                update(thietbi);
                getCTFromDB(phieuMuon.getMaPM());
                dialogChiTietTB.dismiss();
            }
        });
        dialogChiTietTB.show();
    }

    private String findTrangThai(String ma){
        LoginActivity.database = openOrCreateDatabase(LoginActivity.DATABASE_NAME, MODE_PRIVATE, null);
        Cursor cursor = LoginActivity.database.query("TRANGTHAI", null,"MaTT=?",new String[]{ma},null,null,null);
        thietBiAdapter.clear();
        while (cursor.moveToNext()) {
            String matt = cursor.getString(0);
            String tentt = cursor.getString(1);
            trangThai =  new TrangThai(matt, tentt);
            trangThaiAdapter.add(trangThai);
        }
        cursor.close();
        return trangThai.getTenTT();
    }

    private void showDiaLogAddThietBi(){
        dialogThemTB = new Dialog(PhieuMuonActivity.this);
        dialogThemTB.setContentView(R.layout.dialog_them_tb_pm);

        final ListView lvThemTB = dialogThemTB.findViewById(R.id.lvThemTB_CTPM);
        thietBiAdapter = new ArrayAdapter<ThietBi>(PhieuMuonActivity.this, android.R.layout.simple_list_item_1);
        lvThemTB.setAdapter(thietBiAdapter);
        getTBFromDB("SC",phieuMuon.getMaP());

        lvThemTB.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                thietbi = thietBiAdapter.getItem(i);
                updateTB(thietbi,"DM");
                add();
            }
        });

        dialogThemTB.show();
    }

    private void updateTB(ThietBi thietbi,String t){
        ContentValues values = new ContentValues();
        values.put("MaTB",thietbi.getMaTB());
        values.put("TenTB",thietbi.getTenTB());
        values.put("MaTT",t);
        values.put("MaLoai",thietbi.getMaLoai());
        values.put("MaP",thietbi.getMaP());
        int kq = LoginActivity.database.update("THIETBI", values, "MaTB = ?", new String[]{thietbi.getMaTB()});
        if (kq != 0){
            getCTFromDB(phieuMuon.getMaPM());
        }else {
            Toast.makeText(PhieuMuonActivity.this, "Có lỗi xảy ra, vui lòng thử lại", Toast.LENGTH_LONG).show();
        }
    }

    private void add(){
        ContentValues values = new ContentValues();
        values.put("MaPM",phieuMuon.getMaPM().toString());
        values.put("MaTB",thietbi.getMaTB().toString());
        values.put("ThoiGianTra","");
        int kq = (int) LoginActivity.database.insert("CT_PHIEUMUON",null,values);
        if (kq >0){
            //Toast.makeText(PhieuMuonActivity.this, "Thêm thành công", Toast.LENGTH_LONG).show();
            dialogThemTB.dismiss();
            getCTFromDB(phieuMuon.getMaPM());
        } else {
            dialogThemTB.dismiss();
            getCTFromDB(phieuMuon.getMaPM());
            Toast.makeText(PhieuMuonActivity.this, "Thêm thành công", Toast.LENGTH_LONG).show();
        }
    }

    private void update(ThietBi thietbi){
        ContentValues values = new ContentValues();
        values.put("MaPM",phieuMuon.getMaPM().toString());
        values.put("MaTB",thietbi.getMaTB().toString());
        long millis=System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        String toDay = String.valueOf(date);
        values.put("ThoiGianTra",toDay);
        int kq = (int) LoginActivity.database.update("CT_PHIEUMUON", values, "MaPM =? AND MaTB = ?", new String[]{phieuMuon.getMaPM().toString(),thietbi.getMaTB()});
        if (kq > 0) {

            Toast.makeText(PhieuMuonActivity.this, "Đã trả", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(PhieuMuonActivity.this, "Có lỗi xảy ra, vui lòng thử lại", Toast.LENGTH_LONG).show();
        }
    }
}
