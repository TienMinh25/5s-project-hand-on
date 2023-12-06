package com.tienminh.a5s_project_hand_on.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

import com.tienminh.a5s_project_hand_on.classes.User;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dbAppAndroid.db";
    private static final int DATABASE_VERSION = 1;
    private static final String INITIALIZE_SCRIPT_NAME = "initialize.sqlite";

    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Thực hiện tạo bảng và thêm dữ liệu ban đầu
        // Thực hiện tạo bảng người dùng
        db.execSQL("CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "fullname VARCHAR(50) NOT NULL," +
                "username VARCHAR(50) NOT NULL," +
                "password VARCHAR(50) NOT NULL," +
                "email VARCHAR(50) NOT NULL," +
                "phone VARCHAR(50)," +
                "is_admin INTEGER NOT NULL);"
        );

        // Thực hiện tạo bảng khu vực
        db.execSQL("CREATE TABLE IF NOT EXISTS areas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR(50) NOT NULL);"
        );


        db.execSQL("CREATE TABLE IF NOT EXISTS rooms (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR(50) NOT NULL," +
                "location VARCHAR(50) NOT NULL," +
                "area_id INTEGER REFERENCES areas(id) ON UPDATE CASCADE ON DELETE CASCADE);"
        );

        db.execSQL("CREATE TABLE IF NOT EXISTS criteria ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "name VARCHAR(50) NOT NULL);"
        );

        db.execSQL("CREATE TABLE IF NOT EXISTS descriptions (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "content VARCHAR(100) NOT NULL," +
                "criterion_id INTEGER REFERENCES criteria(id) ON UPDATE CASCADE ON DELETE CASCADE);"
        );

        db.execSQL("CREATE TABLE IF NOT EXISTS scores (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER REFERENCES users(id) ON UPDATE CASCADE ON DELETE CASCADE," +
                "room_id INTEGER REFERENCES rooms(id) ON UPDATE CASCADE ON DELETE CASCADE," +
                "description_id INTEGER REFERENCES descriptions(id) ON UPDATE CASCADE ON DELETE CASCADE," +
                "score INTEGER CHECK (score >= 0 AND score <= 4));"
        );

        db.execSQL("INSERT OR IGNORE INTO users(fullname, username, password, email, phone, is_admin)" +
                "VALUES ('admin', 'admin', 'admin', 'admin@admin.com', '0000.000.000', 1);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xử lý cập nhật database nếu cần
    }

    // Async Task kiểm tra đăng ký
    public static class ExecuteQueryTaskCheckUserExists<T> extends AsyncTask<Void, Void, T> {
        private User user;
        private DatabaseCallback<T> callback;
        private Context context;

        public ExecuteQueryTaskCheckUserExists(DatabaseCallback<T> callback, Context context, User user) {
            this.user = user;
            this.callback = callback;
            this.context = context;
        }

        @Override
        protected T doInBackground(Void... params) {
            SQLiteDatabase db = null;
            try {
                db = SQLiteDatabase.openDatabase(context.getDatabasePath(DATABASE_NAME).getPath(), null, SQLiteDatabase.OPEN_READWRITE);

                String[] projection = { "fullname", "username", "password", "email", "phone", "is_admin" };
                String selection = "username = ? AND is_admin = ?";
                String[] selectionArgs = { user.getUsername(), String.valueOf(user.getIs_admin()) };

                try (Cursor cursor = db.query("users", projection, selection, selectionArgs, null, null, null)) {
                    if (cursor != null) {
                        boolean newCheck = (cursor.getCount() != 0);
                        Boolean checkAdminExists = new Boolean(newCheck);
                        return (T)checkAdminExists;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (db != null && db.isOpen()) {
                    db.close();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(T result) {
            callback.onTaskComplete(result);
        }
    }


    // AsyncTask để thực hiện kiểm tra thông tin đăng nhập
    public static class ExecuteQueryTaskCheckUserSignIn<T> extends AsyncTask<Void, Void, T> {
        private User user;
        private DatabaseCallback<T> callback;
        private Context context;

        // truyen username, password duoi dang mang String[] parameters
        public ExecuteQueryTaskCheckUserSignIn(DatabaseCallback<T> callback, Context context, User user) {
            this.user = user;
            this.callback = callback;
            this.context = context;
        }

        @Override
        protected T doInBackground(Void... params) {
            SQLiteDatabase db = null;
            try {
                db = SQLiteDatabase.openDatabase(context.getDatabasePath(DATABASE_NAME).getPath(), null, SQLiteDatabase.OPEN_READWRITE);

                String[] projection = { "fullname", "username", "password", "email", "phone", "is_admin" };
                String selection = "username = ? AND password = ? AND is_admin = ?";
                String[] selectionArgs = { user.getUsername(), user.getPassword(), String.valueOf(user.getIs_admin()) };

                try (Cursor cursor = db.query("users", projection, selection, selectionArgs, null, null, null)) {
                    if (cursor != null && cursor.moveToFirst()) {
                        User newUser = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5));
                        return (T) newUser;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (db != null && db.isOpen()) {
                    db.close();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(T result) {
            callback.onTaskComplete(result);
        }
    }

    // AsyncTask check admin
    public static class ExecuteQueryCheckAdmin<T> extends AsyncTask<Void, Void, T> {
        private User user;
        private DatabaseCallback<T> callback;
        private Context context;

        // truyen username, password vao check admin
        public ExecuteQueryCheckAdmin(DatabaseCallback<T> callback, Context context, User user) {
            this.user = user;
            this.callback = callback;
            this.context = context;
        }

        @Override
        protected T doInBackground(Void... params) {
            SQLiteDatabase db = null;
            try {
                db = SQLiteDatabase.openDatabase(context.getDatabasePath(DATABASE_NAME).getPath(), null, SQLiteDatabase.OPEN_READWRITE);

                String[] projection = { "fullname", "username", "password", "email", "phone", "is_admin" };
                String selection = "username = ? AND password = ? AND is_admin = ?";
                String[] selectionArgs = { user.getUsername(), user.getPassword(), String.valueOf(user.getIs_admin()) };

                try (Cursor cursor = db.query("users", projection, selection, selectionArgs, null, null, null)) {
                    if (cursor != null) {
                        boolean newCheck = (cursor.getCount() != 0);
                        Boolean checkAdminExists = new Boolean(newCheck);
                        return (T)checkAdminExists;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (db != null && db.isOpen()) {
                    db.close();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(T result) {
            callback.onTaskComplete(result);
        }
    }

    // Async Task đăng ký user
    public static class ExecuteQueryInsertUser<T> extends AsyncTask<Void, Void, T> {
        private User user;
        private DatabaseCallback<T> callback;
        private Context context;

        // truyen username, password vao check admin
        public ExecuteQueryInsertUser(DatabaseCallback<T> callback, Context context, User user) {
            this.user = user;
            this.callback = callback;
            this.context = context;
        }

        @Override
        protected T doInBackground(Void... params) {
            SQLiteDatabase db = null;
            try {
                db = SQLiteDatabase.openDatabase(context.getDatabasePath(DATABASE_NAME).getPath(), null, SQLiteDatabase.OPEN_READWRITE);

                ContentValues values = new ContentValues();
                values.put("fullname", user.getFullname());
                values.put("username", user.getUsername());
                values.put("password", user.getPassword());
                values.put("email", user.getEmail());
                values.put("phone", user.getPhone());
                values.put("is_admin", user.getIs_admin());

                Log.d("INSERT", "INSERT");
                long newRowId = db.insert("users", null, values);
                if (newRowId != -1) {
                    return (T)new Boolean(true);
                }
                else {
                    return (T)new Boolean(false);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (db != null && db.isOpen()) {
                    db.close();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(T result) {
            callback.onTaskComplete(result);
        }
    }
}
