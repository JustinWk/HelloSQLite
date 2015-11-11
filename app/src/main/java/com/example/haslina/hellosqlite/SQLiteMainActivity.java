package com.example.haslina.hellosqlite;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SQLiteMainActivity extends AppCompatActivity {

    TextView tvDetails, tvShowRecords;
    EditText etBNAME, etBAUTHOR;
    Button btADD, btFIND, btDELETE;
    ListView lv;
    List<Book> bookList;
    ArrayAdapter newBookListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_main);
        tvDetails = (TextView) findViewById(R.id.textViewDetails);
        tvShowRecords = (TextView) findViewById(R.id.tvShowRecords);
        etBNAME = (EditText) findViewById(R.id.editTextBName);
        etBAUTHOR = (EditText) findViewById(R.id.editTextBAuthor);
        btADD = (Button) findViewById(R.id.buttonADD);
        btFIND = (Button) findViewById(R.id.buttonFind);
        btDELETE = (Button) findViewById(R.id.buttonDelete);
        lv = (ListView) findViewById(R.id.lvShowRecords);

        BooksDBHandler dbHandler = new BooksDBHandler(this, null,
                null, BooksDBHandler.DATABASE_VERSION);
        dbHandler.addBook(new Book("The Great Gatsby", "F. Scott Fitzgerald"));
        dbHandler.addBook(new Book("Anna Karenina", "Leo Tolstoy"));
        dbHandler.addBook(new Book("The Grapes of Wrath", "John Steinbeck"));
        dbHandler.addBook(new Book("Invisible Man", "Ralph Ellison"));
        dbHandler.addBook(new Book("Gone with the Wind", "Margaret Mitchell"));
        dbHandler.addBook(new Book("Pride and Prejudice", "Jane Austen"));
        dbHandler.addBook(new Book("Sense and Sensibility", "Jane Austen"));
        dbHandler.addBook(new Book("Mansfield Park", "Jane Austen"));
        dbHandler.addBook(new Book("The Color Purple", "Alice Walker"));
        dbHandler.addBook(new Book("The Temple of My Familiar", "Alice Walker"));
        dbHandler.addBook(new Book("The waves", "Virginia Woolf"));
        dbHandler.addBook(new Book("Mrs Dalloway", "Virginia Woolf"));
        dbHandler.addBook(new Book("War and Peace", "Leo Tolstoy"));

    }

    @Override
    protected void onResume() {
        super.onResume();
        final BooksDBHandler dbHandler = new BooksDBHandler(this, null, null, BooksDBHandler.DATABASE_VERSION);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String valueClicked = (String)lv.getItemAtPosition(position);

                Book book = dbHandler.findBookByName(valueClicked);
                if (book != null) {
                    Snackbar.make(findViewById(android.R.id.content), "Book ID-" + String.valueOf(book.get_id()
                            +" & The Author is-"+ book.getBookAuthor() ),Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "No Match Found",Snackbar.LENGTH_SHORT).show();
                }

            }
        });
        refreshList();

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                String valueClicked = (String)lv.getItemAtPosition(position);

                Book book = dbHandler.findBookByName(valueClicked);
                if (book != null) {
                   //startA new activity to update the data
                    Snackbar.make(findViewById(android.R.id.content), "Record Found",Snackbar.LENGTH_SHORT).show();

                    int clickedID = book.get_id();
                    Intent i = new Intent(SQLiteMainActivity.this, EditDialog.class);
                    i.putExtra("CLICKED_ID", clickedID);
                    startActivity(i);

                } else {
                    Snackbar.make(findViewById(android.R.id.content), "No Match Found",Snackbar.LENGTH_SHORT).show();
                }

                return false;
            }
        });
    }

    private void refreshList() {
        // get all books
        BooksDBHandler dbHandler = new BooksDBHandler(this, null,
                null, BooksDBHandler.DATABASE_VERSION);

        bookList = dbHandler.getAllBooks();
        if(bookList.size()>=1){
            tvShowRecords.setText("Records Available");
        }else tvShowRecords.setText("No Records Available");

        List listTitle = new ArrayList();

        for (int i = 0; i < bookList.size(); i++) {
            listTitle.add(i, bookList.get(i).getBookName());
        }
        newBookListAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1 , listTitle);
        lv.setAdapter(newBookListAdapter);
    }

    public void addBook(View v) {
        BooksDBHandler dbHandler = new BooksDBHandler(this, null, null, BooksDBHandler.DATABASE_VERSION);
        Book product = new Book(etBNAME.getText().toString(), etBAUTHOR.getText().toString());
        dbHandler.addBook(product);
        etBNAME.setText("");
        etBAUTHOR.setText("");
        refreshList();
    }

    public void findBook(View v) {
        BooksDBHandler dbHandler = new BooksDBHandler(this, null, null, BooksDBHandler.DATABASE_VERSION);
        Book book =
                dbHandler.findBookByName(etBNAME.getText().toString());
        if (book != null) {
            Snackbar.make(findViewById(android.R.id.content), "Book Found: ID-" + String.valueOf(book.get_id()
                    +" Author-"+ book.getBookAuthor() ),Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(findViewById(android.R.id.content), "No Match Found",Snackbar.LENGTH_SHORT).show();
        }
        refreshList();
    }

    public void deleteBook(View v) {
        BooksDBHandler dbHandler = new BooksDBHandler(this, null,
                null, BooksDBHandler.DATABASE_VERSION);
        boolean result = dbHandler.deleteBook(
                etBNAME.getText().toString());
        if (result) {
            tvDetails.setText("Record Deleted");
            etBNAME.setText("");
            etBAUTHOR.setText("");
        } else
            tvDetails.setText("No Match Found");
        refreshList();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sqlite_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            BooksDBHandler dbHandler = new BooksDBHandler(this, null,
                    null, BooksDBHandler.DATABASE_VERSION);
           dbHandler.clearTable();
            refreshList();
            Toast.makeText(getApplicationContext(),"All records are deleted",Toast.LENGTH_LONG);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
