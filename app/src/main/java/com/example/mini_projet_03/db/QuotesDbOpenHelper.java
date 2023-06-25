package com.example.mini_projet_03.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.mini_projet_03.db.QuotesContract;
import com.example.mini_projet_03.models.Quote;

import java.util.ArrayList;

public class QuotesDbOpenHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Quotes.db";
    private static final String SQL_CREATE_FAVORITE_QUOTES = String.format("CREATE TABLE %s (" +
                    "%s TEXT PRIMARY KEY," +
                    "%s TEXT)",
            QuotesContract.info.TABLE_NAME,
            QuotesContract.info.COLUMN_NAME_QUOTE,
            QuotesContract.info.COLUMN_NAME_AUTHOR);

    private static final String SQL_DELETE_FAVORITEQUOTE = String.format("DROP TABLE IS EXISTS %s",
            QuotesContract.info.TABLE_NAME);

    public QuotesDbOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_QUOTES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_FAVORITEQUOTE);
        onCreate(sqLiteDatabase);
    }

    //region Method to add to Database
    public void add(Quote quote) {
        SQLiteDatabase db = QuotesDbOpenHelper.this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(QuotesContract.info.COLUMN_NAME_QUOTE, quote.getQuote());
        values.put(QuotesContract.info.COLUMN_NAME_AUTHOR, quote.getAuthor());

        db.insert(QuotesContract.info.TABLE_NAME, null, values);
    }
    //endregion

    //region Method to read from Database
    public ArrayList<Quote> getAll() {
        ArrayList<Quote> quotes = new ArrayList<>();
        SQLiteDatabase db = QuotesDbOpenHelper.this.getReadableDatabase();

        String[] projection = {
                QuotesContract.info.COLUMN_NAME_QUOTE,
                QuotesContract.info.COLUMN_NAME_AUTHOR
        };

        Cursor cursor = db.query(
                QuotesContract.info.TABLE_NAME,   // The table to query
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            String quote = cursor.getString(
                    cursor.getColumnIndexOrThrow(QuotesContract.info.COLUMN_NAME_QUOTE));
            String author = cursor.getString(
                    cursor.getColumnIndexOrThrow(QuotesContract.info.COLUMN_NAME_AUTHOR));

            quotes.add(new Quote(quote, author));
        }
        cursor.close();

        return quotes;
    }
    //endregion
}
