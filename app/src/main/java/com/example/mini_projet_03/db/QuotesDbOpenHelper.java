package com.example.mini_projet_03.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.mini_projet_03.models.Quote;

import java.util.ArrayList;

public class QuotesDbOpenHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Quotes.db";
    public static final String TABLE_NAME = "quotes";
    public static final String COLUMN_NAME_QUOTE = "quote";
    public static final String COLUMN_NAME_AUTHOR = "author";
    private static final String SQL_CREATE_QUOTE = String.format("CREATE TABLE %s (" +
                    "%s TEXT PRIMARY KEY," +
                    "%s TEXT)",
            TABLE_NAME,
            COLUMN_NAME_QUOTE,
            COLUMN_NAME_AUTHOR);

    private static final String SQL_DELETE_QUOTE = String.format("DROP TABLE IS EXISTS %s",
            TABLE_NAME);

    //______________________________________________________________________________________________

    public QuotesDbOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //______________________________________________________________________________________________

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_QUOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_QUOTE);
        onCreate(sqLiteDatabase);
    }

    //______________________________________________________________________________________________

    //region Method to add to Database
    public void add(Quote quote) {
        SQLiteDatabase db = QuotesDbOpenHelper.this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_QUOTE, quote.getQuote());
        values.put(COLUMN_NAME_AUTHOR, quote.getAuthor());

        db.insert(TABLE_NAME, null, values);
    }
    //endregion

    //region Method to update quotes in database
    public void update(Quote originQuote, Quote updatedQuote) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_QUOTE, updatedQuote.getQuote());
        values.put(COLUMN_NAME_AUTHOR, updatedQuote.getAuthor());

        String selection = COLUMN_NAME_QUOTE + " = ?";
        String[] selectionArgs = { originQuote.getQuote() };

        db.update(TABLE_NAME, values, selection, selectionArgs);
    }
    //endregion

    //region Method to read from Database
    public ArrayList<Quote> getAll() {
        ArrayList<Quote> quotes = new ArrayList<>();
        SQLiteDatabase db = QuotesDbOpenHelper.this.getReadableDatabase();

        String[] projection = {
                COLUMN_NAME_QUOTE,
                COLUMN_NAME_AUTHOR
        };

        Cursor cursor = db.query(
                TABLE_NAME,   // The table to query
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            String quote = cursor.getString(
                    cursor.getColumnIndexOrThrow(COLUMN_NAME_QUOTE));
            String author = cursor.getString(
                    cursor.getColumnIndexOrThrow(COLUMN_NAME_AUTHOR));

            quotes.add(new Quote(quote, author));
        }
        cursor.close();

        return quotes;
    }
    //endregion
}
