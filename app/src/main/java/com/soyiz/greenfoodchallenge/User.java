package com.soyiz.greenfoodchallenge;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;

import java.util.Map;

public class User {

    private Diet currentDiet = null;
    private Diet goalDiet = null;
    private Pledge currentPledge = null;

    private FirebaseUser firebaseUser = null;
    private Map<String, Object> userDocument = null;

    public User(Diet current, Diet goal, Pledge pledge) {
        currentDiet = current;
        goalDiet = goal;
        currentPledge = pledge;
    }

    // In addition to simply returning the user document, it'll fully update it with diet
    public Map<String, Object> getUserDocument()
    {
        userDocument.put(FirestoreHelper.DIET, currentDiet.exportToStringMap());
        return userDocument;
    }

    public void setUserDocument(Map<String, Object> document)
    {
        currentDiet.loadFromStringMap((Map<String, Float>)document.get(FirestoreHelper.DIET));

        // Doing this since we don't want to mess with the raw map and expect useful side effects
        document.remove(FirestoreHelper.DIET);

        userDocument = document;

        //TODO: this may or may not be a good idea. Decide once the pledge stuff is done, asking @Sahaj about auth too
//        if (getCurrentPledge() == null)
//        {
//            (new FirestoreHelper()).pullPledgeDocument(this);
//        }
    }

    public void setPledgeDocument(Map<String, Object> document)
    {
        currentPledge.loadFromStringMap(document);
    }

    public FirebaseUser getFirebaseUser()
    {
        return firebaseUser;
    }

    public void setFirebaseUser(FirebaseUser user)
    {
        firebaseUser = user;
    }

    // Why are there so many of these freaking getters/setters for all the user doc fields?
    // Well, there is a legitimate use case where we want to update the doc every time a field is set
    // So unfortunately the best solution to that is making real getters/setters.
    // Otherwise, I'd do a public map (albeit with a wrapper of some sort).

    // region userDocument getters/setters
    public String getFirstName()
    {
        return (String) userDocument.get(FirestoreHelper.FIRST_NAME);
    }

    public void setFirstName(String name)
    {
        userDocument.put(FirestoreHelper.FIRST_NAME, name);
    }

    public String getLastName()
    {
        return (String) userDocument.get(FirestoreHelper.LAST_NAME);
    }

    public void setLastName(String name)
    {
        userDocument.put(FirestoreHelper.LAST_NAME, name);
    }

    public String getAlias()
    {
        return (String) userDocument.get(FirestoreHelper.ALIAS);
    }

    public void setAlias(String alias)
    {
        userDocument.put(FirestoreHelper.ALIAS, alias);
    }

    public Boolean getUseAliasForName()
    {
        return (Boolean) userDocument.get(FirestoreHelper.USE_ALIAS_FOR_NAME);
    }

    public void setUseAliasForName(Boolean value)
    {
        userDocument.put(FirestoreHelper.USE_ALIAS_FOR_NAME, value);
    }

    // TODO: @Sahaj make this consistent with however you did the municipality ID thing
    public String getCity()
    {
        return (String) userDocument.get(FirestoreHelper.CITY);
    }

    // TODO: @Sahaj make this consistent with however you did the municipality ID thing
    public void setCity(String city)
    {
        userDocument.put(FirestoreHelper.CITY, city);
    }

    public Boolean getAnonymousPledge()
    {
        return (Boolean) userDocument.get(FirestoreHelper.ANONYMOUS_PLEDGE);
    }

    public void setAnonymousPledge(Boolean value)
    {
        userDocument.put(FirestoreHelper.ANONYMOUS_PLEDGE, value);
    }

    public Boolean getShowFirstName()
    {
        return (Boolean) userDocument.get(FirestoreHelper.SHOW_FIRST_NAME);
    }

    public void setShowFirstName(Boolean value)
    {
        userDocument.put(FirestoreHelper.SHOW_FIRST_NAME, value);
    }

    public Boolean getShowLastName()
    {
        return (Boolean) userDocument.get(FirestoreHelper.SHOW_LAST_NAME);
    }

    public void setShowLastName(Boolean value)
    {
        userDocument.put(FirestoreHelper.SHOW_LAST_NAME, value);
    }

    public Boolean getShowLastInitialForLastName()
    {
        return (Boolean) userDocument.get(FirestoreHelper.SHOW_LAST_INITIAL_FOR_LAST_NAME);
    }

    public void setShowLastInitialForLastName(Boolean value)
    {
        userDocument.put(FirestoreHelper.SHOW_LAST_INITIAL_FOR_LAST_NAME, value);
    }

    public Boolean getShowCity()
    {
        return (Boolean) userDocument.get(FirestoreHelper.SHOW_CITY);
    }

    public void setShowCity(Boolean value)
    {
        userDocument.put(FirestoreHelper.SHOW_CITY, value);
    }

    public DocumentReference getPledgeReference()
    {
        return (DocumentReference) userDocument.get(FirestoreHelper.PLEDGE);
    }

    public void setPledgeReference(DocumentReference pledge)
    {
        userDocument.put(FirestoreHelper.PLEDGE, pledge);
    }
    // endregion

    public Float getCurrentCO2e()
    {
        return currentPledge.getCurrentCO2eSavings();
    }

    public Float getGoalCO2e()
    {
        return currentPledge.getGoalCO2eSavings();
    }

    public Diet getCurrentDiet() {
        return currentDiet;
    }

    public void setCurrentDiet(Diet diet) {
        currentDiet = diet;
    }

    public Diet getGoalDiet() {
        return goalDiet;
    }

    public void setGoalDiet(Diet diet) {
        goalDiet = diet;
    }

    public Pledge getCurrentPledge() {
        return currentPledge;
    }

    public void setCurrentPledge(Pledge pledge) {
        currentPledge = pledge;
    }

    public void removePledge() {
        currentPledge = null;
    }

}
