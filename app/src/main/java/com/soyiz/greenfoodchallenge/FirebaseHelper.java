package com.soyiz.greenfoodchallenge;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.tasks.*;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.*;
import com.google.firebase.firestore.util.Consumer;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.soyiz.greenfoodchallenge.FirebaseHelper.Firestore.UUID_TAG;

// Note: this is not a singleton because it can cause a memory leak due to holding the
// database reference for a long time. Usage case is then to instantiate a helper object for usage
// whenever it's needed. Since it only grabs reference, it's cheap to create.

public class FirebaseHelper {

    private static final String TAG = "FirebaseHelper";

    private final Firestore firestoreInstance = new Firestore();
    private final Functions functionsInstance = new Functions();
    private final Storage storageInstance = new Storage();

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference usersCollection = db.collection("users");
    private final CollectionReference mealsCollection = db.collection("meals");

    private final FirebaseFunctions functions = FirebaseFunctions.getInstance();

    private final FirebaseStorage storage = FirebaseStorage.getInstance();

    // Returns the Firestore instance
    public Firestore getFirestore() {
        return firestoreInstance;
    }

    // Returns the Functions instance
    public Functions getFunctions() {
        return functionsInstance;
    }

    public Storage getStorage() {
        return storageInstance;
    }

    final class Firestore {

        private Firestore() {
        }

        // Field constants for user
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

        //Field constants for meals
        public static final String MEAL_NAME = "mealName";
        public static final String MEAL_PROTEIN = "mealProtein";
        public static final String MEAL_DESCRIPTION = "mealDescription";

        public static final String RESTAURANT_NAME = "restaurantName";
        public static final String RESTAURANT_LOCATION = "restaurantLocation";

        public static final String UUID_TAG = "UUID";

//        public Map<String, Object> getUserTemplate() {
//            Map<String, Object> user = new HashMap<>();
//
//            // User's first name
//            user.put(FIRST_NAME, "");
//            // User's last name
//            user.put(LAST_NAME, "");
//            // Alias, like a username, in case the user wants the pledge shared under it instead
//            user.put(ALIAS, "");
//            // If the user wants to share the pledge under an alias instead of their name
//            user.put(USE_ALIAS_FOR_NAME, false);
//
//            // Which city the pledge goes under
//            user.put(CITY, "");
//
//            // User's biography
//            user.put(BIO, "");
//
//            // If the pledge is shared anonymously. Overrides all other settings below except city
//            user.put(ANONYMOUS_PLEDGE, false);
//            // If the user wants to share their first name
//            user.put(SHOW_FIRST_NAME, false);
//            // If the user wants to share their last name
//            user.put(SHOW_LAST_NAME, false);
//            // If to replace the user's last name with their last initial when shared
//            user.put(SHOW_LAST_INITIAL_FOR_LAST_NAME, false);
//            // If the user's city is to be shared publicly
//            user.put(SHOW_CITY, false);
//
//            // A reference (in the database) to the pledge for this user
//            // user.put(PLEDGE, pledgeCollection.document("template"));
//
//            // A map representing pledge info
//            user.put(PLEDGE, getPledgeTemplate());
//
//            // A map of the string equivalent of a ProteinSource (via .toString) to the float percentage.
//            // Essentially, a string keyed version of the internal map of a diet.
//            // Starts null since it's designed to be loaded from Diet.exportToStringMap.
//            user.put(DIET, null);
//
//            return user;
//        }
//
//        private Map<String, Object> getPledgeTemplate() {
//            Map<String, Object> pledge = new HashMap<>();
//
//            // Current CO2e usage by user with this pledge. -1 means it's not been set
//            pledge.put(CURRENT_CO2E, -1);
//            // Goal CO2e usage by user with this pledge. -1 means it's not been set
//            pledge.put(GOAL_CO2E, -1);
//
//            return pledge;
//        }

//        // Given a user, will set its userDocument to the latest on the server, but does it asynchronously
//        // Would recommend running this immediately upon user login so the pull can resolve by the time it's needed
//        public void pullUserDocument(final User user) {
//            FirebaseUser firebaseUser = user.getFirebaseUser();
//
//            usersCollection.document(firebaseUser.getUid())
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
//            usersCollection.document(firebaseUser.getUid()).set(user.getUserDocument());
//        }

        public void queryPledgesForViewer(final PledgeFragment fragment) {
            usersCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

        private Functions() {
        }

        public static final String USER_ID = "identifier";
        public static final String FIELD_NAME = "fieldName";
        public static final String FIELD_VALUE = "fieldValue";

        public static final String MEAL_MAP = "mealMap";
        public static final String CREATOR = "creator";

        public static final String NUMBER = "number";
        private static final int NUM_PLEDGES_TO_LIST = 20;

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

        private void internalGetter(String functionName, Map<String, Object> data, OnCompleteListener<HttpsCallableResult> callback) {
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

            data.put(USER_ID, userEmail);

            makeCall(functionName, data).addOnCompleteListener(callback);
        }

        private void internalSetter(String functionName, Map<String, Object> data, OnCompleteListener<HttpsCallableResult> callback) {
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

            data.put(USER_ID, userEmail);

            Task<HttpsCallableResult> task = makeCall(functionName, data);
            if (callback != null) {
                task.addOnCompleteListener(callback);
            }
        }

        // Will grab the user information to display on the user fragment and set it
        public void getUserInfoForDisplay() {
            Log.d(TAG, "getUserInfoForDisplay: getting user data");

            Map<String, Object> data = new HashMap<>();

            internalGetter("getUserInfoForDisplay", data, new OnCompleteListener<HttpsCallableResult>() {
                @Override
                public void onComplete(@NonNull Task<HttpsCallableResult> task) {
                    Map<String, Object> data = (Map<String, Object>) task.getResult().getData();
                    Log.d(TAG, "getUserInfoForDisplay.onComplete: data map: '" + data + "'");
                    User.getCurrent().setDisplayInfo(data);
                }
            });
        }

        public void getUserField(final String fieldToGet, final Consumer<Object> callback) {
            Log.d(TAG, "getUserField: getting field '" + fieldToGet + "'");

            Map<String, Object> data = new HashMap<>();
            data.put(FIELD_NAME, fieldToGet);

            internalGetter("getUserField", data, new OnCompleteListener<HttpsCallableResult>() {
                @Override
                public void onComplete(@NonNull Task<HttpsCallableResult> task) {
                    Map<String, Object> data = (Map<String, Object>) task.getResult().getData();
                    Log.d(TAG, "getUserField: data map '" + data + "'");

                    callback.accept(data.get(FIELD_VALUE));
                }
            });
        }

        public void setUserField(final String fieldToChange, final Object newFieldValue) {
            Log.d(TAG, "setUserField: setting field '" + fieldToChange + "' to value '" + newFieldValue + "'");

            Map<String, Object> data = new HashMap<>();
            data.put(FIELD_NAME, fieldToChange);
            data.put(FIELD_VALUE, newFieldValue);

            internalSetter("setUserField", data, null);
        }

        public void getMeal(String uuid, Consumer<MealCard> callback) {
            Log.d(TAG, "getMeal: getting meal with uuid '" + uuid + "'");

            Map<String, Object> data = new HashMap<>();
            data.put(UUID_TAG, uuid);

            internalGetter("getMeal", data, new OnCompleteListener<HttpsCallableResult>() {
                @Override
                public void onComplete(@NonNull Task<HttpsCallableResult> task) {
                    Map<String, Object> data = (Map<String, Object>) task.getResult().getData();
                    MealCard output = new MealCard(data);

                    callback.accept(output);
                }
            });
        }

        public void setMeal(MealCard meal) {
            Map<String, Object> mealMap = meal.exportToStringMap();
            Log.d(TAG, "setMeal: settings meal '" + mealMap + "'");

            Map<String, Object> data = new HashMap<>();
            data.put(MEAL_MAP, mealMap);

            internalSetter("setMeal", data, null);
        }

        public void deleteMeal(MealCard meal) {
            Map<String, Object> mealMap = meal.exportToStringMap();
            Log.d(TAG, "deleteMeal: deleting meal '" + mealMap + "'");

            mealsCollection.document(meal.getUuid()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.d(TAG, "deleteMeal.onComplete: meal deleted!");
                }
            });
        }

        public void getMealsForList(Consumer<MealCard> callback) {
            mealsCollection.limit(20).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    Log.d(TAG, "getMealsForList.onComplete: completed query and now iterating over documents");
                    for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {
                        Map<String, Object> data = snapshot.getData();

                        if (data.get(UUID_TAG) == "template") {
                            continue;
                        }

                        MealCard output = new MealCard(data);
                        callback.accept(output);
                    }
                }
            });
        }

        public void getPledgeListings(Consumer<List<String>> callback) {
            Log.d(TAG, "getPledgeListings: getting pledge listings");

            Map<String, Object> data = new HashMap<>();
            data.put(NUMBER, NUM_PLEDGES_TO_LIST);

            internalGetter("getPledgeListings", data, new OnCompleteListener<HttpsCallableResult>() {
                @Override
                public void onComplete(@NonNull Task<HttpsCallableResult> task) {
                    List<String> data = (List<String>) task.getResult().getData();
                    Log.d(TAG, "getPledgeListings.onComplete: received pledge list is '" + data + "'");

                    callback.accept(data);
                }
            });
        }

    }

    final class Storage {

        private Storage() {
        }

        public void getImage(String path, String imageName, Consumer<File> callback) {
            StorageReference imageRef = storage.getReference().child(path);
            String fileName = imageName.replaceAll("(/)|( )", "_");
            File file;

            String[] imageNameSplit = imageName.split("\\.");
            String extension = imageNameSplit[imageNameSplit.length - 1];

            try {
                file = File.createTempFile(fileName, extension);
            } catch (IOException e) {
                Log.e(TAG, "getImage: failure creating file!");
                return;
            }

            imageRef.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Log.d(TAG, "getImage.onSuccess: successfully downloaded image '" + file.getName() + "'");
                    // Sends file along to callback once complete

                    callback.accept(file);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, "getImage.onFailure: failure downloading image '" + file.getName() + "'");
                    // If there's a download failure instead pass a null
                    callback.accept(null);
                }
            });
        }

        // Given the uuid for a meal will get download its image
        public void getMealImage(String uuid, Consumer<File> callback) {
            getImage("mealPictures/", uuid + ".jpg", callback);
        }

        public void putImage(File image, String path) {
            Uri imageURI = Uri.fromFile(image);
            StorageReference imageRef = storage.getReference().child(path);

            imageRef.putFile(imageURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.d(TAG, "putImage.onSuccess: successfully uploaded image with uri '" + imageURI + "' and path '" + path + "'");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, "getImage.onFailure: failure uploading image with uri '" + imageURI + "' and path '" + path + "'");
                }
            });
        }

        // Given an image URI and a meal's uuid will upload the image
        public void putMealImage(Uri imageURI, String uuid) {
            String[] imageSplitEndOfPath = imageURI.getLastPathSegment().split("\\.");
            String imageExtension = imageSplitEndOfPath[imageSplitEndOfPath.length - 1];

            String path = "mealPictures/" + uuid + "." + imageExtension;
            putImage(new File(imageURI.getPath()), path);
        }
    }

}
