package ptithcm.com.qltb.activity;

import android.app.Dialog;
import android.content.ContentValues;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import ptithcm.com.qltb.R;
import ptithcm.com.qltb.model.NguoiMuon;
import ptithcm.com.qltb.model.NhanVien;

public class QuanLiNguoiMuonActivity extends AppCompatActivity {

    ArrayAdapter<NguoiMuon> nguoiMuonAdapter;
    NguoiMuon nguoiMuon;
    ImageView imgThemNM;
    ListView lvNguoiMuon;
    TextView txtMa;
    EditText edtHo, edtTen, edtGioiTinh, edtNgaySinh, edtDiaChi, edtCMND, edtGhiChu, edtLoai;
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
        edtHo = (EditText) dialogNM.findViewById(R.id.edtHoNM_detail);
        edtTen = (EditText) dialogNM.findViewById(R.id.edtTenNM_detail);
        edtGioiTinh = (EditText) dialogNM.findViewById(R.id.edtGTNM_detail);
        edtNgaySinh = (EditText) dialogNM.findViewById(R.id.edtNSNM_detail);
        edtDiaChi =(EditText) dialogNM.findViewById(R.id.edtDCNM_detail);
        edtCMND = (EditText) dialogNM.findViewById(R.id.edtCMNDNM_detail);
        edtGhiChu = (EditText) dialogNM.findViewById(R.id.edtGCNM_detail);
        edtLoai = (EditText) dialogNM.findViewById(R.id.edtLoaiNM_detail);
        btnCapNhat = dialogNM.findViewById(R.id.btnCapNhatNM);

        txtMa.setText(nguoiMuon.getMaNM());
        edtHo.setText(nguoiMuon.getHo());
        edtTen.setText(nguoiMuon.getTen());
        edtGioiTinh.setText(nguoiMuon.getGioiTinh());
        edtNgaySinh.setText(nguoiMuon.getNgaySinh());
        edtDiaChi.setText(nguoiMuon.getDiaChi());
        edtCMND.setText(nguoiMuon.getCmnd());
        edtGhiChu.setText(nguoiMuon.getGhiChu());
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
        String ho = edtHo.getText().toString();
        String ten = edtTen.getText().toString();
        String gt = edtGioiTinh.getText().toString();
        String ns = edtNgaySinh.getText().toString();;
        String dc = edtDiaChi.getText().toString();
        String cmnd = edtCMND.getText().toString();
        String loai = edtLoai.getText().toString();
        if (!ho.equals("") && !ten.equals("") && !gt.equals("") && !ns.equals("") && !dc.equals("") && !cmnd.equals("") && !loai.equals("")){
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
        values.put("Ho", edtHo.getText().toString());
        values.put("Ten", edtTen.getText().toString());
        values.put("GioiTinh", edtGioiTinh.getText().toString());
        values.put("NgaySinh", edtNgaySinh.getText().toString());
        values.put("DiaChi", edtDiaChi.getText().toString());
        values.put("CMND", edtCMND.getText().toString());
        values.put("GhiChu", edtGhiChu.getText().toString());
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

        edtHo = (EditText) dialogThemNM.findViewById(R.id.edtHoNM_add);
        edtTen = (EditText) dialogThemNM.findViewById(R.id.edtTenNM_add);
        edtGioiTinh = (EditText) dialogThemNM.findViewById(R.id.edtGTNM_add);
        edtNgaySinh = (EditText) dialogThemNM.findViewById(R.id.edtNSNM_add);
        edtDiaChi =(EditText) dialogThemNM.findViewById(R.id.edtDCNM_add);
        edtCMND = (EditText) dialogThemNM.findViewById(R.id.edtCMNDNM_add);
        edtGhiChu = (EditText) dialogThemNM.findViewById(R.id.edtGCNM_add);
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
        values.put("MaNM", layMa(edtLoai.getText().toString()));
        values.put("Ho", edtHo.getText().toString());
        values.put("Ten", edtTen.getText().toString());
        values.put("GioiTinh", edtGioiTinh.getText().toString());
        values.put("NgaySinh", edtNgaySinh.getText().toString());
        values.put("DiaChi", edtDiaChi.getText().toString());
        values.put("CMND", edtCMND.getText().toString());
        values.put("GhiChu", edtGhiChu.getText().toString());
        values.put("Loai",edtLoai.getText().toString());
        int kq = (int) LoginActivity.database.insert("NGUOIMUON",null, values);
        if (kq >0) {
            getNguoiMuonFromDB();
            dialogThemNM.dismiss();
            Toast.makeText(QuanLiNguoiMuonActivity.this, "Đã thêm người mượn", Toast.LENGTH_LONG).show();
        }else
            Toast.makeText(QuanLiNguoiMuonActivity.this, "Có lỗi xảy ra, vui lòng thử lại", Toast.LENGTH_LONG).show();
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
            String ten = cursor.getString(2);
            String gioiTinh = cursor.getString(3);
            String ngaysinh = cursor.getString(4);
            String diachi = cursor.getString(5);
            String cmnd = cursor.getString(6);
            String ghichu = cursor.getString(7);
            String loai = cursor.getString(8);
            NguoiMuon nm = new NguoiMuon(maNM, ho, ten, gioiTinh, ngaysinh, diachi, cmnd, ghichu, loai);
            nguoiMuonAdapter.add(nm);
        }
        cursor.close();
    }
}
