package com.example.haslina.hellosqlite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by haslina on 10/1/2015.
 */


public class EditDialog extends Activity {
    EditText etBNAME, etBAUTHOR;
    Button btEdit;
    Book selectedBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);
        etBNAME = (EditText) findViewById(R.id.editTextBName_EDITAC);
        etBAUTHOR = (EditText) findViewById(R.id.editTextBAuthor_EDITAC);
        btEdit = (Button) findViewById(R.id.buttonEDIT_EDITAC);

        final BooksDBHandler dbHandler = new BooksDBHandler(EditDialog.this, null, null, BooksDBHandler.DATABASE_VERSION);
        Intent i = getIntent();
        int id = i.getIntExtra("CLICKED_ID",0);
        selectedBook = dbHandler.findBookByID(id);


        etBNAME.setText(selectedBook.getBookName());
        etBAUTHOR.setText(selectedBook.getBookAuthor());



        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "This book is updated.", Toast.LENGTH_SHORT).show();
                selectedBook.setBookName(etBNAME.getText().toString());
                selectedBook.setBookAuthor(etBAUTHOR.getText().toString());

                // update book with changes
                dbHandler.updateBook(selectedBook);
                finish();
            }
        });


    }

}
