package com.pllug.course.ivankiv.recruitmenthelper.ui.main.mainscreen.contactlist.adapter;

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
import com.pllug.course.ivankiv.recruitmenthelper.data.model.ContactListItem;
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.mainscreen.contactlist.ContactListPresenter;


import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {
    private Context mContext;
    private List<ContactListItem>  contactList;
    private ContactListPresenter presenter;

    public ContactListAdapter(Context mContext, ContactListPresenter presenter) {
        this.mContext = mContext;
        this.contactList = new ArrayList<>();
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final ContactListItem contactItem = contactList.get(position);

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

        if (contactItem.getTypeOfEmployment() != null && !contactItem.getTypeOfEmployment().isEmpty()) {
            if (contactItem.getTypeOfEmployment().equals("Працює")) {
                if (holder.profession != null && !contactItem.getProfession().isEmpty()) {
                    holder.profession.setText(contactItem.getProfession());
                }
            } else {
                holder.profession.setText("Студент");
            }
        }

        if (holder.jobInterest != null && !contactItem.getJobInterests().isEmpty()) {
            holder.jobInterest.setText(contactItem.getJobInterests());
        }

        if (holder.container != null) {
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.onContactsItemClick(contactItem.getId(), contactItem.getRecruiterNotesId());
                }
            });
        }

        if (holder.editButton != null) {
            holder.editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.onEditButtonClick(contactItem.getId(), contactItem.getRecruiterNotesId());
                }
            });
        }

        if (holder.deleteBtn != null) {
            holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.onDeleteButtonClick(contactItem.getId(), contactItem.getRecruiterNotesId());
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

    public void addAllContacts(List<ContactListItem> contacts) {
        contactList.clear();
        contactList.addAll(contacts);
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView photo;
        TextView name, profession, jobInterest;
        ImageButton editButton, deleteBtn;
        RelativeLayout container;


        ViewHolder(View item) {
            super(item);
            photo = item.findViewById(R.id.item_contact_list_photo);
            name = item.findViewById(R.id.item_contact_list_name);
            profession = item.findViewById(R.id.item_contact_list_profession);
            jobInterest = item.findViewById(R.id.item_contact_list_job_interest);
            editButton = item.findViewById(R.id.item_contact_list_edit_btn);
            container = item.findViewById(R.id.item_contact_list_container);
            deleteBtn = item.findViewById(R.id.item_contact_list_delete_btn);
        }
    }
}
