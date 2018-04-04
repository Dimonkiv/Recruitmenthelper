package com.pllug.course.ivankiv.recruitmenthelper.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.pllug.course.ivankiv.recruitmenthelper.R;
import com.pllug.course.ivankiv.recruitmenthelper.data.model.PhoneContact;
import com.pllug.course.ivankiv.recruitmenthelper.utils.CircularTransformation;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AddContactAdapter extends BaseAdapter {


    public interface AddContactAdapterEventListener {
        void openEditPage(int position);
    }

    private Context context;
    private List<PhoneContact> contacts;
    private LayoutInflater inflater;
    private AddContactAdapterEventListener listener;


    public AddContactAdapter(Context context, List<PhoneContact> contacts) {
        this.context = context;
        this.contacts = contacts;
        inflater = LayoutInflater.from(this.context);
    }

    public void addListener(AddContactAdapterEventListener listener) {
        this.listener = listener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_phone_contact, parent, false);
            myViewHolder = new MyViewHolder(convertView);
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }

        PhoneContact contact = contacts.get(position);

        if (myViewHolder.avatar != null && contact.getPhotoUri() != null) {
            Picasso.with(context)
                    .load(contact.getPhotoUri())
                    .transform(new CircularTransformation(220))
                    .into(myViewHolder.avatar);
        }

        if (myViewHolder.name != null) {
            myViewHolder.name.setText(contact.getName());
        }

        if (myViewHolder.phone != null) {
            myViewHolder.phone.setText(contact.getPhone());
        }


        if (myViewHolder.button != null) {
            myViewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.openEditPage(position);
                    }
                }
            });
        }

        return convertView;


    }

    private class MyViewHolder {
        private TextView name, phone;
        private ImageView avatar;
        private ImageButton button;

        public MyViewHolder(View item) {
            name = item.findViewById(R.id.item_add_contact_name);
            phone = item.findViewById(R.id.item_add_contact_phone);
            avatar = item.findViewById(R.id.item_add_contact_image);
            button = item.findViewById(R.id.item_add_contact_add);
        }

    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public PhoneContact getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


}


