package ptithcm.com.qltb.activity;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import ptithcm.com.qltb.R;
import ptithcm.com.qltb.model.CT_PhieuMuon;
import ptithcm.com.qltb.model.PhieuNhap;

public class DanhSachPhieuNhapActivity extends AppCompatActivity {

    ListView lvPhieuNhap;
    ArrayAdapter<PhieuNhap> phieuNhapAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_phieu_nhap);
        addControls();
        addEvents();
    }

    private void addEvents() {

    }

    private void addControls() {
        lvPhieuNhap = (ListView) findViewById(R.id.lvPhieuNhap);
        phieuNhapAdapter = new ArrayAdapter<PhieuNhap>(DanhSachPhieuNhapActivity.this, android.R.layout.simple_list_item_1);
        lvPhieuNhap.setAdapter(phieuNhapAdapter);
        getPhieuNhapDB();
    }

    private void getPhieuNhapDB() {
        LoginActivity.database = openOrCreateDatabase(LoginActivity.DATABASE_NAME,MODE_PRIVATE, null);
        Cursor cursor = LoginActivity.database.rawQuery("SELECT * FROM PHIEUNHAP",null);
        phieuNhapAdapter.clear();
        while (cursor.moveToNext()){
            String maPN = cursor.getString(0);
            String thoigian = cursor.getString(1);
            String ghichu = cursor.getString(2);
            String maNV = cursor.getString(3);
            PhieuNhap pn = new PhieuNhap(maPN,thoigian,ghichu,maNV);
            phieuNhapAdapter.add(pn);
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
                phieuNhapAdapter.getFilter().filter(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
