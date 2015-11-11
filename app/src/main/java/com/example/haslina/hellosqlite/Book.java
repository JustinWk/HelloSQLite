package com.example.haslina.hellosqlite;

/**
 * Created by haslina on 9/29/2015.
 */
public class Book {
    private int _id;
    private String bookName;
    private String bookAuthor;

    public Book(int _id, String bookName, String bookAuthor) {
        this._id = _id;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
    }

    public Book(String bookName, String bookAuthor) {
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
    }

    public Book() {}

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    @Override
    public String toString() {
        return "Book -id=" + _id + ", title=" + bookName + ", author=" + bookAuthor
                + "\n";
    }
}
