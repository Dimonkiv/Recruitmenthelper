package com.pllug.course.ivankiv.recruitmenthelper.ui.main.contactlist.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pllug.course.ivankiv.recruitmenthelper.R;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.ContactListItem;
import com.pllug.course.ivankiv.recruitmenthelper.ui.secondary.SecondaryActivity;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {
    private Context mContext;
    private List<ContactListItem>  contactList;

    public ContactListAdapter(Context mContext, List<ContactListItem>  contactList) {
        this.mContext = mContext;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact_list, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ContactListItem contactItem = contactList.get(position);

        if (holder.photo != null && contactItem.getPhotoUri() != null) {
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

        if (holder.profession != null && !contactItem.getProfession().isEmpty()) {
            holder.profession.setText(contactItem.getProfession());
        }

        if (holder.jobInterest != null && !contactItem.getWorkInterests().isEmpty()) {
            holder.jobInterest.setText(contactItem.getWorkInterests());
        }

        if (holder.container != null) {
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, SecondaryActivity.class);
                    intent.putExtra("id", contactItem.getId());
                    intent.putExtra("recruiterNotesId", contactItem.getRecruiterNotesId());
                    intent.putExtra("fragmentName", "ContactList");
                    mContext.startActivity(intent);
                }
            });
        }

        if (holder.editButton != null) {
            holder.editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, SecondaryActivity.class);
                    intent.putExtra("fragmentName", "ContactListEditBtn");
                    intent.putExtra("id", contactItem.getId());
                    intent.putExtra("recruiterNotesId", contactItem.getRecruiterNotesId());
                    mContext.startActivity(intent);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView photo;
        TextView name, profession, jobInterest;
        ImageButton editButton;
        RelativeLayout container;


        public ViewHolder(View item) {
            super(item);
            photo = item.findViewById(R.id.item_contact_list_photo);
            name = item.findViewById(R.id.item_contact_list_name);
            profession = item.findViewById(R.id.item_contact_list_profession);
            jobInterest = item.findViewById(R.id.item_contact_list_job_interest);
            editButton = item.findViewById(R.id.item_contact_list_edit_btn);
            container = item.findViewById(R.id.item_contact_list_container);
        }
    }
}
