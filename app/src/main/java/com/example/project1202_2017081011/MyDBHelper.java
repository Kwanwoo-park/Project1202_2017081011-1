package com.example.project1202_2017081011;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {
    public MyDBHelper(Context context) {
        super(context, "groupDB", null, 1);
    } //생성자

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("CREATE TABLE groupTBL (gName CHAR(20) PRIMARY KEY, gNumber INTEGER);");
    } //Table 생성

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) { //onUpgrade에서 전달
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS groupTBL"); //groupTBL이 존재하면 Table 재생성
        onCreate(sqLiteDatabase); //DB를 매개변수로 해서 onCreate 호출
    }


}

//data Validity