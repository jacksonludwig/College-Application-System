package com.example.collegeapplicationsystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class ViewAccountActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private static final String TAG = "USER_QUERY";
    private static final String TAG2 = "USER DATA ENTRY";

    private static final int EDIT_FIRST_NAME_REQUEST_CODE = 10;
    private static final int EDIT_LAST_NAME_REQUEST_CODE = 20;
    private static final int EDIT_MATH_SCORE_REQUEST_CODE = 30;
    private static final int EDIT_READING_SCORE_REQUEST_CODE = 40;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private TextView emailTextView;
    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private TextView mathScoreTextView;
    private TextView readingScoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_account);

        setAccountView();
    }

    private void setAccountView() {
        setEmailView();
        setNameAndScoreView();
    }

    private void setEmailView() {
        emailTextView = findViewById(R.id.email_textview);
        emailTextView.setText(user.getEmail());
    }

    private void setNameAndScoreView() {
        firstNameTextView = findViewById(R.id.firstname_textview);
        lastNameTextView = findViewById(R.id.lastname_textview);
        mathScoreTextView = findViewById(R.id.math_textview);
        readingScoreTextView = findViewById(R.id.reading_textview);
        db.collection("userdata")
                .document(user.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                firstNameTextView.setText(document.get("firstName").toString());
                                lastNameTextView.setText(document.get("lastName").toString());
                                mathScoreTextView.setText(document.get("math").toString());
                                readingScoreTextView.setText(document.get("reading").toString());
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
    }

    private void updateFirstName(final String name) {
        db.collection("userdata")
                .whereEqualTo("email", user.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            Map<String, Object> userdata = new HashMap<>();
                            userdata.put("firstName", name);

                            db.collection("userdata").document(user.getEmail())
                                    .update(userdata)
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG2, "Error writing document", e);
                                        }
                                    });
                            firstNameTextView = findViewById(R.id.firstname_textview);
                            firstNameTextView.setText(name);
                        }
                    }
                });
        syncFirstName(name);
    }

    private void syncFirstName(String name) {
        String[] username = user.getDisplayName().split(" ");
        UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(name + " " + username[1])
                .build();

        updateUserProfile(userProfileChangeRequest);
    }

    private void syncLastName(String name) {
        String[] username = user.getDisplayName().split(" ");
        UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(username[0] + " " + name)
                .build();

        updateUserProfile(userProfileChangeRequest);
    }

    private void updateUserProfile(UserProfileChangeRequest userProfileChangeRequest) {
        user.updateProfile(userProfileChangeRequest)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG2, "User profile changed");
                        }
                    }
                });
    }

    private void updateLastName(final String name) {
        db.collection("userdata")
                .whereEqualTo("email", user.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            Map<String, Object> userdata = new HashMap<>();
                            userdata.put("lastName", name);

                            db.collection("userdata").document(user.getEmail())
                                    .update(userdata)
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG2, "Error writing document", e);
                                        }
                                    });
                            lastNameTextView = findViewById(R.id.lastname_textview);
                            lastNameTextView.setText(name);
                        }
                    }
                });
        syncLastName(name);
    }

    private void updateMathScore(final String score) {
        db.collection("userdata")
                .whereEqualTo("email", user.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            Map<String, Object> userdata = new HashMap<>();
                            userdata.put("math", score);

                            db.collection("userdata").document(user.getEmail())
                                    .update(userdata)
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG2, "Error writing document", e);
                                        }
                                    });
                            mathScoreTextView = findViewById(R.id.math_textview);
                            mathScoreTextView.setText(score);
                        }
                    }
                });

    }

    private void updateReadingScore(final String score) {
        db.collection("userdata")
                .whereEqualTo("email", user.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            Map<String, Object> userdata = new HashMap<>();
                            userdata.put("reading", score);

                            db.collection("userdata").document(user.getEmail())
                                    .update(userdata)
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG2, "Error writing document", e);
                                        }
                                    });
                            readingScoreTextView = findViewById(R.id.reading_textview);
                            readingScoreTextView.setText(score);
                        }
                    }
                });

    }


    public void editAccount(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.edit_account_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.editFirst:
                Intent firstIntent = new Intent(getApplicationContext(), EditFirstNamePopupActivity.class);
                startActivityForResult(firstIntent, EDIT_FIRST_NAME_REQUEST_CODE);
                return true;
            case R.id.editLast:
                Intent lastIntent = new Intent(getApplicationContext(), EditLastNamePopupActivity.class);
                startActivityForResult(lastIntent, EDIT_LAST_NAME_REQUEST_CODE);
                return true;
            case R.id.editMath:
                Intent mathIntent = new Intent(getApplicationContext(), EditMathScorePopupActivity.class);
                startActivityForResult(mathIntent, EDIT_MATH_SCORE_REQUEST_CODE);
                return true;
            case R.id.editReading:
                Intent readingIntent = new Intent(getApplicationContext(), EditReadingScorePopupActivity.class);
                startActivityForResult(readingIntent, EDIT_READING_SCORE_REQUEST_CODE);
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_FIRST_NAME_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                updateFirstName(data.getStringExtra("firstName"));
            } else {
                showErrorToast();
            }
        } else if (requestCode == EDIT_LAST_NAME_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                updateLastName(data.getStringExtra("lastName"));
            } else {
                showErrorToast();
            }
        } else if (requestCode == EDIT_MATH_SCORE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                updateMathScore(data.getStringExtra("mathScore"));
            } else {
                showErrorToast();
            }
        } else if (requestCode == EDIT_READING_SCORE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                updateReadingScore(data.getStringExtra("readingScore"));
            } else {
                showErrorToast();
            }
        }
    }

    private void showErrorToast() {
        Toast.makeText(this, "data not updated", Toast.LENGTH_SHORT).show();
    }
}

