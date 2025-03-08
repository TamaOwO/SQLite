package com.example.sqlitebt;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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
        listView = findViewById(R.id.listview1);
        arrayList = new ArrayList<>();
        adapter = new NotesAdapter(this, R.layout.row_notes, arrayList);
        listView.setAdapter(adapter);


        //Goi ham databaseSQLite
        InitDatabaseSQLite();
        //createDatabaseSQLite();
        databaseSQLite();
    }

    private void createDatabaseSQLite(){
        //Them du lieu vao bang
        databaseHandler.QueryData("INSERT INTO Notes (Id, NameNotes) VALUES (null, 'Ví dụ SQLite 1')");
        databaseHandler.QueryData("INSERT INTO Notes VALUES (null, 'Ví dụ SQLite 2')");
    }

    private void InitDatabaseSQLite(){
        //Khoi tao Database
        databaseHandler = new DatabaseHandler(this, "notes.sqlite", null, 1);
        //Tao bang Notes
        databaseHandler.QueryData("CREATE TABLE IF NOT EXISTS Notes (Id INTEGER PRIMARY KEY AUTOINCREMENT, NameNotes VARCHAR(200))");
    }

    private void databaseSQLite(){
        //Lay du lieu
        Cursor cursor = databaseHandler.GetData("SELECT * FROM Notes");
        while (cursor.moveToNext()){
            // Thêm dữ liệu vào ArrayList
            String name = cursor.getString(1);
            int id = cursor.getInt(0);
            arrayList.add(new NotesModel(id, name));
            Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataSetChanged();
    }

    //goi menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    //Bat su kien
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menuAddNotes){
            DialogThem();
        }
        return super.onOptionsItemSelected(item);
    }

    //Dialog Them
    public void DialogThem(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_notes);

        //Anh xa Dialog
        EditText editText = dialog.findViewById(R.id.editTextName);
        Button btnThem = dialog.findViewById(R.id.btnThem);
        Button btnHuy = dialog.findViewById(R.id.btnHuy);

        //Bat su kien nut them va huy
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText.getText().toString().trim();
                if(name.equals("")){
                    Toast.makeText(MainActivity.this,"Vui lòng nhập tên Notes", Toast.LENGTH_SHORT).show();
                }else{
                    databaseHandler.QueryData("INSERT INTO Notes VALUES(null, '"+name+"')");
                    Toast.makeText(MainActivity.this, "Đã thêm Notes", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    databaseSQLite(); //Goi ham load lai du lieu
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    //Dialog Cập Nhật
    public void DialogCapNhat (String name, int id){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_notes);

        //Anh xa Dialog
        EditText editText = dialog.findViewById(R.id.editTextName1);
        Button btnThem = dialog.findViewById(R.id.btnUpdate);
        Button btnHuy = dialog.findViewById(R.id.btnCancel);
        editText.setText(name);

        //Bat su kien nut them va huy
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText.getText().toString().trim();
                if(name.equals("")){
                    Toast.makeText(MainActivity.this,"Vui lòng nhập tên Notes", Toast.LENGTH_SHORT).show();
                }else{
                    databaseHandler.QueryData("UPDATE Notes SET NameNotes = '"+name+"' WHERE Id = '" + id + "'");
                    Toast.makeText(MainActivity.this, "Đã cập nhật Notes", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    databaseSQLite(); //Goi ham load lai du lieu
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    //Dialog Xoa
    public void DialogDelete(String name, final int id){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có muốn xoá Notes "+name+" này không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }
}