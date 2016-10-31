package com.example.user.recyclerview.DataModel;

import java.util.ArrayList;

/**
 * Created by user on 10/24/2016.
 */

public class Contact {
    private static int lastContactedId = 0;
    private String mName;
    private boolean monLine;

    public Contact(String name, boolean online) {
        mName = name;
        monLine = online;
    }

    public static ArrayList<Contact> createContactList(int numContacts) {
        ArrayList<Contact> contacts = new ArrayList<Contact>();
        for (int i = 1; i <= numContacts; i++) {
            contacts.add(new Contact("Person " + ++lastContactedId, i <= numContacts / 2));
        }
        return contacts;
    }

    public String get_Name() {
        return mName;
    }

    public void set_Name(String n) {
        mName = n;
    }

    public boolean isOnline() {
        return monLine;
    }
}
