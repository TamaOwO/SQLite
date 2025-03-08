package com.example.sqlitebt;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sqlitebt.adapter.NotesAdapter;
import com.example.sqlitebt.model.NotesModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHandler databaseHandler;
    ListView listView;
    ArrayList<NotesModel> arrayList;
    NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        listView = findViewById(R.id.listView1);
        arrayList = new ArrayList<>();
        adapter = new NotesAdapter(this, R.layout.row_notes, arrayList);
        listView.setAdapter(adapter);

        //Goi ham databaseSQLite
        InitDatabaseSQLite();
        //CreateDatabseSQLite();
        databaseSQLite();
    }

    private void createDatabaseSQLite(){
        //Them du lieu vao bang
        databaseHandler.QueryData("INSERT INTO Notes VALUE (null, 'Ví dụ SQLite 1')");
        databaseHandler.QueryData("INSERT INTO Notes VALUE (null, 'Ví dụ SQLite 2')");
    }

    private void InitDatabaseSQLite(){
        //Khoi tao Database
        databaseHandler = new DatabaseHandler(this, "notes.sqlite", null, 1);
        //Tao bang Notes
        databaseHandler.QueryData("CREAT TABLE IF NOT EXISTS Notes (Id INTEGER PRIMARY KEY AUTOINCREMENT, NameNotes VARCHAR(200))");
    }

    private void databaseSQLite(){
        //Lay du lieu
        Cursor cursor = databaseHandler.GetData("SELECT * FROM Notes");
        while (cursor.moveToNext()){
            String name = cursor.getString(1);
            Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataSetChanged();
    }
}