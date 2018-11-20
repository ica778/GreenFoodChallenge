package com.soyiz.greenfoodchallenge;

import android.net.Uri;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.internal.firebase_auth.zzcz;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.auth.UserInfo;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class UserTest {

    private User user = new User();
    private Diet curDiet = new Diet();
    private Diet gDiet = new Diet();

    public void setCurDiet() {
        curDiet.setProteinPercent(ProteinSource.Beef, 35);
        curDiet.setProteinPercent(ProteinSource.Pork, 10);
        curDiet.setProteinPercent(ProteinSource.Chicken, 35);
        curDiet.setProteinPercent(ProteinSource.Fish, 10);
        curDiet.setProteinPercent(ProteinSource.Eggs, 5);
        curDiet.setProteinPercent(ProteinSource.Beans, 0);
        curDiet.setProteinPercent(ProteinSource.Vegetables, 5);
    }

    public void setGDiet() {
        gDiet.setProteinPercent(ProteinSource.Beef, 100);
        gDiet.setProteinPercent(ProteinSource.Pork, 0);
        gDiet.setProteinPercent(ProteinSource.Chicken, 0);
        gDiet.setProteinPercent(ProteinSource.Fish, 0);
        gDiet.setProteinPercent(ProteinSource.Eggs, 0);
        gDiet.setProteinPercent(ProteinSource.Beans, 0);
        gDiet.setProteinPercent(ProteinSource.Vegetables, 0);
    }

    @Test
    public void setUserDocument() {

    }

    @Test
    public void setPledgeDocument() {
    }

    @Test
    public void setFirebaseUser() {
        FirebaseUser fbuser = new FirebaseUser() {
            @NonNull
            @Override
            public String getUid() {
                return null;
            }

            @NonNull
            @Override
            public String getProviderId() {
                return null;
            }

            @Override
            public boolean isAnonymous() {
                //default is false
                return true;
            }

            @Nullable
            @Override
            public List<String> getProviders() {
                return null;
            }

            @NonNull
            @Override
            public List<? extends UserInfo> getProviderData() {
                return null;
            }

            @NonNull
            @Override
            public FirebaseUser zza(@NonNull List<? extends UserInfo> list) {
                return null;
            }

            @Override
            public FirebaseUser zzce() {
                return null;
            }

            @NonNull
            @Override
            public FirebaseApp zzcc() {
                return null;
            }

            @Nullable
            @Override
            public String getDisplayName() {
                return null;
            }

            @Nullable
            @Override
            public Uri getPhotoUrl() {
                return null;
            }

            @Nullable
            @Override
            public String getEmail() {
                return null;
            }

            @Nullable
            @Override
            public String getPhoneNumber() {
                return null;
            }

            @Nullable
            @Override
            public String zzcf() {
                return null;
            }

            @NonNull
            @Override
            public zzcz zzcg() {
                return null;
            }

            @Override
            public void zza(@NonNull zzcz zzcz) {

            }

            @NonNull
            @Override
            public String zzch() {
                return null;
            }

            @NonNull
            @Override
            public String zzci() {
                return null;
            }

            @Nullable
            @Override
            public FirebaseUserMetadata getMetadata() {
                return null;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {

            }

            @Override
            public boolean isEmailVerified() {
                //default is false
                return true;
            }
        };
        user.setFirebaseUser(fbuser);
        assertEquals(fbuser, user.getFirebaseUser());
    }

    //All throw null pointer exceptions, to fix!!!
  /*  @Test
    public void setFirstName() {
        String name = "Name";
        user.setFirstName(name);
        assertEquals(name, user.getFirstName());
    }

    @Test
    public void setLastName() {
        String name = "Name";
        user.setLastName(name);
        assertEquals(name, user.getLastName());
    }

    @Test
    public void setAlias() {
        String name = "Name";
        user.setAlias(name);
        assertEquals(name, user.getAlias());
    }

    @Test
    public void setUseAliasForName() {
        Boolean bool = user.getUseAliasForName();
        bool = !bool;
        user.setUseAliasForName(bool);
        assertEquals(bool, user.getUseAliasForName());
    }

    @Test
    public void setCity() {
        String name = "Name";
        user.setCity(name);
        assertEquals(name, user.getCity());
    }

    @Test
    public void setAnonymousPledge() {
        Boolean bool = user.getAnonymousPledge();
        bool = !bool;
        user.setAnonymousPledge(bool);
        assertEquals(bool, user.getAnonymousPledge());
    }

    @Test
    public void setShowFirstName() {
        Boolean bool = user.getShowFirstName();
        bool = !bool;
        user.setShowFirstName(bool);
        assertEquals(bool, user.getShowFirstName());
    }

    @Test
    public void setShowLastName() {
        Boolean bool = user.getShowLastName();
        bool = !bool;
        user.setShowLastName(bool);
        assertEquals(bool, user.getShowLastName());
    }

    @Test
    public void setShowLastInitialForLastName() {
        Boolean bool = user.getShowLastInitialForLastName();
        bool = !bool;
        user.setShowLastInitialForLastName(bool);
        assertEquals(bool, user.getShowLastInitialForLastName());
    }

    @Test
    public void setShowCity() {
        Boolean bool = user.getShowCity();
        bool = !bool;
        user.setShowCity(bool);
        assertEquals(bool, user.getShowCity());
    }*/

    @Test
    public void setPledgeReference() {
    }

    @Test
    public void setCurrentDiet() {
        setCurDiet();
        user.setCurrentDiet(curDiet);
        assertEquals(curDiet, user.getCurrentDiet());
    }

    @Test
    public void setGoalDiet() {
        setGDiet();
        user.setGoalDiet(gDiet);
        assertEquals(gDiet, user.getGoalDiet());
    }

    @Test
    public void setCurrentPledge() {
        Pledge pledge = new Pledge();
        pledge.setGoalCO2eSavings(111f);
        user.setCurrentPledge(pledge);
        assertEquals(pledge, user.getCurrentPledge());
    }

    @Test
    public void removePledge() {
        Pledge pledge = new Pledge();
        pledge.setGoalCO2eSavings(111f);
        user.setCurrentPledge(pledge);
        user.removePledge();
        assertEquals(null, user.getCurrentPledge());
    }
}