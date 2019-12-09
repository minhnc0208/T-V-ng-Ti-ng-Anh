package education.nguyencaominh.tuvungtienganh;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import education.nguyenhoanghiep.tuvungtienganh.R;

public class MainActivity extends AppCompatActivity {

    private Button btnStart, btnAboutMe, btnGuide, btnHenGio;
    private static final String DATABASE_NAME = "dbtuvung";
    private static final String DB_PATH_SUFFIX = "/databases/";
    private static SQLiteDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        xuLySaoChepCSDLVaoUngDung();
        addControls();
        addEvents();
    }

    private void xuLySaoChepCSDLVaoUngDung() {
        //lấy dữ liệu từ ứng dụng
        File dbFile = getDatabasePath(DATABASE_NAME);
        //Kiểm tra file vừa lấy ra có tồn tại hay không
        //Nếu không có thì tiến hành sao chép dữ liệu
        //Nếu có thì không thực hiện
        if (!dbFile.exists())
        {
            //Thực hiện sao chép dữ liệu
            try {
                copyDatabaseOnAssets();
            }
            catch (Exception ex)
            {
                Log.e("+++++++WARNING+++++++", ex.toString());
            }
        }
    }

    private void copyDatabaseOnAssets() {
        try {
            //Mở file db, myInput lấy dữ liệu từ Assets
            InputStream myInput = getAssets().open(DATABASE_NAME);
            //Đường dẫn lưu trữ, đường dẫn mà cơ sỡ dữ liệu nằm trong thư mục gốc.
            String outFileName = layDuongDanLuuTru();
            //Kiểm tra tồn tại hay chưa.
            //Phòng cho trường hợp chạy lần đầu chưa thấy mới thực hiện.
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if( !f.exists())
            {
                //Tiến hành tao thư mục
                f.mkdir();
            }
            //Tiến hành sao chép dữ liệu
            // myOutPut lấy dữ liệu từ myInput ở trên
            //tương tác với csdl qua myOutPut
            OutputStream myOutPut = new FileOutputStream((outFileName));
            //Chuyển byte từ myInput sang myOutPut
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) >0)
            {
                myOutPut.write(buffer, 0, length);
            }
            //Đẩy vào ứng ụng
            myOutPut.flush();
            //Đóng myOutPut
            myOutPut.close();
            myInput.close();
        }
        catch (Exception ex)
        {
            Log.e("Lỗi Sao Chép Dữ Liệu!!", ex.toString());
        }
    }

    private String layDuongDanLuuTru()
    {
        //getApplicationInfo().dataDir : tên thư mục gốc của ứng dụng + database + database_name
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }

    private void addEvents() {
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "START", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this,danhsachchude.class);
                startActivity(intent);
            }
        });
        btnHenGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HenGioThongBao.class);
                startActivity(intent);
            }
        });
    }
    private void addControls() {
        btnStart = findViewById(R.id.btnStart);
        btnGuide = findViewById(R.id.btnGuide);
        btnAboutMe = findViewById(R.id.btnAboutMe);
        btnHenGio = findViewById(R.id.btnHenGio);
    }
}
