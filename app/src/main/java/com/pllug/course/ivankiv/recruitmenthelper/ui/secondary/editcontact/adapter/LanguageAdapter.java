package com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.editcontact.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.pllug.course.ivankiv.recruitmenthelper.R;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.ViewHolder> {


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        EditText editText;
        ImageButton btn;


        public ViewHolder(View item) {
            super(item);
            editText = item.findViewById(R.id.item_language_edit_text);
            btn = item.findViewById(R.id.item_language_btn);
        }
    }
}
