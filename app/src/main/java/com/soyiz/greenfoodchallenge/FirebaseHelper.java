package com.soyiz.greenfoodchallenge;

import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.util.Consumer;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Note: this is not a singleton because it can cause a memory leak due to holding the
// database reference for a long time. Usage case is then to instantiate a helper object for usage
// whenever it's needed. Since it only grabs reference, it's cheap to create.

public class FirebaseHelper {

    private static final String TAG = "FirebaseHelper";

    private final Firestore firestoreInstance = new Firestore();
    private final Functions functionsInstance = new Functions();

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference userCollection = db.collection("users");

    private final FirebaseFunctions functions = FirebaseFunctions.getInstance();

    // Returns the Firestore instance
    public Firestore getFirestore() {
        return firestoreInstance;
    }

    // Returns the Functions instance
    public Functions getFunctions() {
        return functionsInstance;
    }

    final class Firestore {

        public static final String FIRST_NAME = "firstName";
        public static final String LAST_NAME = "lastName";
        public static final String ALIAS = "alias";
        public static final String USE_ALIAS_FOR_NAME = "useAliasForName";

        public static final String CITY = "city";

        public static final String BIO = "bio";

        public static final String ANONYMOUS_PLEDGE = "anonymousPledge";
        public static final String SHOW_FIRST_NAME = "showFirstName";
        public static final String SHOW_LAST_NAME = "showLastName";
        public static final String SHOW_LAST_INITIAL_FOR_LAST_NAME = "showLastInitialForLastName";
        public static final String SHOW_CITY = "showCity";

        public static final String CURRENT_CO2E = "currentCO2e";
        public static final String GOAL_CO2E = "goalCO2e";

        public static final String PLEDGE = "pledge";

        public static final String DIET = "diet";

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

            // User's biography
            user.put(BIO, "");

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
            // user.put(PLEDGE, pledgeCollection.document("template"));

            // A map representing pledge info
            user.put(PLEDGE, getPledgeTemplate());

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

//        // Given a user, will set its userDocument to the latest on the server, but does it asynchronously
//        // Would recommend running this immediately upon user login so the pull can resolve by the time it's needed
//        public void pullUserDocument(final User user) {
//            FirebaseUser firebaseUser = user.getFirebaseUser();
//
//            userCollection.document(firebaseUser.getUid())
//                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                    if (task.isSuccessful()) {
//                        DocumentSnapshot document = task.getResult();
//
//                        if (document == null) {
//                            // Yes, this silently fails, but throwing will just get more problematic.
//                            // Logging is about as useful as we can get here.
//                            Log.d("pullUserDocument", "[ERROR] onComplete: grabbed document was null. User was never created!");
//                            return;
//                        }
//
//                        if (document.exists()) {
//                            user.setUserDocument(document.getData());
//                        }
//                    }
//                }
//            });
//
//        }
//
//        // Given a user, will send its userDocument to the server as an update
//        public void pushUserDocument(User user) {
//            FirebaseUser firebaseUser = user.getFirebaseUser();
//            userCollection.document(firebaseUser.getUid()).set(user.getUserDocument());
//        }

        public void queryPledgesForViewer(final PledgeFragment fragment) {
            userCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    String TAG = "queryPledgesForViewer";

                    if (task.isSuccessful()) {
                        List<Map<String, Object>> outputList = new ArrayList<>();

                        QuerySnapshot result = task.getResult();
                        if (result == null) {
                            Log.e(TAG, "[ERROR] onComplete: task result was null!");
                            return;
                        }

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (document == null) {
                                // Failing silently, since it's the best we can hope for
                                Log.e(TAG, "[ERROR] onComplete: grabbed a document which was null");
                                return;
                            }

                            if (document.exists()) {
                                Log.d(TAG, "onComplete: user first name: " + document.getData().get(FIRST_NAME));
                                outputList.add(document.getData());

                                //TODO: remove after users are working right
                                break;
                            }
                        }

                        fragment.appendList(outputList);
                    } else {
                        // More silent fails...
                        Log.d(TAG, "[ERROR] onComplete: task unsuccessful");
                    }
                }
            });
        }
    }

    final class Functions {

        public static final String USER_ID = "identifier";
        public static final String FIELD_NAME = "fieldName";
        public static final String FIELD_VALUE = "fieldValue";

        private Task<HttpsCallableResult> makeCall(String functionName, Map<String, Object> data) {
            return functions.getHttpsCallable(functionName).call(data).continueWith(new Continuation<HttpsCallableResult, HttpsCallableResult>() {
                @Override
                public HttpsCallableResult then(@NonNull Task<HttpsCallableResult> task) {
                    return task.getResult();
                }
            });
        }

        private String findUserEmail(FirebaseUser user) throws Exception {
            // Get the user email by looping over the providers and grabbing the first email listed there
            for (UserInfo profile : user.getProviderData()) {
                // Skip firebase as a provider
                if (profile.getProviderId().equals("firebase")) {
                    continue;
                }

                if (profile.getEmail() != null) {
                    Log.d(TAG, "findUserEmail: user email '" + profile.getEmail() + "' found on provider '" + profile.getProviderId() + "'");
                    return profile.getEmail();
                }
            }

            Log.e(TAG, "findUserEmail: no valid user email found! Bad bad things are happening!");
            throw new Exception();
        }

        private FirebaseUser safeGetFirebaseUser() throws Exception {
            User user = User.getCurrent();
            if (user == null) {
                Log.e(TAG, "getUserInfoForDisplay: user is null! Something isn't setup right...");
                throw new Exception();
            }

            FirebaseUser firebaseUser = user.getFirebaseUser();
            if (firebaseUser == null) {
                Log.e(TAG, "getUserInfoForDisplay: Firebase user is null! User likely not logged in...");
                throw new Exception();
            }

            return firebaseUser;
        }

        // Will grab the user information to display on the user fragment and set it
        public void getUserInfoForDisplay() {

            FirebaseUser firebaseUser;
            try {
                firebaseUser = safeGetFirebaseUser();
            } catch (Exception e) {
                return;
            }

            String userEmail;
            try {
                userEmail = findUserEmail(firebaseUser);
            } catch (Exception e) {
                return;
            }

            Map<String, Object> data = new HashMap<>();
            data.put(USER_ID, userEmail);

            Task<HttpsCallableResult> task = makeCall("getUserInfoForDisplay", data);
            task.addOnCompleteListener(new OnCompleteListener<HttpsCallableResult>() {
                @Override
                public void onComplete(@NonNull Task<HttpsCallableResult> task) {
                    Map<String, Object> data = (Map<String, Object>) task.getResult().getData();
                    Log.d(TAG, "getUserInfoForDisplay.onComplete: data map: '" + data + "'");
                    User.getCurrent().setDisplayInfo(data);
                }
            });
        }

        public void setUserField(final String fieldToChange, final Object newFieldValue) {
            Log.d(TAG, "setUserField: setting field '" + fieldToChange + "' to value '" + newFieldValue + "'");

            FirebaseUser firebaseUser;
            try {
                firebaseUser = safeGetFirebaseUser();
            } catch (Exception e) {
                return;
            }

            String userEmail;
            try {
                userEmail = findUserEmail(firebaseUser);
            } catch (Exception e) {
                return;
            }

            Map<String, Object> data = new HashMap<>();
            data.put(USER_ID, userEmail);
            data.put(FIELD_NAME, fieldToChange);
            data.put(FIELD_VALUE, newFieldValue);

            makeCall("setUserField", data);
        }

        public void getUserField(final String fieldToGet, final Consumer<Object> callback) {
            Log.d(TAG, "getUserField: getting field '" + fieldToGet + "'");

            FirebaseUser firebaseUser;
            try {
                firebaseUser = safeGetFirebaseUser();
            } catch (Exception e) {
                return;
            }

            String userEmail;
            try {
                userEmail = findUserEmail(firebaseUser);
            } catch (Exception e) {
                return;
            }

            Map<String, Object> data = new HashMap<>();
            data.put(USER_ID, userEmail);
            data.put(FIELD_NAME, fieldToGet);

            Task<HttpsCallableResult> task = makeCall("getUserField", data);
            task.addOnCompleteListener(new OnCompleteListener<HttpsCallableResult>() {
                @Override
                public void onComplete(@NonNull Task<HttpsCallableResult> task) {
                    Map<String, Object> data = (Map<String, Object>) task.getResult().getData();
                    Log.d(TAG, "getUserField: data map '" + data + "'");

                    callback.accept(data.get(FIELD_VALUE));
                }
            });
        }
    }

}
