package com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.editcontact.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.pllug.course.ivankiv.recruitmenthelper.R;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Skill;

import java.util.ArrayList;
import java.util.List;

public class SkillAdapter extends RecyclerView.Adapter<SkillAdapter.SkillHolder> {

    public List<SkillHolder> skillHolders = new ArrayList<>();
    private List<Skill> skills = new ArrayList<>();
    int skillHoldersSize = 0;
    int countForInitSkills = 0;


    @NonNull
    @Override
    public SkillHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_skill, parent, false);

        SkillHolder skillHolder = new SkillHolder(v);
        skillHolders.add(skillHolder);

        return skillHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SkillHolder holder, final int position) {
        holder.skillEditText.setText("");
        Log.d("mySkill", holder.getAdapterPosition() + " - " + position);
        if (holder.getAdapterPosition() < skillHolders.size()) {
            skillHolders.set(holder.getAdapterPosition(), holder);
        } else {
            skillHolders.add(holder);
        }

        holder.skillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeSkillField(holder);
            }
        });

        if (holder.getAdapterPosition() < skills.size() && countForInitSkills < skills.size()) {
            countForInitSkills++;
            if (holder.skillEditText != null) {
                holder.skillEditText.setText(skills.get(holder.getAdapterPosition()).getSkill());
            }
        }
    }

    @Override
    public int getItemCount() {
        return skillHoldersSize;
    }

    private void removeSkillField(SkillHolder holder) {
        int newPosition = holder.getAdapterPosition();
        skillHolders.remove(newPosition);
        skillHoldersSize--;
        notifyItemRemoved(newPosition);
    }

    public void addSkill() {
        skillHoldersSize++;
    }

    public void addAllSkill(List<Skill> skills) {
        this.skills.clear();
        this.skills.addAll(skills);
        skillHoldersSize = skills.size();
        notifyDataSetChanged();
    }
    public class SkillHolder extends RecyclerView.ViewHolder{
        public EditText skillEditText;
        public ImageButton skillBtn;


        public SkillHolder(View itemView) {
            super(itemView);
            skillEditText = itemView.findViewById(R.id.item_skill_edit_text);
            skillBtn = itemView.findViewById(R.id.item_skill_btn);
        }
    }
}
