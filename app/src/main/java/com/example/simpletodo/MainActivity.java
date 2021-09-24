package com.example.simpletodo;

import static android.os.FileUtils.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.FileUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.nio.charset.Charset;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> items = new ArrayList<>(readLines(getDataFile(), Charset.defaultCharset()));

    Button btnAdd;
    EditText etItem;
    RecyclerView rvItems;
    ItemsAdapter itemsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        etItem = findViewById(R.id.etItem);
        rvItems = findViewById(R.id.rvItems);


        BreakIterator eItem = null;
        eItem.setText("Im doing this from java!");

        loadItems();

        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener() {

            @Override
            public void onItemLongClicked(int position) {
                // Delete the item from the model
                items.remove(position);
                //Notify the adapter
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(),"Item was removed", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };
        ItemsAdapter itemsAdapter = new ItemsAdapter(items, onLongClickListener);
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String todoItem = etItem.getText().toString();
                // Add item to the model
                items.add(todoItem);
                // Notify adapter that an item is inserted
                itemsAdapter.notifyItemInserted(items.size()-1);
                etItem.setText("");
                Toast.makeText(getApplicationContext(),"Item was added", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });

    }

    private File getDataFile() {
        return new File(getFilesDir(),"data.txt");
    }

    // This function will load items by reading every line of the data file
    private void loadItems() {

            items = new ArrayList<>(readLines(getDataFile(), Charset.defaultCharset()));

        }



    // This function saves items by writing them into the data file
    private void saveItems() {
        writeLines(getDataFile(), items);
    }

    private void writeLines(File dataFile, List<String> items) {
    }
}