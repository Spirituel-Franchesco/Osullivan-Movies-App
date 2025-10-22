package com.example.osullivanmoviesapp.Modele;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "email TEXT, " +
                "password TEXT, " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(createTable);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(sqLiteDatabase);
    }

    // Inscrire un nouvel utilisateur
    public boolean registerUser(String name, String email, String password) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        // Vérifie si l'utilisateur existe déjà
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE email = ?", new String[]{email});
        if (cursor.getCount() > 0) {
            cursor.close();
            return false; // déjà existant
        }

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);

        long result = sqLiteDatabase.insert(TABLE_USERS, null, values);
        cursor.close();
        return result != -1;
    }

    // Vérifie les identifiants de connexion
    public boolean checkUser(String name, String password) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT * FROM " + TABLE_USERS + " WHERE name = ? AND password = ?",
                new String[]{name, password}
        );
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Vérifie si le nom existe
    public boolean checkUserExists(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE name = ?", new String[]{name});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public String getLastRegisteredUser() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String name = "";

        try {
            Cursor cursor = sqLiteDatabase.rawQuery(
                    "SELECT name FROM " + TABLE_USERS + " ORDER BY datetime(created_at) DESC LIMIT 1",
                    null
            );

            if (cursor != null && cursor.moveToFirst()) {
                name = cursor.getString(0);
            }

            if (cursor != null) cursor.close();
        } catch (Exception e) {
            e.printStackTrace(); // juste pour debug
        }
        return name;
    }
}
