package com.example.michael.bae;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by Michael on 2/19/2015.
 */
public class InportantMethods {
    private static String FNAME = "", STATUS = "", UID = "", PASSWORD = "";

    final static private String FIRE_BASE_URL = "https://b-a-e.firebaseio.com/";
    private static Firebase STATICREF, STATICMYREF;

    public static String uid(Firebase ref) {
        UID = (String) ref.getAuth().getProviderData().get("email");
        UID = (UID.substring(0, UID.indexOf("@")) +
                (UID.substring((UID.indexOf("@") + 1), (UID.indexOf(".")))));
        return UID;
    }

    public static String getName(Firebase ref) {

        String uniqueID = uid(ref);
        STATICREF = ref.child("Users/" + uniqueID + "/");
        STATICREF.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FNAME = dataSnapshot.child("Name").getValue().toString();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        return FNAME;
    }
}
