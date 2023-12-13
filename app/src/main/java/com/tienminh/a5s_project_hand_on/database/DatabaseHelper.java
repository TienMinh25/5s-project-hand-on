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

        db.execSQL("CREATE TABLE IF NOT EXISTS criteria ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "name TEXT NOT NULL);"
        );

        db.execSQL("CREATE TABLE IF NOT EXISTS descriptions (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT NOT NULL," +
                "content TEXT NOT NULL," +
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

        db.execSQL("INSERT OR IGNORE INTO areas (name)" +
                "VALUES ('KHU VỰC VĂN PHÒNG'),"+
                "('KHU VỰC GIẢNG ĐƯỜNG')," +
                "('KHU VỰC PHÒNG/XƯỞNG THỰC HÀNH')," +
                "('KHU VỰC CHUNG');"
                );

        db.execSQL("INSERT OR IGNORE INTO criteria(name)" +
                "VALUES ('Facility & Equipment Maintenance')," +
                "('Lưu giữ và kiểm soát tài liệu')," +
                "('Kiểm soát công việc')," +
                "('Seiri\n' + '(Sàng lọc)')," +
                "('Seiton\n' + '(Sắp xếp)')," +
                "('Seiso\n' + '(Sạch sẽ)')," +
                "('Seiketsu\n' + '(Săn sóc)')," +
                "('Shitsuke\n' + '(Sẵn sàng)')"
                );

        db.execSQL("INSERT OR IGNORE INTO descriptions(title, content, criterion_id)"+
                "VALUES('Bàn/Ghế', 'Có sạch sẽ và được sắp xếp gọn gàng không?' , 1)," +
                "('Văn phòng phẩm', 'Có sạch sẽ và đặt ở vị trí hợp lý không? ', 1)," +
                "('Máy tính, máy in, điện thoại', 'Có sạch sẽ và được giữ gìn tốt không?', 1)," +
                "('Tủ điện, dây điện, dây mạng và các thiết bị khác', 'Có sạch sẽ và được giữ gìn tốt không? Có được sắp xếp phù hợp để đảm bảo an toàn không? ', 1)," +
                "('Đèn', 'Có sạch sẽ, an toàn và được bố trí hợp lý không? Còn sử dụng tốt hay không?  ', 1)," +
                "('Điều hòa, quạt', 'Có sạch sẽ, an toàn và được bố trí hợp lý không? Còn sử dụng tốt hay không?  ', 1)," +
                "('Tường', 'Có sạch sẽ và được giữ gìn tốt không?', 1), " +
                "('Cửa sổ', 'Có sạch sẽ và được giữ gìn tốt không?', 1)," +
                "('Sàn nhà', 'Có sạch sẽ và được giữ gìn tốt không? Có rác trên sàn không? ', 1), " +
                "('Trần', 'Có sạch sẽ và được giữ gìn tốt không? Có bụi hoặc mạng nhện không? ', 1), " +
                "('Thùng rác', 'Có sạch sẽ và đặt ở vị trí hợp lý không? ', 1)," +
                "('Ấm chén', 'Có sạch sẽ và được sắp xếp gọn gàng không?', 1), " +
                "('Chổi, hót rác, cây lau nhà', 'Có sạch sẽ và đặt ở vị trí hợp lý không? ', 1), " +
                "('Hành lang', 'Có sạch sẽ và giao người chịu trách nhiệm vệ sinh không?', 1), " +
                "('Các góc phòng hoặc nhà kho', 'Có đồ vật nào không cần thiết không?', 1), " +
                "('Hành xử', 'Có chào hỏi người đến thăm quan/đánh giá không?', 1)," +
                "('Tài liệu/Bàn', 'Tài liệu có xếp thành đống trên bàn không?', 2)," +
                "('Tài liệu/Bàn', 'Tài liệu có được xếp vào tủ tài liệu không?', 2)," +
                "('Các cặp/ngăn/giá/tủ tài liệu', 'Các cặp tài liệu có nhãn tên loại tài liệu, người chịu trách nhiệm và thời hạn lưu trữ hay không?', 2)," +
                "('Các cặp/ngăn/giá/tủ tài liệu', 'Tài liệu có bị bụi không?', 2), " +
                "('Kiểm soát tiến độ', 'Kế hoạch công việc và bảng kiểm tra công việc có được trưng bày và cập nhật không? ', 3), " +
                "('Điểm danh', 'Trong trường hợp có nhân viên nào vắng hoặc đi ra ngoài, có thông báo trên bảng không?', 3), " +
                "('Bảng thông tin, lịch làm việc, lịch và các tài liệu trưng bày khác', 'Có sạch sẽ và thường xuyên được cập nhật không?', 3), " +
                "('Trang phục', 'Có mặc trang phục và vẻ ngoài phù hợp hay không?', 3), " +
                "('Thẻ', 'Tất cả nhân viên có đeo thẻ trong giờ làm việc hay không?', 3)," +
                "('Tiết kiệm điện năng', 'Có tắt điện ở những vị trí hoặc máy móc không dùng đến hay không?', 3), " +
                "('1', 'Không có vật dụng, vật tư thừa hoặc không cần thiết ở nơi làm việc', 4), " +
                "('2', 'Nếu có vật dụng thừa, mọi người đều biết tại sao nó lại ở đó', 4)," +
                "('3', 'Tất cả các lối đi và vị trí làm việc đều được mọi người nhận biết rõ', 4), "+
                "('4', 'Không có bản tin, thông báo lỗi thời ở trên tường hoặc trên bảng', 4), " +
                "('5', 'Định rõ nơi cất vật liệu, phụ tùng và dụng cụ (có dán nhãn, danh mục đồ, v.v.)', 5), " +
                "('6', 'Tất cả các lối đi và vị trí làm việc đều được mọi người nhận biết rõ', 5), " +
                "('7', 'Vật liệu, phụ tùng và dụng cụ được trả lại đúng vị trí sau khi dùng', 5)," +
                "('8', 'Các vị trí làm việc được bố trí hợp lý và thuận tiện', 5), " +
                "('9', 'Các bàn, giá và dụng cụ vệ sinh được bố trí hợp lý ở vị trí cố định và được ghi nhãn', 5), " +
                "('10', 'Các cửa sổ có sạch sẽ không?', 6), "+
                "('11', 'Các thiết bị máy móc có sạch và được bảo quản tốt không?', 6)," +
                "('12', 'Sàn nhà có sạch không?', 6), " +
                "('13', 'Có giao người chịu trách nhiệm tại các khu vực hoặc các thiết bị không?', 6), " +
                "('14', 'Có phân loại những đồ có thể tái chế được (vd như đồ kim loại, chai, thùng rỗng,v.v) và không tái chế được không?', 6), "+
                "('15', 'Vị trí làm việc có luôn được giữ sạch sẽ không?', 7), " +
                "('16', 'Các máy móc thiết bị có được kiểm tra hàng ngày hoặc định kỳ theo danh mục kiểm tra không?', 7), " +
                "('17', 'Có lịch vệ sinh thường xuyên không?Tất cả mọi người có đều biết lịch vệ sinh này không?', 7), " +
                "('18', 'Mọi người đều mặc đúng đồng phục, biển tên, đội mũ và giày an toàn', 8), "+
                "('19', 'Xác định rõ và giữ vệ sinh khu hút thuốc, khu vực nghỉ, khu ăn uống', 8), " +
                "('20', 'Lưu trữ tài liệu đầy đủ với các thông tin cần thiết', 8);"
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
}
