package com.example.user.recyclerview.DataAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.user.recyclerview.DataModel.Contact;
import com.example.user.recyclerview.R;

import java.util.List;

/**
 * Created by user on 10/24/2016.
 */

public class ContactsAdapter extends RecyclerView.Adapter {
    private static final int editable_row = 0;
    private static final int non_editable_row = 1;
    int selected_position = -1;
    private List<Contact> mContacts;
    private Context mContext;

    public ContactsAdapter(Context context, List<Contact> contacts) {
        mContacts = contacts;
        mContext = context;
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == editable_row) {
            View enabledView = LayoutInflater.from(getContext()).inflate(R.layout.layout_item_list_enabled, parent, false);
            return new ViewHolderEnabled(enabledView);
        } else if (viewType == non_editable_row) {
            View disabledView = LayoutInflater.from(getContext()).inflate(R.layout.layout_item_list_disabled, parent, false);
            return new ViewHolderDisabled(disabledView);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        //Parsing the data
        Contact contact = mContacts.get(position);

        //  Assign the layout to the view //
        if (getItemViewType(position) == editable_row) {
            ViewHolderEnabled itemview_e = (ViewHolderEnabled) holder;
            itemview_e.nameTextView.setText(contact.get_Name());
            itemview_e.messageButton.setText("E_Message");
        } else if (getItemViewType(position) == non_editable_row) {
            ViewHolderDisabled itemview_d = (ViewHolderDisabled) holder;
            itemview_d.nTextView.setText(contact.get_Name());
            itemview_d.mButton.setText("D_Message");
        }

        // Update the current position //
        if (selected_position == position) {
            holder.itemView.setSelected(true);
        } else {
            holder.itemView.setSelected(false);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return mContacts.get(position).get_Name().equals("Person 1") ? editable_row : non_editable_row;
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public class ViewHolderEnabled extends RecyclerView.ViewHolder {
        public EditText nameTextView;
        public Button messageButton;

        public ViewHolderEnabled(View itemView) {
            super(itemView);
            nameTextView = (EditText) itemView.findViewById(R.id.contact_name);
            messageButton = (Button) itemView.findViewById(R.id.message_button);
            nameTextView.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    Log.d("mytag", "onTextChanged: " + s.toString());
                    Log.d("mytag", "" + mContacts.get(getAdapterPosition()).get_Name());

                    mContacts.get(getAdapterPosition()).set_Name(s.toString());
                    Log.d("mytag", "" + mContacts.get(getAdapterPosition()).get_Name());
                }
            });
        }
    }

    public class ViewHolderDisabled extends RecyclerView.ViewHolder {
        public EditText nTextView;
        public Button mButton;

        public ViewHolderDisabled(final View itemView) {
            super(itemView);
            nTextView = (EditText) itemView.findViewById(R.id.c_name);
            mButton = (Button) itemView.findViewById(R.id.m_button);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyItemChanged(selected_position);
                    selected_position = getAdapterPosition();
                    notifyItemChanged(selected_position);
                }
            });
        }
    }
}
