package com.pllug.course.ivankiv.recruitmenthelper.ui.main.selected.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pllug.course.ivankiv.recruitmenthelper.R;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Contact;
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.mainscreen.contactlist.ContactListPresenter;
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.selected.SelectedContactPresenter;


import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SelectedContactAdapter extends RecyclerView.Adapter<SelectedContactAdapter.ViewHolder> {
    private Context mContext;
    private List<Contact> contactList;
    private SelectedContactPresenter presenter;

    public SelectedContactAdapter(Context mContext, SelectedContactPresenter presenter) {
        this.mContext = mContext;
        this.contactList = new ArrayList<>();
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_contact, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Contact contactItem = contactList.get(position);

        if (holder.photo != null && contactItem.getPhotoUri() != null && !contactItem.getPhotoUri().isEmpty()) {
            Glide.with(mContext)
                    .asBitmap()
                    .load(contactItem.getPhotoUri())
                    .into(holder.photo);
        } else {
            holder.photo.setImageResource(R.drawable.man);
        }

        if (holder.name != null && !contactItem.getName().isEmpty()) {
            holder.name.setText(contactItem.getName());
        }

        if (holder.container != null) {
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.onContactItemClick(contactItem.getId(), contactItem.getRecruiterNotesId());
                }
            });
        }

        if (holder.selectedButton!= null) {
            holder.selectedButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.selectedButton.setImageResource(R.drawable.icon_star_green);
                    contactItem.setSelected(false);
                    presenter.onStarButtonClick(contactItem);
                    contactList.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public void addAllContacts(List<Contact> contacts) {
        contactList.clear();
        contactList.addAll(contacts);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView photo;
        TextView name;
        ImageButton selectedButton;
        RelativeLayout container;


        public ViewHolder(View item) {
            super(item);
            photo = item.findViewById(R.id.item_selected_contact_photo);
            name = item.findViewById(R.id.item_selected_contact_name);
            selectedButton= item.findViewById(R.id.item_selected_contact_selected_btn);
            container = item.findViewById(R.id.item_selected_contact_container);
        }
    }
}
