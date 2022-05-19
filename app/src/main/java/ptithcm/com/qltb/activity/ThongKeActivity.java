package ptithcm.com.qltb.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ptithcm.com.qltb.R;

public class ThongKeActivity extends AppCompatActivity {

    Button btnDSPM, btnDSPN, btnDSPTL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        addComtrols();
        addEvents();
    }

    private void addEvents() {
        btnDSPM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThongKeActivity.this, DanhSachPhieuMuonCTraActivity.class);
                startActivity(intent);
            }
        });
        btnDSPN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThongKeActivity.this, DanhSachPhieuNhapActivity.class);
                startActivity(intent);
            }
        });
        btnDSPTL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThongKeActivity.this, DanhSachPhieuThanhLyActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addComtrols() {
        btnDSPM = (Button) findViewById(R.id.btnDSPM);
        btnDSPN = (Button) findViewById(R.id.btnDSPN);
        btnDSPTL = (Button) findViewById(R.id.btnDSPTL);
    }
}
