package com.tienminh.a5s_project_hand_on.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

import com.tienminh.a5s_project_hand_on.R;
import com.tienminh.a5s_project_hand_on.classes.Room;
import com.tienminh.a5s_project_hand_on.classes.Score;
import com.tienminh.a5s_project_hand_on.classes.User;

import java.util.ArrayList;
import java.util.Vector;

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
                "fullname TEXT NOT NULL," +
                "username TEXT NOT NULL," +
                "password TEXT NOT NULL," +
                "email TEXT NOT NULL," +
                "phone TEXT," +
                "is_admin INTEGER NOT NULL);"
        );

        // Thực hiện tạo bảng khu vực
        db.execSQL("CREATE TABLE IF NOT EXISTS areas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL);"
        );


        db.execSQL("CREATE TABLE IF NOT EXISTS rooms (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "area_id INTEGER REFERENCES areas(id) ON UPDATE CASCADE ON DELETE CASCADE);"
        );

        db.execSQL("CREATE TABLE IF NOT EXISTS descriptions (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT NOT NULL," +
                "content TEXT NOT NULL);"
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

        db.execSQL("INSERT OR IGNORE INTO areas (name)" +
                "VALUES ('KHU VỰC VĂN PHÒNG'),"+
                "('KHU VỰC GIẢNG ĐƯỜNG')," +
                "('KHU VỰC PHÒNG/XƯỞNG THỰC HÀNH')," +
                "('KHU VỰC CHUNG');"
                );

        db.execSQL("INSERT OR IGNORE INTO descriptions(title, content)"+
                "VALUES('Nền sàn', 'Có sạch sẽ và được giữ gìn tốt không? Có rác trên sàn không?')," +
                "('Thùng rác', 'Có sạch sẽ và đặt ở vị trí hợp lý không?')," +
                "('Thùng gạt tàn', 'Có sạch sẽ và đặt ở vị trí hợp lý không?')," +
                "('Tường', 'Có sạch sẽ và được giữ gìn tốt không?')," +
                "('Cửa sổ', 'Có sạch sẽ và được giữ gìn tốt không?')," +
                "('Trần', 'Có sạch sẽ và được giữ gìn tốt không? Có bụi hoặc mạng nhện không?')," +
                "('Đèn', 'Có sạch sẽ, an toàn và được bố trí hợp lý không? Còn sử dụng tốt hay không?')," +
                "('Góc hành lang', 'Có rác hoặc đồ vật nào không cần thiết không?');"
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

                String[] projection = { "id", "fullname", "username", "password", "email", "phone", "is_admin" };
                String selection = "username = ? AND password = ? AND is_admin = ?";
                String[] selectionArgs = { user.getUsername(), user.getPassword(), String.valueOf(user.getIs_admin()) };

                try (Cursor cursor = db.query("users", projection, selection, selectionArgs, null, null, null)) {
                    if (cursor != null && cursor.moveToFirst()) {
                        User newUser = new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6));
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

    public static class ExecuteGetRooms<T> extends AsyncTask<Void, Void, T> {
        private Room room;
        private DatabaseCallbackArray<T> callback;
        private Context context;

        public ExecuteGetRooms(DatabaseCallbackArray<T> callback, Context context, Room room) {
            this.room = room;
            this.callback = callback;
            this.context = context;
        }

        @Override
        protected T doInBackground(Void... params) {
            SQLiteDatabase db = null;
            try {
                db = SQLiteDatabase.openDatabase(context.getDatabasePath(DATABASE_NAME).getPath(), null, SQLiteDatabase.OPEN_READWRITE);

                String[] projection = { "name", "area_id" };
                String selection = "area_id = ?";
                String[] selectionArgs = { String.valueOf(room.getArea_id()) };

                ArrayList<Room> rooms = new ArrayList<>();

                try (Cursor cursor = db.query("rooms", projection, selection, selectionArgs, null, null, null)) {
                    if (cursor != null && cursor.moveToFirst()) {
                        do {
                            // Xử lý dữ liệu từ cursor ở đây
                            String name = cursor.getString(0);
                            int areaId = cursor.getInt(1);

                            rooms.add(new Room(name, areaId));
                            // Thực hiện các thao tác khác với dữ liệu
                        } while (cursor.moveToNext());
                    }
                    return (T)rooms;
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

    public static class ExecuteAddRoom<T> extends AsyncTask<Void, Void, T> {
        private Room room;
        private DatabaseCallbackArray<T> callback;
        private Context context;

        public ExecuteAddRoom(DatabaseCallbackArray<T> callback, Context context, Room room) {
            this.room = room;
            this.callback = callback;
            this.context = context;
        }

        @Override
        protected T doInBackground(Void... params) {
            SQLiteDatabase db = null;
            try {
                db = SQLiteDatabase.openDatabase(context.getDatabasePath(DATABASE_NAME).getPath(), null, SQLiteDatabase.OPEN_READWRITE);

                // Lấy dữ liệu từ đối tượng Room
                String name = room.getName();
                int areaId = room.getArea_id();

                // Tạo ContentValues để chứa dữ liệu muốn chèn
                ContentValues values = new ContentValues();
                values.put("name", name);
                values.put("area_id", areaId);

                // Chèn dữ liệu vào bảng 'rooms'
                long newRowId = db.insert("rooms", null, values);

                if (newRowId != -1) {
                    // Chèn thành công, có thể thực hiện các thao tác khác ở đây nếu cần
                    // Trả về dữ liệu hoặc thông báo chèn thành công nếu cần
                    return (T) "Insert successful";
                } else {
                    // Có lỗi khi chèn dữ liệu
                    return null;
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

    public static class ExecuteMark<T> extends AsyncTask<Void, Void, T> {
        private ArrayList<Score> scores;
        private DatabaseCallback<T> callback;
        private Context context;

        public ExecuteMark(DatabaseCallback<T> callback, Context context, ArrayList<Score> scores) {
            this.callback = callback;
            this.context = context;
            this.scores = scores;
        }

        @Override
        protected T doInBackground(Void... params) {
            SQLiteDatabase db = null;
            try {
                db = SQLiteDatabase.openDatabase(context.getDatabasePath(DATABASE_NAME).getPath(), null, SQLiteDatabase.OPEN_READWRITE);

                // Bắt đầu một giao dịch để tăng hiệu suất khi thêm nhiều bản ghi
                db.beginTransaction();

                // Lặp qua danh sách các điểm và chèn từng bản ghi vào bảng
                for (Score score : scores) {
                    // Tạo ContentValues để chứa dữ liệu muốn chèn
                    ContentValues values = new ContentValues();
                    values.put("user_id", score.getUser_id());
                    values.put("room_id", score.getRoom_id());
                    values.put("description_id", score.getDescription_id());
                    values.put("score", score.getScore());

                    // Chèn dữ liệu vào bảng 'rooms'
                    long newRowId = db.insert("scores", null, values);

                    if (newRowId == -1) {
                        // Có lỗi khi chèn dữ liệu, hủy giao dịch và thoát
                        db.endTransaction();
                        return null;
                    }
                }

                // Giao dịch thành công, đánh dấu là đã thành công
                db.setTransactionSuccessful();
                return (T)new Boolean(true);
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
