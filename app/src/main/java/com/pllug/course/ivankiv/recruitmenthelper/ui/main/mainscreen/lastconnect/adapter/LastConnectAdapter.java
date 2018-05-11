package com.pllug.course.ivankiv.recruitmenthelper.ui.main.mainscreen.lastconnect.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pllug.course.ivankiv.recruitmenthelper.R;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.Contact;
import com.pllug.course.ivankiv.recruitmenthelper.ui.main.mainscreen.lastconnect.LastConnectPresenter;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LastConnectAdapter extends RecyclerView.Adapter<LastConnectAdapter.ViewHolder> {

    private List<Contact> contacts;
    private Context mContext;
    private LastConnectPresenter presenter;

    public LastConnectAdapter(List<Contact> contacts, Context mContext,  LastConnectPresenter presenter) {
        this.contacts = contacts;
        this.mContext = mContext;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_last_connect, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Contact contact = contacts.get(position);

        if (holder.photo != null && contact.getPhotoUri() != null) {
            Glide.with(mContext)
                    .asBitmap()
                    .load(contact.getPhotoUri())
                    .into(holder.photo);
        } else {
            holder.photo.setImageResource(R.drawable.man);
        }

        if (holder.name != null) {
            holder.name.setText(contact.getName());
        }

        if (holder.phone != null) {
            holder.phone.setText(contact.getPhone());
        }

        if (holder.date != null) {
            holder.date.setText(contact.getDateOfLatestContact());
        }

        if (holder.container != null) {
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.setDataFromAdapter(contact.getId(), contact.getRecruiterNotesId(), "LastConnect");
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void filterList(List<Contact> filteredContact) {
        this.contacts = filteredContact;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView photo;
        TextView name, phone, date;
        RelativeLayout container;

        public ViewHolder(View itemView) {
            super(itemView);

            photo = itemView.findViewById(R.id.item_last_connect_image);
            name = itemView.findViewById(R.id.item_last_connect_name);
            phone = itemView.findViewById(R.id.item_last_connect_phone);
            date = itemView.findViewById(R.id.item_last_connect_date);
            container = itemView.findViewById(R.id.item_last_connect_container);
        }
    }
}
