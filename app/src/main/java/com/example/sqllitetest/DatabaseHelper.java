package com.example.sqllitetest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Button;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "mislaos";
    public static final String TABLE_NAME = "tbl_grievance";
    public static final String COL_1 = "Tracking_number";
    public static final String COL_2 = "Reference_number";
    public static final String COL_3 = "Provinceld";
    public static final String COL_4 = "Districtld";
    public static final String COL_5 = "Villageld";
    public static final String COL_6 = "Tracker_date";
    public static final String COL_7 = "SubmitDate";
    private static final int DATABASE_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME+ " (ID INTEGER PRIMARY KEY AUTOINCREMENT,Tracking_number INTGER,Reference_number TEXT,Provinceld TEXT, Districtld TEXT, Villageld TEXT, Tracker_date TEXT, SubmitDate TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion > oldVersion){
            db.execSQL("ALTER TABLE "+ TABLE_NAME+ " ADD COLUMN Tracker_date TEXT ");
            db.execSQL("ALTER TABLE "+TABLE_NAME+ " ADD COLUMN SubmitDate TEXT ");
        }
        //db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        //onCreate(db);

    }

    public boolean insertData(String Tracking_number, String Reference_number, String Provinceld, String Districtld, String Villageld, String Tracker_date, String SubmitDate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, Tracking_number);
        contentValues.put(COL_2, Reference_number);
        contentValues.put(COL_3, Provinceld);
        contentValues.put(COL_4, Districtld);
        contentValues.put(COL_5, Villageld);
        contentValues.put(COL_6, Tracker_date);
        contentValues.put(COL_7, SubmitDate);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor SelectAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ TABLE_NAME, null);
        return res;
    }

    public Cursor search(String search_value){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ TABLE_NAME+ " Where " + COL_1+" =?" ,new String[]{search_value});
        return res;

    }

    public boolean updateData(String Tracking_number, String Reference_number, String Provinceld, String Districtld, String Villageld, String Tracker_date, String SubmitDate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, Tracking_number);
        contentValues.put(COL_2, Reference_number);
        contentValues.put(COL_3, Provinceld);
        contentValues.put(COL_4, Districtld);
        contentValues.put(COL_5, Villageld);
        contentValues.put(COL_6, Tracker_date);
        contentValues.put(COL_7, SubmitDate);
        db.update(TABLE_NAME, contentValues, "Tracking_number = ? ",new String[] {Tracking_number} );
        return true;
    }
    public Integer deleteData(String Tracking_number){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "Tracking_number = ?", new String[] {Tracking_number});
    }


}
