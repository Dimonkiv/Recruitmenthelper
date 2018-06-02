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
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.expandedsearch.ExpandedSearchPresenter;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LanguageForSearchAdapter extends RecyclerView.Adapter<LanguageForSearchAdapter.ViewHolder> {

    private Context mContext;
    private List<LanguageForExpandedSearch> languages;
    private ExpandedSearchPresenter presenter;

    public LanguageForSearchAdapter(Context mContext, ExpandedSearchPresenter presenter) {
        this.mContext = mContext;
        this.presenter = presenter;
        this.languages = new ArrayList<>();
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
        final LanguageForExpandedSearch languageItem = languages.get(position);

        if (holder.image != null && languageItem.getPhotoUri() != null) {
            Glide.with(mContext)
                    .asBitmap()
                    .load(languageItem.getPhotoUri())
                    .into(holder.image);
        } else {
            holder.image.setImageResource(R.drawable.man);
        }

        if (holder.name != null && !languageItem.getName().isEmpty()) {
            holder.name.setText(languageItem.getName());
        }

        if (holder.language != null && !languageItem.getLanguage().isEmpty()) {
            holder.language.setText(languageItem.getLanguage());
        }

        if (holder.container != null) {
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.setDataFromAdapter(languageItem.getId(), languageItem.getRecruiterNotesId(), "ExpandedSearch");
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return languages.size();
    }

    public void filterList(List<LanguageForExpandedSearch> filteredLanguages) {
        this.languages = filteredLanguages;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView image;
        TextView name, language;
        RelativeLayout container;


        public ViewHolder(View item) {
            super(item);
            image = item.findViewById(R.id.item_for_expanded_search_image);
            name = item.findViewById(R.id.item_for_expanded_search_name);
            language = item.findViewById(R.id.item_for_expanded_search_result);
            container = item.findViewById(R.id.item_for_expanded_search_container);
        }
    }
}
