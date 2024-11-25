package com.example.orderfoodbtl.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.orderfoodbtl.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "product_db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USERS = "Account";
    private static final String USER_ID = "id";
    private static final String USER_NAME = "name";
    private static final String USER_EMAIL = "email";
    private static final String USER_PASSWORD = "password";

    private static final String TABLE_PRODUCTS = "products";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_RATING = "rating";
    private static final String COLUMN_IMAGE_RES_ID = "imageResId";
    public static final String COLUMN_PRICE = "price";

    private static final String TABLE_CART = "Cart";
    private static final String CART_ID = "id";
    private static final String CART_PRODUCT_ID = "productId";
    private static final String CART_USER_ID = "userId";
    private static final String CART_QUANTITY = "quantity";

    //Favorite Table
    private static final String TABLE_FAVORITE = "Favorite";
    private static final String FAVORITE_ID = "id";
    private static final String FAVORITE_USER_ID = "userId";
    private static final String FAVORITE_PRODUCT_ID = "productId";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Kiểm tra user name
    public boolean checkUserExists(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + USER_NAME + " = ?", new String[]{name});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    //Kiểm tra tài khoản đã tồn tại
    public boolean checkAccountExists(String name, String mk) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + USER_NAME + " = ? AND " + USER_PASSWORD + " = ?", new String[]{name, mk});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    //Lấy mail từ bảng users
    public String getEmailByUserId(int userId) {
        String email = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT email FROM " + TABLE_USERS + " WHERE " + USER_ID + " =?", new String[]{String.valueOf(userId)});
        if (cursor != null && cursor.moveToFirst()) {
            email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
        }
        cursor.close();
        db.close();
        return email;
    }

    public int getUserIdByUserName(String userName) {
        int userId = -1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM " + TABLE_USERS + " WHERE " + USER_NAME + " =?", new String[]{userName});
        if (cursor != null && cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        }
        cursor.close();
        db.close();
        return userId;
    }

    //Lưu thông tin người dùng
    public void saveUserInfo(Context context, int userId, String email, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("user_id", userId); // Lưu user_id
        editor.putString("email", email); // Lưu email
        editor.putString("username", username); // Lưu username
        editor.apply(); // Lưu thay đổi
    }

    //Lấy thông tin
    public int getUserId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_pref", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("user_id", -1); // -1 nếu không tìm thấy
    }

    public String getEmail(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_pref", Context.MODE_PRIVATE);
        return sharedPreferences.getString("email", null); // null nếu không tìm thấy
    }

    public String getUsername(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_pref", Context.MODE_PRIVATE);
        return sharedPreferences.getString("username", null); // null nếu không tìm thấy
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + " ("
                + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_NAME + " TEXT NOT NULL, "
                + USER_EMAIL + " TEXT UNIQUE NOT NULL, "
                + USER_PASSWORD + " TEXT NOT NULL)";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_CATEGORY + " TEXT,"
                + COLUMN_RATING + " REAL,"
                + COLUMN_IMAGE_RES_ID + " INTEGER,"
                + COLUMN_PRICE + " REAL"
                + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);

        String CREATE_CART_TABLE = "CREATE TABLE " + TABLE_CART + " ("
                + CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CART_PRODUCT_ID + " INTEGER, "
                + CART_USER_ID + " INTEGER, "
                + CART_QUANTITY + " INTEGER, "
                + "FOREIGN KEY (" + CART_PRODUCT_ID + ") REFERENCES " + TABLE_PRODUCTS + "(" + COLUMN_ID + "), "
                + "FOREIGN KEY (" + CART_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + USER_ID + "))";
        db.execSQL(CREATE_CART_TABLE);

        String CREATE_FAVORITE_TABLE = "CREATE TABLE " + TABLE_FAVORITE + " ("
                + FAVORITE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FAVORITE_USER_ID + " INTEGER, "
                + FAVORITE_PRODUCT_ID + " INTEGER,"
                + "FOREIGN KEY (" + FAVORITE_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + USER_ID + "), "
                + "FOREIGN KEY (" + FAVORITE_PRODUCT_ID + ") REFERENCES " + TABLE_PRODUCTS + "(" + COLUMN_ID+ "))";
        db.execSQL(CREATE_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE);
        onCreate(db);
    }

    public void addUser(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_NAME, name);
        values.put(USER_EMAIL, email);
        values.put(USER_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, values);
        if (result == -1) {
            Log.e("DBHelper", "Failed to insert user");
        } else {
            Log.d("DBHelper", "User added successfully");
        }
        db.close();
    }

    public long updateUser(int userId,String name, String email, String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(USER_NAME, name);
        cv.put(USER_EMAIL, email);
        cv.put(USER_PASSWORD, pass);

        long result  = db.update(TABLE_USERS,cv,"id = ?", new String[]{String.valueOf(userId)});
        db.close();
        return  result;
    }

    public void addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, product.getName());
        values.put(COLUMN_CATEGORY, product.getCategory());
        values.put(COLUMN_RATING, product.getRating());
        values.put(COLUMN_IMAGE_RES_ID, product.getImageResId());
        values.put(COLUMN_PRICE, product.getPrice());
        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }

    public void addToCart(int productId, int userId, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT quantity FROM " + TABLE_CART +
                        " WHERE userId = ? AND productId = ?",
                new String[]{String.valueOf(userId), String.valueOf(productId)});
        if (cursor.moveToFirst()) {
            int existingQuantity = cursor.getColumnIndex(CART_QUANTITY);
            int newexist = cursor.getInt(existingQuantity);

            ContentValues values = new ContentValues();
            values.put("quantity", newexist + quantity);
            db.update(TABLE_CART, values, "userId = ? AND productId = ?",
                    new String[]{String.valueOf(userId), String.valueOf(productId)});
        } else {
            ContentValues values = new ContentValues();
            values.put(CART_PRODUCT_ID, productId);
            values.put(CART_USER_ID, userId);
            values.put(CART_QUANTITY, quantity);
            db.insert(TABLE_CART, null, values);
        }
        cursor.close();
        db.close();
    }

    public void deleteformCart(int productId, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART, "userId = ? AND productId = ?",
                new String[]{String.valueOf(userId), String.valueOf(productId)});
        db.close();
    }

    public void deleteAllCard(int userId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART, "userId = ?",
                new String[]{String.valueOf(userId)});
        db.close();
    }

    public void IncreaseQuantity(int productId, int userId, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("quantity", quantity + 1);
        db.update(TABLE_CART, values, "userId = ? AND productId = ?",
                new String[]{String.valueOf(userId), String.valueOf(productId)});
        db.close();
    }

    public void DecreaseQuantity(int productId, int userId, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("quantity", quantity - 1);
        db.update(TABLE_CART, values, "userId = ? AND productId = ?",
                new String[]{String.valueOf(userId), String.valueOf(productId)});
        db.close();
    }

    public double SubTotalPrice(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT SUM(p.price * c.quantity) AS subtotal FROM " + TABLE_PRODUCTS + " p " +
                "INNER JOIN " + TABLE_CART + " c ON p.id = c.productId " +
                "WHERE c.userId = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
        double SubTotal = 0;
        if (cursor != null && cursor.moveToFirst()) {
            SubTotal = cursor.getDouble(cursor.getColumnIndexOrThrow("subtotal"));
        }
        cursor.close();
        db.close();
        return SubTotal;
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS, null);

        if (cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(COLUMN_ID);
                int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
                int categoryIndex = cursor.getColumnIndex(COLUMN_CATEGORY);
                int ratingIndex = cursor.getColumnIndex(COLUMN_RATING);
                int imageResIdIndex = cursor.getColumnIndex(COLUMN_IMAGE_RES_ID);
                int PriceIndex = cursor.getColumnIndex(COLUMN_PRICE);

                if (nameIndex != -1 && categoryIndex != -1 && ratingIndex != -1 && imageResIdIndex != -1) {
                    Product product = new Product(
                            cursor.getInt(idIndex),
                            cursor.getString(nameIndex),
                            cursor.getInt(imageResIdIndex),
                            cursor.getString(categoryIndex),
                            cursor.getDouble(ratingIndex),
                            cursor.getDouble(PriceIndex)
                    );
                    productList.add(product);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return productList;
    }

    public List<Product> getProductsByCategory(String category) {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS + " WHERE category = '" + category + "'", null);

        if (cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(COLUMN_ID);
                int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
                int categoryIndex = cursor.getColumnIndex(COLUMN_CATEGORY);
                int ratingIndex = cursor.getColumnIndex(COLUMN_RATING);
                int imageResIdIndex = cursor.getColumnIndex(COLUMN_IMAGE_RES_ID);
                int PriceIndex = cursor.getColumnIndex(COLUMN_PRICE);

                if (nameIndex != -1 && categoryIndex != -1 && ratingIndex != -1 && imageResIdIndex != -1) {
                    Product product = new Product(
                            cursor.getInt(idIndex),
                            cursor.getString(nameIndex),
                            cursor.getInt(imageResIdIndex),
                            cursor.getString(categoryIndex),
                            cursor.getDouble(ratingIndex),
                            cursor.getDouble(PriceIndex)
                    );
                    productList.add(product);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return productList;
    }

    public List<Product> getProductsByName(String name) {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_PRODUCTS + " WHERE name LIKE ? ",
                new String[]{"%" + name + "%"}
        );


        if (cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(COLUMN_ID);
                int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
                int categoryIndex = cursor.getColumnIndex(COLUMN_CATEGORY);
                int ratingIndex = cursor.getColumnIndex(COLUMN_RATING);
                int imageResIdIndex = cursor.getColumnIndex(COLUMN_IMAGE_RES_ID);
                int PriceIndex = cursor.getColumnIndex(COLUMN_PRICE);

                if (nameIndex != -1 && categoryIndex != -1 && ratingIndex != -1 && imageResIdIndex != -1) {
                    Product product = new Product(
                            cursor.getInt(idIndex),
                            cursor.getString(nameIndex),
                            cursor.getInt(imageResIdIndex),
                            cursor.getString(categoryIndex),
                            cursor.getDouble(ratingIndex),
                            cursor.getDouble(PriceIndex)
                    );
                    productList.add(product);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return productList;
    }

    public List<Product> getAllCart(int userID) {
        List<Product> cartItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT p.*, c.quantity FROM " + TABLE_PRODUCTS + " p " +
                "INNER JOIN " + TABLE_CART + " c ON p.id = c.productId " +
                "WHERE c.userId = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userID)});

        if (cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(COLUMN_ID);
                int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
                int categoryIndex = cursor.getColumnIndex(COLUMN_CATEGORY);
                int ratingIndex = cursor.getColumnIndex(COLUMN_RATING);
                int imageResIdIndex = cursor.getColumnIndex(COLUMN_IMAGE_RES_ID);
                int priceIndex = cursor.getColumnIndex(COLUMN_PRICE);
                int quantityIndex = cursor.getColumnIndex(CART_QUANTITY);

                if (idIndex != -1 && quantityIndex != -1) {
                    Product product = new Product(
                            cursor.getInt(idIndex),
                            cursor.getString(nameIndex),
                            cursor.getInt(imageResIdIndex),
                            cursor.getString(categoryIndex),
                            cursor.getDouble(ratingIndex),
                            cursor.getDouble(priceIndex),
                            cursor.getInt(quantityIndex)
                    );
                    cartItems.add(product);
                }
            } while (cursor.moveToNext());
        } else {
            Log.d("Cart Query", "No items found for userID: " + userID);
        }

        cursor.close();
        db.close();
        return cartItems;
    }

    public void addToFavorite(int productId, int userId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(FAVORITE_PRODUCT_ID, productId);
        cv.put(FAVORITE_USER_ID, userId);

        db.insert(TABLE_FAVORITE, null, cv);
    }

    public boolean getFavoriteById(int productId, int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean isFavorite = false;

        // Câu lệnh truy vấn
        String query = "SELECT * FROM " + TABLE_FAVORITE +
                " WHERE " + FAVORITE_PRODUCT_ID + " = ? AND " + FAVORITE_USER_ID + " = ?";

        // Sử dụng cursor để thực thi truy vấn
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(productId), String.valueOf(userId)});

        // Kiểm tra nếu cursor có dữ liệu thì sản phẩm này là yêu thích
        if (cursor != null && cursor.moveToFirst()) {
            isFavorite = true;
        }

        // Đóng cursor và database sau khi sử dụng
        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return isFavorite;
    }

    public int getIdFavoriteByPIUI(int productId, int userId) {
        int favoriteID = -1; // Giá trị mặc định nếu không tìm thấy
        SQLiteDatabase db = this.getReadableDatabase();

        // Câu lệnh truy vấn
        String query = "SELECT id FROM " + TABLE_FAVORITE +
                " WHERE " + FAVORITE_PRODUCT_ID + " = ? AND " + FAVORITE_USER_ID + " = ?";

        // Sử dụng cursor để thực thi truy vấn
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(productId), String.valueOf(userId)});

        // Kiểm tra nếu cursor có dữ liệu
        if (cursor != null && cursor.moveToFirst()) {
            favoriteID = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        }

        // Đóng cursor và database
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        Log.e("TAG", "getIdFavoriteByPIUI: Favorite ID = " + favoriteID);
        return favoriteID;
    }

    public void deleteFavorite(String id){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_FAVORITE,  "id=?", new String[]{id});
        db.close();
    }

    public List<Product> getAllProductsFromFavorite(int userID){
        List<Product> listFavorite = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT p.* FROM " + TABLE_PRODUCTS + " p " +
                "INNER JOIN " + TABLE_FAVORITE + " f ON p.id = f.productId " +
                "WHERE f.userId = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userID)});

        if (cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(COLUMN_ID);
                int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
                int categoryIndex = cursor.getColumnIndex(COLUMN_CATEGORY);
                int ratingIndex = cursor.getColumnIndex(COLUMN_RATING);
                int imageResIdIndex = cursor.getColumnIndex(COLUMN_IMAGE_RES_ID);
                int PriceIndex = cursor.getColumnIndex(COLUMN_PRICE);
                if (nameIndex != -1 && categoryIndex != -1 && ratingIndex != -1 && imageResIdIndex != -1) {
                    Product product = new Product(
                            cursor.getInt(idIndex),
                            cursor.getString(nameIndex),
                            cursor.getInt(imageResIdIndex),
                            cursor.getString(categoryIndex),
                            cursor.getDouble(ratingIndex),
                            cursor.getDouble(PriceIndex)
                    );
                    listFavorite.add(product);
                }
            } while (cursor.moveToNext());
        } else {
            Log.d("heh", "No favorites found for userID: " + userID);
        }
        cursor.close();
        db.close();
        return listFavorite;
    }
}
