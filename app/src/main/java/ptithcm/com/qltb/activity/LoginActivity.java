package ptithcm.com.qltb.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import ptithcm.com.qltb.R;
import ptithcm.com.qltb.model.TaiKhoan;

public class LoginActivity extends AppCompatActivity {
    public static String DATABASE_NAME = "QLTB.db";
    String DB_PATH_SUFFIX = "/databases/";
    public static SQLiteDatabase database = null;
    EditText Username,Password;
    Button btnLogin;
    String queryParamenter;
    TaiKhoan taiKhoan;
    ArrayAdapter<TaiKhoan> taiKhoanAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        processCopy();
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = Username.getText().toString();
                String pass = Password.getText().toString();
                taiKhoanAdapter = new ArrayAdapter<TaiKhoan>(LoginActivity.this, android.R.layout.simple_list_item_1);
                getTaiKhoan();
                for (int i=0;i<taiKhoanAdapter.getCount();i++){
                    if (taiKhoanAdapter.getItem(i).getUsername().equals(user)&taiKhoanAdapter.getItem(i).getPassword().equals(pass)&taiKhoanAdapter.getItem(i).getMaPQ().equals("AD")){
                        taiKhoan=taiKhoanAdapter.getItem(i);
                        Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                        intent.putExtra("taikhoan",taiKhoan);
                        startActivity(intent);
                    }
                    if (taiKhoanAdapter.getItem(i).getUsername().equals(user)&taiKhoanAdapter.getItem(i).getPassword().equals(pass)&taiKhoanAdapter.getItem(i).getMaPQ().equals("US01")){
                        taiKhoan=taiKhoanAdapter.getItem(i);
                        Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                        intent.putExtra("taikhoan",taiKhoan);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private void addControls() {
        Username = (EditText) findViewById(R.id.etdUsername);
        Password = (EditText) findViewById(R.id.etdPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);

    }

    private void getTaiKhoan(){
        database = openOrCreateDatabase(LoginActivity.DATABASE_NAME, MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery("SELECT * FROM TAIKHOAN",null);
        taiKhoanAdapter.clear();
        while (cursor.moveToNext()) {
            String user = cursor.getString(0);
            System.out.println(user);
            String pass = cursor.getString(1);
            System.out.println(pass);
            String email = cursor.getString(2);
            System.out.println(email);
            String pq = cursor.getString(3);
            System.out.println(pq);
            taiKhoan = new TaiKhoan(user, pass, email, pq);
            taiKhoanAdapter.add(taiKhoan);
        }
        cursor.close();
    }

    private void processCopy() {
        File dbFile = getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists()) {
            try {
                copyDataBaseFromAsset();
                Toast.makeText(this, "Copying success from Assets folder" , Toast.LENGTH_LONG).show();

            }
            catch (Exception e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private String getDataBasePath() {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }

    public void copyDataBaseFromAsset() {
        try {
            InputStream myInput;
            myInput = getAssets().open(DATABASE_NAME);
            String outFileName = getDataBasePath();
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists())
                f.mkdir();
            OutputStream myOuput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOuput.write(buffer, 0, length);
            }
            myOuput.flush();
            myOuput.close();
            myInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
