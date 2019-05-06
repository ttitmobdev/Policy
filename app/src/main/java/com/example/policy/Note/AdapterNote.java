package com.example.policy.Note;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.policy.R;

import java.util.List;

public class AdapterNote extends RecyclerView.Adapter<AdapterNote.ViewHolder> {
    List<TextNote> textNotes;


    public interface NoteItemListener{
        void onNoteClick(long id);
    }

    public AdapterNote(List<TextNote> textNotes) {
        this.textNotes = textNotes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.one_text_note,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.textView.setText(textNotes.get(i).getNote());
    }
    public TextNote getNote(int adapterPosition){
        return textNotes.get(adapterPosition);
    }

    @Override
    public int getItemCount() {
        return textNotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);

        }
    }
     public void removeAt(int position){
        textNotes.remove(position);
        notifyDataSetChanged();
     }
}
