package com.example.sqlitebt.adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sqlitebt.R;
import com.example.sqlitebt.model.NotesModel;

import java.util.List;

public class NotesAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<NotesModel> noteList;

    public NotesAdapter(Context context, int layout, List<NotesModel> noteList) {
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
            viewHolder.imageViewDelete = (ImageView) convertView.findViewById(R.id.imageViewEdit);
            viewHolder.imageViewDelete = (ImageView) convertView.findViewById(R.id.imageViewDelete);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //Lay gia tri
        NotesModel notes = noteList.get(position);
        viewHolder.textViewNote.setText(notes.getNameNote());

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
