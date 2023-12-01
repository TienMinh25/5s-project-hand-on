package com.tienminh.a5s_project_hand_on.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    SQLiteDatabase db;

    public static final String TB_NGUOIDUNG = "NGUOIDUNG"; // Tên bảng 1

    public static final String TB_NGUOIDUNG_ID = "ID";  // Tên cột 1
    public static final String TB_NGUOIDUNG_HOTEN = "HOTEN"; // Tên cột 2
    public static final String TB_NGUOIDUNG_CHUCVU = "CHUCVU"; // Tên cột 3
    public static final String TB_NGUOIDUNG_TENDANGNHAP = "TENDANGNHAP"; // Tên cột 4
    public static final String TB_NGUOIDUNG_MATKHAU = "MATKHAU"; // Tên cột 5
    public static final String TB_NGUOIDUNG_EMAIL = "EMAIL"; // Tên cột 6
    public static final String TB_NGUOIDUNG_SDT = "SDT"; // Tên cột 7
    private static final String DATABASE_NAME = "5S-Project";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tbNGUOIDUNG = "CREATE TABLE " + TB_NGUOIDUNG + " (" +      // Câu lệnh tạo bảng
                TB_NGUOIDUNG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TB_NGUOIDUNG_HOTEN + " TEXT, " +
                TB_NGUOIDUNG_CHUCVU + " TEXT, " +
                TB_NGUOIDUNG_TENDANGNHAP + " TEXT, " +
                TB_NGUOIDUNG_MATKHAU + " TEXT, " +
                TB_NGUOIDUNG_EMAIL + " TEXT, " +
                TB_NGUOIDUNG_SDT + " TEXT)";

        db.execSQL(tbNGUOIDUNG); // Thực thi câu lệnh tạo bảng
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS NGUOIDUNG"); // Xóa bảng nếu tồn tại
        onCreate(db); // Tạo lại bảng
    }

    public SQLiteDatabase open(){
        return this.getWritableDatabase(); // Mở kết nối đến database
    }

    public boolean insertData(String hoten, String chucvu, String tendangnhap, String matkhau, String email, String sdt){
        open(); // Mở kết nối đến database
        ContentValues values = new ContentValues(); // Tạo đối tượng ContentValues để chứa dữ liệu
        values.put(TB_NGUOIDUNG_HOTEN, hoten); // Đưa dữ liệu vào cột 1
        values.put(TB_NGUOIDUNG_CHUCVU, chucvu); // Đưa dữ liệu vào cột 2
        values.put(TB_NGUOIDUNG_TENDANGNHAP, tendangnhap); // Đưa dữ liệu vào cột 3
        values.put(TB_NGUOIDUNG_MATKHAU, matkhau); // Đưa dữ liệu vào cột 4
        values.put(TB_NGUOIDUNG_EMAIL, email); // Đưa dữ liệu vào cột 5
        values.put(TB_NGUOIDUNG_SDT, sdt); // Đưa dữ liệu vào cột 6
        long result = db.insert(TB_NGUOIDUNG, null, values); // Thực hiện câu lệnh insert dữ liệu vào bảng

        if (result == -1) return false; // Nếu kết quả trả về -1 thì thêm thất bại
        else return true; // Ngược lại thêm thành công
    }

    public boolean checkTENDANGNHAP(String tendangnhap){
        open(); // Mở kết nối đến database
        String query = "SELECT * FROM " + TB_NGUOIDUNG + " WHERE " + TB_NGUOIDUNG_TENDANGNHAP + " = '" + tendangnhap + "'"; // Câu lệnh truy vấn
        Cursor cursor = db.rawQuery(query, null); // Thực hiện câu lệnh truy vấn và lưu kết quả vào đối tượng Cursor
        if (cursor.getCount() > 0) return false; // Nếu có kết quả trả về thì trả về false
        else return true; // Ngược lại trả về true
    }

    public boolean checkMATKHAU(String tendangnhap, String matkhau){
        open(); // Mở kết nối đến database
        String query = "SELECT * FROM " + TB_NGUOIDUNG + " WHERE " + TB_NGUOIDUNG_TENDANGNHAP + " = '" + tendangnhap + "' AND " + TB_NGUOIDUNG_MATKHAU + " = '" + matkhau + "'"; // Câu lệnh truy vấn
        Cursor cursor = db.rawQuery(query, null); // Thực hiện câu lệnh truy vấn và lưu kết quả vào đối tượng Cursor
        if (cursor.getCount() > 0) return true; // Nếu có kết quả trả về thì trả về true
        else return false; // Ngược lại trả về false
    }

    //Lấy tên người dùng từ cơ sở dữ liệu
    public String getUserName(String username) {
        open();
        String query = "SELECT " + TB_NGUOIDUNG_HOTEN + " FROM " + TB_NGUOIDUNG +
                " WHERE " + TB_NGUOIDUNG_TENDANGNHAP + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{username});

        String userName = "";

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(TB_NGUOIDUNG_HOTEN);
            if (columnIndex != -1) {
                userName = cursor.getString(columnIndex);
            }
            cursor.close();
        }

        db.close();

        return userName;
    }
}
