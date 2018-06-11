package com.pllug.course.ivankiv.recruitmenthelper.ui.main.phonecontact.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.pllug.course.ivankiv.recruitmenthelper.R;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Contact;
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.phonecontact.PhoneContactPresenter;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PhoneContactAdapter extends RecyclerView.Adapter<PhoneContactAdapter.ViewHolder> {

    private List<Contact> contacts;
    private Context mContext;
    private PhoneContactPresenter presenter;

    public PhoneContactAdapter(Context mContext, PhoneContactPresenter presenter) {
        this.mContext = mContext;
        this.contacts = new ArrayList<>();
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phone_contact, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Contact contact = contacts.get(position);

        if (holder.image != null && contact.getPhotoUri() != null) {
            Glide.with(mContext)
                    .asBitmap()
                    .load(contact.getPhotoUri())
                    .into(holder.image);
        } else {
            holder.image.setImageResource(R.drawable.man);
        }

        if (holder.name != null) {
            holder.name.setText(contact.getName());
        }

        if (holder.phone != null) {
            holder.phone.setText(contact.getPhone());
        }

        if (holder.button != null) {
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.onPhoneContactItemClick(contacts.get(position));
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void addAllContacts(List<Contact> contacts) {
        this.contacts.clear();
        this.contacts.addAll(contacts);
        notifyDataSetChanged();
    }

    public void filterList(List<Contact> filteredContact) {
        this.contacts = filteredContact;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView image;
        TextView name, phone;
        ImageButton button;

        ViewHolder(View item) {
            super(item);
            image = item.findViewById(R.id.item_add_contact_image);
            name = item.findViewById(R.id.item_add_contact_name);
            phone = item.findViewById(R.id.item_add_contact_phone);
            button = item.findViewById(R.id.item_add_contact_button);
        }
    }


}


