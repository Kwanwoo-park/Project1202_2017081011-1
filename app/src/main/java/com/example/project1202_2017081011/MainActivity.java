package com.example.project1202_2017081011;

        import androidx.appcompat.app.AppCompatActivity;

        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    MyDBHelper myHelper; //선언문들
    SQLiteDatabase sqlDB;
    EditText etName, etNumber, etNameResult, etEtNumberResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //Q.super를 안쓰면 왜 안되나용..?
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etNumber = findViewById(R.id.etNumber);
        etNameResult = findViewById(R.id.etNameResult);
        etEtNumberResult = findViewById(R.id.etNumberResult);

        myHelper = new MyDBHelper(this); //객체생성 > OnCreate, Table 생성

        (findViewById(R.id.btnInit)).setOnClickListener(new View.OnClickListener() { //클릭시 초기화
            @Override
            public void onClick(View v) { //기존테이블 삭제하고 재생성
                sqlDB = myHelper.getWritableDatabase();
                myHelper.onUpgrade(sqlDB, 1, 2);
                sqlDB.close();
            }
        });

        (findViewById(R.id.btnInsert)).setOnClickListener(new View.OnClickListener() { //클릭시 입력
            @Override
            public void onClick(View v) {
                sqlDB = myHelper.getWritableDatabase();
                sqlDB.execSQL("INSERT INTO groupTBL VALUES ('"+ etName.getText().toString()+"', '"+etNumber.getText().toString()+"');");
                //파라미터 값에 작은 따옴표 넣을 것
                sqlDB.close(); //하나의 트렌젝션마다 종료 > 생명 주기를 활용하여 onPause()에서 사용 고려
                Toast.makeText(getApplicationContext(), "입력됨", Toast.LENGTH_SHORT).show(); //메시지 출력
            }
        });

        (findViewById(R.id.btnSelect)).setOnClickListener(new View.OnClickListener() { //클릭시 조회
            @Override
            public void onClick(View v) {
                sqlDB = myHelper.getReadableDatabase(); //DB를 읽어옴
                Cursor cursor; //레코드를 조회한 결과세트
                cursor = sqlDB.rawQuery("SELECT * FROM groupTBL;", null); //모든 결과값을 불러옴

                String strNames = "그룹명" + "\r\n" + "------------------------" + "\r\n";
                String strNumbers = "인원" + "\r\n" + "------------------------" + "\r\n";

                while (cursor.moveToNext()){
                    strNames += cursor.getString(0)+ "\r\n"; //text DB에서 값을 데려옴
                    strNumbers += cursor.getString(1)+ "\r\n";
                }

                etNameResult.setText(strNames); //text출력
                etEtNumberResult.setText(strNumbers);

                cursor.close();
                sqlDB.close();
            }
        });
    }
}
