package com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.editcontact.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.pllug.course.ivankiv.recruitmenthelper.R;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Language;
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.mainscreen.lastconnect.adapter.LastConnectAdapter;

import java.util.ArrayList;
import java.util.List;

public class LanguageEditTextAdapter extends RecyclerView.Adapter<LanguageEditTextAdapter.LanguageHolder> {
    public interface LanguageAdapterListener {
        void onRemoveLanguage(int position);
    }

    public List<Language> languages = new ArrayList<>();
    private Context mContext;
    private LanguageAdapterListener listener;

    public LanguageEditTextAdapter(Context mContext, LanguageAdapterListener listener) {
        this.mContext = mContext;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LanguageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_language, parent, false);
        LanguageHolder viewHolder = new LanguageHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final LanguageHolder holder, final int position) {
        final Language language = languages.get(position);

        if (holder.editText != null) {
            holder.editText.setText("");
            initLanguageAutoComplete(holder);

            //Set language to Language EditText
            if (language.getLanguage() != null && !language.getLanguage().isEmpty()) {
                holder.editText.setText(language.getLanguage());
            }

            //Language text watcher
            holder.editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!s.toString().isEmpty()) {
                        languages.get(position).setLanguage(s.toString());
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }




        if (holder.languageLevel != null) {
            initLanguageLevelSpinner(holder.languageLevel);


            if (language.getLanguageLevel() != null && !language.getLanguageLevel().isEmpty()) {
                holder.languageLevel.setSelection(getPosLanguageLevel(language.getLanguageLevel()));
            } else {
                language.setLanguageLevel("A1");
            }

            //Language Level Spinner Watcher
            holder.languageLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    language.setLanguageLevel(parentView.getItemAtPosition(position).toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                }

            });
        }

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onRemoveLanguage(position);
                }
            }
        });

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

    private void initLanguageLevelSpinner(Spinner languageLevel) {
        String level[] = new String[]{"A1", "A2", "B1", "B2", "C1", "C2"};

        ArrayAdapter adapter = new ArrayAdapter(mContext, android.R.layout.simple_expandable_list_item_1, level);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        languageLevel.setAdapter(adapter);
    }

    private void initLanguageAutoComplete(LanguageHolder holder) {
        String typicalLanguages[] = new String[]{"Українська", "Російська", "Польська", "Німецька", "Англійська", "Французька"};
        holder.editText.setAdapter(new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, typicalLanguages));
    }

    @Override
    public int getItemCount() {
        return languages.size();
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void addAllLanguages(List<Language> newLanguages) {
        this.languages.clear();

        if (newLanguages != null) {
            this.languages.addAll(newLanguages);
        } else {
            languages = new ArrayList<>();
        }

        notifyDataSetChanged();
    }

    public void addLanguageField() {
        languages.add(new Language());
    }

    public void removeField(int position) {
        languages.remove(position);
    }


    public class LanguageHolder extends RecyclerView.ViewHolder {
        public AutoCompleteTextView editText;
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
