package com.soyiz.greenfoodchallenge;

import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

// Note: this is not a singleton because it can cause a memory leak due to holding the
// database reference for a long time. Usage case is then to instantiate a helper object for usage
// whenever it's needed. Since it only grabs reference, it's cheap to create.

public class FirestoreHelper {

    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String ALIAS = "alias";
    public static final String USE_ALIAS_FOR_NAME = "useAliasForName";

    public static final String CITY = "city";

    public static final String ANONYMOUS_PLEDGE = "anonymousPledge";
    public static final String SHOW_FIRST_NAME = "showFirstName";
    public static final String SHOW_LAST_NAME = "showLastName";
    public static final String SHOW_LAST_INITIAL_FOR_LAST_NAME = "showLastInitialForLastName";
    public static final String SHOW_CITY = "showCity";

    public static final String CURRENT_CO2E = "currentCO2e";
    public static final String GOAL_CO2E = "goalCO2e";

    public static final String PLEDGE = "pledge";

    public static final String DIET = "diet";

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference userCollection = db.collection("users");
    private final CollectionReference pledgeCollection = db.collection("pledges");

    public CollectionReference getUserCollectionReference()
    {
        return userCollection;
    }

    public CollectionReference getPledgeCollectionReference()
    {
        return pledgeCollection;
    }

    public Map<String, Object> getUserTemplate() {
        Map<String, Object> user = new HashMap<>();

        // User's first name
        user.put(FIRST_NAME, "");
        // User's last name
        user.put(LAST_NAME, "");
        // Alias, like a username, in case the user wants the pledge shared under it instead
        user.put(ALIAS, "");
        // If the user wants to share the pledge under an alias instead of their name
        user.put(USE_ALIAS_FOR_NAME, false);

        // Which city the pledge goes under
        user.put(CITY, "");

        // If the pledge is shared anonymously. Overrides all other settings below except city
        user.put(ANONYMOUS_PLEDGE, false);
        // If the user wants to share their first name
        user.put(SHOW_FIRST_NAME, false);
        // If the user wants to share their last name
        user.put(SHOW_LAST_NAME, false);
        // If to replace the user's last name with their last initial when shared
        user.put(SHOW_LAST_INITIAL_FOR_LAST_NAME, false);
        // If the user's city is to be shared publicly
        user.put(SHOW_CITY, false);

        // A reference (in the database) to the pledge for this user
        user.put(PLEDGE, pledgeCollection.document("template"));

        // A map of the string equivalent of a ProteinSource (via .toString) to the float percentage.
        // Essentially, a string keyed version of the internal map of a diet.
        // Starts null since it's designed to be loaded from Diet.exportToStringMap.
        user.put(DIET, null);

        return user;
    }

    private Map<String, Object> getPledgeTemplate() {
        Map<String, Object> pledge = new HashMap<>();

        // Current CO2e usage by user with this pledge. -1 means it's not been set
        pledge.put(CURRENT_CO2E, -1);
        // Goal CO2e usage by user with this pledge. -1 means it's not been set
        pledge.put(GOAL_CO2E, -1);

        return pledge;
    }

    // Given a user, will set its userDocument to the latest on the server, but does it asynchronously
    // Would recommend running this immediately upon user login so the pull can resolve by the time it's needed
    public void pullUserDocument(final User user) {
        FirebaseUser firebaseUser = user.getFirebaseUser();

        userCollection.document(firebaseUser.getUid())
            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful())
                    {
                        DocumentSnapshot document = task.getResult();

                        if (document == null)
                        {
                            // Yes, this silently fails, but throwing will just get more problematic.
                            // Logging is about as useful as we can get here.
                            Log.d("pullUserDocument", "[ERROR] onComplete: grabbed document was null. User was never created!");
                            return;
                        }

                        if (document.exists())
                        {
                            user.setUserDocument(document.getData());
                        }
                    }
                }
            });

    }

    // Given a user, will send its userDocument to the server as an update
    public void pushUserDocument(User user)
    {
        FirebaseUser firebaseUser = user.getFirebaseUser();
        userCollection.document(firebaseUser.getUid()).set(user.getUserDocument());
    }

    // Given a user, will set its pledgeDocument to the latest on the server, but does it asynchronously
    // Would recommend running this as soon as the user pull is done so this pull can resolve by the time it's needed
    public void pullPledgeDocument(final User user)
    {
        FirebaseUser firebaseUser = user.getFirebaseUser();

        user.getPledgeReference().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful())
                {
                    DocumentSnapshot document = task.getResult();

                    if (document == null)
                    {
                        // Yes, this silently fails, but throwing will just get more problematic.
                        // Logging is about as useful as we can get here.
                        Log.d("pullPledgeDocument", "[ERROR] onComplete: grabbed document was null. Pledge was never created for this user!");
                        return;
                    }

                    if (document.exists())
                    {
                        user.setPledgeDocument(document.getData());
                    }
                }
            }
        });

    }

    // Given a user, will send its pledge (as a document) to the server as an update
    public void pushPledgeDocument(User user)
    {
        user.getPledgeReference().set(user.getCurrentPledge().exportToStringMap());
    }
}
