package ptithcm.com.qltb.activity;

import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import ptithcm.com.qltb.R;
import ptithcm.com.qltb.model.CT_PhieuMuon;
import ptithcm.com.qltb.model.NguoiMuon;
import ptithcm.com.qltb.model.PhieuMuon;
import ptithcm.com.qltb.model.ThietBi;

public class DanhSachPhieuMuonCTraActivity extends AppCompatActivity {

    ListView lvPhieuMuon;
    ArrayAdapter<PhieuMuon> phieuMuonAdapter,phieuMuonAdapterPost;
    ArrayAdapter<CT_PhieuMuon> ct_phieuAdapter;
    ArrayAdapter<NguoiMuon> nguoiMuonAdapter;
    ArrayAdapter<ThietBi> thietBiAdapter, tmp;
    PhieuMuon phieuMuon;
    ThietBi thietBi;
    NguoiMuon nguoiMuon;
    Dialog dialogCTNMCT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_phieu_muon_ctra);
        addControls();
        addEvents();
    }

    private void addEvents() {
        lvPhieuMuon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                phieuMuon = phieuMuonAdapterPost.getItem(i);
                nguoiMuon = nguoiMuonAdapter.getItem(0);
                showCTNMCT();
            }
        });
    }

    private void showCTNMCT() {
        dialogCTNMCT = new Dialog(DanhSachPhieuMuonCTraActivity.this);
        dialogCTNMCT.setContentView(R.layout.dialog_ctnmct);

        final TextView txtMaNM = (TextView) dialogCTNMCT.findViewById(R.id.txtMaNM_ctct);
        final TextView txtTenNM = (TextView) dialogCTNMCT.findViewById(R.id.txtHoNM_ctct);
        final TextView txtSDT = (TextView) dialogCTNMCT.findViewById(R.id.txtSDT_ctct);
        final ListView lvTBCT = (ListView) dialogCTNMCT.findViewById(R.id.lvTBCT);
        final Button btnTra = (Button) dialogCTNMCT.findViewById(R.id.btnTraTBCTCT);

        txtMaNM.setText(nguoiMuon.getMaNM());
        txtTenNM.setText(nguoiMuon.getHoTen());
        txtSDT.setText(nguoiMuon.getsDT());
        lvTBCT.setAdapter(tmp);
        btnTra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i< tmp.getCount();i++) {
                    thietBi = tmp.getItem(i);
                    updateTB(thietBi, "SC");
                    update(thietBi);
                }
                getPhieuMuonFromDB();
                dialogCTNMCT.dismiss();
            }
        });
        dialogCTNMCT.show();

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
        }else {
            Toast.makeText(DanhSachPhieuMuonCTraActivity.this, "Có lỗi xảy ra, vui lòng thử lại", Toast.LENGTH_LONG).show();
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

            Toast.makeText(DanhSachPhieuMuonCTraActivity.this, "Đã trả", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(DanhSachPhieuMuonCTraActivity.this, "Có lỗi xảy ra, vui lòng thử lại", Toast.LENGTH_LONG).show();
        }
    }

    private void addControls() {
        lvPhieuMuon = (ListView) findViewById(R.id.lvPhieuMuonCT);
        ct_phieuAdapter = new ArrayAdapter<CT_PhieuMuon>(DanhSachPhieuMuonCTraActivity.this, android.R.layout.simple_list_item_1);
        phieuMuonAdapterPost = new ArrayAdapter<PhieuMuon>(DanhSachPhieuMuonCTraActivity.this, android.R.layout.simple_list_item_1);
        phieuMuonAdapter = new ArrayAdapter<PhieuMuon>(DanhSachPhieuMuonCTraActivity.this, android.R.layout.simple_list_item_1);
        thietBiAdapter = new ArrayAdapter<ThietBi>(DanhSachPhieuMuonCTraActivity.this, android.R.layout.simple_list_item_1);
        tmp = new ArrayAdapter<ThietBi>(DanhSachPhieuMuonCTraActivity.this, android.R.layout.simple_list_item_1);
        getPhieuMuonFromDB();
        phieuMuon = new PhieuMuon();
        int kt = 0;
        for (int i = 0;i < phieuMuonAdapter.getCount();i++){
            getCTFromDB(phieuMuonAdapter.getItem(i).getMaPM());
            for(int j =0; j < ct_phieuAdapter.getCount();j++){
                if (ct_phieuAdapter.getItem(j).getThoiGianTra().equals("")){
                    kt =1;
                    phieuMuon =  phieuMuonAdapter.getItem(i);
                    nguoiMuonAdapter = new ArrayAdapter<NguoiMuon>(DanhSachPhieuMuonCTraActivity.this, android.R.layout.simple_list_item_1);
                    getNguoiMuonFromDB(phieuMuon.getMaMN());
                    getThietBiFromDB(ct_phieuAdapter.getItem(j).getMaTB());
                    thietBi = thietBiAdapter.getItem(0);
                    tmp.add(thietBi);
                }
            }
            if (kt ==1 ){
                phieuMuon =  phieuMuonAdapter.getItem(i);
                phieuMuonAdapterPost.add(phieuMuon);
                kt =0;
            }
        }
        lvPhieuMuon.setAdapter(phieuMuonAdapterPost);
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
            NguoiMuon nm = new NguoiMuon(maNM, hoten, gioiTinh, sdt, cmnd, loai);
            nguoiMuonAdapter.add(nm);
        }
        cursor.close();
    }

    private void getThietBiFromDB(String ma) {
        LoginActivity.database = openOrCreateDatabase(LoginActivity.DATABASE_NAME, MODE_PRIVATE, null);
        Cursor cursor = LoginActivity.database.rawQuery("SELECT * FROM THIETBI WHERE MaTB=?", new String[]{ma});
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
    }

    private void getCTFromDB(String m) {
        LoginActivity.database = openOrCreateDatabase(LoginActivity.DATABASE_NAME,MODE_PRIVATE, null);
        Cursor cursor = LoginActivity.database.query("CT_PHIEUMUON",null,"MaPM = ?",new  String[]{m},null,null,null);
        ct_phieuAdapter.clear();
        while (cursor.moveToNext()){
            String maPM = cursor.getString(0);
            String maTB = cursor.getString(1);
            String thoigiantra = cursor.getString(2);
            CT_PhieuMuon ct = new CT_PhieuMuon(maPM, maTB, thoigiantra);
            ct_phieuAdapter.add(ct);
        }
        cursor.close();
    }
    private void getPhieuMuonFromDB() {
        LoginActivity.database = openOrCreateDatabase(LoginActivity.DATABASE_NAME,MODE_PRIVATE, null);
        Cursor cursor = LoginActivity.database.query("PHIEUMUON",null,null,null,null,null,null);
        phieuMuonAdapter.clear();
        while (cursor.moveToNext()){
            String maPM = cursor.getString(0);
            String thoiGian = cursor.getString(1);
            String phong = cursor.getString(5);
            String ghiChu = cursor.getString(2);
            String maMN = cursor.getString(4);
            String maNV = cursor.getString(3);
            PhieuMuon pm = new PhieuMuon(maPM,thoiGian, ghiChu,  maNV ,maMN, phong );
            phieuMuonAdapter.add(pm);
        }
        cursor.close();
    }
}
