package com.example.collegeapplicationsystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "DEFAULT_NAME";

    private static final int RC_SIGN_IN_REQUEST_CODE = 0;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        mAuth = FirebaseAuth.getInstance();
    }

    public void createSignInIntent(View view) {
        List<AuthUI.IdpConfig> providers = Collections.singletonList(
                new AuthUI.IdpConfig.EmailBuilder().build());

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setIsSmartLockEnabled(false)
                        .setTheme(R.style.SignInTheme)
                        .build(),
                RC_SIGN_IN_REQUEST_CODE);
    }

    private void startCollegeIntent() {
        Intent intent = new Intent(getApplicationContext(), MainSearchMenuActivity.class);
        startActivity(intent);
    }

    private void addAndSetUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("userdata")
                .whereEqualTo("email", user.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult().isEmpty()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            Map<String, Object> userdata = new HashMap<>();
                            userdata.put("email", user.getEmail());
                            userdata.put("reading", 0);
                            userdata.put("math", 0);

                            String[] fullName = user.getDisplayName().trim().split(" ");
                            if (fullName.length < 2) {
                                userdata.put("firstName", fullName[0]);
                                userdata.put("lastName", "none");
                            } else {
                                userdata.put("firstName", fullName[0]);
                                userdata.put("lastName", fullName[1]);
                            }

                            db.collection("userdata").document(user.getEmail())
                                    .set(userdata)
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w("User data entry", "Error writing document", e);
                                        }
                                    });
                        }
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN_REQUEST_CODE) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                addAndSetUser();
                Toast.makeText(this, "Sign in successful!", Toast.LENGTH_SHORT).show();
                startCollegeIntent();
            } else {
                Toast.makeText(this, "Sign in failed!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
