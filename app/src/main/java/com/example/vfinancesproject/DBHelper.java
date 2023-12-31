package com.example.vfinancesproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private Context context;

    private static final String DATABASE_NAME = "Account.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_EMAIL + " TEXT PRIMARY KEY, " +
                        COLUMN_PASSWORD + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public Boolean insertData(User u) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EMAIL, u.getUser_email());
        contentValues.put(COLUMN_PASSWORD, u.getUser_password());
        long result = myDB.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
        public Boolean checkEmail(String email){
            SQLiteDatabase myDB = this.getReadableDatabase();
            Cursor cursor = myDB.rawQuery("SELECT * FROM users WHERE user_email = ?", new String[] {email});
            if(cursor.getCount() > 0){
                return true;
            }
            else{
                return false;
            }
        }

    public User checkLogin(String email, String password){
        SQLiteDatabase myDB = this.getReadableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM users WHERE user_email = ?", new String[] {email});
        if(cursor.moveToFirst()){
            String em = cursor.getString(0);
            String pass =  cursor.getString(1);
            if(em.equals(email) && pass.equals(password)) {


                cursor.close();
                myDB.close();
                return new User(em, pass);
            }
        }
        cursor.close();
        myDB.close();
        return null;
    }


    }

