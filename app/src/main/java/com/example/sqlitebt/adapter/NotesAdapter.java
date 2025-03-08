package com.example.sqlitebt.adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sqlitebt.MainActivity;
import com.example.sqlitebt.R;
import com.example.sqlitebt.model.NotesModel;

import java.util.List;

public class NotesAdapter extends BaseAdapter {

    private MainActivity context;
    private int layout;
    private List<NotesModel> noteList;

    public NotesAdapter(MainActivity context, int layout, List<NotesModel> noteList) {
        this.context = context;
        this.layout = layout;
        this.noteList = noteList;
    }

    @Override
    public int getCount() {
        return noteList.size();
    }

    //ViewHolder
    private class ViewHolder{
        TextView textViewNote;
        ImageView imageViewAdd;
        ImageView imageViewEdit;
        ImageView imageViewDelete;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Goi Viewholder
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            viewHolder.textViewNote = (TextView) convertView.findViewById(R.id.textViewNameNote);
            viewHolder.imageViewEdit = (ImageView) convertView.findViewById(R.id.imageViewEdit);
            viewHolder.imageViewDelete = (ImageView) convertView.findViewById(R.id.imageViewDelete);
            viewHolder.imageViewAdd = (ImageView) convertView.findViewById(R.id.imageViewAdd);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //Lay gia tri
        final NotesModel notes = noteList.get(position);
        viewHolder.textViewNote.setText(notes.getNameNote());

        //Bat su kien nut add
        viewHolder.imageViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Goi Dialog Trong MainActivity
                context.DialogThem();
            }
        });

        //Bat su kien nut cap nhat
        viewHolder.imageViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Cập nhật" + notes.getNameNote(), Toast.LENGTH_SHORT).show();
                //Goi Dialog Trong MainActivity
                context.DialogCapNhat(notes.getNameNote(),notes.getIdNote());
            }
        });

        //Bat su kien nut Xoa
        viewHolder.imageViewDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                context.DialogDelete(notes.getNameNote(), notes.getIdNote());
            }
        });

        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
