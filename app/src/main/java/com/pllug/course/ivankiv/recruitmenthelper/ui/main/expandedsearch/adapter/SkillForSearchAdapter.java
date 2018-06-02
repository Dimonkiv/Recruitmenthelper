package com.pllug.course.ivankiv.recruitmenthelper.ui.main.expandedsearch.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pllug.course.ivankiv.recruitmenthelper.R;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.LanguageForExpandedSearch;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.SkillForExpandedSearch;
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.expandedsearch.ExpandedSearchPresenter;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SkillForSearchAdapter extends RecyclerView.Adapter<SkillForSearchAdapter.ViewHolder>  {

    private Context mContext;
    private List<SkillForExpandedSearch> skills;
    private ExpandedSearchPresenter presenter;

    public SkillForSearchAdapter(Context mContext, List<SkillForExpandedSearch> skills,  ExpandedSearchPresenter presenter) {
        this.mContext = mContext;
        this.skills = skills;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_for_expanded_search, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final SkillForExpandedSearch skillItem = skills.get(position);

        if (holder.image != null && skillItem.getPhotoUri() != null) {
            Glide.with(mContext)
                    .asBitmap()
                    .load(skillItem.getPhotoUri())
                    .into(holder.image);
        } else {
            holder.image.setImageResource(R.drawable.man);
        }

        if (holder.name != null && !skillItem.getName().isEmpty()) {
            holder.name.setText(skillItem.getName());
        }

        if (holder.skill != null && !skillItem.getSkill().isEmpty()) {
            holder.skill.setText(skillItem.getSkill());
        }

        if (holder.container != null) {
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.setDataFromAdapter(skillItem.getId(), skillItem.getRecruiterNotesId(), "ExpandedSearch");
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return skills.size();
    }

    public void filterList(List<SkillForExpandedSearch> filteredSkills) {
        this.skills = filteredSkills;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView image;
        TextView name, skill;
        RelativeLayout container;


        public ViewHolder(View item) {
            super(item);
            image = item.findViewById(R.id.item_for_expanded_search_image);
            name = item.findViewById(R.id.item_for_expanded_search_name);
            skill = item.findViewById(R.id.item_for_expanded_search_result);
            container = item.findViewById(R.id.item_for_expanded_search_container);
        }
    }
}
