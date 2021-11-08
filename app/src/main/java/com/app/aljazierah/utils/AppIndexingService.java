package com.app.aljazierah.utils;

import android.app.IntentService;
import android.content.Intent;

import com.google.firebase.appindexing.FirebaseAppIndex;
import com.google.firebase.appindexing.Indexable;


import java.util.ArrayList;

// [START app_index_service]
public class AppIndexingService extends IntentService {

    public AppIndexingService() {
        super("AppIndexingService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ArrayList<Indexable> indexableNotes = new ArrayList<>();



        if (indexableNotes.size() > 0) {
            Indexable[] notesArr = new Indexable[indexableNotes.size()];
            notesArr = indexableNotes.toArray(notesArr);

            // batch insert indexable notes into index
            FirebaseAppIndex.getInstance().update(notesArr);
        }
    }

    // [START_EXCLUDE]

    // [END_EXCLUDE]
}
// [END app_index_service]