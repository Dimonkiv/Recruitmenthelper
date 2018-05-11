package com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.editcontact.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.pllug.course.ivankiv.recruitmenthelper.R;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Language;

import java.util.ArrayList;
import java.util.List;

public class LanguageEditTextAdapter extends RecyclerView.Adapter<LanguageEditTextAdapter.LanguageHolder> {

    public List<LanguageHolder> languageHolders = new ArrayList<>();
    public List<Language> languages = new ArrayList<>();
    int languageHoldersSize = 0;
    private Context mContext;
    int countForInitLanguages = 0;

    public LanguageEditTextAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public LanguageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_language, parent, false);
        LanguageHolder viewHolder = new LanguageHolder(v);
        languageHolders.add(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final LanguageHolder holder, final int position) {

        holder.editText.setText("");
        Log.d("myLanguage", holder.getAdapterPosition() + " - " + position);
        if (holder.getAdapterPosition() < languageHolders.size()) {
            languageHolders.set(holder.getAdapterPosition(), holder);
        } else {
            languageHolders.add(holder);
        }

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeLanguageField(holder);
            }
        });

        initLanguageLevelSpinner(holder.languageLevel);

        if (position < languages.size() && countForInitLanguages < languages.size()) {
            countForInitLanguages++;
            Language language = languages.get(holder.getAdapterPosition());
            if (holder.editText != null) {
                holder.editText.setText(language.getLanguage());
            }

            if (holder.languageLevel != null) {
                holder.languageLevel.setSelection(getPosLanguageLevel(language.getLanguageLevel()));
            }
        }
    }

    private int getPosLanguageLevel(String languageLevel) {
        int pos = 0;
        switch (languageLevel) {
            case "A1":
                pos = 0;
                break;
            case "A2":
                pos = 1;
                break;
            case "B1":
                pos = 2;
                break;
            case "B2":
                pos = 3;
                break;
            case "C1":
                pos = 4;
                break;
            case "C2":
                pos = 5;
                break;
            default:
                pos = 0;
                break;
        }

        return pos;
    }

    @Override
    public int getItemCount() {
        return languageHoldersSize;
    }

    private void initLanguageLevelSpinner(Spinner languageLevel) {
        String level[] = new String[]{"A1", "A2", "B1", "B2", "C1", "C2"};

        ArrayAdapter adapter = new ArrayAdapter(mContext, android.R.layout.simple_expandable_list_item_1, level);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        languageLevel.setAdapter(adapter);

        languageLevel.setPrompt("Рівень");
    }

    public void addAllLanguages(List<Language> languages) {
        this.languages.clear();
        this.languages.addAll(languages);
        languageHoldersSize = languages.size();
        notifyDataSetChanged();
    }

    public void addLanguage() {
        languageHoldersSize++;
    }

    private void removeLanguageField(LanguageHolder holder) {
        int newPosition = holder.getAdapterPosition();
        languageHolders.remove(newPosition);
        languageHoldersSize--;
        notifyItemRemoved(newPosition);
    }

    public class LanguageHolder extends RecyclerView.ViewHolder {
        public EditText editText;
        public ImageButton btn;
        public Spinner languageLevel;


        LanguageHolder(View item) {
            super(item);
            editText = item.findViewById(R.id.item_language_edit_text);
            btn = item.findViewById(R.id.item_language_btn);
            languageLevel = item.findViewById(R.id.item_language_level);
        }
    }
}
