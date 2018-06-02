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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;

import com.pllug.course.ivankiv.recruitmenthelper.R;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Skill;

import java.util.ArrayList;
import java.util.List;

public class SkillAdapter extends RecyclerView.Adapter<SkillAdapter.SkillHolder> {

    public interface SkillAdapterListener {
        void onRemoveSkill(int position);
    }

    private List<Skill> skills = new ArrayList<>();
    private Context mContext;
    private SkillAdapterListener listener;
    private String typicalSkills[];

    public SkillAdapter(Context mContext, SkillAdapterListener listener) {
        this.mContext = mContext;
        this.listener = listener;
    }


    @NonNull
    @Override
    public SkillHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_skill, parent, false);

        SkillHolder skillHolder = new SkillHolder(v);

        return skillHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SkillHolder holder, final int position) {
        holder.skillEditText.setText("");
        Log.d("skill - ", "" + position);
        if (holder.skillEditText != null) {
            initTypicalSkills();

            holder.skillEditText.setAdapter(new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, typicalSkills));

            holder.skillEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    skills.get(position).setSkill(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });
        }

        holder.skillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) {
                    listener.onRemoveSkill(position);
                }
            }
        });

        if (holder.skillEditText != null) {
            holder.skillEditText.setText(skills.get(holder.getAdapterPosition()).getSkill());
        }
    }

    @Override
    public int getItemCount() {
        return skills.size();
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void addAllSkill(List<Skill> newSkills) {
        this.skills.clear();

        if(newSkills != null) {
            this.skills.addAll(newSkills);
        }else {
            skills = new ArrayList<>();
        }

        notifyDataSetChanged();
    }

    private void initTypicalSkills() {
        typicalSkills = new String[] {"Java", "Android", "Git", "Sql", "C", "C++", "Python", "Django", "Html", "Css"
                , "JavaScript", "Jquery", "React", "AngularJs", "Angular 2", "Vue.js", "Backbone.js", "Ember.js", "Knockout.js", "Node.js"
                , "Html5", "Css3", "C#", "Kotlin", "Room", "SQLite", "Retrofit", "Rest.API", ".NET", "objective c" , "swift"
                , "Php", "Assembler", "Менеджмент", "Підбір персоналу", "Набір IT-персоналу", "Управління персоналом", "Android SDK"
                , "Android NDK"};
    }

    public class SkillHolder extends RecyclerView.ViewHolder{
        public AutoCompleteTextView skillEditText;
        public ImageButton skillBtn;


        public SkillHolder(View itemView) {
            super(itemView);
            skillEditText = itemView.findViewById(R.id.item_skill_edit_text);
            skillBtn = itemView.findViewById(R.id.item_skill_btn);
        }
    }
}
