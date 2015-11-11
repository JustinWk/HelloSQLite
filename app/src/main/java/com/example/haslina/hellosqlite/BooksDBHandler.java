package com.example.haslina.hellosqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by haslina on 9/29/2015.
 */
public class BooksDBHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BOOKSdb.db";
    public static final String DATABASE_TABLE_BOOKS = "books";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_BOOKNAME = "book_name";
    public static final String COLUMN_AUTHORNAME = "book_author";
    public static final String CREATE_BOOKS_TABLE = "CREATE TABLE " +
            DATABASE_TABLE_BOOKS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_BOOKNAME
            + " TEXT," + COLUMN_AUTHORNAME + " TEXT" + ")";
    private static final String TAG = "BooksDBHandlerCLASS";
    private static final String[] COLUMNS = {COLUMN_ID, COLUMN_BOOKNAME, COLUMN_AUTHORNAME};


    public BooksDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        Log.e(TAG, "Constructor Called");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOKS_TABLE);
        Log.e(TAG, "onCreate Called");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e(TAG, "onUpgrade Called");
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_BOOKS);
        onCreate(db);
    }

    //ADD Handler Methods for ADDING, DELETING, QUERYING ETC.
    public void addBook(Book book) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOKNAME, book.getBookName());
        values.put(COLUMN_AUTHORNAME, book.getBookAuthor());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(DATABASE_TABLE_BOOKS, null, values);

        Log.e(TAG, "addBook - Book Added");

        db.close();
    }

    public Book findBookByName(String bookName) {
        String query = "Select * FROM " + DATABASE_TABLE_BOOKS + " WHERE " + COLUMN_BOOKNAME + " =  \"" + bookName + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Book book = new Book();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            book.set_id(Integer.parseInt(cursor.getString(0)));
            book.setBookName(cursor.getString(1));
            book.setBookAuthor(cursor.getString(2));
            cursor.close();
        } else {
            book = null;
        }
        db.close();
        return book;
    }

    public Book findBookByID(int bookID) {
        String query = "Select * FROM " + DATABASE_TABLE_BOOKS + " WHERE " + COLUMN_ID + " =  \"" + bookID + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Book book = new Book();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            book.set_id(Integer.parseInt(cursor.getString(0)));
            book.setBookName(cursor.getString(1));
            book.setBookAuthor(cursor.getString(2));
            cursor.close();
        } else {
            book = null;
        }
        db.close();
        return book;
    }

    public boolean deleteBook(String bookName) {

        boolean result = false;

        String query = "Select * FROM " + DATABASE_TABLE_BOOKS + " WHERE " + COLUMN_BOOKNAME + " =  \"" + bookName + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Book book = new Book();

        if (cursor.moveToFirst()) {
            book.set_id(Integer.parseInt(cursor.getString(0)));
            db.delete(DATABASE_TABLE_BOOKS, COLUMN_ID + " = ?",
                    new String[]{String.valueOf(book.get_id())});
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }


    public List<Book> getAllBooks() {
        List<Book> books = new LinkedList<Book>();

        // select book query
        String query = "SELECT  * FROM " + DATABASE_TABLE_BOOKS;

        // get reference of the BookDB database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        Book book = null;
        if (cursor.moveToFirst()) {
            do {
                book = new Book();
                book.set_id(Integer.parseInt(cursor.getString(0)));
                book.setBookName(cursor.getString(1));
                book.setBookAuthor(cursor.getString(2));

                // Add book to books
                books.add(book);
            } while (cursor.moveToNext());
        }
        return books;
    }

    public int updateBook(Book book) {

        // get reference of the BookDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // make values to be inserted
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOKNAME, book.getBookName()); // get title
        values.put(COLUMN_AUTHORNAME, book.getBookAuthor()); // get author

        // update
        int i = db.update(DATABASE_TABLE_BOOKS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(book.get_id())});

        db.close();
        return i;
    }

    public Book readBook(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DATABASE_TABLE_BOOKS, // a. table
                COLUMNS, " id = ?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Book book = new Book();
        book.set_id(Integer.parseInt(cursor.getString(0)));
        book.setBookName(cursor.getString(1));
        book.setBookAuthor(cursor.getString(2));

        return book;
    }

    public void clearTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE_BOOKS, null, null);
    }

    public void deleteAllBooks() {
        Log.e(TAG, "deleteAll Called");
        // get reference of the BookDB database
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_BOOKS);
        onCreate(db);
    }
}
