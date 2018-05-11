package com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.detailcontact.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pllug.course.ivankiv.recruitmenthelper.R;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Language;

import java.util.List;

public class LanguageTextViewAdapter extends RecyclerView.Adapter<LanguageTextViewAdapter.ViewHolder> {
    private List<Language> languages;

    public LanguageTextViewAdapter(List<Language> languages) {
        this.languages = languages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_language_text_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Language language = languages.get(position);

        if (holder.language != null) {
            holder.language.setText(language.getLanguage());
        }

        if (holder.languageLevel != null) {
            holder.languageLevel.setText(language.getLanguageLevel());
        }
    }

    @Override
    public int getItemCount() {
        return languages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView language, languageLevel;

        public ViewHolder(View itemView) {
            super(itemView);
            language = itemView.findViewById(R.id.item_language_text_view_language);
            languageLevel = itemView.findViewById(R.id.item_language_text_view_language_level);
        }
    }
}
